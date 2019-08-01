package me.midas.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.ess3.api.Economy;

public class Main extends JavaPlugin implements Listener {

    public  static Database db;
    public static Main getInstance;
    public EconomyImplementer implementer;
    private VaultHook Vault;
    public Economy eco;

    @Override
    public void onEnable(){
        if(!setupEconomy())
        {
            this.getLogger().info("Plugin has been disabled due to missing Vault");
            getPluginLoader().disablePlugin(this);
        }
        Instanceclasses();
        this.getLogger().info("Checking Files...");
        new FileHandler();
        this.getLogger().info("Checking Database...");
        db = new Database();
        getServer().getPluginManager().registerEvents(this,this);
        Vault.hook();
       Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
           @Override
           public void run() {
               LoadCMDS();
           }
       });
    }

    private void Instanceclasses(){
        getInstance = this;
        implementer = new EconomyImplementer();
        Vault = new VaultHook();
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        return true;
    }

    private void LoadCMDS()
    {
        this.getCommand("balance").setExecutor(new CommandBalance());
        this.getCommand("setbalance").setExecutor(new CommandSetBalance());
        this.getCommand("addbalance").setExecutor(new CommandAddBalance());
        this.getCommand("addbalanceoffline").setExecutor(new CommandAddBalance());
    }

    @Override
    public void onDisable(){
        Vault.unhook();
    }

    @EventHandler

    public void OnJoin(PlayerJoinEvent event)
    {
        Player Player = event.getPlayer();
        Player.sendMessage("Welcome aboard! " + Player.getName());
        OfflinePlayer player = ((Player) Player).getPlayer();
         Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
             @Override
             public void run() {
                 db.AddPlayerToDB(Player.getName(),Player.getUniqueId().toString(),((Player) Player).getAddress().getAddress().getHostAddress());
                 Client c = new Client(Player.getName(), Player.getUniqueId().toString(), ((Player) Player).getAddress().getAddress().getHostAddress(), db.getBalance(Player.getName(), Player.getUniqueId().toString()));
                 if(!Vars.isPlayerAlreadyInServer(c))
                     Vars.Players.add(c);
             }
         });
    }



}
