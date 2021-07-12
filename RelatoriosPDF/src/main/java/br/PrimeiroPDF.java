package br;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class PrimeiroPDF {

    public PrimeiroPDF(String fraseAImprimir) {

    	// Criando um objeto que vai ser o documento PDF
        Document documentPDF = new Document();

        try {
        	// Passando o objeto que criamos e o local aonde vai ser salvo o PDF
            PdfWriter.getInstance(documentPDF, new FileOutputStream("D:/Relatorio1.pdf"));

            // Abrindo o PDF pra edicao
            documentPDF.open();

            // Criando o paragrafo que vai ser inserido no PDF
            Paragraph paragrafoTeste = new Paragraph(fraseAImprimir);

            // Adicionando o objeto no PDF
            documentPDF.add(paragrafoTeste);

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Fechando o PDF
        documentPDF.close();

    }

}
