package br.com.wmw.MiniAplication.Controller.Validate;

public class CamposValidator {
	
	private String campo;
	private String mensagem;
	
	public CamposValidator(String campo, String mensagem) {
		this.campo = campo;
		this.mensagem = mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public String getMensagem() {
		return mensagem;
	}
	
}
