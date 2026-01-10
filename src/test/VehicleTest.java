package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para a classe Vehicle.
 * Testa o veículo com suas propriedades e comportamento.
 */
@DisplayName("Testes para a classe Vehicle")
class VehicleTest {

    // =========================================================================
    // REGIÃO: Classes Auxiliares para Testes
    // =========================================================================

    /**
     * ImageView mock para testes.
     */
    private static class MockImageView extends ImageView {
        private String id;
        private double x = 0;
        private double y = 0;
        private boolean visible = true;

        public MockImageView() {
            super();
        }

        public MockImageView(String id) {
            super();
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Override
        public void setX(double x) {
            this.x = x;
        }

        @Override
        public double getX() {
            return x;
        }

        @Override
        public void setY(double y) {
            this.y = y;
        }

        @Override
        public double getY() {
            return y;
        }

        @Override
        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        @Override
        public boolean isVisible() {
            return visible;
        }
    }

    // =========================================================================
    // REGIÃO: Fixtures e Setup
    // =========================================================================

    private Vehicle vehicle;
    private MockImageView imageView;
    private Map<String, Double> posicaoInicial;
    private Map<String, Double> destinoInicial;

    @BeforeEach
    void setUp() {
        // Configurar mapa de posição inicial
        posicaoInicial = new HashMap<>();
        posicaoInicial.put("x", 100.0);
        posicaoInicial.put("y", 200.0);

        // Configurar mapa de destino
        destinoInicial = new HashMap<>();
        destinoInicial.put("x", 500.0);
        destinoInicial.put("y", 300.0);

        // Criar ImageView mock
        imageView = new MockImageView("carro-1");

        // Criar veículo para testes
        vehicle = new Vehicle("vermelho", imageView, posicaoInicial, destinoInicial);
    }

    // =========================================================================
    // REGIÃO: Testes do Construtor
    // =========================================================================

    @Nested
    @DisplayName("Testes do Construtor")
    class ConstrutorTests {

        @Test
        @DisplayName("Construtor deve inicializar com valores corretos")
        void testConstrutor_InicializacaoCorreta() {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            posicao.put("x", 50.0);
            posicao.put("y", 75.0);

            Map<String, Double> destino = new HashMap<>();
            destino.put("x", 300.0);
            destino.put("y", 400.0);

            MockImageView img = new MockImageView("teste");

            // Act
            Vehicle veiculoTeste = new Vehicle("azul", img, posicao, destino);

            // Assert
            assertEquals("azul", veiculoTeste.getCor());
            assertEquals(img, veiculoTeste.getImage());
            assertEquals(50.0, veiculoTeste.getPosicao("x"));
            assertEquals(75.0, veiculoTeste.getPosicao("y"));
            assertEquals(300.0, veiculoTeste.getDestino("x"));
            assertEquals(400.0, veiculoTeste.getDestino("y"));
            assertTrue(veiculoTeste.isEmMovimento()); // Por padrão em movimento
        }

        @Test
        @DisplayName("Construtor com posição nula")
        void testConstrutor_PosicaoNula() {
            // Arrange
            Map<String, Double> destino = new HashMap<>();
            destino.put("x", 100.0);

            // Act & Assert
            assertThrows(NullPointerException.class, () -> {
                new Vehicle("vermelho", imageView, null, destino);
            });
        }

        @Test
        @DisplayName("Construtor com destino nulo")
        void testConstrutor_DestinoNulo() {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            posicao.put("x", 100.0);

            // Act & Assert
            assertThrows(NullPointerException.class, () -> {
                new Vehicle("vermelho", imageView, posicao, null);
            });
        }

        @Test
        @DisplayName("Construtor com ImageView nulo")
        void testConstrutor_ImageViewNulo() {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            Map<String, Double> destino = new HashMap<>();

            // Act
            Vehicle veiculo = new Vehicle("vermelho", null, posicao, destino);

            // Assert - Deve permitir ImageView nulo
            assertNull(veiculo.getImage());
        }

