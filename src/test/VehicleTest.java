package test;

import javafx.scene.image.ImageView;
import model.Vehicle;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    @Test
    void main() {
        double x = 10;
        double y = 10.0;
        ImageView imageView = new ImageView();
        imageView.setLayoutY(x);
        imageView.setLayoutX(y);

        HashMap<String, Double> destino = new HashMap<>();
        double destinationX = 100.0;
        double destinationY = 12.5;
        destino.put("X", destinationX);
        destino.put("Y", destinationY);
        Vehicle vehicle = new Vehicle(imageView, destino);

        //region Inicio
        assertEquals(x, vehicle.getX());
        assertEquals(y, vehicle.getY());
        assertEquals(destinationX, vehicle.getDestinationX());
        assertEquals(destinationY, vehicle.getDestinationY());
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