package me.ibhh.AnimalShop;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimalShop extends JavaPlugin
{
	public float Version = 0.0F;
	public static PluginManager pm;
	private PermissionsHandler Permission;
	private iConomyHandler iConomy;
	private ShopPlayerListener playerListener;
	private ShopBlockListener blockListener;

	public void onDisable()
	{
		System.out.println("[AnimalShop] disabled!");
	}

	public boolean UpdateConfig()
	{
		try {
			getConfig().options().copyDefaults(true);
			saveConfig();
			reloadConfig();
			System.out.println("[AnimalShop] Config file found!");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}return false;
	}

	public void onEnable()
	{
		pm = getServer().getPluginManager();

		this.playerListener = new ShopPlayerListener(this);
		this.blockListener = new ShopBlockListener(this);
		registerEvents();
		UpdateConfig();
		this.iConomy = new iConomyHandler(this);
		this.Permission = new PermissionsHandler(this);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		try
		{
			iConomyHandler.iConomyversion();
		} catch (Exception e) {
			e.printStackTrace();
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
					System.out
					.println("[AnimalShop] Error on checking permissions with PermissionsEx!");

					e.printStackTrace();
					return;
				}
			}
			else
				System.out
				.println("[AnimalShop] Please type [AnimalShop download] to download manual! ");
		}
	}

	public String getPermissionsError()
	{
		String Error = ChatColor.GRAY + "[AnimalShop] " + ChatColor.RED + getConfig().get("permissionserror");
		return Error;
	}

	private void registerEvents()
	{
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvent(Event.Type.SIGN_CHANGE, this.blockListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener, Event.Priority.Normal, this);
		System.out
		.println("[AnimalShop] Events registered!");
	}


	public boolean blockIsValid(Sign s)
	{
		boolean a = false;
		if ((s.getLine(0).equals("[AnimalShop]")) && ((s.getLine(2).equals(getConfig().get("Sheep"))) || (s.getLine(2).equals(getConfig().get("Cow"))) || (s.getLine(2).equals(getConfig().get("Chicken"))) || (s.getLine(2).equals(getConfig().get("Pig"))))) {
			a = true;
		}

		return a;
	}

	public float aktuelleVersion()
	{
		try
		{
			this.Version = Float.parseFloat(getDescription().getVersion());
		} catch (Exception e) {
			System.out
			.println("[AnimalShop]Could not parse version in float");
		}
		return this.Version;
	}

	public void onReload()
	{
		onEnable();
	}

	public void spawnAnimal(Player p, String Animal)
	{
		if (Animal.equals(getConfig().get("Sheep." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.SHEEP);
		}
		else if (Animal.equals(getConfig().get("Cow." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.COW);
		}
		else if (Animal.equals(getConfig().get("Chicken." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.CHICKEN);
		}
		else if (Animal.equals(getConfig().get("Pig." + getConfig().getString("language"))))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.PIG);
		}
		p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Woah! A " + Animal + "! You should befriend it!");
	}
	protected static boolean isConsole(CommandSender sender) {
		return !(sender instanceof Player);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if ((sender instanceof Player))
		{
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("AnimalShop"))
			{
				if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("list"))
					{
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "List:");
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().get("Sheep." + getConfig().getString("language")));
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().get("Cow." + getConfig().getString("language")));
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().get("Chicken." + getConfig().getString("language")));
						player.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + getConfig().get("Pig." + getConfig().getString("language")));
					}
				}
				return false;
			}
		}
		else if ((isConsole(sender)) && 
				(cmd.getName().equalsIgnoreCase("AnimalShop")) && 
				(args.length == 1) && 
				(args[0].equals("download"))) {
			String path = getDataFolder().toString() + 
					"/Update/";
			Update.autoUpdate(
					"http://ibhh.de/AnimalShop.jar", path, 
					"AnimalShop.jar");
		}
		return false;
	}
}