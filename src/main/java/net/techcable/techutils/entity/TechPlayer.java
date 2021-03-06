/**
 * The MIT License
 * Copyright (c) 2014-2015 Techcable
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.techcable.techutils.entity;

import lombok.*;

import java.util.UUID;

import net.techcable.techutils.TechPlugin;
import net.techcable.techutils.scoreboard.BukkitTechBoard;
import net.techcable.techutils.scoreboard.TechBoard;
import net.techcable.techutils.uuid.UUIDUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TechPlayer {

    private final UUID id;
    @SuppressWarnings("unchecked")
    private final TechPlugin<?> plugin;

    /**
     * Retreive the plugin that owns this player
     *
     * @return this player's plugin
     */
    public TechPlugin<?> getPlugin() {
        return this.plugin;
    }

    /**
     * Retreive this player's uuid
     *
     * @return this player's uuid
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Retrieve the entity backing this techplayer
     *
     * @return the accociated player entity
     */
    public Player getEntity() {
        return Bukkit.getPlayer(getId());
    }

    /**
     * Returns if the player is online
     *
     * @return if the player is online
     */
    public boolean isOnline() {
        return getEntity() != null; // isOnline() is stupid and just does Bukkit.getPlayer() in an inefficient way
    }

    /**
     * Retreive this player's username
     *
     * @return this player's username
     */
    public String getName() {
        String name = UUIDUtils.getName(getId());
        assert name != null : "Unable to lookup name";
        return name;
    }

    final void destroy() {
        if (scoreboard != null) scoreboard.cleanup();
        cleanup();
    }

    private TechBoard scoreboard;

    /**
     * Get the player's scoreboard, creating it if it doesn't exist
     *
     * @return the player's scoreboard, creating it if it doesn't exist
     */
    public TechBoard getScoreboard() {
        if (scoreboard == null) {
            scoreboard = new BukkitTechBoard(this);
        }
        return scoreboard;
    }

    /**
     * Teleport the player to the specified location
     * <p/>
     * Dismounts the player if it is mounted
     * 
     * @return if successful
     */
    public boolean teleport(Location to) {
        return teleport(to, false);
    }

    /**
     * Teleport the player to the specified location
     * 
     * @param preserveMount if we should teleport this player's vehicle too
     * 
     * @return if successful
     */
    public boolean teleport(Location to, boolean preserveMount) {
        return teleport(getEntity(), to, preserveMount);
    }

    private static boolean teleport(Entity entity, Location to, boolean preserveMount) {
        if (entity == null) return false;
        Entity vehicle = entity.getVehicle();
        boolean wasRiding = entity.leaveVehicle();
        boolean success = entity.teleport(to);
        if (wasRiding && preserveMount) {
            teleport(vehicle, to, preserveMount);
            vehicle.setPassenger(entity);
        }
        return success;
    }

    /**
     * Perform any necessary cleanup actions
     */
    protected void cleanup() {

    }

    public final boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (other instanceof TechPlayer) {
            UUID otherId = ((TechPlayer) other).getId();
            return getId().equals(otherId);
        }
        return false;
    }

    public final int hashCode() {
        return getId().hashCode();
    }
}
