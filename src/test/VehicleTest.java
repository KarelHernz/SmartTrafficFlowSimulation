package test;

import javafx.scene.image.ImageView;
import model.Vehicle;
import org.junit.jupiter.api.Test;
import util.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    @Test
    void main() {
        double x = 10;
        double y = 10.0;
        ImageView imageView = new ImageView();
        imageView.setLayoutY(x);
        imageView.setLayoutX(y);

        Coordinate destino = new Coordinate(100.0, 12.5);
        Vehicle vehicle = new Vehicle(imageView, destino);

        //region Inicio
        assertEquals(x, vehicle.getX());
        assertEquals(y, vehicle.getY());
        assertEquals(100.0, vehicle.getDestinationX());
        assertEquals(12.5, vehicle.getDestinationY());
        assertEquals(imageView, vehicle.getImage());
        assertTrue(vehicle.isEmMovement());
        //endregion

        //region Não está em movimento
        vehicle.setMovimento(false);
        assertFalse(vehicle.isEmMovement());
        //endregion

        //region Mudar a posição do veículo
        vehicle.update();
        assertEquals(x, vehicle.getX());
        assertEquals(y, vehicle.getY());

        vehicle.setMovimento(true);
        vehicle.update();
        assertEquals(x+1, vehicle.getX());
        assertEquals(y, vehicle.getY());
        //endregion
    }
}