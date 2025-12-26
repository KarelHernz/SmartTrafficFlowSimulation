package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.World;

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
    private ComboBox<String> cbVelocidade;
    @FXML
    private TreeView<String> tvEstadisticas;

    private World world;

    //Função que corre quando inicia o programa
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbVelocidade.getItems().addAll("Pausa", "x1", "x1.5", "x2");
        cbVelocidade.setValue("x1");

        var root = makeTreeView();
        tvEstadisticas.setRoot(root);
        tvEstadisticas.setShowRoot(false);

        ArrayList<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(ivTopYellowLigth);
        imageViewList.add(ivTopRedLigth);
        imageViewList.add(ivBottomYellowLigth);
        imageViewList.add(ivBottomRedLigth);
        imageViewList.add(ivLeftGreenLigth);
        imageViewList.add(ivLeftYellowLigth);
        imageViewList.add(ivRigthGreenLigth);
        imageViewList.add(ivRigthYellowLigth);

        world = new World(imageViewList, ivMapa);
    }

    public void changeFixedCycle(){

    }

    public void changeAdaptativeCycle(){

    }

    public void changeSpeed(){
        switch (cbVelocidade.getValue()){
            case "Pausa":
                world.changeSpeed(0);
                break;
            case "x1":
                world.changeSpeed(1);
                break;
            case "x1.5":
                world.changeSpeed(1.5F);
                break;
            case "x2":
                world.changeSpeed(2);
                break;
        }
    }

    public void reset(){
        var root = makeTreeView();
        tvEstadisticas.setRoot(root);
    }

    public void exportar(){
    }

    //Planilha para criar a TreeView onde vão ser apresentadas as estadísticas
    public TreeItem<String> makeTreeView() {
        TreeItem<String> root = new TreeItem<>();
        TreeItem<String> rootVehicle = new TreeItem<>("Carros");
        TreeItem<String> rootIntersection = new TreeItem<>("Intersecção");
        TreeItem<String> rootTime = new TreeItem<>("Tempo: 0s");

        rootVehicle.getChildren().addAll(
                new TreeItem<>("Vermelhos: 0"),
                new TreeItem<>("Brancos: 0"),
                new TreeItem<>("Dorados: 0"),
                new TreeItem<>("Roxos: 0")
        );

        rootIntersection.getChildren().addAll(
                new TreeItem<>("Encima: 0"),
                new TreeItem<>("Embaixo: 0"),
                new TreeItem<>("Esquerda: 0"),
                new TreeItem<>("Direita: 0")
        );

        TreeItem<String> rootTotal = new TreeItem<>("Total");
        rootTotal.getChildren().add(rootVehicle);
        rootTotal.getChildren().add(rootIntersection);
        rootTotal.getChildren().add(rootTime);
        rootTotal.setExpanded(true);
        root.getChildren().add(rootTotal);
        return root;
    }
}
