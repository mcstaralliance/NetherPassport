package com.mcstaralliance.netherpassport.task;

import com.mcstaralliance.netherpassport.NetherPassport;
import com.mcstaralliance.netherpassport.util.NetherPassportUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class BossBarTask extends BukkitRunnable {
    private final NetherPassport plugin = NetherPassport.getInstance();
    private static final HashMap<UUID, BossBar> bars = new HashMap<>();
    private final FileConfiguration config = plugin.getConfig();
    private BossBar bar;
    private int totalTime;
    private int remainingTime;
    private String expirationTime;
    private Player player;

    public BossBarTask(Player player) {
        this.player = player;
        this.totalTime = config.getInt("usage.time");
        this.remainingTime = totalTime;
        this.expirationTime = NetherPassportUtil.getPassportExpirationTime(player);

        this.bar = plugin.getServer().createBossBar("下界通行证将在 " + expirationTime + " 后失效", BarColor.RED, BarStyle.SOLID);
        this.bar.setVisible(true);
        this.bar.addPlayer(player);
        bars.put(player.getUniqueId(), this.bar);
    }

    @Override
    public void run() {
        if (remainingTime > 0) {
            double progress = (double) remainingTime / totalTime;
            bar.setProgress(progress);
            remainingTime--;
        } else {
            bar.setVisible(false);
            bar.removeAll();
            bars.remove(player.getUniqueId()); // 任务完成后从HashMap中移除
            cancel();
            return;
        }
    }


}
