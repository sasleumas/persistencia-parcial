package estruturadedados;

import java.util.HashMap;

public class ArvoreRubroNegra {
    Node raiz = null;
    int quantidade = 0;
    int versaoAtual = 0;
    //Mantém uma lista com os nós modificados na última inserção ou remoção
    // para atualização dos mods da versão de cada nó. 
    //private HashSet<Node> nosModificados = new HashSet<>();

    HashMap<Integer, Node> raizesDasVersoes = new HashMap<>();

    public void inserir(int chave) {
        versaoAtual++;
 
        //nosModificados.clear();
        Node z = criarNo(chave, null);
        Node x = raiz;
        Node y = x;
        //Procura o local (o pai) onde o novo nó será inserido.
        while (x != null) {
            y = x;//a última atribuição a "y" no laço determina o pai do novo nó
            if (z.chave < x.chave) {
                x = x.esq;
            } else {
                x = x.dir;
            }
        }
        z.pai = y;
        if (y == null) {
            raiz = z;
        } else if (z.chave < y.chave) {
            y.esq = z;
            configurarVersao(y);
            //nosModificados.add(y);
        } else {
            y.dir = z;
            configurarVersao(y);
            //nosModificados.add(y);
        }
        z.cor = Cor.Rubro;
        configurarVersao(z);
        //nosModificados.add(z);
        consertarInsert(z);
        quantidade++;
        raizesDasVersoes.put(versaoAtual, raiz);
    }

