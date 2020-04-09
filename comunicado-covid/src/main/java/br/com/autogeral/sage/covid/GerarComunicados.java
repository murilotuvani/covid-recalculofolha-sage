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
import java.util.ArrayList;
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
			GerarComunicados i = new GerarComunicados();
			Map<Long, Funcionario> fs = i.buscarFuncionarios();
			Map<Long, List<Dependente>> ds = i.buscarDependententes(fs);
			Map<Long, Map<Integer, Salario>> mapaSalarios = i.buscarSalarios(fs);

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet funcionarios = workbook.createSheet("Funcionarios");
			XSSFSheet dependentes = workbook.createSheet("Dependentes");

			File file = new File("relatoiro.xlsx");
			try (FileOutputStream fos = new FileOutputStream(file)) {
				workbook.write(fos);
				fos.flush();
			}
		} catch (SQLException | IOException ex) {
			Logger.getLogger(GerarComunicados.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private Map<Long, Funcionario> buscarFuncionarios() throws SQLException {
		String query = "Select A.codfun, A.nome, A.funcao, A.endereco, A.numero, A.comple, A.bairro, A.cid, A.uf\n"
				+ "     , A.telefone, A.cep, A.nasc, A.sexo, A.ecivil, A.email, A.depto, A.cbo\n"
				+ "     , A.numdep, A.cpf, A.pis, A.numrg, A.orgrg, A.mae\n" + "  From f0004.func As A\n"
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

	private Map<Long, Map<Integer, Salario>> buscarSalarios(Map<Long, Funcionario> fs) throws SQLException {
		String query = "Select S.codeven, S.codfun, S.anomes, S.valor\n" + "  From f0004.salarios As S\n"
				+ " Where S.codfun='000042'\n" +
				// " -- AND S.anomes='202003'\n" +
				"   AND S.codeven='0000'\n" + "  order by S.anomes desc\n" + "  limit 6";

		Map<Long, Map<Integer, Salario>> map = new TreeMap<>();
		try (Connection conn = ConnectionFactory.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Salario d = new Salario();
				preenche(d, rs);

				Map<Integer, Salario> mapaSalarios = null;
				if (map.containsKey(d.getCodfun())) {
					mapaSalarios = map.get(d.getCodfun());
				} else {
					mapaSalarios = new TreeMap<>();
					map.put(d.getCodfun(), mapaSalarios);
				}
				mapaSalarios.put(d.getAnoMes(), d);
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
