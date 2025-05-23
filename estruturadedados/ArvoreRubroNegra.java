package estruturadedados;

public class ArvoreRubroNegra {
    Node raiz = null;
    int quantidade = 0;
    
    public void inserir(int chave) {
        //int prof = 0;
        Node z = criarNo(chave, null);
        Node x = raiz;
        Node y = x;
        while (x != null) {
            y = x;//a última atribuição a "y" no laço determina o pai do novo nó
            if (z.chave < x.chave) {
                x = x.esq;
            } else {
                x = x.dir;
            }
            //prof++;
        }
        z.pai = y;
        if (y == null) {
            raiz = z;
        } else if (z.chave < y.chave) {
            y.esq = z;
        } else {
            y.dir = z;
        }
        z.cor = Cor.Rubro;
        //System.out.println("Profundidade: " + prof);
        consertarInsert(z);
        quantidade++;
    }

    private void consertarInsert(Node z) {
        while (z.pai != null && z.pai.cor.equals(Cor.Rubro)) {
            if (z.pai == z.pai.pai.esq) {
                Node y = z.pai.pai.dir;
                //Se o irmão do pai é rubro ou não existe, 
                // o pai e o irmão (se existir) podem ser mudados para negros 
                if (y == null || y.cor.equals(Cor.Rubro)) {
                    z.pai.cor = Cor.Negro;
                    if (y != null) {
                        y.cor = Cor.Negro;
                    }
                    z.pai.pai.cor = Cor.Rubro;
                    z = z.pai.pai;
                } else if (z == z.pai.dir) {
                    z = z.pai;                    
                    rotacionarAEsquerda(z);
                    z.pai.cor = Cor.Negro;
                    z.pai.pai.cor = Cor.Rubro;
                    rotacionarADireita(z.pai.pai);
                }
            } else {// faz do outro lado da árvore (de forma inversa).
                Node y = z.pai.pai.esq;
                //Se o irmão do pai é rubro ou não existe, 
                // o pai e o irmão (se existir) podem ser mudados para negros
                if (y == null || y.cor.equals(Cor.Rubro)) {
                    z.pai.cor = Cor.Negro;
                    if (y != null) {
                        y.cor = Cor.Negro;
                    }
                    z.pai.pai.cor = Cor.Rubro;
                    z = z.pai.pai;
                } else if (z == z.pai.esq) {
                    z = z.pai;
                    rotacionarADireita(z);
                    z.pai.cor = Cor.Negro;
                    z.pai.pai.cor = Cor.Rubro;
                    rotacionarAEsquerda(z.pai.pai);
                }
            }
        }
        raiz.cor = Cor.Negro;
    }

    private void rotacionarAEsquerda(Node x) {
        Node y = x.dir;
        //a subárvore esquerda de y ficará à direita de x
        x.dir = y.esq;
        //se a esquerda de y não é nula, terá x como pai
        if (y.esq != null) {
            y.esq.pai = x;
        }
        y.pai = x.pai;
        organizarFilhosDaRotacao(x, y);
        y.esq = x; // x ficará a esquerda de y.
        x.pai = y; // y é o novo pai de x.
    }
    
    private void rotacionarADireita(Node x) {
        Node y = x.esq;
        //a subárvore direita de y ficará à esquerda de x
        x.esq = y.dir;
        //se a direita de y não é nula, terá x como pai
        if (y.dir != null) {
            y.dir.pai = x;
        }
        y.pai = x.pai;
        organizarFilhosDaRotacao(x, y);
        y.dir = x; // x ficará a direita de y.
        x.pai = y; // y é o novo pai de x.
    }
    
