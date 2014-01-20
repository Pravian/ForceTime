package net.pravian.forcetime;

import net.pravian.bukkitlib.util.WorldUtils.WeatherType;

public enum WeatherState {

    SUN("sun", WeatherType.SUN),
    RAIN("rain", WeatherType.RAIN),
    THUNDER("thunder", WeatherType.STORM),
    UNSET("off", WeatherType.SUN);

    public static WeatherState fromName(String name) {

        for (WeatherState state : WeatherState.values()) {

            if (state.getName().equals(name)) {
                return state;
            }

        }

        return WeatherState.UNSET;
    }
    private String name;
    private WeatherType type;

    private WeatherState(String name, WeatherType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public WeatherType getType() {
        return type;
    }
}
