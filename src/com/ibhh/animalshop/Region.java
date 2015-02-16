/*
 * Copyright by Miny & Ibhh (http://desch.ch, http://ibhh.de)
 * Removing this will result in a Copyright Punishment!
 */

package com.ibhh.animalshop;

import org.bukkit.Location;

public class Region
{

	public static Boolean isInside(Location loc, Location corner1, Location corner2)
	{
		double xMin = 0;
		double xMax = 0;
		double zMin = 0;
		double zMax = 0;
		double x = loc.getX();
		double z = loc.getZ();

		xMin = Math.min(corner1.getX(), corner2.getX());
		xMax = Math.max(corner1.getX(), corner2.getX());

		zMin = Math.min(corner1.getZ(), corner2.getZ());
		zMax = Math.max(corner1.getZ(), corner2.getZ());

		if(x >= xMin && x <= xMax && z >= zMin && z <= zMax)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Location getMid(Location corner1, Location corner2) {
		double xMin = 0;
		double xMax = 0;
		double zMin = 0;
		double zMax = 0;

		xMin = Math.min(corner1.getX(), corner2.getX());
		xMax = Math.max(corner1.getX(), corner2.getX());

		zMin = Math.min(corner1.getZ(), corner2.getZ());
		zMax = Math.max(corner1.getZ(), corner2.getZ());
		
		return new Location(corner1.getWorld(), (xMax - xMin) / 2 + xMin, corner1.getY(), (zMax - zMin) / 2 + zMin);
	}
}