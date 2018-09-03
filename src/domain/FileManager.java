package domain;

import com.sun.deploy.util.StringUtils;

import java.io.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;


public class FileManager {

    //ArrayList que va a contener los path de los subdirectorios
    private ArrayList<String> subDirPath = new ArrayList<>();
    private ArrayList<String> filesWValidate = new ArrayList<>();

    public FileManager() {
    }

    //Funcion que muerta los archivos por subdirectorio
    public void showFiles (String directoryPath) throws FileNotFoundException {
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
                    String validate =   validateFile(file.getName());
                    if(!validate.isEmpty())
                    {
                        filesWValidate.add(validate);
                    }

                    //System.out.println(file.getName());

                }
            }
        }
    }

    public String validateFile(String filePath) throws FileNotFoundException {
        Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(1|2|3|4|5|6|7|8))$)");
            Matcher mtch = fileExtnPtrn.matcher(filePath);
            if(!mtch.matches())
            {
                return filePath;

            }
            else return  "";
    }

    public String getTextFile(String path)throws FileNotFoundException{
        File file = new File(path);
        Scanner sc = new Scanner(file);

        sc.useDelimiter("\\Z");
        return sc.next();
    }
    public void createMap(String text, String[] stop)
    {

        String[] lineas = text.split("\n");
        String texto = "";
        for(String linea: lineas){
            linea = linea.trim();
            String[] words = linea.split(" ");
            if(words.length>0 && !words[0].equals(".\\\"")) {
                if(words[0].startsWith(".") && words[0].length()<4){
                    for(int i=1;i<words.length;i++){
                        texto+=(words[i]+" ");
                    }
                }
                else
                    texto+=linea+" ";
            }
        }
        texto = texto.substring(0,texto.length()-1).toLowerCase();
        String[] palabras;
        texto = texto.replace("\t"," ").replace("á","a")
                .replace("é","e").replace("í","i")
                .replace("ó","o").replace("ú","u").replace("\\-\\-","@")
                .replaceAll("[(){}_,!`~#$%^&*/\'\"+<>?:;|\\-]"," ");
        palabras = texto.split(" ");
        for(int i=0;i<palabras.length;i++){
            palabras[i]= quitarNonWords(palabras[i]);
        }

        ArrayList<String> stopWords = new ArrayList<>(Arrays.asList(stop));
        ArrayList<String> terminos = new ArrayList<>(Arrays.asList(palabras));
        for(String palabra : stopWords){
            while(terminos.remove(palabra));
        }

        Map<String,Integer> diccionario = new HashMap<String,Integer>();
        for(int e=0;e<terminos.size();e++){
            String tmp = terminos.get(e);
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

    public String quitarNonWords(String palabra){
        String[] tmp = palabra.split("\\.");
        Boolean si = true;
        for(String t: tmp){
            if(!t.matches("[0-9]+"))
                si=false;
        }
        if(palabra.contains(".") && si && tmp.length>=2) {
            return palabra;
        }
        else if(palabra.contains("\\f")){
            return "";
        }
        else{
            return palabra.replaceAll("[\\.\\\\]","");
        }

    }


    public void saveMap(Map<String,Integer> map,String path)
    {
        try{
            File fileOne=new File(path);
            FileOutputStream fos=new FileOutputStream(fileOne);
            ObjectOutputStream oos=new ObjectOutputStream(fos);

            oos.writeObject(map);
            oos.flush();
            oos.close();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void readMap(String path){
        //read from file
        try{
            File toRead=new File(path);
            FileInputStream fis=new FileInputStream(toRead);
            ObjectInputStream ois=new ObjectInputStream(fis);

            HashMap<String,Integer> mapInFile=(HashMap<String,Integer>)ois.readObject();

            ois.close();
            fis.close();
            //print All data in MAP
            for(Map.Entry<String,Integer> m :mapInFile.entrySet()){
                System.out.println(m.getKey()+" : "+m.getValue());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
