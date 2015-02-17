/*
 * Copyright by Miny & Ibhh (http://desch.ch, http://ibhh.de)
 * Removing this will result in a Copyright Punishment!
 */

package com.ibhh.animalshop;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.locales.PluginLocale;
import com.ibhh.animalshop.raw.JSONManager;
import com.ibhh.animalshop.raw.RawColor;
import com.ibhh.animalshop.raw.chat.ClickEvent;
import com.ibhh.animalshop.raw.chat.HoverEvent;
import com.ibhh.animalshop.raw.chat.TellRaw;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public class Commands implements CommandExecutor
{
	public static final String[] commands = new String[] {"changelanguage", "languagelist", "resetlanguage"};

	public Commands()
	{
	}

	public static String[] getCommands()
	{
		return commands;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player player = (Player) cs;

			if(args.length == 2)
			{
				if(args[0].equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString(player, "commands.changelanguage.name")) || args[0].equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "commands.changelanguage.name")))
				{
					return executeCommandChangeLanguage(player, args);
				}
			}
			else if(args.length == 1)
			{
				/* Language List Command /wr languagelist */

				if(args[0].equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString(player, "commands.languagelist.name")) || args[0].equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "commands.languagelist.name")))
				{
					return executeCommandLanguageList(player, args);
				}
				
				if(args[0].equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString(player, "commands.resetlanguage.name")) || args[0].equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "commands.resetlanguage.name")))
				{
					return executeCommandResetLanguage(player, args);
				}
			}
			Help.help(player, args);
			return true;
		}
		else
		{
			System.out.println(ChatColor.GOLD + "[AnimalShop] " + ChatColor.DARK_RED + "Befehle koennen nicht als Konsole ausgefuehrt werden!");
			return true;
		}
	}

	private boolean executeCommandLanguageList(Player player, String... args)
	{
		if(AnimalShop.getPermissionsUtility().checkpermissions(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands.languagelist.permission", false)))
		{
			AnimalShop.getLoggerUtility().log(player, ChatColor.GREEN + AnimalShop.getConfigHandler().getLanguageString(player, "configuration.language.list"), LoggerLevel.INFO);
			AnimalShop.getLoggerUtility().log(player, ChatColor.GREEN + AnimalShop.getConfigHandler().getLanguageString(player, "commands.changelanguage.usage"), LoggerLevel.INFO);

			for(PluginLocale file : AnimalShop.getConfigHandler().getLanguage_configs().values())
			{
				try
				{
					TellRaw.sendRawMessage(player, JSONManager.getJSONTellRaw(file.getLocaleName() + " (" + file.getCode() + ")", true, false, true, false, false, RawColor.green, ClickEvent.run_command, "/animalshop " + AnimalShop.getConfigHandler().getLanguageString("system", "commands.changelanguage.name") + " " + file.getCode(), HoverEvent.show_text, AnimalShop.getConfigHandler().getLanguageString(player, "configuration.language.listhover")));
					player.sendMessage("");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			try
			{
				TellRaw.sendRawMessage(player, JSONManager.getJSONTellRaw(AnimalShop.getConfigHandler().getLanguageString(player, "commands.resetlanguage.return"), true, false, true, false, false, RawColor.green, ClickEvent.run_command, "/animalshop " + AnimalShop.getConfigHandler().getLanguageString("system", "commands.resetlanguage.name"), HoverEvent.show_text, AnimalShop.getConfigHandler().getLanguageString(player, "configuration.language.listhover")));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
		return true;
	}
	
	private boolean executeCommandResetLanguage(Player player, String... args)
	{
		if(AnimalShop.getPermissionsUtility().checkpermissions(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands.resetlanguage.permission", false)))
		{
			try
			{
				AnimalShop.getConfigHandler().setPlayerLanguage(player, "default");
				AnimalShop.getLoggerUtility().log(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands.resetlanguage.return"), LoggerLevel.INFO);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * Change the language of a player
	 * @param player
	 * @param args
	 * @return
	 */
	private boolean executeCommandChangeLanguage(Player player, String... args)
	{
		if(AnimalShop.getPermissionsUtility().checkpermissions(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands.changelanguage.permission", false)))
		{
			try
			{
				AnimalShop.getConfigHandler().setPlayerLanguage(player, args[1]);
			}
			catch(IllegalArgumentException e)
			{
				AnimalShop.getLoggerUtility().log(player, e.getMessage(), LoggerLevel.ERROR);
				return true;
			}
			catch(IOException e)
			{
				e.printStackTrace();
				AnimalShop.getLoggerUtility().log(player, e.getMessage(), LoggerLevel.ERROR);
				return true;
			}
			AnimalShop.getLoggerUtility().log(player, AnimalShop.getConfigHandler().getLanguageString(player, "commands.changelanguage.return"), LoggerLevel.INFO);
		}
		return true;
	}
}