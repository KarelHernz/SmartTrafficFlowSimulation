package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class ControlPanel implements Initializable {
    @FXML
    private ImageView ivMapa;
    @FXML
    private ImageView imgMapa;
    @FXML
    private ImageView ivLeftGreenLigth;
    @FXML
    private ImageView ivLeftYellowLigth;
    @FXML
    private ImageView ivRigthGreenLigth;
    @FXML
    private ImageView ivRigthYellowLigth;
    @FXML
    private ImageView ivTopRedLigth;
    @FXML
    private ImageView ivTopYellowLigth;
    @FXML
    private ImageView ivBottomRedLigth;
    @FXML
    private ImageView ivBottomYellowLigth;
    @FXML
    private RadioButton rbFixo;
    @FXML
    private RadioButton rbAdaptativo;
    @FXML
    private ToggleGroup grpModo;
    @FXML
    private ComboBox<String> cbVelocidade;
    @FXML
    private TreeView<String> tvEstadisticas;
    @FXML
    private Button bttExportar;
    @FXML
    private Button bttReset;

    //Função que corre quando inicia o programa
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbVelocidade.getItems().addAll("Pausa", "x1", "x1.5", "x2");
        cbVelocidade.setValue("x1");

        TreeItem<String> root = new TreeItem<>();
        TreeItem<String> rootTotal = new TreeItem<>("Total");
        TreeItem<String> rootCarros = new TreeItem<>("Carros");
        TreeItem<String> rootInterseccao = new TreeItem<>("Intersecção");
        TreeItem<String> rootTempo = new TreeItem<>("Tempo: 0s");

        rootCarros.getChildren().addAll(
                new TreeItem<>("Vermelhos: 0"),
                new TreeItem<>("Brancos: 0"),
                new TreeItem<>("Dorados: 0"),
                new TreeItem<>("Roxos: 0")
        );

        rootInterseccao.getChildren().addAll(
                new TreeItem<>("Encima: 0"),
                new TreeItem<>("Embaixo: 0"),
                new TreeItem<>("Esquerda: 0"),
                new TreeItem<>("Direita: 0")
        );

        rootTotal.getChildren().add(rootCarros);
        rootTotal.getChildren().add(rootInterseccao);
        rootTotal.getChildren().add(rootTempo);
        rootTotal.setExpanded(true);
        root.getChildren().add(rootTotal);

        tvEstadisticas.setRoot(root);
        tvEstadisticas.setShowRoot(false);
    }
}
