package util;

public class TimeElapsed {
    private long allSeconds;
    private int seconds;
    private int minutes;
    private int hours;

    //Aumenta o tempo transcorrido
    public void increment() {
        this.seconds += 1;
        this.allSeconds += 1;
        if (seconds > 59) {
            minutes += 1;
            seconds = 0;
        }
        if (minutes > 59) {
            hours += 1;
            minutes = 0;
        }
    }

    //Devolve o tempo transcorrido
    @Override
    public String toString() {
        if (hours == 0) {
            return (minutes == 0) ? "%ds".formatted(seconds): "%dm:%ds".formatted(minutes, seconds);
        } else {
            return "%dh:%dm:%ds".formatted(hours, minutes, seconds);
        }
    }

    public Long getAllSeconds() {
        return allSeconds;
    }

    public void reset() {
        allSeconds = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
    }
}
