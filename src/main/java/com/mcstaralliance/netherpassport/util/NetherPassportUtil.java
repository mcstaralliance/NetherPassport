package com.mcstaralliance.netherpassport.util;

import com.mcstaralliance.netherpassport.NetherPassport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class NetherPassportUtil {
    private static final NetherPassport plugin = NetherPassport.getInstance();
    public static final String NETHER_PASSPORT_PERMISSION = "netherpassport.pass";

    public static void permitPlayer(Player player) {
        LuckPermsUtil.addTempPermission(player, NETHER_PASSPORT_PERMISSION, 5, TimeUnit.MINUTES);
    }

    public static boolean isPermittedPlayer(Player player) {
        return player.hasPermission(NETHER_PASSPORT_PERMISSION);
    }

    public static void transport(Player player) {
        // transport your dear player.
        FileConfiguration config = plugin.getConfig();
        String netherName = config.getString("nether.name");
        World nether = Bukkit.getServer().getWorld(netherName);
        if (nether != null) {
            int x = config.getInt("nether.spawn.x");
            int y = config.getInt("nether.spawn.y");
            int z = config.getInt("nether.spawn.z");
            Location netherSpawn = new Location(nether, x, y, z);
            player.teleport(netherSpawn);
        }
    }

    public static boolean isDebugging() {
        FileConfiguration config = plugin.getConfig();
        return config.getBoolean("debug");
    }
}
