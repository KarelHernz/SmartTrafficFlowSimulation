package model;

import javafx.scene.layout.Pane;
import util.Coordinate;
import java.util.*;

public class Road {
    private final ArrayList<Lane> lanes;
    private final ArrayList<TrafficLight> trafficLights = new ArrayList<>();
    private final Sensor sensor;

    public Road(ArrayList<Lane> lanes,  ArrayList<TrafficLight> trafficLights) {
        this.lanes = lanes;
        this.sensor = new Sensor(this);
        this.trafficLights.addAll(trafficLights);
    }

    public int getNumberOfLanes() {
        return lanes.size();
    }

    public void addVehicle(int nVia, Vehicle vehicle){
        lanes.get(nVia-1).addVehicle(vehicle);
    }

    public LinkedList<Vehicle> getVehicles(Integer nVia) {
        return this.lanes.get(nVia-1).getVehicles();
    }

    //Retorna o número de veículos que estão numa via
    public int getNumberOfVehicles(int nVia) {
        return this.lanes.get(nVia-1).getNumberOfVehicles();
    }

    public Integer getMaxVehiclesStopped(int nVia) {
        return lanes.get(nVia-1).getMaxVehiclesStopped();
    }

    public Coordinate getLaneStart(Integer nVia){
        return lanes.get(nVia-1).getStart();
    }

    public Coordinate getLaneEnd(Integer nVia){
        return lanes.get(nVia-1).getEnd();
    }

    //Limpa todos os veículos que estão nas vias
    public void clear() {
        for (Lane lane : lanes) {
            lane.clear();
        }
    }

    public ArrayList<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    public Sensor getSensor() {
        return this.sensor;
    }

    //Atualiza os veículos que estão em cada via das roads
    public void update(Pane vehiclesPane){
        for (int via = 1; via <= getNumberOfLanes(); via++) {
            Iterator<Vehicle> iterator = getVehicles(via).iterator();

            while (iterator.hasNext()) {
                Vehicle vehicle = iterator.next();
                //Verifica se o veículo está em movimento
                vehicle.update();
                double x = vehicle.getX();
                double y = vehicle.getY();
                double destinationX = vehicle.getDestinationX();
                double destinationY = vehicle.getDestinationY();

                //Valida se o veiculo chegou ao seu destino
                if (Objects.equals(x, destinationX) && Objects.equals(y, destinationY)) {
                    vehiclesPane.getChildren().remove(vehicle.getImage());
                    iterator.remove();
                }
            }
        }
    }
}