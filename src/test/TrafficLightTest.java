package test;

import org.junit.jupiter.api.*;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

// ============================================================================
// REGIÃO: TESTES DA CLASSE TRAFFICLIGHT
// ============================================================================
@DisplayName("Testes para a classe TrafficLight")
class TrafficLightTest {

    private TrafficLight trafficLight;

    // ========================================================================
    // CONFIGURAÇÃO INICIAL
    // ========================================================================
    @BeforeEach
    void setUp() {
        trafficLight = new TrafficLight("TL001", "Main Street");
    }

    // ========================================================================
    // TESTES ESTADOS DO SEMÁFORO
    // ========================================================================
    @Nested
    @DisplayName("Testes dos estados do semáforo")
    class StateTests {

        @Test
        @DisplayName("Testa estado inicial do semáforo")
        void testInitialState() {
            assertEquals(TrafficLight.State.RED, trafficLight.getState());
        }

        @Test
        @DisplayName("Testa transição de estados")
        void testStateTransition() {
            trafficLight.changeToGreen();
            assertEquals(TrafficLight.State.GREEN, trafficLight.getState());

            trafficLight.changeToYellow();
            assertEquals(TrafficLight.State.YELLOW, trafficLight.getState());

            trafficLight.changeToRed();
            assertEquals(TrafficLight.State.RED, trafficLight.getState());
        }
    }

    // ========================================================================
    // TESTES MÉTODOS TEMPORIZADOS
    // ========================================================================
    @Nested
    @DisplayName("Testes de temporização")
    class TimingTests {

        @Test
        @DisplayName("Testa configuração de tempo para cada estado")
        void testSetTiming() {
            trafficLight.setGreenDuration(30);
            trafficLight.setYellowDuration(5);
            trafficLight.setRedDuration(25);

            assertEquals(30, trafficLight.getGreenDuration());
            assertEquals(5, trafficLight.getYellowDuration());
            assertEquals(25, trafficLight.getRedDuration());
        }

        @Test
        @DisplayName("Testa tempo restante no estado atual")
        void testGetRemainingTime() {
            trafficLight.setGreenDuration(30);
            trafficLight.changeToGreen();

            // Simula passagem de 10 segundos
            trafficLight.updateTime(10);

            assertEquals(20, trafficLight.getRemainingTime());
        }
    }

    // ========================================================================
    // TESTES COMPORTAMENTO EM HORÁRIOS ESPECÍFICOS
    // ========================================================================
    @Test
    @DisplayName("Testa comportamento em horário de pico")
    void testPeakHourBehavior() {
        LocalTime peakHour = LocalTime.of(8, 0); // 8:00 AM
        trafficLight.adjustForPeakHour(peakHour);

        // Em horário de pico, o tempo do verde deve ser maior
        assertTrue(trafficLight.getGreenDuration() > 30);
    }

    // ========================================================================
    // TESTES DE EXCEÇÕES
    // ========================================================================
    @Test
    @DisplayName("Testa configuração de tempo inválido")
    void testInvalidTimingConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> {
            trafficLight.setGreenDuration(0); // Tempo deve ser > 0
        });
    }

    // ========================================================================
    // TESTES ASSÍNCRONOS/TEMPORIZADOS
    // ========================================================================
    @Test
    @Timeout(5) // Teste deve completar em 5 segundos
    @DisplayName("Testa ciclo completo do semáforo")
    void testCompleteCycle() throws InterruptedException {
        trafficLight.setGreenDuration(2);
        trafficLight.setYellowDuration(1);
        trafficLight.setRedDuration(2);

        trafficLight.startCycle();

        // Aguarda ciclo completar
        Thread.sleep(6000);

        assertTrue(trafficLight.getCycleCount() > 0);
    }
}