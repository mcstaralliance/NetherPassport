package com.mcstaralliance.netherpassport.util;

import com.mcstaralliance.netherpassport.NetherPassport;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class LuckPermsUtil {
    private static final LuckPerms api = NetherPassport.getLuckPermsApi();

    public static void addTempPermission(Player player, String permission, long duration, TimeUnit timeUnit) {
        User user = api.getUserManager().getUser(player.getUniqueId());
        if (user != null) {
            Node node = Node.builder(permission)
                    .expiry(duration, timeUnit)
                    .build();
            user.data().add(node);
            api.getUserManager().saveUser(user);
        }
    }
}
