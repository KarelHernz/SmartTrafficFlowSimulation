package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

// ============================================================================
// REGIÃO: TESTES DA CLASSE ROAD
// ============================================================================
@DisplayName("Testes para a classe Road")
class RoadTest {

    private Road road;

    // ========================================================================
    // CONFIGURAÇÃO INICIAL
    // ========================================================================
    @BeforeEach
    void setUp() {
        road = new Road("A1", 100.0, 2);
    }

    // ========================================================================
    // TESTES CONSTRUTOR E GETTERS
    // ========================================================================
    @Nested
    @DisplayName("Testes do construtor e métodos getters")
    class ConstructorAndGettersTests {

        @Test
        @DisplayName("Testa criação de rodovia com valores válidos")
        void testRoadCreationWithValidValues() {
            assertNotNull(road);
            assertEquals("A1", road.getName());
            assertEquals(100.0, road.getLength());
            assertEquals(2, road.getLanes());
        }

        @Test
        @DisplayName("Testa criação com nome nulo deve lançar exceção")
        void testRoadCreationWithNullName() {
            assertThrows(IllegalArgumentException.class, () -> {
                new Road(null, 100.0, 2);
            });
        }

        @Test
        @DisplayName("Testa criação com comprimento negativo")
        void testRoadCreationWithNegativeLength() {
            assertThrows(IllegalArgumentException.class, () -> {
                new Road("A1", -10.0, 2);
            });
        }
    }

    // ========================================================================
    // TESTES MÉTODOS SETTERS
    // ========================================================================
    @Nested
    @DisplayName("Testes dos métodos setters")
    class SettersTests {

        @Test
        @DisplayName("Testa setLanes com valor válido")
        void testSetLanesWithValidValue() {
            road.setLanes(3);
            assertEquals(3, road.getLanes());
        }

        @Test
        @DisplayName("Testa setLanes com valor zero deve lançar exceção")
        void testSetLanesWithZero() {
            assertThrows(IllegalArgumentException.class, () -> {
                road.setLanes(0);
            });
        }
    }

    // ========================================================================
    // TESTES MÉTODOS DE NEGÓCIO
    // ========================================================================
    @Nested
    @DisplayName("Testes dos métodos de negócio")
    class BusinessMethodsTests {

        @Test
        @DisplayName("Testa cálculo de capacidade da rodovia")
        void testCalculateCapacity() {
            // Assume que cada faixa tem capacidade para 1000 veículos
            double expectedCapacity = 2 * 1000;
            assertEquals(expectedCapacity, road.calculateCapacity());
        }

        @Test
        @DisplayName("Testa método isCongested com diferentes cenários")
        void testIsCongested() {
            Road smallRoad = new Road("Local", 10.0, 1);

            // Teste com tráfego abaixo do limite
            assertFalse(smallRoad.isCongested(500));

            // Teste com tráfego acima do limite
            assertTrue(smallRoad.isCongested(1500));
        }
    }

    // ========================================================================
    // TESTES MÉTODOS equals() E hashCode()
    // ========================================================================
    @Test
    @DisplayName("Testa igualdade entre rodovias")
    void testEqualsAndHashCode() {
        Road road1 = new Road("A1", 100.0, 2);
        Road road2 = new Road("A1", 100.0, 2);
        Road road3 = new Road("A2", 100.0, 2);

        assertEquals(road1, road2);
        assertEquals(road1.hashCode(), road2.hashCode());
        assertNotEquals(road1, road3);
    }
}