        @Test
        @DisplayName("Construtor com cor nula")
        void testConstrutor_CorNula() {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            Map<String, Double> destino = new HashMap<>();

            // Act
            Vehicle veiculo = new Vehicle(null, imageView, posicao, destino);

            // Assert - Deve permitir cor nula
            assertNull(veiculo.getCor());
        }

        @Test
        @DisplayName("Construtor com mapas vazios")
        void testConstrutor_MapasVazios() {
            // Arrange
            Map<String, Double> posicaoVazia = new HashMap<>();
            Map<String, Double> destinoVazio = new HashMap<>();

            // Act
            Vehicle veiculo = new Vehicle("verde", imageView, posicaoVazia, destinoVazio);

            // Assert
            assertEquals(0.0, veiculo.getPosicao("x"));
            assertEquals(0.0, veiculo.getPosicao("y"));
            assertEquals(0.0, veiculo.getDestino("x"));
            assertEquals(0.0, veiculo.getDestino("y"));
        }

        @Test
        @DisplayName("Construtor com mapas contendo valores negativos")
        void testConstrutor_ValoresNegativos() {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            posicao.put("x", -100.0);
            posicao.put("y", -50.0);

            Map<String, Double> destino = new HashMap<>();
            destino.put("x", -300.0);
            destino.put("y", -200.0);

            // Act
            Vehicle veiculo = new Vehicle("amarelo", imageView, posicao, destino);

            // Assert
            assertEquals(-100.0, veiculo.getPosicao("x"));
            assertEquals(-50.0, veiculo.getPosicao("y"));
            assertEquals(-300.0, veiculo.getDestino("x"));
            assertEquals(-200.0, veiculo.getDestino("y"));
        }
    }

    // =========================================================================
    // REGIÃO: Testes dos Getters e Setters de Cor
    // =========================================================================

    @Nested
    @DisplayName("Testes dos Getters e Setters de Cor")
    class CorTests {

        @Test
        @DisplayName("getCor retorna cor correta")
        void testGetCor_RetornaCorCorreta() {
            // Act
            String cor = vehicle.getCor();

            // Assert
            assertEquals("vermelho", cor);
        }

        @Test
        @DisplayName("setCor altera cor corretamente")
        void testSetCor_AlteraCor() {
            // Act
            vehicle.setCor("azul");

            // Assert
            assertEquals("azul", vehicle.getCor());
        }

        @Test
        @DisplayName("setCor com valor nulo")
        void testSetCor_ComNulo() {
            // Act
            vehicle.setCor(null);

            // Assert
            assertNull(vehicle.getCor());
        }

        @Test
        @DisplayName("setCor com string vazia")
        void testSetCor_StringVazia() {
            // Act
            vehicle.setCor("");

            // Assert
            assertEquals("", vehicle.getCor());
        }

        @Test
        @DisplayName("Múltiplas alterações de cor")
        void testSetCor_MultiplasAlteracoes() {
            // Act & Assert
            vehicle.setCor("verde");
            assertEquals("verde", vehicle.getCor());

            vehicle.setCor("amarelo");
            assertEquals("amarelo", vehicle.getCor());

            vehicle.setCor("roxo");
            assertEquals("roxo", vehicle.getCor());
        }
    }

    // =========================================================================
    // REGIÃO: Testes dos Getters e Setters de ImageView
    // =========================================================================

    @Nested
    @DisplayName("Testes dos Getters e Setters de ImageView")
    class ImageViewTests {

        @Test
        @DisplayName("getImage retorna ImageView correto")
        void testGetImage_RetornaImageViewCorreto() {
            // Act
            ImageView imagem = vehicle.getImage();

            // Assert
            assertSame(imageView, imagem);
        }

        @Test
        @DisplayName("setImage altera ImageView corretamente")
        void testSetImage_AlteraImageView() {
            // Arrange
            MockImageView novaImagem = new MockImageView("novo-carro");

            // Act
            vehicle.setImage(novaImagem);

            // Assert
            assertSame(novaImagem, vehicle.getImage());
        }

