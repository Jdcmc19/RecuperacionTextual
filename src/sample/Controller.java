package sample;

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

public class Controller {
    @FXML
    TextField txtStopwords,txtIndice,txtColeccion;
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
            System.out.println(txtColeccion.getText());
            System.out.println(txtIndice.getText());
            System.out.println(txtStopwords.getText());
            if(rdTfidf.isSelected()) System.out.println(rdTfidf.getText());
            else System.out.println(rdBm25.getText());
        });

    }

}
