package view;

import controller.AdaptiveCycle;
import controller.FixedCycle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.World;
import util.JsonExporter;
import util.Statistics;

public class ControlPanel implements Initializable {
    @FXML
    public Pane vehiclesPane;
    @FXML
    public ImageView ivLeftGreenLight;
    @FXML
    public ImageView ivLeftYellowLight;
    @FXML
    public ImageView ivRightGreenLight;
    @FXML
    public ImageView ivRightYellowLight;
    @FXML
    public ImageView ivTopRedLight;
    @FXML
    public ImageView ivTopYellowLight;
    @FXML
    public ImageView ivBottomRedLight;
    @FXML
    public ImageView ivBottomYellowLight;
    @FXML
    public ComboBox<String> cbSpeed;
    @FXML
    public Label lblRedValue;
    @FXML
    public Label lblWhiteValue;
    @FXML
    public Label lblGoldenValue;
    @FXML
    public Label lblPurpleValue;
    @FXML
    public Label lblBlueValue;
    @FXML
    public Label lblBottomValue;
    @FXML
    public Label lblTopValue;
    @FXML
    public Label lblLeftValue;
    @FXML
    public Label lblRightValue;
    @FXML
    public Label lblTime;

    private World world;
    private Statistics statistics;

    //Função que corre quando inicia o programa
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbSpeed.getItems().addAll("Pausa", "x1", "x1.5", "x2");
        cbSpeed.setValue("x1");

        ArrayList<ImageView> lightsImageViewList = new ArrayList<>();
        lightsImageViewList.add(ivTopYellowLight);
        lightsImageViewList.add(ivTopRedLight);
        lightsImageViewList.add(ivBottomYellowLight);
        lightsImageViewList.add(ivBottomRedLight);
        lightsImageViewList.add(ivLeftGreenLight);
        lightsImageViewList.add(ivLeftYellowLight);
        lightsImageViewList.add(ivRightGreenLight);
        lightsImageViewList.add(ivRightYellowLight);

        statistics = new Statistics();
        statistics.addVehiclesColors("Red");
        statistics.addVehiclesColors("White");
        statistics.addVehiclesColors("Golden");
        statistics.addVehiclesColors("Purple");
        statistics.addVehiclesColors("Blue");

        statistics.addIntersection("Top");
        statistics.addIntersection("Bottom");
        statistics.addIntersection("Left");
        statistics.addIntersection("Right");

        world = new World(this, lightsImageViewList, statistics);
    }

    public void changeFixedCycle(){
        world.changeCycle(new FixedCycle());
        reset();
    }

    public void changeAdaptativeCycle(){
        world.changeCycle(new AdaptiveCycle());
        reset();
    }

    //Função para mudar a velocidade
    public void changeSpeed(){
        switch (cbSpeed.getValue()){
            case "Pausa":
                world.changeSpeed(0);
                break;
            case "x1":
                world.changeSpeed(1);
                break;
            case "x1.5":
                world.changeSpeed(1.5f);
                break;
            case "x2":
                world.changeSpeed(2);
                break;
        }
    }

    public void reset(){
        world.reset();
        vehiclesPane.getChildren().clear();
        updateLabels();
    }

    public void export(){
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            JsonExporter jsonExporter = new JsonExporter();
            File file = directoryChooser.showDialog(new Stage());

            if (file != null) {
                world.changeSpeed(0);
                cbSpeed.setValue("Pausa");
                jsonExporter.export(statistics, file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Função para mudar o texto das labels das estatísticas
    public void updateLabels(){
        //Carros
        lblRedValue.setText(statistics.getVehiclesValue("Red").toString());
        lblWhiteValue.setText(statistics.getVehiclesValue("White").toString());
        lblGoldenValue.setText(statistics.getVehiclesValue("Golden").toString());
        lblPurpleValue.setText(statistics.getVehiclesValue("Purple").toString());
        lblBlueValue.setText(statistics.getVehiclesValue("Blue").toString());

        //Intersecção
        lblBottomValue.setText(statistics.getIntersectionValue("Bottom").toString());
        lblTopValue.setText(statistics.getIntersectionValue("Top").toString());
        lblLeftValue.setText(statistics.getIntersectionValue("Left").toString());
        lblRightValue.setText(statistics.getIntersectionValue("Right").toString());

        //Tempo
        lblTime.setText(statistics.getTimeElapsed());
    }

    public void addImageView(ImageView imageViewVehicle){
        vehiclesPane.getChildren().add(imageViewVehicle);
    }

    public Pane getVehiclesPane(){
        return vehiclesPane;
    }
}