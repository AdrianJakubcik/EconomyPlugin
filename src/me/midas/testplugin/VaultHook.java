package me.midas.testplugin;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    public Main plugin = Main.getInstance;
    private Economy provider;

    public void hook () {
        provider = plugin.implementer;
        Bukkit.getServicesManager().register(Economy.class,provider,this.plugin,ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultApi hooked into " + ChatColor.AQUA + plugin.getName() );
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class,this.provider);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultApi unhooked from " + ChatColor.AQUA + plugin.getName() );
    }




}
