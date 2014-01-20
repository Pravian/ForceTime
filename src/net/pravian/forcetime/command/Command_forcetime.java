package net.pravian.forcetime.command;

import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.forcetime.ForceTime;
import net.pravian.forcetime.TimeState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(permission = "forcetime.set", usage = "/<command> <day | night | off>")
public class Command_forcetime extends BukkitCommand<ForceTime> {

    @Override
    public boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length != 1) {
            return showUsage();
        }

        for (TimeState state : TimeState.values()) {
            if (state.getName().equalsIgnoreCase(args[0])) {

                plugin.timeState = state;
                plugin.config.set("time", state.getName());
                plugin.config.save();

                msg("ForceTime time set to: " + args[0]);
                return true;
            }
        }

        return showUsage();
    }
}
