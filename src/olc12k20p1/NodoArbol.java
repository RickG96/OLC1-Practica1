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
public class NodoArbol {
    
    public String valor;
    public String tipo;
    public String id;
    
    public NodoArbol derecho;
    public NodoArbol izquierdo;
    
    public int identificador;
    public ArrayList<Integer> primeros;
    public ArrayList<Integer> ultimos;
    public boolean anulable;
    
    public NodoArbol(){
        this.derecho = null;
        this.izquierdo = null;
        this.identificador = 0;
        this.primeros = new ArrayList();
        this.ultimos = new ArrayList();
        this.anulable = false;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public ArrayList<Integer> getPrimeros() {
        return primeros;
    }

    public void setPrimeros(int primeros) {
        this.primeros.add(primeros);
    }

    public ArrayList<Integer> getUltimos() {
        return ultimos;
    }

    public void setUltimos(int ultimos) {
        this.ultimos.add(ultimos);
    }

    public boolean isAnulable() {
        return anulable;
    }

    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }
    
    
    
}
