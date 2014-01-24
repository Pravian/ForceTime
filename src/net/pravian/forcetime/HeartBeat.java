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

            final String worldName = world.getName().toLowerCase();

            if (!plugin.timeStates.containsKey(worldName)) {
                plugin.timeStates.put(worldName, TimeState.UNSET);
                plugin.config.set(worldName + ".time", TimeState.UNSET.getName());
                plugin.config.save();
            }

            if (!plugin.weatherStates.containsKey(worldName)) {
                plugin.weatherStates.put(worldName, WeatherState.UNSET);
                plugin.config.set(worldName + ".weather", WeatherState.UNSET.getName());
                plugin.config.save();
            }

            if (plugin.timeStates.get(worldName) != TimeState.UNSET) {
                WorldUtils.setTime(world, plugin.timeStates.get(worldName).getTicks());
            }

            if (plugin.weatherStates.get(worldName) != WeatherState.UNSET) {
                WorldUtils.setWeather(world, plugin.weatherStates.get(worldName).getType());
            }

        }
    }
}
