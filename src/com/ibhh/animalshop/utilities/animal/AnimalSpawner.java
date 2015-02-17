/**
 * 
 */
package com.ibhh.animalshop.utilities.animal;

import org.bukkit.entity.Player;

/**
 * @author ibhh
 *
 */
public interface AnimalSpawner
{
	/**
	 * 
	 * @param args
	 * @return true on success
	 */
	public boolean spawn(String args, Player p);
	
	public String getIdetifier();
	
	public String getSystemIdentifier();
}
