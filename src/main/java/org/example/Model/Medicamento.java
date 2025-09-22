package org.example.Model;

public class Medicamento {
    private String nome;
    private String tipo;
    private int quantidade;
    private String validade;
    private String codigo;

    public Medicamento() {}

    public Medicamento(String nome, String tipo, int quantidade, String validade, String codigo) {
        this.nome = nome;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.validade = validade;
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void exibirDetalhes() {
        System.out.println(nome + " | Tipo: " + tipo + " | Quantidade: " + quantidade +
                " | Validade: " + validade + " | Código: " + codigo);
    }
}
