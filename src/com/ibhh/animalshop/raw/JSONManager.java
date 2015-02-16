package com.ibhh.animalshop.raw;

import com.ibhh.animalshop.raw.chat.ClickEvent;
import com.ibhh.animalshop.raw.chat.HoverEvent;

public class JSONManager
{
	private static String jsontellraw = "{\"text\": \"%s\", \"bold\":%b, \"italic\":%b, \"underlined\": %b, \"strikethrough\": %b, \"obfuscated\": %b, \"color\":\"%s\", \"clickEvent\":{ \"action\":\"%s\", \"value\": \"%s\"" + "}, \"hoverEvent\":{\"action\":\"%s\", \"value\": \"%s\"}}";

	public static String getJSONTellRaw(String message, boolean chat_bold, boolean chat_italic, boolean chat_underlined, boolean chat_strikethrough, boolean chat_obfuscated, RawColor color, ClickEvent clickevent_action, String clickevent_value, HoverEvent hoverevent_action, String hoverevent_value)
	{
		return String.format(jsontellraw, message, chat_bold, chat_italic, chat_underlined, chat_strikethrough, chat_obfuscated, color.name(), clickevent_action.name(), clickevent_value, hoverevent_action.name(), hoverevent_value);
	}
}
