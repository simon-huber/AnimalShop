package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.Main;

public class HorseSpawner extends AnimalSpawner
{

	public HorseSpawner(Main plugin)
	{
		super(plugin);
	}

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
				if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.horse.type." + variant.name()).toLowerCase()))
				{
					horse.setVariant(variant);
				}
			}
		}

		for(Horse.Color variant : Horse.Color.values())
		{
			for(String string : aargs)
			{
				if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.horse.color." + variant.name()).toLowerCase()))
				{
					horse.setColor(variant);
				}
			}
		}

		for(Horse.Style variant : Horse.Style.values())
		{
			for(String string : aargs)
			{
				if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.horse.type." + variant.name()).toLowerCase()))
				{
					horse.setStyle(variant);
				}
			}
		}
		for(String string : aargs)
		{
			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.horse.type.TAMED").toLowerCase()))
			{
				horse.setTamed(true);
				horse.setOwner(p);
			}
			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.horse.type.BABY").toLowerCase()))
			{
				horse.setBaby();
			}
			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.horse.type.CHEST").toLowerCase()))
			{
				horse.setCarryingChest(true);
			}
			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.horse.type.AGE_LOCK").toLowerCase()))
			{
				horse.setAgeLock(true);
			}

			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.horse.type.CUSTOM_NAME").toLowerCase()))
			{
				String[] z = string.split(":");
				if(z.length == 2)
				{
					horse.setCustomName(z[1]);
				}
			}
			if(string.toLowerCase().contains(plugin.getConfigHandler().getLanguageString("system", "animal.horse.type.CUSTOME_NAME_INVISIBLE").toLowerCase()))
			{
				horse.setCustomNameVisible(false);
			}
		}
		return true;
	}

	@Override
	public String getIdetifier()
	{
		return plugin.getConfigHandler().getLanguageString("system", "animal.horse.name");
	}

	@Override
	public String getSystemIdentifier()
	{
		return "horse";
	}

}
