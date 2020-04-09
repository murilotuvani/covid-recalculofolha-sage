package br.com.autogeral.sage.covid;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Funcionario {

private static final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

    private Long codfun = null;
    private String nome = null;
    private String funcao = null;
    private String endereco = null;
    private String numero = null;
    private String comple = null;
    private String bairro = null;
    private String cid = null;
    private String uf = null;
    private String telefone = null;
    private String cep = null;
    private Date nasc = null;
    private String sexo = null;
    private String ecivil = null;
    private String email = null;
    private String depto = null;
    private String cbo = null;
    private String numdep = null;
    private String cpf = null;
    private String pis = null;
    private String numrg = null;
    private String orgrg = null;
    private String mae = null;

    public Funcionario() {
        
    }
    
    public Funcionario(String cpf, String pis, String nome, Date nasc) {
        this.cpf = clear(cpf);
        this.pis = clear(pis);
        nome = clear(nome);
        if (nome != null && nome.length() > 70) {
            this.nome = nome.substring(0, 70);
        } else {
            this.nome = nome;
        }
        this.nasc = nasc;
    }

    public Long getCodfun() {
        return codfun;
    }

    public void setCodfun(Long codfun) {
        this.codfun = codfun;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComple() {
        return comple;
    }

    public void setComple(String comple) {
        this.comple = comple;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Date getNasc() {
        return nasc;
    }

    public void setNasc(Date nasc) {
        this.nasc = nasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEcivil() {
        return ecivil;
    }

    public void setEcivil(String ecivil) {
        this.ecivil = ecivil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getNumdep() {
        return numdep;
    }

    public void setNumdep(String numdep) {
        this.numdep = numdep;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public String getNumrg() {
        return numrg;
    }

    public void setNumrg(String numrg) {
        this.numrg = numrg;
    }

    public String getOrgrg() {
        return orgrg;
    }

    public void setOrgrg(String orgrg) {
        this.orgrg = orgrg;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }
    
    @Override
    public String toString() {
        //return "Funcionario{" + "cpf=" + cpf + ", pis=" + pis + ", nome=" + nome + ", nasc=" + nasc + '}';
        return cpf + ";" + pis + ";" + nome + ";" + sdf.format(nasc);
    }

    private String clear(String cpf) {
        return (cpf != null ? cpf.trim() : "")
               .replace(".", "").replace("-", "").replace("/", "");
    }
}
