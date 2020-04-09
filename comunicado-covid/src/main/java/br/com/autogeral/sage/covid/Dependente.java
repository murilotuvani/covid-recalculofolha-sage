package br.com.autogeral.sage.covid;

import java.util.Date;

public class Dependente {
	
	private Long codfun = null;
    private Integer ordem = null;
    private String nome = null;
    private String parentesco = null;
    private Date datanasc = null;
    private String nomemae = null;

    public Long getCodfun() {
        return codfun;
    }

    public void setCodfun(Long codfun) {
        this.codfun = codfun;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

    public String getNomemae() {
        return nomemae;
    }

    public void setNomemae(String nomemae) {
        this.nomemae = nomemae;
    }

}
