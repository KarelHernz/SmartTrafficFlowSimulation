package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.Objects;

public class MainApp extends Application {
    public static void main(String[] args) {launch(args);}

    //Janela do programa
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ControlPanel.fxml")));
        var primaryStage = new Stage();
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/semaforo_logo.png")));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Smart Traffic Flow Simulation");
        primaryStage.setScene(new Scene(root,1008, 654));
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
