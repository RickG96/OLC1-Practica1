/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc12k20p1;

/**
 *
 * @author Pistacho
 */
public class ExpresionRegular {
    
    String nombre;
    String expresion;
    Arbol arbolito;
    
    
    public ExpresionRegular() {
        arbolito = new Arbol();
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }
    
}
