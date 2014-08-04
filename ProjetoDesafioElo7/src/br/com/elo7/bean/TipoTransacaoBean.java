package br.com.elo7.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import br.com.elo7.enums.TipoTransacao;

@ManagedBean
public class TipoTransacaoBean {

	public SelectItem[] getTipoTransacao(){
		SelectItem[] itens = new SelectItem[TipoTransacao.values().length];
		int i = 0;      
		for (TipoTransacao tipo: TipoTransacao.values()){
			itens[i++] = new SelectItem(tipo, tipo.getLabel());
		}      
		return itens;
	}
	
}
