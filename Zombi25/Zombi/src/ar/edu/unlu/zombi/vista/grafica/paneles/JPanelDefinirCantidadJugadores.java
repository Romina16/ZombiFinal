package ar.edu.unlu.zombi.vista.grafica.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class JPanelDefinirCantidadJugadores extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JTextField textField;
	private JFramePrincipal framePrincipal;
	private JButton btnCargar;
	
	public JPanelDefinirCantidadJugadores(IVista administradorVista,JFramePrincipal frame ) {
		this.administradorVista = administradorVista;
		this.framePrincipal = frame;
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
        this.setLayout(new BorderLayout());
        
        // Panel medio con componentes verticales
        JPanel panelMedio = new JPanel();
        panelMedio.setOpaque(false);
        panelMedio.setLayout(new BoxLayout(panelMedio, BoxLayout.Y_AXIS));

        // Título
        JLabel lblTitulo = new JLabel("Definir cantidad de jugadores");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(Color.GREEN.darker());
        panelMedio.add(Box.createVerticalStrut(20));
        panelMedio.add(lblTitulo);

        // Panel para el input y su label (horizontal)
        JPanel panelInput = new JPanel();
        panelInput.setOpaque(false);
        panelInput.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JLabel lblCantidad = new JLabel("Cantidad de jugadores:");
        lblCantidad.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblCantidad.setForeground(Color.GREEN.darker());
        panelInput.add(lblCantidad);

        textField = new JTextField(8);
        panelInput.add(textField);

        panelMedio.add(Box.createVerticalStrut(15));
        panelMedio.add(panelInput);

        // Botón que define cantidad de jugadores
        this.btnCargar = crearBotonEstilizado("Definir");
        this.btnCargar.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        panelMedio.add(Box.createVerticalStrut(10));
        panelMedio.add(btnCargar);
        panelMedio.add(Box.createVerticalStrut(20));

        
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setOpaque(false);
        contenedor.add(panelMedio);
        
        this.setLayout(new BorderLayout());
        this.add(contenedor, BorderLayout.CENTER);
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
	
		//es como inicializarAccionEnter();
		private void inicializarAccionBoton() {
			btnCargar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String cantidadJugadores = textField.getText();
					if(cantidadJugadores.isEmpty()) {
						administradorVista.mostrarMensajeError("Se debe ingresar un numero");
					}else {
						try {
							int opcion = Integer.parseInt(cantidadJugadores);
							if (opcion >= 2 && opcion <= 4) {
								administradorVista.obtenerDatosCargaCantidadJugadores(cantidadJugadores);
							}else {
								administradorVista.mostrarMensajeError("Opcion invalida. Debe ser entre 2 y 4");
							}
						}catch(NumberFormatException ex){
							administradorVista.mostrarMensajeError("Debe ingresar un numero valido");
						}
					}
				}
			});
			textField.addActionListener(e -> btnCargar.doClick());
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