    private void organizarFilhosDaRotacao(Node x, Node y) {
        //se o pai de x era null, então y deve ser a nova raiz
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.esq) {
            x.pai.esq = y;
        } else {
            x.pai.dir = y;
        }
    }

    /**
     * Cria um nó com a chave informada e adicionando a
     * referência ao pai.
     * @param chave
     * @param pai
     * @return 
     */
    public Node criarNo(int chave, Node pai) {
        Node no = new Node();
        no.chave = chave;
        no.pai = pai;
        return no;
    }
    
    public void remover(int chave) {
        Node z = buscar(chave);
        if (z == null)
            return;
        Node y = z;
        Node x = null;
        Cor corOriginal = y.cor;
        if (z.esq == null) {
            x = z.dir; //Guarda para consertar a árvore a partir desse nó.
            transplantar(z, z.dir);
        } else if (z.dir == null) {
            x = z.esq; //Guarda para consertar a árvore a partir desse nó.
            transplantar(z, z.esq);
        } else {
            y = buscarMinimoNaSubArvore(z.dir);
            corOriginal = y.cor;
            x = y.dir;//Guarda para consertar a árvore a partir desse nó.
            if (y.pai == z) {
                x.pai = y; //???
            } else {
                transplantar(y, y.dir);
                y.dir = z.dir;
                y.dir.pai = y;
            }
            transplantar(z, y);
            y.esq = z.esq;
            y.esq.pai = y;
            y.cor = z.cor;
        }
        if (x != null && corOriginal.equals(Cor.Negro)) {
            consertarDelete(x);
        }
        quantidade--;
    }

    /**
     * Corrige as cores e as referências de pais da árvore
     * devido a exclusão de um nó.
     * @param x
     */
    public void consertarDelete(Node x) {
        Node w = null;
        while (x != raiz && x.cor.equals(Cor.Negro)) {
            if (x == x.pai.esq) {
                w = x.pai.dir;
                if (w.cor.equals(Cor.Rubro)) {
                    w.cor = Cor.Negro;
                    x.pai.cor = Cor.Rubro;
                    rotacionarAEsquerda(x.pai);
                    w = x.pai.dir;
                }
                if (w.esq.cor.equals(Cor.Negro) && w.dir.cor.equals(Cor.Negro)) {
                    w.cor = Cor.Rubro;
                    x = x.pai;
                } else if (w.dir.cor.equals(Cor.Negro)) {
                    w.esq.cor = Cor.Negro;
                    w.cor = Cor.Rubro;
                    rotacionarADireita(w);
                    w = x.pai.dir;
                }
            } else { // faz do outro lado da árvore (de forma inversa).
                w = x.pai.esq;
                if (w.cor.equals(Cor.Rubro)) {
                    w.cor = Cor.Negro;
                    x.pai.cor = Cor.Rubro;
                    rotacionarADireita(x.pai);
                    w = x.pai.esq;
                }
                if (w.dir.cor.equals(Cor.Negro) && w.esq.cor.equals(Cor.Negro)) {
                    w.cor = Cor.Rubro;
                    x = x.pai;
                } else if (w.esq.cor.equals(Cor.Negro)) {
                    w.dir.cor = Cor.Negro;
                    w.cor = Cor.Rubro;
                    rotacionarAEsquerda(w);
                    w = x.pai.esq;
                }
            }
        }
        x.cor = Cor.Negro;
    }

    /**
     * Ajusta a referência/ligação do nó pai e dos nós filhos de "u". 
     * Como u será extraído/mudado da subárvore, precisa ajustar
     * as referências dos nós filhos de "u" para o novo pai. 
     * @param u
     * @param v
     */
    public void transplantar(Node u, Node v) {
        if (u.pai == null) {
            raiz = v;
        } else if (u == u.pai.esq) {
            u.pai.esq = v;
        } else {
            u.pai.dir = v;
        }
        if (v != null) {
            v.pai = u.pai;
        }
    }

    /**
     * Procura pelo elemento de menor chave na subárvore em que "no" é a raiz
     * @param no
     * @return
     */
    public Node buscarMinimoNaSubArvore(Node no) {
        if (no == null)
            return null;
        Node min = no;
        while (min.esq != null) {
            min = min.esq;
        }
        return min;
    }

    public Node buscarMaximoNaSubArvore(Node no) {
        if (no == null)
            return null;
        Node max = no;
        while (max.dir != null) {
            max = max.dir;
        }
        return max;
    }

    public Node buscarSucessor(int chave) {
        Node x = raiz;
        Node suc = null;
        while (suc == null && x != null) {
            Node y = buscarMaximoNaSubArvore(x.esq);
            if (y != null && y.chave > chave) {
                //Caminha na subárvore à esquerda.
                x = x.esq;
            } else if (x.chave > chave) {
                suc = x;
            } else {
                //Caminha na subárvore à direita.
                x = x.dir;
            }
        }
        return suc;
    }

    public Node buscar(int chave) {
        Node x = raiz;
        while (x != null) {
            if (chave == x.chave) {
                return x;
            } else if (chave < x.chave) {
                x = x.esq;
            } else {
                x = x.dir;
            }
        }
        return x;
    }

    public void imprimirPreOrdem(Node no) {
        if(no != null) {
            System.out.print("(" + no.chave + no.cor.name().toLowerCase().charAt(0));
            imprimirPreOrdem(no.esq);
            imprimirPreOrdem(no.dir);
            System.out.print(")");
        }
    }

    public void imprimirInOrdem(Node no) {
        if(no != null) {
            imprimirInOrdem(no.esq);
            System.out.print(no.chave + " ");
            imprimirInOrdem(no.dir);
        }
    }
}
