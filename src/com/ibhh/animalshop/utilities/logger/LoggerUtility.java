package com.ibhh.animalshop.utilities.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ibhh.animalshop.Main;

/**
 * @author ibhh
 */
public class LoggerUtility
{

	private final Main plugin;
	private final boolean debugfile;
	private final boolean debug;
	private final String Prefix;
	private final boolean usePrefix;
	public ChatColor PrefixColor,
			TextColor;


	public LoggerUtility(Main plugin)
	{
		this.plugin = plugin;
		debugfile = plugin.getConfigHandler().getConfig().getBoolean("debugfile");
		debug = plugin.getConfigHandler().getConfig().getBoolean("debug");
		Prefix = plugin.getConfigHandler().getConfig().getString("Prefix");
		usePrefix = plugin.getConfigHandler().getConfig().getBoolean("UsePrefix");
		loadcolors();
	}

	private void loadcolors()
	{
		PrefixColor = ChatColor.getByChar(plugin.getConfigHandler().getConfig().getString("PrefixColor"));
		TextColor = ChatColor.getByChar(plugin.getConfigHandler().getConfig().getString("TextColor"));
	}

	public void log(String msg, LoggerLevel TYPE)
	{
		try
		{
			if(TYPE.equals(LoggerLevel.WARNING) || TYPE.equals(LoggerLevel.SEVERE))
			{
				System.err.println("[" + plugin.getName() + "] " + TYPE.name() + ": " + msg);
				Bukkit.broadcast(PrefixColor + "[" + Prefix + "]" + ChatColor.RED + " " + TYPE.name() + ": " + TextColor + msg, "CurveCraft.log");
				if(debugfile)
				{
					this.log("Error: " + msg);
				}
			}
			else if(TYPE.equals(Level.FINE))
			{
				if(debug)
				{
					System.out.println("[" + Prefix + "]" + " Debug: " + msg);
					Bukkit.broadcast(PrefixColor + "[" + Prefix + "]" + " Debug: " + TextColor + msg, "CurveCraft.log");
				}
				if(debugfile)
				{
					this.log("Debug: " + msg);
				}
			}
			else
			{
				System.out.println("[" + Prefix + "]" + " " + msg);
				Bukkit.broadcast(PrefixColor + "[" + Prefix + "]" + " " + TextColor + msg, "CurveCraft.log");
				if(debugfile)
				{
					this.log(msg);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("[CurveCraft] Error: Uncatch Exeption!");
		}
	}

	public void log(Player p, String msg, LoggerLevel error)
	{
		try
		{
			String playername = p.getName();
			
			if(error.equals(LoggerLevel.WARNING) || error.equals(LoggerLevel.ERROR))
			{
				if(usePrefix)
				{
					p.sendMessage(PrefixColor + "[" + Prefix + "]" + ChatColor.RED + " " + error.name() + ": " + TextColor + msg);
					if(debugfile)
					{
						this.log("Player: " + playername + " " + error.name() + ": " + msg);
					}
				}
				else
				{
					p.sendMessage(ChatColor.RED + error.name() + ": " + TextColor + msg);
					if(debugfile)
					{
						this.log("Player: " + playername + " " + error.name() + ": " + msg);
					}
				}
			}
			else
			{
				if(usePrefix)
				{
					p.sendMessage(PrefixColor + "[" + Prefix + "]" + " " + TextColor + msg);
					if(debugfile)
					{
						this.log("Player: " + playername + " Msg: " + msg);
					}
				}
				else
				{
					p.sendMessage(TextColor + msg);
					if(debugfile)
					{
						this.log("Player: " + playername + " Msg: " + msg);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("[CurveCraft] Error: Uncatch Exeption!");
		}
	}

	public void log(String in)
	{
		Date now = new Date();
		String Stream = now.toString();
		String path = plugin.getDataFolder().toString() + File.separator + "debugfiles" + File.separator;
		File directory = new File(path);
		directory.mkdirs();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd 'at' HH");
		File file = new File(path + "debug-" + ft.format(now) + ".txt");
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch(IOException ex)
			{
				System.out.println("Error: " + ex.getMessage());
			}
		}
		try
		{
			// Create file
			FileWriter fstream = new FileWriter(file, true);
			PrintWriter out = new PrintWriter(fstream);
			out.println("[" + Stream + "] " + in);
			// Close the output stream
			out.close();
		}
		catch(Exception e)
		{// Catch exception if any
			System.out.println("Error: " + e.getMessage());
		}
	}
}
