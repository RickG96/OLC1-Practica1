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
public class TablaTransiciones {
    
    ArrayList<FilaTransiciones> filaTrans;
    ArrayList<String> columnas;
    
    public TablaTransiciones() {
        this.filaTrans = new ArrayList();
        
    }
    
    public void insertar(FilaTransiciones nuevo) {
        this.filaTrans.add(nuevo);
    }
    
}
