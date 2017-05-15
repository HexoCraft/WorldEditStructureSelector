package com.github.hexocraft.wss.commands;

import com.github.hexocraft.wss.WssApi;
import com.github.hexocraft.wss.WssPlugin;
import com.github.hexocraft.wss.configuration.Permissions;
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandArgument;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.command.type.ArgTypeInteger;
import com.github.hexocraftapi.message.predifined.message.EmptyMessage;
import com.github.hexocraftapi.message.predifined.message.ErrorMessage;
import com.github.hexocraftapi.message.predifined.message.PluginTitleMessage;
import com.github.hexocraftapi.message.predifined.message.WarningPrefixedMessage;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

import static com.github.hexocraft.wss.commands.WssCommands.prefix;

public class WssCommandLenghtY extends Command<WssPlugin>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public WssCommandLenghtY(WssPlugin plugin)
	{
		super("maxY", plugin);
		this.setAliases(Lists.newArrayList("y"));
		this.setPermission(Permissions.ADMIN.toString());
		this.setPermissionMessage(plugin.messages.AccesDenied);

		this.addArgument(new CommandArgument<Integer>("maxY", ArgTypeInteger.get(), 32, false,false, plugin.messages.cMaxY));
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

		// Get maxY from command line
		int maxY = Integer.parseInt(commandInfo.getNamedArg("maxY"));
		if(maxY<=0)
		{
			WarningPrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.eMaxY);
			return false;
		}

		// Define the maxY for the player
		WssApi.getPlayer(player).maxY = maxY;

		// Message
		EmptyMessage.toSender(commandInfo.getPlayer());
		PluginTitleMessage titleMessage = new PluginTitleMessage(plugin, plugin.messages.maxY + " : " + maxY);
		titleMessage.send(commandInfo.getSenders());

		return true;
	}
}
