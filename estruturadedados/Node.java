package estruturadedados;

public class Node {
    
    Node pai = null;
    Node esq = null;
    Node dir = null;
    Node versaoAnterior = null;
    int chave;
    Cor cor;
    int profundidade = 0;
    //mods de tamanho 6, pois o nó tem 3 ponteiros (mods <= 2p).
    Mod mods[] = new Mod[6];

    public Node() {}

    public Node(Node no) {
        this.pai = no.pai;
        this.esq = no.esq;
        this.dir = no.dir;
        this.chave = no.chave;
        this.cor = no.cor;
    }

    /**
     * Adiciona uma nova modificação da versão no nó.
     * @param versao
     * @return true, se houve alteração e se foi possível adicionar o mod.
     *  fase, caso houve alteração e não foi possível adicionar o mod.
     */
    boolean adicionarMod(int versao) {
        for(int i = 0; i < mods.length; i++) {
            if (mods[i] != null && mods[i].versao == versao) {
                mods[i] = new Mod(versao, this);
                return true;
            } else if(mods[i] == null) {
                boolean configura = true;
                if (i > 0) {
                    Node anterior = mods[i-1].noNaVersao;
                    if (ehIgualModAnterior(anterior, this)) {
                        configura = false;
                    }
                }
                if (configura) {
                    Mod m = new Mod(versao, this);
                    mods[i] = m;
                }
                return true;
            }
        }
        //Se chegou aqui, não há mais espaço para inserir modificação.
        //Verifica se houve modificação que justifique necessidade de informar estouro. 
        Node ultima = mods[mods.length-1].noNaVersao;
        if (ehIgualModAnterior(ultima, this)) {
            return true;
        }
        return false;
    }

    private boolean ehIgualModAnterior(Node ant, Node atual) {
        boolean compara = ehMesmaChave(ant.esq, atual.esq) &&
            ehMesmaChave(ant.dir, atual.dir) &&
            ehMesmaChave(ant.pai, atual.pai) &&
            ant.cor.ordinal() == atual.cor.ordinal();
        return compara;
    }

    private boolean ehMesmaChave(Node n1, Node n2) {
        return n1 != null && n2 != null && n1.chave == n2.chave;
    }

    @Override
    public String toString() {
        return String.format("%d", chave);
    }

    @Override
    public boolean equals(Object obj) {
        Node other = ((Node)obj);
        return obj != null 
        && this.chave == other.chave;
    }
}

enum Cor {Rubro, Negro}

class Mod {
    int versao;
    Node noNaVersao;

    public Mod(int ve, Node no) {
        versao = ve;
        noNaVersao = new Node(no);
    }
}