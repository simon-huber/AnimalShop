package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public class MushroomCowSpawner implements AnimalSpawner
{
	@Override
	public boolean spawn(String args, Player p)
	{
		String[] aargs = args.split(" ");
		MushroomCow mushroomcow = (MushroomCow) p.getWorld().spawnEntity(p.getLocation(), EntityType.MUSHROOM_COW);
		mushroomcow.setRemoveWhenFarAway(false);
		for(String string : aargs)
		{
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.mushroomcow.type.BABY").toLowerCase()))
			{
				mushroomcow.setBaby();
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.mushroomcow.type.AGE_LOCK").toLowerCase()))
			{
				mushroomcow.setAgeLock(true);
			}
			if(string.toLowerCase().contains(AnimalShop.getConfigHandler().getLanguageString("system", "animal.mushroomcow.type.CUSTOM_NAME").toLowerCase()))
			{
				String[] z = string.split(":");
				if(z.length == 2)
				{
					mushroomcow.setCustomName(z[1]);
				}
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.mushroomcow.type.CUSTOM_NAME_INVISIBLE").toLowerCase()))
			{
				mushroomcow.setCustomNameVisible(false);
			}
		}
		AnimalShop.getLoggerUtility().log("Player " + p.getName() + "(" + p.getUniqueId() +") spawned a " + getIdetifier() + " with args: " + args, LoggerLevel.DEBUG);

		return true;
	}

	@Override
	public String getIdetifier()
	{
		return AnimalShop.getConfigHandler().getLanguageString("system", "animal.mushroomcow.name");
	}

	@Override
	public String getSystemIdentifier()
	{
		return "mushroomcow";
	}

}
