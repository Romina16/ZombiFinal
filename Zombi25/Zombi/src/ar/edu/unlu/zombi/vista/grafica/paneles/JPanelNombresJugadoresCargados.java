package ar.edu.unlu.zombi.vista.grafica.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.grafica.JFramePrincipal;

public class JPanelNombresJugadoresCargados extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal framePrincipal;
	private List<String> nombresJugadores;
	private JButton btnContinuar;
	
	public JPanelNombresJugadoresCargados(IVista administradorVista,JFramePrincipal framePrincipal) {
		this.administradorVista = administradorVista;
		this.framePrincipal = framePrincipal;
		
		
	}
	
	private void obtenerDatosPanel() {
    	nombresJugadores = administradorVista.obtenerNombresJugadores();
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon fondo = new ImageIcon(getClass().getResource(
			"/ar/edu/unlu/zombi/vista/grafica/imagenes/fondos/fondoNormal.png"));
		if (fondo != null) g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
	}
	
	private void inicializarComponentes() {
		JLabel lblEsperando = new JLabel("Jugadores cargados");
		lblEsperando.setForeground(Color.GREEN.darker());
		lblEsperando.setFont(new Font("SansSerif", Font.BOLD, 36));
		lblEsperando.setHorizontalAlignment(SwingConstants.CENTER);
		lblEsperando.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel panelNombres = new JPanel();
		panelNombres.setLayout(new BoxLayout(panelNombres, BoxLayout.Y_AXIS));
		panelNombres.setOpaque(false);
		
		for (String nombreJugador : nombresJugadores) {
			JLabel lblNombre = new JLabel(nombreJugador);
			lblNombre.setFont(new Font("Serif", Font.PLAIN, 24));
			lblNombre.setForeground(Color.WHITE);
			lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelNombres.add(lblNombre);
			panelNombres.add(Box.createVerticalStrut(15));
		}
		
		this.btnContinuar = crearBotonEstilizado("Continuar");
		
		// Panel para acomodar vertical las cosas
				JPanel panelCentro = new JPanel();
				panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
				panelCentro.setOpaque(false);
				panelCentro.add(Box.createVerticalStrut(40));
				panelCentro.add(lblEsperando);
				panelCentro.add(Box.createVerticalStrut(30));
				panelCentro.add(panelNombres);
				panelCentro.add(Box.createVerticalStrut(30));
				panelCentro.add(btnContinuar);

				// Centra el panel que tiene las cosas
				JPanel Completo = new JPanel(new GridBagLayout());
				Completo.setOpaque(false);
				Completo.add(panelCentro);

				this.setLayout(new BorderLayout());
				this.add(Completo, BorderLayout.CENTER);
	}
	
	//Estilo de los botones
			private JButton crearBotonEstilizado(String texto) {
			    JButton boton = new JButton(texto);

			    boton.setBackground(new Color(68, 85, 90)); // Verde apagado
			    boton.setForeground(Color.WHITE);
			    boton.setFont(new Font("Arial", Font.BOLD, 18));

			    boton.setFocusPainted(false);
			    boton.setBorderPainted(false);
			    boton.setContentAreaFilled(false);
			    boton.setOpaque(true);
			    boton.setBorder(BorderFactory.createLineBorder(new Color(40, 50, 55), 2));
			    
			    boton.setPreferredSize(new Dimension(200, 40));
			    boton.setMaximumSize(new Dimension(200, 40));
			    boton.setAlignmentX(Component.CENTER_ALIGNMENT);
			    
			    boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

			    return boton;
			}
	
			private void inicializarAccionBoton() {
				this.btnContinuar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 administradorVista.iniciarRonda();
					}
				});
			}

	@Override
	public void mostrarPanel() {
		this.obtenerDatosPanel();
		this.inicializarComponentes();
		inicializarAccionBoton();
		framePrincipal.showPanel(this);
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		// TODO Auto-generated method stub
		
	}

}
