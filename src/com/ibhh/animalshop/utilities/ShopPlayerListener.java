package com.ibhh.animalshop.utilities;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.ibhh.animalshop.Main;
import com.ibhh.animalshop.exception.NoiConomyPluginFound;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;
import com.ibhh.animalshop.utilities.metrics.MetricsHandler;

public class ShopPlayerListener implements Listener
{

	private final Main plugin;
	double doubeline;
	private static final BlockFace[] shopFaces = {BlockFace.SELF, BlockFace.DOWN, BlockFace.UP, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};

	public ShopPlayerListener(Main plugin)
	{
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		Player p = event.getPlayer();
		if((event.hasBlock()) && ((event.getClickedBlock().getState() instanceof Sign)) && (event.getAction() == Action.LEFT_CLICK_BLOCK) && (!p.isSneaking()))
		{ // && !(p.isSneaking())
			Sign s = (Sign) event.getClickedBlock().getState();
			String[] line = s.getLines();
			if(line[0].toLowerCase().contains("animalshop"))
			{
				plugin.getLoggerUtility().log("AnimalShop sign!", LoggerLevel.DEBUG);
				if(plugin.getPermissionsUtility().checkpermissions(p, "AnimalShop.use"))
				{
					if(this.plugin.blockIsValid(line, p))
					{
						double price = getPrice(s, 1, p);
						String Animal = getType(s, 2);
						try
						{
							if((plugin.getMoneyHandler().getBalance(p) - price) >= 0)
							{
								plugin.getMoneyHandler().substract(price, p);
								plugin.getMetricshandler().AnimalShopSignBuy++;
								plugin.spawnAnimal(p, Animal, line[3]);
								plugin.getLoggerUtility().log(p, String.format(plugin.getConfigHandler().getLanguageString(p, "money.purchased"), (int) price), LoggerLevel.INFO);
								plugin.getLoggerUtility().log(p, String.format(plugin.getConfigHandler().getLanguageString(p, "money.purchased2"), plugin.getConfigHandler().getLanguageString(p, "animal." + plugin.getAnimalSpawnHandler().getSystemNameofAnimal(Animal) + ".name")), LoggerLevel.INFO);
							}
							else
							{
								plugin.getLoggerUtility().log(p, plugin.getConfigHandler().getLanguageString(p, "money.notenough"), LoggerLevel.ERROR);
							}
						}
						catch(NoiConomyPluginFound e)
						{
							plugin.getLoggerUtility().log(event.getPlayer(), e.getMessage(), LoggerLevel.ERROR);
						}
					} else {
						plugin.getLoggerUtility().log(p, plugin.getConfigHandler().getLanguageString(p, "sign.notvalid.general"), LoggerLevel.ERROR);
					}
				}
			}
		}
	}

	private double getPrice(Sign s, int l, Player p)
	{
		String line = s.getLine(l);
		try
		{
			this.doubeline = Double.parseDouble(line);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return this.doubeline;
	}

	private String getType(Sign s, int l)
	{
		String line = s.getLine(l);
		return line;
	}

	@EventHandler
	public void onChange(SignChangeEvent event)
	{
		Player p = event.getPlayer();
		if(event.getLine(0).toLowerCase().contains("animalshop"))
		{
			try
			{
				if(plugin.getPermissionsUtility().checkpermissions(p, "AnimalShop.create"))
				{
					String[] line = event.getLines();
					if(plugin.blockIsValid(line, p))
					{
						plugin.getLoggerUtility().log(p, plugin.getConfigHandler().getLanguageString(p, "sign.created"), LoggerLevel.INFO);
						event.setLine(0, "[AnimalShop]");
						MTLocation loc = MTLocation.getMTLocationFromLocation(event.getBlock().getLocation());
						if(!MetricsHandler.Shop.containsKey(loc))
						{
							MetricsHandler.Shop.put(loc, event.getPlayer().getName());
						}
					}
					else
					{
						plugin.getLoggerUtility().log(p, plugin.getConfigHandler().getLanguageString(p, "sign.failed"), LoggerLevel.ERROR);
						event.setCancelled(true);
					}
				}
				else
				{
					event.setCancelled(true);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				event.setCancelled(true);
				plugin.getLoggerUtility().log(p, plugin.getConfigHandler().getLanguageString(p, "sign.failed"), LoggerLevel.ERROR);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBreak(BlockBreakEvent event)
	{
		Player p = event.getPlayer();
		if(!(event.getBlock().getState() instanceof Sign))
		{
			org.bukkit.block.Sign sign = findSign(event.getBlock(), p);
			if(isCorrectSign(sign, event.getBlock()))
			{
				if(sign.getLine(0).toLowerCase().contains("animalshop"))
				{
					if(plugin.getPermissionsUtility().checkpermissions(p, "AnimalShop.create"))
					{
						if(plugin.blockIsValid(sign, p))
						{
							MTLocation loc = MTLocation.getMTLocationFromLocation(sign.getLocation());
							if(MetricsHandler.Shop.containsKey(loc))
							{
								MetricsHandler.Shop.remove(loc);
							}
						}
					}
					else
					{
						event.setCancelled(true);
					}
				}
			}
		}
		else
		{
			Sign s = (Sign) event.getBlock().getState();
			String[] line = s.getLines();

			if(line[0].toLowerCase().contains("animalshop"))
			{
				if(plugin.getPermissionsUtility().checkpermissions(p, "AnimalShop.create"))
				{
					if(this.plugin.blockIsValid(line, p))
					{
						MTLocation loc = MTLocation.getMTLocationFromLocation(s.getLocation());
						if(MetricsHandler.Shop.containsKey(loc))
						{
							MetricsHandler.Shop.remove(loc);
						}
					}
				}
				else
				{
					event.setCancelled(true);
				}
			}
		}
	}

	public Sign findSign(Block block, Player p)
	{
		for(BlockFace bf : shopFaces)
		{
			Block faceBlock = block.getRelative(bf);
			if(isSign(faceBlock))
			{
				Sign sign = (Sign) faceBlock.getState();
				if((plugin.blockIsValid(sign, p)) && ((faceBlock.equals(block)) || (getAttachedFace(sign).equals(block))))
				{
					return sign;
				}
			}
		}
		return null;
	}

	public static Block getAttachedFace(org.bukkit.block.Sign sign)
	{
		return sign.getBlock().getRelative(((org.bukkit.material.Sign) sign.getData()).getAttachedFace());
	}

	private static boolean isCorrectSign(org.bukkit.block.Sign sign, Block block)
	{
		return (sign != null) && ((sign.getBlock().equals(block)) || (getAttachedFace(sign).equals(block)));
	}

	public static boolean isSign(Block block)
	{
		return block.getState() instanceof Sign;
	}
}