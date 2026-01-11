package util;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private final Map<String, Integer> vehiclesColors = new HashMap<>();
    private final Map<String, Integer> intersection = new HashMap<>();
    private String timeElapsed = "0s";

    public void addVehiclesColors(String color){
        vehiclesColors.put(color, 0);
    }

    public void updateVehiclesColors(String color, int value){
        vehiclesColors.put(color, value);
    }

    public Integer getVehiclesValue(String color){
        return vehiclesColors.get(color);
    }

    public Map<String, Integer> getVehiclesColors() {
        return vehiclesColors;
    }

    public void addIntersection(String direction){
        intersection.put(direction, 0);
    }

    public void updateIntersection(String direction, int value){
        intersection.put(direction, value);
    }

    public Integer getIntersectionValue(String direction){
        return intersection.get(direction);
    }

    public Map<String, Integer> getIntersection() {
        return intersection;
    }

    public void setTimeElapsed(String timeElapsed){
        this.timeElapsed = timeElapsed;
    }

    public String getTimeElapsed(){
        return timeElapsed;
    }

    public void reset(){
        vehiclesColors.replaceAll((c, v) -> 0);
        intersection.replaceAll((d, v) -> 0);
        timeElapsed = "0s";
    }
}
