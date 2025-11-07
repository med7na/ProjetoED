package pckproduto;

import java.util.List;
import java.util.Scanner;

public class SistemaFormulaApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final ProdutoDAO produtoDAO = new ProdutoDAO();
    private static final ItemFormulaDAO itemDAO = new ItemFormulaDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== SISTEMA DE FÓRMULAS DE PRODUÇÃO ===");
            System.out.println("1. Cadastrar produto e fórmula");
            System.out.println("2. Exibir fórmula (explosão)");
            System.out.println("3. Calcular custo total (recalcula e salva)");
            System.out.println("4. Atualizar preço de componente");
            System.out.println("5. Excluir produto");
            System.out.println("6. Listar produtos");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1" -> cadastrarProduto();
                case "2" -> exibirFormula();
                case "3" -> calcularCusto();
                case "4" -> atualizarComponente();
                case "5" -> excluirProduto();
                case "6" -> listarProdutos();
                case "0" -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarProduto() {
        System.out.print("Nome do produto principal: ");
        String nome = sc.nextLine().trim();
        if (nome.isEmpty()) { System.out.println("Nome inválido."); return; }

        Produto existente = produtoDAO.buscarPorNome(nome);
        if (existente != null) {
            System.out.println("Produto já existe: " + existente);
            System.out.print("Deseja adicionar quantidade ao estoque? (s/n): ");
            String r = sc.nextLine().trim();
            if (r.equalsIgnoreCase("s")) {
                System.out.print("Quantidade a adicionar: ");
                int qtd = Integer.parseInt(sc.nextLine().trim());
                produtoDAO.atualizarQuantidade(existente.getId(), qtd);
                System.out.println("Quantidade atualizada.");
            }
            return;
        }

        System.out.print("Quantidade inicial no sistema (enter = 0): ");
        String qtdStr = sc.nextLine().trim();
        int qtd = qtdStr.isEmpty() ? 0 : Integer.parseInt(qtdStr);

        Produto prod = new Produto(nome, qtd, 0.0);
        int idProduto = produtoDAO.inserirProduto(prod);
        if (idProduto <= 0) { System.out.println("Erro ao inserir produto."); return; }

        System.out.println("Informe os componentes (digite 'fim' no nome para terminar).");
        while (true) {
            System.out.print("Nome do componente (ou 'fim'): ");
            String comp = sc.nextLine().trim();
            if (comp.equalsIgnoreCase("fim")) break;
            if (comp.isEmpty()) { System.out.println("Nome inválido."); continue; }

            System.out.print("Quantidade necessária (inteiro): ");
            int q = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Custo unitário (ex: 1.50): ");
            double c = Double.parseDouble(sc.nextLine().trim());

            int idComponente = itemDAO.garantirComponente(comp, c); // cria ou atualiza componente
            if (idComponente <= 0) { System.out.println("Erro ao garantir componente."); continue; }

            itemDAO.inserirFormula(idProduto, idComponente, q);
        }

        double custoTotal = itemDAO.calcularCustoTotal(idProduto);
        produtoDAO.atualizarCusto(idProduto, custoTotal);
        System.out.printf("Produto '%s' cadastrado. Custo total: R$ %.2f%n", nome, custoTotal);
    }

    private static void exibirFormula() {
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine().trim();
        Produto p = produtoDAO.buscarPorNome(nome);
        if (p == null) { System.out.println("Produto não encontrado."); return; }

        List<ItemFormula> itens = itemDAO.buscarPorProduto(p.getId());
        if (itens.isEmpty()) { System.out.println("Sem componentes cadastrados."); return; }

        System.out.println("\n--- Fórmula de " + p.getNome() + " ---");
        for (ItemFormula it : itens) System.out.println("- " + it);
        System.out.printf("Custo total (registrado): R$ %.2f%n", p.getCustoTotal());
    }

    private static void calcularCusto() {
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine().trim();
        Produto p = produtoDAO.buscarPorNome(nome);
        if (p == null) { System.out.println("Produto não encontrado."); return; }

        double total = itemDAO.calcularCustoTotal(p.getId());
        produtoDAO.atualizarCusto(p.getId(), total);
        System.out.printf("Custo total recalculado e salvo: R$ %.2f%n", total);
    }

    private static void atualizarComponente() {
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine().trim();
        Produto p = produtoDAO.buscarPorNome(nome);
        if (p == null) { System.out.println("Produto não encontrado."); return; }

        List<ItemFormula> itens = itemDAO.buscarPorProduto(p.getId());
        if (itens.isEmpty()) { System.out.println("Sem componentes."); return; }

        System.out.println("Componentes:");
        for (int i = 0; i < itens.size(); i++) {
            ItemFormula it = itens.get(i);
            System.out.println((i+1) + ". " + it.getNome() + " | Qtd: " + it.getQuantidade() + " | Unit: R$ " + String.format("%.2f", it.getCusto()));
        }

        System.out.print("Escolha o número do componente para atualizar: ");
        int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
        if (idx < 0 || idx >= itens.size()) { System.out.println("Índice inválido."); return; }

        ItemFormula alvo = itens.get(idx);
        System.out.print("Novo custo unitário para " + alvo.getNome() + ": ");
        double novo = Double.parseDouble(sc.nextLine().trim());

        itemDAO.atualizarCustoComponente(alvo.getComponenteId(), novo);

        double total = itemDAO.calcularCustoTotal(p.getId());
        produtoDAO.atualizarCusto(p.getId(), total);
        System.out.printf("Componente atualizado. Novo custo total do produto: R$ %.2f%n", total);
    }

    private static void excluirProduto() {
        System.out.print("Nome do produto a excluir: ");
        String nome = sc.nextLine().trim();
        Produto p = produtoDAO.buscarPorNome(nome);
        if (p == null) { System.out.println("Produto não encontrado."); return; }

        itemDAO.excluirPorProduto(p.getId()); // apaga fórmula
        produtoDAO.excluirProduto(p.getId()); // apaga produto
        System.out.println("Produto e componentes da fórmula excluídos.");
    }

    private static void listarProdutos() {
        List<Produto> lista = produtoDAO.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum produto cadastrado."); return; }
        System.out.println("\n--- Produtos cadastrados ---");
        for (Produto p : lista) System.out.println(p);
    }
}
