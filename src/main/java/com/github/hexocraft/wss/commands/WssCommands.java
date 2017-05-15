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
import com.github.hexocraftapi.message.Prefix;
import com.github.hexocraftapi.message.predifined.message.EmptyMessage;
import com.github.hexocraftapi.message.predifined.message.PluginTitleMessage;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static com.github.hexocraft.wss.WssPlugin.instance;

public class WssCommands extends Command<WssPlugin>
{
	public static Prefix prefix = new Prefix(instance.messages.chatPrefix);

	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public WssCommands(WssPlugin plugin)
	{
		super("WorldEditStructureSelector", plugin);
		this.setAliases(Lists.newArrayList("wss"));
		this.setPermission(Permissions.ADMIN.toString());

		this.addSubCommand(new WssCommandHelp(plugin));
		this.addSubCommand(new WssCommandEnable(plugin));
		this.addSubCommand(new WssCommandDisable(plugin));
		this.addSubCommand(new WssCommandLenghtX(plugin));
		this.addSubCommand(new WssCommandLenghtY(plugin));
		this.addSubCommand(new WssCommandLenghtZ(plugin));
		this.addSubCommand(new WssCommandExclude(plugin));
		this.addSubCommand(new WssCommandReload(plugin));
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

		// Send info message
		EmptyMessage.toSender(player);
		PluginTitleMessage titleMessage = new PluginTitleMessage(plugin, instance.messages.chatPrefix + " " + ChatColor.AQUA + (WssApi.isEnable(player) ? "on" : "off"));
		titleMessage.add(instance.messages.maxX + " : " + ChatColor.AQUA + WssApi.getMaxX(player));
		titleMessage.add(instance.messages.maxY + " : " + ChatColor.AQUA + WssApi.getMaxY(player));
		titleMessage.add(instance.messages.maxZ + " : " + ChatColor.AQUA + WssApi.getMaxZ(player));
		titleMessage.send(commandInfo.getSenders());

		return true;
	}
}
