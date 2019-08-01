package me.midas.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.plugin2.main.server.Plugin;

import static org.bukkit.Bukkit.getOfflinePlayer;

@SuppressWarnings("ALL")
public class CommandAddBalance implements CommandExecutor {
            Database db = new Database();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try{
            if(sender instanceof Player)
            {
                if(command.getName().equalsIgnoreCase("addbalance"))
                {
                    if(args.length > 0 && args.length < 2)
                    {
                        Player p = ((Player) sender).getPlayer();
                        Double amount = Double.valueOf(args[0]);
                        db.addPlayerBalance(p.getName(),p.getUniqueId().toString(),amount);
                        sender.sendMessage(ChatColor.DARK_RED + "Successfully " + ChatColor.WHITE + "added " + ChatColor.GREEN + "$" + amount + ChatColor.WHITE + "to " + ChatColor.BLUE + p.getName());
                        return true;
                    }
                    else if(args.length > 1)
                    {
                        Player p = Bukkit.getPlayer(args[0]);
                        if(p == null)
                        {
                            sender.sendMessage(ChatColor.RED + "Error: No such player found");
                            return true;
                        }
                        Double amount = Double.valueOf(args[1]);
                        db.addPlayerBalance(p.getName(),p.getUniqueId().toString(),amount);
                        sender.sendMessage(ChatColor.DARK_RED + "Successfully " + ChatColor.WHITE + "added " + ChatColor.GREEN + "$" + amount + ChatColor.WHITE + "to " + ChatColor.BLUE + p.getName());
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else if(command.getName().equalsIgnoreCase("addbalanceoffline"))
                {
                    if(args.length > 1)
                    {
                        OfflinePlayer p = getOfflinePlayer(args[0]);
                        if(p == null)
                        {
                            sender.sendMessage(ChatColor.RED + "Error: No such player found");
                            return true;
                        }
                        Double amount = Double.valueOf(args[1]);
                        db.addPlayerBalance(p.getName(),p.getUniqueId().toString(),amount);
                        sender.sendMessage(ChatColor.DARK_RED + "Successfully " + ChatColor.WHITE + "added " + ChatColor.GREEN + "$" + amount + ChatColor.WHITE + "to " + ChatColor.BLUE + p.getName());
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                return true;
            }
            else
            {
                if(command.getName().equalsIgnoreCase("addbalance"))
                {
                    if(args.length > 1)
                    {
                        Player p = Bukkit.getPlayer(args[0]);
                        if(p == null)
                        {
                            Log.ColorLogWriteConsole(ChatColor.RED + "Error: No such player found");
                            return true;
                        }
                        Double amount = Double.valueOf(args[1]);
                        db.addPlayerBalance(p.getName(), p.getUniqueId().toString(), Double.valueOf(amount));
                        Log.ColorLogWriteConsole(ChatColor.DARK_RED + "Successfully " + ChatColor.WHITE + "added " + ChatColor.GREEN + "$" + amount + ChatColor.WHITE + "to " + ChatColor.BLUE + p.getName());
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else if(command.getName().equalsIgnoreCase("addbalanceoffline"))
                {
                    if(args.length > 1)
                    {
                        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                        if(p == null)
                        {
                            Log.ColorLogWriteConsole(ChatColor.RED + "Error: No such player found");
                            return true;
                        }
                        Double amount = Double.valueOf(args[1]);
                        db.addPlayerBalance(p.getName(), p.getUniqueId().toString(), Double.valueOf(amount));
                        Log.ColorLogWriteConsole(ChatColor.DARK_RED + "Successfully " + ChatColor.WHITE + "added " + ChatColor.GREEN + "$" + amount + ChatColor.WHITE + "to " + ChatColor.BLUE + p.getName());
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                return true;
            }

        }catch (Exception e){
            if(sender instanceof Player)
            {
                sender.sendMessage(ChatColor.RED + "Error while processing 'addbalance' command");
                e.printStackTrace();
            }
            else {
                Log.LogWriteConsole("Error while processing 'setbalance' command...");
                e.printStackTrace();
            }
            return true;
        }
    }
}
