package br.com.cursoDankiCode.Pizzaria.Pizza;

import jakarta.persistence.*;


@Entity
@Table(name = "Pizza")


public class Pizza {        ///  entidade pizza
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) /// o id serve para identificação
    private Long id;

    private String nome;

    private double preco;

    private boolean disponivel;

    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Enumerated(EnumType.STRING)
    private Sabor sabor;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public void setSabor(Sabor sabor) {
        this.sabor = sabor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
