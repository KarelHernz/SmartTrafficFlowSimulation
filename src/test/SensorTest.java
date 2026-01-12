package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para a classe Sensor.
 * Testa o método countAllVehicles() que conta veículos parados em todas as vias.
 */
@DisplayName("Testes para a classe Sensor")
class SensorTest {

    // =========================================================================
    // REGIÃO: Classes Auxiliares para Testes
    // =========================================================================

    /**
     * Classe Vehicle personalizada para controle do estado de movimento.
     * Estende a classe Vehicle original para sobrescrever isEmMovimento().
     */
    private static class VehicleTeste extends Vehicle {
        private final boolean emMovimento;

        public VehicleTeste(String placa, String tipo, boolean emMovimento) {
            super(placa, tipo);
            this.emMovimento = emMovimento;
        }

        @Override
        public boolean isEmMovimento() {
            return emMovimento;
        }
    }

    /**
     * Classe Road mockada manualmente para testes controlados.
     */
    private static class RoadMock extends Road {
        private final List<Vehicle>[] vias;

        @SuppressWarnings("unchecked")
        public RoadMock(int nVias) {
            super(nVias);
            this.vias = new List[nVias];
            for (int i = 0; i < nVias; i++) {
                vias[i] = new java.util.ArrayList<>();
            }
        }

        @Override
        public Integer getNVias() {
            return vias.length;
        }

        @Override
        public List<Vehicle> getVehicles(int nVia) {
            if (nVia < 1 || nVia > vias.length) {
                throw new IndexOutOfBoundsException("Via " + nVia + " não existe");
            }
            return new java.util.ArrayList<>(vias[nVia - 1]);
        }

        public void addVehicleToVia(int nVia, Vehicle vehicle) {
            if (nVia < 1 || nVia > vias.length) {
                throw new IndexOutOfBoundsException("Via " + nVia + " não existe");
            }
            vias[nVia - 1].add(vehicle);
        }

        @Override
        public void clearRoad() {
            for (List<Vehicle> via : vias) {
                via.clear();
            }
        }
    }

    // =========================================================================
    // REGIÃO: Testes do Construtor
    // =========================================================================

    @Test
    @DisplayName("Construtor deve criar sensor com road válida")
    void testConstrutor_ComRoadValida() {
        // Arrange
        Road road = new Road(3);

        // Act
        Sensor sensor = new Sensor(road);

        // Assert
        assertNotNull(sensor);
        // Como road é private final, testamos indiretamente
        assertDoesNotThrow(sensor::countAllVehicles);
    }

