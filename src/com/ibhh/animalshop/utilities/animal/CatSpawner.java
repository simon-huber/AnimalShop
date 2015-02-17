package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.Main;

public class CatSpawner extends AnimalSpawner
{

	public CatSpawner(Main plugin)
	{
		super(plugin);
	}

	@Override
	public boolean spawn(String args, Player p)
	{
		String[] aargs = args.split(" ");
		Ocelot ocelot = (Ocelot) p.getWorld().spawnEntity(p.getLocation(), EntityType.OCELOT);
		ocelot.setRemoveWhenFarAway(false);
		for(Ocelot.Type type : Ocelot.Type.values())
		{
			for(String string : aargs)
			{
				if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.ocelot.type." + type.name()).toLowerCase()))
				{
					ocelot.setCatType(type);
				}
			}
		}
		for(String string : aargs)
		{
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.ocelot.type.TAMED").toLowerCase()))
			{
				ocelot.setTamed(true);
				ocelot.setOwner(p);
			}

			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.ocelot.type.BABY").toLowerCase()))
			{
				ocelot.setBaby();
			}
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.ocelot.type.AGE_LOCK").toLowerCase()))
			{
				ocelot.setAgeLock(true);
			}
			if(string.toLowerCase().equalsIgnoreCase(plugin.getConfigHandler().getLanguageString("system", "animal.ocelot.type.CUSTOM_NAME_INVISIBLE").toLowerCase()))
			{
				ocelot.setCustomNameVisible(false);
			}
			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.ocelot.type.CUSTOM_NAME").toLowerCase()))
			{
				String[] z = string.split(":");
				if(z.length == 2)
				{
					ocelot.setCustomName(z[1]);
				}
			}
		}

		return true;
	}

	@Override
	public String getIdetifier()
	{
		return plugin.getConfigHandler().getLanguageString("system", "animal.ocelot.name");
	}

	@Override
	public String getSystemIdentifier()
	{
		return "ocelot";
	}

}
