package com.github.hexocraft.wss.configuration;

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

import com.github.hexocraftapi.configuration.Configuration;
import com.github.hexocraftapi.configuration.annotation.ConfigFooter;
import com.github.hexocraftapi.configuration.annotation.ConfigHeader;
import com.github.hexocraftapi.configuration.annotation.ConfigPath;
import com.github.hexocraftapi.configuration.annotation.ConfigValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

@ConfigHeader(comment = {
"# ===--- WorldEdit Structure Selector ----------------------------------------------------------------------------=== #",
"#                                                                                                                      ",
"#     Auto select any structures as a WorldEdit selection                                                              ",
"#                                                                                                                      ",
"# ===------------------------------------------------------------------------------------------ © 2017 Hexosse ---=== #"
})
@ConfigFooter(comment = {
" ",
"# ===--- Enjoy -------------------------------------------------------------------------------- © 2017 Hexosse ---=== #"
})
public class Messages extends Configuration
{
	/* Chat */
	@ConfigPath(path = "chat")
	@ConfigValue(path = "chat.prefix")                          public String chatPrefix = "&3[&bWss&3]&r";

	/* Commands */
	@ConfigPath(path = "commands", 		comment = "List of Messages used in commands")
	@ConfigValue(path = "commands.help.cmd")					public List<String>   cHelp    = Arrays.asList("Display Wss help");
	@ConfigValue(path = "commands.reload.cmd")					public List<String> cReload  = Arrays.asList("Reload Wss");
	@ConfigValue(path = "commands.eanble.cmd")                  public String         cEnable  = "Enable Wss!";
	@ConfigValue(path = "commands.disable.cmd")                 public String         cDisable = "Disable Wss!";
	@ConfigValue(path = "commands.maxx.cmd")             		public String         cMaxX    = "Define maximum maxX selection";
	@ConfigValue(path = "commands.maxy.cmd")             		public String         cMaxY   = "Define maximum maxY selection";
	@ConfigValue(path = "commands.maxz.cmd")             		public String         cMaxZ   = "Define maximum maxZ selection";
	@ConfigValue(path = "commands.exclude.cmd")             	public String         cExclude   = "List of material to exclude";

	/* Success */
	@ConfigPath(path = "success", 		comment = "List of Messages used after a sucess command")
	@ConfigValue(path = "success.reload")		                public String sReload = "Wss has been reloaded";

	/* Errors */
	@ConfigPath(path = "errors", 		comment = "List of error messages")
	@ConfigValue(path = "errors.AccesDenied")                   public String AccesDenied = "You don't have permission to do that!";
	@ConfigValue(path = "errors.player")                        public String ePlayer     = "This command can only be executed by a player";
	@ConfigValue(path = "errors.maxX")                        	public String   eMaxX     = "X length must be positive!";
	@ConfigValue(path = "errors.maxY")             	        	public String   eMaxY     = "Y length must be positive!";
	@ConfigValue(path = "errors.maxZ")             	        	public String   eMaxZ     = "Z length must be positive!";

	/* Messages */
	@ConfigPath(path = "messages")
	@ConfigValue(path = "messages.enable")                      public String enable    = "is enable!";
	@ConfigValue(path = "messages.disable")                     public String disable   = "is disable!";
	@ConfigValue(path = "messages.leftClick")                   public String leftClick = "left click an item with a wooden axe to select a structure!";
	@ConfigValue(path = "messages.maxX")             			public String maxX      = "Maximum length on X axis";
	@ConfigValue(path = "messages.maxY")             			public String maxY      = "Maximum length on Y axis";
	@ConfigValue(path = "messages.maxZ")             			public String maxZ      = "Maximum length on Z axis";
	@ConfigValue(path = "messages.exclude")             		public String exclude   = "Excluded materials";


	public Messages(JavaPlugin plugin, String fileName, boolean load)
	{
		super(plugin, fileName);

		if(load) load();
	}
}
