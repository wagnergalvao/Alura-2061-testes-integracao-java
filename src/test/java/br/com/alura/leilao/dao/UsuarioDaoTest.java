package br.com.alura.leilao.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {

	private EntityManager em;
	private UsuarioDao dao;
	private Usuario usuario;
	private Usuario retorno;
	private Faker fake = new Faker(new Locale("pt-BR"));
	private String _userName;
	private String _userEmail;
	private String _userPassword;

	@Test
	void deveBuscarUsuarioPeloUserName() {
		_userName = fake.name().username();
		_userEmail = fake.internet().emailAddress(_userName);
		_userPassword = fake.internet().password(true);
		usuario = new Usuario(_userName,_userEmail, _userPassword);
		em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		this.dao = new UsuarioDao(em);
		retorno = dao.buscarPorUsername(usuario.getNome());
		assertNotNull(retorno);
		assertEquals(_userName, retorno.getNome());
		assertEquals(_userEmail, retorno.getEmail());
		assertEquals(_userPassword, retorno.getSenha());
	}

}
