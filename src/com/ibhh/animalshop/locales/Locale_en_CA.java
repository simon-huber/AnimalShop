package com.ibhh.animalshop.locales;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

public class Locale_en_CA extends PluginLocale implements PredefinedLocale
{

	public Locale_en_CA(String path)
	{
		super("en_CA", Localizer.getByCode("en_CA").getName());
		createFile(path);
	}

	@Override
	public void createFile(String path)
	{
		File folder = new File(path + File.separator);
		folder.mkdirs();
		File configl = new File(path + File.separator + "language." + getCode() + ".yml");
		if(!configl.exists())
		{
			try
			{
				configl.createNewFile();
			}
			catch(IOException ex)
			{
				System.err.println("Couldnt create new arenaconfig file!");
			}
		}

		try
		{
			load(configl);
		}
		catch(IOException | InvalidConfigurationException e)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + configl, e);
			e.printStackTrace();
		}

		addDefault("configuration.language.pathnotfound", "In the configuration file \"language.%s.yml\" is no path \"%s\"! The default language is used: ");
		addDefault("configuration.language.languagenotfound", "The configuration file of the language \"%s\" does not exist!");
		addDefault("configuration.language.list", "Following languages are installed and can be selected with: ");
		addDefault("configuration.language.listhover", "Click to change!");
		
		addDefault("help.head", "AnimalShop Help ");
		addDefault("help.commandsallowed", "You are allowed to run following commands:");
		addDefault("permission.error", "we have a problem! You musnt do this!");
		
		addDefault("money.notenough", "You haven't got enough money!");
		addDefault("money.purchased", "You paid %d.");
		addDefault("money.purchased2", "Woah! A %s. You should befriend it!");
		
		addDefault("sign.created", "AnimalShop created!");
		addDefault("sign.failed", "Creation failed!");

		addDefault("sign.notvalid.general", "Wrong syntax: [AnimalShop] -> price -> animalname -> arguments");
		addDefault("sign.notvalid.firstline", "The first line must contain \"AnimalShop\"!");
		addDefault("sign.notvalid.secondline", "The second line must contain a number!");
		addDefault("sign.notvalid.thirdline", "The third line must be an animal! Possible: ");
		addDefault("sign.notvalid.fourthline", "The fourth line can contain following arguments: ");

		
		addDefault("animal.sheep.name", "Sheep");
		addDefault("animal.sheep.color.WHITE", "white");
		addDefault("animal.sheep.color.SILVER", "silver");
		addDefault("animal.sheep.color.GRAY", "grey");
		addDefault("animal.sheep.color.BLACK", "black");
		addDefault("animal.sheep.color.RED", "red");
		addDefault("animal.sheep.color.BROWN", "brown");
		addDefault("animal.sheep.color.YELLOW", "yellow");
		addDefault("animal.sheep.color.LIME", "lime");
		addDefault("animal.sheep.color.GREEN", "green");
		addDefault("animal.sheep.color.CYAN", "cyan");
		addDefault("animal.sheep.color.PINK", "pink");
		addDefault("animal.sheep.color.BLUE", "blue");
		addDefault("animal.sheep.color.LIGHT_BLUE", "light_blue");
		addDefault("animal.sheep.color.MAGENTA", "magenta");
		addDefault("animal.sheep.color.PURPLE", "purple");
		addDefault("animal.sheep.color.ORANGE", "orange");
		addDefault("animal.sheep.type.BABY", "baby");
		addDefault("animal.sheep.type.AGE_LOCK", "age_lock");
		addDefault("animal.sheep.type.CUSTOM_NAME", "name:");
		addDefault("animal.sheep.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.cow.name", "Cow");
		addDefault("animal.cow.type.BABY", "baby");
		addDefault("animal.cow.type.AGE_LOCK", "age_lock");
		addDefault("animal.cow.type.CUSTOM_NAME", "name:");
		addDefault("animal.cow.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		

		addDefault("animal.mushroomcow.name", "Mushroomcow");
		addDefault("animal.mushroomcow.type.BABY", "baby");
		addDefault("animal.mushroomcow.type.AGE_LOCK", "age_lock");
		addDefault("animal.mushroomcow.type.CUSTOM_NAME", "name:");
		addDefault("animal.mushroomcow.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.pig.name", "Pig");
		addDefault("animal.pig.type.SADDLE", "saddle");
		addDefault("animal.pig.type.BABY", "baby");
		addDefault("animal.pig.type.AGE_LOCK", "age_lock");
		addDefault("animal.pig.type.CUSTOM_NAME", "name:");
		addDefault("animal.pig.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		

		addDefault("animal.villager.name", "Villager");
		addDefault("animal.villager.type.BLACKSMITH", "blacksmith");
		addDefault("animal.villager.type.BUTCHER", "butcher");
		addDefault("animal.villager.type.FARMER", "farmer");
		addDefault("animal.villager.type.PRIEST", "priest");
		addDefault("animal.villager.type.LIBRARIAN", "librarian");
		addDefault("animal.villager.type.BABY", "baby");
		addDefault("animal.villager.type.AGE_LOCK", "age_lock");
		addDefault("animal.villager.type.CUSTOM_NAME", "name:");
		addDefault("animal.villager.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.wolf.name", "Wolf");
		addDefault("animal.wolf.color.WHITE", "white");
		addDefault("animal.wolf.color.SILVER", "silver");
		addDefault("animal.wolf.color.GRAY", "grey");
		addDefault("animal.wolf.color.BLACK", "black");
		addDefault("animal.wolf.color.RED", "red");
		addDefault("animal.wolf.color.BROWN", "brown");
		addDefault("animal.wolf.color.YELLOW", "yellow");
		addDefault("animal.wolf.color.LIME", "lime");
		addDefault("animal.wolf.color.GREEN", "green");
		addDefault("animal.wolf.color.CYAN", "cyan");
		addDefault("animal.wolf.color.PINK", "pink");
		addDefault("animal.wolf.color.BLUE", "blue");
		addDefault("animal.wolf.color.LIGHT_BLUE", "light_blue");
		addDefault("animal.wolf.color.MAGENTA", "magenta");
		addDefault("animal.wolf.color.PURPLE", "purple");
		addDefault("animal.wolf.color.ORANGE", "orange");
		addDefault("animal.wolf.type.TAMED", "tamed");
		addDefault("animal.wolf.type.ANGRY", "angry");
		addDefault("animal.wolf.type.BABY", "baby");
		addDefault("animal.wolf.type.AGE_LOCK", "age_lock");
		addDefault("animal.wolf.type.CUSTOM_NAME", "name:");
		addDefault("animal.wolf.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.ocelot.name", "Ocelot");
		addDefault("animal.ocelot.type.BLACK_CAT", "black");
		addDefault("animal.ocelot.type.RED_CAT", "red");
		addDefault("animal.ocelot.type.SIAMESE_CAT", "siamese");
		addDefault("animal.ocelot.type.WILD_OCELOT", "wild");
		addDefault("animal.ocelot.type.TAMED", "tamed");
		addDefault("animal.ocelot.type.BABY", "baby");
		addDefault("animal.ocelot.type.AGE_LOCK", "age_lock");
		addDefault("animal.ocelot.type.CUSTOM_NAME", "name:");
		addDefault("animal.ocelot.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.horse.name", "Horse");
		addDefault("animal.horse.type.DONKEY", "donkey");
		addDefault("animal.horse.type.HORSE", "horse");
		addDefault("animal.horse.type.MULE", "mule");
		addDefault("animal.horse.type.SKELETON_HORSE", "skeleton");
		addDefault("animal.horse.type.UNDEAD_HORSE", "undead");
		addDefault("animal.horse.type.BLACK_DOTS", "black_dots");
		addDefault("animal.horse.type.NONE", "none");
		addDefault("animal.horse.type.WHITE", "st_white");
		addDefault("animal.horse.type.WHITE_DOTS", "white_dots");
		addDefault("animal.horse.type.WHITEFIELD", "whitefield");
		addDefault("animal.horse.color.WHITE", "white");
		addDefault("animal.horse.color.BROWN", "brown");
		addDefault("animal.horse.color.CHESTNUT", "chestnut");
		addDefault("animal.horse.color.CREAMY", "creamy");
		addDefault("animal.horse.color.DARK_BROWN", "darkbrown");
		addDefault("animal.horse.color.GRAY", "gray");
		addDefault("animal.horse.type.CHEST", "chest");
		addDefault("animal.horse.type.TAMED", "tamed");
		addDefault("animal.horse.type.BABY", "baby");
		addDefault("animal.horse.type.AGE_LOCK", "age_lock");
		addDefault("animal.horse.type.CUSTOM_NAME", "name:");
		addDefault("animal.horse.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.chicken.name", "Chicken");
		addDefault("animal.chicken.type.BABY", "baby");
		addDefault("animal.chicken.type.AGE_LOCK", "age_lock");
		addDefault("animal.chicken.type.CUSTOM_NAME", "name:");
		addDefault("animal.chicken.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.rabbit.name", "Rabbit");
		addDefault("animal.rabbit.type.BLACK", "black");
		addDefault("animal.rabbit.type.BLACK_AND_WHITE", "blackandwhite");
		addDefault("animal.rabbit.type.BROWN", "brown");
		addDefault("animal.rabbit.type.GOLD", "gold");
		addDefault("animal.rabbit.type.SALT_AND_PEPPER", "salt_and_pepper");
		addDefault("animal.rabbit.type.WHITE", "white");
		addDefault("animal.rabbit.type.THE_KILLER_BUNNY", "killerbunny");
		addDefault("animal.rabbit.type.BABY", "baby");
		addDefault("animal.rabbit.type.AGE_LOCK", "age_lock");
		addDefault("animal.rabbit.type.CUSTOM_NAME", "name:");
		addDefault("animal.rabbit.type.CUSTOM_NAME_INVISIBLE", "nainvis");


		/* changelanguage Command */
		addDefault("commands.changelanguage.name", "changelanguage");
		addDefault("commands.changelanguage.permission", "AnimalShop.changelanguage");
		addDefault("commands.changelanguage.description", "changes the language");
		addDefault("commands.changelanguage.usage", "/animalshop changelanguage [language]");
		addDefault("commands.changelanguage.return", "The language has been changed.");

		/* sprachliste Command */
		addDefault("commands.languagelist.name", "languagelist");
		addDefault("commands.languagelist.permission", "AnimalShop.changelanguage");
		addDefault("commands.languagelist.description", "Shows all languages");
		addDefault("commands.languagelist.usage", "/animalshop languagelist");
		
		/* resetlanguage Command */
		addDefault("commands.resetlanguage.name", "resetlanguage");
		addDefault("commands.resetlanguage.permission", "AnimalShop.resetlanguage");
		addDefault("commands.resetlanguage.description", "Reactivates the automatically detection.");
		addDefault("commands.resetlanguage.usage", "/animalshop resetlanguage");
		addDefault("commands.resetlanguage.return", "Detection activated.");

		try
		{
			options().copyDefaults(true);
			save(configl);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			System.err.println("Couldnt save language arenaconfig!");
		}
	}
}
