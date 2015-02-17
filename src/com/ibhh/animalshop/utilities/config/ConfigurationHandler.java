package com.ibhh.animalshop.utilities.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.locales.LocaleHandler;
import com.ibhh.animalshop.locales.Localizer;
import com.ibhh.animalshop.locales.PluginLocale;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

/**
 * @author ibhh
 */
public class ConfigurationHandler
{
	private LocaleHandler language_configs;
	private FileConfiguration pluginconfig;

	public void init(AnimalShop plugin)
	{
		// loading main arenaconfig
		try
		{
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();
			plugin.reloadConfig();
			pluginconfig = plugin.getConfig();
		}
		catch(Exception e)
		{
			System.out.println("Cannot create config!");
			e.printStackTrace();
		}
		AnimalShop.getLoggerUtility().loadConfigs();
		language_configs = new LocaleHandler();
	}

	public LocaleHandler getLanguage_configs()
	{
		return language_configs;
	}

	/**
	 * Returns the current language configuration
	 * @return YamlConfiguration
	 */
	public PluginLocale getLanguage_config(String type)
	{
		return language_configs.get(type);
	}

	public String getLanguageString(Player player, String path, boolean notfoundmessage)
	{
		return getLanguageString(getPlayerLanguage(player), path, notfoundmessage);
	}

	public String getLanguageString(Player player, String path)
	{
		return getLanguageString(getPlayerLanguage(player), path);
	}

	/**
	 * @param type z.B. system, de, en, fr
	 * @param path
	 * @return String
	 */
	public String getLanguageString(String type, String path)
	{
		return getLanguageString(type, path, true);
	}

	/**
	 * @param type z.B. system, de, en, fr
	 * @param path
	 * @return String
	 */
	public String getLanguageString(String type, String path, boolean notfoundmessage)
	{
		String ret = "";
		if(type == null || type.equals("system"))
		{
			type = getConfig().getString("language");
		}
		PluginLocale config = getLanguage_config(type);
		if(config != null)
		{
			if(config.getString(path) == null)
			{
				ret += ChatColor.RED + "ERROR: ";
				AnimalShop.getLoggerUtility().log("arenaconfig.getLanguageString(type, path) == null because path " + path + " type: " + type, LoggerLevel.DEBUG);
				PluginLocale config_sys = getLanguage_config("system");
				if(config_sys != null && config.getString(path) != null)
				{
					ret += getLanguageString("system", path);
					ret += String.format(getLanguageString("system", "configuration.language.pathnotfound", false), type, path);
				}
				else
				{
					ret += "Nothing FOUND. Contact developer";
				}
			}
			else
			{
				return config.getString(path);
			}
		}
		return ret;
	}

	/**
	 * @return plugin.getConfig();
	 */
	public FileConfiguration getConfig()
	{
		return pluginconfig;
	}

	public void setPlayerLanguage(Player p, String language) throws IOException
	{
		if(language_configs.get(language) != null || language.equals("default"))
		{
			language_configs.getPlayer_language().set(p.getUniqueId().toString(), language);
			language_configs.savePlayer_language();
		}
		else if(language_configs.get(Localizer.getCodebyLanguage(language)) != null && language_configs.get(Localizer.getCodebyLanguage(language)) != null)
		{
			language_configs.getPlayer_language().set(p.getUniqueId().toString(), Localizer.getCodebyLanguage(language));
			language_configs.savePlayer_language();
		}
		else
		{
			throw new IllegalArgumentException(String.format(getLanguageString(p, "configuration.language.languagenotfound"), language));
		}
		AnimalShop.getLoggerUtility().log("The language of " + p.getName() + "(" + p.getUniqueId() + ") was set to " + language, LoggerLevel.DEBUG);

	}

	public String getPlayerLanguage(Player p)
	{
		if(language_configs.getPlayer_language().contains(p.getUniqueId().toString()) && !language_configs.getPlayer_language().getString(p.getUniqueId().toString()).equals("default"))
		{
//			plugin.getLoggerUtility().log("Player " + p.getName() + " has manually defined locale: " + language_configs.getPlayer_language().getString(p.getUniqueId().toString()), Level.FINE);
			return language_configs.getPlayer_language().getString(p.getUniqueId().toString());
		}
		Localizer locale = Localizer.getLocalizer(p);
		if(language_configs.containsKey(locale.getCode()))
		{
//			plugin.getLoggerUtility().log("Player " + p.getName() + " has locale: " + locale.getCode(), Level.FINE);
			return locale.getCode();
		}
		else
		{
			try
			{
				AnimalShop.getLoggerUtility().log("Player " + p.getName() + " has locale: en_CA because LOCALE not found: " + locale.getCode() + " Read: " + Localizer.getLanguage(p), LoggerLevel.DEBUG);
			}
			catch(NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
			return "en_CA";
		}
	}

}