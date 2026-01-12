package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para a classe Road.
 */
@DisplayName("Testes para a classe Road")
class RoadTest {

    // =========================================================================
    // REGIÃO: Fixtures e Setup
    // =========================================================================

    private Road road;
    private Vehicle carro;
    private Vehicle moto;
    private Vehicle caminhao;

    @BeforeEach
    void setUp() {
        // Configuração comum para a maioria dos testes
        road = new Road(3);
        carro = new Vehicle("ABC123", "Carro");
        moto = new Vehicle("XYZ789", "Moto");
        caminhao = new Vehicle("DEF456", "Caminhão");
    }

    // =========================================================================
    // REGIÃO: Testes do Construtor
    // =========================================================================

    @Test
    @DisplayName("Construtor deve criar road com número correto de vias")
    void testConstrutor_CriacaoComNumeroVias() {
        // Arrange & Act
        Road road2 = new Road(5);

        // Assert
        assertEquals(5, road2.getNVias());
    }

    @Test
    @DisplayName("Construtor deve inicializar todas as vias vazias")
    void testConstrutor_ViasInicializadasVazias() {
        // Arrange & Act
        Road road2 = new Road(4);

        // Assert
        for (int i = 1; i <= 4; i++) {
            List<Vehicle> vehicles = road2.getVehicles(i);
            assertNotNull(vehicles);
            assertTrue(vehicles.isEmpty());
        }
    }

    @Test
    @DisplayName("Construtor com 0 vias (caso de borda)")
    void testConstrutor_ZeroVias() {
        // Arrange & Act
        Road road2 = new Road(0);

        // Assert
        assertEquals(0, road2.getNVias());
        // Não deve lançar exceção
    }

    // =========================================================================
    // REGIÃO: Testes do Método getNVias()
    // =========================================================================

    @Test
    @DisplayName("getNVias deve retornar número correto de vias")
    void testGetNVias_RetornaNumeroCorreto() {
        // Arrange
        Road road2 = new Road(7);

        // Act
        int numeroVias = road2.getNVias();

        // Assert
        assertEquals(7, numeroVias);
    }

    @Test
    @DisplayName("getNVias retorna imutável após adicionar vias extras")
    void testGetNVias_ImutavelAposAddVia() {
        // Arrange
        Road road2 = new Road(2);

        // Act
        road2.addVia(3); // Adiciona via extra
        road2.addVia(4); // Adiciona outra via

        // Assert
        assertEquals(2, road2.getNVias()); // Continua 2 (valor do construtor)
    }

    // =========================================================================
    // REGIÃO: Testes do Método getVehicles()
    // =========================================================================

    @Test
    @DisplayName("getVehicles deve retornar cópia da lista, não referência")
    void testGetVehicles_RetornaCopia() {
        // Arrange
        road.addVehicle(1, carro);

        // Act
        List<Vehicle> copia = road.getVehicles(1);
        copia.clear(); // Modifica a cópia

        // Assert
        List<Vehicle> original = road.getVehicles(1);
        assertEquals(1, original.size()); // Original não foi alterado
        assertEquals(carro, original.get(0));
    }

    @Test
    @DisplayName("getVehicles deve retornar lista vazia para via sem veículos")
    void testGetVehicles_ViaVazia() {
        // Act
        List<Vehicle> vehicles = road.getVehicles(2);

        // Assert
        assertNotNull(vehicles);
        assertTrue(vehicles.isEmpty());
    }