    @Test
    @DisplayName("Construtor com road nula deve lançar exceção")
    void testConstrutor_ComRoadNula() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new Sensor(null);
        });
    }

    // =========================================================================
    // REGIÃO: Testes Básicos do Método countAllVehicles()
    // =========================================================================

    @Nested
    @DisplayName("Testes Básicos de countAllVehicles")
    class CountAllVehiclesBasicosTests {

        private Road road;
        private Sensor sensor;

        @BeforeEach
        void setUp() {
            road = new Road(3);
            sensor = new Sensor(road);
        }

        @Test
        @DisplayName("Road vazia deve retornar 0")
        void testCountAllVehicles_RoadVazia() {
            // Act
            int resultado = sensor.countAllVehicles();

            // Assert
            assertEquals(0, resultado);
        }

        @Test
        @DisplayName("Todos veículos em movimento deve retornar 0")
        void testCountAllVehicles_TodosEmMovimento() {
            // Arrange
            RoadMock roadMock = new RoadMock(2);
            roadMock.addVehicleToVia(1, new VehicleTeste("V1", "Carro", true));
            roadMock.addVehicleToVia(1, new VehicleTeste("V2", "Moto", true));
            roadMock.addVehicleToVia(2, new VehicleTeste("V3", "Caminhão", true));
            Sensor sensorMock = new Sensor(roadMock);

            // Act
            int resultado = sensorMock.countAllVehicles();

            // Assert
            assertEquals(0, resultado);
        }

        @Test
        @DisplayName("Todos veículos parados deve retornar contagem total")
        void testCountAllVehicles_TodosParados() {
            // Arrange
            RoadMock roadMock = new RoadMock(2);
            roadMock.addVehicleToVia(1, new VehicleTeste("V1", "Carro", false));
            roadMock.addVehicleToVia(1, new VehicleTeste("V2", "Moto", false));
            roadMock.addVehicleToVia(2, new VehicleTeste("V3", "Caminhão", false));
            Sensor sensorMock = new Sensor(roadMock);

            // Act
            int resultado = sensorMock.countAllVehicles();

            // Assert
            assertEquals(3, resultado);
        }

        @Test
        @DisplayName("Mistura de veículos parados e em movimento")
        void testCountAllVehicles_MisturaParadosEmMovimento() {
            // Arrange
            RoadMock roadMock = new RoadMock(3);
            // Via 1: 2 parados
            roadMock.addVehicleToVia(1, new VehicleTeste("V1", "Carro", false));
            roadMock.addVehicleToVia(1, new VehicleTeste("V2", "Moto", false));
            // Via 2: 1 em movimento, 1 parado
            roadMock.addVehicleToVia(2, new VehicleTeste("V3", "Caminhão", true));
            roadMock.addVehicleToVia(2, new VehicleTeste("V4", "Ônibus", false));
            // Via 3: vazia
            Sensor sensorMock = new Sensor(roadMock);

            // Act
            int resultado = sensorMock.countAllVehicles();

            // Assert
            assertEquals(3, resultado); // V1, V2, V4
        }
    }

    // =========================================================================
    // REGIÃO: Testes com Road Real (Integração)
    // =========================================================================

    @Nested
    @DisplayName("Testes com Road Real")
    class RoadRealTests {

        @Test
        @DisplayName("Cenário real complexo com múltiplas vias")
        void testCountAllVehicles_CenarioRealComplexo() {
            // Arrange - Usando a classe VehicleTeste
            Road road = new Road(4);

            // Adicionar veículos com diferentes estados
            road.addVehicle(1, new VehicleTeste("V1", "Carro", false)); // Parado
            road.addVehicle(1, new VehicleTeste("V2", "Moto", true));   // Em movimento
            road.addVehicle(2, new VehicleTeste("V3", "Caminhão", false)); // Parado
            road.addVehicle(3, new VehicleTeste("V4", "Ônibus", true));    // Em movimento
            road.addVehicle(3, new VehicleTeste("V5", "Carro", false));    // Parado
            road.addVehicle(3, new VehicleTeste("V6", "Moto", true));      // Em movimento
            // Via 4: vazia

            Sensor sensor = new Sensor(road);

            // Act
            int resultado = sensor.countAllVehicles();

            // Assert
            assertEquals(3, resultado); // V1, V3, V5
        }

        @Test
        @DisplayName("Road com apenas 1 via")
        void testCountAllVehicles_ApenasUmaVia() {
            // Arrange
            Road road = new Road(1);
            road.addVehicle(1, new VehicleTeste("V1", "Carro", false));
            road.addVehicle(1, new VehicleTeste("V2", "Moto", true));
            road.addVehicle(1, new VehicleTeste("V3", "Caminhão", false));

            Sensor sensor = new Sensor(road);

            // Act
            int resultado = sensor.countAllVehicles();

            // Assert
            assertEquals(2, resultado); // V1 e V3
        }

        @Test
        @DisplayName("Road com muitas vias, maioria vazias")
        void testCountAllVehicles_MuitasViasVazias() {
            // Arrange
            Road road = new Road(10);

            // Apenas algumas vias têm veículos
            road.addVehicle(3, new VehicleTeste("V1", "Carro", false));
            road.addVehicle(3, new VehicleTeste("V2", "Moto", true));
            road.addVehicle(7, new VehicleTeste("V3", "Caminhão", false));
            road.addVehicle(7, new VehicleTeste("V4", "Ônibus", false));
            road.addVehicle(7, new VehicleTeste("V5", "Carro", true));

            Sensor sensor = new Sensor(road);

            // Act
            int resultado = sensor.countAllVehicles();

            // Assert
            assertEquals(3, resultado); // V1, V3, V4
        }
    }

    // =========================================================================
    // REGIÃO: Testes de Casos de Borda
    // =========================================================================

    @Nested
    @DisplayName("Testes de Casos de Borda")
    class CasosBordaTests {

        @Test
        @DisplayName("Road com 0 vias (caso de borda)")
        void testCountAllVehicles_RoadComZeroVias() {
            // Arrange
            Road road = new Road(0);
            Sensor sensor = new Sensor(road);

            // Act
            int resultado = sensor.countAllVehicles();

            // Assert
            assertEquals(0, resultado); // Loop não executa
        }

        @Test
        @DisplayName("Múltiplos veículos na mesma via")
        void testCountAllVehicles_MultiplosVeículosMesmaVia() {
            // Arrange
            RoadMock roadMock = new RoadMock(1);

            // Adicionar 10 veículos, alternando estados
            for (int i = 0; i < 10; i++) {
                boolean emMovimento = (i % 2 == 0); // Pares em movimento, ímpares parados
                roadMock.addVehicleToVia(1, new VehicleTeste("V" + i, "Tipo", emMovimento));
            }

            Sensor sensor = new Sensor(roadMock);

            // Act
            int resultado = sensor.countAllVehicles();

            // Assert
            assertEquals(5, resultado); // Ímpares (1, 3, 5, 7, 9)
        }

        @Test
        @DisplayName("Via com muitos veículos parados")
        void testCountAllVehicles_MuitosVeiculosParados() {
            // Arrange
            RoadMock roadMock = new RoadMock(2);

            // Via 1: 100 veículos parados
            for (int i = 0; i < 100; i++) {
                roadMock.addVehicleToVia(1, new VehicleTeste("V1-" + i, "Carro", false));
            }

            // Via 2: 50 veículos em movimento
            for (int i = 0; i < 50; i++) {
                roadMock.addVehicleToVia(2, new VehicleTeste("V2-" + i, "Moto", true));
            }

            Sensor sensor = new Sensor(roadMock);

            // Act
            int resultado = sensor.countAllVehicles();

            // Assert
            assertEquals(100, resultado);
        }
    }

    // =========================================================================
    // REGIÃO: Testes Parametrizados
    // =========================================================================

    @Nested
    @DisplayName("Testes Parametrizados")
    class ParametrizadosTests {

        static Stream<Object[]> fornecerCenariosContagem() {
            return Stream.of(
                    // {numVias, veiculosParadosPorVia[], veiculosMovimentoPorVia[], resultadoEsperado}
                    new Object[]{1, new int[]{0}, new int[]{0}, 0},
                    new Object[]{1, new int[]{2}, new int[]{0}, 2},
                    new Object[]{1, new int[]{0}, new int[]{3}, 0},
                    new Object[]{2, new int[]{1, 1}, new int[]{0, 0}, 2},
                    new Object[]{2, new int[]{0, 0}, new int[]{1, 1}, 0},
                    new Object[]{3, new int[]{2, 0, 1}, new int[]{1, 0, 2}, 3},
                    new Object[]{4, new int[]{1, 2, 0, 3}, new int[]{0, 1, 0, 1}, 6}
            );
        }

        @ParameterizedTest
        @MethodSource("fornecerCenariosContagem")
        @DisplayName("Testes parametrizados de contagem")
        void testCountAllVehicles_Parametrizado(
                int numVias,
                int[] veiculosParadosPorVia,
                int[] veiculosMovimentoPorVia,
                int resultadoEsperado) {

            // Arrange
            RoadMock roadMock = new RoadMock(numVias);

            // Adicionar veículos parados
            for (int via = 0; via < numVias; via++) {
                for (int i = 0; i < veiculosParadosPorVia[via]; i++) {
                    roadMock.addVehicleToVia(via + 1,
                            new VehicleTeste("P" + via + "-" + i, "Parado", false));
                }
            }

            // Adicionar veículos em movimento
            for (int via = 0; via < numVias; via++) {
                for (int i = 0; i < veiculosMovimentoPorVia[via]; i++) {
                    roadMock.addVehicleToVia(via + 1,
                            new VehicleTeste("M" + via + "-" + i, "Movimento", true));
                }
            }

            Sensor sensor = new Sensor(roadMock);

            // Act
            int resultado = sensor.countAllVehicles();

            // Assert
            assertEquals(resultadoEsperado, resultado);
        }
    }

    // =========================================================================
    // REGIÃO: Testes de Consistência e Estado
    // =========================================================================

    @Test
    @DisplayName("Sensor não deve modificar a Road")
    void testSensorNaoModificaRoad() {
        // Arrange
        Road road = new Road(3);
        road.addVehicle(1, new VehicleTeste("V1", "Carro", false));
        road.addVehicle(2, new VehicleTeste("V2", "Moto", true));

        // Registrar estado inicial
        int estadoInicialVia1 = road.getVehicles(1).size();
        int estadoInicialVia2 = road.getVehicles(2).size();
        int estadoInicialVia3 = road.getVehicles(3).size();

        Sensor sensor = new Sensor(road);

        // Act
        sensor.countAllVehicles();
        sensor.countAllVehicles(); // Chamar múltiplas vezes

        // Assert - O estado da Road não deve mudar
        assertEquals(estadoInicialVia1, road.getVehicles(1).size());
        assertEquals(estadoInicialVia2, road.getVehicles(2).size());
        assertEquals(estadoInicialVia3, road.getVehicles(3).size());
    }

    @Test
    @DisplayName("Resultado deve ser consistente entre múltiplas chamadas")
    void testConsistenciaMultiplasChamadas() {
        // Arrange
        RoadMock roadMock = new RoadMock(2);
        roadMock.addVehicleToVia(1, new VehicleTeste("V1", "Carro", false));
        roadMock.addVehicleToVia(2, new VehicleTeste("V2", "Moto", true));

        Sensor sensor = new Sensor(roadMock);

        // Act - Chamar múltiplas vezes
        int resultado1 = sensor.countAllVehicles();
        int resultado2 = sensor.countAllVehicles();
        int resultado3 = sensor.countAllVehicles();

        // Assert - Todas as chamadas devem retornar o mesmo valor
        assertEquals(1, resultado1);
        assertEquals(resultado1, resultado2);
        assertEquals(resultado1, resultado3);
    }

    // =========================================================================
    // REGIÃO: Testes de Performance
    // =========================================================================

    @Test
    @DisplayName("Performance com muitas vias e veículos")
    @Timeout(3) // Deve executar em menos de 3 segundos
    void testPerformance_MuitosVeiculos() {
        // Arrange
        Road road = new Road(20);
        Sensor sensor = new Sensor(road);

        // Adicionar veículos (100 por via, alternando estados)
        for (int via = 1; via <= 20; via++) {
            for (int i = 0; i < 100; i++) {
                boolean emMovimento = (via % 2 == 0) ^ (i % 3 == 0); // Lógica para variar
                road.addVehicle(via, new VehicleTeste("V" + via + "-" + i, "Tipo", emMovimento));
            }
        }

        // Act
        int resultado = sensor.countAllVehicles();

        // Assert - Verifica que o cálculo foi feito
        assertTrue(resultado >= 0);
        assertTrue(resultado <= 2000); // Máximo possível (20 vias * 100 veículos)
    }

    // =========================================================================
    // REGIÃO: Testes de Integração Completa
    // =========================================================================

    @Test
    @DisplayName("Teste de integração completa com operações na Road")
    void testIntegracaoCompleta() {
        // Arrange
        Road road = new Road(3);
        Sensor sensor = new Sensor(road);

        // Fase 1: Adicionar veículos
        road.addVehicle(1, new VehicleTeste("V1", "Carro", false));
        road.addVehicle(1, new VehicleTeste("V2", "Moto", true));
        road.addVehicle(2, new VehicleTeste("V3", "Caminhão", false));

        int resultado1 = sensor.countAllVehicles();
        assertEquals(2, resultado1); // V1 e V3

        // Fase 2: Adicionar mais veículos
        road.addVehicle(2, new VehicleTeste("V4", "Ônibus", false));
        road.addVehicle(3, new VehicleTeste("V5", "Carro", true));

        int resultado2 = sensor.countAllVehicles();
        assertEquals(3, resultado2); // V1, V3, V4

        // Fase 3: Remover veículos
        road.removeVehicle(1); // Remove V1
        int resultado3 = sensor.countAllVehicles();
        assertEquals(2, resultado3); // V3, V4

        // Fase 4: Limpar road
        road.clearRoad();
        int resultado4 = sensor.countAllVehicles();
        assertEquals(0, resultado4);

        // Fase 5: Adicionar novamente
        road.addVehicle(1, new VehicleTeste("V6", "Moto", false));
        road.addVehicle(2, new VehicleTeste("V7", "Carro", true));

        int resultado5 = sensor.countAllVehicles();
        assertEquals(1, resultado5); // Apenas V6
    }

    // =========================================================================
    // REGIÃO: Testes de Comportamento Específico
    // =========================================================================

    @Test
    @DisplayName("Verifica que conta apenas veículos PARADOS")
    void testContaApenasParados() {
        // Arrange
        RoadMock roadMock = new RoadMock(2);

        // Via 1: 3 em movimento
        roadMock.addVehicleToVia(1, new VehicleTeste("V1", "Carro", true));
        roadMock.addVehicleToVia(1, new VehicleTeste("V2", "Moto", true));
        roadMock.addVehicleToVia(1, new VehicleTeste("V3", "Caminhão", true));

        // Via 2: 1 parado
        roadMock.addVehicleToVia(2, new VehicleTeste("V4", "Ônibus", false));

        Sensor sensor = new Sensor(roadMock);

        // Act
        int resultado = sensor.countAllVehicles();

        // Assert
        assertEquals(1, resultado); // Apenas V4
    }

    @Test
    @DisplayName("Verifica percorre TODAS as vias")
    void testPercorreTodasAsVias() {
        // Arrange
        RoadMock roadMock = new RoadMock(5);

        // Colocar 1 veículo parado em cada via
        for (int i = 1; i <= 5; i++) {
            roadMock.addVehicleToVia(i, new VehicleTeste("V" + i, "Carro", false));
        }

        Sensor sensor = new Sensor(roadMock);

        // Act
        int resultado = sensor.countAllVehicles();

        // Assert
        assertEquals(5, resultado); // Deve contar todos
    }

    // =========================================================================
    // REGIÃO: Testes de Erros (se aplicável)
    // =========================================================================

    @Test
    @DisplayName("Road com via inválida no loop (proteção interna)")
    void testRoadComProblemasInternos() {
        // Arrange - Road que sempre retorna lista vazia (comportamento seguro)
        Road roadProtegida = new Road(3) {
            @Override
            public List<Vehicle> getVehicles(int nVia) {
                // Sempre retorna lista vazia, mesmo para via inválida
                return new java.util.ArrayList<>();
            }
        };

        Sensor sensor = new Sensor(roadProtegida);

        // Act & Assert - Não deve lançar exceção
        assertDoesNotThrow(() -> {
            int resultado = sensor.countAllVehicles();
            assertEquals(0, resultado);
        });
    }
}