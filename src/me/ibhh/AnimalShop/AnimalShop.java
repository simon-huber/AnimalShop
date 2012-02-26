package me.ibhh.AnimalShop;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimalShop extends JavaPlugin {

    public float Version = 0;
    public static PluginManager pm;
    private PermissionsHandler Permission;
    private iConomyHandler iConomy;
    private ShopPlayerListener ShopListener;
    public boolean debug;
    public static String PrefixConsole = "[xpShop] ";
    public static String Prefix = "[xpShop] ";
    public Update upd;
    public boolean blacklisted;

    @Override
    public void onDisable() {
        System.out.println("[AnimalShop] disabled!");
                String URL = "http://ibhh.de:80/aktuelleversion" + this.getDescription().getName() + ".html";
        if ((UpdateAvailable(URL, Version) == true)) {
            Logger("New version: " + Update.getNewVersion(URL) + " found!", "Warning");
            Logger("******************************************", "Warning");
            Logger("*********** Please update!!!! ************", "Warning");
            Logger("* http://ibhh.de/AnimalShop.jar *", "Warning");
            Logger("******************************************", "Warning");
            if (getConfig().getBoolean("autodownload") == true) {
                try {
                    String path = "plugins" + "\\";
                    if (autoUpdate("http://ibhh.de/AnimalShop.jar", path, "AnimalShop.jar", "forceupdate")) {
                        Logger("Downloaded new Version!", "Warning");
                        Logger("AnimalShop will be updated on the next restart!", "Warning");
                    } else {
                        Logger(" Cant download new Version!", "Warning");
                    }
                } catch (Exception e) {
                    Logger("Error on donwloading new Version!", "Error");
                    e.printStackTrace();
                }
            } else {
                Logger("Please type [AnimalShop download] to download manual! ", "Warning");
            }
        }
    }

    public boolean UpdateConfig() {
        try {
            getConfig().options().copyDefaults(true);
            if (debug) {
                System.out.println("[AnimalShop] Debug: copyDefaults into config!");
            }
            saveConfig();
            if (debug) {
                System.out.println("[AnimalShop] Debug: Saving config!");
            }
            reloadConfig();
            if (debug) {
                System.out.println("[AnimalShop] Debug: Reload config!");
            }
            System.out.println("[AnimalShop] Config file found!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[AnimalShop] Debug: Reload config!" + e.getMessage());
            if (debug) {
                System.out.println("[AnimalShop] Debug: Error on loading config!" + e.getMessage() + " Unknown error, please report!");
            }
        }
        return false;
    }

    @Override
    public void onEnable() {
        UpdateConfig();
        debug = getConfig().getBoolean("debug");
        try {
            pm = getServer().getPluginManager();
            if (debug) {
                System.out.println("[AnimalShop] Debug: Pluginmanager found!");
            }
        } catch (Exception e) {
            System.out.println("[AnimalShop] Debug: Loading pluginmanager failed!" + e.getMessage());
        }
        ShopListener = new ShopPlayerListener(this);

        if (debug) {
            System.out.println("[AnimalShop] Debug: Blocklistener objekt created!");
        }
        System.out.println("[AnimalShop] Debug: Playerlistener objekt created!");
        if (debug) {
            System.out.println("[AnimalShop] Debug: Events registered!");
        }

        if (debug) {
            System.out.println("[AnimalShop] Debug: Config updated and reloaded!");
        }
        this.iConomy = new iConomyHandler(this);
        this.Permission = new PermissionsHandler(this);
        upd = new Update(this);
        if (debug) {
            System.out.println("[AnimalShop] Debug: iConomyhandler objekt created!");
        }
        System.out.println("[AnimalShop] Debug: PermissionsHandler objekt created!");
        if (this.iConomy != null) {
            System.out.println("[AnimalShop] iConomyHandler successfully enabled!");
        }
        if (this.Permission != null) {
            System.out.println("[AnimalShop] PermissionsHandler successfully enabled!");
        }
        try {
            aktuelleVersion();
            if (debug) {
                System.out.println("[AnimalShop] Debug: Version checked!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (debug) {
                System.out.println("[AnimalShop] Debug: Version couldnt be found in plugin.yml!" + e.getMessage());
            }
            onDisable();
            return;
        }
        try {
            iConomy = new iConomyHandler(this);
            iConomy.iConomyversion();
            if (debug) {
                System.out.println("[AnimalShop] Debug: Money plugin checked!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (debug) {
                System.out.println("[AnimalShop] Debug: Money plugin check failed!" + e.getMessage());
            }
            onDisable();
            return;
        }

        System.out.println("[AnimalShop] Version: " + this.Version
                + " successfully enabled!");
    }
    
    
    /**
     * Delete an download new version of xpShop in the Update folder.
     *
     * @param
     * @return
     */
    public boolean autoUpdate(String url, String path, String name, String type) {
        try {
            Update.autoDownload(url, path, name, type);
            return true;
        } catch (Exception e) {
            Logger(e.getMessage(), "Error");
            try {
                Update.autoDownload(url, path + "xpShop\\", name, type);
                return true;
            } catch (Exception ex) {
                Logger(ex.getMessage(), "Error");
                return false;
            }
        }
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
            Logger("Could not parse version in float", "");
        }
        return Version;
    }

    /**
     * Compares Version to newVersion
     *
     * @param url from newVersion file + currentVersion
     * @return true if newVersion recommend.
     */
    public boolean UpdateAvailable(String url, float currVersion) {
        boolean a = false;
        if (Update.getNewVersion(url) > currVersion) {
            a = true;
        }
        return a;
    }

    public String getPermissionsError() {
        String Error = ChatColor.GRAY + "[AnimalShop] " + ChatColor.RED + getConfig().getString("permissionserror");
        return Error;
    }

    public static void Logger(String msg, String TYPE) {
        if (TYPE.equalsIgnoreCase("Warning") || TYPE.equalsIgnoreCase("Error")) {
            System.err.println(PrefixConsole + TYPE + ": " + msg);
        } else if (TYPE.equalsIgnoreCase("Debug")) {
            System.out.println(PrefixConsole + "Debug: " + msg);
        } else {
            System.out.println(PrefixConsole + msg);
        }
    }

    public static void PlayerLogger(Player p, String msg, String TYPE) {
        if (TYPE.equalsIgnoreCase("Error")) {
            p.sendMessage(ChatColor.DARK_BLUE + Prefix + ChatColor.GOLD + "Error: " + msg);
        } else {
            p.sendMessage(ChatColor.DARK_BLUE + Prefix + ChatColor.GOLD + msg);
        }
    }

    public boolean blockIsValid(String[] line, Player p) {
        boolean a = false;
        if ((line[0].equalsIgnoreCase("[AnimalShop]"))
                && (Tools.isInteger(line[1]))
                && ((line[2].equalsIgnoreCase(getConfig().getString("Sheep." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("Cow." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("Chicken." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("Pig." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("Villager." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("MushroomCow." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("Wolf." + getConfig().getString("language")))))) {
            a = true;
        } else 
            if ((line[0].equalsIgnoreCase("[AnimalShop]"))) {
                if (!(Tools.isInteger(line[1]))) {
                    p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 2 isnt a number");
                }
                if (!(line[2].equalsIgnoreCase(getConfig().getString("Sheep." + getConfig().getString("language"))))) {
                    p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Sheep." + getConfig().getString("language")));
                }
                if (!(line[2].equalsIgnoreCase(getConfig().getString("Cow." + getConfig().getString("language"))))) {
                    p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Cow." + getConfig().getString("language")));
                }
                if (!(line[2].equalsIgnoreCase(getConfig().getString("Chicken." + getConfig().getString("language"))))) {
                    p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Chicken." + getConfig().getString("language")));
                }
                if (!(line[2].equalsIgnoreCase(getConfig().getString("Pig." + getConfig().getString("language"))))) {
                    p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Pig." + getConfig().getString("language")));
                }
                if (!(line[2].equalsIgnoreCase(getConfig().getString("Villager." + getConfig().getString("language"))))) {
                    p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Villager." + getConfig().getString("language")));
                }
                if (!(line[2].equalsIgnoreCase(getConfig().getString("MushroomCow." + getConfig().getString("language"))))) {
                    p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("MushroomCow." + getConfig().getString("language")));
                }
                if (!((line[2].equalsIgnoreCase(getConfig().getString("Wolf." + getConfig().getString("language")))))) {
                    p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Wolf." + getConfig().getString("language")));
                }
                if (debug) {
                    System.out.println("[AnimalShop] Animal: " + getConfig().getString("Sheep." + getConfig().getString("language")));
                    System.out.println("[AnimalShop] Animal: " + getConfig().getString("Cow." + getConfig().getString("language")));
                    System.out.println("[AnimalShop] Animal: " + getConfig().getString("Chicken." + getConfig().getString("language")));
                    System.out.println("[AnimalShop] Animal: " + getConfig().getString("Pig." + getConfig().getString("language")));
                    System.out.println("[AnimalShop] Animal: " + getConfig().getString("Villager." + getConfig().getString("language")));
                    System.out.println("[AnimalShop] Animal: " + getConfig().getString("MushroomCow." + getConfig().getString("language")));
                    System.out.println("[AnimalShop] Animal: " + getConfig().getString("Wolf." + getConfig().getString("language")));
                }
            }
        return a;
    }

    public void onReload() {
        onDisable();
        onEnable();
    }

    public void spawnAnimal(Player p, String Animal) {
        if (Animal.equalsIgnoreCase(getConfig().getString("Sheep." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), CreatureType.SHEEP);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Cow." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), CreatureType.COW);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Chicken." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), CreatureType.CHICKEN);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Pig." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), CreatureType.PIG);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("MushroomCow." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), CreatureType.MUSHROOM_COW);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Villager." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), CreatureType.VILLAGER);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Villager." + getConfig().getString("language")))) {
            Wolf w = (Wolf) p.getWorld().spawnCreature(p.getLocation(), CreatureType.WOLF);
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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (debug) {
            System.out.println("[AnimalShop] Debug: Command get!");
        }
        if ((sender instanceof Player)) {
            if (debug) {
                System.out.println("[AnimalShop] Debug: Commandsender instance of player!");
            }
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("AnimalShop")) {
                if (debug) {
                    System.out.println("[AnimalShop] Debug: Command: AnimalShop!");
                }
                if (args.length == 1) {
                    if (debug) {
                        System.out.println("[AnimalShop] Debug: args.lenght == 1!");
                    }
                    if (args[0].equalsIgnoreCase("list")) {
                        if (debug) {
                            System.out.println("[AnimalShop] Debug: Command equalsIgnoreCase list!");
                        }
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
        } else if ((isConsole(sender))
                && (cmd.getName().equalsIgnoreCase("AnimalShop"))
                && (args.length == 1)
                && (args[0].equalsIgnoreCase("download"))) {
            try {
                String path = "plugins" + "\\";
                autoUpdate("http://ibhh.de/AnimalShop.jar", path, "AnimalShop.jar", "forceupdate");
            } catch (Exception e) {
                System.out.println("[AnimalShop] Debug: Manual download failed!" + e.getMessage());
            }
        }
        return false;
    }
}