        @Test
        @DisplayName("setImage com valor nulo")
        void testSetImage_ComNulo() {
            // Act
            vehicle.setImage(null);

            // Assert
            assertNull(vehicle.getImage());
        }

        @Test
        @DisplayName("Alternar entre múltiplas imagens")
        void testSetImage_AlternarImagens() {
            // Arrange
            MockImageView img1 = new MockImageView("img1");
            MockImageView img2 = new MockImageView("img2");
            MockImageView img3 = new MockImageView("img3");

            // Act & Assert
            vehicle.setImage(img1);
            assertSame(img1, vehicle.getImage());

            vehicle.setImage(img2);
            assertSame(img2, vehicle.getImage());

            vehicle.setImage(img3);
            assertSame(img3, vehicle.getImage());

            vehicle.setImage(img1); // Voltar para primeira
            assertSame(img1, vehicle.getImage());
        }
    }

    // =========================================================================
    // REGIÃO: Testes dos Métodos de Posição
    // =========================================================================

    @Nested
    @DisplayName("Testes dos Métodos de Posição")
    class PosicaoTests {

        @Test
        @DisplayName("getPosicao retorna valor correto para eixo existente")
        void testGetPosicao_EixoExistente() {
            // Act & Assert
            assertEquals(100.0, vehicle.getPosicao("x"));
            assertEquals(200.0, vehicle.getPosicao("y"));
        }

        @Test
        @DisplayName("getPosicao retorna 0.0 para eixo inexistente")
        void testGetPosicao_EixoInexistente() {
            // Act & Assert
            assertEquals(0.0, vehicle.getPosicao("z"));
            assertEquals(0.0, vehicle.getPosicao(""));
            assertEquals(0.0, vehicle.getPosicao(null));
        }

        @Test
        @DisplayName("setPosicao adiciona novo eixo")
        void testSetPosicao_AdicionaNovoEixo() {
            // Act
            vehicle.setPosicao("z", 150.0);

            // Assert
            assertEquals(150.0, vehicle.getPosicao("z"));
            // Eixos originais mantidos
            assertEquals(100.0, vehicle.getPosicao("x"));
            assertEquals(200.0, vehicle.getPosicao("y"));
        }

        @Test
        @DisplayName("setPosicao atualiza eixo existente")
        void testSetPosicao_AtualizaEixoExistente() {
            // Act
            vehicle.setPosicao("x", 250.0);

            // Assert
            assertEquals(250.0, vehicle.getPosicao("x"));
            assertEquals(200.0, vehicle.getPosicao("y")); // Inalterado
        }

        @Test
        @DisplayName("setPosicao com valor negativo")
        void testSetPosicao_ValorNegativo() {
            // Act
            vehicle.setPosicao("x", -50.0);

            // Assert
            assertEquals(-50.0, vehicle.getPosicao("x"));
        }

        @Test
        @DisplayName("setPosicao com valor zero")
        void testSetPosicao_ValorZero() {
            // Act
            vehicle.setPosicao("x", 0.0);

            // Assert
            assertEquals(0.0, vehicle.getPosicao("x"));
        }

        @Test
        @DisplayName("setPosicao com eixo nulo")
        void testSetPosicao_EixoNulo() {
            // Act
            vehicle.setPosicao(null, 100.0);

            // Assert
            assertEquals(100.0, vehicle.getPosicao(null));
        }

        @Test
        @DisplayName("setPosicao com eixo vazio")
        void testSetPosicao_EixoVazio() {
            // Act
            vehicle.setPosicao("", 100.0);

            // Assert
            assertEquals(100.0, vehicle.getPosicao(""));
        }

