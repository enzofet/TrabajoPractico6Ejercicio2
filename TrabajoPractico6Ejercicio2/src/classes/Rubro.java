/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author emadupre
 */
public enum Rubro {
    COMESTIBLE("Comestible"),
    LIMPIEZA("Limpieza"),
    PERFUMERIA("Perfumeria");
    
    private final String nombre;
    
    Rubro (String nombre){
        this.nombre = nombre;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
