package com.ibhh.animalshop.locales;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public class LocaleHandler extends HashMap<String, PluginLocale>
{
	private static final long serialVersionUID = 1L;
	private File player_language_file;
	private YamlConfiguration player_language;

	public LocaleHandler()
	{
		AnimalShop.getLoggerUtility().log("Loading language files ...", LoggerLevel.DEBUG);

		/**
		 * Alle vordefinierten Sprachen
		 */
		put("de_DE", new Locale_de_DE(AnimalShop.getPluginDataFolder().getPath()));
		put("de_BY", new Locale_de_BY(AnimalShop.getPluginDataFolder().getPath()));
		put("lb_LU", new Locale_lb_LU(AnimalShop.getPluginDataFolder().getPath()));
		put("en_CA", new Locale_en_CA(AnimalShop.getPluginDataFolder().getPath()));
		put("en_PT", new Locale_en_PT(AnimalShop.getPluginDataFolder().getPath()));
		
		/**
		 * Liste der Dateien wird sortiert
		 */
		File[] eintraege = AnimalShop.getPluginDataFolder().listFiles(new FileFilter()
		{

			@Override
			public boolean accept(File pathname)
			{
				/**
				 * Filtert alle Dateien mit der Endung "properties"
				 */
				if(pathname.getName().startsWith("language.") && !pathname.getName().contains("language.de_DE") && !pathname.getName().contains("language.de_BY") && !pathname.getName().contains("language.en_CA") && !pathname.getName().contains("language.en_PT") && !pathname.getName().contains("language.lb_LU"))
				{
					return true;
				}
				return false;
			}
		});
		for(int j = 0; j < eintraege.length; j++)
		{
			File config_o = new File(AnimalShop.getPluginDataFolder() + File.separator + eintraege[j].getName());
			PluginLocale language_config1 = PluginLocale.loadConfiguration(config_o);
			put(language_config1.getCode(), language_config1);
		}
		AnimalShop.getLoggerUtility().log("Language files loaded", LoggerLevel.DEBUG);
		createPlayerLanguageConfig();

	}
	
	public void savePlayer_language() throws IOException
	{
		AnimalShop.getLoggerUtility().log("Saving player language file ...", LoggerLevel.DEBUG);
		player_language.save(player_language_file);
		AnimalShop.getLoggerUtility().log("Saved", LoggerLevel.DEBUG);
	}
	
	public YamlConfiguration getPlayer_language()
	{
		return player_language;
	}

	public String[] getLanguages()
	{
		/**
		 * Liste der Dateien wird sortiert
		 */
		File[] eintraege = AnimalShop.getPluginDataFolder().listFiles(new FileFilter()
		{

			@Override
			public boolean accept(File pathname)
			{
				/**
				 * Filtert alle Dateien mit der Endung "properties"
				 */
				if(pathname.getName().startsWith("language."))
				{
					return true;
				}
				return false;
			}
		});
		String[] names = new String[eintraege.length];
		for(int i = 0; i < eintraege.length; i++)
		{
			names[i] = eintraege[i].getName().split("\\.")[1];
		}
		return names;
	}

	private void createPlayerLanguageConfig()
	{
		AnimalShop.getLoggerUtility().log("Creating file with player languages .. ", LoggerLevel.DEBUG);
		File folderp = new File(AnimalShop.getPluginDataFolder() + File.separator);
		folderp.mkdirs();
		player_language_file = new File(AnimalShop.getPluginDataFolder() + File.separator + "player_language.yml");
		if(!player_language_file.exists())
		{
			try
			{
				player_language_file.createNewFile();
			}
			catch(IOException ex)
			{
				System.err.println("Couldnt create new arenaconfig file!");
			}
		}
		player_language = YamlConfiguration.loadConfiguration(player_language_file);
		try
		{
			player_language.options().copyDefaults(true);
			player_language.save(player_language_file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		AnimalShop.getLoggerUtility().log("Created", LoggerLevel.DEBUG);
	}
}
