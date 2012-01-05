/**
 * 
 */
package me.ibhh.AnimalShop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * @author Simon
 * 
 */
public class PermissionsHandler
{
	private AnimalShop AnimalShopV;
	String Error;
	
	public PermissionsHandler(AnimalShop AnimalSh)
	{
		AnimalShopV = AnimalSh;
	}

	public boolean checkpermissions(Player sender , String action)
	{
		if (sender instanceof Player) 
		{
			Player player = (Player) sender;
			try {
			Error = ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD +  "Houston we have a problem! You can't use this command!";
			}
			catch (Exception e) {
				e.printStackTrace();
				sender.sendMessage("[AnimalShop] " + "Error on checking permissions (Config)!");
			}
				if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) 
				{
					try {
						if (player.hasPermission("AnimalShop." + action)) 
						{
							return true;
						} // if(permissions.has(player, "AnimalShop." + action))
						else 
						{
							player.sendMessage(Error);
							return false;
						}
					} 
					catch (Exception e) 
					{
						System.out.println("[AnimalShop] "
								+ "Error on checking permissions with BukkitPermissions!");
						player.sendMessage("[AnimalShop] "
								+ "Error on checking permissions with BukkitPermissions!");
						e.printStackTrace();
						return false;
					}
				} 
				else 
				{
					if (Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) 
					{
						try 
						{
							PermissionManager permissions = PermissionsEx.getPermissionManager();
							// Permission check
							if (permissions.has(player, "AnimalShop." + action)) {
								// yay!
								return true;
							} 
							else 
							{
								// houston, we have a problem :)
								player.sendMessage(Error);
								return false;
							}
						} 
						catch (Exception e) 
						{
							System.out.println("[AnimalShop] " + "Error on checking permissions with PermissionsEx!");
							player.sendMessage("[AnimalShop] " + "Error on checking permissions with PermissionsEx!");
							e.printStackTrace();
							return false;
						}
					} 
					else 
					{
						System.out
						.println("PermissionsEx plugin are not found.");
						return false;
					}
				}
		}
		else {
			System.out.println("[AnimalShop] "
					+ (AnimalShopV.getConfig().getString("command.error.noplayer"
							+ AnimalShopV.getConfig().getString("language"))));
			return false;
		}
	}
}
