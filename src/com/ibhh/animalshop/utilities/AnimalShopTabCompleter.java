package com.ibhh.animalshop.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.Main;

public class AnimalShopTabCompleter implements TabCompleter
{

	private final Main plugin;

	public AnimalShopTabCompleter(Main main)
	{
		plugin = main;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3)
	{
		List<String> list = new ArrayList<String>();
		if(arg0 instanceof Player)
		{
			Player player = (Player) arg0;
			if(arg1.getName().equalsIgnoreCase("animalshop") && arg3.length >= 1)
			{
				ConfigurationSection configSection = plugin.getConfigHandler().getLanguage_config(plugin.getConfigHandler().getPlayerLanguage(player)).getConfigurationSection("commands");
				if(configSection != null)
				{
					for(String key : configSection.getKeys(false))
					{
						if(configSection.getString(key + ".name").startsWith(arg3[0]) && plugin.getPermissionsUtility().checkpermissionssilent(player, configSection.getString(key + ".permission")))
						{
							list.add(configSection.getString(key + ".name"));
						}
					}
				}
				ConfigurationSection configSectiondefault = plugin.getConfigHandler().getLanguage_config(plugin.getConfigHandler().getConfig().getString("language")).getConfigurationSection("commands");
				if(configSectiondefault != null)
				{
					for(String key : configSectiondefault.getKeys(false))
					{
						if(configSectiondefault.getString(key + ".name").startsWith(arg3[0]) && plugin.getPermissionsUtility().checkpermissionssilent(player, configSectiondefault.getString(key + ".permission")))
						{
							list.add(configSectiondefault.getString(key + ".name"));
						}
					}
				}
			}
		}
		return list;
	}

}
