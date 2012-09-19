package controle;

import java.util.HashMap;
import java.util.Vector;

public class Sorteia {

	public Vector opcoes=new Vector();
	private String palavra; 
	private int numero; 
	/**
	 * @param args
	 * @return 
	 */
public Sorteia(){
	this.criaDicionario();
	//this.puxaDado();
}

public void criaDicionario(){
	
	HashMap hp = new HashMap();
	hp.put("nome", "Victor");
	hp.put("significado", "Bonitasso!!!");
	opcoes.add(hp);
	
	hp = new HashMap();
	hp.put("nome", "B");
	hp.put("significado", "é verde");
	opcoes.add(hp);
	hp = new HashMap();
	hp.put("nome", "C");
	hp.put("significado", "é amarela");
	opcoes.add(hp);
	hp = new HashMap();
	hp.put("nome", "D");
	hp.put("significado", "letra D");
	opcoes.add(hp);
	hp = new HashMap();
	

}
private void puxaDado(){
	setNumero((int)(Math.random()*opcoes.size()));
	setPalavra(((HashMap)opcoes.elementAt(getNumero())).get("nome").toString());
}
public String puxaDadoExterno(int num){
	String dado;
	dado=((HashMap)opcoes.elementAt(num)).get("nome").toString();
	return dado;
}
public String puxaDescricaoExterno(int num){
	String dado;
	dado=((HashMap)opcoes.elementAt(num)).get("significado").toString();
	return dado;
}
public void setPalavra(String palavra) {
	this.palavra = palavra;
}

public String getPalavra() {
	return palavra;
}

private void setNumero(int numero) {
	this.numero = numero;
}

public int getNumero() {
	return numero;
}

}
