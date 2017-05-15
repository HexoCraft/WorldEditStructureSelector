package com.github.hexocraft.wss.listeners;

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

import com.github.hexocraft.wss.WssApi;
import com.github.hexocraft.wss.WssPlugin;
import com.github.hexocraft.wss.configuration.Permissions;
import com.github.hexocraftapi.util.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("unused")
public class PlayerListener implements Listener
{
	public PlayerListener(WssPlugin plugin)
	{
		super();
	}

	@EventHandler()
	public void onPlayerInteract(PlayerQuitEvent event)
	{
		WssApi.remove(event.getPlayer());
	}


	@EventHandler(priority= EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		// Get the player
		final Player player = event.getPlayer();

		//
		if(!WssApi.isEnable(player)) return;

		//
		WssApi.WssPlayer wssPlayer = WssApi.getPlayer(player);

		//
		if((event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		   && PlayerUtil.getItemInHand(player) != null
		   && PlayerUtil.getItemInHand(player).getType() == Material.WOOD_AXE
		   && Permissions.has(player, Permissions.ADMIN))
		{
			// Clicked location
			Location clickedLoc = event.getClickedBlock().getLocation();

			// Cancel event
			event.setCancelled(true);

			// Select structure
			WssApi.select(player, clickedLoc);
		}
	}
}
