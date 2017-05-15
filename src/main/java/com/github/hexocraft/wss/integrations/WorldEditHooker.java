package com.github.hexocraft.wss.integrations;

/*
 * Copyright 2017 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.hexocraft.wss.utils.Box;
import com.github.hexocraftapi.integration.Hooker;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class WorldEditHooker extends Hooker<com.sk89q.worldedit.bukkit.WorldEditPlugin,WorldEditHooker>
{
	public WorldEditHooker() {
		super();
	}

	// Capture the plugin if exist
	public WorldEditHooker capture(com.sk89q.worldedit.bukkit.WorldEditPlugin worldEditPlugin)
	{
		this.plugin = worldEditPlugin;
		return this;
	}

	public void select(Player player, Box box)
	{
		if(player == null) throw new IllegalArgumentException("Null player not allowed");
		if(!player.isOnline()) throw new IllegalArgumentException("Offline player not allowed");

		if(box == null) {
			BukkitPlayer worldEditPlayer = plugin.wrapPlayer(player);
			LocalSession session = WorldEdit.getInstance().getSessionManager().get(worldEditPlayer);
			session.getRegionSelector(worldEditPlayer.getWorld()).clear();
			session.dispatchCUISelection(worldEditPlayer);
			return;
		}

		Location lower = new Location(player.getWorld(), box.getLower().getX(), box.getLower().getY(), box.getLower().getZ());
		Location upper = new Location(player.getWorld(), box.getUpper().getX()-1, box.getUpper().getY()-1, box.getUpper().getZ()-1);
		plugin.setSelection(player, new CuboidSelection(player.getWorld(), lower, upper));
	}

	/**
	 * this function is inspired from com.sk89q.worldedit.bukkit.WorldEditPlugin.getSelection
	 *
	 * @param player Player
	 * @return player region
	 */
	@SuppressWarnings("deprecation")
	public Region getRegion(Player player)
	{
		if(player == null) throw new IllegalArgumentException("Null player not allowed");
		if(!player.isOnline()) throw new IllegalArgumentException("Offline player not allowed");

		BukkitPlayer worldEditPlayer = plugin.wrapPlayer(player);
		LocalSession session = WorldEdit.getInstance().getSessionManager().get(worldEditPlayer);
		RegionSelector selector = session.getRegionSelector(worldEditPlayer.getWorld());
		try {
			return selector.getRegion();
		} catch (IncompleteRegionException e) {
			return null;
		}
	}

	/**
	 * @param player Player
	 * @param location Location
	 * @return true if @location is in @player selection
	 */
	public boolean isLocationInSelection(Player player, Location location)
	{
		Selection selection = plugin.getSelection(player);
		return selection != null && selection.contains(location);
	}
}
