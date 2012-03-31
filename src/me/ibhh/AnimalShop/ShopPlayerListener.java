package me.ibhh.AnimalShop;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopPlayerListener implements Listener {

    private final AnimalShop plugin;
    double doubeline;
    private static final BlockFace[] shopFaces = {BlockFace.SELF, BlockFace.DOWN, BlockFace.UP, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};

    public ShopPlayerListener(AnimalShop plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if ((event.hasBlock()) && ((event.getClickedBlock().getState() instanceof Sign)) && (event.getAction() == Action.LEFT_CLICK_BLOCK) && (!p.isSneaking())) { // && !(p.isSneaking())
            Sign s = (Sign) event.getClickedBlock().getState();
            String[] line = s.getLines();
            if (this.plugin.blockIsValid(line, p)) {
                if (plugin.permissionsChecker.checkpermissions(p, "AnimalShop.use")) {
                    double price = getPrice(s, 1, p);
                    String Animal = getType(s, 2);
                    if ((plugin.MoneyHandler.getBalance(p) - price) >= 0) {
                        plugin.MoneyHandler.substract(price, p);
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
                    if (plugin.permissionsChecker.checkpermissions(p, "AnimalShop.create")) {
                        event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + " Successfully created AnimalShop!");
                        event.setLine(0, "[AnimalShop]");
                    } else {
                        event.setCancelled(true);
                    }
                } else {
                    event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "AnimalShop creation failed! Wrong Syntax!");
                    if (plugin.debug) {
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (!(event.getBlock().getState() instanceof Sign)) {
            org.bukkit.block.Sign sign = findSignxp(event.getBlock(), p);
            if (isCorrectSign(sign, event.getBlock())) {
                if (sign.getLine(0).equalsIgnoreCase("[AnimalShop]")) {
                    String[] line = sign.getLines();
                    if (plugin.blockIsValid(sign, p)) {
                        if (plugin.permissionsChecker.checkpermissions(p, "AnimalShop.create")) {
                            plugin.PlayerLogger(p, "Destroying xpShop!", "");
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        } else {
            Sign s = (Sign) event.getBlock().getState();
            String[] line = s.getLines();

            if (line[0].equalsIgnoreCase("[AnimalShop]")) {
                if (this.plugin.blockIsValid(line, p)) {
                    if (plugin.permissionsChecker.checkpermissions(p, "AnimalShop.create")) {
                        plugin.PlayerLogger(p, "Destroying xpShop!", "");
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    public Sign findSignxp(Block block, Player p) {
        for (BlockFace bf : shopFaces) {
            Block faceBlock = block.getRelative(bf);
            if (isSign(faceBlock)) {
                Sign sign = (Sign) faceBlock.getState();
                if ((plugin.blockIsValid(sign, p)) && ((faceBlock.equals(block)) || (getAttachedFace(sign).equals(block)))) {
                    return sign;
                }
            }
        }
        return null;
    }

    public static Block getAttachedFace(org.bukkit.block.Sign sign) {
        return sign.getBlock().getRelative(((org.bukkit.material.Sign) sign.getData()).getAttachedFace());
    }

    private static boolean isCorrectSign(org.bukkit.block.Sign sign, Block block) {
        return (sign != null) && ((sign.getBlock().equals(block)) || (getAttachedFace(sign).equals(block)));
    }

    public static boolean isSign(Block block) {
        return block.getState() instanceof Sign;
    }
}