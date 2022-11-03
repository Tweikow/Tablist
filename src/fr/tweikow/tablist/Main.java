package fr.tweikow.tablist;

import fr.tweikow.tablist.utils.TablistManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Plugin instance;
    public static String header;
    public static String footer;
    public static Integer cooldown_reload;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        header = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("tablist.header"));
        footer = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("tablist.footer"));
        cooldown_reload = Main.instance.getConfig().getInt("tablist.cooldown_reload");
        TablistManager.sendTabList();

        log(this.getName() + " §ais Enable");
    }

    @Override
    public void onDisable() {
        log(this.getName() + " §cis Disable");
    }

    public static void log(String s){
        Bukkit.getConsoleSender().sendMessage(s);
    }
}
