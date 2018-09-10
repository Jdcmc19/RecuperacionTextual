package view;

import domain.FileManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
<<<<<<< HEAD
        primaryStage.setTitle("Consulta de Archivos");
        primaryStage.setScene(new Scene(root, 414, 334));
=======
        primaryStage.setTitle("Hola joseph");
        primaryStage.setScene(new Scene(root, 423, 466));
>>>>>>> a20a7058c30cd123118ac2e24f290821e3a86af3
        primaryStage.show();
    }


    public static void main(String[] args)throws FileNotFoundException {
        launch(args);


    }
}
