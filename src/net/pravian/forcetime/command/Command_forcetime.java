package net.pravian.forcetime.command;

import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.forcetime.ForceTime;
import net.pravian.forcetime.TimeState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(permission = "forcetime.set")
public class Command_forcetime extends BukkitCommand<ForceTime> {

    @Override
    public boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length != 2) {
            return showUsage();
        }

        if (server.getWorld(args[0]) == null) {
            msg("World not found!", ChatColor.RED);
            return true;
        }

        for (TimeState state : TimeState.values()) {
            if (state.getName().equalsIgnoreCase(args[1])) {
                final String worldName = server.getWorld(args[0]).getName().toLowerCase();

                plugin.timeStates.put(worldName, state);

                plugin.config.set(worldName + ".time", state.getName());
                plugin.config.save();

                msg("ForceTime time for " + worldName + " set to: " + args[1]);
                return true;
            }
        }

        return showUsage();
    }
}
