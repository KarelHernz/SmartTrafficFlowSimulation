package test;

import javafx.scene.image.ImageView;
import model.Lane;
import model.Road;
import model.Vehicle;
import org.junit.jupiter.api.Test;
import util.Coordinate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {
    @Test
    void main() {
        ArrayList<Lane> lanes = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            Coordinate start = new Coordinate(100.0, 20.0);
            Coordinate end = new Coordinate(100.0+(i+1), 20.0);
            Coordinate stop = new Coordinate(100.0, 20.0+(i+1));
            lanes.add(new Lane(start, end, stop));
        }

        Road road = new Road(lanes, new ArrayList<>());
        Coordinate destino = new Coordinate(4.3, 4.3);

        //region Sem veículos
        assertEquals(4, road.getNumberOfLanes());
        assertEquals(3, road.getMaxVehiclesStopped(1));
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

        //region Limpar o Road
        road.clear();
        assertEquals(0, road.getNumberOfVehicles(1));
        assertEquals(0, road.getNumberOfVehicles(2));
        assertEquals(0, road.getNumberOfVehicles(3));
        assertEquals(0, road.getNumberOfVehicles(4));
        //endregion

        //region Obter as coordenadas iniciais e finais de uma via
        assertEquals(100.0, road.getLaneStart(1).getX());
        assertEquals(20.0, road.getLaneEnd(1).getY());

        assertEquals(103.0, road.getLaneEnd(3).getX());
        assertEquals(20.0, road.getLaneEnd(3).getY());
        //endregion
    }
}