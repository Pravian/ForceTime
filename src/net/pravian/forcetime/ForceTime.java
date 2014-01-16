package net.pravian.forcetime;

import java.io.IOException;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.metrics.Metrics;
import net.pravian.forcetime.command.Command_forcetime;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ForceTime extends BukkitPlugin {

    public ForceTime plugin;
    //
    public BukkitLogger logger;
    public BukkitCommandHandler handler;
    //
    public TimeState state;
    //
    public final long heartbeat = 20L;

    @Override
    public void onLoad() {
        plugin = this;
        logger = new BukkitLogger(plugin);

        handler = new BukkitCommandHandler(plugin);
    }

    @Override
    public void onEnable() {
        BukkitLib.init(plugin);

        handler.setCommandLocation(Command_forcetime.class.getPackage());

        state = TimeState.UNSET;

        new HeartBeat(plugin).runTaskTimer(plugin, heartbeat, heartbeat);

        logger.info(plugin.getName() + " v" + plugin.getVersion() + " by " + plugin.getAuthor() + " is Enabled");

        try {
            final Metrics metrics = new Metrics(plugin);

            metrics.start();
        } catch (IOException ex) {
            logger.warning("Failed to submit metrics");
        }
    }

    @Override
    public void onDisable() {
        logger.info(plugin.getName() + " is Enabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        return handler.handleCommand(sender, command, commandLabel, args);
    }
}
