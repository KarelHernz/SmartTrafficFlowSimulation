package test;

import javafx.scene.image.ImageView;
import model.Road;
import model.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {
    @Test
    void main() {
        Road road = new Road(4, new ArrayList<>());
        HashMap<String, Double> destino = new HashMap<>();
        destino.put("X", 4.3);
        destino.put("Y", 4.3);

        //region Sem veículos
        assertEquals(4, road.getNVias());
        assertEquals(3, road.getMaxVehiclesStoped());
        assertEquals(0, road.getNumberOfVehicles(1));
        assertEquals(0, road.getNumberOfVehicles(2));
        assertEquals(0, road.getNumberOfVehicles(3));
        assertEquals(0, road.getNumberOfVehicles(4));
        //endregion

        //region Com veículos
        road.addVehicle(1, new Vehicle(new ImageView(), destino));
        for (int i = 0; i < 3; i++){
            road.addVehicle(2, new Vehicle(new ImageView(), destino));
        }
        for (int i = 0; i < 10; i++){
            road.addVehicle(3, new Vehicle(new ImageView(), destino));
        }
        assertEquals(1, road.getNumberOfVehicles(1));
        assertEquals(3, road.getNumberOfVehicles(2));
        assertEquals(10, road.getNumberOfVehicles(3));
        assertEquals(0, road.getNumberOfVehicles(4));
        //endregion

        //region Eliminar o primeiro veículo
        road.removeVehicle(1);
        for (int i = 0; i < 4; i++){
            road.removeVehicle(3);
        }
        assertEquals(0, road.getNumberOfVehicles(1));
        assertEquals(6, road.getNumberOfVehicles(3));
        //endregion

        //region Limpar o Road
        road.clearRoad();
        assertEquals(0, road.getNumberOfVehicles(1));
        assertEquals(0, road.getNumberOfVehicles(2));
        assertEquals(0, road.getNumberOfVehicles(3));
        assertEquals(0, road.getNumberOfVehicles(4));
        //endregion

        //region Coordinates
        var l = road.getCoordinates(1);
    }
}