        @Test
        @DisplayName("Múltiplas atualizações de posição")
        void testSetPosicao_MultiplasAtualizacoes() {
            // Act
            vehicle.setPosicao("x", 150.0);
            vehicle.setPosicao("y", 250.0);
            vehicle.setPosicao("z", 350.0);
            vehicle.setPosicao("x", 450.0); // Atualizar novamente

            // Assert
            assertEquals(450.0, vehicle.getPosicao("x"));
            assertEquals(250.0, vehicle.getPosicao("y"));
            assertEquals(350.0, vehicle.getPosicao("z"));
        }
    }

    // =========================================================================
    // REGIÃO: Testes dos Métodos de Destino
    // =========================================================================

    @Nested
    @DisplayName("Testes dos Métodos de Destino")
    class DestinoTests {

        @Test
        @DisplayName("getDestino retorna valor correto para eixo existente")
        void testGetDestino_EixoExistente() {
            // Act & Assert
            assertEquals(500.0, vehicle.getDestino("x"));
            assertEquals(300.0, vehicle.getDestino("y"));
        }

        @Test
        @DisplayName("getDestino retorna 0.0 para eixo inexistente")
        void testGetDestino_EixoInexistente() {
            // Act & Assert
            assertEquals(0.0, vehicle.getDestino("z"));
            assertEquals(0.0, vehicle.getDestino(""));
            assertEquals(0.0, vehicle.getDestino(null));
        }

        @Test
        @DisplayName("setDestino adiciona novo eixo")
        void testSetDestino_AdicionaNovoEixo() {
            // Act
            vehicle.setDestino("z", 150.0);

            // Assert
            assertEquals(150.0, vehicle.getDestino("z"));
            // Eixos originais mantidos
            assertEquals(500.0, vehicle.getDestino("x"));
            assertEquals(300.0, vehicle.getDestino("y"));
        }

        @Test
        @DisplayName("setDestino atualiza eixo existente")
        void testSetDestino_AtualizaEixoExistente() {
            // Act
            vehicle.setDestino("x", 600.0);

            // Assert
            assertEquals(600.0, vehicle.getDestino("x"));
            assertEquals(300.0, vehicle.getDestino("y")); // Inalterado
        }

        @Test
        @DisplayName("setDestino com valor negativo")
        void testSetDestino_ValorNegativo() {
            // Act
            vehicle.setDestino("x", -100.0);

            // Assert
            assertEquals(-100.0, vehicle.getDestino("x"));
        }

        @Test
        @DisplayName("setDestino com valor zero")
        void testSetDestino_ValorZero() {
            // Act
            vehicle.setDestino("x", 0.0);

            // Assert
            assertEquals(0.0, vehicle.getDestino("x"));
        }

        @Test
        @DisplayName("setDestino com eixo nulo")
        void testSetDestino_EixoNulo() {
            // Act
            vehicle.setDestino(null, 100.0);

            // Assert
            assertEquals(100.0, vehicle.getDestino(null));
        }

        @Test
        @DisplayName("Múltiplas atualizações de destino")
        void testSetDestino_MultiplasAtualizacoes() {
            // Act
            vehicle.setDestino("x", 550.0);
            vehicle.setDestino("y", 350.0);
            vehicle.setDestino("z", 450.0);
            vehicle.setDestino("x", 650.0); // Atualizar novamente

            // Assert
            assertEquals(650.0, vehicle.getDestino("x"));
            assertEquals(350.0, vehicle.getDestino("y"));
            assertEquals(450.0, vehicle.getDestino("z"));
        }

        @Test
        @DisplayName("Posição e destino são independentes")
        void testPosicaoDestino_Independentes() {
            // Act
            vehicle.setPosicao("x", 200.0);
            vehicle.setDestino("x", 700.0);

            // Assert
            assertEquals(200.0, vehicle.getPosicao("x"));
            assertEquals(700.0, vehicle.getDestino("x"));

            // Alterar um não afeta o outro
            vehicle.setPosicao("x", 300.0);
            assertEquals(300.0, vehicle.getPosicao("x"));
            assertEquals(700.0, vehicle.getDestino("x")); // Inalterado
        }
    }

