package com.ibhh.animalshop;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public class Help
{

	private final Main plugin;

	public Help(Main pl)
	{
		this.plugin = pl;
	}

	public void help(Player player, String[] args)
	{
		this.plugin.getLoggerUtility().log("Help executed!", LoggerLevel.DEBUG);
		this.plugin.getLoggerUtility().log(player, ChatColor.GREEN + this.plugin.getConfigHandler().getLanguageString(player, "help.head"), LoggerLevel.INFO);
		if(args.length == 0)
		{
			this.plugin.getLoggerUtility().log(player, ChatColor.GREEN + this.plugin.getConfigHandler().getLanguageString(player, "help.commandsallowed"), LoggerLevel.INFO);
			for(String command : this.plugin.getCommandsUtility().getCommands())
			{
				if(this.plugin.getPermissionsUtility().checkpermissionssilent(player, this.plugin.getConfigHandler().getLanguageString(player, "commands." + command + ".permission")))
				{
					this.plugin.getLoggerUtility().log(player, this.plugin.getConfigHandler().getLanguageString(player, "commands." + command + ".usage"), LoggerLevel.INFO);
				}
			}
			this.plugin.getLoggerUtility().log(player, ChatColor.GRAY + "(C) ibhh", LoggerLevel.INFO);
		}
		else if(args.length > 0)
		{
			boolean found = false;
			for(String command : this.plugin.getCommandsUtility().getCommands())
			{
				if((!this.plugin.getConfigHandler().getLanguageString(player, "commands." + command + ".name").equalsIgnoreCase(args[0])) || (!this.plugin.getPermissionsUtility().checkpermissions(player, this.plugin.getConfigHandler().getLanguageString(player, "commands." + command + ".permission"))))
				{
					continue;
				}
				this.plugin.getLoggerUtility().log(player, "-----------", LoggerLevel.INFO);
				this.plugin.getLoggerUtility().log(player, this.plugin.getConfigHandler().getLanguageString(player, "commands." + command + ".usage"), LoggerLevel.INFO);
				this.plugin.getLoggerUtility().log(player, this.plugin.getConfigHandler().getLanguageString(player, "commands." + command + ".description"), LoggerLevel.INFO);
				found = true;
				this.plugin.getLoggerUtility().log(player, ChatColor.GRAY + "(C) ibhh", LoggerLevel.INFO);
				return;
			}

			if(!found)
			{
				this.plugin.getLoggerUtility().log(player, ChatColor.GREEN + this.plugin.getConfigHandler().getLanguageString(player, "help.commandsallowed"), LoggerLevel.INFO);

				for(String command : this.plugin.getCommandsUtility().getCommands())
				{
					if(this.plugin.getPermissionsUtility().checkpermissionssilent(player, this.plugin.getConfigHandler().getLanguageString(player, "commands." + command + ".permission")))
					{
						this.plugin.getLoggerUtility().log(player, "-----------", LoggerLevel.INFO);
						this.plugin.getLoggerUtility().log(player, this.plugin.getConfigHandler().getLanguageString(player, "commands." + command + ".usage"), LoggerLevel.INFO);
						this.plugin.getLoggerUtility().log(player, this.plugin.getConfigHandler().getLanguageString(player, "commands." + command + ".description"), LoggerLevel.INFO);
					}
				}
			}

			this.plugin.getLoggerUtility().log(player, ChatColor.GRAY + "(C) ibhh", LoggerLevel.INFO);
		}
	}
}
