package br.com.elo7.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.elo7.bo.TransferenciaBO;
import br.com.elo7.dao.TransferenciaDAO;
import br.com.elo7.dao.impl.TransferenciaDAOImpl;
import br.com.elo7.enums.TipoTransacao;
import br.com.elo7.to.Transferencia;

public class ConsoleView {

	private static TransferenciaBO tBO = new TransferenciaBO();
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("CLIENTE_ORACLE");
	private static EntityManager em = factory.createEntityManager();
	private static TransferenciaDAO tDAO = new TransferenciaDAOImpl(em);
	
	
	public static void main(String[] args) {
		
		agendarTransferencia();
		
		agendarTransferencia("06408-5", "04621-9", "15/10/2014", 252000, TipoTransacao.D);
		
		consultarTransferencias();
		
	}
	

	/**
	 * Método para agendar uma Tranferencia financeira
	 * @author Amanda Yuri
	 * @return void
	 * @see br.com.elo7.to.Transferencia
	 */
	private static void agendarTransferencia(){
		
		Transferencia trans = new Transferencia();
			trans.setContaOrigem("03480-1");
			trans.setContaDestino("09475-6");
			trans.setTipoTransacao(TipoTransacao.D);
			trans.setValor(18065.80F);
			
				Calendar data = Calendar.getInstance();
				data.set(2014, Calendar.SEPTEMBER, 1);
			trans.setDataAgendamento(data);
		
			float taxa = 0;
			
			switch (trans.getTipoTransacao()) {
			case A:
				taxa = calcularTaxaTipoA(trans);
				break;
			case B:
				taxa = calcularTaxaTipoB(trans);
				break;
			case C:
				taxa = calcularTaxaTipoC(trans);
				break;
			case D:
				taxa = calcularTaxaTipoD(trans);
				break;
			}
			
			trans.setVlTtaxa(taxa);
			
			try {
				tBO.agendarTransferencia(trans);
				System.out.println("** Transação Financeira agendado com sucesso **");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	private static void agendarTransferencia(String contaOrigem, String contaDestino, String dataAgendamento, float valorTrans, TipoTransacao tipoTransacao){
		
		Transferencia trans = new Transferencia();
			trans.setContaOrigem(contaOrigem);
			trans.setContaDestino(contaDestino);
			trans.setTipoTransacao(tipoTransacao);
			trans.setValor(valorTrans);
			
			//Converte a data em String para Calendar
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar data = Calendar.getInstance();
				data.setTime(sdf.parse(dataAgendamento));
				trans.setDataAgendamento(data);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		
				
			float taxa = 0;
			
			switch (trans.getTipoTransacao()) {
			case A:
				taxa = calcularTaxaTipoA(trans);
				break;
			case B:
				taxa = calcularTaxaTipoB(trans);
				break;
			case C:
				taxa = calcularTaxaTipoC(trans);
				break;
			case D:
				taxa = calcularTaxaTipoD(trans);
				break;
			}
			
			trans.setVlTtaxa(taxa);
			
			try {
				tBO.agendarTransferencia(trans);
				System.out.println("** Transação Financeira agendado com sucesso **");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void consultarTransferencias(){
		
		//List<Transferencia> lstTransferencia = tBO.listarTransferencias();
		List<Transferencia> lstTransferencia = tDAO.listarTodos();
		
		if(!lstTransferencia.isEmpty()){
			for (Transferencia trans : lstTransferencia) {
				System.out.println("\nConta Origem: " + trans.getContaOrigem());
				System.out.println("Conta Destino: " + trans.getContaDestino());
				System.out.println("Data de Agendamento: " + sdf.format(trans.getDataAgendamento().getTime()));
				System.out.println("Valor: R$" + trans.getValor());
				System.out.println("Transação do Tipo " + trans.getTipoTransacao() + " de R$" + trans.getVlTtaxa());
				
			}
		}else{
			System.out.println("\nNão há nenhum agendamento cadastrado.");
		}
	}
	
	/**
	 * Recebe data do agendamento da transferencia e pega a data do sistema
	 * para calcular a diferença de dias entre as duas datas
	 * @author Amanda Yuri
	 * @param dataAgendamento
	 * @return número de dias de diferença
	 */
	private static int calcularDiferencaDias(Calendar dataAgendamento){
		
		DateTime dtAtual = new DateTime();
		DateTime dtAgend = new DateTime(dataAgendamento);
		return Days.daysBetween(dtAtual, dtAgend).getDays();
	}
	
	/**
	 * Calcula o valor da taxa conforme o tipo de taxa A
	 * @author Amanda Yuri
	 * @param t (Objeto do tipo Transferencia)
	 * @return taxa
	 */
	private static float calcularTaxaTipoA(Transferencia t){
		return 2 + (t.getValor()*0.03F);
	}
	
	/**
	 * Calcula o valor da taxa conforme o tipo de taxa B
	 * @author Amanda Yuri
	 * @param t (Objeto do tipo Transferencia)
	 * @return taxa
	 */
	private static float calcularTaxaTipoB(Transferencia t){
		int dias = calcularDiferencaDias(t.getDataAgendamento());
		if(dias <= 30){
			return 10;
		}else{
			return 8;
		}
	}
	
	/**
	 * Calcula o valor da taxa conforme o tipo de taxa C
	 * @author Amanda Yuri
	 * @param t (Objeto do tipo Transferencia)
	 * @return taxa
	 */
	private static float calcularTaxaTipoC(Transferencia t){
		int dias = calcularDiferencaDias(t.getDataAgendamento());
		float taxa = 0;
		
		if(dias <= 5){
			taxa = t.getValor() * 0.083F;
		}
		if(dias > 5 && dias <= 10){
			taxa = t.getValor() * 0.074F;
		}
		if(dias > 10 && dias <= 15){
			taxa = t.getValor() * 0.067F;
		}
		if(dias > 15 && dias <= 20){
			taxa = t.getValor() * 0.054F;
		}
		if(dias > 20 && dias <= 25){
			taxa = t.getValor() * 0.043F;
		}
		if(dias > 25 && dias <= 30){
			taxa = t.getValor() * 0.021F;
		}
		if(dias > 30){
			taxa = t.getValor() * 0.012F;
		}
		
		return taxa;
	}
	
	/**
	 * Calcula o valor da taxa conforme o tipo de taxa D
	 * @author Amanda Yuri
	 * @param t (Objeto do tipo Transferencia)
	 * @return taxa
	 */
	private static float calcularTaxaTipoD(Transferencia t){
		float taxa = 0;
		
		if(t.getValor() <= 25000){
			taxa = calcularTaxaTipoA(t);
		}else{
			if(t.getValor() <= 120000){
				taxa = calcularTaxaTipoB(t);
			}else{
				if(t.getValor() > 120000){
					taxa = calcularTaxaTipoC(t);
				}
			}
		}
		
		return taxa;
	}

}
