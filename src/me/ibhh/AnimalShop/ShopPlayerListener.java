package me.ibhh.AnimalShop;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopPlayerListener implements Listener {

    private final AnimalShop plugin;
    private PermissionsHandler Permissions;
    private iConomyHandler iConomy;
    double doubeline;

    public ShopPlayerListener(AnimalShop plugin) {
        this.plugin = plugin;
        this.Permissions = new PermissionsHandler(plugin);
        this.iConomy = new iConomyHandler(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if ((event.hasBlock()) && ((event.getClickedBlock().getState() instanceof Sign)) && (event.getAction() == Action.LEFT_CLICK_BLOCK) && (!p.isSneaking())) { // && !(p.isSneaking())
            Sign s = (Sign) event.getClickedBlock().getState();
            String[] line = s.getLines();
            if (this.plugin.blockIsValid(line, p)) {
                if (this.Permissions.checkpermissions(p, "use")) {
                    double price = getPrice(s, 1, p);
                    String Animal = getType(s, 2);
                    if ((iConomy.getBalance156(p) - price) >= 0) {
                        this.iConomy.substractmoney156(price, p);
                        this.plugin.spawnAnimal(p, Animal);
                    } else {
                        p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "You havent enough money!");
                    }
                }
            }
        }
    }

    private double getPrice(Sign s, int l, Player p) {
        String line = s.getLine(l);
        try {
            this.doubeline = Double.parseDouble(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.doubeline;
    }

    private String getType(Sign s, int l) {
        String line = s.getLine(l);
        return line;
    }

    @EventHandler
    public void onChange(SignChangeEvent event) {
        Player p = event.getPlayer();

        if (event.getLine(0).equalsIgnoreCase("[AnimalShop]")) {
            try {
                String[] line = event.getLines();
                if (plugin.blockIsValid(line, p)) {
                    if (this.Permissions.checkpermissions(p, "create")) {
                        event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + " Successfully created AnimalShop!");
                        event.setLine(0, "[AnimalShop]");
                    } else {
                        event.setCancelled(true);
                    }
                } else {
                    event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "AnimalShop creation failed! Wrong Syntax!");
                    if(plugin.debug){
                        plugin.Logger("Line 0: " + line[0], "Debug");
                        plugin.Logger("Line 1: " + line[1], "Debug");
                        plugin.Logger("Line 2: " + line[2], "Debug");
                    }
                    event.setCancelled(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "AnimalShop creation failed!");
            }
        }
    }
//
//    @EventHandler
//    public void onPlace(BlockPlaceEvent event) {
//        if ((event.getBlock() instanceof Sign)) {
//            Sign sign = (Sign) event.getBlock().getState();
//
//            Player p = event.getPlayer();
//            if (sign.getLine(0).equalsIgnoreCase("[AnimalShop]")) {
//                boolean secondLineValid;
//                if (Tools.isInteger(sign.getLine(1))) {
//                    secondLineValid = true;
//                } else {
//                    secondLineValid = false;
//                }
//                if (this.Permissions.checkpermissions(p, "create")) {
//                    if (secondLineValid) {
//                        event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + " Successfully created AnimalShop!");
//                        sign.setLine(0, "[AnimalShop]");
//                    }
//                } else {
//                    event.setCancelled(true);
//                }
//                if (!secondLineValid) {
//                    event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "AnimalShop creation failed!");
//                    sign.setLine(1, ChatColor.DARK_BLUE + sign.getLine(1));
//                    event.setCancelled(true);
//                }
//            }
//        }
//    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (!(event.getBlock().getState() instanceof Sign)) {
            return;
        }
        Sign s = (Sign) event.getBlock().getState();
        String[] line = s.getLines();
        if (this.plugin.blockIsValid(line, p)) {
            if (!this.Permissions.checkpermissions(p, "create")) {
                event.setCancelled(true);
            } else {
                event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Destroying AnimalShop");
            }
        }
    }
}