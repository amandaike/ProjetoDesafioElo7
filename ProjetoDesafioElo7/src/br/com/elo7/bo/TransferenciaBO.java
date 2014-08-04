package br.com.elo7.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.elo7.dao.TransferenciaDAO;
import br.com.elo7.dao.impl.TransferenciaDAOImpl;
import br.com.elo7.enums.TipoTransacao;
import br.com.elo7.singleton.EntityManagerFactorySingleton;
import br.com.elo7.to.Transferencia;

public class TransferenciaBO {

	private static List<Transferencia> lstTransferencia = new ArrayList<Transferencia>();
	private static TransferenciaDAO tDAO;
	
	public TransferenciaBO(){
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		tDAO = new TransferenciaDAOImpl(em);
	}
	
	public void agendarTransferencia(Transferencia t) throws Exception{
		calcularTaxa(t);	
		//lstTransferencia.add(t);
		tDAO.inserir(t);
		
	}
		
	public List<Transferencia> listarTransferencias(){
		//return lstTransferencia;
		return tDAO.listarTodos();
	}
	
	public float calcularTaxa(Transferencia trans){
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
		
		return taxa;
	}
	
	/**
	 * Recebe data do agendamento da transferencia e pega a data do sistema
	 * para calcular a diferença de dias entre as duas datas
	 * @author Amanda Yuri
	 * @param dataAgendamento
	 * @return número de dias de diferença
	 */
	private int calcularDiferencaDias(Calendar dataAgendamento){
		
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
	private float calcularTaxaTipoA(Transferencia t){
		return 2 + (t.getValor()*0.03F);
	}
	
	/**
	 * Calcula o valor da taxa conforme o tipo de taxa B
	 * @author Amanda Yuri
	 * @param t (Objeto do tipo Transferencia)
	 * @return taxa
	 */
	private float calcularTaxaTipoB(Transferencia t){
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
	private float calcularTaxaTipoC(Transferencia t){
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
	private float calcularTaxaTipoD(Transferencia t){
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
	
	public Calendar converterDataAgendamento(Date dataAgendamento){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dt = sdf.format(dataAgendamento);
		Calendar data = Calendar.getInstance();
		try {
			data.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
}
