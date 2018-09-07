package view;

import domain.FileManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

public class Controller {
    @FXML
    TextField txtStopwords,txtIndice,txtColeccion,txtConsulta;
    @FXML
    Button btoIniciar, btoStopwords,btoColeccion,btoIndice;
    @FXML
    RadioButton rdTfidf, rdBm25;
    public void initialize(){
        ToggleGroup tg = new ToggleGroup();
        rdBm25.setToggleGroup(tg);
        rdTfidf.setToggleGroup(tg);

        btoStopwords.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if(file!=null)txtStopwords.setText(file.getAbsolutePath());
        });
        btoColeccion.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            txtColeccion.setText(selectedDirectory.getAbsolutePath());
        });
        btoIndice.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            txtIndice.setText(selectedDirectory.getAbsolutePath());
        });


        TextField[] txt = {txtIndice,txtColeccion,txtStopwords};
        for(TextField t: txt){
            t.setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    Dragboard db = event.getDragboard();
                    if (db.hasFiles()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    } else {
                        event.consume();
                    }
                }
            });
            t.setOnDragDropped(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasFiles()) {
                        success = true;
                        String filePath = null;
                        for (File file:db.getFiles()) {
                            filePath = file.getAbsolutePath();
                            t.setText(filePath);
                        }
                    }
                    event.setDropCompleted(success);
                    event.consume();
                }
            });
        }
        btoIniciar.setOnAction(event -> {
            String pathColeccion = txtColeccion.getText();
            String pathIndice = txtIndice.getText();
            String pathStopwords = txtStopwords.getText();
            String consulta = txtConsulta.getText();
            String[] stopwords;
            ArrayList<String> files = new ArrayList<>();
            FileManager fileManager = new FileManager();
            if(!pathColeccion.isEmpty() && !pathIndice.isEmpty() && !pathStopwords.isEmpty() && !consulta.isEmpty()){
                try {
                    //files = fileManager.showFiles(pathColeccion);
                    String stopw = fileManager.getTextFile(pathStopwords);
                    stopwords = stopw.split(",");
                }catch (FileNotFoundException f) {
                    f.printStackTrace();
                    System.out.println(" SGHIT");
                    return;
                }
                for(String f: files){
                    try{
                        String text = fileManager.getTextFile(f);
                        text.replace("@","");
                        Map<String,Integer> terminosArchivo = fileManager.createMap(text,stopwords);
                        String nombre = f.substring(f.lastIndexOf('\\'),f.length()-1);
                        fileManager.saveMap(terminosArchivo,pathIndice+nombre);

                    }catch (FileNotFoundException fe){
                        fe.printStackTrace();
                        System.out.println(f + " FUCCKKKKK");
                        return;
                    }
                }
                Map<String,Integer> consultaIndex = fileManager.createMap(consulta,stopwords);
                System.out.println(consultaIndex.keySet().toString());
            }

        });

    }

}
