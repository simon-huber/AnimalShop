package me.ibhh.AnimalShop;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
//import org.bukkit.entity.Sheep;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Simon
 * 
 */
public class AnimalShop extends JavaPlugin 
{
	public float Version = 0;
	public static PluginManager pm;
	private PermissionsHandler Permission;
	private iConomyHandler iConomy;
	private ShopPlayerListener playerListener;
	private ShopBlockListener blockListener;


	/**
	 * Called by Bukkit on stopping the server
	 * 
	 * @param
	 * @return
	 */
	@Override
	public void onDisable() {
		System.out.println("[AnimalShop] disabled!");

	}

	public boolean UpdateConfig() {
		try {
			getConfig().options().copyDefaults(true);
			saveConfig();
			reloadConfig();
			System.out.println("[AnimalShop] Config file found!");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Called by Bukkit on starting the server
	 * 
	 * @param
	 * @return
	 */
	@Override
	public void onEnable() 
	{
		pm = getServer().getPluginManager();
		//Register our events
	    this.playerListener = new ShopPlayerListener(this);
	    this.blockListener = new ShopBlockListener(this);
		registerEvents();
		UpdateConfig();
		iConomy = new iConomyHandler(this);
		Permission = new PermissionsHandler(this);
		if(iConomy != null)
		{
			System.out.println("[AnimalShop] iConomyHandler successfully enabled!");
		}
		if(Permission != null)
		{
			System.out.println("[AnimalShop] PermissionsHandler successfully enabled!");
		}
		//		Permission = new PermissionsHandler(this);
		try {
			aktuelleVersion();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			iConomyHandler.iConomyversion();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("[AnimalShop] Version: " + Version
				+ " successfully enabled!");

		String URL = "http://ibhh.de:80/aktuelleversionAnimalShop.html";
		if ((Update.UpdateAvailable(URL, Version) == true)) {
			System.out.println("[AnimalShop] New version: "
					+ Update.getNewVersion(URL) + " found!");
			System.out
			.println("[AnimalShop] ******************************************");
			System.out
			.println("[AnimalShop] *********** Please update!!!! ************");
			System.out
			.println("[AnimalShop] * http://ibhh.de/AnimalShop.jar *");
			System.out
			.println("[AnimalShop] ******************************************");
			if (getConfig().getBoolean("autodownload") == true) {
				try {
					String path = getDataFolder().toString() + "/Update/";
					Update.autoUpdate("http://ibhh.de/AnimalShop.jar", path,
							"AnimalShop.jar");
				} catch (Exception e) {
					System.out
					.println("[AnimalShop] "
							+ "Error on checking permissions with PermissionsEx!");
					e.printStackTrace();
					return;
				}

			} else {
				System.out
				.println("[AnimalShop] Please type [AnimalShop download] to download manual! ");
			}
		}
	}

	public String getPermissionsError()
	{
		String Error = ChatColor.GRAY + "[AnimalShop] " + ChatColor.RED + (getConfig().get("permissionserror"));
		return Error;
	}

	////////////////// REGISTER EVENTS & SCHEDULER ///////////////////////////
	private void registerEvents() {
		registerEvent(Event.Type.BLOCK_BREAK, blockListener);
		registerEvent(Event.Type.BLOCK_PLACE, blockListener);
		registerEvent(Event.Type.SIGN_CHANGE, blockListener);
		registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Highest);
		System.out
		.println("[AnimalShop] Events registered!");
	}

	private void registerEvent(Event.Type type, Listener listener) {
		registerEvent(type, listener, Event.Priority.Normal);
	}

	private void registerEvent(Event.Type type, Listener listener, Event.Priority priority) {
		pm.registerEvent(type, listener, priority, this);
	}



	public boolean blockIsValid(Sign s) {
		boolean a = false;
		if((s.getLine(0).equals("[AnimalShop]")) && (s.getLine(3).equals(getConfig().get("Sheep")) || s.getLine(3).equals(getConfig().get("Cow")) || s.getLine(3).equals(getConfig().get("Chicken")) || s.getLine(3).equals(getConfig().get("Pig"))))
			a = true;
		//		if((s.getLine(0).equals("[AnimalShop]")) && (s.getLine(3).equals("Sheep") || s.getLine(3).equals("Cow") || s.getLine(3).equals("Chicken") || s.getLine(3).equals("Pig")))
		//			a = true;
		return a;
	}

	/**
	 * Gets version.
	 * 
	 * @param
	 * @return float: Version of the installed plugin.
	 */
	public float aktuelleVersion() {
		try {
			Version = Float.parseFloat(getDescription().getVersion());
		} catch (Exception e) {
			System.out
			.println("[AnimalShop]Could not parse version in float");
		}
		return Version;
	}


	/**
	 * Called by Bukkit on reloading the server
	 * 
	 * @param
	 * @return
	 */
	public void onReload() {
		onEnable();
	}

	public void spawnAnimal(Player p, String Animal)
	{
		System.out
		.println("[AnimalShop]Spawn before");
		if(Animal.equals(getConfig().get("Sheep")))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.SHEEP);
			//			Sheep Sheep = (Sheep)p.getWorld().spawnCreature(p.getLocation(), CreatureType.SHEEP);
			System.out
			.println("[AnimalShop]Spawn");
		}
		else if(Animal.equals(getConfig().get("Cow")))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.COW);

		}
		else if(Animal.equals(getConfig().get("Chicken")))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.CHICKEN);
		}
		else if(Animal.equals(getConfig().get("Pig")))
		{
			p.getWorld().spawnCreature(p.getLocation(), CreatureType.PIG);
		}
		p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Woah! A " + Animal + "! You should befriend it!");
	}
	protected static boolean isConsole(CommandSender sender) {
		return !(sender instanceof Player);
	}

	/**
	 * Called by Bukkit if player posts a command
	 * 
	 * @param none
	 *            , cause of Bukkit.
	 * @return true if no errors happened else return false to Bukkit.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (sender instanceof Player) {
			//			Player player = (Player) sender;
			//			action = args[0];
			if (cmd.getName().equalsIgnoreCase("AnimalShop")) 
			{
				return false;
			}
		} else {
			if (isConsole(sender)) {
				if (cmd.getName().equalsIgnoreCase("AnimalShop")) {
					if (args.length == 1) {
						if (args[0].equals("download")) {
							String path = getDataFolder().toString()
									+ "/Update/";
							Update.autoUpdate(
									"http://ibhh.de/AnimalShop.jar", path,
									"AnimalShop.jar");
						}
					}
				}
			}
		}
		return false;
	}

}

