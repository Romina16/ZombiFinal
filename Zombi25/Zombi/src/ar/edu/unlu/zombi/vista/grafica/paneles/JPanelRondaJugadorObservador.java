package ar.edu.unlu.zombi.vista.grafica.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.vista.grafica.JFramePrincipal;

public class JPanelRondaJugadorObservador extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;
	private final IVista administradorVista;
	private JFramePrincipal framePrincipal;
	private String nombreJugadorActual;
	private List<CartaDTO> mazoParejas;
	private List<CartaDTO> mazoJugador;
	private Integer cantidadCartasJugadorDerecha;
	private BloqueoOverlay overlay;
	
	public JPanelRondaJugadorObservador(IVista administradorVista,JFramePrincipal framePrincipal) {
		this.administradorVista = administradorVista;
		this.framePrincipal = framePrincipal;
		
		
		// Asegura que el overlay se inicialice bien cuando el panel está visible
				addHierarchyListener(e -> {
					if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
						activarOverlay(); // por defecto, en modo observador
					}
				});
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon fondo = new ImageIcon(
				getClass().getResource("/ar/edu/unlu/zombi/vista/grafica/imagenes/fondos/fondoNormal.png"));
		if (fondo != null) {
			g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	private void inicializarComponentes() {
		this.removeAll();
		// Usamos OverlayLayout para apilar "baseUI" y "overlay"
				setLayout(new OverlayLayout(this));
				setOpaque(false);

				// ----- base UI (igual que el panel de turno) -----
				JPanel base = new JPanel(new BorderLayout());
				base.setOpaque(false);

				// Panel superior (espacio)
				JPanel panelSuperior = new JPanel();
				panelSuperior.setOpaque(false);
				panelSuperior.setPreferredSize(new Dimension(0, 50));
				base.add(panelSuperior, BorderLayout.NORTH);

				// CENTRO: cartas centrales + leyenda + mano
				JPanel panelContenido = new JPanel();
				panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
				panelContenido.setOpaque(false);

				// Cartas del centro
				JPanel panelCartasCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
				panelCartasCentro.setOpaque(false);
				for (CartaDTO carta : mazoParejas) {
					JButton b = new JButton();
					b.setPreferredSize(new Dimension(90, 130));
					dibujarCarta(b, carta); // normal
					panelCartasCentro.add(b);
				}
				panelContenido.add(panelCartasCentro);
				panelContenido.add(Box.createVerticalStrut(10));

				// Leyenda
				JLabel lbl = new JLabel("Turno de " + nombreJugadorActual);
				lbl.setFont(new Font("SansSerif", Font.PLAIN, 20));
				lbl.setForeground(Color.WHITE);
				lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
				panelContenido.add(lbl);
				panelContenido.add(Box.createVerticalStrut(10));

				// Mano del jugador (solo se muestra, sin acción)
				JPanel panelMano = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
				panelMano.setOpaque(false);
				for (CartaDTO carta : mazoJugador) {
					JButton b = new JButton();
					b.setPreferredSize(new Dimension(90, 130));
					dibujarCarta(b, carta);
					panelMano.add(b);
				}
				panelContenido.add(panelMano);

				base.add(panelContenido, BorderLayout.CENTER);

				// DERECHA: cartas ocultas (rotadas 90°, sin acción)
				JPanel panelDerecha = new JPanel();
				panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS)); // vertical
				panelDerecha.setOpaque(false);
				panelDerecha.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
				panelDerecha.setPreferredSize(new Dimension(130, 0)); // ancho fijo para EAST
				
				// Importante para OverlayLayout (que base ocupe todo)
				base.setAlignmentX(0.5f);
				base.setAlignmentY(0.5f);

				add(base);

				// ----- Overlay bloqueador (gris translúcido) -----
				overlay = new BloqueoOverlay("No es tu turno");
				overlay.setVisible(true); // observador => bloqueado
				overlay.setAlignmentX(0.5f);
				overlay.setAlignmentY(0.5f);
				overlay.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
				add(overlay); // último agregado = encima
	}

	private void obtenerDatosPanel() {
		nombreJugadorActual = administradorVista.obtenerNombreJugadorActual();
		mazoParejas = administradorVista.obtenerUltimasDosCartasMazoParejas();
		mazoJugador = administradorVista.obtenerMazoJugador();
		cantidadCartasJugadorDerecha = administradorVista.ObtenerCantidadCartasJugadorDerecha();
	}
	

	private void dibujarCarta(JButton boton, CartaDTO carta) {
		String ruta = (carta != null) ? rutaCarta(carta) : rutaReverso();
		URL url = getClass().getResource(ruta);

		int w = boton.getWidth(), h = boton.getHeight();
		if (w <= 0 || h <= 0) { w = 90; h = 130; }

		if (url != null) {
			ImageIcon icon = new ImageIcon(url);
			Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
			boton.setIcon(new ImageIcon(img));
			boton.setText("");
		} else {
			boton.setText("X");
		}
		boton.setHorizontalTextPosition(SwingConstants.CENTER);
		boton.setVerticalTextPosition(SwingConstants.CENTER);
	}

	/*private void dibujarCartaRotadaDerecha(JButton boton) {
		int w = 110, h = 75; // "acostada"
		boton.setPreferredSize(new Dimension(w, h));

		URL url = getClass().getResource(rutaReverso());
		ImageIcon icon = iconoRotado90(url, w, h);

		if (icon != null) {
			boton.setIcon(icon);
			boton.setText("");
		} else {
			boton.setText("X");
		}
		boton.setHorizontalTextPosition(SwingConstants.CENTER);
		boton.setVerticalTextPosition(SwingConstants.CENTER);
	}*/

	/*private ImageIcon iconoRotado90(URL recurso, int ancho, int alto) {
		if (recurso == null) return null;

		ImageIcon src = new ImageIcon(recurso);
		BufferedImage original = new BufferedImage(
				src.getIconWidth(), src.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g0 = original.createGraphics();
		g0.drawImage(src.getImage(), 0, 0, null);
		g0.dispose();

		BufferedImage rotada = new BufferedImage(
				original.getHeight(), original.getWidth(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g1 = rotada.createGraphics();
		g1.translate(rotada.getWidth()/2.0, rotada.getHeight()/2.0);
		g1.rotate(Math.PI/2);
		g1.translate(-original.getWidth()/2.0, -original.getHeight()/2.0);
		g1.drawImage(original, 0, 0, null);
		g1.dispose();

		Image esc = rotada.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		return new ImageIcon(esc);
	}*/

	private String rutaCarta(CartaDTO carta) {
		return "/ar/edu/unlu/zombi/vista/grafica/imagenes/cartasTodas/"
				+ carta.NombreImagenDeLaCarta() + ".png";
	}

	private String rutaReverso() {
		return "/ar/edu/unlu/zombi/vista/grafica/imagenes/cartasTodas/Reverso.png";
	}

	// ===== Overlay gris que bloquea interacción =====
	private static class BloqueoOverlay extends JPanel {

		private static final long serialVersionUID = 1L;
		private final String mensaje;

		BloqueoOverlay(String mensaje) {
			this.mensaje = mensaje;
			setOpaque(false);

			// Consumí todos los eventos de mouse
			MouseAdapter eater = new MouseAdapter() {};
			addMouseListener(eater);
			addMouseMotionListener(eater);
			addMouseWheelListener(eater);

			setFocusable(true);
		}
	}
		
		public void activarOverlay() {
			if (overlay != null) {
				overlay.setVisible(true);
				SwingUtilities.invokeLater(overlay::requestFocusInWindow);
			}
		}

		public void desactivarOverlay() {
			if (overlay != null) overlay.setVisible(false);
		}
	
	
		@Override
		public void mostrarPanel() {
			obtenerDatosPanel();
			inicializarComponentes();
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
