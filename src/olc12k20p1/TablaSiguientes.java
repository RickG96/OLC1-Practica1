/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc12k20p1;

import java.util.ArrayList;

/**
 *
 * @author Pistacho
 */
public class TablaSiguientes {
    
    ArrayList<FilaSiguientes> filas;
    
    public TablaSiguientes() {
        this.filas = new ArrayList();
    }
    
    public void insertarFila(FilaSiguientes nuevo) {
        this.filas.add(nuevo);
    }
    
    public ArrayList<FilaSiguientes> getSiguientes() {
        return this.filas;
    }
    
}
