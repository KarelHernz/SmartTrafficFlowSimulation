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
    public String toString() {
        if (hours == 0) {
            if (minutes == 0) {
                return seconds + "s";
            } else {
                return minutes + "m:" + seconds + "s";
            }
        } else {
            return hours + "h:" + minutes + "m:" + seconds + "s";
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
