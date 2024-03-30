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
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            player.sendMessage("ItemStack Type:" + item.getType());
        }
        // 非必要不执行
        if (!event.getAction().toString().contains("RIGHT_CLICK")) {
            return;
        }
        Player player = event.getPlayer();
        String item = String.valueOf(player.getInventory().getItemInMainHand().getType());
        FileConfiguration config = plugin.getConfig();
        String passport = config.getString("nether-passport-id");
        if (!item.equalsIgnoreCase(passport)) {
            return;
        }
        int slot = player.getInventory().getHeldItemSlot();

        // take the passport away.
        player.getInventory().setItem(slot, null);
        NetherPassportUtil.permitPlayer(player);
        NetherPassportUtil.transport(player);
    }
}
