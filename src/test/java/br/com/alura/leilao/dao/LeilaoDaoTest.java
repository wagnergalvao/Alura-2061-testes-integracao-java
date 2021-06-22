package br.com.alura.leilao.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class LeilaoDaoTest {

	private EntityManager em;
	private LeilaoDao dao;
	private Usuario usuario;
	private Faker fake = new Faker(new Locale("pt-BR"));
	private String _userName;
	private Leilao leilao;
	private Leilao leilaoSalvo;

	@BeforeEach
	public void setup() {
		_userName = fake.name().username();
		usuario = new Usuario(_userName, fake.internet().emailAddress(_userName), fake.internet().password(true));

		em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(usuario);
		this.dao = new LeilaoDao(em);
		leilao = new Leilao(fake.commerce().productName(),
				new BigDecimal(fake.commerce().price(100, 1000).replace(",", ".")), LocalDate.now(), usuario);
		leilao = dao.salvar(leilao);
		leilaoSalvo = dao.buscarPorId(leilao.getId());
	}

	@AfterEach
	public void tearDown() {
		em.getTransaction().getRollbackOnly();
	}

	@Test
	void deveCadastrarUmLelilao() {
		assertNotNull(leilaoSalvo);
		assertEquals(leilao.getId(), leilaoSalvo.getId());
		assertEquals(leilao.getUsuario(), leilaoSalvo.getUsuario());
		assertEquals(leilao.getNome(), leilaoSalvo.getNome());
		assertEquals(leilao.getValorInicial(), leilaoSalvo.getValorInicial());
		assertEquals(leilao.getDataAbertura(), leilaoSalvo.getDataAbertura());
	}

	@Test
	void deveAtualizarUmLelilao() {
		leilao.setNome(leilao.getNome() + " no estado");
		leilao.setValorInicial(leilao.getValorInicial().multiply(new BigDecimal(0.75)));
		leilao.setDataAbertura(leilao.getDataAbertura().plusDays(7));
		leilao = dao.salvar(leilao);
		assertNotNull(leilaoSalvo);
		assertEquals(leilao.getId(), leilaoSalvo.getId());
		assertEquals(leilao.getUsuario(), leilaoSalvo.getUsuario());
		assertEquals(leilao.getNome(), leilaoSalvo.getNome());
		assertEquals(leilao.getValorInicial(), leilaoSalvo.getValorInicial());
		assertEquals(leilao.getDataAbertura(), leilaoSalvo.getDataAbertura());
	}
}
