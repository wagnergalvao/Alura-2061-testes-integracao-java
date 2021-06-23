package br.com.alura.leilao.dao.util.builder;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

public class LeilaoBuilder {

	private String nome;
	private BigDecimal valorInicial;
	private LocalDate dataInicio;
	private Usuario usuario;
	
	
	public LeilaoBuilder comNome(String nome) {
		this.nome = nome;
		return this;
	}

	public LeilaoBuilder comValorInicial(String valorInicial) {
		this.valorInicial = new BigDecimal(valorInicial);
		return this;
	}

	public LeilaoBuilder comDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
		return this;
	}
	
	public LeilaoBuilder comUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	public Leilao criar() {
		return new Leilao(nome, valorInicial, dataInicio, usuario);
	}

}
