package org.player.mp3player;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/MainWindow.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("css/MainWindow.css").toExternalForm());
//        System.out.println(getClass().getResource("controllers/MainWindow.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                Platform.exit();
                System.exit(0);

            }
        });

    }

}
