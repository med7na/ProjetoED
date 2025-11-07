package pckproduto;

public class Produto {
    private int id;
    private String nome;
    private double custoTotal;
    private int quantidadeTotal;

    public Produto() {}

    // usado no SistemaFormulaApp: new Produto(nome, qtd, 0.0)
    public Produto(String nome, int quantidadeTotal, double custoTotal) {
        this.nome = nome;
        this.quantidadeTotal = quantidadeTotal;
        this.custoTotal = custoTotal;
    }

    public Produto(int id, String nome, double custoTotal, int quantidadeTotal) {
        this.id = id;
        this.nome = nome;
        this.custoTotal = custoTotal;
        this.quantidadeTotal = quantidadeTotal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getCustoTotal() { return custoTotal; }
    public void setCustoTotal(double custoTotal) { this.custoTotal = custoTotal; }

    public int getQuantidadeTotal() { return quantidadeTotal; }
    public void setQuantidadeTotal(int quantidadeTotal) { this.quantidadeTotal = quantidadeTotal; }

    @Override
    public String toString() {
        return nome + " | Qtd: " + quantidadeTotal + " | Custo total: R$ " + String.format("%.2f", custoTotal);
    }
}
