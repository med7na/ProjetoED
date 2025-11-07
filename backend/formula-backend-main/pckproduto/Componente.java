package pckproduto;

public class Componente {
    private int id;
    private String nome;
    private double custo;

    public Componente() {}

    public Componente(int id, String nome, double custo) {
        this.id = id;
        this.nome = nome;
        this.custo = custo;
    }

    public Componente(String nome, double custo) {
        this.nome = nome;
        this.custo = custo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getCusto() { return custo; }
    public void setCusto(double custo) { this.custo = custo; }

    @Override
    public String toString() {
        return nome + " | Custo unitário: R$ " + String.format("%.2f", custo);
    }
}
