package net.techcable.minecraft.techutils.offlineplayer;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor
@Getter
public class PlayerPlayerData implements PlayerData {
	private Player player;

	@Override
	public ItemStack[] getArmor() {
		return getInventory().getArmorContents();
	}

	@Override
	public ItemStack getHelmet() {
		return getInventory().getHelmet();
	}

	@Override
	public ItemStack getChestplate() {
		return getInventory().getChestplate();
	}

	@Override
	public ItemStack getLeggings() {
		return getInventory().getLeggings();
	}

	@Override
	public ItemStack getBoots() {
		return getInventory().getBoots();
	}

	@Override
	public void setArmor(ItemStack[] armor) {
		getInventory().setArmorContents(armor);
	}

	@Override
	public void setHelmet(ItemStack helmet) {
		getInventory().setHelmet(helmet);
	}

	@Override
	public void setChestplate(ItemStack chestplate) {
		getInventory().setChestplate(chestplate);
	}

	@Override
	public void setLeggings(ItemStack leggings) {
		getInventory().setLeggings(leggings);
	}

	@Override
	public void setBoots(ItemStack boots) {
		getInventory().setBoots(boots);
	}

	@Override
	public float getExp() {
		return getPlayer().getExp();
	}

	@Override
	public void setExp(float exp) {
		getPlayer().setExp(exp);
	}

	@Override
	public int getLevel() {
		return getPlayer().getLevel();
	}

	@Override
	public void setLevel(int level) {
		getPlayer().setLevel(level);
	}

	@Override
	public float getHealth() {
		return (float) getPlayer().getHealth();
	}

	@Override
	public void setHealth(float health) {
		getPlayer().setHealth(health);
	}

	@Override
	public int getFoodLevel() {
		return getPlayer().getFoodLevel();
	}

	@Override
	public void setFoodLevel(int foodLevel) {
		getPlayer().setFoodLevel(foodLevel);
	}

	@Override
	public float getSaturation() {
		return getPlayer().getSaturation();
	}

	@Override
	public void setSaturation(float saturation) {
		getPlayer().setSaturation(saturation);
	}

	@Override
	public float getExhaustion() {
		return getPlayer().getExhaustion();
	}

	@Override
	public void setExhaustion(float exhaustion) {
		getPlayer().setExhaustion(exhaustion);
	}

	@Override
	public ItemStack[] getEnderchest() {
		return getPlayer().getEnderChest().getContents();
	}

	@Override
	public void setEnderchest(ItemStack[] enderchest) {
		getPlayer().getEnderChest().setContents(enderchest);
	}

	@Override
	public void setEnderchestItem(int slot, ItemStack item) {
		getPlayer().getEnderChest().setItem(slot, item);
	}

	@Override
	public ItemStack getEnderchestItem(int slot) {
		return getPlayer().getEnderChest().getItem(slot);
	}

	@Override
	public ItemStack[] getItems() {
		return getInventory().getContents();
	}

	@Override
	public void setItems(ItemStack[] items) {
		getInventory().setContents(items);
	}

	@Override
	public ItemStack getItem(int slot) {
		return getInventory().getItem(slot);
	}

	@Override
	public void setItem(int slot, ItemStack item) {
		getInventory().setItem(slot, item);
	}

	@Override
	public int getFireTicks() {
		return getPlayer().getFireTicks();
	}

	@Override
	public void setFireTicks(int ticks) {
		getPlayer().setFireTicks(ticks);
	}

	@Override
	public int getAir() {
		return getPlayer().getRemainingAir();
	}

	@Override
	public void setAir(int air) {
		getPlayer().setRemainingAir(air);
	}

	@Override
	public World getWorld() {
		return getLocation().getWorld();
	}

	@Override
	public Location getLocation() {
		return getPlayer().getLocation();
	}

	@Override
	public void load() {}

	@Override
	public void save() {
		getPlayer().saveData();
	}

	@Override
	public void addPotionEffect(PotionEffect effect) {
		getPlayer().addPotionEffect(effect);
	}

	@Override
	public void addPotionEffects(Collection<PotionEffect> effects) {
		getPlayer().addPotionEffects(effects);
	}

	@Override
	public Collection<PotionEffect> getPotionEffects() {
		return getPlayer().getActivePotionEffects();
	}

	@Override
	public void removePotionEffect(PotionEffectType type) {
		getPlayer().removePotionEffect(type);
	}
	
	public PlayerInventory getInventory() {
		return getPlayer().getInventory();
	}
}
