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
public class Analizador {
    
    String texto;
    ArrayList<Conjunto> conjuntos;
    ArrayList<ExpresionRegular> expresiones;
    ArrayList<Lexema> lexemas;
    
    public Analizador(String entrada) {
        this.texto = entrada;
        this.conjuntos = new ArrayList();
        this.expresiones = new ArrayList();
        this.lexemas = new ArrayList();
    }
    
    public void limpiarArchivo(String texto) {
        char cadenaEntrada[] = texto.toCharArray();
        
        String limpio;
        
        StringBuilder strLimpio = new StringBuilder();
        
        boolean estado = false;
        
        
        boolean comentarioM = false;
        boolean comentarioU = false;
        
        //Interfaz.consola.append(cadenaEntrada.length + "");
        int esExclamacion;
        for(int i = 0; i < cadenaEntrada.length; i++) {
            int asciiChar = (int) cadenaEntrada[i];
            
            if(asciiChar == 123) {
                estado = true;
            }
            
            if(estado && !comentarioM && !comentarioU) {
                if(asciiChar == 60) { //es un < de un comentario multilinea
                    esExclamacion = (int) cadenaEntrada[i + 1];   
                    if(esExclamacion == 33) comentarioM = true; //estado para los comentarios multilinea 
                } else if (asciiChar == 47) { // es un slash de un comentario de una linea
                    int esSlash = (int) cadenaEntrada[i + 1];
                    if(esSlash == 47) comentarioU = true; //estado para los comentarios de una linea
                } else if(asciiChar != 10 && (asciiChar < 32 || asciiChar > 128)) {
                    Interfaz.consola.append("Error lexico con el caracter: " + cadenaEntrada[i] + "\n");
                } else { //ignoramos cualquier otro caracter
                    strLimpio.append(cadenaEntrada[i]);
                }
                //strLimpio.append(cadenaEntrada[i]);
            }
            
            if(comentarioU) {
                if(asciiChar == 10) { // se acaba comentario de una linea
                    comentarioU = false;
                    strLimpio.append(cadenaEntrada[i]);
                }
            }
            if(comentarioM) {
                if(asciiChar == 62) {
                    esExclamacion = (int) cadenaEntrada[i - 1];
                    if(esExclamacion == 33) comentarioM = false;
                }
            }
            
        }
        
        limpio = strLimpio.toString();
        //Interfaz.consola.append(limpio);
        analizadorLexico(limpio);
    }
    
    public void analizadorLexico(String strlimpio) {
        strlimpio = strlimpio.replace("\n", "").replace("\r", "");
        
        String[] lines = strlimpio.split(";");
        
        StringBuilder sbToken = new StringBuilder();
        
        boolean conj = false;
        
        boolean lex = false;
        
        int contadorComillas = 0;
        
        int asciiActual;
        char lineaActual[];
        String token;
        for(int i = 0; i < lines.length; i++) {
            if(i == 0) lines[i] = lines[i].replace("{", "");
            //System.out.println(lines[i]);
            
            Conjunto nuevo = new Conjunto();
            ExpresionRegular expr = new ExpresionRegular();
            Lexema lexema = new Lexema(); 
            
            if(lines[i].charAt(0) == '%' && lines[i].charAt(2) == '%' && 
               lines[i].charAt(3) == '%' && lines[i].charAt(1) == '%') lex = true;
            
            if(lex == true) lines[i] = lines[i].replace("%%%%", "");
            
            lineaActual = lines[i].toCharArray();
            if(lineaActual[1] == '}') break; // estado de aceptacion
            
            
            for(int j = 0; j < lineaActual.length; j++) {
                asciiActual = (int) lineaActual[j];
                
                if(asciiActual == 58) {
                    token = sbToken.toString();
                    if(token.equals("CONJ")) {
                        conj = true;
                    }
                }
                if(asciiActual == 32 && conj == true && sbToken.length() > 2 && contadorComillas == 0) { // espacio
                    nuevo.setNombre(sbToken.toString());
                    sbToken.delete(0, sbToken.length());
                } else if(asciiActual == 45 && conj == false && sbToken.length() > 2 && contadorComillas == 0) {// REVISAR ESTO
                    expr.setNombre(sbToken.toString());
                    sbToken.delete(0, sbToken.length());
                } else if(asciiActual == 58 && sbToken.length() > 2 && contadorComillas == 0 && lex) {
                    lexema.setNombre(sbToken.toString());
                    sbToken.delete(0, sbToken.length());
                }
                
                if(asciiActual > 32 && asciiActual < 128) {
                        //comienza la linea
                    sbToken.append(lineaActual[j]);
                    if(asciiActual == 34) {
                        contadorComillas ++;
                    }
                } else if(asciiActual == 37){
                        //nada 
                }
                
                if(contadorComillas == 2) contadorComillas = 0;
                
                if(contadorComillas > 0 && asciiActual == 32) {
                    sbToken.append(lineaActual[j]);
                }
                
                if(sbToken.toString().replace(" ", "").equals("->") || (sbToken.toString().replace(" ", "").equals(":") && contadorComillas == 0)) {
                    sbToken.delete(0, sbToken.length());
                }
            }
            if(conj) {
                nuevo.setCadena(sbToken.toString().replace(" ", ""));
                this.conjuntos.add(nuevo);
                Interfaz.consola.append("Nuevo conjunto --> " + nuevo.nombre + " " + nuevo.cadena + "\n\n");
            } else if(!conj && !lex) {
                expr.setExpresion(sbToken.toString());
                Interfaz.consola.append("Nueva expresion regular -->" + expr.nombre + ": " + expr.expresion + "\n\n");
                this.expresiones.add(expr);
            } else if(lex) {
                lexema.setCadena(sbToken.toString().replace("\"", ""));
                Interfaz.consola.append("Nuevo lexema --> " + lexema.nombre + ": " + lexema.cadena + "\n\n");
            }
            conj = false;
            sbToken.delete(0, sbToken.length());
        }
    }
    
}
