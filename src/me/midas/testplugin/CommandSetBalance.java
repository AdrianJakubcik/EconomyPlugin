package me.midas.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@SuppressWarnings("ALL")
public class CommandSetBalance implements CommandExecutor {
            Database db = new Database();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
            try{
                if(sender instanceof Player)
                {
                    if(command.getName().equalsIgnoreCase("setbalance"))
                    {
                        if(args.length > 0 && args.length < 2)
                        {
                            Player p = ((Player) sender).getPlayer();
                            Double amount = Double.valueOf(args[0]);
                            db.setPlayerBalance(p.getName(),p.getUniqueId().toString(),amount);
                            sender.sendMessage(ChatColor.RED + p.getName() + ChatColor.WHITE + "'s balance changed to " + ChatColor.GREEN + "$" + amount);
                            return true;
                        }
                        else if(args.length > 1)
                        {
                            Player p = Bukkit.getPlayer(args[0]);
                            if(p == null) {
                                OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
                                if (pl == null) {
                                    sender.sendMessage(ChatColor.RED + "Error: No such player found");
                                    return true;
                                } else
                                {
                                    Double amount = Double.valueOf(args[1]);
                                    db.setPlayerBalance(pl.getName(),pl.getUniqueId().toString(),amount);
                                    sender.sendMessage(ChatColor.RED + p.getName() + ChatColor.WHITE + "'s balance changed to " + ChatColor.GREEN + "$" + amount);
                                    return true;
                                }
                            }
                            Double amount = Double.valueOf(args[1]);
                            db.setPlayerBalance(p.getName(),p.getUniqueId().toString(),amount);
                            sender.sendMessage(ChatColor.RED + p.getName() + ChatColor.WHITE + "'s balance changed to " + ChatColor.GREEN + "$" + amount);
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
                    if(command.getName().equalsIgnoreCase("setbalance"))
                    {
                        if(args.length > 1)
                        {
                            Player p = Bukkit.getPlayer(args[0]);
                            if(p == null)
                            {
                                OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
                                if (pl == null) {
                                    Log.ColorLogWriteConsole(ChatColor.RED + "Error: No such player found");
                                    return true;
                                } else
                                {
                                    Double amount = Double.valueOf(args[1]);
                                    db.setPlayerBalance(pl.getName(),pl.getUniqueId().toString(),amount);
                                    Log.ColorLogWriteConsole(ChatColor.RED + p.getName() + ChatColor.WHITE + "'s balance changed to " + ChatColor.GREEN + "$" + amount);
                                    return true;
                                }
                            }
                            Double amount = Double.valueOf(args[1]);
                            db.setPlayerBalance(p.getName(), p.getUniqueId().toString(), Double.valueOf(amount));
                            Log.ColorLogWriteConsole(ChatColor.RED + p.getName() + ChatColor.WHITE + "'s balance changed to " + ChatColor.GREEN + "$" + amount);
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
                    sender.sendMessage(ChatColor.RED + "Error while processing 'setbalance' command");
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
