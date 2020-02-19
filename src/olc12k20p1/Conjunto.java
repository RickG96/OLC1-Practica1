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
public class Conjunto {
    
    String nombre;
    boolean tipo; //true = rango, false = especificos
    ArrayList<Character> caracteres; 
    String cadena;
    
    public Conjunto() {
        caracteres = new ArrayList();
    }
    
    public void setCadena(String cadena) {
        this.cadena = cadena;
        
        char chars[] = this.cadena.toCharArray();
        
        int asciiActual = (int) chars[1];
        if(asciiActual == 44) { //coma
            String charsComa[] = this.cadena.split(",");
            for (String charsComa1 : charsComa) {
                this.setChar(charsComa1.charAt(0));
            }
        } else if(asciiActual == 126) {
            int charA = (int) chars[0];
            int charB = (int) chars[2];
            for(int i = charA; i <= charB; i++) {
                char c = (char) i;
                this.setChar(c);
            }
        }
        
    }
    
    public void setChar(char caracter) {
        this.caracteres.add(caracter);
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }
    
}
