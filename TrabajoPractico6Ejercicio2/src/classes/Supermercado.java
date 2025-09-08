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
    
    public Supermercado(){}
    
    public void anadirProducto(Producto p){
        catalogo.add(p);
    }
    
    public void sobreescribirProducto(Producto p){
        if(catalogo.contains(p)){
            
        }
    }
    
    
    public void eliminarProducto(int codigo){
        for(Producto p : catalogo){
            if(codigo == p.getCodigo()){
                catalogo.remove(p);
            }
        }
    }
    
    public List buscarProductos(String rubro){
        List<Producto> productos = new ArrayList();
        for(Producto p : catalogo){
            if(p.getRubro().equalsIgnoreCase(rubro)){
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
