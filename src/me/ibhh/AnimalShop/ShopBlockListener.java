package me.ibhh.AnimalShop;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class ShopBlockListener extends BlockListener
{
  private final AnimalShop plugin;
  private PermissionsHandler Permissions;

  public ShopBlockListener(AnimalShop plugin)
  {
    this.plugin = plugin;
    this.Permissions = new PermissionsHandler(plugin);
  }

  public void onSignChange(SignChangeEvent event)
  {
    Player p = event.getPlayer();

    if (event.getLine(0).equalsIgnoreCase("[AnimalShop]"))
      try
      {
        boolean secondLineValid = false;
        if (Tools.isInteger(event.getLine(1)))
          secondLineValid = true;
        else
          secondLineValid = false;
        if (this.Permissions.checkpermissions(p, "create"))
        {
          if (secondLineValid)
          {
            event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + " Successfully created AnimalShop!");
            event.setLine(0, "[AnimalShop]");
          }
          else
          {
            event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "AnimalShop creation failed!");
            return;
          }
        }
        else {
          event.setCancelled(true);
          return;
        }
        if (!secondLineValid) {
          event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "AnimalShop creation failed!");
          event.setLine(1, ChatColor.DARK_BLUE + event.getLine(1));
          event.setCancelled(true);
          return;
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "AnimalShop creation failed!");
      }
  }

  public void onBlockPlace(BlockPlaceEvent event)
  {
    if ((event.getBlock() instanceof Sign)) {
      Sign sign = (Sign)event.getBlock().getState();

      Player p = event.getPlayer();
      if (sign.getLine(0).equalsIgnoreCase("[AnimalShop]"))
      {
        boolean secondLineValid = false;
        if (Tools.isInteger(sign.getLine(1)))
          secondLineValid = true;
        else {
          secondLineValid = false;
        }
        if (this.Permissions.checkpermissions(p, "create"))
        {
          if (secondLineValid) {
            event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + " Successfully created AnimalShop!");
            sign.setLine(0, "[AnimalShop]");
          }
        }
        else
        {
          event.setCancelled(true);
        }
        if (!secondLineValid) {
          event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "AnimalShop creation failed!");
          sign.setLine(1, ChatColor.DARK_BLUE + sign.getLine(1));
          event.setCancelled(true);
        }
      }
    }
  }

  public void onBlockBreak(BlockBreakEvent event)
  {
    if (!(event.getBlock().getState() instanceof Sign)) {
      return;
    }
    Sign s = (Sign)event.getBlock().getState();
    if (this.plugin.blockIsValid(s))
      if (!this.Permissions.checkpermissions(event.getPlayer(), "create"))
        event.setCancelled(true);
      else
        event.getPlayer().sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Destroying AnimalShop");
  }
}