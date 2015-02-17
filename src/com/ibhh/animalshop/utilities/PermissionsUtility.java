package com.ibhh.animalshop.utilities;

/**
 * PermissionsUtility by ibhh (http://ibhh.de)
 */

import org.bukkit.entity.Player;

import com.ibhh.animalshop.AnimalShop;
import com.ibhh.animalshop.utilities.logger.LoggerLevel;

public class PermissionsUtility
{
	/**
	 * Check if the player has the permission without sending message
	 * @param player
	 * @param action
	 * @return
	 */
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

	/**
	 * Check if the player has the permission with sending message
	 * @param player
	 * @param action
	 * @return
	 */
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
		AnimalShop.getLoggerUtility().log(player, player.getName() + " " + AnimalShop.getConfigHandler().getLanguageString(player, "permission.error") + " (" + action + ")", LoggerLevel.ERROR);
	}

	private void sendGeneralErrorMessage(Player player, Exception e)
	{
		AnimalShop.getLoggerUtility().log("Error on checking permissions!", LoggerLevel.ERROR);
		AnimalShop.getLoggerUtility().log(player, "Error on checking permissions!", LoggerLevel.ERROR);
	}
}