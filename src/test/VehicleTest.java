package test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;

// ============================================================================
// REGIÃO: TESTES DA CLASSE VEHICLE
// ============================================================================
@DisplayName("Testes para a classe Vehicle")
class VehicleTest {

    // ========================================================================
    // TESTES CONSTRUTOR
    // ========================================================================
    @Nested
    @DisplayName("Testes do construtor")
    class ConstructorTests {

        @Test
        @DisplayName("Testa criação de veículo com parâmetros válidos")
        void testVehicleCreation() {
            Vehicle vehicle = new Vehicle("ABC123", "Toyota", "Corolla", 2020);

            assertNotNull(vehicle);
            assertEquals("ABC123", vehicle.getLicensePlate());
            assertEquals("Toyota", vehicle.getBrand());
            assertEquals("Corolla", vehicle.getModel());
            assertEquals(2020, vehicle.getYear());
        }

        @Test
        @DisplayName("Testa criação com placa inválida")
        void testVehicleCreationWithInvalidLicensePlate() {
            assertThrows(IllegalArgumentException.class, () -> {
                new Vehicle(null, "Toyota", "Corolla", 2020);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                new Vehicle("", "Toyota", "Corolla", 2020);
            });
        }

        @Test
        @DisplayName("Testa criação com ano inválido")
        void testVehicleCreationWithInvalidYear() {
            assertThrows(IllegalArgumentException.class, () -> {
                new Vehicle("ABC123", "Toyota", "Corolla", 1885);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                new Vehicle("ABC123", "Toyota", "Corolla",
                        java.time.Year.now().getValue() + 1);
            });
        }
    }

    // ========================================================================
    // TESTES PARAMETRIZADOS
    // ========================================================================
    @Nested
    @DisplayName("Testes parametrizados")
    class ParameterizedTests {

        @ParameterizedTest
        @ValueSource(strings = {"ABC123", "XYZ987", "DEF456"})
        @DisplayName("Testa diferentes placas válidas")
        void testValidLicensePlates(String licensePlate) {
            Vehicle vehicle = new Vehicle(licensePlate, "Toyota", "Corolla", 2020);
            assertEquals(licensePlate, vehicle.getLicensePlate());
        }

        @ParameterizedTest
        @CsvSource({
                "ABC123, Toyota, Corolla, 2020, 0",
                "XYZ987, Honda, Civic, 2018, 30000",
                "DEF456, Ford, Focus, 2015, 80000"
        })
        @DisplayName("Testa criação com diferentes parâmetros")
        void testVehicleCreationWithParameters(
                String plate, String brand, String model, int year, int mileage) {
            Vehicle vehicle = new Vehicle(plate, brand, model, year);
            vehicle.setMileage(mileage);

            assertEquals(plate, vehicle.getLicensePlate());
            assertEquals(brand, vehicle.getBrand());
            assertEquals(mileage, vehicle.getMileage());
        }
    }

    // ========================================================================
    // TESTES MÉTODOS DE MOVIMENTAÇÃO
    // ========================================================================
    @Nested
    @DisplayName("Testes de movimentação do veículo")
    class MovementTests {

        private Vehicle vehicle;

        @BeforeEach
        void setUp() {
            vehicle = new Vehicle("ABC123", "Toyota", "Corolla", 2020);
        }

        @Test
        @DisplayName("Testa aceleração do veículo")
        void testAccelerate() {
            vehicle.accelerate(50);
            assertEquals(50, vehicle.getSpeed());

            vehicle.accelerate(30);
            assertEquals(80, vehicle.getSpeed());
        }

        @Test
        @DisplayName("Testa desaceleração do veículo")
        void testDecelerate() {
            vehicle.accelerate(100);
            vehicle.decelerate(30);
            assertEquals(70, vehicle.getSpeed());
        }

        @Test
        @DisplayName("Testa frenagem completa")
        void testFullBrake() {
            vehicle.accelerate(100);
            vehicle.fullBrake();
            assertEquals(0, vehicle.getSpeed());
        }
    }

    // ========================================================================
    // TESTES DE CONSUMO E MANUTENÇÃO
    // ========================================================================
    @Nested
    @DisplayName("Testes de consumo e manutenção")
    class ConsumptionAndMaintenanceTests {

        @Test
        @DisplayName("Testa cálculo de consumo de combustível")
        void testCalculateFuelConsumption() {
            Vehicle vehicle = new Vehicle("ABC123", "Toyota", "Corolla", 2020);
            vehicle.setFuelEfficiency(15.0); // km/l

            double distance = 150.0;
            double expectedFuel = distance / 15.0;

            assertEquals(expectedFuel, vehicle.calculateFuelNeeded(distance));
        }

        @Test
        @DisplayName("Testa necessidade de manutenção")
        void testMaintenanceNeeded() {
            Vehicle vehicle = new Vehicle("ABC123", "Toyota", "Corolla", 2020);

            // Com 0 km não precisa manutenção
            assertFalse(vehicle.isMaintenanceNeeded());

            // Com 15,000 km precisa manutenção
            vehicle.setMileage(15000);
            assertTrue(vehicle.isMaintenanceNeeded());
        }
    }

    // ========================================================================
    // TESTES DE EXCEÇÕES E CASOS LIMITE
    // ========================================================================
    @Test
    @DisplayName("Testa aceleração além do limite")
    void testAccelerateBeyondLimit() {
        Vehicle vehicle = new Vehicle("ABC123", "Toyota", "Corolla", 2020);
        vehicle.setMaxSpeed(180);

        // Não deve permitir acelerar além do limite máximo
        vehicle.accelerate(200);
        assertEquals(180, vehicle.getSpeed());
    }

    @Test
    @DisplayName("Testa desaceleração abaixo de zero")
    void testDecelerateBelowZero() {
        Vehicle vehicle = new Vehicle("ABC123", "Toyota", "Corolla", 2020);
        vehicle.accelerate(50);
        vehicle.decelerate(60); // Tentar desacelerar mais que a velocidade atual

        assertEquals(0, vehicle.getSpeed()); // Não pode ficar negativo
    }

    // ========================================================================
    // TESTES DO MÉTODO toString()
    // ========================================================================
    @Test
    @DisplayName("Testa representação em string do veículo")
    void testToString() {
        Vehicle vehicle = new Vehicle("ABC123", "Toyota", "Corolla", 2020);
        String expected = "Vehicle{plate='ABC123', brand='Toyota', model='Corolla', year=2020}";

        assertEquals(expected, vehicle.toString());
    }
}