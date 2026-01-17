package util;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private String strategy;
    private final Map<String, Integer> color = new HashMap<>();
    private final Map<String, Integer> created = new HashMap<>();
    private String timeElapsed = "0s";

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getStrategy() {
        return strategy;
    }

    public void addVehiclesColors(String color){
        this.color.put(color, 0);
    }

    public void updateVehiclesColors(String color, int value){
        this.color.put(color, value);
    }

    public Integer getVehiclesValue(String color){
        return this.color.get(color);
    }

    public Map<String, Integer> getColor() {
        return color;
    }

    public void addDirection(String direction){
        created.put(direction, 0);
    }

    public void updateDirection(String direction, int value){
        created.put(direction, value);
    }

    public Integer getDirectionValue(String direction){
        return created.get(direction);
    }

    public Map<String, Integer> getCreated() {
        return created;
    }

    public void setTimeElapsed(String timeElapsed){
        this.timeElapsed = timeElapsed;
    }

    public String getTimeElapsed(){
        return timeElapsed;
    }

    public void reset(){
        color.replaceAll((c, v) -> 0);
        created.replaceAll((d, v) -> 0);
        timeElapsed = "0s";
    }
}
