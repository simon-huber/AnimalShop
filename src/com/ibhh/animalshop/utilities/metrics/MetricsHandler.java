package com.ibhh.animalshop.utilities.metrics;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.utilities.MTLocation;
import com.ibhh.animalshop.utilities.ObjectManager;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

/**
 * @author ibhh
 */
public class MetricsHandler
{

	private AnimalShop plugin;
	private Metrics metrics;
	public static HashMap<MTLocation, String> Shop = new HashMap<MTLocation, String>();
	public static int Error = 0;
	public int AnimalShopSignBuy = 0;
	public int Commands = 0;

	public MetricsHandler(AnimalShop pl)
	{
		plugin = pl;
	}

	public void onStart()
	{
		try
		{
			metrics = new Metrics(plugin);
		}
		catch(IOException ex)
		{
			AnimalShop.getLoggerUtility().log("There was an error while submitting statistics.", LoggerLevel.ERROR);
		}
		initializeGraphs();
		startStatistics();
		AnimalShop.getLoggerUtility().log("Metrics started", LoggerLevel.DEBUG);
	}

	public void saveStatsFiles()
	{
		try
		{
			File folder = new File(plugin.getDataFolder() + File.separator + "metrics");
			folder.mkdirs();
			ObjectManager.save(Shop, plugin.getDataFolder() + File.separator + "metrics" + File.separator + "Shop.statistics");
			AnimalShop.getLoggerUtility().log("Shops stats file contains " + calculateShopQuantity() + " values!", LoggerLevel.DEBUG);
		}
		catch(Exception e)
		{
			AnimalShop.getLoggerUtility().log("Cannot save Shop statistics! " + e.getMessage(), LoggerLevel.ERROR);
		}
	}

	public void loadStatsFiles()
	{
		try
		{
			Shop = ObjectManager.load(plugin.getDataFolder() + File.separator + "metrics" + File.separator + "Shop.statistics");
			AnimalShop.getLoggerUtility().log("Shops stats file contains " + calculateShopQuantity() + " values!", LoggerLevel.DEBUG);
			AnimalShop.getLoggerUtility().log("Stats loaded!", LoggerLevel.DEBUG);
		}
		catch(Exception e)
		{
			AnimalShop.getLoggerUtility().log("Cannot load Shop statistics! " + e.getMessage(), LoggerLevel.ERROR);
		}
	}

	private void startStatistics()
	{
		try
		{
			metrics.start();
		}
		catch(Exception ex)
		{
			AnimalShop.getLoggerUtility().log("There was an error while submitting statistics.", LoggerLevel.DEBUG);
		}
	}

	private void initializeGraphs()
	{
		initializeOthers();
		initializeDependenciesGraph();
		initializeCommandGraph();
	}

	public void initializeOthers()
	{
		Metrics.Graph ShopCountGraph = metrics.createGraph("Shops");
		ShopCountGraph.addPlotter(new Metrics.Plotter("AnimalShopSigns")
		{

			@Override
			public int getValue()
			{
				return calculateShopQuantity();
			}
		});
		Metrics.Graph GMGraph = metrics.createGraph("DefaultGameMode");
		GMGraph.addPlotter(new Metrics.Plotter(plugin.getServer().getDefaultGameMode().name())
		{

			@Override
			public int getValue()
			{
				return 1;
			}
		});
		Metrics.Graph errorgraph = metrics.createGraph("uncatchedErrors");
		errorgraph.addPlotter(new Metrics.Plotter(plugin.getServer().getDefaultGameMode().name())
		{

			@Override
			public int getValue()
			{
				return Error;
			}

			@Override
			public void reset()
			{
				Error = 0;
			}
		});
	}

	private void initializeCommandGraph()
	{
		Metrics.Graph CMDUses = metrics.createGraph("ShopUses");
		CMDUses.addPlotter(new Metrics.Plotter("BookShopSignBuy")
		{

			@Override
			public int getValue()
			{
				return AnimalShopSignBuy;
			}

			@Override
			public void reset()
			{
				AnimalShopSignBuy = 0;
			}
		});
		CMDUses.addPlotter(new Metrics.Plotter("Commands")
		{

			@Override
			public int getValue()
			{
				return Commands;
			}

			@Override
			public void reset()
			{
				Commands = 0;
			}
		});
	}

	public void initializeDependenciesGraph()
	{
		Metrics.Graph depGraph = metrics.createGraph("EconomyDependencies");
		String iConomyName = "None";
		if(AnimalShop.getMoneyHandler().iConomyversion() != 0)
		{
			if(AnimalShop.getMoneyHandler().iConomyversion() == 1)
			{
				iConomyName = "Register";
			}
			else if(AnimalShop.getMoneyHandler().iConomyversion() == 2)
			{
				iConomyName = "Vault";
			}
			else if(AnimalShop.getMoneyHandler().iConomyversion() == 5)
			{
				iConomyName = "iConomy5";
			}
			else if(AnimalShop.getMoneyHandler().iConomyversion() == 6)
			{
				iConomyName = "iConomy6";
			}
		}
		depGraph.addPlotter(new Metrics.Plotter(iConomyName)
		{

			@Override
			public int getValue()
			{
				return 1;
			}
		});
		Metrics.Graph Permgraph = metrics.createGraph("PermissionDependencies");
		String PermName = "BukkitPermissions";
		Permgraph.addPlotter(new Metrics.Plotter(PermName)
		{

			@Override
			public int getValue()
			{
				return 1;
			}
		});
	}

	public int calculateShopQuantity()
	{
		int a = 0;
		for(@SuppressWarnings("unused")
		String i : Shop.values())
		{
			a++;
		}
		return a;
	}
}
