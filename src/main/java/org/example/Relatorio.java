package org.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Relatorio {

    public static void gerarRelatorioPDF() {
        if (Historico.getLog().isEmpty()) {
            System.out.println("Nenhuma ação registrada para gerar relatório.");
            return;
        }

        String nomeArquivo = "Relatorio_Acoes.pdf";
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));
            document.open();
            document.add(new Paragraph("Relatório de Ações do Sistema Steve\n\n"));

            for (String acao : Historico.getLog()) {
                document.add(new Paragraph("- " + acao));
            }

            document.close();
            System.out.println("Relatório gerado com sucesso: " + nomeArquivo);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
