package Grafo;

public class Main {

    public static void main(String[] args) {
    	
    	 GrafoMatrizAdjacencia grafo = new GrafoMatrizAdjacencia(6, true, true);

    	 grafo.inserirAresta(0, 1, 15);
         grafo.inserirAresta(0, 2, 9);
         
         grafo.inserirAresta(1, 3, 2);
         
         grafo.inserirAresta(2, 1, 4);
         grafo.inserirAresta(2, 3, 3);
         grafo.inserirAresta(2, 4, 16);
         
         grafo.inserirAresta(3, 4, 6);
         grafo.inserirAresta(3, 5, 21);
         
         grafo.inserirAresta(4, 5, 7);
        
        
        System.out.println("Matriz de Adjacência Inicial:");
        grafo.mostrarMatriz();

        
        grafo.dijkstra(0,5);     

    }
}
