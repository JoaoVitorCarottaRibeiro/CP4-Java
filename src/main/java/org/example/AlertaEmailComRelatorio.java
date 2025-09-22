package org.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AlertaEmailComRelatorio {

    public static void enviarHistoricoPorEmail(String destino) {
        String remetente = "fiapespx1@gmail.com";
        String senha = "gjdy gxjw tjnm efyn"; // senha do app

        // Gera PDF
        String nomePDF = "Relatorio_Acoes.pdf";
        gerarPDF(nomePDF);

        // Corpo do email
        String corpoEmail = Historico.getLog().isEmpty()
                ? "Nenhuma ação registrada ainda."
                : "Histórico de ações do Controle de Estoque:\n\n" + String.join("\n", Historico.getLog());

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            message.setSubject("Histórico de Ações - Controle de Estoque");

            MimeBodyPart texto = new MimeBodyPart();
            texto.setText(corpoEmail);

            MimeBodyPart anexo = new MimeBodyPart();
            anexo.attachFile(nomePDF);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(texto);
            multipart.addBodyPart(anexo);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("E-mail enviado com sucesso para " + destino + "!");
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    private static void gerarPDF(String nomeArquivo) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));
            document.open();
            document.add(new Paragraph("Relatório de Ações do Sistema Steve\n\n"));
            for (String acao : Historico.getLog()) {
                document.add(new Paragraph("- " + acao));
            }
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
