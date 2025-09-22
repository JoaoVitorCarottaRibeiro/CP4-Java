package org.example;

import org.example.DAO.MedicamentoDAO;
import org.example.Model.Medicamento;

import java.util.List;

public class TesteDAO {
    public static void main(String[] args) {
        MedicamentoDAO dao = new MedicamentoDAO();

        Medicamento m1 = new Medicamento("Ibuprofeno","Anti-inflamatório",200,"2026-08","111");
        dao.inserir(m1);

        List<Medicamento> lista = dao.listar();
        for (Medicamento m : lista) {
            m.exibirDetalhes();
        }

        dao.atualizarQuantidade("111",500);

        dao.remover("111");
    }
}

