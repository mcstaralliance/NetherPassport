package com.mcstaralliance.netherpassport;

import com.mcstaralliance.netherpassport.command.DebugCommand;
import com.mcstaralliance.netherpassport.listener.PlayerInteractListener;
import com.mcstaralliance.netherpassport.listener.PlayerPortalListener;
import com.mcstaralliance.netherpassport.listener.PlayerTeleportListener;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public final class NetherPassport extends JavaPlugin {
    private static NetherPassport instance;

    public static NetherPassport getInstance() {
        return instance;
    }

    private static LuckPerms api;

    public static LuckPerms getLuckPermsApi() {
        return api;
    }

    @Override
    public void onEnable() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }
        instance = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPortalListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        Bukkit.getPluginCommand("np").setExecutor(new DebugCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
