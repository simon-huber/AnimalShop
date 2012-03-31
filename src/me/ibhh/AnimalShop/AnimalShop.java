package me.ibhh.AnimalShop;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimalShop extends JavaPlugin {

    public float Version = 0;
    public static PluginManager pm;
    private ShopPlayerListener ShopListener;
    public boolean debug;
    public static String PrefixConsole = "[AnimalShop] ";
    public static String Prefix = "[AnimalShop] ";
    public Update upd;
    public boolean blacklisted;
    public iConomyHandler MoneyHandler;
    public PermissionsChecker permissionsChecker;

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
                    String path = "plugins" + File.separator;
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
            debug = getConfig().getBoolean("debug");
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
            if (debug) {
                System.out.println("[AnimalShop] Config file found!");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (debug) {
                System.out.println("[AnimalShop] Debug: Reload config!" + e.getMessage());
            }
            if (debug) {
                System.out.println("[AnimalShop] Debug: Error on loading config!" + e.getMessage() + " Unknown error, please report!");
            }
        }
        return false;
    }

    @Override
    public void onEnable() {
        UpdateConfig();
        try {
            pm = getServer().getPluginManager();
            if (debug) {
                System.out.println("[AnimalShop] Debug: Pluginmanager found!");
            }
        } catch (Exception e) {
            System.out.println("[AnimalShop] Debug: Loading pluginmanager failed!" + e.getMessage());
        }
        ShopListener = new ShopPlayerListener(this);
        MoneyHandler = new iConomyHandler(this);
        permissionsChecker = new PermissionsChecker(this, "AnimalShop");
        if (debug) {
            System.out.println("[AnimalShop] Debug: Blocklistener objekt created!");
        }
        if (debug) {
            System.out.println("[AnimalShop] Debug: Playerlistener objekt created!");
        }
        if (debug) {
            System.out.println("[AnimalShop] Debug: Events registered!");
        }

        if (debug) {
            System.out.println("[AnimalShop] Debug: Config updated and reloaded!");
        }
        upd = new Update(this);
        if (debug) {
            System.out.println("[AnimalShop] Debug: iConomyhandler objekt created!");
        }
        if (debug) {
            System.out.println("[AnimalShop] Debug: PermissionsHandler objekt created!");
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

//        if (this.getServer().getPluginManager().getPlugin("PermissionsHandler") == null) {
//            String path = "plugins" + File.separator;
//            Logger("Download PermissionsHandler !", "Warning");
//            try {
//                upd.autoDownload("http://ibhh.de/PermissionsHandler.jar", path, "PermissionsHandler.jar", "forceupdate");
//            } catch (Exception ex) {
//                Logger("ibhhs server currently not aviable!", "Error");
//            }
//            Logger("Downloaded PermissionsHandler successfully!", "Warning");
//            Logger(".. Done!", "Warning");
//            Logger("Restart the server to enable Permissions support, other wise it wont work!", "Error");
//            this.setEnabled(false);
//            onDisable();
//            System.out.println("[AnimalShop] disabled!");
//        }
//        if (this.getServer().getPluginManager().getPlugin("MoneyHandler") == null) {
//            String path = "plugins" + File.separator;
//            Logger("Download MoneyHandler !", "Warning");
//            try {
//                upd.autoDownload("http://ibhh.de/MoneyHandler.jar", path, "MoneyHandler.jar", "forceupdate");
//            } catch (Exception ex) {
//                Logger("ibhhs server currently not aviable!", "Error");
//            }
//            Logger("Downloaded MoneyHandler successfully!", "Warning");
//            Logger(".. Done!", "Warning");
//            Logger("Restart the server to enable any money support, other wise it wont work!", "Error");
//            this.setEnabled(false);
//            onDisable();
//            System.out.println("[AnimalShop] disabled!");
//            return;
//        }
        startStatistics();
        System.out.println("[AnimalShop] Version: " + this.Version
                + " successfully enabled!");
    }

    private void startStatistics() {
        try {
            new Metrics().beginMeasuringPlugin(this);
        } catch (Exception ex) {
            Logger("There was an error while submitting statistics.", "Error");
        }
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
                Update.autoDownload(url, path + "xpShop" + File.separator, name, type);
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

    public boolean blockIsValid(Sign sign, Player p) {
        String[] linetemp = sign.getLines();
        if (blockIsValid(linetemp, p)) {
            return true;
        }
        return false;
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
                || (line[2].equalsIgnoreCase(getConfig().getString("Catred." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("Catblack." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("Catsiamese." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("Catwild." + getConfig().getString("language"))))
                || (line[2].equalsIgnoreCase(getConfig().getString("Wolf." + getConfig().getString("language")))))) {
            a = true;
        } else if ((line[0].equalsIgnoreCase("[AnimalShop]"))) {
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
            if (!(line[2].equalsIgnoreCase(getConfig().getString("Catred." + getConfig().getString("language"))))) {
                p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Catred." + getConfig().getString("language")));
            }
            if (!(line[2].equalsIgnoreCase(getConfig().getString("Catblack." + getConfig().getString("language"))))) {
                p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Catblack." + getConfig().getString("language")));
            }
            if (!(line[2].equalsIgnoreCase(getConfig().getString("Catsiamese." + getConfig().getString("language"))))) {
                p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Catsiamese." + getConfig().getString("language")));
            }
            if (!(line[2].equalsIgnoreCase(getConfig().getString("Catwild." + getConfig().getString("language"))))) {
                p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Line 3 isnt a " + getConfig().getString("Catwild." + getConfig().getString("language")));
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
            p.getWorld().spawnCreature(p.getLocation(), EntityType.SHEEP);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Cow." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), EntityType.COW);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Chicken." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), EntityType.CHICKEN);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Pig." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), EntityType.PIG);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("MushroomCow." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), EntityType.MUSHROOM_COW);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Villager." + getConfig().getString("language")))) {
            p.getWorld().spawnCreature(p.getLocation(), EntityType.VILLAGER);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Wolf." + getConfig().getString("language")))) {
            Wolf w = (Wolf) p.getWorld().spawnCreature(p.getLocation(), EntityType.WOLF);
            w.setOwner(p);
            w.setSitting(false);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Catred." + getConfig().getString("language")))) {
            Ocelot w = (Ocelot) p.getWorld().spawnCreature(p.getLocation(), EntityType.OCELOT);
            w.setOwner(p);
            w.setCatType(Ocelot.Type.RED_CAT);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Catblack." + getConfig().getString("language")))) {
            Ocelot w = (Ocelot) p.getWorld().spawnCreature(p.getLocation(), EntityType.OCELOT);
            w.setOwner(p);
            w.setCatType(Ocelot.Type.BLACK_CAT);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Catsiamese." + getConfig().getString("language")))) {
            Ocelot w = (Ocelot) p.getWorld().spawnCreature(p.getLocation(), EntityType.OCELOT);
            w.setOwner(p);
            w.setCatType(Ocelot.Type.SIAMESE_CAT);
        } else if (Animal.equalsIgnoreCase(getConfig().getString("Catwild." + getConfig().getString("language")))) {
            Ocelot w = (Ocelot) p.getWorld().spawnCreature(p.getLocation(), EntityType.OCELOT);
            w.setOwner(p);
            w.setCatType(Ocelot.Type.WILD_OCELOT);
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
                String path = "plugins" + File.separator;
                autoUpdate("http://ibhh.de/AnimalShop.jar", path, "AnimalShop.jar", "forceupdate");
            } catch (Exception e) {
                System.out.println("[AnimalShop] Debug: Manual download failed!" + e.getMessage());
            }
        }
        return false;
    }
}