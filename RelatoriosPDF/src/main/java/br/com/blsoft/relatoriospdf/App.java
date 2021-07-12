package br.com.blsoft.relatoriospdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.PrimeiroPDF;
import br.com.blsoft.relatoriospdf.relatorios.Relatorio;
import br.com.blsoft.relatoriospdf.relatorios.RelatorioPDFBonito;
import br.com.blsoft.relatoriospdf.relatorios.RelatorioPDFSimples;
import br.com.blsoft.relatoriospdf.vendas.Produto;
import br.com.blsoft.relatoriospdf.vendas.Venda;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {	
    	//new PrimeiroPDF("Testando o primeiro PDF");
    	
    	// Criando uma nova venda 
        Venda venda = new Venda("Marquês da Sapucaí", new ArrayList<Produto>());
        venda.addProdutoAoCarrinho(new Produto("0-TITLE", 00, 1));
        venda.addProdutoAoCarrinho(new Produto("Pão com mortadela", 1, 1.0));
        venda.addProdutoAoCarrinho(new Produto("Linguiça", 2, 0.90));
        venda.addProdutoAoCarrinho(new Produto("Gorgonzola", 3, 10.90));
        venda.addProdutoAoCarrinho(new Produto("Pinga", 2, 0.10));
        venda.addProdutoAoCarrinho(new Produto("Pão com mortadela", 1, 1.0));
        venda.addProdutoAoCarrinho(new Produto("Linguiça", 2, 0.90));
        venda.addProdutoAoCarrinho(new Produto("Gorgonzola", 3, 10.90));
        venda.addProdutoAoCarrinho(new Produto("Pinga", 2, 0.10));
        venda.addProdutoAoCarrinho(new Produto("Pão com mortadela", 1, 1.0));
        venda.addProdutoAoCarrinho(new Produto("Linguiça", 2, 0.90));
        
        // Criando o relatorio
        Relatorio relatorioPdfSimples = new RelatorioPDFSimples(venda);
        //Relatorio relatorioPdfSimples = new RelatorioPDFBonito(venda);
        relatorioPdfSimples.gerarCabecalho();
        relatorioPdfSimples.gerarCorpo();
        relatorioPdfSimples.gerarRodape();
        relatorioPdfSimples.imprimir();
        
    }
}
