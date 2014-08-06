package br.com.elo7.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;

import br.com.elo7.bo.TransferenciaBO;
import br.com.elo7.to.Transferencia;

@ManagedBean
@ViewScoped
public class TransferenciaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6344062393139891912L;
	private Transferencia transferencia;
	private Date dataAgend;
	TransferenciaBO transBO;
	
	@PostConstruct
	private void init(){
		transferencia = new Transferencia();
		dataAgend = new Date();
		transferencia.setDataAgendamento(Calendar.getInstance());
	}
	
	public void agendarTransferencia(){
		transBO = new TransferenciaBO();
		
		try {
			transferencia.setDataAgendamento(transBO.converterDataAgendamento(dataAgend));
			calcularValorTaxa();
			transBO.cadastrar(transferencia);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Agendamento cadastrado", "Cadastrado com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void calcularValorTaxa(){
		transBO = new TransferenciaBO();
		
		transferencia.setDataAgendamento(transBO.converterDataAgendamento(dataAgend));
		float taxa = transBO.calcularTaxa(transferencia);
		transferencia.setVlTaxa(taxa);
	}
	
	public void validaFormatoConta(FacesContext context, UIComponent component, Object value) throws ValidatorException{ 
		String conta = value.toString(); 
		if (!conta.matches("\\d{5,5}-\\d{1,1}")){ 
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato da Conta digitada é inválida", "Formato inválida"));
		}
	}

	public Transferencia getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	public Date getDataAgend() {
		return dataAgend;
	}

	public void setDataAgend(Date dataAgend) {
		this.dataAgend = dataAgend;
	}
	
}
