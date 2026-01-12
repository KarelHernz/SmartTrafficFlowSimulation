package model;

import controller.State;
import model.state.Green;
import model.state.Red;
import model.state.Yellow;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javafx.scene.image.ImageView;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para a classe TrafficLight.
 * Testa o semáforo com diferentes estados e transições.
 */
@DisplayName("Testes para a classe TrafficLight")
class TrafficLightTest {

    // =========================================================================
    // REGIÃO: Classes Auxiliares para Testes
    // =========================================================================

    /**
     * Classe State mock para testes.
     * Implementa um estado que não faz nada nas transições.
     */
    private static class MockState implements State {
        private final String name;
        private int enterCount = 0;
        private int updateCount = 0;
        private double lastDeltaTime = 0;

        public MockState(String name) {
            this.name = name;
        }

        @Override
        public void enter(TrafficLight trafficLight) {
            enterCount++;
        }

        @Override
        public void update(TrafficLight trafficLight, double deltaTime) {
            updateCount++;
            lastDeltaTime = deltaTime;
        }

        public int getEnterCount() { return enterCount; }
        public int getUpdateCount() { return updateCount; }
        public double getLastDeltaTime() { return lastDeltaTime; }

        @Override
        public String toString() {
            return "MockState[" + name + "]";
        }
    }

    /**
     * ImageView mock para testes.
     */
    private static class MockImageView extends ImageView {
        private boolean visible = false;
        private int setVisibleCount = 0;

        public MockImageView() {
            super();
        }

        @Override
        public void setVisible(boolean visible) {
            this.visible = visible;
            setVisibleCount++;
        }

        @Override
        public boolean isVisible() {
            return visible;
        }

        public int getSetVisibleCount() {
            return setVisibleCount;
        }

        public void resetCounters() {
            setVisibleCount = 0;
        }
    }

    // =========================================================================
    // REGIÃO: Fixtures e Setup
    // =========================================================================

    private MockImageView greenView;
    private MockImageView yellowView;
    private MockImageView redView;
    private TrafficLight trafficLight;

    @BeforeEach
    void setUp() {
        greenView = new MockImageView();
        yellowView = new MockImageView();
        redView = new MockImageView();
    }

    // =========================================================================
    // REGIÃO: Testes do Construtor
    // =========================================================================

    @Nested
    @DisplayName("Testes do Construtor")
    class ConstrutorTests {

        @Test
        @DisplayName("Construtor deve inicializar com estado verde")
        void testConstrutor_EstadoVerde() {
            // Arrange
            State estadoInicial = new Green();

            // Act
            trafficLight = new TrafficLight(estadoInicial, greenView, yellowView, redView);

            // Assert
            assertNotNull(trafficLight);
            assertTrue(trafficLight.getState() instanceof Green);
            assertEquals(0.0, trafficLight.getTempo());

            // Verificar visibilidade
            assertTrue(greenView.isVisible());
            assertFalse(yellowView.isVisible());
            assertFalse(redView.isVisible());
        }

        @Test
        @DisplayName("Construtor deve inicializar com estado amarelo")
        void testConstrutor_EstadoAmarelo() {
            // Arrange
            State estadoInicial = new Yellow();

            // Act
            trafficLight = new TrafficLight(estadoInicial, greenView, yellowView, redView);

            // Assert
            assertNotNull(trafficLight);
            assertTrue(trafficLight.getState() instanceof Yellow);

            // Verificar visibilidade
            assertFalse(greenView.isVisible());
            assertTrue(yellowView.isVisible());
            assertFalse(redView.isVisible());
        }

        @Test
        @DisplayName("Construtor deve inicializar com estado vermelho")
        void testConstrutor_EstadoVermelho() {
            // Arrange
            State estadoInicial = new Red();

            // Act
            trafficLight = new TrafficLight(estadoInicial, greenView, yellowView, redView);

            // Assert
            assertNotNull(trafficLight);
            assertTrue(trafficLight.getState() instanceof Red);

            // Verificar visibilidade
            assertFalse(greenView.isVisible());
            assertFalse(yellowView.isVisible());
            assertTrue(redView.isVisible());
        }

