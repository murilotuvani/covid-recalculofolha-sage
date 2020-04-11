package br.com.autogeral.sage.covid;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class CalculoSalario {

	private int faixa = 0;
	private Funcionario funcionario;
	private BigDecimal salarioEmpresa = BigDecimal.ZERO;
	private BigDecimal salarioGoverno = BigDecimal.ZERO;
	private Map<Integer, Salario> salarios;
	private BigDecimal media = BigDecimal.ZERO;
	private final BigDecimal reducao;
	private final BigDecimal fatorReducao;
	private BigDecimal salarioTotal;
	private static final BigDecimal LIMITE_FAIXA_1 = BigDecimal.valueOf(1500);
	private static final BigDecimal LIMITE_FAIXA_2 = BigDecimal.valueOf(2666.29);

	public CalculoSalario(BigDecimal reducao, Funcionario funcionario, Map<Integer, Salario> salarios) {
		this.reducao = reducao;
		this.funcionario = funcionario;
		this.fatorReducao = reducao.divide(BigDecimal.valueOf(100));
		this.salarios = salarios;
		calcula();
	}

	private void calcula() {
//		for(salarios.keySet())
		BigDecimal soma = salarios.values().stream().map(s -> s.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
		if(funcionario.isAdicionalPericulosidade()) {
			soma = soma.multiply(BigDecimal.valueOf(1.3));
		}
		BigDecimal divisor = new BigDecimal(salarios.keySet().size());
		//this.media  = soma.divide(divisor, RoundingMode.HALF_UP);
		
		this.media = soma.divide(divisor, 2, RoundingMode.HALF_UP);
		//this.media.setScale(2, RoundingMode.HALF_UP);
		this.salarioEmpresa = this.media.multiply(BigDecimal.ONE.subtract(fatorReducao));
		if (this.media.compareTo(LIMITE_FAIXA_1) <= 0) {
			this.faixa = 1;
			this.salarioGoverno = this.media.multiply(fatorReducao)
					.multiply(BigDecimal.valueOf(0.8))
					.setScale(2, RoundingMode.HALF_UP);
		} else if (this.media.compareTo(LIMITE_FAIXA_2) <= 0) {
			this.faixa = 2;
			BigDecimal valorLimite = BigDecimal.valueOf(1599.61);
			BigDecimal diferenca = this.media.subtract(valorLimite);
			BigDecimal ajuda = diferenca.multiply(BigDecimal.valueOf(0.5))
					.add(BigDecimal.valueOf(1279.69))
					.multiply(fatorReducao)
					.setScale(2, RoundingMode.HALF_UP);; 
			this.salarioGoverno = ajuda;
		} else {
			this.faixa = 3;
			this.salarioGoverno = BigDecimal.valueOf(453.26);
		}
		this.salarioTotal = this.salarioEmpresa.add(this.salarioGoverno);
	}

	public BigDecimal getMedia() {
		return this.media;
	}

	public int getFaixa() {
		return faixa;
	}

	public BigDecimal getSalarioEmpresa() {
		return salarioEmpresa;
	}

	public BigDecimal getSalarioGoverno() {
		return salarioGoverno;
	}

	public Map<Integer, Salario> getSalarios() {
		return salarios;
	}

	public BigDecimal getReducao() {
		return reducao;
	}

	public BigDecimal getFatorReducao() {
		return fatorReducao;
	}

	public BigDecimal getSalarioTotal() {
		return salarioTotal;
	}

}
