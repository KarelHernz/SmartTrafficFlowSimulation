package test;

import model.Lane;
import model.Vehicle;
import org.junit.jupiter.api.Test;
import util.Coordinate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class LaneTest {
    @Test
    void main() {
        Coordinate start = new Coordinate(0.0, 1.0);
        Coordinate end = new Coordinate(1.0, 0.0);
        Coordinate stop = new Coordinate(100.0, 20.0);
        Lane lane = new Lane(start, end, stop);

        //region Inicio
        assertEquals(0, lane.getNumberOfVehicles());
        assertEquals(3, lane.getMaxVehiclesStopped());
        assertEquals(0, lane.getVehiclesStopped());

        assertEquals(0.0, lane.getStart().getX());
        assertEquals(1.0, lane.getStart().getY());

        assertEquals(1.0, lane.getEnd().getX());
        assertEquals(0.0, lane.getEnd().getY());

        assertEquals(100.0, lane.getStop().getX());
        assertEquals(20.0, lane.getStop().getY());
        //endregion

        //region Adicionando veículos
        for (int i = 0; i < 10; i++) {
            lane.addVehicle(new Vehicle(null, null));
        }
        assertEquals(10, lane.getNumberOfVehicles());
        //endregion

        //region Contar veículos parados
        LinkedList<Vehicle> linkedList = lane.getVehicles();
        for (int i = 0; i < 3; i++) {
            linkedList.get(i).stop();
        }
        assertEquals(3, lane.getMaxVehiclesStopped());
        //endregion

        //region Apagando os veículos
        lane.clear();
        assertEquals(0, lane.getNumberOfVehicles());
        //endregion
    }
}
