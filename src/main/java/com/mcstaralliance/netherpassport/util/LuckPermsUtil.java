package com.mcstaralliance.netherpassport.util;

import com.mcstaralliance.netherpassport.NetherPassport;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Set;
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

    public static long getExpirationTime(Player player, String permission) {
        User user = api.getUserManager().getUser(player.getUniqueId());
        if (user != null) {
            Collection<Node> nodes = user.getNodes();
            for (Node node : nodes) {
                if (node.getKey().equals(permission)) {
                    if (node.hasExpired()) {
                        // 如果权限节点已经过期，返回 0 表示已过期
                        return 0;
                    } else {
                        // 如果权限节点未过期，返回失效毫秒级时间戳
                        return node.getExpiry().toEpochMilli();
                    }
                }
            }
        }
        // 未找到返回 -1
        return -1;
    }


}
