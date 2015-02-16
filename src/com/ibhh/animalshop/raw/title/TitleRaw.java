package com.ibhh.animalshop.raw.title;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;

import com.ibhh.animalshop.raw.Reflection;

public class TitleRaw
{
	private static Class<?> titleclass = Reflection.getNMSClass("PacketPlayOutTitle");
	private static Class<?> enumtitleaction = Reflection.getNMSClass("EnumTitleAction");
	private static Class<?> chatserial = Reflection.getNMSClass("ChatSerializer");

	public static void sendActionBar(Player p, String text)
	{
		try
		{
			sendPacket(p, "PacketPlayOutChat", new Class[] {Reflection.getNMSClass("IChatBaseComponent"), byte.class}, chatserial.getMethod("a", String.class).invoke(null, "{\"text\": \"" + text + "\"}"), (byte) 2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void sendHeaderAndFooter(Player p, String header, String footer)
	{
		try
		{
			Object packet = Reflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter").newInstance();
			Reflection.getField(packet.getClass().getDeclaredField("a")).set(packet, chatserial.getMethod("a", String.class).invoke(null, "{'text': '" + header + "'}"));
			Reflection.getField(packet.getClass().getDeclaredField("b")).set(packet, chatserial.getMethod("a", String.class).invoke(null, "{'text': '" + footer + "'}"));
			sendPacket(p, packet);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void sendTitles(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
		sendTimings(p, fadeIn, stay, fadeOut);
		sendTitle(p, title);
		sendSubTitle(p, subtitle);
	}

	public static void sendTitle(Player p, String title, int fadeIn, int stay, int fadeOut)
	{
		sendTimings(p, fadeIn, stay, fadeOut);
		sendTitle(p, title);
	}

	public static void sendTitle(Player p, String title)
	{
		try
		{
			Object t = titleclass.newInstance();
			Field f = t.getClass().getDeclaredField("a");
			f.setAccessible(true);
			f.set(t, Reflection.getField(enumtitleaction.getDeclaredField("TITLE")).get(null));
			f = t.getClass().getDeclaredField("b");
			f.setAccessible(true);
			f.set(t, chatserial.getMethod("a", String.class).invoke(null, "{'text': '" + title + "'}"));
			sendPacket(p, t);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void sendSubTitle(Player p, String subtitle, int fadeIn, int stay, int fadeOut)
	{
		sendTimings(p, fadeIn, stay, fadeOut);
		sendSubTitle(p, subtitle);
	}

	public static void sendSubTitle(Player p, String subtitle)
	{
		try
		{
			Object t = titleclass.newInstance();
			Field f = t.getClass().getDeclaredField("a");
			f.setAccessible(true);
			f.set(t, Reflection.getField(enumtitleaction.getDeclaredField("SUBTITLE")).get(null));
			f = t.getClass().getDeclaredField("b");
			f.setAccessible(true);
			f.set(t, chatserial.getMethod("a", String.class).invoke(null, "{'text': '" + subtitle + "'}"));
			sendPacket(p, t);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void sendTimings(Player p, int fadeIn, int stay, int fadeOut)
	{
		try
		{
			Object t = titleclass.newInstance();
			Field f = t.getClass().getDeclaredField("a");
			f.setAccessible(true);
			f.set(t, Reflection.getField(enumtitleaction.getDeclaredField("TIMES")).get(null));
			f = t.getClass().getDeclaredField("c");
			f.setAccessible(true);
			f.set(t, fadeIn);
			f = t.getClass().getDeclaredField("d");
			f.setAccessible(true);
			f.set(t, stay);
			f = t.getClass().getDeclaredField("e");
			f.setAccessible(true);
			f.set(t, fadeOut);
			sendPacket(p, t);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void reset(Player p)
	{
		try
		{
			Object t = titleclass.newInstance();
			Field f = t.getClass().getDeclaredField("a");
			f.setAccessible(true);
			f.set(t, Reflection.getField(enumtitleaction.getDeclaredField("RESET")).get(null));
			sendPacket(p, t);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void clear(Player p)
	{
		try
		{
			Object t = titleclass.newInstance();
			Field f = t.getClass().getDeclaredField("a");
			f.setAccessible(true);
			f.set(t, Reflection.getField(enumtitleaction.getDeclaredField("CLEAR")).get(null));
			sendPacket(p, t);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void sendPacket(Player p, Object packet)
	{
		try
		{
			Object nmsPlayer = Reflection.getNMSPlayer(p);
			Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
			connection.getClass().getMethod("sendPacket", Reflection.getNMSClass("Packet")).invoke(connection, packet);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void sendPacket(Player p, String packetName, Class<?>[] parameterclass, Object... parameters)
	{
		try
		{
			Object nmsPlayer = Reflection.getNMSPlayer(p);
			Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
			Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + "." + packetName).getConstructor(parameterclass).newInstance(parameters);
			connection.getClass().getMethod("sendPacket", Reflection.getNMSClass("Packet")).invoke(connection, packet);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
