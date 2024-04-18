import java.util.*;

public class SolucionadorLabirinto {
    private char[][] labirinto;
    private boolean[][] visitado;
    private int linhas;
    private int colunas;
    private int[][] direcoes = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Baixo, Cima, Direita, Esquerda
    private static final int DELAY_MS = 800; // Atraso em milissegundos (200ms)

    public SolucionadorLabirinto(char[][] labirinto) {
        this.labirinto = labirinto;
        this.linhas = labirinto.length;
        this.colunas = labirinto[0].length;
        this.visitado = new boolean[linhas][colunas];
    }

  public boolean encontrarCaminho(int linhaInicial, int colunaInicial) {
    Stack<Posicao> pilha = new Stack<>();
    pilha.push(new Posicao(linhaInicial, colunaInicial));

    while (!pilha.isEmpty()) {
        Posicao posicaoAtual = pilha.peek();
        int linha = posicaoAtual.getLinha();
        int coluna = posicaoAtual.getColuna();

        if (movimentoValido(linha, coluna)) {
            if (labirinto[linha][coluna] == 'D') { // Destino encontrado
                labirinto[linha][coluna] = 'F'; // Altera 'D' para 'F' (indicando fim)
                imprimirCaminho();
                System.out.println("Caminho encontrado!");
                return true;

            }

            visitado[linha][coluna] = true;

            boolean movimentoRealizado = false;
            for (int[] direcao : direcoes) {
                int novaLinha = linha + direcao[0];
                int novaColuna = coluna + direcao[1];
                if (movimentoPossivel(novaLinha, novaColuna)) {
                    pilha.push(new Posicao(novaLinha, novaColuna));
                    movimentoRealizado = true;
                }
            }

            if (!movimentoRealizado) {
                pilha.pop(); // Se não houver movimentos possíveis, remova a posição atual da pilha
            }

            imprimirCaminho();

            try {
                Thread.sleep(DELAY_MS); // Atraso após cada movimento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            pilha.pop(); // Se a posição atual não for válida, remova da pilha
        }
    }

    System.out.println("Nenhum caminho encontrado!");
    return false;
}


    private boolean movimentoValido(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas && labirinto[linha][coluna] != '1' && !visitado[linha][coluna];
    }

    private boolean movimentoPossivel(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas && labirinto[linha][coluna] != '1';
    }

    private void imprimirCaminho() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (visitado[i][j]) {
                    System.out.print("X "); // Marcar posições visitadas como 'X'
                } else {
                    System.out.print(labirinto[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
 }