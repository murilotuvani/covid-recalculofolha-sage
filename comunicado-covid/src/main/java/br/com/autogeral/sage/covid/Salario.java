package br.com.autogeral.sage.covid;

import java.math.BigDecimal;

public class Salario {

	private int codeven;
	private long codfun;
	private int anoMes;
	private BigDecimal valor = BigDecimal.ZERO;

	public int getCodeven() {
		return codeven;
	}

	public void setCodeven(int codeven) {
		this.codeven = codeven;
	}

	public long getCodfun() {
		return codfun;
	}

	public void setCodfun(long codfun) {
		this.codfun = codfun;
	}

	public int getAnoMes() {
		return anoMes;
	}

	public void setAnoMes(int anomes) {
		this.anoMes = anomes;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Salario [codeven=" + codeven + ", codfun=" + codfun + ", anomes=" + anoMes + ", valor=" + valor + "]";
	}

}
