package br.com.elo7.dao;

import java.util.List;

import br.com.elo7.to.Transferencia;

public interface TransferenciaDAO extends DAO<Transferencia, Integer> {

	List<Transferencia> listarTodos();
	
}
