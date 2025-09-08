/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Comparator;

/**
 *
 * @author Enzo_2
 */
public class Producto implements Comparator<Producto>{
    private String nombre, descripcion;
    private String rubro;
    private double precio;
    private int codigo;

    public Producto(int codigo, String nombre, String descripcion, double precio, String rubro) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.codigo = codigo;
        this.rubro = rubro;
    }
    
    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public int compare(Producto t, Producto t1) {
        if(t.getCodigo() < t1.getCodigo()){
            return 1;
        }
        if(t.getCodigo() > t1.getCodigo()){
            return -1;
        }
        return 0;
    }
    
}
