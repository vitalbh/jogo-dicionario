package gui;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextPane;

import rede.Conexao;

import controle.Controle;

public class Gui extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;

	public String Sentenca = null;

	private JPanel jContentPane = null;

	private JToolBar jJToolBarBar_Botoes = null;

	private JButton jButton_Configura = null;

	private JButton jButton_Ranking = null;

	public JProgressBar jProgressBar_Timer = null;

	private JPanel jPanel_Conteudo = null;

	private JPanel jPanel_Cima = null;

	private JLabel jLabel_PalavraSorteada = null;

	private JLabel jLabel = null;

	private JLabel jLabel1_Pontos = null;

	private JPanel jPanel_Configuracao = null;  //  @jve:decl-index=0:visual-constraint="318,11"

	private JLabel jLabel1_Nome = null;

	private JTextField jTextField_Nome = null;

	private JLabel jLabel1_Ip = null;

	private JTextField jTextField_IP = null;

	private JLabel jLabel1_Porta = null;

	private JTextField jTextField_Porta = null;

	private JPanel jPanel_Ranking = null;  //  @jve:decl-index=0:visual-constraint="319,143"

	private JScrollPane jScrollPane_RolagemRanking = null;

	private JTextArea jTextArea_Ranking = null;

	private JPanel jPanel_meio = null;

	private JScrollPane jScrollPane_BarraDeRolagemSig = null;

	private JTextArea jTextArea_Significado = null;

	public Controle controle = new Controle();  //  @jve:decl-index=0:

	/**
	 * This method initializes jJToolBarBar_Botoes	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJJToolBarBar_Botoes() {
		if (jJToolBarBar_Botoes == null) {
			jJToolBarBar_Botoes = new JToolBar();
			jJToolBarBar_Botoes.add(getJButton_Configura());
			jJToolBarBar_Botoes.add(getJButton_Ranking());
		}
		return jJToolBarBar_Botoes;
	}

	/**
	 * This method initializes jButton_Configura	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Configura() {
		if (jButton_Configura == null) {
			jButton_Configura = new JButton();
			jButton_Configura.setText("Configurar");
			jButton_Configura.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setaConfiguracao();

				}
			});
		}
		return jButton_Configura;
	}
	
	public void setaConfiguracao(){
		JOptionPane.showMessageDialog(null, getJPanel_Configuracao(),"Configure o jogo",JOptionPane.PLAIN_MESSAGE);
		controle.setNomeJogador(getJTextField_Nome().getText());
		controle.setNumIp(getJTextField_IP().getText());
		controle.setPorta(Integer.getInteger(getJTextField_IP().getText()));
	}
	/**
	 * This method initializes jButton_Ranking	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Ranking() {
		if (jButton_Ranking == null) {
			jButton_Ranking = new JButton();
			jButton_Ranking.setText("Ranking");
			jButton_Ranking.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JOptionPane.showMessageDialog(null, getJPanel_Ranking(),"Ranking de jogadores",JOptionPane.PLAIN_MESSAGE);
				}
			});
		}
		return jButton_Ranking;
	}

	/**
	 * This method initializes jProgressBar_Timer	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	public JProgressBar getJProgressBar_Timer() {
		if (jProgressBar_Timer == null) {
			int min=0;
			int max=30;
			jProgressBar_Timer = new JProgressBar(min,max);
			jProgressBar_Timer.setString("");
			jProgressBar_Timer.setValue(0);
			jProgressBar_Timer.setForeground(new Color(191, 34, 28));
		}
		
	
		return jProgressBar_Timer;
	}
public JProgressBar percorre(){
	int num=jProgressBar_Timer.getValue();
	num=num++;
	jProgressBar_Timer.setValue(num);
	return jProgressBar_Timer;
}
	/**
	 * This method initializes jPanel_Conteudo	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Conteudo() {
		if (jPanel_Conteudo == null) {
			jPanel_Conteudo = new JPanel();
			jPanel_Conteudo.setLayout(new BorderLayout());
			jPanel_Conteudo.add(getJPanel_Cima(), BorderLayout.NORTH);
			jPanel_Conteudo.add(getJPanel_meio(), BorderLayout.CENTER);
		}
		return jPanel_Conteudo;
	}

	/**
	 * This method initializes jPanel_Cima	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJPanel_Cima() {
		if (jPanel_Cima == null) {
			jLabel1_Pontos = new JLabel();
			//controle.getPontuacao().toString()
			jLabel1_Pontos.setText("0");
			jLabel1_Pontos.setForeground(new Color(51, 132, 23));
			jLabel1_Pontos.setFont(new Font("Dialog", Font.BOLD, 24));
			setJLabel(new JLabel());
			//getJLabel().setText("abacaxi");
			jLabel_PalavraSorteada = new JLabel();
			jLabel_PalavraSorteada.setText("Palavra Sorteada:   ");
			jLabel_PalavraSorteada.setForeground(new Color(219, 22, 22));
			jPanel_Cima = new JPanel();
			jPanel_Cima.setLayout(new BorderLayout());
			jPanel_Cima.add(jLabel_PalavraSorteada, BorderLayout.WEST);
			jPanel_Cima.add(getJLabel(), BorderLayout.CENTER);
			jPanel_Cima.add(jLabel1_Pontos, BorderLayout.EAST);
		}
		return jPanel_Cima;
	}

	/**
	 * This method initializes jPanel_Configuracao	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJPanel_Configuracao() {
		if (jPanel_Configuracao == null) {
			jLabel1_Porta = new JLabel();
			jLabel1_Porta.setText("Porta");
			jLabel1_Ip = new JLabel();
			jLabel1_Ip.setText("IP");
			jLabel1_Nome = new JLabel();
			jLabel1_Nome.setText("Nome");
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(4);
			gridLayout.setColumns(2);
			jPanel_Configuracao = new JPanel();
			jPanel_Configuracao.setSize(new Dimension(150, 56));
			jPanel_Configuracao.setLayout(gridLayout);
			jPanel_Configuracao.setPreferredSize(new Dimension(150, 100));
			jPanel_Configuracao.add(jLabel1_Nome, null);
			jPanel_Configuracao.add(getJTextField_Nome(), null);
			jPanel_Configuracao.add(jLabel1_Ip, null);
			jPanel_Configuracao.add(getJTextField_IP(), null);
			jPanel_Configuracao.add(jLabel1_Porta, null);
			jPanel_Configuracao.add(getJTextField_Porta(), null);
		}
		return jPanel_Configuracao;
	}

	/**
	 * This method initializes jTextField_Nome	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getJTextField_Nome() {
		if (jTextField_Nome == null) {
			jTextField_Nome = new JTextField();
			jTextField_Nome.setPreferredSize(new Dimension(2, 20));
		}
		return jTextField_Nome;
	}

	/**
	 * This method initializes jTextField_IP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getJTextField_IP() {
		if (jTextField_IP == null) {
			jTextField_IP = new JTextField();
			jTextField_IP.setPreferredSize(new Dimension(2, 20));
		}
		return jTextField_IP;
	}

	/**
	 * This method initializes jTextField_Porta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_Porta() {
		if (jTextField_Porta == null) {
			jTextField_Porta = new JTextField();
		}
		return jTextField_Porta;
	}

	/**
	 * This method initializes jPanel_Ranking	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Ranking() {
		if (jPanel_Ranking == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jPanel_Ranking = new JPanel();
			jPanel_Ranking.setLayout(new GridBagLayout());
			jPanel_Ranking.setPreferredSize(new Dimension(250, 250));
			jPanel_Ranking.add(getJScrollPane_RolagemRanking(), gridBagConstraints);
		}
		return jPanel_Ranking;
	}

	/**
	 * This method initializes jScrollPane_RolagemRanking	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_RolagemRanking() {
		if (jScrollPane_RolagemRanking == null) {
			jScrollPane_RolagemRanking = new JScrollPane();
			jScrollPane_RolagemRanking.setViewportView(getJTextArea_Ranking());
		}
		return jScrollPane_RolagemRanking;
	}

	/**
	 * This method initializes jTextArea_Ranking	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea_Ranking() {
		if (jTextArea_Ranking == null) {
			jTextArea_Ranking = new JTextArea();
			jTextArea_Ranking.setText("");
		}
		return jTextArea_Ranking;
	}

	/**
	 * This method initializes jPanel_meio	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_meio() {
		if (jPanel_meio == null) {
			jPanel_meio = new JPanel();
			jPanel_meio.setLayout(new BorderLayout());
			jPanel_meio.setBorder(BorderFactory.createTitledBorder(null, "Digite um significado", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel_meio.add(getJScrollPane_BarraDeRolagemSig(), BorderLayout.CENTER);
		}
		return jPanel_meio;
	}

	/**
	 * This method initializes jScrollPane_BarraDeRolagemSig	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_BarraDeRolagemSig() {
		if (jScrollPane_BarraDeRolagemSig == null) {
			jScrollPane_BarraDeRolagemSig = new JScrollPane();
			jScrollPane_BarraDeRolagemSig.setViewportView(getJTextArea_Significado());
		}
		return jScrollPane_BarraDeRolagemSig;
	}

	/**
	 * This method initializes jTextArea_Significado	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea_Significado() {
		if (jTextArea_Significado == null) {
			jTextArea_Significado = new JTextArea();
		}
		return jTextArea_Significado;
	}


	/**
	 * This is the default constructor
	 */
	public Gui() {
		
		super();
		initialize();
		
		startCliente();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("Jogo do dicionario");
		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJJToolBarBar_Botoes(), BorderLayout.NORTH);
			jContentPane.add(getJProgressBar_Timer(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel_Conteudo(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	public void setJLabel(JLabel jLabel) {
		this.jLabel = jLabel;
	}

	public JLabel getJLabel() {
		return jLabel;
	}
	
	public void startCliente(){
		
		//Thread de trabalhador do cliente
		
		ExecutorService trabalhador = Executors.newFixedThreadPool(1);
		trabalhador.execute(this);
		
	}
	
	public void run() {
		try {
			setaConfiguracao();
			//Conexao con = new Conexao();
            //con.setSocket(new Socket(controle.getNumIp().toString(),controle.getPorta().intValue()));
            int serverPort = 6070;
            String serverHost = "localhost";

            Socket clientSocket = new Socket(serverHost, serverPort);
            DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());
              BufferedReader inFromServer =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             
            
			Sentenca = inFromServer.readLine();
		} catch (IOException e) {
			// TODO Bloco catch gerado automaticamente
			e.printStackTrace();
		}
		while(Sentenca!="EXIT"){
			
			if(Sentenca.contains("rank=")){
				int count=Sentenca.length();
				String ranking = Sentenca.substring(6, count);
				int pos = Sentenca.indexOf(">");
				String nomeR = ranking.substring(0, pos-1);
				String pontosR = ranking.subSequence(pos+1, ranking.length()).toString();
			
			}
			else if(Sentenca.contains("PALAVRA=")){
				int count=Sentenca.length();
				String idPalavra = Sentenca.substring(9, count);
				controle.setIdPalavra(Integer.parseInt(idPalavra));
				//Chama metodo de mostrar a palavra
			}else if(Sentenca.contains("MEUID=")){
				int count=Sentenca.length();
				String idJogador = Sentenca.substring(7, count);
				controle.setIdJogador(Integer.parseInt(idJogador));
				jTextArea_Ranking.setText(Integer.toString(controle.getIdJogador()));
				//Chama metodo de mostrar a palavra
			}
			
			
			
			
		}
	}


}
