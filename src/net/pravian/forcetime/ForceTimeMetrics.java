package net.pravian.forcetime;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import net.pravian.bukkitlib.metrics.Graph;
import net.pravian.bukkitlib.metrics.IncrementalPlotter;
import net.pravian.bukkitlib.metrics.Metrics;

public class ForceTimeMetrics {

    public void start(ForceTime plugin) {
        try {
            final Metrics metrics = new Metrics(plugin);

            // Time - Start
            final EnumMap<TimeState, Integer> timeCount = new EnumMap<TimeState, Integer>(TimeState.class);

            for (TimeState state : plugin.timeStates.values()) {
                if (!timeCount.containsKey(state)) {
                    timeCount.put(state, 1);
                } else {
                    timeCount.put(state, timeCount.get(state) + 1);
                }
            }

            final Graph timeGraph = metrics.createGraph("Time");
            for (final TimeState time : timeCount.keySet()) {
                timeGraph.addPlotter(new IncrementalPlotter(time.getName(), timeCount.get(time)));
            }
            // Time - End

            // Weather - Start
            final Map<WeatherState, Integer> weatherCount = new EnumMap<WeatherState, Integer>(WeatherState.class);

            for (WeatherState state : plugin.weatherStates.values()) {
                if (!weatherCount.containsKey(state)) {
                    weatherCount.put(state, 1);
                } else {
                    weatherCount.put(state, weatherCount.get(state) + 1);
                }
            }

            final Graph weatherGraph = metrics.createGraph("Weather");
            for (final WeatherState weather : weatherCount.keySet()) {
                weatherGraph.addPlotter(new IncrementalPlotter(weather.getName(), weatherCount.get(weather)));
            }
            // Weather - End

            metrics.start();
        } catch (IOException ex) {
            plugin.logger.warning("Failed to submit metrics");
        }
    }
}
