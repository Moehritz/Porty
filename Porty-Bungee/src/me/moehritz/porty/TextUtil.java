package me.moehritz.porty;

import net.md_5.bungee.api.ChatColor;

public class TextUtil
{
	public static String[] applyColors(String... in)
	{
		for (int i = 0; i < in.length; i++)
		{
			in[i] = ChatColor.translateAlternateColorCodes('&', in[i]);
		}
		return in;
	}

	public static String[] applyTag(String tag, String replace, String... in)
	{
		for (int i = 0; i < in.length; i++)
		{
			in[i] = in[i].replaceAll(tag, replace);
		}
		return in;
	}
}
