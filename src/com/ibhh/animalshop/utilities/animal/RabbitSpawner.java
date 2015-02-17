package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public class RabbitSpawner implements AnimalSpawner
{

	@Override
	public boolean spawn(String args, Player p)
	{
		String[] aargs = args.split(" ");
		Rabbit rabbit = (Rabbit) p.getWorld().spawnEntity(p.getLocation(), EntityType.RABBIT);
		for(Rabbit.Type variant : Rabbit.Type.values())
		{
			for(String string : aargs)
			{
				if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.rabbit.type." + variant.name())))
				{
					rabbit.setRabbitType(variant);
					AnimalShop.getLoggerUtility().log("Type set: " + variant.name(), LoggerLevel.DEBUG);
				}
			}
		}
		for(String string : aargs)
		{
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.rabbit.type.BABY").toLowerCase()))
			{
				rabbit.setBaby();
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.rabbit.type.AGE_LOCK").toLowerCase()))
			{
				rabbit.setAgeLock(true);
			}
			if(string.toLowerCase().contains(AnimalShop.getConfigHandler().getLanguageString("system", "animal.rabbit.type.CUSTOM_NAME").toLowerCase()))
			{
				String[] z = string.split(":");
				if(z.length == 2)
				{
					rabbit.setCustomName(z[1]);
				}
			}
			if(string.toLowerCase().equalsIgnoreCase(AnimalShop.getConfigHandler().getLanguageString("system", "animal.rabbit.type.CUSTOM_NAME_INVISIBLE").toLowerCase()))
			{
				rabbit.setCustomNameVisible(false);
			}
		}
		AnimalShop.getLoggerUtility().log("Player " + p.getName() + "(" + p.getUniqueId() +") spawned a " + getIdetifier() + " with args: " + args, LoggerLevel.DEBUG);

		return true;
	}

	@Override
	public String getIdetifier()
	{
		return AnimalShop.getConfigHandler().getLanguageString("system", "animal.rabbit.name");
	}

	@Override
	public String getSystemIdentifier()
	{
		return "rabbit";
	}

}
