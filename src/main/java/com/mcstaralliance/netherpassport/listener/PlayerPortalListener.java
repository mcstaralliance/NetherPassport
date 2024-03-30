package com.mcstaralliance.netherpassport.listener;

import com.mcstaralliance.netherpassport.NetherPassport;
import com.mcstaralliance.netherpassport.util.NetherPassportUtil;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PlayerPortalListener implements Listener {
    private NetherPassport plugin = NetherPassport.getInstance();
    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        FileConfiguration config = plugin.getConfig();
        String world = event.getTo().getWorld().getName();
        String nether = config.getString("nether.name");
        if (!world.equalsIgnoreCase(nether)) {
            return;
        }
        Player player = event.getPlayer();
        if (NetherPassportUtil.isPermittedPlayer(player)) {
            return;
        }
        player.sendMessage(ChatColor.RED + "你尚未取得前往下界的权限，请考虑使用下界通行证。");
        event.setCancelled(true);
    }
}
