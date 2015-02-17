package com.ibhh.animalshop;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public abstract class Help
{
	public static void help(Player player, String[] args)
	{
		AnimalShop.getLoggerUtility().log("Help executed!", LoggerLevel.DEBUG);
		AnimalShop.getLoggerUtility().log(player, ChatColor.GREEN + AnimalShop.getConfigHandler().getLanguageString(player, "help.head"), LoggerLevel.INFO);
		if(args.length == 0)
		{
			AnimalShop.getLoggerUtility().log(player, ChatColor.GREEN + AnimalShop.getConfigHandler().getLanguageString(player, "help.commandsallowed"), LoggerLevel.INFO);
			for(String command : Commands.getCommands())
			{
				if(AnimalShop.getPermissionsUtility().checkpermissionssilent(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands." + command + ".permission")))
				{
					AnimalShop.getLoggerUtility().log(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands." + command + ".usage"), LoggerLevel.INFO);
				}
			}
			AnimalShop.getLoggerUtility().log(player, ChatColor.GRAY + "(C) ibhh", LoggerLevel.INFO);
		}
		else if(args.length > 0)
		{
			boolean found = false;
			for(String command : Commands.getCommands())
			{
				if((!AnimalShop.getConfigHandler().getLanguageString(player, "commands." + command + ".name").equalsIgnoreCase(args[0])) || (!AnimalShop.getPermissionsUtility().checkpermissions(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands." + command + ".permission"))))
				{
					continue;
				}
				AnimalShop.getLoggerUtility().log(player, "-----------", LoggerLevel.INFO);
				AnimalShop.getLoggerUtility().log(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands." + command + ".usage"), LoggerLevel.INFO);
				AnimalShop.getLoggerUtility().log(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands." + command + ".description"), LoggerLevel.INFO);
				found = true;
				AnimalShop.getLoggerUtility().log(player, ChatColor.GRAY + "(C) ibhh", LoggerLevel.INFO);
				return;
			}

			if(!found)
			{
				AnimalShop.getLoggerUtility().log(player, ChatColor.GREEN + AnimalShop.getConfigHandler().getLanguageString(player, "help.commandsallowed"), LoggerLevel.INFO);
				for(String command : Commands.getCommands())
				{
					if(AnimalShop.getPermissionsUtility().checkpermissionssilent(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands." + command + ".permission")))
					{
						AnimalShop.getLoggerUtility().log(player, "-----------", LoggerLevel.INFO);
						AnimalShop.getLoggerUtility().log(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands." + command + ".usage"), LoggerLevel.INFO);
						AnimalShop.getLoggerUtility().log(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands." + command + ".description"), LoggerLevel.INFO);
					}
				}
			}

			AnimalShop.getLoggerUtility().log(player, ChatColor.GRAY + "(C) ibhh", LoggerLevel.INFO);
		}
	}
}
