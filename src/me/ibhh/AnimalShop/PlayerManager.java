/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ibhh.AnimalShop;

import org.bukkit.entity.Player;

/**
 *
 * @author Simon
 */
public class PlayerManager {

    private AnimalShop plugin;

    public PlayerManager(AnimalShop pl) {
        plugin = pl;
    }

    public int BroadcastMsg(String Permission, String msg) {
        int BroadcastedPlayers = 0;
        if (plugin.toggle) {
            return 0;
        }
        try {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (plugin.permissionsChecker != null) {
                    if (plugin.permissionsChecker.checkpermissionssilent(player, Permission)) {
                        if (plugin.getConfig().getBoolean("UsePrefix")) {
                            player.sendMessage(plugin.Prefix + "[AnimalShop] " + plugin.Text + msg);
                        } else {
                            player.sendMessage(plugin.Text + msg);
                        }
                        BroadcastedPlayers++;
                    }
                }
            }
        } catch (Exception e) {
            plugin.Logger("Error on broadcasting message.", "Error");
        }
        return BroadcastedPlayers;
    }
}
