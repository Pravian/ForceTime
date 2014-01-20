package net.pravian.forcetime.command;

import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.forcetime.ForceTime;
import net.pravian.forcetime.WeatherState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(permission = "forcetime.setweather", usage = "/<command> <sun | rain | thunder>")
public class Command_forceweather extends BukkitCommand<ForceTime> {

    @Override
    protected boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length != 1) {
            return showUsage();
        }

        for (WeatherState state : WeatherState.values()) {
            if (state.getName().equalsIgnoreCase(args[0])) {

                plugin.weatherState = state;
                plugin.config.set("weather", state.getName());
                plugin.config.save();

                msg("ForceTime weather set to: " + args[0]);
                return true;
            }
        }

        return showUsage();
    }
}
