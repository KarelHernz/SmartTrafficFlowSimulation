package model;

public class Sensor {
    private final Road road;

    public Sensor(Road road) {
        this.road = road;
    }

    //Devolve o número de veículos que não estão em movimento de todas as vias de uma road
    public Integer countAllStoppedVehicles(){
        int result = 0;
        for(int i = 1; i <= road.getNumberOfLanes(); i++){
            int numVeiculos = 0;
            var vehicles = road.getVehicles(i);

            //Percorre na linkedList o número de veículos que não estão em movimento
            for (var vehicle : vehicles){
                if (!vehicle.isEmMovement()){
                    numVeiculos++;
                }
            }
            result += numVeiculos;
        }
        return result;
    }
}