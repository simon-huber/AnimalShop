/**
 * 
 */
package com.ibhh.animalshop.utilities.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author ibhh, Miny
 */
public class CustomYamlConfiguration extends YamlConfiguration
{

	/**
	 * Creates a new {@link YamlConfiguration}, loading from the given file.
	 * <p>
	 * Any errors loading the Configuration will be logged and then ignored. If the specified input is not a valid arenaconfig, a
	 * blank arenaconfig will be returned.
	 * <p>
	 * The encoding used may follow the system dependent default.
	 * @param file Input file
	 * @return Resulting configuration
	 * @throws IllegalArgumentException Thrown if file is null
	 */
	public static CustomYamlConfiguration loadConfiguration(File file)
	{
		Validate.notNull(file, "File cannot be null");

		CustomYamlConfiguration config = new CustomYamlConfiguration();

		try
		{
			config.load(file);
		}
		catch(FileNotFoundException ex)
		{
		}
		catch(IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
		}
		catch(InvalidConfigurationException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
		}

		return config;
	}
}
