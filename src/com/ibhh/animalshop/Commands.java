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

	private Main plugin;
	public String[] commands = new String[] {"changelanguage", "languagelist", "resetlanguage"};

	public Commands(Main plugin)
	{
		this.plugin = plugin;
	}

	public String[] getCommands()
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
				if(args[0].equalsIgnoreCase(plugin.getConfigHandler().getLanguageString(player, "commands.changelanguage.name")) || args[0].equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "commands.changelanguage.name")))
				{
					return executeCommandChangeLanguage(player, args);
				}
			}
			else if(args.length == 1)
			{
				/* Language List Command /wr languagelist */

				if(args[0].equalsIgnoreCase(plugin.getConfigHandler().getLanguageString(player, "commands.languagelist.name")) || args[0].equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "commands.languagelist.name")))
				{
					return executeCommandLanguageList(player, args);
				}
				
				if(args[0].equalsIgnoreCase(plugin.getConfigHandler().getLanguageString(player, "commands.resetlanguage.name")) || args[0].equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "commands.resetlanguage.name")))
				{
					return executeCommandResetLanguage(player, args);
				}
			}
			plugin.getHelp().help(player, args);
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
		if(plugin.getPermissionsUtility().checkpermissions(player, plugin.getConfigHandler().getLanguageString(player, "commands.languagelist.permission", false)))
		{
			plugin.getLoggerUtility().log(player, ChatColor.GREEN + plugin.getConfigHandler().getLanguageString(player, "configuration.language.list"), LoggerLevel.INFO);
			plugin.getLoggerUtility().log(player, ChatColor.GREEN + plugin.getConfigHandler().getLanguageString(player, "commands.changelanguage.usage"), LoggerLevel.INFO);

			for(PluginLocale file : plugin.getConfigHandler().getLanguage_configs().values())
			{
				try
				{
					TellRaw.sendRawMessage(player, JSONManager.getJSONTellRaw(file.getLocaleName() + " (" + file.getCode() + ")", true, false, true, false, false, RawColor.green, ClickEvent.run_command, "/animalshop " + plugin.getConfigHandler().getLanguageString("system", "commands.changelanguage.name") + " " + file.getCode(), HoverEvent.show_text, plugin.getConfigHandler().getLanguageString(player, "configuration.language.listhover")));
					player.sendMessage("");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			try
			{
				TellRaw.sendRawMessage(player, JSONManager.getJSONTellRaw(plugin.getConfigHandler().getLanguageString(player, "commands.resetlanguage.return"), true, false, true, false, false, RawColor.green, ClickEvent.run_command, "/animalshop " + plugin.getConfigHandler().getLanguageString("system", "commands.resetlanguage.name"), HoverEvent.show_text, plugin.getConfigHandler().getLanguageString(player, "configuration.language.listhover")));
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
		if(plugin.getPermissionsUtility().checkpermissions(player, plugin.getConfigHandler().getLanguageString(player, "commands.resetlanguage.permission", false)))
		{
			try
			{
				plugin.getConfigHandler().setPlayerLanguage(player, "default");
				plugin.getLoggerUtility().log(player, plugin.getConfigHandler().getLanguageString(player, "commands.resetlanguage.return"), LoggerLevel.INFO);
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
		if(plugin.getPermissionsUtility().checkpermissions(player, plugin.getConfigHandler().getLanguageString(player, "commands.changelanguage.permission", false)))
		{
			try
			{
				plugin.getConfigHandler().setPlayerLanguage(player, args[1]);
			}
			catch(IllegalArgumentException e)
			{
				plugin.getLoggerUtility().log(player, e.getMessage(), LoggerLevel.ERROR);
				return true;
			}
			catch(IOException e)
			{
				e.printStackTrace();
				plugin.getLoggerUtility().log(player, e.getMessage(), LoggerLevel.ERROR);
				return true;
			}
			plugin.getLoggerUtility().log(player, plugin.getConfigHandler().getLanguageString(player, "commands.changelanguage.return"), LoggerLevel.INFO);
		}
		return true;
	}
}