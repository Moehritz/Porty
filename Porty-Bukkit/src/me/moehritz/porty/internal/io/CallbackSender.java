package me.moehritz.porty.internal.io;

import me.moehritz.porty.Porty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class CallbackSender {

    public static void sendCallback(int uid, int ret) {
        OutgoingPluginMessage out = new OutgoingPluginMessage(IOStatics.CALLBACK);

        out.write(uid);
        out.write(ret);

        Iterator<? extends Player> iterator = Bukkit.getOnlinePlayers().iterator();
        if (iterator.hasNext()) {
            iterator.next().sendPluginMessage(Porty.getInstance(), IOStatics.CHANNEL, out.getMessage());
        }
    }
}
