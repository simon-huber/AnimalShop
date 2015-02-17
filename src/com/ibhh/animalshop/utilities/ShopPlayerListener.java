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

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.exception.NoiConomyPluginFound;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;
import com.ibhh.animalshop.utilities.metrics.MetricsHandler;

public class ShopPlayerListener implements Listener
{

	double doubeline;
	private static final BlockFace[] shopFaces = {BlockFace.SELF, BlockFace.DOWN, BlockFace.UP, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};

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
				AnimalShop.getLoggerUtility().log("AnimalShop sign!", LoggerLevel.DEBUG);
				if(AnimalShop.getPermissionsUtility().checkpermissions(p, "AnimalShop.use"))
				{
					if(AnimalShop.blockIsValid(line, p))
					{
						double price = getPrice(s, 1, p);
						String Animal = getType(s, 2);
						try
						{
							if((AnimalShop.getMoneyHandler().getBalance(p) - price) >= 0)
							{
								AnimalShop.getMoneyHandler().substract(price, p);
								AnimalShop.getMetricshandler().AnimalShopSignBuy++;
								AnimalShop.getAnimalSpawnHandler().spawnAnimal(Animal, line[3], p);
								AnimalShop.getLoggerUtility().log(p, String.format(AnimalShop.getConfigHandler().getLanguageString(p, "money.purchased"), (int) price), LoggerLevel.INFO);
								AnimalShop.getLoggerUtility().log(p, String.format(AnimalShop.getConfigHandler().getLanguageString(p, "money.purchased2"), AnimalShop.getConfigHandler().getLanguageString(p, "animal." + AnimalShop.getAnimalSpawnHandler().getSystemNameofAnimal(Animal) + ".name")), LoggerLevel.INFO);
							}
							else
							{
								AnimalShop.getLoggerUtility().log(p, AnimalShop.getConfigHandler().getLanguageString(p, "money.notenough"), LoggerLevel.ERROR);
							}
						}
						catch(NoiConomyPluginFound e)
						{
							AnimalShop.getLoggerUtility().log(event.getPlayer(), e.getMessage(), LoggerLevel.ERROR);
						}
					}
					else
					{
						AnimalShop.getLoggerUtility().log(p, AnimalShop.getConfigHandler().getLanguageString(p, "sign.notvalid.general"), LoggerLevel.ERROR);
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
				if(AnimalShop.getPermissionsUtility().checkpermissions(p, "AnimalShop.create"))
				{
					String[] line = event.getLines();
					if(AnimalShop.blockIsValid(line, p))
					{
						AnimalShop.getLoggerUtility().log(p, AnimalShop.getConfigHandler().getLanguageString(p, "sign.created"), LoggerLevel.INFO);
						event.setLine(0, "[AnimalShop]");
						MTLocation loc = MTLocation.getMTLocationFromLocation(event.getBlock().getLocation());
						if(!MetricsHandler.Shop.containsKey(loc))
						{
							MetricsHandler.Shop.put(loc, event.getPlayer().getName());
						}
					}
					else
					{
						AnimalShop.getLoggerUtility().log(p, AnimalShop.getConfigHandler().getLanguageString(p, "sign.failed"), LoggerLevel.ERROR);
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
				AnimalShop.getLoggerUtility().log(p, AnimalShop.getConfigHandler().getLanguageString(p, "sign.failed"), LoggerLevel.ERROR);
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
					if(AnimalShop.getPermissionsUtility().checkpermissions(p, "AnimalShop.create"))
					{
						if(AnimalShop.blockIsValid(sign, p))
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
				if(AnimalShop.getPermissionsUtility().checkpermissions(p, "AnimalShop.create"))
				{
					if(AnimalShop.blockIsValid(line, p))
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
				if((AnimalShop.blockIsValid(sign, p)) && ((faceBlock.equals(block)) || (getAttachedFace(sign).equals(block))))
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