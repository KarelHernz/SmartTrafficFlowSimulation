package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {
    public static void main(String[] args) {launch(args);}


    //Janela do programa
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ControlPanel.fxml")));
        var primaryStage = new Stage();
        primaryStage.setTitle("Hello world");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
