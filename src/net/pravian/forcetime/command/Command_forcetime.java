package net.pravian.forcetime.command;

import net.pravian.forcetime.ForceTime;
import net.pravian.forcetime.TimeState;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(permission = "forcetime.set", usage = "/<command> <day | night | off>")
public class Command_forcetime extends BukkitCommand<ForceTime> {

    @Override
    public boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length != 1) {
            return showUsage();
        }

        if (args[0].equals("day")) {
            plugin.state = TimeState.DAY;
        } else if (args[0].equals("night")) {
            plugin.state = TimeState.NIGHT;
        } else if (args[0].equals("off")) {
            plugin.state = TimeState.UNSET;
        } else {
            return showUsage();
        }

        msg("ForceTime time set to: " + args[0]);

        return true;
    }
}
