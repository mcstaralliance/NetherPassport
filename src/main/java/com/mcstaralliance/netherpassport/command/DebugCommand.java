package com.mcstaralliance.netherpassport.command;

import com.mcstaralliance.netherpassport.NetherPassport;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {
    NetherPassport plugin = NetherPassport.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "该命令仅能在游戏内使用。");
            return false;
        }

        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "你没有执行此命令的权限。");
            return false;
        }

        FileConfiguration config = plugin.getConfig();
        boolean debugStatus = config.getBoolean("debug");
        config.set("debug", !debugStatus);
        plugin.saveConfig();
        return true;
    }
}
