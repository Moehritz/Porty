package me.moehritz.porty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import lombok.Getter;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.util.CaseInsensitiveMap;

@Getter
public class PortyConfiguration
{
	private int globalTeleportTimer;
	private int timeout;
	private Map<String, Integer> serverTeleportTimer;
	
	private File configFile;
	
	public void reload() {
		try
		{
			load(configFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void load(File file) throws IOException
	{
		this.configFile = file;
		
		if (!file.exists())
		{
			saveDefaultValues(file);
		}

		Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

		Configuration timer = cfg.getSection("timer");
		globalTeleportTimer = timer.getInt("global", 0);
		serverTeleportTimer = new CaseInsensitiveMap<>();
		for (ServerInfo si : ProxyServer.getInstance().getServers().values())
		{
			serverTeleportTimer.put(si.getName(), timer.getInt("server." + si.getName(), -1));
		}

		timeout = cfg.getInt("timeout", 5);
	}

	public void saveDefaultValues(File file) throws IOException
	{
		file.createNewFile();

		InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml");
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(file);
			int r = in.read();
			while (r != -1)
			{
				fos.write(r);
				r = in.read();
			}
		}
		finally
		{
			if (fos != null)
			{
				fos.close();
			}
		}
	}
}
