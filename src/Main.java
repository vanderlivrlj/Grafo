import java.util.*;

// Classe que representa uma aresta no grafo
class Aresta implements Comparable<Aresta> {
    int origem;
    int destino;
    int valor;

    public Aresta(int origem, int destino, int valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    @Override
    public int compareTo(Aresta outra) {
        return this.valor - outra.valor;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Entrada do número de roteadores
        System.out.print("Digite a quantidade de roteadores: ");
        int numRoteadores = scanner.nextInt();

        // Entrada do número de cabos de internet
        System.out.print("Digite a quantidade de cabos de internet: ");
        int numCabos = scanner.nextInt();

        List<Aresta> cabos = new ArrayList<>();

        // Entrada das conexões dos cabos de internet
        System.out.println("Digite as conexões dos cabos de internet no formato 'origem destino valor':");
        for (int i = 0; i < numCabos; i++) {
            int origem = scanner.nextInt() - 1; // Ajuste no índice
            int destino = scanner.nextInt() - 1; // Ajuste no índice
            int valor = scanner.nextInt();
            cabos.add(new Aresta(origem, destino, valor));
        }
        // Cálculo do custo total com os cabos de internet após as modificações
        int custoTotal = calcularCustoTotal(numRoteadores, cabos);
        System.out.println("O custo total com cabos após as modificações é: " + custoTotal);
    }
    // Função para calcular o custo total com os cabos de internet após as modificações
    private static int calcularCustoTotal(int numRoteadores, List<Aresta> cabos) {
        Collections.sort(cabos); // Ordena as arestas pelo valor

        int[] pai = new int[numRoteadores];
        for (int i = 0; i < numRoteadores; i++) {
            pai[i] = i; // Inicializa cada roteador como seu próprio pai
        }

        int custoTotal = 0;

        for (Aresta aresta : cabos) {
            int origem = aresta.origem;
            int destino = aresta.destino;
            int valor = aresta.valor;

            int paiOrigem = encontrarPai(origem, pai);
            int paiDestino = encontrarPai(destino, pai);

            if (paiOrigem != paiDestino) {
                // Aresta não forma ciclo, pode ser adicionada à árvore
                custoTotal += valor;
                pai[paiOrigem] = paiDestino; // Faz a união dos conjuntos
            }
        }

        return custoTotal;
    }
    // Função para encontrar o pai de um vértice (utilizada para verificar ciclos)
    private static int encontrarPai(int vertice, int[] pai) {
        if (pai[vertice] != vertice) {
            // Realiza compressão de caminho
            pai[vertice] = encontrarPai(pai[vertice], pai);
        }
        return pai[vertice];
    }
}
