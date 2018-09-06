package domain;

import java.util.ArrayList;
import java.util.Map;
import java.lang.Math;

public class TFIDF {
    private int N;
    private Map<String, ArrayList<VectorialStruct>> diccionarioGeneral;
    private Map<String, Integer>  diccionarioConsulta;

    public TFIDF(String path) {
        FileManager fm = new FileManager();
        this.diccionarioGeneral = fm.readDiccionarioGeneral(path+"\\DiccionarioGeneral");
        this.diccionarioConsulta = fm.readConsulta(path+"\\DiccionarioConsulta");
        VectorialStruct t = this.diccionarioGeneral.get("!").get(0);
        if (t.getPath().equals("!")){
            this.N = t.getCantidad();
            this.diccionarioGeneral.remove("!");
        }
    }

    public TFIDF(Map<String, ArrayList<VectorialStruct>> diccionarioGeneral, Map<String, Integer> diccionarioConsulta, int N) {
        this.diccionarioGeneral = diccionarioGeneral;
        this.diccionarioConsulta = diccionarioConsulta;
        this.N = N;
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

}
