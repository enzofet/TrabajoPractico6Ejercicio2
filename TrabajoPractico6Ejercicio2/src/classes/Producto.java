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
public class Producto implements Comparator<Producto> {

    private String descripcion;
    private double precio;
    private int codigo, stock;
    private Rubro rubro;

    public Producto(int codigo, String descripcion, double precio, Rubro rubro, int stock) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.codigo = codigo;
        this.rubro = rubro;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
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
        if (t.getCodigo() < t1.getCodigo()) {
            return -1;
        }
        if (t.getCodigo() > t1.getCodigo()) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Producto)) {
            return false;
        }
        Producto otro = (Producto) obj;
        return this.codigo == otro.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "Producto (" + "codigo = " + codigo + " | " + " descripcion = " + descripcion + " | " + "precio = " + precio + " | rubro = " + rubro + ")";
    }

}
