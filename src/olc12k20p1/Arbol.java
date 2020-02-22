/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc12k20p1;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Pistacho
 */
public class Arbol {
    
    public NodoArbol raiz;
    String strArbol;
    int contador;
    TablaSiguientes tabla;
    TablaTransiciones transiciones;
    
    public Arbol() {
        this.raiz = null;
        this.strArbol = "";
        this.tabla = new TablaSiguientes();
        this.transiciones = new TablaTransiciones();
    }

    public NodoArbol getRaiz() {
        return this.raiz;
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
            this.contador = (int)(Math.random() * (max - min + 1));
            PrintWriter doc = new PrintWriter("arbol" + this.contador + ".txt", "UTF-8");
            doc.println("digraph g {\n \n");
            crearNodos(nodo, true);
            crearAristas(nodo, true);
            this.strArbol = this.strArbol.replaceAll("null", "");
            doc.println(this.strArbol);
            doc.println("}");
            doc.close();
            Process p = Runtime.getRuntime().exec("dot -Tpng arbol" + this.contador + ".txt -o arbol" + this.contador + ".png");
            Interfaz.consola.append("Se ha creado la imagen: arbol" + this.contador + ".png \n\n");
            this.strArbol = "";
        } catch(Exception e) {
            System.err.println(e.toString());
        }
    }
    
    int hojas;
    
    public void metodoArbol(NodoArbol r, int contadorHojas) {
        if(r == null) return;
        
        if(r.getIzquierdo() != null) metodoArbol(r.getIzquierdo(), contadorHojas + 1);
        if(r.getDerecho() != null) metodoArbol(r.getDerecho(), contadorHojas + 1);
        if(r.getTipo().equals("cadena") || r.getTipo().equals("conj") || r.getTipo().equals("final")){
            hojas++;
            r.setAnulable(false);
            r.setPrimeros(hojas);
            r.setUltimos(hojas);
            r.setIdentificador(hojas);
        } else if(r.getTipo().equals("and")) {
            // set anulable
            if(r.getIzquierdo().isAnulable() && r.getDerecho().isAnulable()) r.setAnulable(true);
            // primeros
            if(r.getIzquierdo().isAnulable()) {
                for(int i = 0; i < r.getIzquierdo().getPrimeros().size(); i++) {
                    r.setPrimeros(r.getIzquierdo().getPrimeros().get(i));
                }
                for(int i = 0; i < r.getDerecho().getPrimeros().size(); i++) {
                    r.setPrimeros(r.getDerecho().getPrimeros().get(i));
                }
                Set<Integer> hashSet = new HashSet<Integer>(r.getPrimeros());
                r.getPrimeros().clear();
                r.primeros.addAll(hashSet);
            } else {
                for(int i = 0; i < r.getIzquierdo().getPrimeros().size(); i++) {
                    r.setPrimeros(r.getIzquierdo().getPrimeros().get(i));
                }
            }
            // ultimos
            if(r.getDerecho().isAnulable()) {
                for(int i = 0; i < r.getIzquierdo().getUltimos().size(); i++) {
                    r.setUltimos(r.getIzquierdo().getUltimos().get(i));
                }
                for(int i = 0; i < r.getDerecho().getUltimos().size(); i++) {
                    r.setUltimos(r.getDerecho().getUltimos().get(i));
                }
                Set<Integer> hashSet = new HashSet<Integer>(r.getPrimeros());
                r.getUltimos().clear();
                r.ultimos.addAll(hashSet);
            } else {
                for(int i = 0; i < r.getDerecho().getUltimos().size(); i++) {
                    r.setUltimos(r.getDerecho().getUltimos().get(i));
                }
            }
        } else if(r.getTipo().equals("or")) {
            if(r.getIzquierdo().isAnulable() || r.getDerecho().isAnulable()) r.setAnulable(true);
            //primeros
            for(int i = 0; i < r.getIzquierdo().getPrimeros().size(); i++) {
                r.setPrimeros(r.getIzquierdo().getPrimeros().get(i));
            }
            for(int i = 0; i < r.getDerecho().getPrimeros().size(); i++) {
                r.setPrimeros(r.getDerecho().getPrimeros().get(i));
            }
            Set<Integer> hashSet = new HashSet<Integer>(r.getPrimeros());
            r.getPrimeros().clear();
            r.primeros.addAll(hashSet);
            //ultmios
            for(int i = 0; i < r.getIzquierdo().getUltimos().size(); i++) {
                r.setUltimos(r.getIzquierdo().getUltimos().get(i));
            }
            for(int i = 0; i < r.getDerecho().getUltimos().size(); i++) {
                r.setUltimos(r.getDerecho().getUltimos().get(i));
            }
            Set<Integer> hashSet1 = new HashSet<Integer>(r.getPrimeros());
            r.getUltimos().clear();
            r.ultimos.addAll(hashSet1);
        } else if(r.getTipo().equals("unomas")) { // +
            if(r.getIzquierdo().isAnulable()) r.setAnulable(true);
            //primeros
            r.primeros.addAll(r.getIzquierdo().getPrimeros());
            //ultimos 
            r.ultimos.addAll(r.getIzquierdo().getUltimos());
        } else if(r.getTipo().equals("ceromas")) { // *
            r.setAnulable(true);
            //primeros
            r.primeros.addAll(r.getIzquierdo().getPrimeros());
            //ultimos 
            r.ultimos.addAll(r.getIzquierdo().getUltimos());
        } else if(r.getTipo().equals("cerouno")) { // ?
            r.setAnulable(true);
            //primeros
            r.primeros.addAll(r.getIzquierdo().getPrimeros());
            //ultimos 
            r.ultimos.addAll(r.getIzquierdo().getUltimos());
        }
        //System.out.println(r.getValor() + " " + r.isAnulable() + " " + r.getPrimeros().toString() + " " + r.getUltimos().toString());
    }
    
    // agregamos las filas para la tabla de siguientes
    public void tablaSiguientes(NodoArbol r) {
        if(r == null) return;
        
        if(r.getIzquierdo() != null) tablaSiguientes(r.getIzquierdo());
        if(r.getDerecho() == null && r.getIzquierdo() == null) {
            FilaSiguientes fila = new FilaSiguientes();
            fila.setHoja(r.getValor());
            fila.setNumero(r.getIdentificador());
            this.tabla.insertarFila(fila);
        }
        if(r.getDerecho() != null) tablaSiguientes(r.getDerecho());
        
    }
    
    public void agregarSiguientes(NodoArbol r) {
        if(r == null) return;
        
        if(r.getIzquierdo() != null) agregarSiguientes(r.getIzquierdo());
        if(r.getDerecho() != null) agregarSiguientes(r.getDerecho());
        
        if(r.getTipo().equals("unomas") || r.getTipo().equals("ceromas")) {
            for(int i = 0; i < r.getIzquierdo().getUltimos().size(); i++) {
                for(int j = 0; j < r.getIzquierdo().getPrimeros().size(); j++) {
                    this.tabla.filas.get(r.getIzquierdo().getUltimos().get(i) - 1).siguientes.addAll(r.getIzquierdo().getPrimeros());
                }
            }
        } else if(r.getTipo().equals("and")) {
            for(int i = 0; i < r.getIzquierdo().getUltimos().size(); i ++) {
                for(int j = 0; j < r.getDerecho().getPrimeros().size(); j++) {
                    this.tabla.filas.get(r.getIzquierdo().getUltimos().get(i) - 1).siguientes.addAll(r.getDerecho().getPrimeros());
                }
            }
        }
    }
    
    public void eliminarSiguientesDuplicados() {
        for(int i = 0; i < this.tabla.filas.size(); i++) {
            Set<Integer> hashSet = new HashSet<Integer>(this.tabla.filas.get(i).getSiguientes());
            this.tabla.filas.get(i).getSiguientes().clear();
            this.tabla.filas.get(i).siguientes.addAll(hashSet);
        }
    }
    
    public void dibujarTablaSiguientes() {
        try {
            int max = 999;
            int min = 1;
            this.contador = (int)(Math.random() * (max - min + 1));
            PrintWriter doc = new PrintWriter("siguientes" + this.contador + ".txt", "UTF-8");
            doc.println("digraph g {\n\n");
            doc.println("aHtmlTable [\nshape=plaintext\ncolor=coral1\nlabel=<\n\n");
            doc.println("<table border='1' cellborder='1'>");
            for(int i = 0; i < this.tabla.getSiguientes().size(); i++) {
                doc.println("<tr><td>" + this.tabla.getSiguientes().get(i).getHoja() + "</td><td>" + 
                        this.tabla.getSiguientes().get(i).getNumero() + "</td><td>" + 
                        this.tabla.getSiguientes().get(i).getSiguientes().toString() + "</td></tr>");
            }
            doc.println("</table>\n");
            doc.println(">];\n");
            doc.println("}");
            doc.close();
            Process p = Runtime.getRuntime().exec("dot -Tpng siguientes" + this.contador + ".txt -o siguientes" + this.contador + ".png");
            Interfaz.consola.append("Se ha creado la imagen: siguientes" + this.contador + ".png \n\n");
        } catch(Exception e) {
            System.err.println(e.toString());
        }
    }
    
    public void agregarEstados(NodoArbol nodo) {
        //Hasr un array list con un vector de ints, luego eliminar los duplicados, y por ultimo crear los estados
        ArrayList<ArrayList<Integer>> lista = new ArrayList();
        lista.add(nodo.primeros);
        for(int i = 0; i < this.tabla.getSiguientes().size(); i++) {
            if(!this.tabla.getSiguientes().get(i).getSiguientes().isEmpty())
                lista.add(this.tabla.getSiguientes().get(i).getSiguientes());
        }
        
        Set<ArrayList<Integer>> hsSet = new HashSet<ArrayList<Integer>>(lista);
        lista.clear();
        lista.addAll(hsSet);
        
        ArrayList<String> listColum = new ArrayList();
        for(int i = 0; i < this.tabla.getSiguientes().size() - 1; i++) {
            listColum.add(this.tabla.getSiguientes().get(i).getHoja());
        }
        Set<String> strCol = new HashSet<String>(listColum);
        listColum.clear();
        listColum.addAll(strCol);
        
        FilaTransiciones n = new FilaTransiciones();
        n.setEstado("Estado");
        n.irs = "-";
        for(int i = 0; i < listColum.size(); i++) {
            n.sigEstado.add(listColum.get(i));
        }
        this.transiciones.filaTrans.add(n);
        for(int i = 0; i < lista.size(); i++) {
            FilaTransiciones nueva = new FilaTransiciones();
            nueva.setEstado("S" + i);
            nueva.irs = lista.get(i).toString();
            this.transiciones.filaTrans.add(nueva);
            for(int j = 0; j < n.sigEstado.size(); j++) {
                nueva.sigEstado.add("-");
            }
        }
        
        for(int i = 1; i < this.transiciones.filaTrans.size(); i++) {
            String aux = this.transiciones.filaTrans.get(i).irs.replace("[", "").replace("]", "").replace(" ", "");
            String aux2[] = aux.split(",");
            //System.out.println(aux2.toString());
            for(int j = 0; j < aux2.length; j++) {
                for(int k = 0; k < this.tabla.getSiguientes().size(); k++) {
                    int nFilaSig = Integer.parseInt(aux2[j]);
                    String auxNombreHoja;
                    String auxSigHoja; // buscar en tabla de transiciones el nombre del estado segun los siguientes
                    int lugarColumna;
                    if(nFilaSig == this.tabla.getSiguientes().get(k).getNumero()) {
                        auxNombreHoja = this.tabla.getSiguientes().get(k).getHoja();// con este buscamos en la tabla de transiciones el lugar de la columna en el que lo insertaremos
                        auxSigHoja = this.tabla.getSiguientes().get(k).getSiguientes().toString();
                        for(int x = 1; x < this.transiciones.filaTrans.size(); x++) {
                            if(this.transiciones.filaTrans.get(x).irs.equals(auxSigHoja)) {
                                auxSigHoja = this.transiciones.filaTrans.get(x).estado;
                                break;
                            }
                        }
                        for(int y = 0; y < this.transiciones.filaTrans.get(0).sigEstado.size(); y++) {
                            if(this.transiciones.filaTrans.get(0).sigEstado.get(y).equals(auxNombreHoja)) {
                                this.transiciones.filaTrans.get(i).sigEstado.set(y, auxSigHoja);
                            }
                        }
                    }
                }
            }
        }
        
        for(int i = 0; i < this.transiciones.filaTrans.size(); i++) {
            System.out.println(this.transiciones.filaTrans.get(i).estado + " -- " +
                    this.transiciones.filaTrans.get(i).irs + 
                    this.transiciones.filaTrans.get(i).sigEstado.toString());
        }
        
        try {
            int max = 999;
            int min = 1;
            this.contador = (int)(Math.random() * (max - min + 1));
            PrintWriter doc = new PrintWriter("transiciones" + this.contador + ".txt", "UTF-8");
            doc.println("digraph g {\n\n");
            doc.println("aHtmlTable [\nshape=plaintext\ncolor=deeppink4\nlabel=<\n\n");
            doc.println("<table border='1' cellborder='1'>");
            String aux = "";
            for(int i = 0; i < this.transiciones.filaTrans.get(0).sigEstado.size(); i++) {
                aux += "<td>" + this.transiciones.filaTrans.get(0).sigEstado.get(i) + "</td>";
            }
            doc.print("<tr><td>" + this.transiciones.filaTrans.get(0).estado + "</td><td> - </td>" + aux + "</tr>");
            aux = "";
            for(int i = 1; i < this.transiciones.filaTrans.size(); i++) {
                for(int x = 0; x < this.transiciones.filaTrans.get(0).sigEstado.size(); x++) {
                    aux += "<td>" + this.transiciones.filaTrans.get(i).sigEstado.get(x) + "</td>";
                }
                doc.print("<tr><td>" + this.transiciones.filaTrans.get(i).estado + "</td><td>"+ this.transiciones.filaTrans.get(i).irs + "</td>" + aux + "</tr>");
                aux = "";
            }
            doc.println("</table>\n");
            doc.println(">];\n");
            doc.println("}");
            doc.close();
            Process p = Runtime.getRuntime().exec("dot -Tpng transiciones" + this.contador + ".txt -o transiciones" + this.contador + ".png");
            Interfaz.consola.append("Se ha creado la imagen: transiciones" + this.contador + ".png \n\n");
        } catch(Exception e) {
            
        }
        
    }
    
}
