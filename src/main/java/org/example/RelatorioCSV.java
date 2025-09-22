package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class RelatorioCSV {
    public static void gerarCSV() {
        if (Historico.getLog().isEmpty()) {
            System.out.println("Nenhuma ação registrada para gerar CSV.");
            return;
        }

        String nomeArquivo = "Relatorio_Acoes.csv";

        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            fw.append("Ação\n");
            for (String acao : Historico.getLog()) {
                fw.append(acao).append("\n");
            }
            System.out.println("CSV gerado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
