package com.maia.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.maia.cursomc.services.validation.ClienteInsert;

@ClienteInsert
public class ClienteNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Length(min = 5, max = 120)
	private String nome;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Email(message = "Email Ivalido")
	private String email;

	private String cpfOrCnpf;
	private Integer tipoPessoa;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String logradouro;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String numero;

	private String complemtno;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String bairro;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String cep;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String telefone1;

	private String telefone2;
	private String telefone3;

	private Integer cidadeId;

	public ClienteNewDTO() {
		// TODO Auto-generated constructor stub
	}

	// **Getters e Setters**//
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOrCnpf() {
		return cpfOrCnpf;
	}

	public void setCpfOrCnpf(String cpfOrCnpf) {
		this.cpfOrCnpf = cpfOrCnpf;
	}

	//Retorna Um Integer e não um TipoPessoa
	public Integer getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(Integer tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemtno() {
		return complemtno;
	}

	public void setComplemtno(String complemtno) {
		this.complemtno = complemtno;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

}
