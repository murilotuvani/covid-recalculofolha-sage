package br.com.autogeral.sage.covid;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ContratoGerador {

	public static final Font FONT_NORMAL = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
	public static final Font FONT_BOLD = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	public static final Font FONT_ITALIC = new Font(FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.BLACK);

	public static void main(String[] args) {
		File file = new File("contrato.pdf");
		Funcionario func = new Funcionario();
		func.setNome("Murlo de Moraes Tuvani");
		func.setCpf("279.339.928-03");
		func.setCep("13.301-999");
		func.setEndereco("Rua Marechal Deodoro");
		func.setNumero("550");
		func.setComple("Apartamento 132");
		func.setBairro("Centro");
		func.setCid("Itu");
		func.setUf("SP");

		Empresa emp = new Empresa();
		emp.setNome("Auto Geral Autopeças LTDA");
		emp.setCnpj("05.437.537/0001-37");
		emp.setEndereco("Av. Dr. Octaviano Pereira Mends, 1333 - Itu/SP");
		emp.setCidade("Itu");
		emp.setRepresentanteCpf("279.339.928-03");
		emp.setRepresentanteNome("João Maria José");
		emp.setCodigo(1);

		Contrato ctr = new Contrato();
		ctr.setDias(90);
		ctr.setFuncionario(func);
		ctr.setEmpresa(emp);

		ContratoGerador cg = new ContratoGerador();
		cg.gerar(ctr, file);
	}

	void gerar(Contrato ctr, File file) {
		try {
			if (file.exists()) {
				file.delete();
			}
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			// Left
			Paragraph paragraph = new Paragraph("ACORDO PARA REDUÇÃO PROPORCIONAL DE JORNADA DE TRABALHO E DE SALÁRIO",
					FONT_BOLD);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			// Centered
			paragraph = new Paragraph();
			Chunk chunk = new Chunk("EMPREGADORA:", FONT_BOLD);
			paragraph.add(chunk);

			String texto = ctr.getEmpresa().getNome() + ", CNPJ nº" + ctr.getEmpresa().getCnpj() + ", "
					+ ctr.getEmpresa().getEndereco() + ", neste ato representada por "
					+ ctr.getEmpresa().getRepresentanteNome() + ", CPF nº " + ctr.getEmpresa().getRepresentanteCpf();
			chunk = new Chunk(texto, FONT_NORMAL);
			paragraph.add(chunk);
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(paragraph);

			paragraph = new Paragraph();
			chunk = new Chunk("EMPREGADO(A): ", FONT_BOLD);
			paragraph.add(chunk);

			texto = ctr.getFuncionario().getNome() + ", CPF nº " + ctr.getFuncionario().getCpf() + ", endereço "
					+ ctr.getFuncionario().getEnderecoCompleto();

			chunk = new Chunk(texto, FONT_NORMAL);
			paragraph.add(chunk);

			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(paragraph);
			// Left with indentation
			paragraph = new Paragraph(
					"Considerando o estado de calamidade pública decorrente do coronavírus (COVID-19), reconhecido pelo Decreto Legislativo nº 66 de 20 de março de 2020, e o teor da Medida Provisória nº 936 publicada em 1º de abril de 2020, as partes acima qualificadas, pelo presente instrumento e nos termos do artigo 468 da CLT, vêm por mútuo acordo promover a REDUÇÃO PROPORCIONAL DE JORNADA DE TRABALHO E DE SALÁRIO, nos seguintes moldes:");
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);

			chunk = new Chunk("CLÁUSULA PRIMEIRA", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" – O presente Termo Aditivo tem por objeto a redução proporcional de jornada de trabalho e de salário em 25% (vinte e cinco por cento), com fundamento na alínea “a”, do artigo 7º, inciso III, da Medida Provisória nº 936, preservado o valor do salário-hora de trabalho.\n"
							+ "PARÁGRAFO ÚNICO. O prazo desta redução será de xx dias (MÁXIMO DE 90 DIAS) nos termos do artigo 7º da Medida Provisória n.º 936.");
			paragraph.add(chunk);

			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("CLÁUSULA SEGUNDA", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" – A jornada de trabalho e o salário pago anteriormente à redução prevista na cláusula anterior serão restabelecidos no prazo de 2 (dois) dias corridos contado:");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("I.", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(" da cessação do estado de calamidade pública; ou");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("II.", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" da data estabelecida no acordo individual como termo de encerramento do período e redução pactuado; ou");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("III.", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" da data de comunicação do empregador que informe ao empregado sobre a sua decisão de antecipar o fim do período de redução pactuado.");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("CLÁUSULA TERCEIRA", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" – Esta redução proporcional de jornada de trabalho e salário negociada por acordo individual está dentro da parametrização estabelecida no artigo 12 da Medida Provisória nº 936, pois o(a) EMPREGADO(A) recebe salário igual ou inferior a R$ 3.135,00 (três mil cento e trinta e cinco reais), ou é portador(a) de diploma de nível superior e percebe salário mensal igual ou superior a duas vezes o limite máximo dos benefícios do Regime Geral de Previdência Social.");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("CLÁUSULA QUARTA", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" – Declaram-se cientes, a empregadora e o empregado, de que o auxílio será pago pelo Governo após 30 (trinta) dias da comunicação do acordo por parte da empresa.");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("§ 1º.", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" A empregadora comunicará redução proporcional da jornada e do salário ao Ministério da Economia, em até 10 (dez) dias corridos contados da assinatura dessa data.");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("§ 2º.", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" A empregadora comunicará redução proporcional da jornada e do salário ao respectivo Sindicato laboral, no prazo de até 10 (dez) dias corridos, contado da data de sua celebração.");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("CLÁUSULA QUINTA", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" – Assegura-se ao empregado que receber o Benefício Emergencial de Preservação do Emprego e da Renda a garantia provisória do emprego, nos seguintes termos:");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("I", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" - Durante o período acordado de redução da jornada de trabalho e de salário ou de suspensão temporária do contrato de trabalho; e");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("II", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" - Após o restabelecimento da jornada de trabalho e de salário ou do encerramento da suspensão temporária do contrato de trabalho, por período equivalente ao acordado para a redução ou a suspensão.");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("PARÁGRAFO ÚNICO.", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" A garantia provisória do emprego não existirá nas hipóteses de dispensa a pedido ou por justa causa do empregado.");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			chunk = new Chunk("CLÁUSULA SEXTA", FONT_BOLD);
			paragraph.add(chunk);

			chunk = new Chunk(
					" – As partes firmam o presente acordo individual de trabalho, que passa a fazer parte integrante e dissociável do contrato individual de trabalho previamente pactuado, para que produza seus efeitos jurídicos.");
			paragraph.add(chunk);
			document.add(paragraph);

			paragraph = new Paragraph(
					"Tendo assim acordado, as partes assinam o presente instrumento, em duas vias, na presença das testemunhas abaixo.");
			paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			document.add(paragraph);
			
			document.add(Chunk.NEWLINE);

			texto = ctr.getEmpresa().getCidade();
			texto += ", " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()).toLowerCase();
			paragraph = new Paragraph(texto);
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			paragraph.setFirstLineIndent(20);
			paragraph.setIndentationLeft(0);
			document.add(paragraph);

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(90);
			table.setWidths(new float[] { 40f, 20f, 40f });

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			table.addCell(createCell("Fruncionário(a):"));
			table.addCell(createCell(""));
			table.addCell(createCell("Empregador(a):"));

			PdfPCell cell = createCell("");
			cell.setBorder(Rectangle.BOTTOM);
			table.addCell(cell);

			table.addCell(createCell(""));

			cell = createCell("");
			cell.setBorder(Rectangle.BOTTOM);
			table.addCell(cell);

			table.addCell(createCell(ctr.getFuncionario().getNome(), Element.ALIGN_CENTER));
			table.addCell(createCell(""));
			table.addCell(createCell(ctr.getEmpresa().getNome(), Element.ALIGN_CENTER));

			table.addCell(createCell(""));
			table.addCell(createCell(""));
			table.addCell(createCell(""));

			table.addCell(createCell("Testemunha :"));
			table.addCell(createCell(""));
			table.addCell(createCell("Testemunha :"));

			cell = createCell("");
			cell.setBorder(Rectangle.BOTTOM);
			table.addCell(cell);

			table.addCell(createCell(""));

			cell = createCell("");
			cell.setBorder(Rectangle.BOTTOM);
			table.addCell(cell);

			document.add(table);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private PdfPCell createCell(String string) {
		return createCell(string, Element.ALIGN_LEFT);
	}

	private PdfPCell createCell(String string, int align) {
		PdfPCell cell = new PdfPCell(new Phrase(string));
		cell.setBorder(Rectangle.NO_BORDER);

		cell.setHorizontalAlignment(align);
		cell.setMinimumHeight(20);
		return cell;
	}

}
