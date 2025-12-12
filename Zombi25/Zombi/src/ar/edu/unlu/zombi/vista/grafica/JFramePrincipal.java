package ar.edu.unlu.zombi.vista.grafica;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unlu.zombi.interfaces.IPanel;
//import jdk.internal.org.jline.terminal.TerminalBuilder.SystemOutput;

public class JFramePrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contenedor;
    private final CardLayout cardLayout;
    
    public JFramePrincipal() {
		super("Zombies");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        setContentPane(contenedor);       
	}
	
    public void showPanel(IPanel panel) {
        this.getContentPane().removeAll();
        this.getContentPane().add((Component) panel);
        this.revalidate();
        this.repaint();
    }

    public void showFrame() {
        setVisible(true);
    }
}