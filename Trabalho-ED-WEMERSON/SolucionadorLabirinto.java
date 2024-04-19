import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class SolucionadorLabirinto {
    private char[][] labirinto;
    private boolean[][] visitado;
    private int linhas;
    private int colunas;
    private int[][] direcoes = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }; // Baixo, Cima, Direita, Esquerda
    private static final int DELAY_MS = 800; // Atraso em milissegundos (200ms)

    public SolucionadorLabirinto(char[][] labirinto) {
        this.labirinto = labirinto;
        this.linhas = labirinto.length;
        this.colunas = labirinto[0].length;
        this.visitado = new boolean[linhas][colunas];
    }
      //função para encontrar o caminho (ou não)
    public boolean encontrarCaminho(int linhaInicial, int colunaInicial) {
        Stack<Posicao> pilha = new Stack<>();
        pilha.push(new Posicao(linhaInicial, colunaInicial));

        while (!pilha.isEmpty()) {
            if (explorarCaminho(pilha)) {
                imprimirCaminho();
                System.out.println("Caminho encontrado!");
                return true;
            }
        }

        System.out.println("Nenhum caminho encontrado!");
        return false;
    }
      //função para explorar caminhos
    private boolean explorarCaminho(Stack<Posicao> pilha) {
        Posicao posicaoAtual = pilha.peek();
        int linha = posicaoAtual.getLinha();
        int coluna = posicaoAtual.getColuna();

        if (movimentoValido(linha, coluna)) {
            if (labirinto[linha][coluna] == 'D') { // Destino encontrado
                labirinto[linha][coluna] = 'F'; // Altera de 'D' para 'F' indicando "fim", mostrando que encontrou o destino
                return true;
            }

            visitado[linha][coluna] = true;

            boolean movimentoRealizado = realizarMovimentos(pilha, linha, coluna);

            if (!movimentoRealizado) {
                pilha.pop(); // Se não houver movimentos possíveis, remove a posição atual da pilha
            }

            return false;
        } else {
            pilha.pop(); // Se a posição atual não for válida, remove da pilha
            return false;
        }
    }
      //função que realiza e imprime o caminho com o delay
    private boolean realizarMovimentos(Stack<Posicao> pilha, int linha, int coluna) {
        boolean movimentoRealizado = false;
        for (int[] direcao : direcoes) {
            int novaLinha = linha + direcao[0];
            int novaColuna = coluna + direcao[1];
            if (movimentoPossivel(novaLinha, novaColuna)) {
                pilha.push(new Posicao(novaLinha, novaColuna));
                movimentoRealizado = true;
            }
        }

        imprimirCaminho();

        try {
            Thread.sleep(DELAY_MS); // Delay de movimento para ser possível ver o "robô" andando
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return movimentoRealizado;
    }

    private boolean movimentoValido(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas && labirinto[linha][coluna] != '1'
                && !visitado[linha][coluna];
    }

    private boolean movimentoPossivel(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas && labirinto[linha][coluna] != '1';
    }

    private void imprimirCaminho() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (visitado[i][j]) {
                    System.out.print("X "); // Marcar posições visitadas com 'X'
                } else {
                    System.out.print(labirinto[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static char[][] lerLabirintoDoArquivo(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            int linhas = 0;
            int colunas = 0;

            // Contar linhas e colunas
            while ((linha = br.readLine()) != null) {
                linhas++;
                if (colunas == 0) {
                    colunas = linha.length();
                }
            }

            // Reiniciar o leitor
            try (BufferedReader br2 = new BufferedReader(new FileReader(nomeArquivo))) {
                char[][] labirinto = new char[linhas][colunas];
                int linhaAtual = 0;

                // Preenche a matriz do labirinto
                while ((linha = br2.readLine()) != null) {
                    if (linha.trim().isEmpty()) {
                        continue; // Ignora linhas em branco
                    }
                    for (int i = 0; i < colunas; i++) {
                        labirinto[linhaAtual][i] = linha.charAt(i);
                    }
                    linhaAtual++;
                }

                return labirinto;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}