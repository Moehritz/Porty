package me.moehritz.porty.cmds;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import me.moehritz.porty.Messages;
import me.moehritz.porty.Porty;
import me.moehritz.porty.TextUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public abstract class BasePortyCommand extends Command implements TabExecutor
{

	public static String PREFIX_MAIN = Messages.prefixColor + "[TP] ";
	public static String PREFIX_HELP = Messages.prefixColor + "[TP-?] ";
	public static String PREFIX_TEXT = Messages.prefixColor + "> ";

	public BasePortyCommand(String name, String permission, String[] aliases)
	{
		super(appendPrefix(name)[0], permission, appendPrefix(aliases));
	}

	private static String[] appendPrefix(String... args)
	{
		String pre = Porty.getInstance().getConfig().getCommandPrefix();
		for (int i = 0; i < args.length; i++)
		{
			args[i] = pre + args[i];
		}
		return args;
	}

	public abstract String[] getHelpText();

	public abstract void executeCommand(CommandSender sender, String[] args);

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length != 0 && args[0].equalsIgnoreCase("help"))
		{
			sender.sendMessage(TextComponent.fromLegacyText(PREFIX_HELP + "/" + getName() + " help:"));
			sendMessages(sender, getHelpText());
			return;
		}
		executeCommand(sender, args);
	}

	public void sendMessage(CommandSender sender, String text)
	{
		sendMessage(sender, text, true);
	}

	public void sendMessages(CommandSender sender, String[] text)
	{
		boolean first = true;
		for (String s : text)
		{
			String start = first ? PREFIX_MAIN : PREFIX_TEXT;
			first = false;
			sender.sendMessage(TextComponent.fromLegacyText(start + s));
		}
	}

	public void sendMessage(CommandSender sender, String text, boolean main)
	{
		sender.sendMessage(TextComponent.fromLegacyText((main ? PREFIX_MAIN : PREFIX_TEXT) + text));
	}

	public void sendWrongUsage(CommandSender sender)
	{
		sender.sendMessage(TextComponent.fromLegacyText(PREFIX_MAIN + TextUtil.applyTag("<command>", getName(), Messages.getMessage("general_help", "&7Type '&e/<command> help&7' to get help."))[0]));
	}

	public void sendNoPerm(CommandSender sender, String perm)
	{
		sender.sendMessage(TextComponent.fromLegacyText(PREFIX_MAIN + TextUtil.applyTag("<perm>", getName(), Messages.getMessage("perm_needed", "&7You need the permission '&e<perm>&7' for this."))[0]));
	}

	// https://github.com/SpigotMC/BungeeCord/blob/master/api/src/main/java/net/md_5/bungee/command/PlayerCommand.java
	// copied because PlayerCommand is for Internal use only (why???)
	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args)
	{
		final String lastArg = (args.length > 0) ? args[args.length - 1].toLowerCase() : "";
		return Iterables.transform(Iterables.filter(ProxyServer.getInstance().getPlayers(), new Predicate<ProxiedPlayer>()
		{
			@Override
			public boolean apply(ProxiedPlayer player)
			{
				return player.getName().toLowerCase().startsWith(lastArg);
			}
		}), new Function<ProxiedPlayer, String>()
		{
			@Override
			public String apply(ProxiedPlayer player)
			{
				return player.getName();
			}
		});
	}

}
