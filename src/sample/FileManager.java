package sample;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {

    //ArrayList que va a contener los path de los subdirectorios
    private ArrayList<String> subDirPath = new ArrayList<>();

    public FileManager() {
    }

    //Funcion que muerta los archivos por subdirectorio
    public void showFiles (String directoryPath)
    {
        File dicPath = new File(directoryPath);

        //Función override obtiene el nombre de los subdirectorios (Convertida a notación lambda)
        String[] directories = dicPath.list((current, name) -> new File(current, name).isDirectory());
        for(int i = 0; i<directories.length; i++)
        {
            subDirPath.add(directoryPath+"\\"+directories[i]);
        }


        //Obtiene todos los archivos por subdirectorio
        for (int y = 0; y<subDirPath.size(); y++)
        {
            File folder = new File(subDirPath.get(y).toString());
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles)
            {
                if (file.isFile())
                {
                    System.out.println(file.getName());
                }
            }
        }
    }
}
