package org.example;

import org.example.DAO.MedicamentoDAO;

import java.util.List;
import java.util.Scanner;
import org.example.Model.Medicamento;
import org.example.DAO.MedicamentoDAO;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MedicamentoDAO dao = new MedicamentoDAO();

        int opcao;
        do {
            System.out.println("\n==== MENU ====");
            System.out.println("1 - Listar remédios");
            System.out.println("2 - Buscar por nome");
            System.out.println("3 - Atualizar quantidade");
            System.out.println("4 - Inserir novo remédio");
            System.out.println("5 - Remover remédio");
            System.out.println("6 - Listar validade próxima");
            System.out.println("7 - Ordenar por validade");
            System.out.println("8 - Ver histórico");
            System.out.println("9 - Gerar relatório CSV");
            System.out.println("10 - Enviar relatório PDF por e-mail");
            System.out.println("11 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    List<Medicamento> lista = dao.listar();
                    System.out.println("----- Lista de Medicamentos -----");
                    for (Medicamento m : lista) {
                        m.exibirDetalhes();
                    }
                }
                case 2 -> {
                    System.out.print("Nome do remédio: ");
                    String nome = sc.nextLine();
                    Medicamento encontrado = dao.listar().stream()
                            .filter(m -> m.getNome().equalsIgnoreCase(nome))
                            .findFirst().orElse(null);
                    if (encontrado != null) encontrado.exibirDetalhes();
                    else System.out.println("Não encontrado.");
                }
                case 3 -> {
                    System.out.print("Código do remédio: ");
                    String codigo = sc.nextLine();
                    System.out.print("Nova quantidade: ");
                    int qtd = sc.nextInt(); sc.nextLine();
                    dao.atualizarQuantidade(codigo, qtd);
                    Historico.registrar("Quantidade do medicamento " + codigo + " atualizada para " + qtd);
                }
                case 4 -> {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Tipo: ");
                    String tipo = sc.nextLine();
                    System.out.print("Qtd: ");
                    int qtd = sc.nextInt(); sc.nextLine();
                    System.out.print("Validade (YYYY-MM): ");
                    String validade = sc.nextLine();
                    System.out.print("Código: ");
                    String codigo = sc.nextLine();
                    Medicamento novo = new Medicamento(nome, tipo, qtd, validade, codigo);
                    dao.inserir(novo);
                    Historico.registrar("Inserido medicamento: " + nome);
                }
                case 5 -> {
                    System.out.print("Código do remédio: ");
                    String cod = sc.nextLine();
                    dao.remover(cod);
                    Historico.registrar("Removido medicamento com código: " + cod);
                }
                case 6 -> {
                    System.out.print("Quantos meses para frente? ");
                    int meses = sc.nextInt(); sc.nextLine();
                    Estoque estoqueTmp = new Estoque();
                    estoqueTmp.getLista().addAll(dao.listar());
                    estoqueTmp.listarPorValidadeProxima(meses);
                }
                case 7 -> {
                    List<Medicamento> lista = dao.listar();
                    lista = OrdenadorPorValidade.ordenar(lista);
                    System.out.println("----- Medicamentos ordenados por validade -----");
                    for (Medicamento m : lista) {
                        m.exibirDetalhes();
                    }
                }
                case 8 -> Historico.exibir();
                case 9 -> RelatorioCSV.gerarCSV();
                case 10 -> {
                    System.out.print("E-mail do destinatário: ");
                    String destino = sc.nextLine();
                    AlertaEmailComRelatorio.enviarHistoricoPorEmail(destino);
                }
                case 11 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 11);

        sc.close();
    }
}
