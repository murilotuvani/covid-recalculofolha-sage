package br.com.autogeral.sage.covid;

import java.util.Map;

public class CalculoSalario {

	private Map<Integer, Salario> salarios;

	public CalculoSalario(Map<Integer, Salario> salarios) {
		this.salarios = salarios;
		calcula();
	}

	private void calcula() {
		for(salarios.keySet())
	}

}
