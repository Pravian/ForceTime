package net.pravian.forcetime;

import java.util.HashMap;
import java.util.Map;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.concurrent.BukkitSyncTask;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.forcetime.command.Command_forcetime;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ForceTime extends BukkitPlugin {
    
    public ForceTime plugin;
    //
    public BukkitLogger logger;
    public BukkitCommandHandler handler;
    public YamlConfig config;
    //
    public Map<String, TimeState> timeStates;
    public Map<String, WeatherState> weatherStates;
    //
    public final long heartbeat = 10L;
    
    @Override
    public void onLoad() {
        plugin = this;
        
        logger = new BukkitLogger(plugin);
        handler = new BukkitCommandHandler(plugin);
        config = new YamlConfig(plugin, "config.yml");
        
        timeStates = new HashMap<String, TimeState>();
        weatherStates = new HashMap<String, WeatherState>();
    }
    
    @Override
    public void onEnable() {
        BukkitLib.init(plugin);
        
        handler.setCommandLocation(Command_forcetime.class.getPackage());
        
        config.load();

        // Parse old config (changed 2.2)
        if (config.isString("time")
                || config.isString("weather")) {
            
            logger.info("Transferring config to new format...");
            
            config.set("world.time", config.getString("time"));
            config.set("world.weather", config.getString("weather"));
            
            config.set("time", null);
            config.set("weather", null);
            
            config.save();
        }
        // old config end

        for (String world : config.getKeys(false)) {
            if (!config.isConfigurationSection(world)) {
                logger.warning("Could not load settings for world: " + world);
                continue;
            }
            
            timeStates.put(world.toLowerCase(), TimeState.fromName(config.getString(world + ".time")));
            weatherStates.put(world.toLowerCase(), WeatherState.fromName(config.getString(world + ".weather")));
        }
        
        
        
        new HeartBeat(plugin).runTaskTimer(plugin, heartbeat, heartbeat);
        
        logger.info(plugin.getName() + " v" + plugin.getVersion() + " by " + plugin.getAuthor() + " is enabled");
        
        final ForceTime pl = plugin;
        new BukkitSyncTask(plugin) {
            @Override
            public void run() {
                new ForceTimeMetrics().start(pl);
            }
        }.start(heartbeat + 40L);
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