    /**
     * Faz as rotações e mudanças de cor necessárias para 
     * preservar as características da árvore rubro negra.
     * @param z
     */
    private void consertarInsert(Node z) {
        while (z.pai != null && z.pai.cor.equals(Cor.Rubro)) {
            if (z.pai == z.pai.pai.esq) {
                Node y = z.pai.pai.dir;
                if (y != null && y.cor.equals(Cor.Rubro)) {
                    z.pai.cor = Cor.Negro;
                    configurarVersao(z.pai);
                    //nosModificados.add(z.pai);
                    y.cor = Cor.Negro;
                    configurarVersao(y);
                    //nosModificados.add(y);
                    z.pai.pai.cor = Cor.Rubro;
                    configurarVersao(z.pai.pai);
                    //nosModificados.add(z.pai.pai);
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.dir) {
                        z = z.pai;                    
                        rotacionarAEsquerda(z);
                    }
                    z.pai.cor = Cor.Negro;
                    z.pai.pai.cor = Cor.Rubro;
                    configurarVersao(z.pai);
                    configurarVersao(z.pai.pai);
                    //nosModificados.add(z.pai);
                    //nosModificados.add(z.pai.pai);                        
                    rotacionarADireita(z.pai.pai);
                }
            } else {// faz do outro lado da árvore (de forma inversa).
                Node y = z.pai.pai.esq;
                //Se o irmão do pai é rubro ou não existe, 
                // o pai e o irmão (se existir) podem ser mudados para negros
                if (y != null && y.cor.equals(Cor.Rubro)) {
                    z.pai.cor = Cor.Negro;
                    configurarVersao(z.pai);
                    //nosModificados.add(z.pai);
                    y.cor = Cor.Negro;
                    configurarVersao(y);
                    //nosModificados.add(y);
                    z.pai.pai.cor = Cor.Rubro;
                    configurarVersao(z.pai.pai);
                    //nosModificados.add(z.pai.pai);
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esq) {
                        z = z.pai;
                        rotacionarADireita(z);
                    }
                    z.pai.cor = Cor.Negro;
                    z.pai.pai.cor = Cor.Rubro;
                    configurarVersao(z.pai);
                    configurarVersao(z.pai.pai);
                    //nosModificados.add(z.pai);
                    //nosModificados.add(z.pai.pai);
                    rotacionarAEsquerda(z.pai.pai);
                }
            }
        }
        raiz.cor = Cor.Negro;
        configurarVersao(raiz);
    }

    private void rotacionarAEsquerda(Node x) {
        Node y = x.dir;
        //a subárvore esquerda de y ficará à direita de x
        x.dir = y.esq;
        //se a esquerda de y não é nula, terá x como pai
        if (y.esq != null) {
            y.esq.pai = x;
            configurarVersao(y.esq);
            //nosModificados.add(y.esq);
        }
        y.pai = x.pai;
        organizarFilhosDaRotacao(x, y);
        y.esq = x; // x ficará a esquerda de y.
        x.pai = y; // y é o novo pai de x.
        configurarVersao(x);
        configurarVersao(y);
        //nosModificados.add(x);
        //nosModificados.add(y);
    }
    
    private void rotacionarADireita(Node x) {
        Node y = x.esq;
        //a subárvore direita de y ficará à esquerda de x
        x.esq = y.dir;
        //se a direita de y não é nula, terá x como pai
        if (y.dir != null) {
            y.dir.pai = x;
            configurarVersao(y.dir);
            //nosModificados.add(y.dir);
        }
        y.pai = x.pai;
        organizarFilhosDaRotacao(x, y);
        y.dir = x; // x ficará a direita de y.
        x.pai = y; // y é o novo pai de x.
        configurarVersao(x);
        configurarVersao(y);
        //nosModificados.add(x);
        //nosModificados.add(y);
    }
    
    private void organizarFilhosDaRotacao(Node x, Node y) {
        //se o pai de x era null, então y deve ser a nova raiz
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.esq) {
            x.pai.esq = y;
            configurarVersao(x.pai);
            //nosModificados.add(x.pai);
        } else {
            x.pai.dir = y;
            configurarVersao(x.pai);
            //nosModificados.add(x.pai);
        }
    }

    /**
     * Cria um nó com a chave informada e adicionando a
     * referência ao pai.
     * @param chave
     * @param pai
     * @return 
     */
    private Node criarNo(int chave, Node pai) {
        Node no = new Node();
        no.chave = chave;
        no.pai = pai;
        return no;
    }
    
    public void remover(int chave) {
        //nosModificados.clear();        
        Node z = buscar(chave);
        versaoAtual++;
        if (z == null) {
            raizesDasVersoes.put(versaoAtual, raiz);
            return;
        }
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
            //Escolhe o menor da direita para ser a nova raiz da subárvore.
            y = buscarMinimoNaSubArvore(z.dir);
            corOriginal = y.cor;
            x = y.dir;//Guarda para consertar a árvore a partir desse nó.
            if (y.pai == z && x != null) {
                x.pai = y;
                configurarVersao(x);
            } else {
                transplantar(y, y.dir);
                y.dir = z.dir;
                configurarVersao(y);
                if (y.dir != null) {
                    y.dir.pai = y;
                    configurarVersao(y.dir);
                }
            }
            transplantar(z, y);

            y.esq = z.esq;
            y.cor = z.cor;
            configurarVersao(y);
            if (y.esq != null) {
                y.esq.pai = y;
                configurarVersao(y.esq);
            }
        }
        if (corOriginal.equals(Cor.Negro)) {

            if (x != null)
                consertarDelete(x);
        }
        quantidade--;
        raizesDasVersoes.put(versaoAtual, raiz);
    }

    /**
     * Corrige as cores e as referências de pais da árvore
     * devido a exclusão de um nó.
     * @param x
     */
    private void consertarDelete(Node x) {
        Node w = null;
        while (x != raiz && x.cor.equals(Cor.Negro)) {
            if (x == x.pai.esq) {
                w = x.pai.dir;
                if (w.cor.equals(Cor.Rubro)) {
                    w.cor = Cor.Negro;
                    configurarVersao(w);
                    x.pai.cor = Cor.Rubro;
                    configurarVersao(x.pai);
                    rotacionarAEsquerda(x.pai);
                    w = x.pai.dir;
                }
                if ((w.esq == null ||  w.esq.cor.equals(Cor.Negro)) && (w.dir == null || w.dir.cor.equals(Cor.Negro))) {
                    w.cor = Cor.Rubro;
                    configurarVersao(w);
                    x = x.pai;
                } else {
                    if (w.dir.cor.equals(Cor.Negro)) {
                        w.esq.cor = Cor.Negro;
                        configurarVersao(w.esq);
                        w.cor = Cor.Rubro;
                        configurarVersao(w);
                        rotacionarADireita(w);
                        w = x.pai.dir;
                        configurarVersao(w);
                    }
                    w.cor = x.pai.cor;
                    configurarVersao(w);
                    x.pai.cor = Cor.Negro;
                    configurarVersao(x.pai);
                    w.dir.cor = Cor.Negro;
                    configurarVersao(w.dir);
                    rotacionarAEsquerda(x.pai);
                    x = raiz;
                }
            } else { // faz do outro lado da árvore (de forma inversa).
                w = x.pai.esq;
                if (w.cor.equals(Cor.Rubro)) {
                    w.cor = Cor.Negro;
                    configurarVersao(w);
                    x.pai.cor = Cor.Rubro;
                    configurarVersao(x.pai);
                    rotacionarADireita(x.pai);
                    w = x.pai.esq;
                }
                if ((w.dir == null || w.dir.cor.equals(Cor.Negro)) && (w.esq == null || w.esq.cor.equals(Cor.Negro))) {
                    w.cor = Cor.Rubro;
                    configurarVersao(w);
                    x = x.pai;
                } else {
                    if (w.esq.cor.equals(Cor.Negro)) {
                        w.dir.cor = Cor.Negro;
                        configurarVersao(w.dir);
                        w.cor = Cor.Rubro;
                        configurarVersao(w);
                        rotacionarAEsquerda(w);
                        w = x.pai.esq;
                    }
                    w.cor = x.pai.cor;
                    configurarVersao(w);
                    x.pai.cor = Cor.Negro;
                    configurarVersao(x.pai);
                    w.esq.cor = Cor.Negro;
                    configurarVersao(w.esq);
                    rotacionarADireita(x.pai);
                    x = raiz;
                }
            }
        }
        if (x != null && x == raiz) {
            x.cor = Cor.Negro;
            configurarVersao(x);
        }
    }

    /**
     * Ajusta a referência/ligação do nó pai e dos nós filhos de "u". 
     * Como u será extraído/mudado da subárvore, precisa ajustar
     * as referências dos nós filhos de "u" para o novo pai. 
     * @param u
     * @param v
     */
    private void transplantar(Node u, Node v) {
        if (u.pai == null) {
            raiz = v;
        } else if (u == u.pai.esq) {
            u.pai.esq = v;
            configurarVersao(u.pai);
        } else {
            u.pai.dir = v;
            configurarVersao(u.pai);
        }
        if (v != null) {
            v.pai = u.pai;
            configurarVersao(v);
        }
    }

    private void configurarVersao(Node n) {
        Node novoNo = n;
        boolean adicionou = n.adicionarMod(versaoAtual); 
        //Chegou no limite dos mods
        //Cria nova estrutura para o nó e atualiza as 
        // referências a ele.
        if (!adicionou) {
            novoNo = new Node(n);
            novoNo.versaoAnterior = n;
            if (n == raiz) {
                raiz = novoNo;
            }
            if(n.dir != null) {
                novoNo.dir.pai = novoNo;
            }
            if (n.esq != null) {
                novoNo.esq.pai = novoNo;
            }
            if (n.pai != null) {
                if (n.pai.esq == n) {
                    novoNo.pai.esq = novoNo;
                } else {
                    novoNo.pai.dir = novoNo;
                }
            }
            novoNo.adicionarMod(versaoAtual);
            n = novoNo;
        }
    }
    
    /**
     * Busca o sucessor da "chave" na versão indicada.
     * @param chave
     * @param versao
     * @return O sucessor da chave solicitada.
     */
    public String buscarSucessor(int chave, int versao) {
        if (versao > versaoAtual) {
            versao = versaoAtual;
        }
        Node x = raizesDasVersoes.get(versao);
        x = pegarVersao(versao, x);
        Node suc = null;
        while (suc == null && x != null) {
            Node esq = pegarVersao(versao, x.esq);
            Node y = buscarMaximoNaSubArvore(versao, esq);
            if (y != null && y.chave > chave) {
                //Caminha na subárvore à esquerda.
                x = esq;
            } else if (x.chave > chave) {
                suc = x;
            } else {
                //Caminha na subárvore à direita.
                x = pegarVersao(versao, x.dir);
            }
        }
        String retorno = "";
        if (suc == null) {
            retorno = "INFINITO";
        } else {
            retorno = suc.chave + ""; 
        }
        return retorno;
    }

    public Node buscarMaximoNaSubArvore(int versao, Node no) {
        if (no == null)
            return null;
        Node max = no;
            /*Node max = pegarVersao(versao, no);
        if (max == null)
            return null;*/
        Node max_dir = pegarVersao(versao, max.dir);
        while (max_dir != null) {
            max = max_dir;
            if (max != null) {
                max_dir = pegarVersao(versao, max.dir);
            }
        }
        return max;
    }
    
    /**
     * Procura pelo nó que tem a chave indicada.
     * @param chave
     * @return
     */
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

    public String imprimirEmOrdem(int versao) {
        Node raizDaVersao = null;
        if (versao < 1) {
            return "";
        } else if (versao > versaoAtual) {
            raizDaVersao = raiz;
        } else {
            raizDaVersao = raizesDasVersoes.get(versao);
            raizDaVersao = pegarVersao(versao, raizDaVersao);
        }
        return imprimirEmOrdem(versao, raizDaVersao, 0);
    }

    private String imprimirEmOrdem(int versao, Node no, int prof) {
        String s = "";
        if(no != null) {
            Node pai = pegarVersao(versao, no.pai);
            if (pai == null) {
                no.profundidade = 0;
                prof = 0;
            } else {
                prof++;
                no.profundidade = prof;
            }
            Node esq = pegarVersao(versao, no.esq);
            s += imprimirEmOrdem(versao, esq, prof);
            s += (no.chave + "," + no.profundidade + "," + no.cor.name().charAt(0) + " ");
            Node dir = pegarVersao(versao, no.dir);
            s += imprimirEmOrdem(versao, dir, prof);
        }
        return s;
    }

    public Node pegarVersao(int versao, Node no) {
        Node noVerificar = no;
        Node noDaVersao = null;
        while (noVerificar != null && noDaVersao == null) {
            for(int i = noVerificar.mods.length - 1; i >= 0; i--) {
                if (noVerificar.mods[i] != null && versao >= noVerificar.mods[i].versao) {
                    noDaVersao = noVerificar.mods[i].noNaVersao;
                    break;
                }
            }
            noVerificar = noVerificar.versaoAnterior;
        }
        return noDaVersao;
    }

    /***** Métodos utilziados nos testes da árvore ***** */
    /* ************************************************* */
    public void imprimirEmOrdemComVersoes(Node no) {
        if(no != null) {
            if (no.pai != null) {
                no.profundidade = no.pai.profundidade + 1;
            }
            imprimirEmOrdemComVersoes(no.esq);
            System.out.print(no.chave + "," + no.profundidade + "," + no.cor.name().charAt(0) + "\n");
            imprimirMods(no);
            Node versaoAnterior = no.versaoAnterior;
            while (versaoAnterior != null) {
                System.out.println("Versão anterior:");
                imprimirMods(no.versaoAnterior);
                versaoAnterior = versaoAnterior.versaoAnterior;
            }
            System.out.println();
            imprimirEmOrdemComVersoes(no.dir);
        }
    }

    private void imprimirMods(Node no) {
        for(Mod m : no.mods) {
            if (m == null)
                break;
            System.out.printf("%dv,%dch,%sc,%sp,%se,%sd\n", 
                m.versao, 
                m.noNaVersao.chave, 
                m.noNaVersao.cor.name().charAt(0), 
                m.noNaVersao.pai, 
                m.noNaVersao.esq, 
                m.noNaVersao.dir);
        }
    }

    /***** Métodos comuns para a Árvore Rubro Negra sem persistência parcial *****/
    /* ************************************************* */
    public String buscarSucessor(int chave) {
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
        String retorno = "";
        if (suc == null) {
            retorno = "INFINITO";
        } else {
            retorno = suc.chave + ""; 
        }
        return retorno;
    }

    public String imprimirPreOrdem(Node no) {
        String s = "";
        if(no != null) {
            s += ("(" + no.chave + no.cor.name().toLowerCase().charAt(0));
            s += imprimirPreOrdem(no.esq);
            s += imprimirPreOrdem(no.dir);
            s += (")");
        }
        return s;
    }

    public String imprimirEmOrdem(Node no) {
        return imprimirEmOrdem(no, 0);
    }    

    private String imprimirEmOrdem(Node no, int prof) {
        String s = "";
        if(no != null) {
            if (no.pai == null) {
                no.profundidade = 0;
                prof = 0;
            } else {
                prof++;
                no.profundidade = prof;
            }
            s += imprimirEmOrdem(no.esq, prof);
            s += (no.chave + "," + no.profundidade + "," + no.cor.name().charAt(0) + " ");
            s += imprimirEmOrdem(no.dir, prof);
        }
        return s;
    }
}