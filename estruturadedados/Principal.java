package estruturadedados;

public class Principal {
    public static void main(String[] args) {
        int[] chaves = {11, 2, 14, 1, 7, 15, 5, 8, 4, 16, 3, 13, 2};
        //int[] chaves = {9, 7, 15, 2, 8, 12, 17};
        ArvoreRubroNegra a = new ArvoreRubroNegra();
        System.out.println("Ver resultado de cada inserção em pré-ordem e em ordem.");
        testarInsercao(a, chaves);

        //System.out.println("Raízes de cada versão.");
        //testarRaizes(a);

        //System.out.println("Impressão por versões em ordem.");
        //testarImpressaoDaVersao(a);
        
        //int [] novasChaves = {9, -5, 11, 2, 14, 1, 12, 7, 15, 5, 0, 8, 4, 16, 90, 3, 13, 2, 1000};
        //System.out.println("Sucessores de cada chave em cada versão");
        //testarSucessorDaVersao(a, novasChaves);

        //System.out.println("Impressão de modificações.");
        //a.imprimirEmOrdemComVersoes(a.raiz);

        System.out.println("Remoção na ordem inversa da inserção");
        //int[] chs = chaves;
        int[] chs = {14};
        testarRemocao(a, chs);

        //System.out.println("Impressão por versões em ordem.");
        //testarImpressaoDaVersao(a);
    }

    public static void testarInsercao(ArvoreRubroNegra a, int[] chaves) {        
        for(int i = 0; i < chaves.length; i++) {
            a.inserir(chaves[i]);
            String s = a.imprimirPreOrdem(a.raiz);
            //if (!s.equals(arvoreInsercoesPreOrdem[i])) {
            //    throw new RuntimeException("Erro no resultado da inserção da chave " + chaves[i]);
            //}
            System.out.println(s);
            
            /*System.out.print("Em ordem: ");
            s = a.imprimirEmOrdem(a.raiz);
            System.out.println(s);*/

            //System.out.println("Total de nós: " + a.quantidade);
        }
    }

    static void testarRaizes(ArvoreRubroNegra a) {
        if (a.versaoAtual != a.raizesDasVersoes.size()) {
            throw new RuntimeException("Versão da árvore não é igual ao número de raízes armazenadas.");
        }
        for(int i = 1; i <= a.versaoAtual; i++) {
            Node no = a.raizesDasVersoes.get(i);
            System.out.print("Versão " + i + ": ");
            System.out.println(no.chave);
            
        }
    }

    static void testarImpressaoDaVersao(ArvoreRubroNegra a) {
        for(int i = 0; i < a.versaoAtual + 2; i++) {
            String s = a.imprimirEmOrdem(i);
            System.out.print("Versão " + i + ": ");
            System.out.println(s);
        }
    }

    public void testarImprimirEmOrdem(ArvoreRubroNegra a, int[] chaves) {
        for(int i : chaves) {
            a.inserir(i);
            String s = a.imprimirEmOrdem(a.raiz);
            System.out.println(s);
            //System.out.println("Imprimir em ordem:");
            //a.imprimirEmOrdemComVersoes(a.raiz);
        }
        System.out.println("Imprimindo Versões");
        for (int i = 1; i <= chaves.length + 2; i++) {
            a.imprimirEmOrdem(i);
            System.out.println();
        }
    }

    static void testarSucessorDaVersao(ArvoreRubroNegra a, int[] chaves) {
        for (int i = 0; i < chaves.length + 1; i++) {
            System.out.print("Versão " + i + ": ");
            for(int j : chaves) {
                String s = a.buscarSucessor(j, i);
                System.out.print(j + " -> " + s + ", ");
            }    
            System.out.println();
        }
    }

    static void testarRemocao(ArvoreRubroNegra a, int[] chaves) {
        for(int i = chaves.length - 1; i >=0; i--) {
            if (chaves[i] == 1) {
               System.out.println(i);
            }
            a.remover(chaves[i]);
            String s = a.imprimirPreOrdem(a.raiz);
            /*if (!s.equals(arvoreInsercoesPreOrdem[i])) {
                throw new RuntimeException("Erro no resultado da inserção da chave " + chaves[i]);
            }*/
            
            System.out.println(s);
            //System.out.println(" - Versão " + a.versaoAtual + " (" + (chaves[i]) + ")");
            
            /*System.out.print("Em ordem: ");
            s = a.imprimirEmOrdem(a.raiz);
            System.out.println(s);*/

            //System.out.println("Total de nós: " + a.quantidade);
        }
    }

    static String[] arvoreInsercoesPreOrdem =
    {
        "(11n)",
        "(11n(2r))",
        "(11n(2r)(14r))",
        "(11n(2n(1r))(14n))",
        "(11n(2n(1r)(7r))(14n))",
        "(11n(2n(1r)(7r))(14n(15r)))",
        "(11n(2r(1n)(7n(5r)))(14n(15r)))",
        "(11n(2r(1n)(7n(5r)(8r)))(14n(15r)))",
        "(7n(2r(1n)(5n(4r)))(11r(8n)(14n(15r))))",
        "(7n(2r(1n)(5n(4r)))(11r(8n)(15n(14r)(16r))))",
        "(7n(2r(1n)(4n(3r)(5r)))(11r(8n)(15n(14r)(16r))))",
        "(7n(2n(1n)(4n(3r)(5r)))(11n(8n)(15r(14n(13r))(16n))))",
        "(7n(2n(1n)(4r(3n(2r))(5n)))(11n(8n)(15r(14n(13r))(16n))))"
    }; 

    static String[] arvoreInsercoesEmOrdem =
    {"11,0,N",
        "2,1,R 11,0,N",
        "2,1,R 11,0,N 14,1,R",
        "1,2,R 2,1,N 11,0,N 14,1,N",
        "1,2,R 2,1,N 7,2,R 11,0,N 14,1,N",
        "1,2,R 2,1,N 7,2,R 11,0,N 14,1,N 15,2,R",
        "1,2,N 2,1,R 5,3,R 7,2,N 11,0,N 14,1,N 15,2,R",
        "1,2,N 2,1,R 5,3,R 7,2,N 8,3,R 11,0,N 14,1,N 15,2,R",
        "1,2,N 2,1,R 4,3,R 5,2,N 7,0,N 8,2,N 11,1,R 14,1,N 15,2,R",
        "1,2,N 2,1,N 4,3,R 5,2,N 7,0,N 8,2,N 11,1,N 14,2,R 15,3,N 16,4,R",
        "1,2,N 2,1,N 3,4,R 4,3,N 5,2,R 7,0,N 8,2,N 11,1,N 14,2,R 15,3,N 16,4,R",
        "1,2,N 2,1,N 3,4,R 4,3,N 5,2,R 7,0,N 8,3,N 11,2,R 13,1,N 14,2,R 15,3,N 16,4,R",     
        "1,2,N 2,2,R 2,4,R 3,3,N 4,1,N 5,2,R 7,0,N 8,3,N 11,2,R 13,1,N 14,2,R 15,3,N 16,4,R",
        "1,2,N 2,2,R 2,4,R 3,3,N 4,1,N 5,2,R 7,0,N 8,3,N 11,2,R 13,1,N 14,2,R 15,3,N 16,4,R"
    };
}
