package br.com.elo7.enums;

public enum TipoTransacao {
	A("A"), B("B"), C("C"), D("D");
	
	private final String label;

	private TipoTransacao(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}
}
