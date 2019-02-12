/*
 * Copyright (C) 2019  Selebrator
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.selebrator.partyarmor;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;
import java.util.UUID;

import static de.selebrator.partyarmor.PartyArmorPlugin.*;

public class ArmorInventory {

	private float uniqifier;
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	private boolean helmetChanged;
	private boolean chestplateChanged;
	private boolean leggingsChanged;
	private boolean bootsChanged;

	public ArmorInventory(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, float uniqifier) {
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
		this.uniqifier = uniqifier;
	}

	public ArmorInventory(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
		this(helmet, chestplate, leggings, boots, 0.0f);
	}

	public ArmorInventory(Player target) {
		this(target.getInventory().getHelmet(), target.getInventory().getChestplate(), target.getInventory().getLeggings(), target.getInventory().getBoots());
		if(DESYNC_COLOR) {
			this.uniqifier = uniqifier(target.getUniqueId());
		}
	}

	private static float uniqifier(UUID uuid) {
		return (uuid.getLeastSignificantBits() & 0xfffffffffffffffL) / 0x1.0p60f;
	}

	private static boolean isApplicable(ItemStack item, Material wantedMaterial, String wantedName, List<String> wantedLore) {
		if(item == null) {
			return false;
		}
		if(item.getType() != wantedMaterial) {
			return false;
		}
		if(!wantedName.isEmpty()) {
			if(!item.hasItemMeta() || !wantedName.equals(item.getItemMeta().getDisplayName())) {
				return false;
			}
		}
		if(!wantedLore.isEmpty()) {
			if(!item.hasItemMeta() || !item.getItemMeta().hasLore()) {
				return false;
			}
			return item.getItemMeta().getLore().containsAll(wantedLore);
		}

		return true;
	}

	private static Color color(float hue) {
		return Color.fromRGB(java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f) & 0xffffff);
	}

	public void cycle(float step) {
		if(isApplicable(this.helmet, Material.LEATHER_HELMET, HAT_NAME, HAT_LORE)) {
			this.helmet = this.setColor(this.helmet, (step + this.uniqifier + HAT_OFFSET) % 1.0f);
			this.helmetChanged = true;
		}
		if(isApplicable(this.chestplate, Material.LEATHER_CHESTPLATE, SHIRT_NAME, SHIRT_LORE)) {
			this.chestplate = this.setColor(this.chestplate, (step + this.uniqifier + SHIRT_OFFSET) % 1.0f);
			this.chestplateChanged = true;
		}
		if(isApplicable(this.leggings, Material.LEATHER_LEGGINGS, PANTS_NAME, PANTS_LORE)) {
			this.leggings = this.setColor(this.leggings, (step + this.uniqifier + PANTS_OFFSET) % 1.0f);
			this.leggingsChanged = true;
		}
		if(isApplicable(this.boots, Material.LEATHER_BOOTS, SHOES_NAME, SHOES_LORE)) {
			this.boots = this.setColor(this.boots, (step + this.uniqifier + SHOES_OFFSET) % 1.0f);
			this.bootsChanged = true;
		}
	}

	//only use after isApplicable because meta has to exist
	private ItemStack setColor(ItemStack item, float hue) {
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color(hue));
		item.setItemMeta(meta);
		return item;
	}

	public void apply(Player target) {
		PlayerInventory inventory = target.getInventory();
		if(this.helmetChanged) {
			inventory.setHelmet(this.helmet);
		}
		if(this.chestplateChanged) {
			inventory.setChestplate(this.chestplate);
		}
		if(this.leggingsChanged) {
			inventory.setLeggings(this.leggings);
		}
		if(this.bootsChanged) {
			inventory.setBoots(this.boots);
		}
	}
}
