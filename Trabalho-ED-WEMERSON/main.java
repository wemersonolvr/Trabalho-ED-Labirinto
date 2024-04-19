import java.util.Stack;

public class main {
    public static void main(String[] args) {
        char[][] labirinto = SolucionadorLabirinto.lerLabirintoDoArquivo("matriz.txt");

        if (labirinto != null) {
            int linhaInicial = -1;
            int colunaInicial = -1;

            // Encontrar a posição inicial 'S' no labirinto
            for (int i = 0; i < labirinto.length; i++) {
                for (int j = 0; j < labirinto[0].length; j++) {
                    if (labirinto[i][j] == 'S') {
                        linhaInicial = i;
                        colunaInicial = j;
                        break;
                    }
                }
            }

            if (linhaInicial != -1 && colunaInicial != -1) {
                SolucionadorLabirinto solucionador = new SolucionadorLabirinto(labirinto);
                solucionador.encontrarCaminho(linhaInicial, colunaInicial); // Posição inicial
            } else {
                System.out.println("Posição inicial 'S' não encontrada!");
            }
        } else {
            System.out.println("Erro ao ler o labirinto do arquivo!");
        }
    }
}
