package br.com.alura.leilao.dao.util.builder;

import br.com.alura.leilao.model.Usuario;

public class UsuarioBuilder {

	private String userName;
	private String emailAddress;
	private String password;
	
	public UsuarioBuilder comUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public UsuarioBuilder comEmail(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}

	public UsuarioBuilder comSenha(String password) {
		this.password = password;
		return this;
	}

	public Usuario criar() {
		return new Usuario(userName, emailAddress, password);
	}

}
