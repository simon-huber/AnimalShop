package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.Main;

public class ChickenSpawner extends AnimalSpawner
{

	public ChickenSpawner(Main plugin)
	{
		super(plugin);
	}

	@Override
	public boolean spawn(String args, Player p)
	{
		String[] aargs = args.split(" ");
		Chicken chicken = (Chicken) p.getWorld().spawnEntity(p.getLocation(), EntityType.CHICKEN);
		for(String string : aargs)
		{
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.chicken.type.BABY").toLowerCase()))
			{
				chicken.setBaby();
			}
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.chicken.type.AGE_LOCK").toLowerCase()))
			{
				chicken.setAgeLock(true);
			}
			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.chicken.type.CUSTOM_NAME").toLowerCase()))
			{
				String[] z = string.split(":");
				if(z.length == 2)
				{
					chicken.setCustomName(z[1]);
				}
			}
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.chicken.type.CUSTOM_NAME_INVISIBLE").toLowerCase()))
			{
				chicken.setCustomNameVisible(false);
			}
		}
		return true;
	}

	@Override
	public String getIdetifier()
	{
		return plugin.getConfigHandler().getLanguageString("system", "animal.chicken.name");
	}

	@Override
	public String getSystemIdentifier()
	{
		return "chicken";
	}

}
