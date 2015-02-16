package com.ibhh.animalshop.raw.chat;

import org.bukkit.entity.Player;

import com.ibhh.animalshop.raw.Reflection;

public class TellRaw
{
	private static Class<?> nmsChatSerializer = Reflection.getNMSClass("ChatSerializer");
	private static Class<?> nmsPacketPlayOutChat = Reflection.getNMSClass("PacketPlayOutChat");

	public static void sendRawMessage(Player player, String message) throws Exception
	{
		if(message != null)
			try
			{
				Object handle = Reflection.getHandle(player);
				Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
				Object serialized = Reflection.getMethod(nmsChatSerializer, "a", new Class[] {String.class}).invoke(null, new Object[] {message});
				Object packet = nmsPacketPlayOutChat.getConstructor(new Class[] {Reflection.getNMSClass("IChatBaseComponent")}).newInstance(new Object[] {serialized});
				Reflection.getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, new Object[] {packet});
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		else
			throw new Exception("Message must not be null! It will cause game crashes!");
	}
}
