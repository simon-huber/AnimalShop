package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public class HorseSpawner implements AnimalSpawner
{
	@Override
	public boolean spawn(String args, Player p)
	{
		String[] aargs = args.split(" ");
		Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
		horse.setRemoveWhenFarAway(false);
		horse.setAdult();

		for(Horse.Variant variant : Horse.Variant.values())
		{
			for(String string : aargs)
			{
				if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.type." + variant.name()).toLowerCase()))
				{
					horse.setVariant(variant);
				}
			}
		}

		for(Horse.Color variant : Horse.Color.values())
		{
			for(String string : aargs)
			{
				if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.color." + variant.name()).toLowerCase()))
				{
					horse.setColor(variant);
				}
			}
		}

		for(Horse.Style variant : Horse.Style.values())
		{
			for(String string : aargs)
			{
				if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.type." + variant.name()).toLowerCase()))
				{
					horse.setStyle(variant);
				}
			}
		}
		for(String string : aargs)
		{
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.type.TAMED").toLowerCase()))
			{
				horse.setTamed(true);
				horse.setOwner(p);
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.type.BABY").toLowerCase()))
			{
				horse.setBaby();
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.type.CHEST").toLowerCase()))
			{
				horse.setCarryingChest(true);
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.type.AGE_LOCK").toLowerCase()))
			{
				horse.setAgeLock(true);
			}

			if(string.toLowerCase().contains(AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.type.CUSTOM_NAME").toLowerCase()))
			{
				String[] z = string.split(":");
				if(z.length == 2)
				{
					horse.setCustomName(z[1]);
				}
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.type.CUSTOM_NAME_INVISIBLE").toLowerCase()))
			{
				horse.setCustomNameVisible(false);
			}
		}
		AnimalShop.getLoggerUtility().log("Player " + p.getName() + "(" + p.getUniqueId() +") spawned a " + getIdetifier() + " with args: " + args, LoggerLevel.DEBUG);

		return true;
	}

	@Override
	public String getIdetifier()
	{
		return AnimalShop.getConfigHandler().getLanguageString("system", "animal.horse.name");
	}

	@Override
	public String getSystemIdentifier()
	{
		return "horse";
	}

}
