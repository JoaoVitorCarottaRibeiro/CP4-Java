package org.example.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.Model.Medicamento;

public class MedicamentoDAO {

    public MedicamentoDAO() {
        criarTabela();
        inserirRemediosIniciais();
    }

    private void criarTabela() {
        String sql = """
                CREATE TABLE IF NOT EXISTS medicamento(
                codigo VARCHAR(50) PRIMARY KEY,
                nome VARCHAR(100),
                tipo VARCHAR(50),
                quantidade INT,
                validade VARCHAR(10)
                )
                """;
        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void inserirRemediosIniciais() {
        String[] codigos = {"456890","543231","998008","544678","176580"};
        String[][] remedios = {
                {"Paracetamol","Analgesico","50","2025-12"},
                {"Amoxicilina","Antibiotico","30","2025-08"},
                {"Ibuprofeno","Anti-inflamatorio","40","2025-11"},
                {"Losartana","Anti-hipertensivo","20","2026-01"},
                {"Vitamina C","Vitamina/Suplemento","100","2026-06"}
        };

        try (Connection conn = Conexao.getConnection()) {
            for (int i = 0; i < remedios.length; i++) {
                String checkSql = "SELECT COUNT(*) FROM medicamento WHERE codigo=?";
                try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                    psCheck.setString(1, codigos[i]);
                    ResultSet rs = psCheck.executeQuery();
                    if (rs.next() && rs.getInt(1) == 0) {
                        inserir(new Medicamento(remedios[i][0], remedios[i][1],
                                Integer.parseInt(remedios[i][2]),
                                remedios[i][3],
                                codigos[i]));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void inserir(Medicamento m) {
        String sql = "INSERT INTO medicamento(codigo,nome,tipo,quantidade,validade) VALUES (?,?,?,?,?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getCodigo());
            ps.setString(2, m.getNome());
            ps.setString(3, m.getTipo());
            ps.setInt(4, m.getQuantidade());
            ps.setString(5, m.getValidade());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medicamento> listar() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamento";
        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Medicamento m = new Medicamento(
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getInt("quantidade"),
                        rs.getString("validade"),
                        rs.getString("codigo")
                );
                lista.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void atualizarQuantidade(String codigo, int novaQtd) {
        String sql = "UPDATE medicamento SET quantidade=? WHERE codigo=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, novaQtd);
            ps.setString(2, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remover(String codigo) {
        String sql = "DELETE FROM medicamento WHERE codigo=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

