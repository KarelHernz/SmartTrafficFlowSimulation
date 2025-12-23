package model;

import java.util.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Road {
    private final HashMap<Integer, LinkedList<Vehicle>> pista = new HashMap<>();
    private final Integer nVias;

    public Road(Integer nVia) {
        this.nVias = nVia;
        for(int i = 1; i <= this.nVias; i++){
            addVia(i);
        }
    }

    public void addVia(Integer nVia) {
        this.pista.put(nVia, new LinkedList<>());
    }

    public Integer getNVias() {
        return this.nVias;
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