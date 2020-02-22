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
public class FilaTransiciones {
    
    String estado;
    ArrayList<String> sigEstado; // siguientes del estado
    String irs;
    boolean estadoFinal;
    
    public FilaTransiciones() {
        this.sigEstado = new ArrayList();
        this.estadoFinal = false;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        return this.estado;
    }
    
}
