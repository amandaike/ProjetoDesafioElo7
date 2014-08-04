package br.com.elo7.dao;

public interface DAO<T,K> {
	
	void inserir(T entidade);
	
	void alterar(T entidade);
	
	void excluir(K codigo);
	
	T buscarPorId(K codigo);
	
}
