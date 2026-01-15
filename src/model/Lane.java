package model;

import util.Coordinate;
import java.util.*;

public class Lane {
    private final LinkedList<Vehicle> vehicles = new LinkedList<>();
    private final HashMap<String, Coordinate> coordinates = new HashMap<>();
    private final int MAX_VEHICLES_STOPPED = 3;

    public Lane(Coordinate start, Coordinate end, Coordinate stop) {
        this.coordinates.put("Start", start);
        this.coordinates.put("End", end);
        this.coordinates.put("Stop", stop);
    }

    public void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    public LinkedList<Vehicle> getVehicles(){
        return vehicles;
    }

    public int getNumberOfVehicles(){
        return vehicles.size();
    }

    public void clear(){
        vehicles.clear();
    }

    public int getMaxVehiclesStopped() {
        return MAX_VEHICLES_STOPPED;
    }

    public int getVehiclesStopped(){
        return (int) vehicles.stream().filter(v -> !v.inMovement()).count();
    }

    public Coordinate getStart(){
        return coordinates.get("Start");
    }

    public Coordinate getEnd(){
        return coordinates.get("End");
    }

    public Coordinate getStop(){
        return coordinates.get("Stop");
    }
}
