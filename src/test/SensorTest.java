package test;

import javafx.scene.image.ImageView;
import model.Lane;
import model.Road;
import model.Sensor;
import model.Vehicle;
import org.junit.jupiter.api.Test;
import util.Coordinate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SensorTest {
    @Test
    void main() {
        ArrayList<Lane> lanes = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            lanes.add(new Lane(null, null, null));
        }

        Road road = new Road(lanes, new ArrayList<>() {});
        Sensor sensor = new Sensor(road);
        Coordinate destino = new Coordinate(4.3, 4.3);
        int result;

        //region Todos os veículos em movimento
        Vehicle vehicleRound1 = new Vehicle(new ImageView(), destino);

        for (int i = 0; i <= 4; i++){
            road.addVehicle(1, vehicleRound1);
        }

        for (int i = 0; i <= 2; i++){
            road.addVehicle(3, vehicleRound1);
        }

        assertEquals(0, sensor.countAllStoppedVehicles());
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