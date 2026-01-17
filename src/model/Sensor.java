package model;

public class Sensor {
    private final Road road;

    public Sensor(Road road) {
        this.road = road;
    }

    //Devolve o número de veículos que não estão em movimento de todas as vias de uma road
    public Integer countAllStoppedVehicles(){
        int result = 0;
        for(int via = 1; via <= road.getNumberOfLanes(); via++){
            result += road.getVehiclesStopped(via);
        }
        return result;
    }
}