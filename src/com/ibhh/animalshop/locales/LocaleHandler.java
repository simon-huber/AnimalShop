package com.ibhh.animalshop.locales;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import com.ibhh.animalshop.Main;

public class LocaleHandler extends HashMap<String, PluginLocale>
{
	private static final long serialVersionUID = 1L;
	private final Main plugin;
	private File player_language_file;
	private YamlConfiguration player_language;

	public LocaleHandler(Main plugin)
	{
		this.plugin = plugin;
		/**
		 * Alle vordefinierten Sprachen
		 */
		put("de_DE", new Locale_de_DE(this.plugin.getDataFolder().getPath()));
		put("de_BY", new Locale_de_BY(this.plugin.getDataFolder().getPath()));
		put("lb_LU", new Locale_lb_LU(this.plugin.getDataFolder().getPath()));
		put("en_CA", new Locale_en_CA(this.plugin.getDataFolder().getPath()));
		put("en_PT", new Locale_en_PT(this.plugin.getDataFolder().getPath()));
		
		/**
		 * Liste der Dateien wird sortiert
		 */
		File[] eintraege = plugin.getDataFolder().listFiles(new FileFilter()
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
			File config_o = new File(plugin.getDataFolder() + File.separator + eintraege[j].getName());
			PluginLocale language_config1 = PluginLocale.loadConfiguration(config_o);
			put(language_config1.getCode(), language_config1);
		}
		createPlayerLanguageConfig();
	}
	
	public void savePlayer_language() throws IOException
	{
		player_language.save(player_language_file);
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
		File[] eintraege = plugin.getDataFolder().listFiles(new FileFilter()
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
		File folderp = new File(plugin.getDataFolder() + File.separator);
		folderp.mkdirs();
		player_language_file = new File(plugin.getDataFolder() + File.separator + "player_language.yml");
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
	}
}
