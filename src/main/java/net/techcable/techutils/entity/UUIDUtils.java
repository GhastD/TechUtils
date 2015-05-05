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

import java.util.UUID;

import com.google.common.base.Charsets;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.techcable.techutils.entity.ProfileUtils.PlayerProfile;

public class UUIDUtils {
    private UUIDUtils() {}
    
    /**
     * Retreive a player's UUID based on it's name
     * 
     * Returns null if lookup failed
     * 
     * @param name the player's name
     * @return the player's uuid, or null if failed
     */
    public static UUID getId(String name) {
    	if (ProfileUtils.getIfCached(name) != null) return ProfileUtils.getIfCached(name).getId(); //Previously cached by UUIDUtils.getPlayerExact()
    	if (getPlayerExact(name) != null) {
    		return getPlayerExact(name).getUniqueId();
    	}
        if (Bukkit.getOnlineMode()) {
            PlayerProfile profile = ProfileUtils.lookup(name);
            if (profile == null) return null;
            return profile.getId();
        } else {
            return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
        }
    }
    
    /**
     * Retreive a player's name based on it's uuid
     * 
     * Returns null if lookup failed
     * 
     * @param name the player's uuid
     * @return the player's name, or null if failed
     */
    public static String getName(UUID id) {
        if (ProfileUtils.getIfCached(id) != null) return ProfileUtils.getIfCached(id).getName();
    	if (Bukkit.getPlayer(id) != null) {
            String name = Bukkit.getPlayer(id).getName();
            ProfileUtils.addToCache(id, name); //Saves us a potential lookup by staying in the cache after player leaves
            return name;
        }
        if (Bukkit.getOnlineMode()) {
            PlayerProfile profile = ProfileUtils.lookup(id);
            if (profile == null) return null;
            return profile.getName();
        } else {
            OfflinePlayer player = Bukkit.getOfflinePlayer(id);
            return player.getName();
        }
    }
    
    /**
     * A faster version of Bukkit.getPlayerExact()
     * <p>
     * Bukkit.getPlayerExact() iterates through all online players <br>
     * This caches results from Bukkit.getPlayerExact() to speed up lookups
     * 
     * @see Bukkit#getPlayerExact(String)
     * 
     * @param name get player with this name
     * @return player with specified name
     */
     public static Player getPlayerExact(String name) {
     	if (ProfileUtils.getIfCached(name) != null) return Bukkit.getPlayer(ProfileUtils.getIfCached(name).getId());
     	if (Bukkit.getPlayerExact(name) != null) {
            UUID id = Bukkit.getPlayerExact(name).getUniqueId();
            /*
             * Calling Bukkit.getPlayerExact() iterates through all online players, making it far slower than hashmap retreival
             * This has the added benefit of remaining in the cache even after the player leaves; potentially saving a mojang lookup for uuid fetching
             */
            ProfileUtils.addToCache(id, name); 
            return Bukkit.getPlayer(id);
        }
     	return null;
     }
}