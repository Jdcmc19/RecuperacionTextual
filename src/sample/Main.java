package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hola joseph");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args)throws FileNotFoundException {

        FileManager test = new FileManager();
      //  test.showFiles("C:\\Users\\Joseph Salas\\Desktop\\TEC\\VI Semestre\\Información Textual\\Tarea programada 1\\man-es");
        //test.createMap();
        //launch(args);
        Map<String,Integer> hm =new HashMap<String,Integer>();

        hm.put("Hola",3);
        hm.put("Joseph",4);
        hm.put("Juande",5);

        test.saveMap(hm);


    }
}
