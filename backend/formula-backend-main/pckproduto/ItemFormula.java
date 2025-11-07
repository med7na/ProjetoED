package pckproduto;

public class ItemFormula {
    private int id;
    private int produtoId;
    private int componenteId;
    private String nome; // nome do componente
    private int quantidade;
    private double custo;

    public ItemFormula() {}

    public ItemFormula(int produtoId, int componenteId, String nome, int quantidade, double custo) {
        this.produtoId = produtoId;
        this.componenteId = componenteId;
        this.nome = nome;
        this.quantidade = quantidade;
        this.custo = custo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public int getComponenteId() { return componenteId; }
    public void setComponenteId(int componenteId) { this.componenteId = componenteId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getCusto() { return custo; }
    public void setCusto(double custo) { this.custo = custo; }

    @Override
    public String toString() {
        double total = quantidade * custo;
        return nome + " | Qtd: " + quantidade + " | Unit: R$ " + String.format("%.2f", custo) + " | Total: R$ " + String.format("%.2f", total);
    }
}
