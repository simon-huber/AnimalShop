/*
 * Copyright by Miny & Ibhh (http://desch.ch, http://ibhh.de)
 * Removing this will result in a Copyright Punishment!
 */

package com.ibhh.animalshop;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.ibhh.animalshop.exception.SignNotValidException;
import com.ibhh.animalshop.update.Updater;
import com.ibhh.animalshop.update.Updater.UpdateResult;
import com.ibhh.animalshop.utilities.MoneyHandler;
import com.ibhh.animalshop.utilities.PermissionsUtility;
import com.ibhh.animalshop.utilities.ShopPlayerListener;
import com.ibhh.animalshop.utilities.animal.AnimalSpawnHandler;
import com.ibhh.animalshop.utilities.config.ConfigurationHandler;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;
import com.ibhh.animalshop.utilities.logger.LoggerUtility;
import com.ibhh.animalshop.utilities.metrics.MetricsHandler;

public class Main extends JavaPlugin
{
	private ConfigurationHandler confighandler;
	private PermissionsUtility permissionUtility;
	private MoneyHandler moneyHandler;
	private LoggerUtility loggerUtility;
	private Commands commands;
	private Help help;
	@SuppressWarnings("unused")
	private ShopPlayerListener listener;
	private MetricsHandler metricshandler;
	private AnimalSpawnHandler animalSpawnHandler;

	public AnimalSpawnHandler getAnimalSpawnHandler()
	{
		return animalSpawnHandler;
	}

	public MetricsHandler getMetricshandler()
	{
		return metricshandler;
	}

	public ConfigurationHandler getConfigHandler()
	{
		return confighandler;
	}

	public PermissionsUtility getPermissionsUtility()
	{
		return permissionUtility;
	}

	public MoneyHandler getMoneyHandler()
	{
		return moneyHandler;
	}

	public LoggerUtility getLoggerUtility()
	{
		return loggerUtility;
	}

	public Help getHelp()
	{
		return help;
	}

	public Commands getCommandsUtility()
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
		confighandler = new ConfigurationHandler(this);
		permissionUtility = new PermissionsUtility(this);
		loggerUtility = new LoggerUtility(this);
		moneyHandler = new MoneyHandler(this);
		help = new Help(this);
		animalSpawnHandler = new AnimalSpawnHandler(this);

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
		commands = new Commands(this);
		this.getCommand("animalshop").setExecutor(commands);
		/* Eventregistrierung */
		listener = new ShopPlayerListener(this);
		getLoggerUtility().log("AnimalShop loaded", LoggerLevel.INFO);
	}

	public boolean blockIsValid(Sign s, Player p)
	{
		return blockIsValid(s.getLines(), p);
	}

	public boolean blockIsValid(String[] line, Player p)
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

	public void spawnAnimal(Player p, String animal, String args)
	{
		animalSpawnHandler.spawnAnimal(animal, args, p);
	}
}
