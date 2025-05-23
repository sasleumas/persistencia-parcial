package estruturadedados;

public class Node {
    
    Node pai = null;
    Node esq = null;
    Node dir = null;
    int chave;
    Cor cor;

    @Override
    public String toString() {
        return String.format("%d", chave);
    }
}

enum Cor {Rubro, Negro}