/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc12k20p1;

import java.io.PrintWriter;

/**
 *
 * @author Pistacho
 */
public class Arbol {
    
    public NodoArbol raiz;
    String strArbol;
    int contador;
    
    public Arbol() {
        this.raiz = null;
        this.strArbol = "";
    }

    public NodoArbol getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoArbol raiz) {
        this.raiz = raiz;
    }
    
    public void crearNodos(NodoArbol r, boolean bandera) {
        if(bandera) {
            r = this.raiz;
        }
        if(r.getIzquierdo() != null) {
            crearNodos(r.getIzquierdo(), false);
        }
        this.strArbol += r.getId() + "[fontname=\"Arial\" fontsize=8 color=blue shape=circle peripheries=2 label=\"" + r.getValor() + "\"];\n";
        if(r.getDerecho() != null) {
            crearNodos(r.getDerecho(), false);
        }
    }
    
    public void crearAristas(NodoArbol r, boolean bandera) {
        if(bandera) {
            r = this.raiz;
        }
        if(r.getIzquierdo() != null) {
            this.strArbol += r.getId() + " -> " + r.getIzquierdo().getId() + "[color=blue]";
            crearAristas(r.getIzquierdo(), false);
        }
        if(r.getDerecho() != null) {
            this.strArbol += r.getId() + " -> " + r.getDerecho().getId() + "[color=blue]";
            crearAristas(r.getDerecho(), false);
        }
    }
    
    public void dibujar(NodoArbol nodo) {
        try {
            int max = 999;
            int min = 1;
            this.contador =(int)(Math.random() * (max - min + 1));
            PrintWriter doc = new PrintWriter("arbol" + this.contador + ".txt", "UTF-8");
            doc.println("digraph g {\n graph [splines=ortho];\n");
            crearNodos(nodo, true);
            crearAristas(nodo, true);
            this.strArbol = this.strArbol.replaceAll("null", "");
            doc.println(this.strArbol);
            doc.println("}");
            doc.close();
            Process p = Runtime.getRuntime().exec("dot -Tpng arbol" + this.contador + ".txt -o arbol" + this.contador + ".png");
            this.strArbol = "";
        } catch(Exception e) {
            System.err.println(e.toString());
        }
    }
    
}
