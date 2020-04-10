package br.com.autogeral.sage.covid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class CalculoSalarioTest {

	@Test
	public void testCalculoMedia() {

		Map<Integer, Salario> mapa = new TreeMap<>();
		mapa.put(202003, new Salario(0, 245, 202003, new BigDecimal(2545)));
		mapa.put(202002, new Salario(0, 245, 202002, new BigDecimal(2530)));
		mapa.put(202001, new Salario(0, 245, 202001, new BigDecimal(2500)));
		mapa.put(202912, new Salario(0, 245, 202912, new BigDecimal(2351)));
		mapa.put(202911, new Salario(0, 245, 202911, new BigDecimal(2200)));

		BigDecimal reducao = BigDecimal.valueOf(25);
		CalculoSalario cs = new CalculoSalario(reducao, mapa);

		BigDecimal media = BigDecimal.valueOf(2425.2).setScale(2, RoundingMode.HALF_UP);
		assertEquals(media, cs.getMedia());
		assertEquals(2, cs.getFaixa());

	}

	@Test
	public void testCalculoFaixa2() {
		Map<Integer, Salario> mapa = new TreeMap<>();
		mapa.put(202003, new Salario(0, 245, 202003, new BigDecimal(1750)));

		BigDecimal reducao = BigDecimal.valueOf(25);
		CalculoSalario cs = new CalculoSalario(reducao, mapa);

		BigDecimal media = BigDecimal.valueOf(1750).setScale(2, RoundingMode.HALF_UP);
		assertEquals(media, cs.getMedia());
		assertEquals(2, cs.getFaixa());
		assertTrue(BigDecimal.valueOf(1312.5).compareTo(cs.getSalarioEmpresa()) == 0);
		assertTrue(BigDecimal.valueOf(338.72).compareTo(cs.getSalarioGoverno()) == 0);
		assertTrue(BigDecimal.valueOf(1651.22).compareTo(cs.getSalarioTotal()) == 0);

	}
	
	@Test
	public void testCalculoFaixa3() {
		Map<Integer, Salario> mapa = new TreeMap<>();
		mapa.put(202003, new Salario(0, 245, 202003, new BigDecimal(3200)));

		BigDecimal reducao = BigDecimal.valueOf(25);
		CalculoSalario cs = new CalculoSalario(reducao, mapa);

		BigDecimal media = BigDecimal.valueOf(3200).setScale(2, RoundingMode.HALF_UP);
		assertEquals(media, cs.getMedia());
		assertEquals(3, cs.getFaixa());
		assertTrue(BigDecimal.valueOf(2400).compareTo(cs.getSalarioEmpresa()) == 0);
		assertTrue(BigDecimal.valueOf(453.26).compareTo(cs.getSalarioGoverno()) == 0);
		assertTrue(BigDecimal.valueOf(2853.26).compareTo(cs.getSalarioTotal()) == 0);

	}

}