    @Test
    @DisplayName("getVehicles deve lançar exceção para via inexistente")
    void testGetVehicles_ViaInexistente() {
        // Arrange
        int viaInexistente = 99;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            road.getVehicles(viaInexistente);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("getVehicles funciona para todas as vias existentes")
    void testGetVehicles_ParaTodasViasExistentes(int via) {
        // Arrange
        Vehicle veiculo = new Vehicle("TEST" + via, "Tipo" + via);
        road.addVehicle(via, veiculo);

        // Act
        List<Vehicle> vehicles = road.getVehicles(via);

        // Assert
        assertNotNull(vehicles);
        assertEquals(1, vehicles.size());
        assertEquals(veiculo, vehicles.get(0));
    }

    // =========================================================================
    // REGIÃO: Testes do Método addVehicle()
    // =========================================================================

    @Test
    @DisplayName("addVehicle deve adicionar veículo à via correta")
    void testAddVehicle_AdicionaNaViaCorreta() {
        // Act
        road.addVehicle(2, carro);
        road.addVehicle(2, moto);

        // Assert
        List<Vehicle> via2 = road.getVehicles(2);
        assertEquals(2, via2.size());
        assertEquals(carro, via2.get(0));
        assertEquals(moto, via2.get(1));
    }

    @Test
    @DisplayName("addVehicle mantém ordem de inserção FIFO")
    void testAddVehicle_MantemOrdemFIFO() {
        // Arrange
        Vehicle v1 = new Vehicle("V1", "Tipo1");
        Vehicle v2 = new Vehicle("V2", "Tipo2");
        Vehicle v3 = new Vehicle("V3", "Tipo3");

        // Act
        road.addVehicle(1, v1);
        road.addVehicle(1, v2);
        road.addVehicle(1, v3);

        // Assert
        List<Vehicle> vehicles = road.getVehicles(1);
        assertEquals(3, vehicles.size());
        assertEquals(v1, vehicles.get(0));
        assertEquals(v2, vehicles.get(1));
        assertEquals(v3, vehicles.get(2));
    }

    @Test
    @DisplayName("addVehicle múltiplos veículos em vias diferentes")
    void testAddVehicle_MultiplasVias() {
        // Act
        road.addVehicle(1, carro);
        road.addVehicle(2, moto);
        road.addVehicle(3, caminhao);

        // Assert
        assertEquals(1, road.getVehicles(1).size());
        assertEquals(1, road.getVehicles(2).size());
        assertEquals(1, road.getVehicles(3).size());
        assertEquals(carro, road.getVehicles(1).get(0));
        assertEquals(moto, road.getVehicles(2).get(0));
        assertEquals(caminhao, road.getVehicles(3).get(0));
    }

    @Test
    @DisplayName("addVehicle em via inexistente lança exceção")
    void testAddVehicle_ViaInexistente() {
        // Arrange
        int viaInexistente = 99;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            road.addVehicle(viaInexistente, carro);
        });
    }

    // =========================================================================
    // REGIÃO: Testes do Método removeVehicle()
    // =========================================================================

    @Test
    @DisplayName("removeVehicle deve remover primeiro veículo (FIFO)")
    void testRemoveVehicle_RemovePrimeiro() {
        // Arrange
        road.addVehicle(1, carro);
        road.addVehicle(1, moto);
        road.addVehicle(1, caminhao);

        // Act
        road.removeVehicle(1);

        // Assert
        List<Vehicle> vehicles = road.getVehicles(1);
        assertEquals(2, vehicles.size());
        assertEquals(moto, vehicles.get(0));
        assertEquals(caminhao, vehicles.get(1));
    }

    @Test
    @DisplayName("removeVehicle em via vazia lança exceção")
    void testRemoveVehicle_ViaVazia() {
        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> {
            road.removeVehicle(1);
        });
    }

    @Test
    @DisplayName("removeVehicle sucessivas esvazia a via")
    void testRemoveVehicle_SucessivasEsvaziaVia() {
        // Arrange
        road.addVehicle(3, carro);
        road.addVehicle(3, moto);

        // Act
        road.removeVehicle(3); // Remove carro
        road.removeVehicle(3); // Remove moto

        // Assert
        List<Vehicle> vehicles = road.getVehicles(3);
        assertTrue(vehicles.isEmpty());

        // Tentar remover de via vazia deve lançar exceção
        assertThrows(NoSuchElementException.class, () -> {
            road.removeVehicle(3);
        });
    }

    @Test
    @DisplayName("removeVehicle não afeta outras vias")
    void testRemoveVehicle_NaoAfetaOutrasVias() {
        // Arrange
        road.addVehicle(1, carro);
        road.addVehicle(2, moto);
        road.addVehicle(3, caminhao);

        // Act
        road.removeVehicle(1);

        // Assert
        assertEquals(0, road.getVehicles(1).size());
        assertEquals(1, road.getVehicles(2).size()); // Não afetada
        assertEquals(1, road.getVehicles(3).size()); // Não afetada
    }

    // =========================================================================
    // REGIÃO: Testes do Método addVia()
    // =========================================================================

    @Test
    @DisplayName("addVia deve criar nova via vazia")
    void testAddVia_CriaNovaViaVazia() {
        // Arrange
        Road road2 = new Road(2);

        // Act
        road2.addVia(3);

        // Assert
        List<Vehicle> novaVia = road2.getVehicles(3);
        assertNotNull(novaVia);
        assertTrue(novaVia.isEmpty());
    }

    @Test
    @DisplayName("addVia não afeta vias existentes")
    void testAddVia_NaoAfetaViasExistentes() {
        // Arrange
        Road road2 = new Road(2);
        road2.addVehicle(1, carro);
        road2.addVehicle(2, moto);

        // Act
        road2.addVia(3);

        // Assert
        assertEquals(1, road2.getVehicles(1).size());
        assertEquals(1, road2.getVehicles(2).size());
        assertEquals(0, road2.getVehicles(3).size());
    }

    @Test
    @DisplayName("addVia substitui via existente (limpa veículos)")
    void testAddVia_SubstituiViaExistente() {
        // Arrange
        Road road2 = new Road(2);
        road2.addVehicle(1, carro);
        road2.addVehicle(1, moto);

        // Act
        road2.addVia(1); // Re-cria via 1

        // Assert
        List<Vehicle> vehicles = road2.getVehicles(1);
        assertTrue(vehicles.isEmpty()); // Foi limpa
    }

    @Test
    @DisplayName("addVia permite vias não sequenciais")
    void testAddVia_ViasNaoSequenciais() {
        // Arrange
        Road road2 = new Road(2);

        // Act
        road2.addVia(10); // Via não sequencial
        road2.addVehicle(10, carro);

        // Assert
        List<Vehicle> via10 = road2.getVehicles(10);
        assertEquals(1, via10.size());
        assertEquals(carro, via10.get(0));
    }

    // =========================================================================
    // REGIÃO: Testes do Método clearRoad()
    // =========================================================================

    @Test
    @DisplayName("clearRoad deve limpar todas as vias")
    void testClearRoad_LimpaTodasVias() {
        // Arrange
        road.addVehicle(1, carro);
        road.addVehicle(2, moto);
        road.addVehicle(2, caminhao);
        road.addVehicle(3, new Vehicle("V4", "Ônibus"));

        // Verificar que há veículos antes
        assertEquals(1, road.getVehicles(1).size());
        assertEquals(2, road.getVehicles(2).size());
        assertEquals(1, road.getVehicles(3).size());

        // Act
        road.clearRoad();

        // Assert
        for (int i = 1; i <= 3; i++) {
            assertTrue(road.getVehicles(i).isEmpty());
        }
    }

    @Test
    @DisplayName("clearRoad em road vazia não causa erro")
    void testClearRoad_RoadVazia() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            road.clearRoad();
        });

        // Verificar que continua vazia
        for (int i = 1; i <= 3; i++) {
            assertTrue(road.getVehicles(i).isEmpty());
        }
    }

    @Test
    @DisplayName("clearRoad permite reuso das vias")
    void testClearRoad_PermiteReuso() {
        // Arrange
        road.addVehicle(1, carro);
        road.addVehicle(2, moto);

        // Act
        road.clearRoad();
        Vehicle novoCarro = new Vehicle("NOVO123", "CarroNovo");
        road.addVehicle(1, novoCarro);
        road.addVehicle(3, new Vehicle("OUTRO", "Moto"));

        // Assert
        assertEquals(1, road.getVehicles(1).size());
        assertEquals(0, road.getVehicles(2).size());
        assertEquals(1, road.getVehicles(3).size());
        assertEquals(novoCarro, road.getVehicles(1).get(0));
    }

    // =========================================================================
    // REGIÃO: Testes de Isolamento e Independência
    // =========================================================================

    @Test
    @DisplayName("Vias são independentes entre si")
    void testViasIndependentes() {
        // Act
        road.addVehicle(1, carro);
        road.addVehicle(2, moto);
        road.addVehicle(3, caminhao);

        // Assert
        assertEquals(1, road.getVehicles(1).size());
        assertEquals(1, road.getVehicles(2).size());
        assertEquals(1, road.getVehicles(3).size());

        // Modificar uma via não afeta as outras
        road.removeVehicle(1);
        assertEquals(0, road.getVehicles(1).size());
        assertEquals(1, road.getVehicles(2).size()); // Inalterada
        assertEquals(1, road.getVehicles(3).size()); // Inalterada
    }

    @Test
    @DisplayName("Múltiplas roads são independentes")
    void testRoadsIndependentes() {
        // Arrange
        Road road1 = new Road(2);
        Road road2 = new Road(2);

        // Act
        road1.addVehicle(1, carro);
        road2.addVehicle(1, moto);

        // Assert
        assertEquals(1, road1.getVehicles(1).size());
        assertEquals(1, road2.getVehicles(1).size());
        assertEquals(carro, road1.getVehicles(1).get(0));
        assertEquals(moto, road2.getVehicles(1).get(0));
    }

    // =========================================================================
    // REGIÃO: Testes de Cenários Complexos
    // =========================================================================

    @Test
    @DisplayName("Cenário complexo com múltiplas operações")
    void testCenarioComplexo_MultiplasOperacoes() {
        // Arrange
        Road road2 = new Road(4);
        Vehicle[] vehicles = new Vehicle[10];
        for (int i = 0; i < 10; i++) {
            vehicles[i] = new Vehicle("V" + i, "Tipo" + i);
        }

        // Act - Sequência complexa de operações
        road2.addVehicle(1, vehicles[0]);
        road2.addVehicle(1, vehicles[1]);
        road2.addVehicle(2, vehicles[2]);
        road2.addVehicle(3, vehicles[3]);
        road2.removeVehicle(1); // Remove vehicles[0]
        road2.addVehicle(1, vehicles[4]);
        road2.addVehicle(4, vehicles[5]);
        road2.addVehicle(4, vehicles[6]);
        road2.removeVehicle(4); // Remove vehicles[5]
        road2.clearRoad();
        road2.addVehicle(2, vehicles[7]);
        road2.addVehicle(2, vehicles[8]);
        road2.addVehicle(3, vehicles[9]);

        // Assert
        assertEquals(1, road2.getVehicles(1).size()); // Apenas vehicles[4]
        assertEquals(2, road2.getVehicles(2).size()); // vehicles[7] e vehicles[8]
        assertEquals(1, road2.getVehicles(3).size()); // vehicles[9]
        assertEquals(0, road2.getVehicles(4).size()); // Vazia após clearRoad

        assertEquals(vehicles[4], road2.getVehicles(1).get(0));
        assertEquals(vehicles[7], road2.getVehicles(2).get(0));
        assertEquals(vehicles[8], road2.getVehicles(2).get(1));
        assertEquals(vehicles[9], road2.getVehicles(3).get(0));
    }

    @Test
    @DisplayName("Cenário: adicionar e remover veículos ciclicamente")
    void testCenario_CiclicoAddRemove() {
        // Arrange
        Road road2 = new Road(2);

        // Act & Assert - Ciclo de operações
        for (int i = 0; i < 5; i++) {
            Vehicle v = new Vehicle("V" + i, "Tipo");
            road2.addVehicle(1, v);
            assertEquals(i + 1, road2.getVehicles(1).size());
        }

        for (int i = 4; i >= 0; i--) {
            road2.removeVehicle(1);
            assertEquals(i, road2.getVehicles(1).size());
        }

        assertTrue(road2.getVehicles(1).isEmpty());
    }

    // =========================================================================
    // REGIÃO: Testes Parametrizados
    // =========================================================================

    @Nested
    @DisplayName("Testes Parametrizados")
    class ParametrizadosTests {

        static Stream<Integer> numerosViasValidos() {
            return Stream.of(1, 2, 5, 10);
        }

        @ParameterizedTest
        @MethodSource("numerosViasValidos")
        @DisplayName("Construtor com diferentes números de vias")
        void testConstrutor_VariasQuantidadesVias(int numeroVias) {
            // Act
            Road road2 = new Road(numeroVias);

            // Assert
            assertEquals(numeroVias, road2.getNVias());
            for (int i = 1; i <= numeroVias; i++) {
                assertNotNull(road2.getVehicles(i));
                assertTrue(road2.getVehicles(i).isEmpty());
            }
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3})
        @DisplayName("Operações em diferentes vias")
        void testOperacoes_DiferentesVias(int via) {
            // Arrange
            Road road2 = new Road(3);

            // Act
            road2.addVehicle(via, carro);
            road2.addVehicle(via, moto);

            // Assert
            List<Vehicle> vehicles = road2.getVehicles(via);
            assertEquals(2, vehicles.size());

            // Remove
            road2.removeVehicle(via);
            assertEquals(1, vehicles.size());
            assertEquals(moto, vehicles.get(0));
        }
    }

    // =========================================================================
    // REGIÃO: Testes de Performance (básicos)
    // =========================================================================

    @Test
    @DisplayName("Performance: adicionar muitos veículos")
    @Timeout(2)
    void testPerformance_AdicionarMuitosVeiculos() {
        // Arrange
        Road road2 = new Road(3);

        // Act
        for (int i = 0; i < 1000; i++) {
            road2.addVehicle(1, new Vehicle("V" + i, "Tipo"));
        }

        // Assert
        assertEquals(1000, road2.getVehicles(1).size());
    }

    @Test
    @DisplayName("Performance: clearRoad com muitos veículos")
    @Timeout(2)
    void testPerformance_ClearRoadComMuitosVeiculos() {
        // Arrange
        Road road2 = new Road(5);

        for (int via = 1; via <= 5; via++) {
            for (int i = 0; i < 200; i++) {
                road2.addVehicle(via, new Vehicle("V" + via + "-" + i, "Tipo"));
            }
        }

        // Act
        road2.clearRoad();

        // Assert
        for (int via = 1; via <= 5; via++) {
            assertTrue(road2.getVehicles(via).isEmpty());
        }
    }

    // =========================================================================
    // REGIÃO: Testes de Estado Inicial e Consistência
    // =========================================================================

    @Test
    @DisplayName("Estado inicial correto após construção")
    void testEstadoInicial_Correto() {
        // Arrange & Act
        Road road2 = new Road(3);

        // Assert
        assertEquals(3, road2.getNVias());
        assertNotNull(road2.getVehicles(1));
        assertNotNull(road2.getVehicles(2));
        assertNotNull(road2.getVehicles(3));

        for (int i = 1; i <= 3; i++) {
            List<Vehicle> vehicles = road2.getVehicles(i);
            assertNotNull(vehicles);
            assertTrue(vehicles.isEmpty());
        }
    }

    @Test
    @DisplayName("Consistência após múltiplas operações")
    void testConsistencia_AposMultiplasOperacoes() {
        // Act
        road.addVehicle(1, carro);
        road.addVehicle(1, moto);
        road.removeVehicle(1);
        road.addVehicle(1, caminhao);
        road.addVehicle(2, new Vehicle("V4", "Ônibus"));
        road.removeVehicle(1);

        // Assert
        assertEquals(1, road.getVehicles(1).size()); // Apenas caminhao foi removido
        assertEquals(1, road.getVehicles(2).size());
        assertEquals(0, road.getVehicles(3).size());

        assertEquals(moto, road.getVehicles(1).get(0)); // moto ficou após remoções
    }
}