package pckproduto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemFormulaDAO {

    // garante componente (insere ou atualiza custo) e retorna id
    public int garantirComponente(String nome, double custo) {
        String sqlInsert = "INSERT INTO componente (nome, custo) VALUES (?, ?) ON DUPLICATE KEY UPDATE custo = VALUES(custo)";
        String sqlSelect = "SELECT id FROM componente WHERE nome = ?";
        try (Connection con = DatabaseConnection.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                ps.setString(1, nome);
                ps.setDouble(2, custo);
                ps.executeUpdate();
            }
            try (PreparedStatement ps2 = con.prepareStatement(sqlSelect)) {
                ps2.setString(1, nome);
                try (ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro garantir componente: " + e.getMessage());
        }
        return -1;
    }

    // insere registro na formula
    public void inserirFormula(int produtoId, int componenteId, int quantidade) {
        String sql = "INSERT INTO formula (produto_id, componente_id, quantidade) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, produtoId);
            ps.setInt(2, componenteId);
            ps.setInt(3, quantidade);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro inserir formula: " + e.getMessage());
        }
    }

    // lista itens da fórmula de um produto
    public List<ItemFormula> buscarPorProduto(int produtoId) {
        List<ItemFormula> lista = new ArrayList<>();
        String sql = "SELECT f.id, f.produto_id, f.componente_id, c.nome AS nome_comp, f.quantidade, c.custo AS custo_unit " +
                     "FROM formula f JOIN componente c ON f.componente_id = c.id WHERE f.produto_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, produtoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemFormula it = new ItemFormula();
                    it.setId(rs.getInt("id"));
                    it.setProdutoId(rs.getInt("produto_id"));
                    it.setComponenteId(rs.getInt("componente_id"));
                    it.setNome(rs.getString("nome_comp"));
                    it.setQuantidade(rs.getInt("quantidade"));
                    it.setCusto(rs.getDouble("custo_unit"));
                    lista.add(it);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro buscarPorProduto: " + e.getMessage());
        }
        return lista;
    }

    // soma quantidade*custo
    public double calcularCustoTotal(int produtoId) {
        String sql = "SELECT IFNULL(SUM(f.quantidade * c.custo), 0) AS total " +
                     "FROM formula f JOIN componente c ON f.componente_id = c.id WHERE f.produto_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, produtoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("Erro calcularCustoTotal: " + e.getMessage());
        }
        return 0.0;
    }

    public void atualizarCustoComponente(int componenteId, double novoCusto) {
        String sql = "UPDATE componente SET custo = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, novoCusto);
            ps.setInt(2, componenteId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro atualizarCustoComponente: " + e.getMessage());
        }
    }

    public void excluirPorProduto(int produtoId) {
        String sql = "DELETE FROM formula WHERE produto_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, produtoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro excluirPorProduto: " + e.getMessage());
        }
    }
}
