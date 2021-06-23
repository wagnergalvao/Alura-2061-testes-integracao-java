package br.com.alura.leilao.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.alura.leilao.dao.util.builder.UsuarioBuilder;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {

	private EntityManager em;
	private UsuarioDao dao;
	private Usuario usuario;
	private Faker fake = new Faker(new Locale("pt-BR"));
	private String _userName;

	@BeforeEach
	public void setup() {
		_userName = fake.name().username();
		usuario = new UsuarioBuilder()
				.comUserName(_userName)
				.comEmail(fake.internet().emailAddress(_userName))
				.comSenha(fake.internet().password(true))
				.criar();

		em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(usuario);
		this.dao = new UsuarioDao(em);
	}

	@AfterEach
	public void tearDown() {
		em.getTransaction().getRollbackOnly();
	}

	@Test
	void deveEncontrarUsuarioCadastrado() {
		Usuario _retorno = dao.buscarPorUsername(usuario.getNome());

		assertNotNull(_retorno);
		assertEquals(usuario.getNome(), _retorno.getNome());
		assertEquals(usuario.getEmail(), _retorno.getEmail());
		assertEquals(usuario.getSenha(), _retorno.getSenha());
	}

	@Test
	void naoDeveEncontrarUsuarioNaoCadastrado() {
		assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(fake.name().username()));
	}

	@Test
	void deveExcluirUsuarioCadastrado() {
		dao.deletar(usuario);
		assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));
	}
}
