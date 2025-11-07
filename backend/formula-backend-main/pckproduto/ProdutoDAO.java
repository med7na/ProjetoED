package pckproduto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public int inserirProduto(Produto p) {
        String sql = "INSERT INTO produto (nome, custo_total, quantidade_total) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNome());
            ps.setDouble(2, p.getCustoTotal());
            ps.setInt(3, p.getQuantidadeTotal());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    p.setId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro inserir produto: " + e.getMessage());
        }
        return -1;
    }

    public Produto buscarPorNome(String nome) {
        String sql = "SELECT id, nome, custo_total, quantidade_total FROM produto WHERE nome = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("custo_total"),
                            rs.getInt("quantidade_total")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro buscar produto: " + e.getMessage());
        }
        return null;
    }

    public List<Produto> listarTodos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT id, nome, custo_total, quantidade_total FROM produto";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("custo_total"),
                        rs.getInt("quantidade_total")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro listar produtos: " + e.getMessage());
        }
        return lista;
    }

    public void atualizarQuantidade(int idProduto, int adicionar) {
        String sql = "UPDATE produto SET quantidade_total = quantidade_total + ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, adicionar);
            ps.setInt(2, idProduto);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro atualizar quantidade: " + e.getMessage());
        }
    }

    public void atualizarCusto(int idProduto, double novoCusto) {
        String sql = "UPDATE produto SET custo_total = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, novoCusto);
            ps.setInt(2, idProduto);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro atualizar custo do produto: " + e.getMessage());
        }
    }

    public void excluirProduto(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro excluir produto: " + e.getMessage());
        }
    }
}
