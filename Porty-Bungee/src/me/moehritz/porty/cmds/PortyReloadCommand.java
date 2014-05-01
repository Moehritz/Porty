package me.moehritz.porty.cmds;

import me.moehritz.porty.Porty;
import net.md_5.bungee.api.CommandSender;

public class PortyReloadCommand extends BasePortyCommand
{

	public PortyReloadCommand()
	{
		super("portyreload", "porty.cmd.reload", new String[] { "preload", "porty-reload" });
	}

	@Override
	public String[] getHelpText()
	{
		return new String[] { "Reloads the configuration" };
	}

	@Override
	public void executeCommand(final CommandSender sender, String[] args)
	{
		if (args.length == 0)
		{
			Porty.getInstance().getConfig().reload();

			sendMessage(sender, "The configuration has been reloaded.");
		}
		else
		{
			sendWrongUsage(sender);
		}
	}
}
