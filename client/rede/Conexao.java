package rede;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
 
 public class Conexao {
	 /*
	  * Decla��o das variaveis
	  */
	 private Integer porta;
	 private String ip="127.0.0.1";
	 private Socket socket = null; //Declaro o socket cliente
	 public DataOutputStream outToServer;
	 public BufferedReader inFromServer;
	 public Conexao(){

      
	 }
     public void startConexao() {
             

           
 
     
	 }
	 
	 
	 
	 /*
	  * Decla��o dos metodos
	  */
	 public void setPorta(Integer integer){
			this.porta=integer;
	 }
	 public void setIp(String novoValor){
			this.ip=novoValor;
	 }
	 public String getIp() {
		          return this.ip;
	 }
	 public Integer getPorta() {
         return porta;
     }
     public void setSocket(Socket s) {
		this.socket = s;
     }
     
     public Socket getSocket() {
		return socket;
     }
 }