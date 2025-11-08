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
    private TextArea taEstadisticas;
    @FXML
    private RadioButton rbAdaptativo;
    @FXML
    private ToggleGroup grpModo;
    @FXML
    private RadioButton rbFixo;
    @FXML
    private ImageView imgMapa;
    @FXML
    private ComboBox<String> cbVelocidade;
    @FXML
    private TextArea txtaEstadisticas;
    @FXML
    private Button bttExportar;
    @FXML
    private Button bttReset;

    //Função que corre quando inicia o programa
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbVelocidade.getItems().addAll("Pausa", "x1", "x1.5", "x2");
        cbVelocidade.setValue("x1");
    }
}
