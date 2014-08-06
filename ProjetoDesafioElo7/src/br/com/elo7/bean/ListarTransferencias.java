package br.com.elo7.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.elo7.bo.TransferenciaBO;
import br.com.elo7.to.Transferencia;

@ManagedBean
@ViewScoped
public class ListarTransferencias implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4266115044964767231L;
	private List<Transferencia> lista;
	
	@PostConstruct
	private void init(){
		TransferenciaBO transBO = new TransferenciaBO();
		lista = transBO.listarTransferencias();
	}


	public List<Transferencia> getLista() {
		return lista;
	}

	public void setLista(List<Transferencia> lista) {
		this.lista = lista;
	}
}
