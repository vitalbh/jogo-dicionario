package controle;

public class Ranking {
private Integer[] idJogador;
private Integer[] pontos;
public String mensagem;
Controle controle = new Controle();
public Ranking(){
	
	
}

public boolean verificaGanhador(){
	int pos;
	boolean teste = false;
	String nome1=controle.getNomeJogador();
	for(pos=0;pos<3;pos++){
	
		if(this.getNome(pos).equalsIgnoreCase(nome1)){
			if(this.getPontos(pos)==Integer.valueOf(controle.getPontuacaoMaxima())){
				teste= true;
			}else{
				teste=false;
			}	
		}
	}
	return teste;
}


private String getNome(int pos) {
	// TODO Stub de m�todo gerado automaticamente
	return null;
}

public void setPontos(Integer pontos, int pos) {
	this.pontos[pos] = pontos;
}

public Integer getPontos(int pos) {
	return pontos[pos];
}

public void setIdJogador(Integer[] idJogador) {
	this.idJogador = idJogador;
}

public Integer[] getIdJogador() {
	return idJogador;
}
	
	
	
}
