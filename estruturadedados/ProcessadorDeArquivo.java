package estruturadedados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProcessadorDeArquivo {

    private ArvoreRubroNegra arvore;
    private String resultado = "";
    public ProcessadorDeArquivo() {
        this.arvore = new ArvoreRubroNegra();
    }

    public void processarArquivo(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {            
            String linha;
            while ((linha = br.readLine()) != null) {
                processarComando(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("saida.txt", false))) {
            if (!resultado.isEmpty()) {
                bw.write(resultado);
                bw.newLine();
                System.out.println("Arquivo de saida gerado com sucesso.");
                System.out.println("Local: persistencia-parcial/saida.txt");
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de saída: " + e.getMessage());
        }
    }

    private void processarComando(String linha) {
        String[] partes = linha.trim().split("\\s+");
        if (partes.length < 2){
            return;
        } 

        String comando = partes[0].toLowerCase();

        int valor;
        try {
            valor = Integer.parseInt(partes[1]);
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido: " + partes[1]);
            return;
        }

        switch (comando) {
            case "inc":
                arvore.inserir(valor);
                //System.out.println("Inserido: " + valor);
                //System.out.println("Versao: " + arvore.versaoAtual);
                break;
            case "rem":
                arvore.remover(valor);
                //System.out.println("Removido: " + valor);
                break;
            case "suc":
                if (partes.length < 3) {
                    System.err.println("Comando suc precisa de dois parâmetros: chave e versão.");
                    return;
                }
                int chave = Integer.parseInt(partes[1]);
                int versao = Integer.parseInt(partes[2]);
                String suc = arvore.buscarSucessor(chave, versao);

                 // Grava diretamente no arquivo "saida.txt"
                resultado += "SUC " + chave + " " + versao + "\n";
                resultado += suc + "\n";
                
                // Imprimir conforme especificado
                //System.out.println("SUC " + chave + " " + versao);
                //System.out.println(resultado);
                break;
            
            case "imp":
                int versionTree = Integer.parseInt(partes[1]);
                String resultadoImpressao = arvore.imprimirEmOrdem(versionTree);

                resultado += "IMP " + versionTree + "\n";
                resultado += resultadoImpressao + "\n";
                //System.out.println("IMP " + versionTree + "\n" + resultadoImpressao);
            break;

            default:
                System.out.println("Comando desconhecido: " + comando);
                break;
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java estruturadedados.ProcessadorDeArquivo <caminho_arquivo>");
            return;
        }

        ProcessadorDeArquivo pa = new ProcessadorDeArquivo();
        pa.processarArquivo(args[0]);
    }
}
