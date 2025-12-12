package ar.edu.unlu.zombi.vista.grafica.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.grafica.JFramePrincipal;

public class JPanelEsperaJugadores extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal framePrincipal;
	
	public JPanelEsperaJugadores(IVista administradorVista,JFramePrincipal framePrincipal) {
		this.administradorVista = administradorVista;
		this.framePrincipal = framePrincipal;
		inicializarComponentes();
	}
	
	private void inicializarComponentes() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);

		JLabel lblEsperando = new JLabel("Esperando Jugadores...");
		lblEsperando.setForeground(Color.GREEN.darker());
		lblEsperando.setFont(new Font("SansSerif", Font.BOLD, 36));
		lblEsperando.setHorizontalAlignment(SwingConstants.CENTER);

		panel.add(lblEsperando);
		
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.CENTER);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);					 
		ImageIcon fondo = new ImageIcon(getClass().getResource("/ar/edu/unlu/zombi/vista/grafica/imagenes/fondos/fondoNormal.png"));
		if (fondo != null) {
			g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}

	@Override
	public void mostrarPanel() {
		framePrincipal.showPanel(this);
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(null,
				mensaje,
				"Error",
				JOptionPane.ERROR_MESSAGE
				);
	}

}
