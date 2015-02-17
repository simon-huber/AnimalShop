package com.ibhh.animalshop.utilities.animal;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.exception.SignNotValidException;

public class AnimalSpawnHandler extends ArrayList<AnimalSpawner>
{
	private static final long serialVersionUID = 1L;

	public AnimalSpawnHandler()
	{
		add(new SheepSpawner());
		add(new CowSpawner());
		add(new MushroomCowSpawner());
		add(new PigSpawner());
		add(new VillagerSpawner());
		add(new WolfSpawner());
		add(new CatSpawner());
		add(new HorseSpawner());
		add(new RabbitSpawner());
	}

	public boolean spawnAnimal(String animal, String args, Player p)
	{
		for(Iterator<AnimalSpawner> iterator = iterator(); iterator.hasNext();)
		{
			AnimalSpawner animalSpawner = (AnimalSpawner) iterator.next();
			if(animalSpawner.getIdetifier().equalsIgnoreCase(animal))
			{
				return animalSpawner.spawn(args, p);
			}
		}
		return false;
	}

	public void signIsValid(String[] s) throws SignNotValidException
	{
		if(!s[0].toLowerCase().contains("animalshop"))
		{
			throw new SignNotValidException(AnimalShop.getConfigHandler().getLanguageString("system", "sign.notvalid.firstline"));
		}
		try
		{
			Integer.parseInt(s[1]);
		}
		catch(Exception e)
		{
			throw new SignNotValidException(AnimalShop.getConfigHandler().getLanguageString("system", "sign.notvalid.secondline"));
		}
		if(!animalNameValid(s[2]))
		{
			String animals = "";
			for(Iterator<AnimalSpawner> iterator = iterator(); iterator.hasNext();)
			{
				AnimalSpawner animalSpawner = (AnimalSpawner) iterator.next();
				animals += animalSpawner.getIdetifier() + ", ";
			}
			throw new SignNotValidException(AnimalShop.getConfigHandler().getLanguageString("system", "sign.notvalid.thirdline") + animals);
		}
		if(s[3].equals(""))
		{
			return;
		}
		if(getSystemNameofAnimal(s[2]) == null)
		{
			return;
		}
		ConfigurationSection configSection = AnimalShop.getConfigHandler().getLanguage_config(AnimalShop.getConfigHandler().getConfig().getString("language")).getConfigurationSection("animal." + getSystemNameofAnimal(s[2]) + ".color");
		ConfigurationSection configSectiontype = AnimalShop.getConfigHandler().getLanguage_config(AnimalShop.getConfigHandler().getConfig().getString("language")).getConfigurationSection("animal." + getSystemNameofAnimal(s[2]) + ".type");
		String[] args_name = s[3].split(" ");
		for(String string : args_name)
		{
			boolean found = false;
			if(configSection != null)
			{
				for(String key : configSection.getKeys(false))
				{
					if(string.toLowerCase().contains(configSection.getString(key).toLowerCase()))
					{
						found = true;
					}
				}
			}
			if(configSectiontype != null)
			{
				for(String key : configSectiontype.getKeys(false))
				{
					if(string.toLowerCase().contains(configSectiontype.getString(key).toLowerCase()))
					{
						found = true;
					}
				}
			}
			if(!found)
			{
				String args = "";
				if(configSection != null)
				{
					for(String key : configSection.getKeys(false))
					{
						args += configSection.getString(key) + ", ";
					}
				}
				if(configSectiontype != null)
				{
					for(String key : configSectiontype.getKeys(false))
					{
						args += configSectiontype.getString(key) + ", ";
					}
				}
				throw new SignNotValidException(AnimalShop.getConfigHandler().getLanguageString("system", "sign.notvalid.fourthline") + " " + args);
			}
		}
		return;
	}

	public String getSystemNameofAnimal(String s)
	{
		for(Iterator<AnimalSpawner> iterator = iterator(); iterator.hasNext();)
		{
			AnimalSpawner animalSpawner = (AnimalSpawner) iterator.next();
			if(s.equalsIgnoreCase(animalSpawner.getIdetifier()))
			{
				return animalSpawner.getSystemIdentifier();
			}
		}
		return null;
	}

	public boolean animalNameValid(String s)
	{
		for(Iterator<AnimalSpawner> iterator = iterator(); iterator.hasNext();)
		{
			AnimalSpawner animalSpawner = (AnimalSpawner) iterator.next();
			if(s.equalsIgnoreCase(animalSpawner.getIdetifier()))
			{
				return true;
			}
		}
		return false;
	}
}
