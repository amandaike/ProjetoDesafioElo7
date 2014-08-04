package br.com.elo7.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import org.joda.time.DateTime;

import br.com.elo7.bo.TransferenciaBO;
import br.com.elo7.to.Transferencia;

@ManagedBean
@ViewScoped
public class TransferenciaBean implements Serializable {

	private Transferencia transferencia;
	private Date dataAgend;
	
	@PostConstruct
	private void init(){
		transferencia = new Transferencia();
		transferencia.setDataAgendamento(Calendar.getInstance());
	}
	
	public String agendarTransferencia(){
		TransferenciaBO transBO = new TransferenciaBO();
		
			
		try {
			transferencia.setDataAgendamento(transBO.converterDataAgendamento(dataAgend));
			calcularValorTaxa();
			transBO.agendarTransferencia(transferencia);
			FacesContext context = FacesContext.getCurrentInstance();
			String mensagem =  "Agendamento realizado com sucesso";
			
			FacesMessage msg = new FacesMessage(mensagem);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "lista-agendamento";
	}
	
	public void calcularValorTaxa(){
		TransferenciaBO transBO = new TransferenciaBO();
		
		transferencia.setDataAgendamento(transBO.converterDataAgendamento(dataAgend));
		float taxa = transBO.calcularTaxa(transferencia);
		transferencia.setVlTtaxa(taxa);
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
