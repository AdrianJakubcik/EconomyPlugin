package me.midas.testplugin;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

class Log {

    public static Logger log = Bukkit.getPluginManager().getPlugin("TestPlugin").getLogger();

    public static void LogWriteConsole(String message)
    {
        log.info(message);
    }
    public static void ColorLogWriteConsole(String message) {Bukkit.getConsoleSender().sendMessage(message); }

}
