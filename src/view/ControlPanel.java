package view;

import controller.AdaptiveCycle;
import controller.FixedCycle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
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
    private Pane vehiclesPane;
    @FXML
    private ImageView ivLeftLight;
    @FXML
    private ImageView ivRightLight;
    @FXML
    private ImageView ivTopLight;
    @FXML
    private ImageView ivBottomLight;
    @FXML
    private ComboBox<String> cbSpeed;
    @FXML
    private Label lblRedValue;
    @FXML
    private Label lblWhiteValue;
    @FXML
    private Label lblGoldenValue;
    @FXML
    private Label lblPurpleValue;
    @FXML
    private Label lblBlueValue;
    @FXML
    private Label lblBottomValue;
    @FXML
    private Label lblTopValue;
    @FXML
    private Label lblLeftValue;
    @FXML
    private Label lblRightValue;
    @FXML
    private Label lblTime;

    private World world;
    private final FixedCycle fixedCycle = new FixedCycle();
    private final AdaptiveCycle adaptiveCycle = new AdaptiveCycle();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private final JsonExporter jsonExporter = new JsonExporter();
    private Statistics statistics;

    //Função que corre quando inicia o programa
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbSpeed.getItems().addAll("Pausa", "x1", "x1.5", "x2");
        cbSpeed.setValue("x1");

        HashMap<String, ImageView> lightsImageView = new HashMap<>();
        lightsImageView.put("Top", ivTopLight);
        lightsImageView.put("Bottom", ivBottomLight);
        lightsImageView.put("Left", ivLeftLight);
        lightsImageView.put("Right", ivRightLight);

        //Configuração das estatísticas
        statistics = new Statistics();
        statistics.addVehiclesColors("Red");
        statistics.addVehiclesColors("White");
        statistics.addVehiclesColors("Golden");
        statistics.addVehiclesColors("Purple");
        statistics.addVehiclesColors("Blue");

        statistics.addDirection("Top");
        statistics.addDirection("Bottom");
        statistics.addDirection("Left");
        statistics.addDirection("Right");

        world = new World(this, lightsImageView, statistics);
    }

    @FXML
    public void changeFixedCycle(ActionEvent event){
        world.changeCycle(fixedCycle);
        statistics.setStrategy("Ciclo Fixo");
        reset(event);
    }

    @FXML
    public void changeAdaptativeCycle(ActionEvent event){
        world.changeCycle(adaptiveCycle);
        statistics.setStrategy("Ciclo Adaptado");
        reset(event);
    }

    //Função para mudar a velocidade
    @FXML
    public void changeSpeed(ActionEvent event){
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

    @FXML
    public void reset(ActionEvent event){
        world.reset();
        vehiclesPane.getChildren().clear();
        updateLabels();
    }

    @FXML
    public void export(ActionEvent event){
        try {
            File file = directoryChooser.showDialog(new Stage());
            //Exporta o ficheiro das estatísticas na pasta escolhida
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
        //Cores
        lblRedValue.setText(statistics.getVehiclesValue("Red").toString());
        lblWhiteValue.setText(statistics.getVehiclesValue("White").toString());
        lblGoldenValue.setText(statistics.getVehiclesValue("Golden").toString());
        lblPurpleValue.setText(statistics.getVehiclesValue("Purple").toString());
        lblBlueValue.setText(statistics.getVehiclesValue("Blue").toString());

        //Servidos
        lblBottomValue.setText(statistics.getDirectionValue("Bottom").toString());
        lblTopValue.setText(statistics.getDirectionValue("Top").toString());
        lblLeftValue.setText(statistics.getDirectionValue("Left").toString());
        lblRightValue.setText(statistics.getDirectionValue("Right").toString());

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