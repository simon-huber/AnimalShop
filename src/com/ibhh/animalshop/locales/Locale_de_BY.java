package com.ibhh.animalshop.locales;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

public class Locale_de_BY extends PluginLocale implements PredefinedLocale
{

	public Locale_de_BY(String path)
	{
		super("de_BY", Localizer.getByCode("de_BY").getName());
		createFile(path);
	}

	@Override
	public void createFile(String path)
	{
		String a = "de_BY";
		File folder = new File(path + File.separator);
		folder.mkdirs();
		File configl = new File(path + File.separator + "language." + a + ".yml");
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

		addDefault("configuration.language.pathnotfound", "Mir ham in der \"language.%s.yml\" koane Ansage für \"%s\" gfunden! Mir müssen leider die gratlerte Standard Sprach' verwendn: ");
		addDefault("configuration.language.languagenotfound", "Mir ham die Konfigurationsdatei zur Sprache \"%s\" ned gfunden!");
		addDefault("configuration.language.list", "Die paar Sprachen sin installiert und können ausgwählt werdn mit: ");
		addDefault("configuration.language.listhover", "Klickn zum Ändern!");

		addDefault("help.head", "AnimalShop Hilfe");
		addDefault("help.commandsallowed", "Diese Befehle derfst machn:");
		addDefault("permission.error", "hey du derfst das ned!");
		
		addDefault("money.notenough", "Du hast nicht genug Geld!");
		addDefault("money.purchased", "Du hast %d bezahlt.");
		addDefault("money.purchased2", "Woah! Ein(e) %s! Sei freundlich zu ihm/ihr!");
		
		addDefault("sign.created", "AnimalShop erstellt!");
		addDefault("sign.failed", "Erstellung fehlgeschlagen!");

		addDefault("sign.notvalid.general", "Flasche Syntax: AnimalShop -> Preis -> Animalname -> Arguments");
		addDefault("sign.notvalid.firstline", "Die erste Zeile muss \"AnimalShop\" enthalten!");
		addDefault("sign.notvalid.secondline", "Die zweite Zeile muss eine Ganzzahl enthalten!");
		addDefault("sign.notvalid.thirdline", "Die dritte Zeile muss den Tiernamen enthalten! Animals: ");
		addDefault("sign.notvalid.fourthline", "Die vierte Zeile kann nur folgende Argumente enthalten: ");

		addDefault("animal.sheep.name", "Schaaf");
		addDefault("animal.sheep.color.WHITE", "weiß");
		addDefault("animal.sheep.color.SILVER", "silber");
		addDefault("animal.sheep.color.GRAY", "grau");
		addDefault("animal.sheep.color.BLACK", "schwarz");
		addDefault("animal.sheep.color.RED", "rot");
		addDefault("animal.sheep.color.BROWN", "braun");
		addDefault("animal.sheep.color.YELLOW", "gelb");
		addDefault("animal.sheep.color.LIME", "lime");
		addDefault("animal.sheep.color.GREEN", "grün");
		addDefault("animal.sheep.color.CYAN", "cyan");
		addDefault("animal.sheep.color.PINK", "pink");
		addDefault("animal.sheep.color.BLUE", "blau");
		addDefault("animal.sheep.color.LIGHT_BLUE", "hellblau");
		addDefault("animal.sheep.color.MAGENTA", "magenta");
		addDefault("animal.sheep.color.PURPLE", "purple");
		addDefault("animal.sheep.color.ORANGE", "orange");
		addDefault("animal.sheep.type.BABY", "baby");
		addDefault("animal.sheep.type.AGE_LOCK", "ewig");
		addDefault("animal.sheep.type.CUSTOM_NAME", "name:");
		addDefault("animal.sheep.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.cow.name", "Kuh");
		addDefault("animal.cow.type.BABY", "baby");
		addDefault("animal.cow.type.AGE_LOCK", "ewig");
		addDefault("animal.cow.type.CUSTOM_NAME", "name:");
		addDefault("animal.cow.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.mushroomcow.name", "Pilzkuh");
		addDefault("animal.mushroomcow.type.BABY", "baby");
		addDefault("animal.mushroomcow.type.AGE_LOCK", "ewig");
		addDefault("animal.mushroomcow.type.CUSTOM_NAME", "name:");
		addDefault("animal.mushroomcow.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		

		addDefault("animal.pig.name", "Schwein");
		addDefault("animal.pig.type.SADDLE", "sattel");
		addDefault("animal.pig.type.BABY", "baby");
		addDefault("animal.pig.type.AGE_LOCK", "ewig");
		addDefault("animal.pig.type.CUSTOM_NAME", "name:");
		addDefault("animal.pig.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.villager.name", "Villager");
		addDefault("animal.villager.type.BLACKSMITH", "Rüstungsschmied");
		addDefault("animal.villager.type.BUTCHER", "Fleischer");
		addDefault("animal.villager.type.FARMER", "Schäfer");
		addDefault("animal.villager.type.PRIEST", "Priester");
		addDefault("animal.villager.type.LIBRARIAN", "librarian");
		addDefault("animal.villager.type.BABY", "baby");
		addDefault("animal.villager.type.AGE_LOCK", "ewig");
		addDefault("animal.villager.type.CUSTOM_NAME", "name:");
		addDefault("animal.villager.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		

		addDefault("animal.wolf.name", "Wolf");
		addDefault("animal.wolf.color.WHITE", "weiß");
		addDefault("animal.wolf.color.SILVER", "silber");
		addDefault("animal.wolf.color.GRAY", "grau");
		addDefault("animal.wolf.color.BLACK", "schwarz");
		addDefault("animal.wolf.color.RED", "rot");
		addDefault("animal.wolf.color.BROWN", "braun");
		addDefault("animal.wolf.color.YELLOW", "gelb");
		addDefault("animal.wolf.color.LIME", "lime");
		addDefault("animal.wolf.color.GREEN", "grün");
		addDefault("animal.wolf.color.CYAN", "cyan");
		addDefault("animal.wolf.color.PINK", "pink");
		addDefault("animal.wolf.color.BLUE", "blau");
		addDefault("animal.wolf.color.LIGHT_BLUE", "hellblau");
		addDefault("animal.wolf.color.MAGENTA", "magenta");
		addDefault("animal.wolf.color.PURPLE", "purple");
		addDefault("animal.wolf.color.ORANGE", "orange");
		addDefault("animal.wolf.type.ANGRY", "verärgert");
		addDefault("animal.wolf.type.TAMED", "gezähmt");
		addDefault("animal.wolf.type.BABY", "baby");
		addDefault("animal.wolf.type.AGE_LOCK", "ewig");
		addDefault("animal.wolf.type.CUSTOM_NAME", "name:");
		addDefault("animal.wolf.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		

		addDefault("animal.ocelot.name", "Ocelot");
		addDefault("animal.ocelot.type.BLACK_CAT", "schwarz");
		addDefault("animal.ocelot.type.RED_CAT", "rot");
		addDefault("animal.ocelot.type.SIAMESE_CAT", "siamese");
		addDefault("animal.ocelot.type.WILD_OCELOT", "wild");
		addDefault("animal.ocelot.type.TAMED", "gezähmt");
		addDefault("animal.ocelot.type.BABY", "baby");
		addDefault("animal.ocelot.type.AGE_LOCK", "ewig");
		addDefault("animal.ocelot.type.CUSTOM_NAME", "name:");
		addDefault("animal.ocelot.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		
		addDefault("animal.horse.name", "Pferdt");
		addDefault("animal.horse.type.DONKEY", "donkey");
		addDefault("animal.horse.type.HORSE", "pferd");
		addDefault("animal.horse.type.MULE", "mule");
		addDefault("animal.horse.type.SKELETON_HORSE", "skelett");
		addDefault("animal.horse.type.UNDEAD_HORSE", "untot");
		addDefault("animal.horse.type.BLACK_DOTS", "black_dots");
		addDefault("animal.horse.type.NONE", "none");
		addDefault("animal.horse.type.WHITE", "st_white");
		addDefault("animal.horse.type.WHITE_DOTS", "white_dots");
		addDefault("animal.horse.type.WHITEFIELD", "whitefield");
		addDefault("animal.horse.color.WHITE", "weiß");
		addDefault("animal.horse.color.BROWN", "braun");
		addDefault("animal.horse.color.CHESTNUT", "chestnut");
		addDefault("animal.horse.color.CREAMY", "creamy");
		addDefault("animal.horse.color.DARK_BROWN", "dunkelbraun");
		addDefault("animal.horse.color.GRAY", "grau");
		addDefault("animal.horse.type.CHEST", "kiste");
		addDefault("animal.horse.type.TAMED", "gezähmt");
		addDefault("animal.horse.type.BABY", "baby");
		addDefault("animal.horse.type.AGE_LOCK", "ewig");
		addDefault("animal.horse.type.CUSTOM_NAME", "name:");
		addDefault("animal.horse.type.CUSTOM_NAME_INVISIBLE", "nainvis");
		

		/* changelanguage Command */
		addDefault("commands.changelanguage.name", "ichverstehbayrischnicht");
		addDefault("commands.changelanguage.permission", "AnimalShop.changelanguage");
		addDefault("commands.changelanguage.description", "ändert die Sprache");
		addDefault("commands.changelanguage.usage", "/animalshop ichverstehbayrischnicht [alternativesprache]");
		addDefault("commands.changelanguage.return", "Die Sprache wurde geändert.");

		/* sprachliste Command */
		addDefault("commands.languagelist.name", "alternativesprachen");
		addDefault("commands.languagelist.permission", "AnimalShop.changelanguage");
		addDefault("commands.languagelist.description", "Zeigt alle Sprachen");
		addDefault("commands.languagelist.usage", "/animalshop alternativesprachen");

		/* resetlanguage Command */
		addDefault("commands.resetlanguage.name", "sprachezurücksetzen");
		addDefault("commands.resetlanguage.permission", "AnimalShop.resetlanguage");
		addDefault("commands.resetlanguage.description", "Reaktiviert die automatische Erkennung.");
		addDefault("commands.resetlanguage.usage", "/animalshop sprachezurücksetzen");
		addDefault("commands.resetlanguage.return", "Automatische Erkennung aktiviert.");
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
