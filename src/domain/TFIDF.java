package domain;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.Map;
import java.lang.Math;
import java.util.TreeMap;
import java.util.Vector;

public class TFIDF {
    private int N;
    private Map<String, ArrayList<VectorialStruct>> diccionarioGeneral = new TreeMap<>();
    private Map<String, ArrayList<VectorialStruct>> diccionarioArchivos = new TreeMap<>();
    private Map<String, Integer>  diccionarioConsulta = new TreeMap<>();
    private Map<String,ArrayList<Map<String,Double>>> wijCalculada = new TreeMap<>();

    public TFIDF(String path) {
        FileManager fm = new FileManager();
        this.diccionarioGeneral = fm.readDiccionarioGeneral(path+"\\DiccionarioGeneral");
        this.diccionarioConsulta = fm.readConsulta(path+"\\DiccionarioConsulta");
        VectorialStruct t = this.diccionarioGeneral.get("!").get(0);
        if (t.getPath().equals("!")){
            this.N = t.getCantidad();
            this.diccionarioGeneral.remove("!");
        }
        getDiccionarioArchivos();
    }

    public TFIDF(Map<String, ArrayList<VectorialStruct>> diccionarioGeneral, Map<String, Integer> diccionarioConsulta, int N) {
        this.diccionarioGeneral = diccionarioGeneral;
        this.diccionarioConsulta = diccionarioConsulta;
        this.N = N;
        this.diccionarioGeneral.remove("!");
        getDiccionarioArchivos();

    }

    public Double getIDF(int ni){
        return Math.log10(N*1.0/ni*1.0)/Math.log10(2.0);
    }
    public Double getTF(int freqij){
        return 1+(Math.log10(freqij)/Math.log10(2.0));
    }
    public Double getWij(String word,String documentoPath){
        ArrayList<VectorialStruct>  vs = this.diccionarioGeneral.get(word);
        int freqij =0;
        int ni=0;
        if(vs != null && vs.size()>0){
            ni = vs.size();
            for(VectorialStruct vv : vs){
                if(vv.getPath().equals(documentoPath)){
                    freqij = vv.getCantidad();
                }
            }
            return getTF(freqij)*getIDF(ni);
        }
        return 0.0;
    }
    public Map<String,Double> getNormas(){
        Map<String,Double> map = new TreeMap<>();
        for(String path : this.diccionarioArchivos.keySet()){
            Double norma = 0.0;
            for(VectorialStruct vs: this.diccionarioArchivos.get(path)){
                Double value = getWij(vs.getPath(),path);
                Map<String,Double> tmp = new TreeMap<>();
                tmp.put(vs.getPath(),value);
                if(wijCalculada.containsKey(path)){
                    ArrayList<Map<String,Double>> tm = wijCalculada.get(path);
                    tm.add(tmp);
                    wijCalculada.put(path,tm);
                }else{
                    ArrayList<Map<String,Double>> tm = new ArrayList<>();
                    tm.add(tmp);
                    wijCalculada.put(path,tm);
                }

                norma+=Math.pow(value,2);
            }
            norma = Math.sqrt(norma);
            map.put(path,norma);
        }
        return map;
    }
    public void normalizarWij(){
        Map<String,Double> map = getNormas();
        for(String mm : wijCalculada.keySet()){
            for(int i=0;i<wijCalculada.get(mm).size();i++){
                Map<String,Double> mp = wijCalculada.get(mm).get(i);
                for(String key:mp.keySet()){
                   // System.out.println("HERE: "+mm+" : "+key+" : "+mp.get(key));
                    Map<String,Double> temp1 = new TreeMap<>();
                    temp1.put(key,mp.get(key)/map.get(mm));
                    wijCalculada.get(mm).set(i,temp1);
                }
            }
        }
        System.out.println("****************************************************************************************************");
        for(String mm : wijCalculada.keySet()){
            for(int i=0;i<wijCalculada.get(mm).size();i++){
                Map<String,Double> mp = wijCalculada.get(mm).get(i);
                for(String key:mp.keySet()){
                    System.out.println("HERE: "+mm+" : "+key+" : "+mp.get(key));
                }
            }
        }
        for(String a:map.keySet()){
            System.out.println(a+" : "+map.get(a));
        }

    }
    public void getDiccionarioArchivos(){
        for(String word : this.diccionarioGeneral.keySet()){
            for(VectorialStruct vs: this.diccionarioGeneral.get(word)){
                if(this.diccionarioArchivos!=null && this.diccionarioArchivos.containsKey(vs.getPath())){
                    ArrayList<VectorialStruct> tmp = this.diccionarioArchivos.get(vs.getPath());
                    tmp.add(new VectorialStruct(word,vs.getCantidad()));
                    this.diccionarioArchivos.put(vs.getPath(),tmp);
                }else{
                    ArrayList<VectorialStruct> t = new ArrayList<>();
                    t.add(new VectorialStruct(word,vs.getCantidad()));
                    this.diccionarioArchivos.put(vs.getPath(),t);
                }
            }
        }
    }

    public Map<String, ArrayList<Map<String, Double>>> getWijCalculada() {
        return wijCalculada;
    }
}
