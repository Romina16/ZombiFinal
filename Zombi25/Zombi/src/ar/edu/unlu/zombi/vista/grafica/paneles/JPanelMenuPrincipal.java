package ar.edu.unlu.zombi.vista.grafica.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.grafica.JFramePrincipal;

public class JPanelMenuPrincipal extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal framePrincipal;
	private JButton btnInicioJuego;
	private JButton btnContinuar;
	private JButton btnSalir;
	private boolean hayPartidaPersistida;
	
	public JPanelMenuPrincipal(IVista administradorVista,JFramePrincipal framePrincipal) {
		this.administradorVista =  administradorVista;
		this.framePrincipal = framePrincipal;
		
		inicializar();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);					 
		ImageIcon fondo = new ImageIcon(getClass().getResource("/ar/edu/unlu/zombi/vista/grafica/imagenes/fondos/fondoNormal.png"));
		if (fondo != null) {
			g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	private void obtenerDatosPanel(){ hayPartidaPersistida = administradorVista.hayPartidaPersistida();}

	
	private void inicializar() {
		
		setSize(900,700);
		
		JPanel panelMesa = new JPanel();
		panelMesa.setLayout(new BorderLayout());
		panelMesa.setOpaque(false);
		
		ImageIcon iconoLogo = new ImageIcon(getClass().getResource("/ar/edu/unlu/zombi/vista/grafica/imagenes/zmb.png"));
		
		Image imagenEscalada1 = iconoLogo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado1 = new ImageIcon(imagenEscalada1);
		
		JLabel LblTitulo1 = new JLabel(iconoEscalado1);
		LblTitulo1.setHorizontalAlignment(SwingConstants.CENTER);
		LblTitulo1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		ImageIcon iconoTitulo = new ImageIcon(getClass().getResource("/ar/edu/unlu/zombi/vista/grafica/imagenes/zombie.png"));

		// Si querés escalar la imagen, podés hacerlo así:
		Image imagenEscalada = iconoTitulo.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

		JLabel LblTitulo = new JLabel(iconoEscalado);
		LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.btnInicioJuego = crearBotonEstilizado("Iniciar Juego");
		btnInicioJuego.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.btnContinuar = crearBotonEstilizado("Continuar");
		btnContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnContinuar.setEnabled(this.hayPartidaPersistida);

		
		this.btnSalir = crearBotonEstilizado("Salir");
		btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		JPanel ListadoMenu = new JPanel();//(new GridLayout(2,1,10,10));
		ListadoMenu.setOpaque(false);  // Hace que no se tape el fondo
		ListadoMenu.setLayout(new BoxLayout(ListadoMenu, BoxLayout.Y_AXIS));
		
		ListadoMenu.add(LblTitulo1);
		ListadoMenu.add(Box.createVerticalStrut(20));
		ListadoMenu.add(LblTitulo);
		ListadoMenu.add(Box.createVerticalStrut(20));
		ListadoMenu.add(btnInicioJuego);
		ListadoMenu.add(Box.createVerticalStrut(20));
		ListadoMenu.add(btnContinuar);
		ListadoMenu.add(Box.createVerticalStrut(20));
		ListadoMenu.add(btnSalir);
				
		LblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel Menu = new JPanel(new GridBagLayout());
		Menu.setOpaque(false);
		Menu.add(ListadoMenu);
		panelMesa.add(Menu, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(panelMesa, BorderLayout.CENTER);
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
			btnInicioJuego.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					administradorVista.IniciarPartida();
				}
			});
			//
			btnContinuar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { //evento salir
					administradorVista.continuarPartidaPersistida();
				}
			});
			//
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { //evento salir
					administradorVista.SalirJuego();
				}
			});
		}
		
		@Override
		public void mostrarPanel() {
			obtenerDatosPanel();
			inicializarAccionBoton();
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
