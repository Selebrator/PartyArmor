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

import de.selebrator.partyarmor.runnable.PartyArmorRunnable;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartyArmorPlugin extends JavaPlugin {

	public static String HAT_NAME;
	public static String SHIRT_NAME;
	public static String PANTS_NAME;
	public static String SHOES_NAME;
	public static List<String> HAT_LORE;
	public static List<String> SHIRT_LORE;
	public static List<String> PANTS_LORE;
	public static List<String> SHOES_LORE;
	public static float HAT_OFFSET;
	public static float SHIRT_OFFSET;
	public static float PANTS_OFFSET;
	public static float SHOES_OFFSET;
	public static float STEP_SIZE;
	public static boolean DESYNC_COLOR;

	@Override
	public void onEnable() {
		this.loadConfig();
		new PartyArmorRunnable().runTaskTimer(this, 0L, this.getConfig().getLong("tick_period"));
	}

	private void loadConfig() {
		this.saveDefaultConfig();

		//cashing for speed
		if(!this.getConfig().contains("helmet", true)) {
			//deprecated
			HAT_NAME = colored(this.getConfig().getString("helmet_name"));
			HAT_LORE = colored(this.getConfig().getStringList("helmet_lore"));
			HAT_OFFSET = (float) this.getConfig().getDouble("helmet_offset");
		} else {
			HAT_NAME = colored(this.getConfig().getString("helmet.name"));
			HAT_LORE = colored(this.getConfig().getStringList("helmet.lore"));
			HAT_OFFSET = (float) this.getConfig().getDouble("helmet.offset");
		}

		if(!this.getConfig().contains("chestplate", true)) {
			//deprecated
			SHIRT_NAME = colored(this.getConfig().getString("chestplate_name"));
			SHIRT_LORE = colored(this.getConfig().getStringList("chestplate_lore"));
			SHIRT_OFFSET = (float) this.getConfig().getDouble("chestplate_offset");
		} else {
			SHIRT_NAME = colored(this.getConfig().getString("chestplate.name"));
			SHIRT_LORE = colored(this.getConfig().getStringList("chestplate.lore"));
			SHIRT_OFFSET = (float) this.getConfig().getDouble("chestplate.offset");
		}

		if(!this.getConfig().contains("leggings", true)) {
			//deprecated
			PANTS_NAME = colored(this.getConfig().getString("leggings_name"));
			PANTS_LORE = colored(this.getConfig().getStringList("leggings_lore"));
			PANTS_OFFSET = (float) this.getConfig().getDouble("leggings_offset");
		} else {
			PANTS_NAME = colored(this.getConfig().getString("leggings.name"));
			PANTS_LORE = colored(this.getConfig().getStringList("leggings.lore"));
			PANTS_OFFSET = (float) this.getConfig().getDouble("leggings.offset");
		}

		if(!this.getConfig().contains("boots", true)) {
			//deprecated
			SHOES_NAME = colored(this.getConfig().getString("boots_name"));
			SHOES_LORE = colored(this.getConfig().getStringList("boots_lore"));
			SHOES_OFFSET = (float) this.getConfig().getDouble("boots_offset");
		} else {
			SHOES_NAME = colored(this.getConfig().getString("boots.name"));
			SHOES_LORE = colored(this.getConfig().getStringList("boots.lore"));
			SHOES_OFFSET = (float) this.getConfig().getDouble("boots.offset");
		}

		STEP_SIZE = (float) this.getConfig().getDouble("color_step_size");
		DESYNC_COLOR = this.getConfig().getBoolean("desync_color");
	}

	private static String colored(String plain) {
		return ChatColor.translateAlternateColorCodes('&', plain);
	}

	private static List<String> colored(List<String> plain) {
		return plain.stream()
				.filter(Objects::nonNull)
				.map(PartyArmorPlugin::colored)
				.collect(Collectors.toList());
	}
}
