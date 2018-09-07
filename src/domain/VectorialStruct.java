package domain;

import java.io.Serializable;

public class VectorialStruct implements Serializable {
    private String path;
    private int cantidad;

    public VectorialStruct(String path, int cantidad) {
        this.path = path;
        this.cantidad = cantidad;
    }

    public VectorialStruct() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
