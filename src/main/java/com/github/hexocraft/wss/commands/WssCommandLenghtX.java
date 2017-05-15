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

public class WssCommandLenghtX extends Command<WssPlugin>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public WssCommandLenghtX(WssPlugin plugin)
	{
		super("maxX", plugin);
		this.setAliases(Lists.newArrayList("x"));
		this.setPermission(Permissions.ADMIN.toString());
		this.setPermissionMessage(plugin.messages.AccesDenied);

		this.addArgument(new CommandArgument<Integer>("maxX", ArgTypeInteger.get(), 32, false,false, plugin.messages.cMaxX));
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

		// Get maxX from command line
		int maxX = Integer.parseInt(commandInfo.getNamedArg("maxX"));
		if(maxX<=0)
		{
			WarningPrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.eMaxX);
			return false;
		}

		// Define the maxX for the player
		WssApi.getPlayer(player).maxX = maxX;

		// Message
		EmptyMessage.toSender(commandInfo.getPlayer());
		PluginTitleMessage titleMessage = new PluginTitleMessage(plugin, plugin.messages.maxX + " : " + maxX);
		titleMessage.send(commandInfo.getSenders());

		return true;
	}
}
