package me.moehritz.porty.cmds;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public abstract class BasePortyCommand extends Command {

	protected static ChatColor COLOR_HIGHLIGHT = ChatColor.YELLOW, COLOR_TEXT = ChatColor.GRAY;

	protected static String PREFIX_MAIN = ChatColor.GREEN + "[TP] " + COLOR_TEXT;
	protected static String PREFIX_HELP = ChatColor.GREEN + "[TP-?] " + COLOR_TEXT;
	protected static String PREFIX_TEXT = ChatColor.GREEN + "> " + COLOR_TEXT;

	public BasePortyCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	public abstract String[] getHelpText();

	public abstract void executeCommand(CommandSender sender, String[] args);

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length != 0) {
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(TextComponent.fromLegacyText(PREFIX_HELP + "/" + getName() + " help:"));
				sendMessages(sender, getHelpText());
				return;
			}
		}
		executeCommand(sender, args);
	}

	public void sendMessage(CommandSender sender, String text) {
		sendMessage(sender, text, true);
	}

	public void sendMessages(CommandSender sender, String[] text) {
		boolean first = true;
		for (String s : text) {
			String start = first ? PREFIX_MAIN : PREFIX_TEXT;
			first = false;
			sender.sendMessage(TextComponent.fromLegacyText(start + s));
		}
	}

	public void sendMessage(CommandSender sender, String text, boolean main) {
		sender.sendMessage(TextComponent.fromLegacyText((main ? PREFIX_MAIN : PREFIX_TEXT) + text));
	}

	public void sendWrongUsage(CommandSender sender) {
		sender.sendMessage(TextComponent.fromLegacyText(PREFIX_MAIN + "Type '" + COLOR_HIGHLIGHT + "/" + getName() + " help" + COLOR_TEXT
				+ "' to get help."));
	}

	public void sendNoPerm(CommandSender sender, String perm) {
		sender.sendMessage(TextComponent.fromLegacyText(PREFIX_MAIN + "You need the permission '" + COLOR_HIGHLIGHT + perm + COLOR_TEXT
				+ "' for this."));
	}

}
