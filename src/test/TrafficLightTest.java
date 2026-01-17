package test;

import model.TrafficLight;
import model.state.Green;
import model.state.Red;
import model.state.Yellow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrafficLightTest {
    @Test
    void main() {
        TrafficLight greenLight = new TrafficLight(new Green(), null);
        TrafficLight yellowLight = new TrafficLight(new Yellow(), null);
        TrafficLight redLight = new TrafficLight(new Red(), null);

        //region Inicio
        assertTrue(greenLight.isGreen());
        assertTrue(yellowLight.isYellow());
        assertTrue(redLight.isRed());

        assertFalse(greenLight.isYellow());
        assertFalse(yellowLight.isRed());
        assertFalse(redLight.isGreen());

        assertEquals(0, greenLight.getTime());
        assertEquals(0, yellowLight.getTime());
        assertEquals(0, redLight.getTime());
        //endregion

        //region Incrementa o tempo
        for (int i = 0; i < 5; i++){
            greenLight.incrementTime();
        }
        for (int i = 0; i < 10; i++){
            redLight.incrementTime();
        }

        assertEquals(5, greenLight.getTime());
        assertEquals(0, yellowLight.getTime());
        assertEquals(10, redLight.getTime());
        //endregion

        //region Faz reset ao tempo
        greenLight.resetTime();
        redLight.resetTime();

        assertEquals(0, greenLight.getTime());
        assertEquals(0, redLight.getTime());
        //endregion
    }
}
