package com.github.hexocraft.wss.commands;
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
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.message.predifined.message.EmptyMessage;
import com.github.hexocraftapi.message.predifined.message.ErrorMessage;
import com.github.hexocraftapi.message.predifined.message.PluginTitleMessage;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

public class WssCommandDisable extends Command<WssPlugin>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public WssCommandDisable(WssPlugin plugin)
	{
		super("disable", plugin);
		this.setAliases(Lists.newArrayList("off", "0"));
		this.setDescription(plugin.messages.cDisable);
		this.setPermission(Permissions.ADMIN.toString());
		this.setPermissionMessage(plugin.messages.AccesDenied);
	}

	/**
	 * Executes the given command, returning its success
	 *
	 * @param commandInfo Info about the command
	 *
	 * @return true if a valid command, otherwise false
	 */
	@Override
	public boolean onCommand(CommandInfo commandInfo)
	{
		// Get the player
		final Player player = commandInfo.getPlayer();

		// Player is mandatory
		if(player == null)  { ErrorMessage.toSender(commandInfo.getSender(), plugin.messages.ePlayer); return false; }

		// Disable Wss for the player
		WssApi.disable(player);

		// Message
		EmptyMessage.toSender(commandInfo.getPlayer());
		PluginTitleMessage titleMessage = new PluginTitleMessage(plugin, plugin.messages.chatPrefix + " " + plugin.messages.disable);
		titleMessage.send(commandInfo.getSenders());

		return true;
	}
}
