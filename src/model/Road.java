package model;

import javafx.scene.layout.Pane;
import util.Coordinates;
import java.util.*;

public class Road {
    private final Map<Integer, LinkedList<Vehicle>> pista = new HashMap<>();
    private final Map<Integer, Coordinates> coords = new HashMap<>();
    private final List<TrafficLight> trafficLights = new ArrayList<>();
    private final Sensor sensor;
    private final Integer nVias;
    private final Integer maxVehiclesStoped = 3;

    public Road(Integer nVia, List<TrafficLight> trafficLights) {
        this.nVias = nVia;
        this.sensor = new Sensor(this);
        for(int i = 1; i <= this.nVias; i++){
            this.pista.put(i, new LinkedList<>());
        }
        this.trafficLights.addAll(trafficLights);
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

    //Retorna o número de veículos que estão numa via
    public Integer getNumberOfVehicles(int nVia) {
        return this.pista.get(nVia).size();
    }

    //Remove o primeiro veículo de uma específica via
    public void removeVehicle(int nVia) {
        this.pista.get(nVia).removeFirst();
    }

    //Limpa todos os veículos que estão nas vias
    public void clearRoad() {
        for (List<Vehicle> vehicles : pista.values()) {
            vehicles.clear();
        }
    }

    public void addCoords(Integer nVia, Coordinates coords){
        this.coords.put(nVia, coords);
    }

    public Coordinates getCoordinates(Integer nVia){
        return this.coords.get(nVia);
    }

    public Integer getMaxVehiclesStoped() {
        return this.maxVehiclesStoped;
    }

    public List<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    public Sensor getSensor() {
        return this.sensor;
    }

    //Atualiza os veículos que estão em cada via das roads
    public void update(Pane vehiclesPane){
        for (int via = 1; via <= nVias; via++) {
            List<Vehicle> vehiclesList = getVehicles(via);
            for (Vehicle vehicle : vehiclesList) {
                //Verifica se o veículo está em movimento
                vehicle.update();
                double x = vehicle.getX();
                double y = vehicle.getY();
                double destinationX = vehicle.getDestinationX();
                double destinationY = vehicle.getDestinationY();

                //Valida se o veiculo chegou ao seu destino
                if (Objects.equals(x, destinationX) && Objects.equals(y, destinationY)) {
                    vehiclesPane.getChildren().remove(vehicle.getImage());
                    removeVehicle(via);
                }
            }
        }
    }
}