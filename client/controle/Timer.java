package controle;
import javax.swing.JOptionPane;


public class Timer extends Controle{
	boolean roda=true;
	
	public Timer(){
		 
}
/*
public void rodaTimer(){
	(new Thread(){
        public void run(){
        	boolean processa = true;
             long tempoUltimaExecucao = System.currentTimeMillis();
             long intervaloAtualizacao = 1000;
             int cont = 0;
           
             while(roda==true){
                long tempoAgora = System.currentTimeMillis();
                 if( (tempoAgora-tempoUltimaExecucao) > intervaloAtualizacao){
                		if(processa){
                			cont++;
                   			processa = false;
                   			
                   			interfaceGrafica.percorre();
                   			
                   			processa = true;
                   			if(cont == 30){
                   				cont=0;
                   				JOptionPane.showMessageDialog(null, "30");
                   				roda=false;
                   			}
                    		}
                        tempoUltimaExecucao = System.currentTimeMillis();
                    }
                 	
                     //libera o processamento para outras coisas
                    Thread.yield();
                }//while                        }
            
           }}).start();
	
}*/
}