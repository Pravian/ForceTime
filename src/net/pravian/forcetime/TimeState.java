package net.pravian.forcetime;

public enum TimeState {

    DAY("day", 6000L),
    NIGHT("night", 18000L),
    UNSET("off", 0L);
    private String name;
    private long ticks;

    private TimeState(String name, long ticks) {
        this.name = name;
        this.ticks = ticks;
    }

    public long getTicks() {
        return ticks;
    }

    public String getName() {
        return name;
    }

    public static TimeState fromName(String name) {

        for (TimeState state : TimeState.values()) {

            if (state.getName().equals(name)) {
                return state;
            }

        }

        return TimeState.UNSET;
    }
}
