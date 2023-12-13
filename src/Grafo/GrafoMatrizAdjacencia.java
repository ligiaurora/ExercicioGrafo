package Grafo;

import java.util.Arrays;

public class GrafoMatrizAdjacencia {
	private int[][] G;
	private int numVertices;
	private boolean ponderado;
	private boolean direcionado;
	
	
	
	public GrafoMatrizAdjacencia(int numVertices, boolean ponderado, boolean direcionado) {
		this.numVertices = numVertices;
		this.direcionado = direcionado;
		this.ponderado = ponderado;
		
		G = new int[numVertices][numVertices];
	}
	
	
	public void inserirAresta(int vertice1, int vertice2) {
		if(ponderado) {
			G[vertice1][vertice2] = 1;
			if(direcionado) {
				G[vertice2][vertice1] = 1;
			}
		}else {
			System.out.println("Grafo ponderado. Necessita peso da aresta.");
		}
	}
	
	public void inserirAresta(int vertice1, int vertice2,int peso) {
		if(ponderado) {
			G[vertice1][vertice2] = peso;
			if(direcionado) {
				G[vertice2][vertice1]= peso;
			}
		}else {
			System.out.println("Grafo não ponderado. Arestas não possuem pesos");
		}
	}
	
	public void removerAresta(int vertice1, int vertice2) {
		
		G[vertice1][vertice2]=0;
		if(direcionado) {
			G[vertice2][vertice1]=0;
		}
		
	}
	


	public void mostrarMatriz() {
	   for(int i=0; i<numVertices; i++) {
		   for(int j=0; j<numVertices; j++) {
			   System.out.print(G[i][j] + "; ");
		   } 
		   System.out.println();
	   }
	}
	
	public int saoAdjacentes(int vertice1, int vertice2) {
	    return G[vertice1][vertice2];
	}
	
	
	/*Os metodos abaixo foram desenvolvidos para resolução da primeira questão do exercicio de grafo*/
	
	

	/*Remoção de Vertice | Resolução A da 1 questão*/
	public void removerVertice(int vertice) {
        if (vertice >= 0 && vertice < numVertices) {
            // Remover arestas conectadas ao vértice
            for (int i = 0; i < numVertices; i++) {
                G[vertice][i] = 0;
                G[i][vertice] = 0;
            }

            // Deslocamento das linhas e colunas da matriz para preenchimento do espaço do vértice removido
            for (int i = vertice; i < numVertices - 1; i++) {
                for (int j = 0; j < numVertices; j++) {
                    G[i][j] = G[i + 1][j];
                }
            }

            for (int j = vertice; j < numVertices - 1; j++) {
                for (int i = 0; i < numVertices; i++) {
                    G[i][j] = G[i][j + 1];
                }
            }

            // Atualização do número de vértices
            numVertices--;
        } else {
            System.out.println("Vértice inválido.");
        }
    }
	
	
	 /*verificação se o grafo é conexo ou não | Resolução B da 1 questão*/
	public boolean grafoConexo() {
	    boolean[] visitado = new boolean[numVertices];
	    Arrays.fill(visitado, false);

	    dfs(0, visitado);

	    // Verifica se todos os vértices foram visitados
	    for (boolean v : visitado) {
	        if (!v) {
	            return false;
	        }
	    }

	    return true;
	}

	private void dfs(int vertice, boolean[] visitado) {
	    visitado[vertice] = true;

	    for (int i = 0; i < numVertices; i++) {
	        if (G[vertice][i] != 0 && !visitado[i]) {
	            dfs(i, visitado);
	        }
	    }
	}
	
	
   
    /*verificação se o grafo é completo ou não | Resolução  C da 1 questão*/
    public boolean grafoCompleto() {
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                // Verifica se não é a diagonal principal e se a aresta é igual a zero
                if (i != j && G[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    /*Encontro do caminho mais curto com o algoritmo de Dijkstra | Resolução D da 1 questao*/
    
    public void dijkstra(int origem, int destino) {
        int[] distancia = new int[numVertices];
        boolean[] visitado = new boolean[numVertices];

        Arrays.fill(distancia, Integer.MAX_VALUE);
        Arrays.fill(visitado, false);

        distancia[origem] = 0;

        for (int i = 0; i < numVertices - 1; i++) {
            int u = encontrarVerticeMinimo(distancia, visitado);
            visitado[u] = true;

            for (int v = 0; v < numVertices; v++) {
                if (!visitado[v] && G[u][v] != 0 && distancia[u] != Integer.MAX_VALUE &&
                        distancia[u] + G[u][v] < distancia[v]) {
                    distancia[v] = distancia[u] + G[u][v];
                }
            }
        }

        exibirCaminhoMinimo(origem, destino, distancia);
    }

    private int encontrarVerticeMinimo(int[] distancia, boolean[] visitado) {
        int minimo = Integer.MAX_VALUE;
        int indiceMinimo = -1;

        for (int i = 0; i < numVertices; i++) {
            if (!visitado[i] && distancia[i] <= minimo) {
                minimo = distancia[i];
                indiceMinimo = i;
            }
        }

        return indiceMinimo;
    }

    private void exibirCaminhoMinimo(int origem, int destino, int[] distancia) {
        System.out.println("Distância mínima de " + origem + " para " + destino + ": " + distancia[destino]);
    }
  
    
    /*Verificar se um grafo é euleriano, semieuleriano ou não euleriano | Resolução E da 1 questao*/
    public void verificarEulerianidade() {
        int grauImpar = 0;
        int[] grauVertice = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                grauVertice[i] += G[i][j];
            }

            // Verifica se o grau do vértice é ímpar
            if (grauVertice[i] % 2 != 0) {
                grauImpar++;
            }
        }

        if (grauImpar == 0) {
            System.out.println("O grafo é euleriano.");
        } else if (grauImpar == 2) {
            System.out.println("O grafo é semieuleriano.");
        } else {
            System.out.println("O grafo não é euleriano nem semieuleriano.");
        }
    }
    
    /*Verificar se um grafo é  Hamiltoniano, semi-hamiltoniano ou não hamiltoniano | Resolução F da 1 questao*/
    public void verificarHamiltonianidade() {
        boolean[] visitado = new boolean[numVertices];
        int[] caminho = new int[numVertices];
        Arrays.fill(visitado, false);

        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(visitado, false);
            caminho[0] = i;
            if (dfsHamiltoniano(i, visitado, caminho, 1)) {
                System.out.println("O grafo é Hamiltoniano.");
                return;
            }
        }

        System.out.println("O grafo não é Hamiltoniano.");
    }

    private boolean dfsHamiltoniano(int vertice, boolean[] visitado, int[] caminho, int pos) {
        visitado[vertice] = true;

        if (pos == numVertices) {
            // Verifica se o último vértice no caminho possui uma aresta de volta ao primeiro
            if (G[vertice][caminho[0]] != 0) {
                return true;
            } else {
                return false;
            }
        }

        for (int i = 0; i < numVertices; i++) {
            if (G[vertice][i] != 0 && !visitado[i]) {
                caminho[pos] = i;
                if (dfsHamiltoniano(i, visitado, caminho, pos + 1)) {
                    return true;
                }
                visitado[i] = false; // Backtrack
            }
        }

        return false;
    }

    
    
    
    
    
    
    
}

