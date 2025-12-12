package model;

import java.util.HashMap;

public class Sensor {
    private final Road road;

    public Sensor(Road road) {
        this.road = road;
    }

    public HashMap<Integer, Integer> countVehicles(Integer nVias){
        //Guarda a número da via e o número de veiculos que estão à espera no semáforo
        HashMap<Integer, Integer> mapResult = new HashMap<>();
        for(int i = 1; i <= nVias; i++){
            int numVeiculos = 0;
            var vehicles = road.getVehicles(i);

            //Percorre na linkedList o número de veiculos que não estão em movimento
            for (var vehicle : vehicles){
                if (!vehicle.isEmMovimento()){
                    numVeiculos++;
                }
            }
            mapResult.put(i, numVeiculos);
        }
        return mapResult;
    }
}