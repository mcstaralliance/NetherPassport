package com.mcstaralliance.netherpassport.listener;

import com.mcstaralliance.netherpassport.NetherPassport;
import com.mcstaralliance.netherpassport.util.NetherPassportUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    NetherPassport plugin = NetherPassport.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (NetherPassportUtil.isDebugging()) {
            NetherPassportUtil.sendDebugMessage(event.getPlayer());
        }

        // 非必要不执行
        if (!event.getAction().toString().contains("RIGHT_CLICK")) {
            return;
        }

        Player player = event.getPlayer();
        String item = String.valueOf(event.getItem().getType());
        if (!NetherPassportUtil.isPassport(item)) {
            return;
        }

        NetherPassportUtil.takeAwayPassport(player);
        NetherPassportUtil.permitPlayer(player);
        NetherPassportUtil.transport(player);
    }
}
