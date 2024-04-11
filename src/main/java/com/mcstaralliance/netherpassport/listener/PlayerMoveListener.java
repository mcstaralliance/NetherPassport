package com.mcstaralliance.netherpassport.listener;

import com.mcstaralliance.netherpassport.NetherPassport;
import com.mcstaralliance.netherpassport.util.NetherPassportUtil;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    NetherPassport plugin = NetherPassport.getInstance();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        FileConfiguration config = plugin.getConfig();
        String netherName = config.getString("nether.name");
        if (!worldName.equalsIgnoreCase(netherName)) {
            return;
        }

        int method = config.getInt("usage.method");
        switch (method) {
            case 1:
                return;
            case 2:
                if (!NetherPassportUtil.isPermittedPlayer(player)) {
                    player.teleport(NetherPassportUtil.getSpawnLocation());
                    player.sendMessage(ChatColor.RED + "你的下界通行证已到期。");
                }
        }
    }

}
