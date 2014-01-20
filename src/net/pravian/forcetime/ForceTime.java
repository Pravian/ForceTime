package net.pravian.forcetime;

import java.io.IOException;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.metrics.Graph;
import net.pravian.bukkitlib.metrics.Metrics;
import net.pravian.bukkitlib.metrics.Plotter;
import net.pravian.forcetime.command.Command_forcetime;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ForceTime extends BukkitPlugin {

    public ForceTime plugin;
    //
    public BukkitLogger logger;
    public BukkitCommandHandler handler;
    public YamlConfig config;
    //
    public TimeState timeState;
    public WeatherState weatherState;
    //
    public final long heartbeat = 10L;

    @Override
    public void onLoad() {
        plugin = this;

        logger = new BukkitLogger(plugin);
        handler = new BukkitCommandHandler(plugin);
        config = new YamlConfig(plugin, "config.yml");
    }

    @Override
    public void onEnable() {
        BukkitLib.init(plugin);

        handler.setCommandLocation(Command_forcetime.class.getPackage());

        config.load();

        timeState = TimeState.fromName(config.getString("time"));
        weatherState = WeatherState.fromName(config.getString("weather"));

        new HeartBeat(plugin).runTaskTimer(plugin, heartbeat, heartbeat);

        logger.info(plugin.getName() + " v" + plugin.getVersion() + " by " + plugin.getAuthor() + " is enabled");

        try {
            final Metrics metrics = new Metrics(plugin);

            final Graph time = metrics.createGraph("Time");
            time.addPlotter(new Plotter(StringUtils.capitalize(timeState.getName())) {
                @Override
                public int getValue() {
                    return 1;
                }
            });

            final Graph weather = metrics.createGraph("Weather");
            weather.addPlotter(new Plotter(StringUtils.capitalize(weatherState.getName())) {
                @Override
                public int getValue() {
                    return 1;
                }
            });


            metrics.start();
        } catch (IOException ex) {
            logger.warning("Failed to submit metrics");
        }
    }

    @Override
    public void onDisable() {
        logger.info(plugin.getName() + " is disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        return handler.handleCommand(sender, command, commandLabel, args);
    }
}
