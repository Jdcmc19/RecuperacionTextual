package view;

import domain.FileManager;
import domain.TFIDF;
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
    TextField txtStopwordsInd, txtIndiceInd, txtColeccionInd,txtConsulta,txtIndiceBusqueda,txtEscalafonDir,txtHTMLDir,txtNumDoc;
    @FXML
    Button btoIniciarInd, btoStopwordsInd, btoColeccionInd, btoIndiceInd,btoIndiceBusqueda,btoEscalafon,btoHTML,btoIniciarBusqueda;
    @FXML
    RadioButton rdTfidf, rdBm25;

    FileManager fileManager = new FileManager();
    public void initialize(){
        ToggleGroup tg = new ToggleGroup();
        rdBm25.setToggleGroup(tg);
        rdTfidf.setToggleGroup(tg);

        btoStopwordsInd.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if(file!=null) txtStopwordsInd.setText(file.getAbsolutePath());
        });
        btoColeccionInd.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            txtColeccionInd.setText(selectedDirectory.getAbsolutePath());
        });
        btoIndiceInd.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            txtIndiceInd.setText(selectedDirectory.getAbsolutePath());
        });


        TextField[] txt = {txtIndiceInd, txtColeccionInd, txtStopwordsInd};
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
        btoIniciarInd.setOnAction(event -> {
            String pathColeccion = txtColeccionInd.getText();
            String pathIndice = txtIndiceInd.getText();
            String pathStopwords = txtStopwordsInd.getText();
            String[] stopwords;
            int cantFiles=0;
            ArrayList<String> files;
            Map<String, ArrayList<VectorialStruct>> dicGeneral = new TreeMap<>();
            FileManager fileManager = new FileManager();
            if(!pathColeccion.isEmpty() && !pathIndice.isEmpty() && !pathStopwords.isEmpty()){
                try {
                    files = fileManager.showFiles(pathColeccion);
                    cantFiles = files.size();

                    String stopw = fileManager.getTextFile(pathStopwords);
                    stopwords = stopw.split(",");

                }catch (FileNotFoundException f) {
                    f.printStackTrace();
                    return;
                }
                ArrayList<String> terminos;
                System.out.println("CANTIDAD FILES: "+files.size());
                for(String f: files){
                    try{
                        String text = fileManager.getTextFile(f);
                        text.replace("@","");


                        String path = pathColeccion+f.substring(f.lastIndexOf('\\'),f.length());
                        terminos = fileManager.createMap(text,stopwords,true);
                        dicGeneral = fileManager.getDiccionarioGeneral(path,terminos,dicGeneral);
                    }catch (FileNotFoundException fe){
                        fe.printStackTrace();
                        return;
                    }
                }




                fileManager.saveDiccionario(dicGeneral,pathIndice+"\\DiccionarioGeneral",cantFiles);

            }
        });
        btoIniciarBusqueda.setOnAction(event -> {
            String consulta =  txtConsulta.getText();
            String pathStopwords = txtStopwordsInd.getText();
            String indice = txtIndiceBusqueda.getText();
            String escalafon = txtEscalafonDir.getText();
            String html = txtHTMLDir.getText();
            String numDoc = txtNumDoc.getText();

            String[] stopwords;
            if(!consulta.isEmpty() && !pathStopwords.isEmpty() && !escalafon.isEmpty() && !html.isEmpty() && !numDoc.isEmpty()){
                try {
                    String stopw = fileManager.getTextFile(pathStopwords);
                    stopwords = stopw.split(",");
                }catch(FileNotFoundException fw){
                    fw.printStackTrace();
                    return;
                }
                ArrayList<String> terminos =  fileManager.createMap(consulta,stopwords,false);
                Map<String,Integer> dicCons = new TreeMap<>();
                dicCons = fileManager.getDiccionarioConsulta(terminos);
                fileManager.saveConsulta(dicCons,indice+"\\DiccionarioConsulta");
                TFIDF tfidf = new TFIDF(indice);
                tfidf.calcularUltimaTabla();
            }

        });

    }

}
