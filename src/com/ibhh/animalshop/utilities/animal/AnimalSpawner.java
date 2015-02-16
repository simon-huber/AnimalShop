/**
 * 
 */
package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.Player;

import com.ibhh.animalshop.Main;

/**
 * @author ibhh
 *
 */
public abstract class AnimalSpawner
{
	
	protected final Main plugin;

	public AnimalSpawner(Main plugin)
	{
		this.plugin = plugin;
	}
	/**
	 * 
	 * @param args
	 * @return true on success
	 */
	public abstract boolean spawn(String args, Player p);
	
	public abstract String getIdetifier();
	
	public abstract String getSystemIdentifier();
}
