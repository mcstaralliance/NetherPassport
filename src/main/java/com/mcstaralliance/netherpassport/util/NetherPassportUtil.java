package com.mcstaralliance.netherpassport.util;

import com.mcstaralliance.netherpassport.NetherPassport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class NetherPassportUtil {
    private static final NetherPassport plugin = NetherPassport.getInstance();
    public static final String NETHER_PASSPORT_PERMISSION = "netherpassport.pass";

    public static void permitPlayer(Player player) {
        FileConfiguration config = plugin.getConfig();
        int method = config.getInt("usage.method");
        switch (method) {
            case 1:
                LuckPermsUtil.addTempPermission(player, NETHER_PASSPORT_PERMISSION, 5, TimeUnit.MINUTES);
                break;
            case 2:
                int time = config.getInt("usage.time");
                LuckPermsUtil.addTempPermission(player, NETHER_PASSPORT_PERMISSION, time, TimeUnit.MINUTES);
                break;
            default:
                break;
        }
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
            player.sendMessage(ChatColor.GREEN + "下界通行证使用成功，你已被传送至下界。");
        }
    }

    public static boolean isDebugging() {
        FileConfiguration config = plugin.getConfig();
        return config.getBoolean("debug");
    }

    public static boolean isPassport(String item) {
        FileConfiguration config = plugin.getConfig();
        String passport = config.getString("nether-passport-id");
        return passport.equalsIgnoreCase(item);
    }

    public static void takeAwayPassport(Player player) {
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack item = player.getInventory().getItemInMainHand();
        item.setAmount(item.getAmount() - 1);
        player.getInventory().setItem(slot, item);
    }

    public static void sendDebugMessage(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        player.sendMessage("ItemStack Type:" + item.getType());
    }

    public static Location getSpawnLocation() {
        FileConfiguration config = plugin.getConfig();
        String worldName = config.getString("spawn.name");
        World world = Bukkit.getWorld(worldName);
        final int x = config.getInt("spawn.x");
        final int y = config.getInt("spawn.y");
        final int z = config.getInt("spawn.z");
        return new Location(world, x, y, z);

    }
}
