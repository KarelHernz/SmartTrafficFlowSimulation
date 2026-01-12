package test;

import javafx.scene.image.ImageView;
import model.Road;
import model.Sensor;
import model.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorTest {
    @Test
    void main() {
        Road road = new Road(4, new ArrayList<>() {});
        Sensor sensor = new Sensor(road);
        HashMap<String, Double> destino = new HashMap<>();
        destino.put("X", 4.3);
        destino.put("Y", 4.3);
        int result;

        //region Todos os veículos em movimento
        Vehicle vehicleRound1 = new Vehicle(new ImageView(), destino);

        for (int i = 0; i <= 4; i++){
            road.addVehicle(1, vehicleRound1);
        }

        for (int i = 0; i <= 2; i++){
            road.addVehicle(3, vehicleRound1);
        }

        result = sensor.countAllStoppedVehicles();
        assertEquals(0, result);
        //endregion

        //region Veículos das vias 2 e 3 que não estão em movimento
        Vehicle vehicleRound2 = new Vehicle(new ImageView(), destino);
        vehicleRound2.setMovimento(false);

        for (int i = 0; i <= 4; i++){
            road.addVehicle(2, vehicleRound2);
        }

        for (int i = 0; i <= 6; i++){
            road.addVehicle(3, vehicleRound2);
        }

        result = sensor.countAllStoppedVehicles();
        assertEquals(12, result);
        //endregion
    }
}