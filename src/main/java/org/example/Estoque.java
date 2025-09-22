package org.example;

import org.example.Model.Medicamento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.time.LocalDate;

public class Estoque {
    protected ArrayList<Medicamento> lista;

    public Estoque() {
        this.lista = new ArrayList<>();
    }

    public void adicionar(Medicamento m) {
        lista.add(m);
        Historico.registrar("Adicionado: " + m.getNome());
    }

    public void removerPorCodigo(String codigo) {
        lista.removeIf(m -> m.getCodigo().equals(codigo));
        Historico.registrar("Removido medicamento com código: " + codigo);
    }

    public Medicamento buscarPorNome(String nome) {
        ordenarPorNome();
        int inicio = 0, fim = lista.size() - 1;
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            String atual = lista.get(meio).getNome().toLowerCase();
            int comp = nome.toLowerCase().compareTo(atual);
            if (comp == 0) return lista.get(meio);
            else if (comp < 0) fim = meio - 1;
            else inicio = meio + 1;
        }
        return null;
    }

    public void atualizarQuantidade(String nome, int delta) {
        for (Medicamento m : lista) {
            if (m.getNome().equalsIgnoreCase(nome)) {
                int novaQuantidade = m.getQuantidade() + delta;
                if (novaQuantidade >= 0) {
                    m.setQuantidade(novaQuantidade);
                    Historico.registrar("Quantidade de " + nome + " atualizada para " + novaQuantidade);
                }
                return;
            }
        }
    }

    public void listarTodos() {
        System.out.println("----- Lista de Medicamentos -----");
        for (Medicamento m : lista) {
            m.exibirDetalhes();
        }
    }

    public void listarPorValidadeProxima(int meses) {
        System.out.println("----- Medicamentos com validade nos próximos " + meses + " meses -----");

        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusMonths(meses);

        for (Medicamento m : lista) {
            LocalDate validade = LocalDate.parse(m.getValidade() + "-01");

            if (!validade.isAfter(limite)) {
                m.exibirDetalhes();
            }
        }
    }


    public void ordenarPorNome() {
        Collections.sort(lista, Comparator.comparing(m -> m.getNome().toLowerCase()));
    }

    public void ordenarPorTipo() {
        Collections.sort(lista, Comparator.comparing(m -> m.getTipo().toLowerCase()));
    }

    public ArrayList<Medicamento> getLista() {
        return lista;
    }
}
