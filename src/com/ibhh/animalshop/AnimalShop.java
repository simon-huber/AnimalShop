/*
 * Copyright by Miny & Ibhh (http://desch.ch, http://ibhh.de)
 * Removing this will result in a Copyright Punishment!
 */

package com.ibhh.animalshop;

import java.io.File;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.ibhh.animalshop.exception.SignNotValidException;
import com.ibhh.animalshop.update.Updater;
import com.ibhh.animalshop.update.Updater.UpdateResult;
import com.ibhh.animalshop.utilities.AnimalShopTabCompleter;
import com.ibhh.animalshop.utilities.MoneyHandler;
import com.ibhh.animalshop.utilities.PermissionsUtility;
import com.ibhh.animalshop.utilities.ShopPlayerListener;
import com.ibhh.animalshop.utilities.animal.AnimalSpawnHandler;
import com.ibhh.animalshop.utilities.config.ConfigurationHandler;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;
import com.ibhh.animalshop.utilities.logger.LoggerUtility;
import com.ibhh.animalshop.utilities.metrics.MetricsHandler;

public class AnimalShop extends JavaPlugin
{
	private static ConfigurationHandler confighandler;
	private static PermissionsUtility permissionUtility;
	private static MoneyHandler moneyHandler;
	private static LoggerUtility loggerUtility;
	private static Commands commands;
	private static ShopPlayerListener listener;
	private static MetricsHandler metricshandler;
	private static AnimalSpawnHandler animalSpawnHandler;
	private static File datafolder;
	private static boolean loaded = false;

	public static AnimalSpawnHandler getAnimalSpawnHandler()
	{
		return animalSpawnHandler;
	}

	public static MetricsHandler getMetricshandler()
	{
		return metricshandler;
	}

	public static File getPluginDataFolder()
	{
		return datafolder;
	}

	public static ConfigurationHandler getConfigHandler()
	{
		return confighandler;
	}

	public static PermissionsUtility getPermissionsUtility()
	{
		return permissionUtility;
	}

	public static MoneyHandler getMoneyHandler()
	{
		return moneyHandler;
	}

	public static LoggerUtility getLoggerUtility()
	{
		return loggerUtility;
	}

	public static Commands getCommandsUtility()
	{
		return commands;
	}

	@Override
	public void onDisable()
	{
		metricshandler.saveStatsFiles();
	}

	@Override
	public void onEnable()
	{
		datafolder = getDataFolder();
		loggerUtility = new LoggerUtility();
		confighandler = new ConfigurationHandler();
		confighandler.init(this);
		permissionUtility = new PermissionsUtility();
		moneyHandler = new MoneyHandler(this);
		animalSpawnHandler = new AnimalSpawnHandler();
		metricshandler = new MetricsHandler(this);
		metricshandler.loadStatsFiles();
		this.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable()
		{

			@Override
			public void run()
			{
				metricshandler.saveStatsFiles();
			}
		}, 200L, 50000L);

		if(confighandler.getConfig().getBoolean("checkforupdate") && confighandler.getConfig().getBoolean("autodownloadandinstall"))
		{
			Updater updater = new Updater(this, 35470, this.getFile(), Updater.UpdateType.DEFAULT, true);
			if(updater.getResult() == UpdateResult.UPDATE_AVAILABLE)
			{
				this.getLogger().info("New version available! " + updater.getLatestName());
			}
		}
		else if(confighandler.getConfig().getBoolean("checkforupdate") && !confighandler.getConfig().getBoolean("autodownloadandinstall"))
		{
			Updater updater = new Updater(this, 35470, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
			if(updater.getResult() == UpdateResult.UPDATE_AVAILABLE)
			{
				this.getLogger().info("New version available! " + updater.getLatestName());
			}
		}

		/* Commandexecutor (Command in einer andere Klasse ausgelagert!) */
		commands = new Commands();
		this.getCommand("animalshop").setExecutor(commands);
		/* Eventregistrierung */
		listener = new ShopPlayerListener();
		getServer().getPluginManager().registerEvents(listener, this);
		getCommand("animalshop").setTabCompleter(new AnimalShopTabCompleter());
		getLoggerUtility().log("AnimalShop loaded", LoggerLevel.INFO);
		loaded = true;
	}

	public static boolean blockIsValid(Sign s, Player p)
	{
		return blockIsValid(s.getLines(), p);
	}

	public static boolean blockIsValid(String[] line, Player p)
	{
		try
		{
			animalSpawnHandler.signIsValid(line);
			return true;
		}
		catch(SignNotValidException se)
		{
			getLoggerUtility().log(p, se.getMessage(), LoggerLevel.ERROR);
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			getLoggerUtility().log(p, e.getMessage(), LoggerLevel.ERROR);
			return false;
		}
	}

	/**
	 * @return the loaded
	 */
	public static boolean isLoaded()
	{
		return loaded;
	}
}
