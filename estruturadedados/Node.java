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
        pai = no.pai;
        esq = no.esq;
        dir = no.dir;
        chave = no.chave;
        cor = no.cor;
    }

    boolean adicionarMod(int versao) {
        for(int i = 0; i < mods.length; i++) {
            if (mods[i] != null && mods[i].versao == versao) {
                mods[i] = new Mod(versao, this);
                return true;
            } else if(mods[i] == null) {
                boolean configura = true;
                if (i > 0) {
                    Node anterior = mods[i-1].noNaVersao;
                    if (anterior.esq == this.esq &&
                        anterior.dir == this.dir &&
                        anterior.pai == this.pai &&
                        anterior.cor.ordinal() == this.cor.ordinal()) {
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
        return false;
    }

    @Override
    public String toString() {
        return String.format("%d", chave);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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