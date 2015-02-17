package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.Main;

public class PigSpawner extends AnimalSpawner
{

	public PigSpawner(Main plugin)
	{
		super(plugin);
	}

	@Override
	public boolean spawn(String args, Player p)
	{
		String[] aargs = args.split(" ");
		Pig pig = (Pig) p.getWorld().spawnEntity(p.getLocation(), EntityType.PIG);
		for(String string : aargs)
		{
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.pig.type.BABY").toLowerCase()))
			{
				pig.setBaby();
			}
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.pig.type.AGE_LOCK").toLowerCase()))
			{
				pig.setAgeLock(true);
			}
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.pig.type.SADDLE").toLowerCase()))
			{
				pig.setSaddle(true);
				pig.setRemoveWhenFarAway(false);
			}

			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.pig.type.CUSTOM_NAME").toLowerCase()))
			{
				String[] z = string.split(":");
				if(z.length == 2)
				{
					pig.setCustomName(z[1]);
				}
			}
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.pig.type.CUSTOM_NAME_INVISIBLE").toLowerCase()))
			{
				pig.setCustomNameVisible(false);
			}
		}
		return true;
	}

	@Override
	public String getIdetifier()
	{
		return plugin.getConfigHandler().getLanguageString("system", "animal.pig.name");
	}

	@Override
	public String getSystemIdentifier()
	{
		return "pig";
	}

}
