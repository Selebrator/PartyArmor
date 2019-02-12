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

package de.selebrator.partyarmor.runnable;

import de.selebrator.partyarmor.ArmorInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static de.selebrator.partyarmor.PartyArmorPlugin.STEP_SIZE;

public class PartyArmorRunnable extends BukkitRunnable {

	private float step = 0;

	@Override
	public void run() {
		this.step += STEP_SIZE;
		this.step %= 1;
		Bukkit.getOnlinePlayers().forEach(this::cycleArmor);
	}

	private void cycleArmor(Player target) {
		ArmorInventory armor = new ArmorInventory(target);
		armor.cycle(this.step);
		armor.apply(target);
	}
}
