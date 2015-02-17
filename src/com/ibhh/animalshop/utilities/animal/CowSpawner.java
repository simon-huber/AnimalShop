package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.Main;

public class CowSpawner extends AnimalSpawner
{

	public CowSpawner(Main plugin)
	{
		super(plugin);
	}

	@Override
	public boolean spawn(String args, Player p)
	{
		String[] aargs = args.split(" ");
		Cow cow = (Cow) p.getWorld().spawnEntity(p.getLocation(), EntityType.COW);
		for(String string : aargs)
		{
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.cow.type.BABY").toLowerCase()))
			{
				cow.setBaby();
			}
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.cow.type.AGE_LOCK").toLowerCase()))
			{
				cow.setAgeLock(true);
			}
			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.ocelot.type.CUSTOM_NAME").toLowerCase()))
			{
				String[] z = string.split(":");
				if(z.length == 2)
				{
					cow.setCustomName(z[1]);
				}
			}
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.cow.type.CUSTOM_NAME_INVISIBLE").toLowerCase()))
			{
				cow.setCustomNameVisible(false);
			}
		}
		return true;
	}

	@Override
	public String getIdetifier()
	{
		return plugin.getConfigHandler().getLanguageString("system", "animal.cow.name");
	}

	@Override
	public String getSystemIdentifier()
	{
		return "cow";
	}

}
