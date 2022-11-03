package fr.tweikow.tablist.utils;

import fr.tweikow.tablist.Main;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

public class TablistManager {

    private static Player player;

    public TablistManager(Player player){
        setPlayer(player);
    }

    public static void sendTabList() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().size() >= 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {

                        setPlayer(p);
                        PlayerConnection connection = ((CraftPlayer) getPlayer()).getHandle().playerConnection;
                        IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + getHeader() + "\"}");
                        IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + getFooter() + "\"}");
                        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

                        try {
                            Field field = headerPacket.getClass().getDeclaredField("b");
                            field.setAccessible(true);
                            field.set(headerPacket, tabFoot);
                        } catch (Exception var11) {
                            var11.printStackTrace();
                        } finally {
                            connection.sendPacket(headerPacket);
                        }
                    }
                }
            }
        }.runTaskTimer(Main.instance, 0, Main.cooldown_reload * 20L);
    }

    public static Player getPlayer() {
        return TablistManager.player;
    }

    public static void setPlayer(Player player) {
        TablistManager.player = player;
    }

    public static String getFooter() {
        int ping = ((CraftPlayer) getPlayer()).getHandle().ping;
        return Main.footer.replace("%online%", Bukkit.getOnlinePlayers().size() + "").replace("%player%", getPlayer().getName()).replace("%ping%", ping + "").replace("%nl%", "\n").replace("%online_max%", Bukkit.getMaxPlayers()+"");
    }

    public static String getHeader() {
        int ping = ((CraftPlayer) getPlayer()).getHandle().ping;
        return Main.header.replace("%online%", Bukkit.getOnlinePlayers().size() + "").replace("%player%", getPlayer().getName()).replace("%ping%", ping + "").replace("%nl%", "\n").replace("%online_max%", Bukkit.getMaxPlayers()+"");
    }
}
