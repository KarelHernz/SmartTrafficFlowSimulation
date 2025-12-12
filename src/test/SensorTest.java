package test;

import javafx.scene.image.ImageView;
import model.Road;
import model.Sensor;
import model.Vehicle;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorTest {
    @Test
    void main() {
        Road road = new Road(4);
        Sensor sensor = new Sensor(road);

        Map<String, Double> mapGenerico = new HashMap<>();
        mapGenerico.put("xpto", 4.3);

        //region Round 1 - Todos os veiculos em movimento
        Vehicle vehicleRound1 = new Vehicle("Verde", new ImageView(), mapGenerico, mapGenerico);

        for (int i = 0; i <= 4; i++){
            road.addVehicle(1, vehicleRound1);
        }

        for (int i = 0; i <= 2; i++){
            road.addVehicle(3, vehicleRound1);
        }

        var mapRound1 = sensor.countVehicles(4);
        assertEquals(0, mapRound1.get(1));
        assertEquals(0, mapRound1.get(2));
        assertEquals(0, mapRound1.get(3));
        assertEquals(0, mapRound1.get(4));
        //endregion

        //region Round 2 - Veiculos das vias 2 e 3 que não estão em movimento
        Vehicle vehicleRound2 = new Vehicle("Verde", new ImageView(), mapGenerico, mapGenerico);
        vehicleRound2.setEmMovimento(false);

        for (int i = 0; i <= 4; i++){
            road.addVehicle(2, vehicleRound2);
        }

        for (int i = 0; i <= 6; i++){
            road.addVehicle(3, vehicleRound2);
        }

        var mapRoun2 = sensor.countVehicles(4);
        assertEquals(0, mapRoun2.get(1));
        assertEquals(5, mapRoun2.get(2));
        assertEquals(7, mapRoun2.get(3));
        assertEquals(0, mapRoun2.get(4));
        //endregion
    }
}