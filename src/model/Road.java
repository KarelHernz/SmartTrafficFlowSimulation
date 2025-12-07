package model;

import java.util.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Road {
    private final HashMap<Integer, LinkedList<Vehicle>> pista = new HashMap<>();

    public Road(Integer nVia) {
        for(int i = 1; i <= nVia; i++){
            addVia(i);
        }
    }

    public void addVia(Integer nVia) {
        this.pista.put(nVia, new LinkedList<>());
    }

    public List<Vehicle> getVehicles(int nVia) {
        var map = this.pista.get(nVia);
        return new ArrayList<>(map);
    }

    public void addVehicle(int nVia, Vehicle v) {
        this.pista.get(nVia).add(v);
    }

    public void removeVehicle(int nVia) {
        this.pista.get(nVia).removeFirst();
    }

    public void clearRoad() {
        for (List<Vehicle> vehicles : pista.values()) {
            vehicles.clear();
        }
    }
}