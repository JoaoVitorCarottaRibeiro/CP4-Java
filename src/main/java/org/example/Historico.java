package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Historico {
    private static ArrayList<String> log = new ArrayList<>();

    public static void registrar(String acao) {
        log.add(LocalDateTime.now() + " - " + acao);
    }

    public static java.util.List<String> getLog() {
        return log;
    }

    public static void exibir() {
        System.out.println("----- Histórico de Ações -----");
        if (log.isEmpty()) {
            System.out.println("Nenhuma ação registrada.");
        } else {
            for (String linha : log) {
                System.out.println(linha);
            }
        }
    }
}