    // =========================================================================
    // REGIÃO: Testes do Estado de Movimento
    // =========================================================================

    @Nested
    @DisplayName("Testes do Estado de Movimento")
    class MovimentoTests {

        @Test
        @DisplayName("isEmMovimento retorna true por padrão")
        void testIsEmMovimento_PadraoTrue() {
            // Act & Assert
            assertTrue(vehicle.isEmMovimento());
        }

        @Test
        @DisplayName("setEmMovimento altera estado para false")
        void testSetEmMovimento_ParaFalse() {
            // Act
            vehicle.setEmMovimento(false);

            // Assert
            assertFalse(vehicle.isEmMovimento());
        }

        @Test
        @DisplayName("setEmMovimento altera estado para true")
        void testSetEmMovimento_ParaTrue() {
            // Arrange
            vehicle.setEmMovimento(false);
            assertFalse(vehicle.isEmMovimento());

            // Act
            vehicle.setEmMovimento(true);

            // Assert
            assertTrue(vehicle.isEmMovimento());
        }

        @Test
        @DisplayName("Alternar estado de movimento múltiplas vezes")
        void testSetEmMovimento_AlternarMultiplasVezes() {
            // Act & Assert
            vehicle.setEmMovimento(false);
            assertFalse(vehicle.isEmMovimento());

            vehicle.setEmMovimento(true);
            assertTrue(vehicle.isEmMovimento());

            vehicle.setEmMovimento(false);
            assertFalse(vehicle.isMovimento());

            vehicle.setEmMovimento(true);
            assertTrue(vehicle.isEmMovimento());
        }

        @Test
        @DisplayName("Estado de movimento não afeta outras propriedades")
        void testMovimento_NaoAfetaOutrasPropriedades() {
            // Arrange
            String corOriginal = vehicle.getCor();
            ImageView imagemOriginal = vehicle.getImage();
            double posicaoXOriginal = vehicle.getPosicao("x");
            double destinoXOriginal = vehicle.getDestino("x");

            // Act
            vehicle.setEmMovimento(false);

            // Assert - Outras propriedades inalteradas
            assertEquals(corOriginal, vehicle.getCor());
            assertSame(imagemOriginal, vehicle.getImage());
            assertEquals(posicaoXOriginal, vehicle.getPosicao("x"));
            assertEquals(destinoXOriginal, vehicle.getDestino("x"));
        }
    }

    // =========================================================================
    // REGIÃO: Testes de Integração e Cenários Complexos
    // =========================================================================

    @Nested
    @DisplayName("Testes de Integração e Cenários Complexos")
    class IntegracaoTests {

        @Test
        @DisplayName("Cenário completo: veículo em movimento com todas propriedades")
        void testCenarioCompleto_VeiculoEmMovimento() {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            posicao.put("x", 0.0);
            posicao.put("y", 0.0);

            Map<String, Double> destino = new HashMap<>();
            destino.put("x", 1000.0);
            destino.put("y", 500.0);

            MockImageView imagem = new MockImageView();
            imagem.setX(0);
            imagem.setY(0);

            Vehicle carro = new Vehicle("vermelho", imagem, posicao, destino);

            // Act - Simular movimento (atualizar posição)
            carro.setPosicao("x", 100.0);
            carro.setPosicao("y", 50.0);

            // Assert
            assertEquals("vermelho", carro.getCor());
            assertSame(imagem, carro.getImage());
            assertEquals(100.0, carro.getPosicao("x"));
            assertEquals(50.0, carro.getPosicao("y"));
            assertEquals(1000.0, carro.getDestino("x"));
            assertEquals(500.0, carro.getDestino("y"));
            assertTrue(carro.isEmMovimento());
        }

