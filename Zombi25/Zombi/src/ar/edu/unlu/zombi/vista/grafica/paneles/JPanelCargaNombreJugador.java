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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.grafica.JFramePrincipal;

public class JPanelCargaNombreJugador extends JPanel implements IPanel {

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal framePrincipal;
	private JTextField textField;
	private JButton btnCargar;
	
	public JPanelCargaNombreJugador(IVista administradorVista,JFramePrincipal framePrincipal ) {
		this.administradorVista = administradorVista;
		this.framePrincipal = framePrincipal;
		inicializarComponentes();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon fondo = new ImageIcon(
				getClass().getResource("/ar/edu/unlu/zombi/vista/grafica/imagenes/fondos/fondoNormal.png"));
		if (fondo != null) {
			g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}

	private void inicializarComponentes() {
		this.setSize(900,700);
		//setSize(900, 700);
		JPanel panelMedio = new JPanel();
		panelMedio.setLayout(new BoxLayout(panelMedio, BoxLayout.Y_AXIS));
		panelMedio.setOpaque(false);

		// titulo
		JLabel lblTitulo = new JLabel("Carga de jugador");
		lblTitulo.setForeground(Color.GREEN.darker());
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		panelMedio.add(Box.createVerticalStrut(30));
		panelMedio.add(lblTitulo);

		// Label nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setForeground(Color.GREEN.darker());
		lblNombre.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblNombre.setAlignmentX(CENTER_ALIGNMENT);
		panelMedio.add(Box.createVerticalStrut(20));
		panelMedio.add(lblNombre);

		// Campo de texto
		textField = new JTextField(20);
		textField.setMaximumSize(new Dimension(300, 25));
		textField.setAlignmentX(CENTER_ALIGNMENT);
		panelMedio.add(Box.createVerticalStrut(10));
		panelMedio.add(textField);

		// Botón Cargar
		this.btnCargar = crearBotonEstilizado("Cargar");
		this.btnCargar.setAlignmentX(CENTER_ALIGNMENT);

		panelMedio.add(Box.createVerticalStrut(20));
		panelMedio.add(this.btnCargar);

		panelMedio.add(Box.createVerticalStrut(20));

		// Contenedor centrado
		JPanel panelCentrado = new JPanel(new GridBagLayout());
		panelCentrado.setOpaque(false);
		panelCentrado.add(panelMedio);

		this.setLayout(new BorderLayout());
		this.add(panelCentrado, BorderLayout.CENTER);

	}
	//es como inicializarAccionEnter();
	private void inicializarAccionBoton() {
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreJugador = textField.getText();
				if (nombreJugador.isEmpty()) {
					administradorVista.mostrarMensajeError("Debes ingresar un nombre");
				}else {
				administradorVista.obtenerDatosCargaNombreJugador(nombreJugador);
				}
			}
		});
		textField.addActionListener(e -> btnCargar.doClick());
	}

	// Estilo de los botones
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

	@Override
	public void mostrarPanel() {
		inicializarAccionBoton();
		textField.requestFocusInWindow();
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
