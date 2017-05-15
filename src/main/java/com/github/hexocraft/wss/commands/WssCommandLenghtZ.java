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

public class WssCommandLenghtZ extends Command<WssPlugin>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public WssCommandLenghtZ(WssPlugin plugin)
	{
		super("maxZ", plugin);
		this.setAliases(Lists.newArrayList("z"));
		this.setPermission(Permissions.ADMIN.toString());
		this.setPermissionMessage(plugin.messages.AccesDenied);

		this.addArgument(new CommandArgument<Integer>("maxZ", ArgTypeInteger.get(), 32, false,false, plugin.messages.cMaxZ));
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

		// Get maxZ from command line
		int maxZ = Integer.parseInt(commandInfo.getNamedArg("maxZ"));
		if(maxZ<=0)
		{
			WarningPrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.eMaxZ);
			return false;
		}

		// Define the maxZ for the player
		WssApi.getPlayer(player).maxZ = maxZ;

		// Message
		EmptyMessage.toSender(commandInfo.getPlayer());
		PluginTitleMessage titleMessage = new PluginTitleMessage(plugin, plugin.messages.maxZ + " : " + maxZ);
		titleMessage.send(commandInfo.getSenders());

		return true;
	}
}