        @Test
        @DisplayName("Construtor com estado mock deve chamar enter")
        void testConstrutor_ChamaEnterDoEstado() {
            // Arrange
            MockState mockState = new MockState("Teste");

            // Act
            trafficLight = new TrafficLight(mockState, greenView, yellowView, redView);

            // Assert
            assertEquals(1, mockState.getEnterCount());
        }

        @Test
        @DisplayName("Construtor deve inicializar tempo como zero")
        void testConstrutor_TempoInicialZero() {
            // Arrange
            State estadoInicial = new Green();

            // Act
            trafficLight = new TrafficLight(estadoInicial, greenView, yellowView, redView);

            // Assert
            assertEquals(0.0, trafficLight.getTempo(), 0.001);
        }
    }

    // =========================================================================
    // REGIÃO: Testes dos Métodos Set State
    // =========================================================================

    @Nested
    @DisplayName("Testes dos Métodos Set State")
    class SetStateTests {

        @BeforeEach
        void setUp() {
            trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);
        }

        @Test
        @DisplayName("setGreen deve mudar para estado verde")
        void testSetGreen_MudaParaVerde() {
            // Act
            trafficLight.setGreen();

            // Assert
            assertTrue(trafficLight.getState() instanceof Green);
            assertTrue(greenView.isVisible());
            assertFalse(yellowView.isVisible());
            assertFalse(redView.isVisible());
        }

        @Test
        @DisplayName("setYellow deve mudar para estado amarelo")
        void testSetYellow_MudaParaAmarelo() {
            // Act
            trafficLight.setYellow();

            // Assert
            assertTrue(trafficLight.getState() instanceof Yellow);
            assertFalse(greenView.isVisible());
            assertTrue(yellowView.isVisible());
            assertFalse(redView.isVisible());
        }

        @Test
        @DisplayName("setRed deve mudar para estado vermelho")
        void testSetRed_MudaParaVermelho() {
            // Act
            trafficLight.setRed();

            // Assert
            assertTrue(trafficLight.getState() instanceof Red);
            assertFalse(greenView.isVisible());
            assertFalse(yellowView.isVisible());
            assertTrue(redView.isVisible());
        }

        @Test
        @DisplayName("Transições sucessivas entre estados")
        void testTransicoesSucessivas() {
            // Act & Assert - Sequência de transições
            trafficLight.setRed();
            assertTrue(trafficLight.getState() instanceof Red);

            trafficLight.setGreen();
            assertTrue(trafficLight.getState() instanceof Green);

            trafficLight.setYellow();
            assertTrue(trafficLight.getState() instanceof Yellow);

            trafficLight.setRed();
            assertTrue(trafficLight.getState() instanceof Red);

            // Verificar contadores de visibilidade
            // Cada setState chama updateVisibilidade que chama setVisible 3 vezes
            // 4 transições + 1 inicial = 5 chamadas * 3 ImageViews = 15
            assertTrue(greenView.getSetVisibleCount() >= 4);
            assertTrue(yellowView.getSetVisibleCount() >= 4);
            assertTrue(redView.getSetVisibleCount() >= 4);
        }

