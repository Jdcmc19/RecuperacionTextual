package view;

import domain.FileManager;
import domain.TFIDF;
import  domain.BM25;
import domain.VectorialStruct;
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
import java.util.TreeMap;

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
            int cantFiles=0;
            ArrayList<String> files;
            Map<String, ArrayList<VectorialStruct>> dicGeneral = new TreeMap<>();
            FileManager fileManager = new FileManager();

            //Agregar validacion cuando algun archivo no concuerda
            if(!pathColeccion.isEmpty() && !pathIndice.isEmpty() && !pathStopwords.isEmpty() && !consulta.isEmpty()){
                try {

                    //Devuelve funcion con los nombres de todos los arhivos
                    files = fileManager.showFiles(pathColeccion);
                    cantFiles = files.size();

                    String stopw = fileManager.getTextFile(pathStopwords);
                    //Lista con todos los stopwords
                    stopwords = stopw.split(",");

                }catch (FileNotFoundException f) {
                    f.printStackTrace();
                    return;
                }
                ArrayList<String> terminos;
                for(String f: files){
                    try{
                       // System.out.println(f + " PATH");
                        String text = fileManager.getTextFile(f);
                        text.replace("@","");


                        String path = pathColeccion+f.substring(f.lastIndexOf('\\'));
                       // System.out.println(path);
                        terminos = fileManager.createMap(text,stopwords,true);
                        dicGeneral = fileManager.getDiccionarioGeneral(path,terminos,dicGeneral);
                    }catch (FileNotFoundException fe){
                        fe.printStackTrace();
                        //System.out.println(f + " FUCCKKKKK");
                        return;
                    }
                }

                terminos = fileManager.createMap(consulta,stopwords,false);
                Map<String,Integer> dicCons = new TreeMap<>();
                //System.out.println(terminos.toString());
                dicCons = fileManager.getDiccionarioConsulta(terminos);

                //Salvan en archivos
                fileManager.saveDiccionario(dicGeneral,pathIndice+"\\DiccionarioGeneral",cantFiles);
                fileManager.saveConsulta(dicCons,pathIndice+"\\DiccionarioConsulta");



                BM25 bm25 = new BM25(1,1,cantFiles, dicGeneral,dicCons);
                 bm25.getAvgdl(bm25.getWdj());
                //TFIDF tfidf = new TFIDF(dicGeneral,dicCons,cantFiles);
                //tfidf.calcularUltimaTabla();

            }

        });

    }

}
