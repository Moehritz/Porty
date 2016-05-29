package me.moehritz.porty;

import net.md_5.bungee.api.ChatColor;

import java.io.*;

public class PortyUtil {
    public static String[] applyColors(String... in) {
        for (int i = 0; i < in.length; i++) {
            in[i] = ChatColor.translateAlternateColorCodes('&', in[i]);
        }
        return in;
    }

    public static String[] applyTag(String tag, String replace, String... in) {
        for (int i = 0; i < in.length; i++) {
            in[i] = in[i].replaceAll(tag, replace);
        }
        return in;
    }

    public static void writeDefaultFile(final String base, final File file) throws IOException {
        InputStream in = PortyUtil.class.getClassLoader().getResourceAsStream(base + ".yml");
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
