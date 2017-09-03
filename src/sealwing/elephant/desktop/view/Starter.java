package sealwing.elephant.desktop.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Starter extends Application {

    private Stage stage;
    private SceneManager scene;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        scene = new SceneManager();
        stage = primaryStage;
        stage.setTitle("Elephant Manager");
        stage.setScene(scene.getScene());
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.show();
    }

}
