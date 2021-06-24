package org.player.mp3player;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.ResourceBundle;

@Slf4j
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        log.info("Starting Java MP3 Player");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainWindow.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("org.player.mp3player.lang.default"));
        Parent root = fxmlLoader.load();

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
                log.info("Java MP3 Player closed correctly");
                System.exit(0);


            }
        });

    }

}
