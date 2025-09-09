/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author Enzo_2
 */
public class Supermercado {

    private TreeSet<Producto> catalogo;

    public Supermercado() {
        this.catalogo = new TreeSet<>();
    }

    public void anadirProducto(Producto p) {
        catalogo.add(p);
    }

    public void sobreescribirProducto(Producto p) {
        Iterator<Producto> iterador = catalogo.iterator();
        while(iterador.hasNext()){
            Producto producto = iterador.next();
            if (producto.getCodigo() == p.getCodigo()) {
                iterador.remove();
            }
        }
        catalogo.add(p);

    }
    
    public ArrayList<Producto> buscarPorPrecio(double precioMin, double precioMax){
        ArrayList<Producto> productos = new ArrayList();
        for(Producto p : catalogo){
            if(p.getPrecio() >= precioMin && p.getCodigo() <= precioMax){
                productos.add(p);
            }
        }
        return productos;
    }
    
   public ArrayList<Producto> buscarPorNombre(String nombre){
        ArrayList<Producto> productos = new ArrayList();
        for(Producto p : catalogo){
            if(p.getDescripcion().equalsIgnoreCase(nombre)){
                productos.add(p);
            }
        }
        return productos;
        
    }

    public void eliminarProducto(int codigo) {
        Iterator<Producto> iterador = catalogo.iterator();
        while(iterador.hasNext()){
            Producto p = iterador.next();
            if(p.getCodigo() == codigo){
                iterador.remove();
                break;
            }
        }
    }

    public ArrayList<Producto> buscarProductosRubro(Rubro rubro) {
        ArrayList<Producto> productos = new ArrayList();
        for (Producto p : catalogo) {
            if(p.getRubro() == rubro){
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
