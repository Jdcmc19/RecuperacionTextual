package domain;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class BM25 {

    private int N;
    private double k1;
    private double b;

    //Llave son todas las palabras y adentro tiene lista de diccionarios -->Archivo tal tiene tantos
    private Map<String, ArrayList<VectorialStruct>> diccionarioGeneral;
    //Igual pero se le da vuelta. Llave geenral son los paths y la cantidad de palabras por archivo
    private Map<String, ArrayList<VectorialStruct>> diccionarioArchivos = new TreeMap<>();
    //Diccionario de la consulta
    private Map<String, Integer>  diccionarioConsulta;
    //Diccionario con los pesos de cada palabra
    private Map<String,ArrayList<Map<String,Double>>> wijCalculada = new TreeMap<>();
    //Pesos de la consulta
    private Map<String, Double> wijConsulta = new TreeMap<>();
    //lISTA CON EL RANKING
    private ArrayList<Rank> ranking = new ArrayList<>();


    public BM25(double _k1, double _b, int n, Map<String, ArrayList<VectorialStruct>> diccionarioGeneral, Map<String, Integer> diccionarioConsulta) {
        N = n;
        this.b = _b;
        this.k1 = _k1;
        this.diccionarioGeneral = diccionarioGeneral;
        this.diccionarioConsulta = diccionarioConsulta;
        getDiccionarioArchivos();

    }

    public Double getIDF(int ni){
        return Math.log10((N-ni+0.5)/(ni+0.5))/Math.log10(2.0);
    }




    public Double getNidf(String word){
        ArrayList<VectorialStruct>  vs = this.diccionarioGeneral.get(word);
        int freqij =0;
        int ni=0;
        if(vs != null && vs.size()>0){
            ni = vs.size();
            return getIDF(ni);
        }
        return 0.0;
    }

    private int  getFregij(String word,String documentoPath)
    {
        ArrayList<VectorialStruct>  vs = this.diccionarioGeneral.get(word);
        int freqij =0;
        if(vs != null && vs.size()>0) {
            for (VectorialStruct vv : vs) {
                if (vv.getPath().equals(documentoPath)) {
                    freqij = vv.getCantidad();
                }
            }
        }
        return  freqij;

    }



    public Map<String, Integer> getWdj() {
        Map<String, Integer> map = new TreeMap<>();
        for (String path : this.diccionarioArchivos.keySet()) {
            int dj = 0;
            for (VectorialStruct vs : this.diccionarioArchivos.get(path)) {
                int value = getFregij(vs.getPath(), path);
                Map<String, Integer> tmp = new TreeMap<>();
                tmp.put(vs.getPath(), value);
                dj += value;
                map.put(path,dj);
            }

        }
        for(String path: map.keySet())
        {
            System.out.println(path.toString()+ " "+ map.get(path).toString());
        }
        return map;
    }



    public double getAvgdl(Map<String, Integer> map)
    {
        double numMaps = 0;
        double dj = 0;
        for(String path: map.keySet())
        {
            dj+=map.get(path);
            numMaps+=1;
        }
        System.out.println(dj/numMaps);
        return dj/numMaps;
    }

    public void calculoTabla( Map<String, Integer> map)
    {
        double avgdl = getAvgdl(map);
        Map<String,ArrayList<Map<String,Double>>> last = new TreeMap();
        for (String key: diccionarioArchivos.keySet())
        {
            for (String keyQ: diccionarioConsulta.keySet())
            {
                Double factor1=0.0;
                Double factor2=0.0;
                Double total;
                if(factor2!= null && factor1!= null)
                {
                    total =.00;
                }

            }

        }



    }




    public void getDiccionarioArchivos(){
        for(String word : this.diccionarioGeneral.keySet()){
            for(VectorialStruct vs: this.diccionarioGeneral.get(word)){
                if(this.diccionarioArchivos!=null && this.diccionarioArchivos.containsKey(vs.getPath())){
                    ArrayList<VectorialStruct> tmp = this.diccionarioArchivos.get(vs.getPath());
                    tmp.add(new VectorialStruct(word,vs.getCantidad()));
                    this.diccionarioArchivos.put(vs.getPath(),tmp);
                }
                else
                {
                    ArrayList<VectorialStruct> t = new ArrayList<>();
                    t.add(new VectorialStruct(word,vs.getCantidad()));
                    this.diccionarioArchivos.put(vs.getPath(),t);
                }
            }
        }
    }
}

