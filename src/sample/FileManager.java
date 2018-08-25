package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.*;

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
    public void createMap() throws FileNotFoundException {
        File file = new File("C:\\Users\\iworth\\iCloudDrive\\Documents\\TEC\\Semestre II 2018\\Recuperacion textual\\Proyecto\\Tarea programada 1\\man-es\\man2\\accept.2");
        Scanner sc = new Scanner(file);

        sc.useDelimiter("\\Z");
        String texto = sc.next();
        String[] lineas = texto.split("\n");
        texto = "";
        for(String linea: lineas){
            linea = linea.trim();
            String[] words = linea.split(" ");
            if(words.length>0 && !words[0].equals(".\\\"")) {
                texto+=linea+" ";
            }
        }
        texto = texto.substring(0,texto.length()-1).toLowerCase();
        String[] palabras;
        texto = texto.replace("\t"," ").replace("á","a")
                .replace("é","e").replace("í","i")
                .replace("ó","o").replace("ú","u")
                .replace("."," ").replace(","," ")
                .replace("_"," ").replaceAll("\\W"," ").replace("-"," ");
               /* .replace("]"," ").replace("["," ")
                .replace("}"," ").replace("{"," ")
                .replace(")"," ").replace(")"," ");*/
        palabras = texto.split(" ");

        String[] stop = {"a","ante","bajo","cabe","con","contra","de","desde","e","el","en","entre","hacia","hasta","ni","la","le","lo","los","las","o","para","pero","por","que","segun","sin","so","uno","unas","unos","y","sobre","tras","u","un","una"};
        ArrayList<String> stopWords = new ArrayList<>(Arrays.asList(stop));
        ArrayList<String> terminos = new ArrayList<>(Arrays.asList(palabras));
        for(String palabra : stopWords){
            while(terminos.remove(palabra));
        }

        String a = "";
        Map<String,Integer> diccionario = new HashMap<String,Integer>();
        for(int e=0;e<terminos.size();e++){
            String tmp = terminos.get(e);/*
            if(tmp.length()>0 && (tmp.charAt(0)=='(' || tmp.charAt(0)=='[' || tmp.charAt(0)=='{' || tmp.charAt(0)=='\"' ))
                tmp=tmp.substring(1);
            if(tmp.length()>0 && (tmp.charAt(tmp.length()-1)==')' || tmp.charAt(tmp.length()-1)==']' || tmp.charAt(tmp.length()-1)=='}' || tmp.charAt(tmp.length()-1)=='\"' ))
                tmp=tmp.substring(0,tmp.length()-1);*/
            if(!tmp.equals("")){
                if(diccionario.containsKey(tmp)){
                    diccionario.put(tmp,diccionario.get(tmp)+1);
                }else{
                    diccionario.put(tmp,1);
                }
            }
        }
        ArrayList<String> llaves = new ArrayList<>(diccionario.keySet());
        Collections.sort(llaves, String.CASE_INSENSITIVE_ORDER);
        for(String termino : llaves){
            System.out.println(termino+": "+diccionario.get(termino));
        }
    }
}
