package me.moehritz.porty;

import me.moehritz.porty.cmds.BasePortyCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Messages {

    private static File configFile;
    private static Configuration cfg;

    public static String prefixColor = "&a";

    public static String getMessage(String id) {
        return getMessage(id, "&4Translation not found.");
    }

    public static String getMessage(String id, String def) {
        if (cfg == null) {
            reload();
        }
        return TextUtil.applyColors(cfg.getString(id, def))[0];
    }

    public static String[] getMessages(String id) {
        if (cfg == null) {
            reload();
        }
        return TextUtil.applyColors((String[]) cfg.getStringList(id).toArray(new String[0]));
    }

    public static void reload() {
        try {
            load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(File file) throws IOException {
        configFile = file;

        if (!file.exists()) {
            saveDefaultValues(file);
        }

        cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

        prefixColor = ChatColor.translateAlternateColorCodes('&', cfg.getString("prefixColor"));
        BasePortyCommand.PREFIX_MAIN = prefixColor + "[TP] ";
        BasePortyCommand.PREFIX_HELP = prefixColor + "[TP-?] ";
        BasePortyCommand.PREFIX_TEXT = prefixColor + "> ";
    }

    public static void saveDefaultValues(File file) throws IOException {
        file.createNewFile();

        InputStream in = Messages.class.getClassLoader().getResourceAsStream("messages.yml");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int r = in.read();
            while (r != -1) {
                fos.write(r);
                r = in.read();
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
