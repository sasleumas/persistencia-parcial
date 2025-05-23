package estruturadedados;

public class Principal {
    public static void main(String[] args) {
        int[] chaves = {11, 2, 14, 1, 7, 15, 5, 8, 4, 16, 3};
        ArvoreRubroNegra a = new ArvoreRubroNegra();
        Node no = a.buscarSucessor(10);
        System.out.println("Sucessor de 10: " + no);
        for(int i : chaves) {
            a.inserir(i);
            a.imprimirPreOrdem(a.raiz);
            System.out.println();
        }
        System.out.println("\n" + a.quantidade + " nós.");
        for(int i = -2; i < 20; i++) {
            no = a.buscarSucessor(i);
            System.out.println("Sucessor de " + i + ": " + no);
        }
        System.out.println("Removendo o nó 11.");
        a.remover(11);
        a.imprimirPreOrdem(a.raiz);
    }
}
