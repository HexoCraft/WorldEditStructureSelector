package com.github.hexocraft.wss.commands;

import com.github.hexocraft.wss.WssApi;
import com.github.hexocraft.wss.WssPlugin;
import com.github.hexocraft.wss.configuration.Permissions;
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandArgument;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.command.type.ArgTypeMaterialList;
import com.github.hexocraftapi.message.predifined.message.ErrorMessage;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.List;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class WssCommandExclude extends Command<WssPlugin>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public WssCommandExclude(WssPlugin plugin)
	{
		super("exclude", plugin);
		this.setAliases(Lists.newArrayList("e"));
		this.setPermission(Permissions.ADMIN.toString());
		this.setPermissionMessage(plugin.messages.AccesDenied);

		this.addArgument(new CommandArgument<List<MaterialData>>("exclude", ArgTypeMaterialList.get(), false, false, plugin.messages.cExclude));
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

		//
		String materialStringList = commandInfo.getNamedArg("exclude");
		if(materialStringList==null) {
			WssApi.getPlayer(player).excluded = null;
			return true;
		}

		// Get material from command line
		List<MaterialData> materialList = ArgTypeMaterialList.get().get(commandInfo.getNamedArg("exclude"));

		// Define the material for the player
		WssApi.getPlayer(player).excluded = materialList;

		return true;
	}
}
