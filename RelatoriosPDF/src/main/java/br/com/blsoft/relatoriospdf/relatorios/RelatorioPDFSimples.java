package br.com.blsoft.relatoriospdf.relatorios;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.GroupLayout.Alignment;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import br.com.blsoft.relatoriospdf.vendas.Produto;
import br.com.blsoft.relatoriospdf.vendas.Venda;

public class RelatorioPDFSimples implements Relatorio {

    private Venda venda;
    private Document documentoPDF;
    private String caminhoRelatorio = "D:/LEP.pdf";

    public RelatorioPDFSimples(Venda venda) {
        this.venda = venda;
        
        // Criando o objeto que vai ser o PDF
        documentoPDF = new Document();
        try {
        	// Passando o objeto que criamos e o local aonde vai ser salvo o PDF
            PdfWriter.getInstance(documentoPDF, new FileOutputStream(caminhoRelatorio));
            // Abrindo o PDF para edicao
            documentoPDF.open();
        } catch (DocumentException e) {

            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gerarCabecalho() {
    	// Quebrando a sessao
    	this.adicionarQuebraDeSessao();
    
    	// Texto que fica acima do titulo
    	// Criando o paragrafo
    	Paragraph textoAcimaTitulo = new Paragraph();
    	// Adicionando o alinhamento
    	textoAcimaTitulo.setAlignment(Element.ALIGN_RIGHT);
    	// Setando o texto
    	textoAcimaTitulo.add(new Chunk("List of Effective Pages", new Font(Font.HELVETICA, 14)));
    	// Adicionando o conteudo ao PDF
    	documentoPDF.add(textoAcimaTitulo);
    	// Quebrando sessao
    	this.adicionarQuebraDeSessao();
    	//Pulando linha
    	this.pularLinha();
    }

    @Override
    public void gerarCorpo() {
    	PdfPTable tableProdutos = this.criarTabelaComCabecalho();
    	this.adicionarProdutosATabela(tableProdutos);
    	this.documentoPDF.add(tableProdutos);
    	
    	// Criando um paragrafo novo
        Paragraph pItensVendidos = new Paragraph();
        // Alinhando ao centro
        pItensVendidos.setAlignment(Element.ALIGN_CENTER);
        // Adicionando o nome
        pItensVendidos.add(new Chunk("ITENS VENDIDOS ", new Font(Font.TIMES_ROMAN, 16)));
        // Adicionando ao PDF
        documentoPDF.add(new Paragraph(pItensVendidos));

        // Pra cada produto que tiver nos produtos vendidos
        for (Produto produto : this.venda.getProdutosVendidos()) {
        	// Criando um paragrafo
            Paragraph pNomeProduto = new Paragraph();
            // Pegando o nome do produto e adicionando ao objeto pNomeProduto
            pNomeProduto.add(new Chunk(produto.getNome(), new Font(Font.COURIER, 14)));
            
            // Criando outro paragrafo
            Paragraph pDadosProduto = new Paragraph();
            // Adicionando o conteudo ao objeto pDadosProduto
            pDadosProduto.add("Quantidade:  " + produto.getQuantidade() + " - Preço unit.: R$ " + produto.getValor()
                    + " - Total: R$ " + produto.calcularPreco());
            
            // Adicionando os paragrafos ao PDF
            this.documentoPDF.add(pNomeProduto);
            this.documentoPDF.add(pDadosProduto);
            
            // Divisao do PDF
            this.documentoPDF.add(new Paragraph("---------------"));

        }
        
        // Criando um paragrafo pra exibir o total da venda
        Paragraph pTotal = new Paragraph();
        // Adicionando o alinhamneto
        pTotal.setAlignment(Element.ALIGN_RIGHT);
        // Adicionando o conteudo ao objeto pTotal
        pTotal.add(new Chunk("Total da venda: R$ " + this.venda.calcularValorTotalCarrinho(),
                new Font(Font.TIMES_ROMAN, 20)));
        // Adicionando o conteudo no PDF
        this.documentoPDF.add(pTotal);
    }

    @Override
    public void gerarRodape() {
    	// Criando uma divisao
        Paragraph paragrafoSessao = new Paragraph("----------------------------------------------------------");
        // Alinhando ao centro
        paragrafoSessao.setAlignment(Element.ALIGN_CENTER);
        // Adicionando o paragrafo ao PDF
        this.documentoPDF.add(paragrafoSessao);
        // Criando um paragrafo vazio
        this.documentoPDF.add(new Paragraph(" "));
        
        // Criando outro paragrafo para informar o site
        Paragraph pRodape = new Paragraph();
        pRodape.setAlignment(Element.ALIGN_CENTER);
        pRodape.add(new Chunk("www.blsoft.com.br/like", new Font(Font.TIMES_ROMAN, 14)));
        this.documentoPDF.add(pRodape);
    }

    @Override
    public void imprimir() {
    	// Se o documento não for nulo e não estiver aberto
        if(this.documentoPDF != null && this.documentoPDF.isOpen()){
        	// Fechando o documento
            documentoPDF.close();
        }
    }
    
    private void adicionarQuebraDeSessao() {
        Paragraph paragrafoSessao = new Paragraph("_____________________________________________________________________________");
        paragrafoSessao.setAlignment(Element.ALIGN_CENTER);
        this.documentoPDF.add(paragrafoSessao);
    }
    
    private void pularLinha() {
        this.documentoPDF.add(new Paragraph(" "));
    }
    
    private PdfPTable criarTabelaComCabecalho() {
        // tabela com 4 colunas
        PdfPTable tableProdutos = new PdfPTable(4);
        tableProdutos.setWidthPercentage(98);
        tableProdutos.setWidths(new float[] { 1f, 1f, 1f, 1f });

        PdfPCell celulaTitulo = new PdfPCell(new Phrase("Block"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tableProdutos.addCell(celulaTitulo);

        celulaTitulo = new PdfPCell(new Phrase("Code"));
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableProdutos.addCell(celulaTitulo);

        celulaTitulo = new PdfPCell(new Phrase("Page"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tableProdutos.addCell(celulaTitulo);

        celulaTitulo = new PdfPCell(new Phrase("Change"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tableProdutos.addCell(celulaTitulo);

        return tableProdutos;
    }
    
    private void adicionarProdutosATabela(PdfPTable tableProdutos) {
        int contador = 1;
        for (Produto produto : this.venda.getProdutosVendidos()) {

            PdfPCell celulaNome = new PdfPCell(new Phrase(produto.getNome()));
            PdfPCell celulaQuantidade = new PdfPCell(new Phrase(String.valueOf(produto.getQuantidade())));
            celulaQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celulaValor = new PdfPCell(new Phrase("R$ " + String.valueOf(produto.getValor())));
            celulaValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celulaTotalUnit = new PdfPCell(new Phrase("R$ " + String.valueOf(produto.calcularPreco())));
            celulaTotalUnit.setHorizontalAlignment(Element.ALIGN_CENTER);

            if (contador % 2 == 0) {
                celulaNome.setBackgroundColor(Color.LIGHT_GRAY);
                celulaQuantidade.setBackgroundColor(Color.LIGHT_GRAY);
                celulaValor.setBackgroundColor(Color.LIGHT_GRAY);
                celulaTotalUnit.setBackgroundColor(Color.LIGHT_GRAY);
            }

            tableProdutos.addCell(celulaNome);
            tableProdutos.addCell(celulaQuantidade);
            tableProdutos.addCell(celulaValor);
            tableProdutos.addCell(celulaTotalUnit);

            contador++;
        }
    }


}
