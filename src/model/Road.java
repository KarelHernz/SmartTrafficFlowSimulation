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

    public Integer getVehiclesStopped(int nVia) {
        return lanes.get(nVia-1).getVehiclesStopped();
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
            Vehicle previusVehicle = null;
            Lane lane = lanes.get(via-1);
            LinkedList<Vehicle> vehicles = getVehicles(via);
            Iterator<Vehicle> iterator = vehicles.iterator();

            //Vai percorrer todos os veículos
            while (iterator.hasNext()) {
                Vehicle vehicle = iterator.next();
                vehicle.update();
                double x = vehicle.getX();
                double y = vehicle.getY();
                double destinationX = vehicle.getDestinationX();
                double destinationY = vehicle.getDestinationY();

                //Verifica se os semáforos estão de cor vermelho ou amarelo para parar ao veículo
                if ((trafficLights.getFirst().isYellow() || trafficLights.getFirst().isRed())) {
                    if(Objects.equals(x, lane.getStop().getX()) && Objects.equals(y, lane.getStop().getY())){
                        vehicle.stop();
                    }
                    else if(previusVehicle != null && !previusVehicle.inMovement()){
                        //Calcula ca diferença absoluta entre o veículo anterior com o presente
                        double distanceX = Math.abs(previusVehicle.getX()-x);
                        double distanceY = Math.abs(previusVehicle.getY()-y);

                        if(distanceX == 0 && distanceY == 57 || distanceX == 57 && distanceY == 0){
                            vehicle.stop();
                        }
                    }
                    previusVehicle = vehicle;
                }
                else if (!vehicle.inMovement()){
                    vehicle.start();
                }

                //Verifica se o veículo chegou ao seu destino
                if (Objects.equals(x, destinationX) && Objects.equals(y, destinationY)) {
                    vehiclesPane.getChildren().remove(vehicle.getImage());
                    iterator.remove();
                }
            }
        }
    }
}