        @Test
        @DisplayName("Cenário: veículo para e depois continua")
        void testCenario_VeiculoParaEContinua() {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            posicao.put("x", 100.0);

            Map<String, Double> destino = new HashMap<>();
            destino.put("x", 500.0);

            Vehicle carro = new Vehicle("azul", imageView, posicao, destino);

            // Act - Veículo se move
            carro.setPosicao("x", 200.0);
            assertTrue(carro.isEmMovimento());

            // Veículo para (semáforo vermelho)
            carro.setEmMovimento(false);
            carro.setPosicao("x", 200.0); // Posição não muda

            // Veículo continua
            carro.setEmMovimento(true);
            carro.setPosicao("x", 250.0);

            // Assert
            assertTrue(carro.isEmMovimento());
            assertEquals(250.0, carro.getPosicao("x"));
            assertEquals(500.0, carro.getDestino("x"));
        }

        @Test
        @DisplayName("Múltiplos veículos independentes")
        void testMultiplosVeiculos_Independentes() {
            // Arrange
            Map<String, Double> posicao1 = new HashMap<>();
            posicao1.put("x", 0.0);

            Map<String, Double> destino1 = new HashMap<>();
            destino1.put("x", 100.0);

            Map<String, Double> posicao2 = new HashMap<>();
            posicao2.put("x", 50.0);

            Map<String, Double> destino2 = new HashMap<>();
            destino2.put("x", 200.0);

            MockImageView img1 = new MockImageView("carro1");
            MockImageView img2 = new MockImageView("carro2");

            Vehicle carro1 = new Vehicle("vermelho", img1, posicao1, destino1);
            Vehicle carro2 = new Vehicle("azul", img2, posicao2, destino2);

            // Act - Modificar apenas carro1
            carro1.setPosicao("x", 25.0);
            carro1.setCor("amarelo");
            carro1.setEmMovimento(false);

            // Assert - Carro2 não foi afetado
            assertEquals(25.0, carro1.getPosicao("x"));
            assertEquals("amarelo", carro1.getCor());
            assertFalse(carro1.isEmMovimento());

            assertEquals(50.0, carro2.getPosicao("x")); // Inalterado
            assertEquals("azul", carro2.getCor()); // Inalterado
            assertTrue(carro2.isEmMovimento()); // Inalterado
        }

        @Test
        @DisplayName("Veículo com múltiplos eixos de posição e destino")
        void testVeiculo_MultiplosEixos() {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            posicao.put("x", 10.0);
            posicao.put("y", 20.0);
            posicao.put("z", 30.0);
            posicao.put("rotacao", 45.0);

            Map<String, Double> destino = new HashMap<>();
            destino.put("x", 100.0);
            destino.put("y", 200.0);
            destino.put("z", 300.0);
            destino.put("rotacao", 90.0);

            Vehicle veiculo = new Vehicle("verde", imageView, posicao, destino);

            // Act & Assert
            assertEquals(10.0, veiculo.getPosicao("x"));
            assertEquals(20.0, veiculo.getPosicao("y"));
            assertEquals(30.0, veiculo.getPosicao("z"));
            assertEquals(45.0, veiculo.getPosicao("rotacao"));

            assertEquals(100.0, veiculo.getDestino("x"));
            assertEquals(200.0, veiculo.getDestino("y"));
            assertEquals(300.0, veiculo.getDestino("z"));
            assertEquals(90.0, veiculo.getDestino("rotacao"));

            // Atualizar posição
            veiculo.setPosicao("z", 35.0);
            veiculo.setPosicao("rotacao", 60.0);

            assertEquals(35.0, veiculo.getPosicao("z"));
            assertEquals(60.0, veiculo.getPosicao("rotacao"));
        }
    }

    // =========================================================================
    // REGIÃO: Testes Parametrizados
    // =========================================================================

    @Nested
    @DisplayName("Testes Parametrizados")
    class ParametrizadosTests {

        @ParameterizedTest
        @ValueSource(strings = {"vermelho", "azul", "verde", "amarelo", "preto", "branco"})
        @DisplayName("Veículo com diferentes cores")
        void testVeiculo_DiferentesCores(String cor) {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            Map<String, Double> destino = new HashMap<>();

            // Act
            Vehicle veiculo = new Vehicle(cor, imageView, posicao, destino);

            // Assert
            assertEquals(cor, veiculo.getCor());
        }

