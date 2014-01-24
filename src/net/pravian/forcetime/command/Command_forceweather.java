package net.pravian.forcetime.command;

import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.forcetime.ForceTime;
import net.pravian.forcetime.WeatherState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(permission = "forcetime.setweather")
public class Command_forceweather extends BukkitCommand<ForceTime> {

    @Override
    public boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length != 2) {
            return showUsage();
        }

        if (server.getWorld(args[0]) == null) {
            msg("World not found!", ChatColor.RED);
            return true;
        }

        for (WeatherState state : WeatherState.values()) {
            if (state.getName().equalsIgnoreCase(args[1])) {
                final String worldName = server.getWorld(args[0]).getName().toLowerCase();

                plugin.weatherStates.put(worldName, state);

                plugin.config.set(worldName + ".weather", state.getName());
                plugin.config.save();

                msg("ForceTime weather for " + worldName + " set to: " + args[1]);
                return true;
            }
        }

        return showUsage();
    }
}
