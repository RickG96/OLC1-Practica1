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
public class FilaSiguientes {
    
    public String hoja;
    public int numero;
    ArrayList<Integer> siguientes;
    
    public FilaSiguientes() {
        this.hoja = "";
        this.numero = 0;
        this.siguientes = new ArrayList();
    }

    public String getHoja() {
        return hoja;
    }

    public void setHoja(String hoja) {
        this.hoja = hoja;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ArrayList<Integer> getSiguientes() {
        return siguientes;
    }

    public void setSiguientes(int siguientes) {
        this.siguientes.add(siguientes);
    }
    
    
    
}
