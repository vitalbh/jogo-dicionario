package controle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import controle.Timer;
import gui.Gui;
public class Controle {

	/**
	 * @Autor Victor Hugo Felix
	 * @Data 18/10/2007
	 * Classe que faz a manipula��o e controle das entradas de dados.
	 */

	private String nomeJogador;
	private int idJogador;
	private Integer pontuacao;
	private String significado;
	private String numIp;  //  @jve:decl-index=0:
	private Integer porta;
	private int idPalavra;
	private int pontuacaoMaxima;
	
	
	public Controle(){
		this.pontuacaoMaxima=30;
		
	}
	
	
	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}




	public String getNomeJogador() {
		return nomeJogador;
	}




	public void setSignificado(String significado) {
		this.significado = significado;
	}




	public String getSignificado() {
		return significado;
	}
	
	public void setNumIp(String numip) {
		this.numIp = numip;
	}

	public String getNumIp() {
		return numIp;
	}

	public void setPorta(Integer string) {
		this.porta = string;
	}

	public Integer getPorta() {
		return porta;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}


	public void setIdPalavra(int i) {
		this.idPalavra = i;
	}


	public int getIdPalavra() {
		return idPalavra;
	}


	public void setPontuacaoMaxima(int pontuacaoMaxima) {
		this.pontuacaoMaxima = pontuacaoMaxima;
	}


	public int getPontuacaoMaxima() {
		return pontuacaoMaxima;
	}


	public void setIdJogador(int idJogador) {
		this.idJogador = idJogador;
	}


	public int getIdJogador() {
		return idJogador;
	}
}
