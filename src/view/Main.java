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
<<<<<<< HEAD
        primaryStage.setTitle("Consulta de Archivos");
=======
        primaryStage.setTitle("Hola joseph");
>>>>>>> parent of 4cbcc50... Modelo BM25
        primaryStage.setScene(new Scene(root, 414, 334));
=======
        primaryStage.setTitle("Hola joseph");
        primaryStage.setScene(new Scene(root, 423, 466));
>>>>>>> a20a7058c30cd123118ac2e24f290821e3a86af3
        primaryStage.show();
    }


    public static void main(String[] args)throws FileNotFoundException {

        FileManager test = new FileManager();

      //  test.showFiles("C:\\Users\\Joseph Salas\\Desktop\\TEC\\VI Semestre\\Información Textual\\Tarea programada 1\\man-es");
        //test.createMap();
        /*String a = "hóla me llam(oñ j\"\"ua^n de {di})o's!";
        System.out.println(a);
        System.out.println(a.replaceAll("[\\W ]",""));*/
        launch(args);

       /* Map<String,Integer> hm =new HashMap<String,Integer>();
        String[] stop = {"a","ante","bajo","cabe","con","contra","de","desde","e","el","en","entre","hacia","hasta","ni","la","le","lo","los","las","o","para","pero","por","que","segun","sin","so","uno","unas","unos","y","sobre","tras","u","un","una"};

        hm.put("Hola",3);
        hm.put("Joseph",4);
        hm.put("Juande",5);

        test.saveMap(hm);*/

       //test.showFiles("C:\\Users\\Joseph Salas\\Desktop\\TEC\\VI Semestre\\Información Textual\\Tarea programada 1\\man-es");



    }
}
