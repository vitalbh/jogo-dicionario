	/***********PUC-MINAS*********************************
	************Trabalho Pratico - Redes - Noite - 2007 **********

	******************** Jogo do dicionario - Servidor****
	******************************************************/

	/* Headers necessarios */
	#include <stdio.h>
	#include <sys/time.h>
	#include <sys/types.h>
	#include <sys/socket.h>
	#include <netinet/in.h>
	#include <arpa/inet.h>
	#include <fcntl.h>
	#include <unistd.h>
	#include <errno.h>
	#include <string.h>
	#include <sys/signal.h>
	#include <stdlib.h>

	#define MAXCLIENTE 3    
	#define PORTA 6070

	/*********Estrutura**************************************************************/
	typedef struct SJogador
	{
	   char Nome[20];
	   char Palavra[20];
	   char Significado[400];
	   int Status;// 1 PRONTO //2    //3 Esperando Numero palavra //4 Lose // 5 Win // 6 Significado Pronto
	   int IdJogador;
	   int Pontos;
	   int Acerto;
	   int Client;
	}Jogador;
	Jogador SJogadors[MAXCLIENTE];
	int serv_socket;            
	int numeros[50];
	int numRodadas=1;
	int ale;
	int jog_servidor;
	char *mens1="Esperando significado...";
	char *mens2="Significados enviados para clientes...";
	char *mens3="pontos atualizados...";
	char *mens4="ranking enviado...";
	char *mens5="Nova rodada!";
	void tratasinal(int sinal);                // Em caso de um Ctrl-C, fecha o socket
	int writes(int fd, char *s);               // Envia uma corda p/ um socket
	int aguarda(int sockprincipal,int timeout);// Faz recepcao e retorna o socket
	int search_sjogador(int sock);             // Retorna o indice do cliente que possui o socket sock ou -1
	void reset_jogador(int i);
	void start_jogo();
	void stop_jogo();
	void geraNumeroRodada();
	void novaRodada();
	int geraInteiroAleatorio();
	void mandaRanking();
	void mandaSignificado();
	/***MAIN************************************************************************/
	int main(int argc, char **argv)
	{
	  int tam_cliaddr;            // tamanho da estrutura cliente
	  int clisock, which;         // novo socket criado e qual fd chegou dados
	  int dataread;               // quant de bytes recebidos
	  int cli;                    // indice do cliente que enviou algo
	  char buffer[1024];          // buffer de entrada
	  struct sockaddr_in servaddr;// estrutura de endereco do servidor
	  struct sockaddr_in cliaddr; // estrutura de endereco dos clientes que conectam
	  int i,j,CONTADOR_JG;
	  int tam_buf;
		
		
	  signal(SIGINT, &tratasinal);
	  srand(time(NULL));
	  for (i=0; i<MAXCLIENTE; i++) SJogadors[i].Client=-1;
			    
	  servaddr.sin_family      = AF_INET;
	  servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	  servaddr.sin_port        = htons(PORTA);
	   
	  CONTADOR_JG=0;
	  for (i=0;i<MAXCLIENTE;i++) {
	    reset_jogador(i);
	  }
	  
	  // Cria um socket TCP, familia INTERNET
	  if ((serv_socket = socket(AF_INET, SOCK_STREAM, 0)) < 0){
	    printf("ERRO(%d) criando socket: %s\n", errno, strerror(errno));
	    exit(1);
	  }
	  
	  // Atribui um endereco IP e porta ao socket criado
	  if(bind(serv_socket, (struct sockaddr *)&servaddr,sizeof(servaddr))<0){
	    printf("ERRO(%d) da chamada bind: %s\n", errno, strerror(errno));
	    exit(1);
	  }
	  
	  // Avisa ao sistema que e' um socket de entrada
	  listen(serv_socket,10);
	  printf("Aguardando conexoes.. \n");
						    
	  while(1){
	    which=aguarda(serv_socket, 10);
	    if (which==serv_socket)
	    {      
	      // Aguarda uma nova conexao no socket
	      tam_cliaddr = sizeof(cliaddr);
	      clisock=accept(serv_socket, (struct sockaddr *)&cliaddr, &tam_cliaddr);
	      if (clisock<0) {
	        fprintf(stderr,"ERRO(%d) no accept: %s\n", errno, strerror(errno));
	      } else {
	        printf("CONEXAO RECEBIDA DE %s:%d\n", inet_ntoa(cliaddr.sin_addr), cliaddr.sin_port);
	        for (i=0; i<MAXCLIENTE && SJogadors[i].Client>=0; i++);
		        if (i<MAXCLIENTE) {
		          
	                              SJogadors[i].Client=clisock;
	                              SJogadors[i].IdJogador=i;
				      writes(clisock, "Inicia!\n");
				      writes(clisock, "NOME?\n");

	                                char *result = (char *) malloc(50);
	                                char mystr[9] = "MEUID=";
		                	sprintf(result, "%s=%d\n", mystr,SJogadors[i].IdJogador);
	                                writes(clisock, result);
	                                printf("Enviado id do jogador: %s=%d\n", SJogadors[i].Nome,SJogadors[i].IdJogador);
			  
		        } else {
				
			          writes(clisock, "Maximo de conexoes excedidas, volte mais tarde...\r\n"); 
			          shutdown(clisock, 2);
			          close(clisock);
		        }
	      }
	    } else if (which>0) {
		    
	      // Recebe uma mensagem do cliente 
	      dataread=recv(which, buffer, sizeof(buffer), MSG_PEEK);
			
		  //cli= id do socket do cliente que esta mandando a mensagem
		  cli=search_sjogador(which);
	      if (cli>=0) {
			  
	        if (dataread<0)	{
				
	          // erro no read - desconecta
	          shutdown(SJogadors[cli].Client, 2);
	          close(SJogadors[cli].Client);
	          SJogadors[cli].Client=-1;
	          fprintf(stderr, "ERRO(%d) no read TCP: %s\n", errno, strerror(errno));
	        } else if (dataread==0)	{
	          writes(SJogadors[SJogadors[cli].IdJogador].Client, "Jogador saiu do Jogo\n");
	          CONTADOR_JG=CONTADOR_JG-1;
				
		  // o jogador desconectou o socket
		  printf("O socket %d desconectou\n", which);
		  shutdown(SJogadors[cli].Client, 2);
		  close(SJogadors[cli].Client);
		  reset_jogador(cli);
	        } else if (dataread>0) {
		 
				// verifica se e' uma linha completa com ENTER
		          if (buffer[dataread-1]=='\r' || buffer[dataread-1]=='\n') {
			    tam_buf = recv(which, buffer, dataread, 0); // le os dados do socket
			    buffer[tam_buf]=0;
			    
			    printf("Mensagem recebida: %s\n", buffer);
			
					//Testes as mensagens	  
				    if (strncasecmp(buffer,"NOME=",5)==0) {
			            	CONTADOR_JG=CONTADOR_JG+1;

			                for(j=5;j<tam_buf;j++)
				        		SJogadors[cli].Nome[j-5]=buffer[j];  
				                printf("Nome do Jogador: %s\n", SJogadors[cli].Nome);
								SJogadors[cli].Status=1;				
						
					}
					  //Se o cliente pedir palavra, o serv. liga a flag para comecar nova rodada
					else if (strncasecmp(buffer, "PALAVRA?",8)==0) {
						
			              SJogadors[cli].Status = 3;
			        }
					else if (strncasecmp(buffer, "SIGNIFICADO=",12)==0) {
						for(j=12;j<tam_buf;j++)
				        		SJogadors[cli].Significado[j-12]=buffer[j];  
				                printf("Significado do jogador: %s=%s\n", SJogadors[cli].Nome,SJogadors[cli].Significado);
			              SJogadors[cli].Status = 6;
			        }
					/*****Soma ou subtrai pontos!**********/
					else if(strncasecmp(buffer, "ACERTEI",7)==0) {
			              SJogadors[cli].Acerto=SJogadors[cli].Acerto++;
	                              SJogadors[cli].Pontos=SJogadors[cli].Acerto*3;
			        }
				    else if(strncasecmp(buffer, "ERREI",5)==0) {
	                                 if(SJogadors[cli].Acerto==0){
	                                        //Não deixa existir pontos negativos
	                                        SJogadors[cli].Acerto=0;
	                                 }else{
				      	        SJogadors[cli].Acerto=SJogadors[cli].Acerto--;
	                                        SJogadors[cli].Pontos=SJogadors[cli].Acerto*1;
	                                  }
			        }
					
				    
					if (SJogadors[0].Status==1&&SJogadors[1].Status==1&&SJogadors[2].Status==1) {
			              start_jogo();
			        }
					/***** Verifica Fim do jogo ********/
					if (SJogadors[0].Pontos==30) {
						SJogadors[0].Status=5;
			              stop_jogo();
			        }
					if(SJogadors[1].Pontos==30){
						SJogadors[1].Status=5;
						stop_jogo();
					}
					if(SJogadors[2].Pontos==30){
						SJogadors[2].Status=5;
						stop_jogo();
					}
					/**********************************/  
					  
					/*****Se flag de nova rodada dos 3 jogadores estiver ligada, inicia nova rodada.***/
					  
					if (SJogadors[0].Status==3&&SJogadors[1].Status==3&&SJogadors[2].Status==3) {
						 geraNumeroRodada();
			             novaRodada();
			        }
					if (SJogadors[0].Status==6&&SJogadors[1].Status==6&&SJogadors[2].Status==6) {
						 mandaSignificado();
			        }
					  
				}
	        }
	      } else {
	        fprintf(stderr, "ERRO: cliente do socket %d nao identificado\n", which);
	      }
	    }
	  }
	}
	void stop_jogo(){
		int i;
		for(i=0;i<3;i++){
			if (SJogadors[i].Status==5) {
		      writes(SJogadors[SJogadors[i].IdJogador].Client, "VENCEU\n");
		   }
			else {
		      writes(SJogadors[SJogadors[i].IdJogador].Client, "PERDEU\n");
		   }
		}
	}
	/******* Comeca o jogo  e a primeira rodada ************/
	void start_jogo(){
		int i;
		geraNumeroRodada();
		for(i=0;i<3;i++){
		writes(SJogadors[SJogadors[i].IdJogador].Client, "START\n");

		
			//Gera ID da palavra e manda para os 3 clientes
			char *pala = (char *) malloc(20);
			char mystr[9] = "PALAVRA=";
			sprintf(pala, "%s %d\n", mystr,ale);
			printf("Palavra: %s\n", pala);
			writes(SJogadors[i].Client, pala);
		}
	}

	void reset_jogador(int i){
	   SJogadors[i].Status=0;
	   SJogadors[i].IdJogador=-1;
	   SJogadors[i].Pontos=0;
	   SJogadors[i].Acerto=0;
	   SJogadors[i].Client=-1;
	   SJogadors[i].Palavra[0]='\0';
	}


	int search_sjogador(int sock){
	   int i;
	   for (i=0; i<MAXCLIENTE; i++) {
	      if (SJogadors[i].Client==sock) return(i);
	   }
	   return(-1);
	}

	int aguarda(int sockprincipal, int timeout){
	   fd_set entradas;
	   struct timeval tm;
	   int i, ret, max;
	   // Preenche o mapa de bits com os sockets que vai escutar
	   FD_ZERO(&entradas);
	   FD_SET(sockprincipal, &entradas);
	   max=sockprincipal;
	   for (i=0; i<MAXCLIENTE; i++) {
	      if (SJogadors[i].Client>=0) {
	         FD_SET(SJogadors[i].Client, &entradas);
	         if (SJogadors[i].Client>max) max=SJogadors[i].Client;
	      }
	   }
	   // Prepara o timeout
	   tm.tv_sec=timeout;
	   tm.tv_usec=0;
	   ret=select(max+1, &entradas, NULL, NULL, &tm);
	   if (ret<0) {
	      fprintf(stderr,"ERRO(%d) no select: %s\n", errno, strerror(errno));
	      return(ret);
	   }
	   if (ret==0) { return(0); } // TIMEOUT
	   // Tenta identificar qual socket enviou dados a partir de 3 (stdin, stdout, stderr)
	   for (i=3; i<=max; i++) {
	     if (FD_ISSET(i, &entradas)) { return(i); }
	   }
	   fprintf(stderr,"ERRO no select: nao localizou handle %d\n", ret);
	   return(-1);
	}
	int writes(int fd, char *s){
	  return(write(fd,s,strlen(s)));
	}
	void tratasinal(int sinal) {
	   shutdown(serv_socket, 2);
	   close(serv_socket);
	   exit(0);
	}


	int geraInteiroAleatorio()
	{

	int Nmin=0;
	int Nmax=50;
	int numero;
	int teste=0;
	int j=0;
			do{
			numero = (rand() % (Nmax-Nmin+1) + Nmin);
				for(j=0;j<numRodadas;j++){
					if(numeros[j]==numero){
						teste=1;//se ja existe
					}else{
						teste=0;
					}
				}
			}while(teste==1);
		
	numeros[numRodadas]=numero;
	numRodadas++;
	return numero;
	}
	void geraNumeroRodada(){
	ale = geraInteiroAleatorio();

	}
	void novaRodada(){
	        int i;
		// A cada rodada Atualiza o ranking
		mandaRanking();
	        

		for(i=0;i<3;i++){
			
			//Gera ID da palavra e manda para os 3 clientes
			char *pala = (char *) malloc(20);
			char mystr[9] = "PALAVRA=";
			sprintf(pala, "%s %d\n", mystr,ale);
			
			writes(SJogadors[i].Client, pala);
		}
		printf("Id Palavra: %s\n", ale);
	}

	void mandaRanking(){
	int i;
	int j;
	for(i=0;i<3;i++){
		for(j=0;j<3;j++){
			char *result = (char *) malloc(50);
			char mystr[9] = "Rank";
			sprintf(result, "%s=%d>%d\n", mystr,SJogadors[i].IdJogador,SJogadors[j].Pontos);

			writes(SJogadors[i].Client, result);
		}
		printf("Enviado ranking para o jogador: %s\n", SJogadors[i].Nome);
	}



	}
	void mandaSignificado(){

	int i;
	int j;
	for(i=0;i<3;i++){
		for(j=0;j<3;j++){
			char *result = (char *) malloc(500);
			char mystr[12] = "SIGNIFICADO=";
			sprintf(result, "%s %s=%s\n", mystr,SJogadors[j].Nome,SJogadors[j].Significado);
			
			writes(SJogadors[i].Client, result);
		}
		printf("Enviado significados para o jogador: %s\n", SJogadors[i].Nome);
	}

	}

