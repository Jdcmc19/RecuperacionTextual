package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hola joseph");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        FileManager test = new FileManager();
        test.showFiles("C:\\Users\\Joseph Salas\\Desktop\\TEC\\VI Semestre\\Información Textual\\Tarea programada 1\\man-es");
        //launch(args);
    }
}
