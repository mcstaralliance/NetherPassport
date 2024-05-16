package com.mcstaralliance.netherpassport.listener;

import com.mcstaralliance.netherpassport.NetherPassport;
import com.mcstaralliance.netherpassport.util.LuckPermsUtil;
import com.mcstaralliance.netherpassport.util.NetherPassportUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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
        if (event.getItem() == null) {
            return;
        }
        String item = event.getItem().getType().toString();
        if (NetherPassportUtil.isPermanentPassport(item)) {
            NetherPassportUtil.takeAwayPassport(player);
            LuckPermsUtil.addPermission(player, NetherPassportUtil.NETHER_PASSPORT_PERMISSION);
            NetherPassportUtil.transport(player, true);
            return;
        }

        if (!NetherPassportUtil.isPassport(item)) {
            return;
        }


        NetherPassportUtil.takeAwayPassport(player);
        NetherPassportUtil.permitPlayer(player);
        NetherPassportUtil.transport(player);
        NetherPassportUtil.showBossBar(player);
    }
}
