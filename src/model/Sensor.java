package model;

import java.util.HashMap;

public class Sensor {
    private final Road road;

    public Sensor(Road road) {
        this.road = road;
    }

    //Devolve o número de veiculos que não estão em movimento de todas as vias de uma road
    public Integer countAllVehicles(){
        int result = 0;
        for(int i = 1; i <= road.getNVias(); i++){
            int numVeiculos = 0;
            var vehicles = road.getVehicles(i);

            //Percorre na linkedList o número de veiculos que não estão em movimento
            for (var vehicle : vehicles){
                if (!vehicle.isEmMovimento()){
                    numVeiculos++;
                }
            }
            result += numVeiculos;
        }
        return result;
    }
}