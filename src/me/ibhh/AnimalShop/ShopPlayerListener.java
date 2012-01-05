package me.ibhh.AnimalShop;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class ShopPlayerListener extends PlayerListener
{
	private final AnimalShop plugin;
	private PermissionsHandler Permissions;
	private iConomyHandler iConomy;
	double doubeline;

	public ShopPlayerListener(AnimalShop plugin)
	{
		this.plugin = plugin;
		Permissions = new PermissionsHandler(plugin);
		iConomy = new iConomyHandler(plugin);
	}

	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player p = event.getPlayer();
		if ((event.hasBlock()) && ((event.getClickedBlock().getState() instanceof Sign)) && (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
			Sign s = (Sign)event.getClickedBlock().getState();
			if (this.plugin.blockIsValid(s))
			{
				if(Permissions.checkpermissions(p, "use"))
				{
					double price = getPrice(s, 1, p);
					String Animal = getType(s, 2);
					if ((iConomy.getBalance156(p) - price) >= 0) 
					{
						System.out.println("Buy");
						iConomy.substractmoney156(price, p);
						plugin.spawnAnimal(p, Animal);
					}
					else
					{
						p.sendMessage(ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "You havent enough money!");
					}
				}
			}
		}
	}



	private double getPrice(Sign s, int l, Player p) 
	{
		String line = s.getLine(l);
		try 
		{
			doubeline = Double.parseDouble(line);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return doubeline;
	}

	private String getType(Sign s, int l)
	{
		String line = s.getLine(l);
		return line;
	}
}