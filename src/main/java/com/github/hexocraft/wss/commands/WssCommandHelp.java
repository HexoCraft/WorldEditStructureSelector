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


import com.github.hexocraft.wss.WssPlugin;
import com.github.hexocraft.wss.configuration.Permissions;
import com.github.hexocraftapi.command.predifined.CommandHelp;
import org.apache.commons.lang.StringUtils;

public class WssCommandHelp extends CommandHelp<WssPlugin>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public WssCommandHelp(WssPlugin plugin)
	{
		super(plugin);
		this.setDescription(StringUtils.join(plugin.messages.cHelp,"\n"));
		this.setPermission(Permissions.ADMIN.toString());
		this.setPermissionMessage(plugin.messages.AccesDenied);
		this.setDisplayInlineDescription(true);
		this.removeArgument("page");
	}
}
