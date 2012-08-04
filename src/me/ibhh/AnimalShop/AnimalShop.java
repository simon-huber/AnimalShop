package me.ibhh.AnimalShop;

import java.io.File;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimalShop extends JavaPlugin {

    public float Version = 0;
    public float newversion = 0;
    public static PluginManager pm;
    private ShopPlayerListener ShopListener;
    public boolean debug;
    public Update upd;
    public boolean blacklisted;
    public iConomyHandler MoneyHandler;
    public PermissionsChecker permissionsChecker;
    public PlayerManager playerManager;
    public Utilities plugman;
    public Metrics metrics;
    public boolean toggle = true;
    public boolean updateaviable = false;
    public ChatColor Prefix, Text;

    @Override
    public void onDisable() {
        toggle = true;
        System.out.println("[AnimalShop] disabled!");
        if (getConfig().getBoolean("internet")) {
            UpdateAvailable(Version);
        }
    }

    public void forceUpdate() {
        if (getConfig().getBoolean("internet")) {
            try {
                if (updateaviable) {
                    Logger("New version: " + newversion + " found!", "Warning");
                    Logger("******************************************", "Warning");
                    Logger("*********** Please update!!!! ************", "Warning");
                    Logger("* http://ibhh.de/AnimalShop.jar *", "Warning");
                    Logger("******************************************", "Warning");
                    if (getConfig().getBoolean("autodownload") || getConfig().getBoolean("installondownload")) {
                        if (getConfig().getBoolean("autodownload")) {
                            try {
                                String path = "plugins" + File.separator + "AnimalShop" + File.separator;
                                if (upd.download(path)) {
                                    Logger("Downloaded new Version!", "Warning");
                                } else {
                                    Logger(" Cant download new Version!", "Warning");
                                }
                            } catch (Exception e) {
                                Logger("Error on dowloading new Version!", "Error");
                                e.printStackTrace();
                            }
                        }
                        if (getConfig().getBoolean("installondownload")) {
                            try {
                                String path = "plugins" + File.separator;
                                if (upd.download(path)) {
                                    Logger("Downloaded new Version!", "Warning");
                                    Logger("AnimalShop will be updated on the next restart!", "Warning");
                                } else {
                                    Logger(" Cant download new Version!", "Warning");
                                }
                            } catch (Exception e) {
                                Logger("Error on donwloading new Version!", "Error");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Logger("Please type [AnimalShop download] to download manual! ", "Warning");
                    }
                }
            } catch (Exception e) {
                Logger("Error on doing update check or update! Message: " + e.getMessage(), "Error");
                Logger("may the mainserver is down!", "Error");
            }
        }
    }

    public void install() {
        if (getConfig().getBoolean("internet")) {
            try {
                String path = "plugins" + File.separator;
                if (upd.download(path)) {
                    Logger("Downloaded new Version!", "Warning");
                    Logger("AnimalShop will be updated on the next restart!", "Warning");
                } else {
                    Logger(" Cant download new Version!", "Warning");
                }
            } catch (Exception e) {
                Logger("Error on donwloading new Version!", "Error");
                e.printStackTrace();
            }
        }
        if (getConfig().getBoolean("installondownload")) {
            Logger("Found Update! Installing now because of 'installondownload = true', please wait!", "Warning");
            playerManager.BroadcastMsg("AnimalShop.update", "Found Update! Installing now because of 'installondownload = true', please wait!");
        }
        try {
            plugman.unloadPlugin("AnimalShop");
        } catch (NoSuchFieldException ex) {
            Logger("Error on installing! Please check the log!", "Error");
            playerManager.BroadcastMsg("AnimalShop.update", "Error on installing! Please check the log!");
            java.util.logging.Logger.getLogger(AnimalShop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger("Error on installing! Please check the log!", "Error");
            playerManager.BroadcastMsg("AnimalShop.update", "Error on installing! Please check the log!");
            java.util.logging.Logger.getLogger(AnimalShop.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            plugman.loadPlugin("AnimalShop");
        } catch (InvalidPluginException ex) {
            Logger("Error on loading after installing! Please check the log!", "Error");
            playerManager.BroadcastMsg("AnimalShop.update", "Error on loading after installing! Please check the log!");
            java.util.logging.Logger.getLogger(AnimalShop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDescriptionException ex) {
            Logger("Error on loading after installing! Please check the log!", "Error");
            playerManager.BroadcastMsg("AnimalShop.update", "Error on loading after installing! Please check the log!");
            java.util.logging.Logger.getLogger(AnimalShop.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger("Installing finished!", "");
        playerManager.BroadcastMsg("AnimalShop.update", "Installing finished!");
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
        Prefix = ChatColor.getByChar(getConfig().getString("PrefixColor"));
        Text = ChatColor.getByChar(getConfig().getString("TextColor"));
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
        upd = new Update(this);
        playerManager = new PlayerManager(this);
        plugman = new Utilities(this);
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
        if (getConfig().getBoolean("internet")) {
            try {
                UpdateAvailable(Version);
                if (updateaviable) {
                    Logger("New version: " + upd.checkUpdate() + " found!", "Warning");
                    Logger("******************************************", "Warning");
                    Logger("*********** Please update!!!! ************", "Warning");
                    Logger("* http://ibhh.de/AnimalShop.jar *", "Warning");
                    Logger("******************************************", "Warning");
                }
            } catch (Exception e) {
                Logger("Error on doing update check! Message: " + e.getMessage(), "Error");
                Logger("may the mainserver is down!", "Error");
            }
        }
        this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                if (getConfig().getBoolean("internet")) {
                    Logger("Searching update for AnimalShop!", "Debug");
                    newversion = upd.checkUpdate();
                    if (newversion == -1) {
                        newversion = aktuelleVersion();
                    }
                    Logger("installed AnimalShop version: " + Version + ", latest version: " + newversion, "Debug");
                    if (newversion > Version) {
                        Logger("New version: " + newversion + " found!", "Warning");
                        Logger("******************************************", "Warning");
                        Logger("*********** Please update!!!! ************", "Warning");
                        Logger("* http://ibhh.de/AnimalShop.jar *", "Warning");
                        Logger("******************************************", "Warning");
                        updateaviable = true;
                        if (getConfig().getBoolean("installondownload")) {
                            install();
                        }
                    } else {
                        Logger("No update found!", "Debug");
                    }
                }
            }
        }, 400L, 50000L);
        this.getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {

            @Override
            public void run() {
                toggle = false;
            }
        }, 20);
        startStatistics();
        System.out.println("[AnimalShop] Version: " + this.Version
                + " successfully enabled!");
    }

    private void startStatistics() {
        try {
            metrics = new Metrics(this);
            metrics.enable();
            metrics.start();
        } catch (Exception ex) {
            Logger("There was an error while submitting statistics.", "Error");
        }
    }

    /**
     * Delete an download new version of AnimalShop in the Update folder.
     *
     * @param
     * @return
     */
    public boolean autoUpdate(final String path) {
        if (getConfig().getBoolean("internet")) {
            try {
                upd.download(path);
            } catch (Exception e) {
                Logger("Error on doing blacklist update! Message: " + e.getMessage(), "Error");
                Logger("may the mainserver is down!", "Error");
            }
        }
        return true;
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
    public void UpdateAvailable(final float currVersion) {
        if (getConfig().getBoolean("internet")) {
            try {
                if (upd.checkUpdate() > currVersion) {
                    updateaviable = true;
                }
                if (updateaviable) {
                    updateaviable = true;
                } else {
                    updateaviable = false;
                }
            } catch (Exception e) {
                Logger("Error checking for new version! Message: " + e.getMessage(), "Error");
                Logger("May the mainserver is down!", "Error");
            }
        }
    }

    public String getPermissionsError() {
        String Error = ChatColor.GRAY + "[AnimalShop] " + ChatColor.RED + getConfig().getString("permissionserror");
        return Error;
    }

    /**
     * Intern logger to send player messages and log it into file
     *
     * @param msg
     * @param TYPE
     */
    public void Logger(String msg, String TYPE) {
        if (TYPE.equalsIgnoreCase("Warning") || TYPE.equalsIgnoreCase("Error")) {
            System.err.println("[AnimalShop] " + TYPE + ": " + msg);
        } else if (TYPE.equalsIgnoreCase("Debug")) {
            if (getConfig().getBoolean("debug")) {
                System.out.println("[AnimalShop] " + "Debug: " + msg);
            }
        } else {
            System.out.println("[AnimalShop] " + msg);
        }
    }

    /**
     * Intern logger to send player messages and log it into file
     *
     * @param p
     * @param msg
     * @param TYPE
     */
    public void PlayerLogger(Player p, String msg, String TYPE) {
        if (TYPE.equalsIgnoreCase("Error")) {
            if (getConfig().getBoolean("UsePrefix")) {
                p.sendMessage(Prefix + "[AnimalShop] " + ChatColor.RED + "Error: " + Text + msg);
            } else {
                p.sendMessage(ChatColor.RED + "Error: " + Text + msg);
            }
        } else {
            if (getConfig().getBoolean("UsePrefix")) {
                p.sendMessage(Prefix + "[AnimalShop] " + Text + msg);
            } else {
                p.sendMessage(Text + msg);
            }
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
        p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop] " + ChatColor.GOLD + String.format(getConfig().getString("buymessage." + getConfig().getString("language")), Animal));
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
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (permissionsChecker.checkpermissions(player, getConfig().getString("AnimalShop.reload"))) {
                            try {
                                PlayerLogger(player, "Please wait: Reloading this plugin!", "Warning");
                                plugman.unloadPlugin("AnimalShop");
                                plugman.loadPlugin("AnimalShop");
                                PlayerLogger(player, "Reloaded!", "");
                            } catch (InvalidPluginException ex) {
                                java.util.logging.Logger.getLogger(AnimalShop.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvalidDescriptionException ex) {
                                java.util.logging.Logger.getLogger(AnimalShop.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NoSuchFieldException ex) {
                                java.util.logging.Logger.getLogger(AnimalShop.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                java.util.logging.Logger.getLogger(AnimalShop.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("update")) {
                        if (permissionsChecker.checkpermissions(player, "CommandLogger.update")) {
                            install();
                            return true;
                        }
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
                upd.download(path);
            } catch (Exception e) {
                System.out.println("[AnimalShop] Debug: Manual download failed!" + e.getMessage());
            }
        }
        return false;
    }
}