package com.ibhh.animalshop.utilities;

/**
 * PermissionsUtility by ibhh (http://ibhh.de)
 */

import org.bukkit.entity.Player;

import com.ibhh.animalshop.Main;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public class PermissionsUtility
{

	private Main plugin;
	public int PermPlugin = 0;

	public PermissionsUtility(Main pl)
	{
		this.plugin = pl;
	}

	public boolean checkpermissionssilent(Player player, String action)
	{
		try
		{
			if(player.isOp())
			{
				return true;
			}
			if(player.hasPermission(action) || player.hasPermission(action.toLowerCase()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			sendGeneralErrorMessage(player, e);
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkpermissions(Player player, String action)
	{
		try
		{
			if(player.isOp())
			{
				return true;
			}
			if(player.hasPermission(action) || player.hasPermission(action.toLowerCase()))
			{
				return true;
			}
			else
			{
				sendErrorMessage(player, action);
				return false;
			}
		}
		catch(Exception e)
		{
			sendGeneralErrorMessage(player, e);
			e.printStackTrace();
			return false;
		}

	}

	private void sendErrorMessage(Player player, String action)
	{
		plugin.getLoggerUtility().log(player, player.getName() + " " + plugin.getConfigHandler().getLanguageString(player, "permission.error") + " (" + action + ")", LoggerLevel.ERROR);
	}

	private void sendGeneralErrorMessage(Player player, Exception e)
	{
		plugin.getLoggerUtility().log("Error on checking permissions!", LoggerLevel.ERROR);
		plugin.getLoggerUtility().log(player, "Error on checking permissions!", LoggerLevel.ERROR);
	}
}