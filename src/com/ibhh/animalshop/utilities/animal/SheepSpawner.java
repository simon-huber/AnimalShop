package com.ibhh.animalshop.utilities.animal;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public class SheepSpawner implements AnimalSpawner
{
	@Override
	public boolean spawn(String args, Player p)
	{
		String[] aargs = args.split(" ");
		Sheep sheep = (Sheep) p.getWorld().spawnEntity(p.getLocation(), EntityType.SHEEP);
		for(DyeColor color : DyeColor.values())
		{
			for(String string : aargs)
			{
				if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.sheep.color." + color.name()).toLowerCase()))
				{
					sheep.setColor(color);
				}
			}
		}
		for(String string : aargs)
		{
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.sheep.type.BABY").toLowerCase()))
			{
				sheep.setBaby();
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.sheep.type.AGE_LOCK").toLowerCase()))
			{
				sheep.setAgeLock(true);
			}
			if(string.toLowerCase().contains(AnimalShop.getConfigHandler().getLanguageString("system", "animal.sheep.type.CUSTOM_NAME").toLowerCase()))
			{
				String[] z = string.split(":");
				if(z.length == 2)
				{
					sheep.setCustomName(z[1]);
				}
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.sheep.type.CUSTOM_NAME_INVISIBLE").toLowerCase()))
			{
				sheep.setCustomNameVisible(false);
			}
		}
		AnimalShop.getLoggerUtility().log("Player " + p.getName() + "(" + p.getUniqueId() +") spawned a " + getIdetifier() + " with args: " + args, LoggerLevel.DEBUG);

		return true;
	}

	@Override
	public String getIdetifier()
	{
		return AnimalShop.getConfigHandler().getLanguageString("system", "animal.sheep.name");
	}

	@Override
	public String getSystemIdentifier()
	{
		return "sheep";
	}

}
