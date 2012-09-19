package controle;

import javax.swing.JFrame;

import gui.Gui;

public class Jogo {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gui interfaceGrafica = new Gui();
		interfaceGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//interfaceGrafica.getJLabel().setText(sorteia.puxaDadoExterno(3));
		interfaceGrafica.setLocationRelativeTo(null);
		interfaceGrafica.setVisible(true);

		
		
	}

}
