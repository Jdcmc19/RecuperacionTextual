package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void validateFiles(File[] listOfFiles)
    {
        Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(1|2|3|4|5|6|7|8))$)");
        File[] subDirPathTemp = listOfFiles;
        int sizeTemp = listOfFiles.length;
        for(int i = 0; i< sizeTemp; i++)
        {
            Matcher mtch = fileExtnPtrn.matcher(listOfFiles[i].getName());
            if(!mtch.matches())
            {

            }
        }
    }
}