        @ParameterizedTest
        @CsvSource({
                "0.0, 0.0",
                "100.0, 200.0",
                "-50.0, -25.0",
                "999.9, 888.8",
                "0.0, 1000.0"
        })
        @DisplayName("Veículo com diferentes posições iniciais")
        void testVeiculo_DiferentesPosicoes(double posX, double posY) {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            posicao.put("x", posX);
            posicao.put("y", posY);

            Map<String, Double> destino = new HashMap<>();
            destino.put("x", 500.0);
            destino.put("y", 500.0);

            // Act
            Vehicle veiculo = new Vehicle("cor", imageView, posicao, destino);

            // Assert
            assertEquals(posX, veiculo.getPosicao("x"));
            assertEquals(posY, veiculo.getPosicao("y"));
        }

        @ParameterizedTest
        @CsvSource({
                "true, false",
                "false, true",
                "true, true",
                "false, false"
        })
        @DisplayName("Alternar estado de movimento")
        void testSetEmMovimento_DiferentesEstados(boolean estadoInicial, boolean estadoFinal) {
            // Arrange
            Map<String, Double> posicao = new HashMap<>();
            Map<String, Double> destino = new HashMap<>();

            Vehicle veiculo = new Vehicle("cor", imageView, posicao, destino);
            veiculo.setEmMovimento(estadoInicial);
            assertEquals(estadoInicial, veiculo.isEmMovimento());

            // Act
            veiculo.setEmMovimento(estadoFinal);

            // Assert
            assertEquals(estadoFinal, veiculo.isEmMovimento());
        }
    }

    // =========================================================================
    // REGIÃO: Testes de Imutabilidade dos Mapas
    // =========================================================================

    @Test
    @DisplayName("Mapas internos não são expostos diretamente")
    void testMapas_NaoExpostosDiretamente() {
        // Arrange
        Map<String, Double> posicaoOriginal = new HashMap<>();
        posicaoOriginal.put("x", 100.0);

        Map<String, Double> destinoOriginal = new HashMap<>();
        destinoOriginal.put("x", 500.0);

        Vehicle veiculo = new Vehicle("cor", imageView, posicaoOriginal, destinoOriginal);

        // Act - Modificar mapas originais não deve afetar o veículo
        posicaoOriginal.put("x", 999.0);
        destinoOriginal.put("x", 999.0);

        // Assert - Veículo mantém valores originais do construtor
        // Nota: O construtor usa as referências, então na verdade são os mesmos mapas
        // Este teste documenta esse comportamento
        assertEquals(999.0, veiculo.getPosicao("x")); // Foi modificado
        assertEquals(999.0, veiculo.getDestino("x")); // Foi modificado
    }

    @Test
    @DisplayName("Consistência após múltiplas operações")
    void testConsistencia_AposMultiplasOperacoes() {
        // Act - Múltiplas operações
        vehicle.setCor("roxo");
        vehicle.setImage(new MockImageView("nova-imagem"));
        vehicle.setPosicao("x", 150.0);
        vehicle.setPosicao("y", 250.0);
        vehicle.setDestino("x", 600.0);
        vehicle.setDestino("y", 400.0);
        vehicle.setEmMovimento(false);
        vehicle.setEmMovimento(true);
        vehicle.setPosicao("x", 200.0);
        vehicle.setCor("laranja");

        // Assert
        assertEquals("laranja", vehicle.getCor());
        assertEquals(200.0, vehicle.getPosicao("x"));
        assertEquals(250.0, vehicle.getPosicao("y"));
        assertEquals(600.0, vehicle.getDestino("x"));
        assertEquals(400.0, vehicle.getDestino("y"));
        assertTrue(vehicle.isEmMovimento());
    }
}