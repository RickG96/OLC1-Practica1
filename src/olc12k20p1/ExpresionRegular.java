/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc12k20p1;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Pistacho
 */
public class ExpresionRegular {
    
    String nombre;
    String expresion;
    ArrayList<NodoArbol> lista;
    Arbol arbolito;
    
    public ExpresionRegular() {
        lista = new ArrayList();
        this.arbolito = new Arbol();
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setExpresion(String expresion) {
        this.expresion = expresion;
        StringBuilder token = new StringBuilder();
        char cadena[] = this.expresion.toCharArray();
        for(int i = 0; i < cadena.length; i++) {
            NodoArbol nodito = new NodoArbol();
            if(cadena[i] == '.') {
                nodito.setValor(".");
                nodito.setTipo("and");
            } else if(cadena[i] == '+') {
                nodito.setValor("+");
                nodito.setTipo("unomas");
            } else if(cadena[i] == '*') {
                nodito.setValor("*");
                nodito.setTipo("ceromas");
            } else if(cadena[i] == '?') {
                nodito.setValor("?");
                nodito.setTipo("cerouno");
            } else if(cadena[i] == '|') {
                nodito.setValor("|");
                nodito.setTipo("or");
            } else if(cadena[i] == '\"') {
                for(int j = i+1; j < cadena.length; j ++) {
                    if(cadena[j] == '\"'){
                        i = j;
                        nodito.setValor(token.toString());
                        nodito.setTipo("cadena");
                        token.delete(0, token.length());
                        break;
                    }
                    
                    token.append(cadena[j]);
                }
            } else if(cadena[i] == '{') {
                for(int j = i+1; j < cadena.length; j ++) {
                    if(cadena[j] == '}'){
                        i = j;
                        nodito.setValor(token.toString());
                        nodito.setTipo("conj");
                        token.delete(0, token.length());
                        break;
                    }
                    
                    token.append(cadena[j]);
                }
            }
            nodito.setId("node" + i);
            lista.add(nodito);
        }
        this.crear();
    }
    
    public void crear() {
        Stack<NodoArbol> pila1 = new Stack();
        Stack<NodoArbol> pila2 = new Stack();
        
        for(int i = this.lista.size() - 1; i >= 0; i--) {
            
            if(lista.get(i).tipo.equals("and") || lista.get(i).tipo.equals("or")) {
                pila1.push(lista.get(i));
                
                if(pila2.size() >= 2) {
                    
                    NodoArbol padre = new NodoArbol();
                    padre = pila1.pop();
                    padre.setIzquierdo(pila2.pop());
                    padre.setDerecho(pila2.pop());
                    pila2.push(padre);
                    //agregar aqui el arbol terminado si la pila 1 esta vacia ya
                    
                }
            } else if(lista.get(i).getTipo().equals("unomas") || 
                    lista.get(i).getTipo().equals("ceromas") ||
                    lista.get(i).getTipo().equals("cerouno")) {
                
                pila1.push(lista.get(i));
                
                if(!pila2.isEmpty()) {
                    NodoArbol padre = new NodoArbol();
                    padre = pila1.pop();
                    padre.setIzquierdo(pila2.pop());
                    pila2.push(padre);
                }
            } else {
                pila2.push(lista.get(i));
            }
            
        }
        System.out.print("");
        this.arbolito.setRaiz(pila2.pop());
        this.arbolito.dibujar(this.arbolito.getRaiz());
        
        // insertar en la raiz un and y en la derecha de la raiz un # de tipo raiz
        Arbol a = new Arbol();
        NodoArbol extra = new NodoArbol();
        extra.setId("node100");
        extra.setTipo("and");
        extra.setValor(".");
        NodoArbol f = new NodoArbol();
        f.setId("node99");
        f.setTipo("final");
        f.setValor("#");
        extra.setDerecho(f);
        extra.setIzquierdo(this.arbolito.getRaiz());
        a.setRaiz(extra);
        //a.dibujar(a.getRaiz());
        a.hojas = 0;
        a.metodoArbol(a.getRaiz(), 0);
        a.hojas = 0;
        // hasta aqui todo bien todo correcto
        a.tablaSiguientes(a.getRaiz());
        a.agregarSiguientes(a.getRaiz());
        a.eliminarSiguientesDuplicados();
        //System.out.println(a.tabla.filas.size() + "");
        for(int i = 0; i < a.tabla.filas.size(); i++) {
            System.out.println(a.tabla.filas.get(i).getHoja() + " " + a.tabla.filas.get(i).getNumero() + " " + a.tabla.filas.get(i).getSiguientes().toString());
        }
    }
    
}
