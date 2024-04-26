package com.mcstaralliance.netherpassport.util;

import com.mcstaralliance.netherpassport.NetherPassport;
import com.mcstaralliance.netherpassport.task.BossBarTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class NetherPassportUtil {
    private static final NetherPassport plugin = NetherPassport.getInstance();
    public static final String NETHER_PASSPORT_PERMISSION = "netherpassport.pass";

    public static void permitPlayer(Player player) {
        FileConfiguration config = plugin.getConfig();
        int method = config.getInt("usage.method");
        switch (method) {
            case 1:
                LuckPermsUtil.addPermission(player, NETHER_PASSPORT_PERMISSION, 5, TimeUnit.MINUTES);
                break;
            case 2:
                int time = config.getInt("usage.time");
                LuckPermsUtil.addPermission(player, NETHER_PASSPORT_PERMISSION, time, TimeUnit.MINUTES);
                break;
            default:
                break;
        }
    }

    public static void showBossBar(Player player) {
        FileConfiguration config = plugin.getConfig();
        int method = config.getInt("usage.method");
        if (method != 2) {
            return;
        }
        BossBarTask task = new BossBarTask(player);
        task.runTaskTimer(plugin, 0, 20 * 60);
    }

    public static String getPassportExpirationTime(Player player) {
        long millis = LuckPermsUtil.getExpirationTime(player, NETHER_PASSPORT_PERMISSION);
        if (millis == 0 || millis == -1) {
            return "Invalid Time";
        }
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalTime time = localDateTime.toLocalTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public static boolean isPermittedPlayer(Player player) {
        return player.hasPermission(NETHER_PASSPORT_PERMISSION);
    }

    public static void transport(Player player) {
        FileConfiguration config = plugin.getConfig();
        player.teleport(getNetherLocation());
        int method = config.getInt("usage.method");
        int minute = config.getInt("usage.time");
        switch (method) {
            case 1:
                player.sendMessage(ChatColor.GREEN + "下界通行证使用成功，你已被传送至下界。");
                break;
            case 2:
                String time = getFormattedTimeAfterMinutes(minute);
                String message = "下界通行证使用成功，你已被传送至下界。" + time + " 以前可以无限次往返。";
                player.sendMessage(ChatColor.GREEN + message);
                break;
        }
    }
    public static String getFormattedTimeAfterMinutes(int minutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime newTime = LocalTime.now().plusMinutes(minutes);
        return newTime.format(formatter);
    }
    public static Location getNetherLocation() {
        FileConfiguration config = plugin.getConfig();
        String netherName = config.getString("nether.name");
        World nether = Bukkit.getServer().getWorld(netherName);
        if (nether == null) {
            plugin.getLogger().warning("获取下界世界失败，返回出生点位置。");
            return getSpawnLocation();
        }
        int x = config.getInt("nether.spawn.x");
        int y = config.getInt("nether.spawn.y");
        int z = config.getInt("nether.spawn.z");

        return new Location(nether, x, y, z);
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

    public static boolean isPermanentPassport(String item) {
        FileConfiguration config = plugin.getConfig();
        String passport = config.getString("permanent-passport-id");
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
