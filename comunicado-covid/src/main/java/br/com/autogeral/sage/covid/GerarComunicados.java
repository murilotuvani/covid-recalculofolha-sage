package br.com.autogeral.sage.covid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
					XSSFSheet funcionarios = workbook.createSheet("Salarios");
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
		Empresa empresa1 = new Empresa();
		empresa1.setCodigo(1);
		empresa1.setNome("Empresa Importadora de Carros Foda LTDA");
		empresa1.setCidade("São Paulo");
		empresa1.setCnpj("00.000.002/0002-99");
		empresa1.setEndereco("Av. Colômbia, 999 - São Paulo/SP");
		empresa1.setRepresentanteNome("Milionário");
		empresa1.setRepresentanteCpf("111.111.111-11");

		Empresa empresa2 = new Empresa();
		empresa2.setCodigo(2);
		empresa2.setNome("Empresa Brasileira de Chopp");
		empresa2.setCidade("Petrópolis");
		empresa2.setCnpj("00.000.001/0002-99");
		empresa2.setEndereco("Perto de Dom Pedro II");
		empresa2.setRepresentanteNome("Bilionário");
		empresa2.setRepresentanteCpf("444.444.444-88");

		return Arrays.asList(empresa1, empresa2);
	}

	private Map<Long, Funcionario> buscarFuncionarios(Empresa empresa) throws SQLException {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setMinimumIntegerDigits(4);
		nf.setMaximumIntegerDigits(4);
		String bd = "f" + nf.format(empresa.getCodigo());
		String query = "Select A.codfun, A.nome, A.funcao, A.endereco, A.numero, A.comple, A.bairro, A.cid, A.uf\n"
				+ "     , A.telefone, A.cep, A.nasc, A.sexo, A.ecivil, A.email, A.depto, A.cbo\n"
				+ "     , A.numdep, A.cpf, A.pis, A.numrg, A.orgrg, A.mae\n" + "  From " + bd + ".func As A\n"
				+ " Where A.sit='f';";
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
		f.setNome(rs.getString("nome"));
		f.setFuncao(rs.getString("funcao"));
		f.setEndereco(rs.getString("endereco"));
		f.setNumero(rs.getString("numero"));
		f.setComple(rs.getString("comple"));
		f.setBairro(rs.getString("bairro"));
		f.setCid(rs.getString("cid"));
		f.setUf(rs.getString("uf"));
		f.setTelefone(rs.getString("telefone"));
		f.setCep(rs.getString("cep"));
		Date nasc = rs.getDate("nasc");
		f.setNasc(nasc);
		f.setSexo(rs.getString("sexo"));
		f.setEcivil(rs.getString("ecivil"));
		f.setEmail(rs.getString("email"));
		f.setDepto(rs.getString("depto"));
		f.setCbo(rs.getString("cbo"));
		f.setNumdep(rs.getString("numdep"));
		f.setCpf(rs.getString("cpf"));
		f.setPis(rs.getString("pis"));
		f.setNumrg(rs.getString("numrg"));
		f.setOrgrg(rs.getString("orgrg"));
		f.setMae(rs.getString("mae"));
	}

	private void preeche(Dependente d, ResultSet rs) throws SQLException {
		d.setCodfun(rs.getLong("codfun"));
		d.setOrdem(rs.getInt("ordem"));
		d.setNome(rs.getString("nome"));
		Date nasc = rs.getDate("datanasc");
		d.setDatanasc(nasc);
		d.setParentesco(rs.getString("parentesco"));
		d.setNomemae(rs.getString("nomemae"));
	}

	private void preenche(Salario s, ResultSet rs) throws SQLException {
		s.setCodfun(rs.getLong("codfun"));
		s.setAnoMes(rs.getInt("anomes"));
		s.setCodeven(rs.getInt("codeven"));
		s.setValor(rs.getBigDecimal("valor"));

	}

}
