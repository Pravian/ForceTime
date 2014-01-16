package net.pravian.forcetime;

public enum TimeState {

    DAY(6000L),
    NIGHT(18000L),
    UNSET(0L);
    private long ticks;

    private TimeState(long ticks) {
        this.ticks = ticks;
    }

    public long getTicks() {
        return ticks;
    }
}
