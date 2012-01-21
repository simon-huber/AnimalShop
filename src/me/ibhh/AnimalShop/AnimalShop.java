package me.ibhh.AnimalShop;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimalShop extends JavaPlugin
{
	public float Version = 0;
	public static PluginManager pm;
	private PermissionsHandler Permission;
	private iConomyHandler iConomy;
	private ShopPlayerListener playerListener;
	private ShopBlockListener blockListener;
        private boolean debug;

    @Override
	public void onDisable()
	{
		System.out.println("[AnimalShop] disabled!");
	}

	public boolean UpdateConfig()
	{
		try {
			getConfig().options().copyDefaults(true);
                        if(debug)
                           System.out.println("[AnimalShop] Debug: copyDefaults into config!");
			saveConfig();
                        if(debug)
                           System.out.println("[AnimalShop] Debug: Saving config!");
			reloadConfig();
                        if(debug)
                           System.out.println("[AnimalShop] Debug: Reload config!");
			System.out.println("[AnimalShop] Config file found!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
                        System.out.println("[AnimalShop] Debug: Reload config!" + e.getMessage());
                        if(debug)
                           System.out.println("[AnimalShop] Debug: Error on loading config!" + e.getMessage() + " Unknown error, please report!");
		}return false;
	}

    @Override
	public void onEnable()
	{
            UpdateConfig();
            debug = getConfig().getBoolean("debug");
            try
            {
		pm = getServer().getPluginManager();
                if(debug)
                   System.out.println("[AnimalShop] Debug: Pluginmanager found!");
            }
            catch (Exception e)
                    {
                           System.out.println("[AnimalShop] Debug: Loading pluginmanager failed!" + e.getMessage());
                    }
		this.playerListener = new ShopPlayerListener(this);
		this.blockListener = new ShopBlockListener(this);
                if(debug)
                   System.out.println("[AnimalShop] Debug: Blocklistener objekt created!");
                   System.out.println("[AnimalShop] Debug: Playerlistener objekt created!");
		registerEvents();
                if(debug)
                    System.out.println("[AnimalShop] Debug: Events registered!");
		
                if(debug)
                    System.out.println("[AnimalShop] Debug: Config updated and reloaded!");
		this.iConomy = new iConomyHandler(this);
		this.Permission = new PermissionsHandler(this);
                if(debug)
                   System.out.println("[AnimalShop] Debug: iConomyhandler objekt created!");
                   System.out.println("[AnimalShop] Debug: PermissionsHandler objekt created!");
		if (this.iConomy != null)
		{
			System.out.println("[AnimalShop] iConomyHandler successfully enabled!");
		}
		if (this.Permission != null)
		{
			System.out.println("[AnimalShop] PermissionsHandler successfully enabled!");
		}
		try
		{
			aktuelleVersion();
                        if(debug)
                           System.out.println("[AnimalShop] Debug: Version checked!");
		} catch (Exception e) {
			e.printStackTrace();
                        if(debug)
                            System.out.println("[AnimalShop] Debug: Version couldnt be found in plugin.yml!" + e.getMessage());
                        onDisable();
                        return;
		}
		try
		{
			iConomyHandler.iConomyversion();
                        if(debug)
                             System.out.println("[AnimalShop] Debug: Money plugin checked!");
		} catch (Exception e) {
			e.printStackTrace();
                        if(debug)
                            System.out.println("[AnimalShop] Debug: Money plugin check failed!" + e.getMessage());
                        onDisable();
                        return;
		}

		System.out.println("[AnimalShop] Version: " + this.Version + 
				" successfully enabled!");

		String URL = "http://ibhh.de:80/aktuelleversionAnimalShop.html";
		if (Update.UpdateAvailable(URL, this.Version)) {
			System.out.println("[AnimalShop] New version: " + 
					Update.getNewVersion(URL) + " found!");
			System.out
			.println("[AnimalShop] ******************************************");
			System.out
			.println("[AnimalShop] *********** Please update!!!! ************");
			System.out
			.println("[AnimalShop] * http://ibhh.de/AnimalShop.jar *");
			System.out
			.println("[AnimalShop] ******************************************");
			if (getConfig().getBoolean("autodownload")) {
				try {
					String path = getDataFolder().toString() + "/Update/";
					Update.autoUpdate("http://ibhh.de/AnimalShop.jar", path, 
							"AnimalShop.jar");
				} catch (Exception e) {
					System.out.println("[AnimalShop] Error on autoupdating!" + e.getMessage());
					e.printStackTrace();
                                        onDisable();
					return;
				}
			}
			else
				System.out.println("[AnimalShop] Please type [AnimalShop download] to download manual! ");
		}
	}

	public String getPermissionsError()
	{
		String Error = ChatColor.GRAY + "[AnimalShop] " + ChatColor.RED + getConfig().getString("permissionserror");
		return Error;
	}

	private void registerEvents()
	{
	    pm.registerEvent(Event.Type.SIGN_CHANGE, this.blockListener, Event.Priority.Normal, this);
            if(debug)
               System.out.println("[AnimalShop] Debug: blocklistener registered!");
	    pm.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, Event.Priority.Normal, this);
            if(debug)
               System.out.println("[AnimalShop] Debug: playerlistener registered!");
	    pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.Normal, this);
            if(debug)
               System.out.println("[AnimalShop] Debug: blocklistener registered!");
	    pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener, Event.Priority.Normal, this);
            if(debug)
               System.out.println("[AnimalShop] Debug: blocklistener registered!");
            System.out.println("[AnimalShop] Events registered!");
	}


	public boolean blockIsValid(Sign s, String von, Player p)
	{
		boolean a = false;
		if ((s.getLine(0).equalsIgnoreCase("[AnimalShop]"))
                           && (Tools.isInteger(s.getLine(1)))
                           && ((s.getLine(2).equalsIgnoreCase(getConfig().getString("Sheep."+ getConfig().getString("language")))) 
                           || (s.getLine(2).equalsIgnoreCase(getConfig().getString("Cow."+ getConfig().getString("language"))))
                           || (s.getLine(2).equalsIgnoreCase(getConfig().getString("Chicken."+ getConfig().getString("language"))) )
                           || (s.getLine(2).equalsIgnoreCase(getConfig().getString("Pig."+ getConfig().getString("language"))))
                           || (s.getLine(2).equalsIgnoreCase(getConfig().getString("Villager."+ getConfig().getString("language"))))
                           || (s.getLine(2).equalsIgnoreCase(getConfig().getString("MushroomCow."+ getConfig().getString("language"))))
                           || (s.getLine(2).equalsIgnoreCase(getConfig().getString("Wolf."+ getConfig().getString("language"))))))
                {
			a = true;
		}
                else
                {
                    if ((s.getLine(0).equalsIgnoreCase("[AnimalShop]")) && (von.equalsIgnoreCase("create") || von.equalsIgnoreCase("Interact")))
                    {
                       if(!(Tools.isInteger(s.getLine(1))))
                           p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 2 isnt a number");
                       if(!(s.getLine(2).equalsIgnoreCase(getConfig().getString("Sheep"))))
                           p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Sheep."+ getConfig().getString("language")));
                       if(!(s.getLine(2).equalsIgnoreCase(getConfig().getString("Cow"))))
                           p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Cow."+ getConfig().getString("language")));
                       if(!(s.getLine(2).equalsIgnoreCase(getConfig().getString("Chicken"))))
                           p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Chicken."+ getConfig().getString("language")));
                       if(!(s.getLine(2).equalsIgnoreCase(getConfig().getString("Pig"))))
                           p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Pig."+ getConfig().getString("language")));
                       if(!(s.getLine(2).equalsIgnoreCase(getConfig().getString("Villager"))))
                           p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Villager."+ getConfig().getString("language")));
                       if(!(s.getLine(2).equalsIgnoreCase(getConfig().getString("MushroomCow"))))
                           p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("MushroomCow."+ getConfig().getString("language")));
                       if(!(s.getLine(2).equalsIgnoreCase(getConfig().getString("Wolf"))))
                           p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Wolf."+ getConfig().getString("language")));
                       if(debug)
                       {
                       System.out.println("[AnimalShop] Animal: " + getConfig().getString("Sheep."+ getConfig().getString("language")));
                       System.out.println("[AnimalShop] Animal: " + getConfig().getString("Cow."+ getConfig().getString("language")));
                       System.out.println("[AnimalShop] Animal: " + getConfig().getString("Chicken."+ getConfig().getString("language")));
                       System.out.println("[AnimalShop] Animal: " + getConfig().getString("Pig."+ getConfig().getString("language")));
                       System.out.println("[AnimalShop] Animal: " + getConfig().getString("Villager."+ getConfig().getString("language")));
                       System.out.println("[AnimalShop] Animal: " + getConfig().getString("MushroomCow."+ getConfig().getString("language")));
                       System.out.println("[AnimalShop] Animal: " + getConfig().getString("Wolf."+ getConfig().getString("language")));
                       }
                    }
                }
		return a;
	}

	public float aktuelleVersion()
	{
		try
		{
			this.Version = Float.parseFloat(getDescription().getVersion());
		} catch (Exception e) {
			System.out.println("[AnimalShop]Could not parse version in float" + e.getMessage());
		}
		return this.Version;
	}

	public void onReload()
	{
                onDisable();
		onEnable();
	}

	public void spawnAnimal(Player p, String Animal)
	{
		if (Animal.equalsIgnoreCase(getConfig().getString("Sheep." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.SHEEP);
		}
		else if (Animal.equalsIgnoreCase(getConfig().getString("Cow." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.COW);
		}
		else if (Animal.equalsIgnoreCase(getConfig().getString("Chicken." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.CHICKEN);
		}
		else if (Animal.equalsIgnoreCase(getConfig().getString("Pig." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.PIG);
		}
                else if (Animal.equalsIgnoreCase(getConfig().getString("MushroomCow." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.MUSHROOM_COW);
                }
                else if (Animal.equalsIgnoreCase(getConfig().getString("Villager." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.VILLAGER);
		}
                else if (Animal.equalsIgnoreCase(getConfig().getString("Villager." + getConfig().getString("language"))))
		{
			Wolf w = (Wolf)p.getWorld().spawnCreature(p.getLocation(), CreatureType.WOLF);
                        w.setHealth(20);
                        w.setOwner(p);
                        w.setSitting(false);
		}
		p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Woah! A " + Animal + "! You should befriend it!");
	}
	protected static boolean isConsole(CommandSender sender) {
		return !(sender instanceof Player);
	}

    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
           if(debug)
               System.out.println("[AnimalShop] Debug: Command get!");
		if ((sender instanceof Player))
		{
                    if(debug)
                         System.out.println("[AnimalShop] Debug: Commandsender instance of player!");
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("AnimalShop"))
			{
                             if(debug)
                                    System.out.println("[AnimalShop] Debug: Command: AnimalShop!");
				if(args.length == 1)
				{
                                    if(debug)
                                            System.out.println("[AnimalShop] Debug: args.lenght == 1!");
					if(args[0].equalsIgnoreCase("list"))
					{
                                                if(debug)
                                                    System.out.println("[AnimalShop] Debug: Command equalsIgnoreCase list!");
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "List:");
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().getString("Sheep." + getConfig().getString("language")));
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().getString("Cow." + getConfig().getString("language")));
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().getString("Chicken." + getConfig().getString("language")));
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().getString("Pig." + getConfig().getString("language")));
                                                player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().getString("MushroomCow." + getConfig().getString("language")));
                                                player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().getString("Villager." + getConfig().getString("language")));
                                                player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().getString("Wolf." + getConfig().getString("language")));
					}
				}
				return false;
			}
		}
		else if ((isConsole(sender)) && 
				(cmd.getName().equalsIgnoreCase("AnimalShop")) && 
				(args.length == 1) && 
				(args[0].equalsIgnoreCase("download"))) {
                    try
                    {
			String path = getDataFolder().toString() + "/Update/";
			Update.autoUpdate("http://ibhh.de/AnimalShop.jar", path, "AnimalShop.jar");
                    }
                    catch(Exception e)
                    {
                       System.out.println("[AnimalShop] Debug: Manual download failed!" + e.getMessage());
                    }
		}
		return false;
	}
}