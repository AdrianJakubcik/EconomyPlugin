package me.midas.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.midas.testplugin.Database;

@SuppressWarnings("ALL")
public class CommandBalance implements CommandExecutor {
    Database db = new Database();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            if (sender instanceof Player) {
                if(command.getName().equalsIgnoreCase("balance")) {
                    if (args.length < 1) {
                        Player p = ((Player) sender).getPlayer();
                        double bal = db.getBalance(p.getName(), p.getUniqueId().toString());
                        sender.sendMessage(ChatColor.YELLOW + "[" + ChatColor.BLUE + "Balance" + ChatColor.YELLOW + "]" + ChatColor.WHITE + ": " + ChatColor.GREEN + "$" + String.valueOf(Double.valueOf(bal).intValue()));
                        return true;
                    }
                    Player p = Bukkit.getPlayer(args[0]);
                    if(p == null)
                    {
                        OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
                        if(pl == null) {
                            sender.sendMessage(ChatColor.RED + "Error: No such player found!");
                            return true;
                        }
                        else
                        {
                            double bal = db.getBalance(pl.getName(), pl.getUniqueId().toString());
                            sender.sendMessage(ChatColor.RED + "[" + ChatColor.WHITE + p.getName() + "'" + ChatColor.RED + "] " + ChatColor.YELLOW + "[" + ChatColor.BLUE + "Balance" + ChatColor.YELLOW + "]" + ChatColor.WHITE + ": " + ChatColor.GREEN + "$" + String.valueOf(bal));
                            return true;
                        }
                    }
                    double bal = db.getBalance(p.getName(), p.getUniqueId().toString());
                    sender.sendMessage(ChatColor.RED + "[" + ChatColor.WHITE + p.getName() + "'" + ChatColor.RED + "] " + ChatColor.YELLOW + "[" + ChatColor.BLUE + "Balance" + ChatColor.YELLOW + "]" + ChatColor.WHITE + ": " + ChatColor.GREEN + "$" + String.valueOf(bal));
                    return true;
                }
            } else {
                if(command.getName().equalsIgnoreCase("balance")) {
                    if (args.length < 1) {
                        return false;
                    }
                    Player p = Bukkit.getPlayer(args[0]);
                    if (p == null) {
                        OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
                        if(pl == null) {
                            Log.ColorLogWriteConsole(ChatColor.RED + "Error: No such player found!");
                            return true;
                        }
                        else
                        {
                            double bal = db.getBalance(pl.getName(), pl.getUniqueId().toString());
                            Log.ColorLogWriteConsole(ChatColor.RED + "[" + ChatColor.WHITE + p.getName() + "'" + ChatColor.RED + "] " + ChatColor.YELLOW + "[" + ChatColor.BLUE + "Balance" + ChatColor.YELLOW + "]" + ChatColor.WHITE + ": " + ChatColor.GREEN + "$" + String.valueOf(bal));
                            return true;
                        }
                    }
                    double bal = db.getBalance(p.getName(), p.getUniqueId().toString());
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + ChatColor.WHITE + p.getName() + "'" + ChatColor.RED + "] " + ChatColor.YELLOW + "[" + ChatColor.BLUE + "Balance" + ChatColor.YELLOW + "]" + ChatColor.WHITE + ": " + ChatColor.GREEN + "$" + String.valueOf(bal));
                    return true;
                }
            }
            return true;
        }
        catch (Exception e)
        {
            Log.LogWriteConsole("Error while executing /blance command...");
            e.printStackTrace();
            sender.sendMessage("There was an internal Error while executing command!");
            return true;
        }
    }
}
