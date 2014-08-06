package br.com.elo7.to;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.elo7.enums.TipoTransacao;

@Entity
@Table(name="P_ELO7_TRANSFERENCIA")
@SequenceGenerator(name="sq_transferencia", sequenceName="SQ_TRANSFERENCIA", allocationSize=1)
public class Transferencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -802949410237818833L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sq_transferencia")
	private int codigo;
	
	@Column(name="ds_conta_origem", nullable=false)
	private String contaOrigem;
	
	@Column(name="ds_conta_destino", nullable=false)
	private String contaDestino;
	
	@Column(name="vl_transferencia", nullable=false)
	private float valor;
	
	@Column(name="vl_taxa", nullable=false)
	private float vlTaxa;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataAgendamento;
	
	@Column(name="tp_transacao")
	private TipoTransacao tipoTransacao;

	public Transferencia() {
	}

	public Transferencia(int codigo, String contaOrigem, String contaDestino,
			float valor, float vlTaxa, Calendar dataAgendamento,
			TipoTransacao tipoTransacao) {
		super();
		this.codigo = codigo;
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
		this.valor = valor;
		this.vlTaxa = vlTaxa;
		this.dataAgendamento = dataAgendamento;
		this.tipoTransacao = tipoTransacao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public String getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getVlTaxa() {
		return vlTaxa;
	}

	public void setVlTaxa(float vlTaxa) {
		this.vlTaxa = vlTaxa;
	}

	public Calendar getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Calendar dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

}
