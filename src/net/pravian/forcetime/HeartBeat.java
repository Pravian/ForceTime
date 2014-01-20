package net.pravian.forcetime;

import net.pravian.bukkitlib.util.WorldUtils;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class HeartBeat extends BukkitRunnable {

    private final ForceTime plugin;

    public HeartBeat(ForceTime plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (World world : plugin.getServer().getWorlds()) {

            if (plugin.timeState != TimeState.UNSET) {
                WorldUtils.setTime(world, plugin.timeState.getTicks());
            }

            if (plugin.weatherState != WeatherState.UNSET) {
                WorldUtils.setWeather(world, plugin.weatherState.getType());
            }
        }
    }
}
