package br.com.elo7.dao.impl;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import br.com.elo7.dao.DAO;

public abstract class DAOImpl<T,K> implements DAO<T,K>{

	protected EntityManager em;
	
	private Class<T> entityClass;
	
	@SuppressWarnings("all")
	public DAOImpl(EntityManager em){
		this.em = em;
		this.entityClass = (Class<T>) 
			((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public void inserir(T entidade) {
		em.getTransaction().begin();
		em.persist(entidade);
		em.getTransaction().commit();
	}

	@Override
	public void alterar(T entidade) {
		em.getTransaction().begin();
		em.merge(entidade);
		em.getTransaction().commit();
	}

	@Override
	public void excluir(K codigo) {
		T entidade = buscarPorId(codigo);
		em.getTransaction().begin();
		em.remove(entidade);
		em.getTransaction().commit();
	}

	@Override
	public T buscarPorId(K codigo) {
		return em.find(entityClass, codigo);
	}

}