        @Test
        @DisplayName("Set para mesmo estado não deve causar problemas")
        void testSetParaMesmoEstado() {
            // Arrange - Já está no verde
            int contadorInicial = greenView.getSetVisibleCount();

            // Act
            trafficLight.setGreen(); // Já está verde

            // Assert
            assertTrue(trafficLight.getState() instanceof Green);
            // A visibilidade pode ter sido atualizada mesmo estando no mesmo estado
            assertTrue(greenView.isVisible());
        }
    }

    // =========================================================================
    // REGIÃO: Testes de Gerenciamento de Tempo
    // =========================================================================

    @Nested
    @DisplayName("Testes de Gerenciamento de Tempo")
    class TempoTests {

        @BeforeEach
        void setUp() {
            trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);
        }

        @Test
        @DisplayName("addTempo deve incrementar tempo corretamente")
        void testAddTempo_IncrementaCorretamente() {
            // Arrange
            double tempoInicial = trafficLight.getTempo();

            // Act
            trafficLight.addTempo(2.5);
            trafficLight.addTempo(1.5);

            // Assert
            assertEquals(tempoInicial + 4.0, trafficLight.getTempo(), 0.001);
        }

        @Test
        @DisplayName("addTempo com valor negativo")
        void testAddTempo_ValorNegativo() {
            // Arrange
            trafficLight.addTempo(5.0);

            // Act
            trafficLight.addTempo(-2.0);

            // Assert
            assertEquals(3.0, trafficLight.getTempo(), 0.001);
        }

        @Test
        @DisplayName("addTempo com valor zero")
        void testAddTempo_ValorZero() {
            // Arrange
            double tempoInicial = trafficLight.getTempo();

            // Act
            trafficLight.addTempo(0.0);

            // Assert
            assertEquals(tempoInicial, trafficLight.getTempo(), 0.001);
        }

        @Test
        @DisplayName("resetTempo deve zerar o tempo")
        void testResetTempo_ZeraTempo() {
            // Arrange
            trafficLight.addTempo(10.5);
            assertEquals(10.5, trafficLight.getTempo(), 0.001);

            // Act
            trafficLight.resetTempo();

            // Assert
            assertEquals(0.0, trafficLight.getTempo(), 0.001);
        }

        @Test
        @DisplayName("getTempo retorna valor atual")
        void testGetTempo_RetornaValorAtual() {
            // Arrange
            trafficLight.addTempo(3.7);

            // Act
            double tempo = trafficLight.getTempo();

            // Assert
            assertEquals(3.7, tempo, 0.001);
        }

        @Test
        @DisplayName("Múltiplas operações de tempo")
        void testMultiplasOperacoesTempo() {
            // Act
            trafficLight.addTempo(1.0);
            trafficLight.addTempo(2.0);
            trafficLight.resetTempo();
            trafficLight.addTempo(3.0);

            // Assert
            assertEquals(3.0, trafficLight.getTempo(), 0.001);
        }
    }

    // =========================================================================
    // REGIÃO: Testes do Método updateTempo
    // =========================================================================

    @Nested
    @DisplayName("Testes do Método updateTempo")
    class UpdateTempoTests {

        @Test
        @DisplayName("updateTempo deve atualizar tempo e chamar update do estado")
        void testUpdateTempo_AtualizaTempoEEstado() {
            // Arrange
            MockState mockState = new MockState("Teste");
            trafficLight = new TrafficLight(mockState, greenView, yellowView, redView);

            // Act
            trafficLight.updateTempo(2.5);

            // Assert
            assertEquals(2.5, trafficLight.getTempo(), 0.001);
            assertEquals(1, mockState.getUpdateCount());
            assertEquals(2.5, mockState.getLastDeltaTime(), 0.001);
        }

        @Test
        @DisplayName("updateTempo múltiplas vezes")
        void testUpdateTempo_MultiplasVezes() {
            // Arrange
            MockState mockState = new MockState("Teste");
            trafficLight = new TrafficLight(mockState, greenView, yellowView, redView);

            // Act
            trafficLight.updateTempo(1.0);
            trafficLight.updateTempo(2.0);
            trafficLight.updateTempo(0.5);

            // Assert
            assertEquals(3.5, trafficLight.getTempo(), 0.001);
            assertEquals(3, mockState.getUpdateCount());
        }

        @Test
        @DisplayName("updateTempo chama updateVisibilidade")
        void testUpdateTempo_ChamaUpdateVisibilidade() {
            // Arrange
            trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);
            int contadorInicial = greenView.getSetVisibleCount();

            // Act
            trafficLight.updateTempo(1.0);

            // Assert
            // Deve ter chamado setVisible pelo menos uma vez adicional
            assertTrue(greenView.getSetVisibleCount() > contadorInicial);
        }

        @Test
        @DisplayName("updateTempo com deltaTime zero")
        void testUpdateTempo_DeltaTimeZero() {
            // Arrange
            MockState mockState = new MockState("Teste");
            trafficLight = new TrafficLight(mockState, greenView, yellowView, redView);

            // Act
            trafficLight.updateTempo(0.0);

            // Assert
            assertEquals(0.0, trafficLight.getTempo(), 0.001);
            assertEquals(1, mockState.getUpdateCount());
            assertEquals(0.0, mockState.getLastDeltaTime(), 0.001);
        }
    }

    // =========================================================================
    // REGIÃO: Testes do Método updateVisibilidade
    // =========================================================================

    @Nested
    @DisplayName("Testes do Método updateVisibilidade")
    class UpdateVisibilidadeTests {

        @Test
        @DisplayName("updateVisibilidade deve mostrar apenas estado atual")
        void testUpdateVisibilidade_MostraApenasEstadoAtual() {
            // Arrange
            trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);

            // Act - Estado verde
            trafficLight.updateVisibilidade();

            // Assert
            assertTrue(greenView.isVisible());
            assertFalse(yellowView.isVisible());
            assertFalse(redView.isVisible());

            // Act - Muda para vermelho
            trafficLight.setRed();

            // Assert
            assertFalse(greenView.isVisible());
            assertFalse(yellowView.isVisible());
            assertTrue(redView.isVisible());
        }

        @Test
        @DisplayName("updateVisibilidade com ImageView nulo não causa erro")
        void testUpdateVisibilidade_ImageViewNulo() {
            // Arrange - Criar semáforo com ImageView nulo
            TrafficLight trafficLightNulo = new TrafficLight(new Green(), null, null, null);

            // Act & Assert - Não deve lançar exceção
            assertDoesNotThrow(trafficLightNulo::updateVisibilidade);
            assertDoesNotThrow(() -> trafficLightNulo.setYellow());
            assertDoesNotThrow(() -> trafficLightNulo.updateTempo(1.0));
        }

        @Test
        @DisplayName("updateVisibilidade é chamado em todas transições")
        void testUpdateVisibilidade_ChamadoEmTransicoes() {
            // Arrange
            trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);
            greenView.resetCounters();
            yellowView.resetCounters();
            redView.resetCounters();

            // Act - Várias operações que devem chamar updateVisibilidade
            trafficLight.setYellow();
            trafficLight.setRed();
            trafficLight.setGreen();
            trafficLight.updateTempo(1.0);

            // Assert - Cada operação deve ter atualizado a visibilidade
            assertTrue(greenView.getSetVisibleCount() >= 2); // Inicial + setGreen + updateTempo
            assertTrue(yellowView.getSetVisibleCount() >= 1); // setYellow
            assertTrue(redView.getSetVisibleCount() >= 1);    // setRed
        }
    }

    // =========================================================================
    // REGIÃO: Testes do Método reset
    // =========================================================================

    @Nested
    @DisplayName("Testes do Método reset")
    class ResetTests {

        @Test
        @DisplayName("reset deve voltar ao estado inicial")
        void testReset_VoltaAoEstadoInicial() {
            // Arrange
            State estadoInicial = new Yellow();
            trafficLight = new TrafficLight(estadoInicial, greenView, yellowView, redView);

            // Mudar para outro estado
            trafficLight.setRed();
            assertTrue(trafficLight.getState() instanceof Red);

            // Act
            trafficLight.reset();

            // Assert
            assertTrue(trafficLight.getState() instanceof Yellow);
            assertTrue(yellowView.isVisible());
        }

        @Test
        @DisplayName("reset deve zerar o tempo")
        void testReset_ZeraTempo() {
            // Arrange
            trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);
            trafficLight.addTempo(15.7);
            assertEquals(15.7, trafficLight.getTempo(), 0.001);

            // Act
            trafficLight.reset();

            // Assert
            assertEquals(0.0, trafficLight.getTempo(), 0.001);
        }

        @Test
        @DisplayName("reset deve chamar enter do estado inicial")
        void testReset_ChamaEnterDoEstadoInicial() {
            // Arrange
            MockState estadoInicial = new MockState("Inicial");
            trafficLight = new TrafficLight(estadoInicial, greenView, yellowView, redView);

            int enterCountInicial = estadoInicial.getEnterCount();

            // Act
            trafficLight.reset();

            // Assert
            assertEquals(enterCountInicial + 1, estadoInicial.getEnterCount());
        }

        @Test
        @DisplayName("reset deve atualizar visibilidade")
        void testReset_AtualizaVisibilidade() {
            // Arrange
            trafficLight = new TrafficLight(new Red(), greenView, yellowView, redView);

            // Mudar para verde
            trafficLight.setGreen();
            assertTrue(greenView.isVisible());
            assertFalse(redView.isVisible());

            // Act
            trafficLight.reset();

            // Assert - Deve voltar a mostrar vermelho
            assertFalse(greenView.isVisible());
            assertTrue(redView.isVisible());
        }

        @Test
        @DisplayName("reset múltiplas vezes")
        void testReset_MultiplasVezes() {
            // Arrange
            MockState estadoInicial = new MockState("Inicial");
            trafficLight = new TrafficLight(estadoInicial, greenView, yellowView, redView);

            int enterCountEsperado = 1; // Construtor já chama uma vez

            // Act
            for (int i = 0; i < 5; i++) {
                trafficLight.setGreen();
                trafficLight.addTempo(i * 2.0);
                trafficLight.reset();
                enterCountEsperado++; // Cada reset chama enter
            }

            // Assert
            assertEquals(enterCountEsperado, estadoInicial.getEnterCount());
            assertEquals(0.0, trafficLight.getTempo(), 0.001);
        }
    }

    // =========================================================================
    // REGIÃO: Testes de Integração com Estados Reais
    // =========================================================================

    @Nested
    @DisplayName("Testes de Integração com Estados Reais")
    class IntegracaoEstadosReaisTests {

        @Test
        @DisplayName("Integração com estado Green real")
        void testIntegracao_EstadoGreenReal() {
            // Arrange
            Green green = new Green();
            trafficLight = new TrafficLight(green, greenView, yellowView, redView);

            // Act & Assert
            assertTrue(trafficLight.getState() instanceof Green);
            assertTrue(greenView.isVisible());

            // Verificar que pode fazer update sem erro
            assertDoesNotThrow(() -> trafficLight.updateTempo(1.0));
        }

        @Test
        @DisplayName("Integração com estado Yellow real")
        void testIntegracao_EstadoYellowReal() {
            // Arrange
            Yellow yellow = new Yellow();
            trafficLight = new TrafficLight(yellow, greenView, yellowView, redView);

            // Act & Assert
            assertTrue(trafficLight.getState() instanceof Yellow);
            assertTrue(yellowView.isVisible());

            // Verificar que pode fazer update sem erro
            assertDoesNotThrow(() -> trafficLight.updateTempo(1.0));
        }

        @Test
        @DisplayName("Integração com estado Red real")
        void testIntegracao_EstadoRedReal() {
            // Arrange
            Red red = new Red();
            trafficLight = new TrafficLight(red, greenView, yellowView, redView);

            // Act & Assert
            assertTrue(trafficLight.getState() instanceof Red);
            assertTrue(redView.isVisible());

            // Verificar que pode fazer update sem erro
            assertDoesNotThrow(() -> trafficLight.updateTempo(1.0));
        }

        @Test
        @DisplayName("Ciclo completo de transições com estados reais")
        void testCicloCompleto_EstadosReais() {
            // Arrange
            trafficLight = new TrafficLight(new Red(), greenView, yellowView, redView);

            // Act & Assert - Ciclo vermelho -> verde -> amarelo -> vermelho
            assertTrue(trafficLight.getState() instanceof Red);
            assertTrue(redView.isVisible());

            trafficLight.setGreen();
            assertTrue(trafficLight.getState() instanceof Green);
            assertTrue(greenView.isVisible());

            trafficLight.setYellow();
            assertTrue(trafficLight.getState() instanceof Yellow);
            assertTrue(yellowView.isVisible());

            trafficLight.setRed();
            assertTrue(trafficLight.getState() instanceof Red);
            assertTrue(redView.isVisible());

            // Update com tempo
            trafficLight.updateTempo(5.0);
            assertEquals(5.0, trafficLight.getTempo(), 0.001);

            // Reset
            trafficLight.reset();
            assertTrue(trafficLight.getState() instanceof Red);
            assertEquals(0.0, trafficLight.getTempo(), 0.001);
        }
    }

    // =========================================================================
    // REGIÃO: Testes Parametrizados
    // =========================================================================

    @Nested
    @DisplayName("Testes Parametrizados")
    class ParametrizadosTests {

        static Stream<Object[]> fornecerEstadosIniciais() {
            return Stream.of(
                    new Object[]{new Green(), "Green"},
                    new Object[]{new Yellow(), "Yellow"},
                    new Object[]{new Red(), "Red"}
            );
        }

        @ParameterizedTest
        @MethodSource("fornecerEstadosIniciais")
        @DisplayName("Construtor com diferentes estados iniciais")
        void testConstrutor_DiferentesEstadosIniciais(State estadoInicial, String nomeEstado) {
            // Act
            trafficLight = new TrafficLight(estadoInicial, greenView, yellowView, redView);

            // Assert
            assertNotNull(trafficLight);
            assertEquals(estadoInicial.getClass(), trafficLight.getState().getClass());
            assertEquals(0.0, trafficLight.getTempo(), 0.001);
        }

        static Stream<Double> fornecerDeltaTimes() {
            return Stream.of(0.0, 0.5, 1.0, 2.5, 10.0, -1.0, -5.0);
        }

        @ParameterizedTest
        @MethodSource("fornecerDeltaTimes")
        @DisplayName("addTempo com diferentes valores")
        void testAddTempo_DiferentesValores(double deltaTime) {
            // Arrange
            trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);
            double tempoInicial = trafficLight.getTempo();

            // Act
            trafficLight.addTempo(deltaTime);

            // Assert
            assertEquals(tempoInicial + deltaTime, trafficLight.getTempo(), 0.001);
        }
    }

    // =========================================================================
    // REGIÃO: Testes de Consistência e Estado
    // =========================================================================

    @Test
    @DisplayName("HashMap lights é preenchido corretamente")
    void testHashMapLights_PreenchidoCorretamente() {
        // Arrange
        trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);

        // Este teste verifica implicitamente que o construtor não lança exceção
        // ao adicionar os estados ao HashMap
        assertDoesNotThrow(() -> {
            trafficLight.setGreen();
            trafficLight.setYellow();
            trafficLight.setRed();
        });
    }

    @Test
    @DisplayName("Estado atual sempre é um dos três possíveis")
    void testEstadoAtual_SempreUmDosTres() {
        // Arrange
        trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);

        // Testar várias transições
        trafficLight.setYellow();
        assertTrue(trafficLight.getState() instanceof Green ||
                trafficLight.getState() instanceof Yellow ||
                trafficLight.getState() instanceof Red);

        trafficLight.setRed();
        assertTrue(trafficLight.getState() instanceof Green ||
                trafficLight.getState() instanceof Yellow ||
                trafficLight.getState() instanceof Red);

        trafficLight.setGreen();
        assertTrue(trafficLight.getState() instanceof Green ||
                trafficLight.getState() instanceof Yellow ||
                trafficLight.getState() instanceof Red);
    }

    // =========================================================================
    // REGIÃO: Testes de Exceções (se aplicável)
    // =========================================================================

    @Test
    @DisplayName("Construtor com estado nulo")
    void testConstrutor_EstadoNulo() {
        // Arrange
        State estadoNulo = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new TrafficLight(estadoNulo, greenView, yellowView, redView);
        });
    }

    @Test
    @DisplayName("Operações com semáforo válido após exceção no construtor")
    void testOperacoes_AposFalhaConstrutor() {
        // Este teste garante que se o construtor falhar parcialmente,
        // o objeto não fica em estado inconsistente
        // (Não aplicável aqui pois o construtor é simples)

        // Arrange - Criar semáforo válido
        trafficLight = new TrafficLight(new Green(), greenView, yellowView, redView);

        // Act & Assert - Todas operações devem funcionar
        assertDoesNotThrow(() -> trafficLight.setYellow());
        assertDoesNotThrow(() -> trafficLight.addTempo(1.0));
        assertDoesNotThrow(() -> trafficLight.updateTempo(1.0));
        assertDoesNotThrow(() -> trafficLight.reset());
    }
}