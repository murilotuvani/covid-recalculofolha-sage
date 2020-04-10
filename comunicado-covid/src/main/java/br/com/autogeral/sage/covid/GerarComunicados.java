package br.com.autogeral.sage.covid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GerarComunicados {

	public static void main(String a[]) {
		try {
			ContratoGerador cg = new ContratoGerador();
			GerarComunicados i = new GerarComunicados();
			for (Empresa empresa : i.getEmpresas()) {
				Map<Long, Funcionario> fs = i.buscarFuncionarios(empresa);
//			Map<Long, List<Dependente>> ds = i.buscarDependententes(fs);
//			Map<Long, Map<Integer, Salario>> mapaSalarios = i.buscarSalarios(fs);
				for (Funcionario f : fs.values()) {
					Map<Integer, Salario> salarios = i.buscarSalarios(empresa, f);

					XSSFWorkbook workbook = new XSSFWorkbook();
					i.criarPlanilhaSalarios(workbook, salarios);
//			XSSFSheet dependentes = workbook.createSheet("Dependentes");

					String nome = f.getNome().replace(" ", "").trim();
					File file = new File(nome + ".xlsx");
					try (FileOutputStream fos = new FileOutputStream(file)) {
						workbook.write(fos);
						fos.flush();
					}
					
					
					file = new File(nome + ".pdf");
					Contrato ctr = new Contrato();
					ctr.setEmpresa(empresa);
					ctr.setFuncionario(f);
					ctr.setDias(90);
					cg.gerar(ctr, file);
					
				}
			}
		} catch (SQLException | IOException ex) {
			Logger.getLogger(GerarComunicados.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private List<Empresa> getEmpresas() {
		List<Empresa> empresas = new ArrayList<>();
		Empresa emp = new Empresa();

		String args[][] = new String[][] { { "1", "05.437.537/0001-37", "Av. Dr. Octaviano Pereira Mendes, 1333 - Itu/SP", "Itu" },
				{ "2", "05.437.537/0002-18", "Av. Dom Pedro II, 1090 - Salto/SP", "Salto" },
				{ "3", "05.437.537/0003-07", "Av. Dr. Armando Pannunzio, 225 - Sorocaba/SP", "Sorocaba" },
				{ "4", "05.437.537/0004-80", "Av. Caetano Ruggieri, 3000 - Itu/SP", "Itu" },
				{ "5", "05.437.537/0005-60", "Av. Francisco de Paula Leite, 160 - Indaiatuba/SP", "Indaiatuba" },
				{ "6", "05.437.537/0006-41", "Av. Dr. Antonio Pires de Almeida, 630 - Porto Feliz/SP", "Porto Feliz" },
				{ "8", "05.437.537/0008-03", "Rua Amazonas, 137 - Cabreúva/SP", "Cabreúva" },
				{ "9", "05.437.537/0009-94", "Rua Onze de Agosto, 1815 - Tatuí/SP", "Tatuí" } };
		for(int i=0;i<9;i++) {
			emp.setCodigo(Integer.parseInt(args[i][0]));
			emp.setNome("Auto Geral Autopeças LTDA");
			emp.setCnpj(args[i][1]);
			emp.setEndereco(args[i][2]);
			emp.setCidade(args[i][3]);
			emp.setRepresentanteNome("Murilo de Moraes Tuvani");
			emp.setRepresentanteCpf("279.339.928-03");
			empresas.add(emp);
		}
		

		return empresas;
	}

	private Map<Long, Funcionario> buscarFuncionarios(Empresa empresa) throws SQLException {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setMinimumIntegerDigits(4);
		nf.setMaximumIntegerDigits(4);
		nf.setGroupingUsed(false);
		String bd = "f" + nf.format(empresa.getCodigo());
		String query = "Select A.codfun, A.nome, A.funcao, A.endereco, A.numero, A.comple, A.bairro, A.cid, A.uf\n"
				+ "     , A.telefone, A.cep, A.nasc, A.sexo, A.ecivil, A.email, A.depto, A.cbo\n"
				+ "     , A.numdep, A.cpf, A.pis, A.numrg, A.orgrg, A.mae\n"
				+ "  From " + bd + ".func As A\n"
				+ " Where A.sit='f';";
		System.out.println(query);
		Map<Long, Funcionario> map;
		try (Connection conn = ConnectionFactory.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			map = new HashMap<>();
			while (rs.next()) {
				Funcionario f = new Funcionario();
				preeche(f, rs);
				map.put(f.getCodfun(), f);
			}
		}
		return map;
	}

	private Map<Long, List<Dependente>> buscarDependententes(Map<Long, Funcionario> fs) throws SQLException {
		Map<Long, List<Dependente>> map = new HashMap<>();
		// '000010','000018'
		String query = "Select A.codfun, A.ordem, A.nome, A.datanasc, A.parentesco, A.datanasc, A.nomemae\n"
				+ "   From f0004.parentes As A";
		try (Connection conn = ConnectionFactory.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Dependente d = new Dependente();
				preeche(d, rs);

				List<Dependente> list = null;
				if (map.containsKey(d.getCodfun())) {
					list = map.get(d.getCodfun());
				} else {
					list = new ArrayList<>();
					map.put(d.getCodfun(), list);
				}
				list.add(d);
			}
		}
		return map;
	}

//	private Map<Long, Map<Integer, Salario>> buscarSalarios(Map<Long, Funcionario> fs) throws SQLException {
//		String query = "Select S.codeven, S.codfun, S.anomes, S.valor\n" + "  From f0004.salarios As S\n"
//				+ " Where S.codfun='000042'\n" +
//				// " -- AND S.anomes='202003'\n" +
//				"   AND S.codeven='0000'\n" + "  order by S.anomes desc\n" + "  limit 6";
//
//		Map<Long, Map<Integer, Salario>> map = new TreeMap<>();
//		try (Connection conn = ConnectionFactory.getConnection();
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(query)) {
//			while (rs.next()) {
//				Salario d = new Salario();
//				preenche(d, rs);
//
//				Map<Integer, Salario> mapaSalarios = null;
//				if (map.containsKey(d.getCodfun())) {
//					mapaSalarios = map.get(d.getCodfun());
//				} else {
//					mapaSalarios = new TreeMap<>();
//					map.put(d.getCodfun(), mapaSalarios);
//				}
//				mapaSalarios.put(d.getAnoMes(), d);
//			}
//		}
//		return map;
//	}

	private Map<Integer, Salario> buscarSalarios(Empresa empresa, Funcionario f) throws SQLException {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(4);
		nf.setMaximumIntegerDigits(4);
		String bd = "f" + nf.format(empresa.getCodigo());
		
		nf.setMinimumIntegerDigits(6);
		nf.setMaximumIntegerDigits(6);
		String codfun = nf.format(f.getCodfun()); 
		
		String query = "Select S.codeven, S.codfun, S.anomes, S.valor\n" +
				"  From " + bd + ".salarios As S\n" +
				" Where S.codfun='" + codfun + "'\n" +
				// " -- AND S.anomes='202003'\n" +
				"   AND S.codeven='0000'\n" +
				"  order by S.anomes desc\n" +
				"  limit 6";

		// Map<Long, Map<Integer, Salario>> map = new TreeMap<>();

		Map<Integer, Salario> map = new TreeMap<>();
		try (Connection conn = ConnectionFactory.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Salario d = new Salario();
				preenche(d, rs);
				map.put(d.getAnoMes(), d);
			}
		}
		return map;
	}

	private void preeche(Funcionario f, ResultSet rs) throws SQLException {
		f.setCodfun(rs.getLong("codfun"));
		f.setNome(rs.getString("nome").trim());
		f.setFuncao(rs.getString("funcao").trim());
		f.setEndereco(rs.getString("endereco").trim());
		f.setNumero(rs.getString("numero").trim());
		f.setComple(rs.getString("comple".trim()));
		f.setBairro(rs.getString("bairro").trim());
		f.setCid(rs.getString("cid").trim());
		f.setUf(rs.getString("uf").trim());
		f.setTelefone(rs.getString("telefone").trim());
		f.setCep(rs.getString("cep").trim());
		Date nasc = rs.getDate("nasc");
		f.setNasc(nasc);
		f.setSexo(rs.getString("sexo"));
		f.setEcivil(rs.getString("ecivil"));
		f.setEmail(rs.getString("email"));
		f.setDepto(rs.getString("depto"));
		f.setCbo(rs.getString("cbo"));
		f.setNumdep(rs.getString("numdep"));
		f.setCpf(rs.getString("cpf").trim());
		f.setPis(rs.getString("pis"));
		f.setNumrg(rs.getString("numrg").trim());
		f.setOrgrg(rs.getString("orgrg".trim()));
		f.setMae(rs.getString("mae").trim());
	}

	private void preeche(Dependente d, ResultSet rs) throws SQLException {
		d.setCodfun(rs.getLong("codfun"));
		d.setOrdem(rs.getInt("ordem"));
		d.setNome(rs.getString("nome").trim());
		Date nasc = rs.getDate("datanasc");
		d.setDatanasc(nasc);
		d.setParentesco(rs.getString("parentesco").trim());
		d.setNomemae(rs.getString("nomemae").trim());
	}

	private void preenche(Salario s, ResultSet rs) throws SQLException {
		s.setCodfun(rs.getLong("codfun"));
		s.setAnoMes(rs.getInt("anomes"));
		s.setCodeven(rs.getInt("codeven"));
		s.setValor(rs.getBigDecimal("valor"));

	}
	
	public void criarPlanilhaSalarios(XSSFWorkbook workbook, Map<Integer, Salario> salarios) {
		XSSFSheet sheet = workbook.createSheet("Salarios");
		
		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum++);		
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("Mes");
		cell = row.createCell(1);
		cell.setCellValue("Salario");
		
		BigDecimal soma = BigDecimal.ZERO;
		
		for(Integer mes:salarios.keySet()) {
			Salario salario = salarios.get(mes);
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(mes);
			
			cell = row.createCell(1);
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(salario.getValor().doubleValue());
			
			
		}
	}

}
