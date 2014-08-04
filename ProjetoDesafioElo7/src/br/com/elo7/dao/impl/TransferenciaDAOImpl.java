package br.com.elo7.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.elo7.dao.TransferenciaDAO;
import br.com.elo7.to.Transferencia;

public class TransferenciaDAOImpl extends DAOImpl<Transferencia, Integer> implements TransferenciaDAO {

	public TransferenciaDAOImpl(EntityManager em) {
		super(em);
	}

	@Override
	public List<Transferencia> listarTodos() {
		TypedQuery<Transferencia> query = em.createQuery("FROM Transferencia", Transferencia.class);
		return query.getResultList();
	}

}
