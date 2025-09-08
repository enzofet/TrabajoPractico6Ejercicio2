/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author Enzo_2
 */
public class Supermercado {

    private TreeSet<Producto> catalogo;

    public Supermercado() {
    }

    public void anadirProducto(Producto p) {
        catalogo.add(p);
    }

    public void sobreescribirProducto(Producto p) {
        for (Producto producto : catalogo) {
            if (producto.equals(p.getCodigo())) {
                catalogo.remove(producto);
                catalogo.add(p);
            }
        }

    }
    
    public List buscarPorPrecio(double precioMin, double precioMax){
        List<Producto> productos = new ArrayList();
        for(Producto p : catalogo){
            if(p.getPrecio() > precioMin && p.getCodigo() < precioMax){
                productos.add(p);
            }
        }
        return productos;
    }
    
    public List buscarPorNombre(String nombre){
        List<Producto> productos = new ArrayList();
        for(Producto p : catalogo){
            if(p.getNombre().equalsIgnoreCase(nombre)){
                productos.add(p);
            }
        }
        return productos;
        
    }

    public void eliminarProducto(int codigo) {
        for (Producto p : catalogo) {
            if (codigo == p.getCodigo()) {
                catalogo.remove(p);
            }
        }
    }

    public List buscarProductosRubro(Rubro rubro) {
        List<Producto> productos = new ArrayList();
        for (Producto p : catalogo) {
            if (p.getRubro() == rubro) {
                productos.add(p);
            }
        }
        return productos;
    }

    public TreeSet<Producto> getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(TreeSet<Producto> catalogo) {
        this.catalogo = catalogo;
    }

}
