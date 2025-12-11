package ar.edu.unlu.zombi.vista.consola;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class JFramePrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	//private JPanel contentPane;
    private final JTextArea consolaArea;
    private final JTextField inputField;
    private final JButton enterButton;

    public JFramePrincipal() {
        super("Zombi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        //Area de salida de texto
        consolaArea = new JTextArea();
        consolaArea.setForeground(Color.black);
        consolaArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        consolaArea.setEnabled(false);
        consolaArea.setBackground(Color.black);
        consolaArea.setEditable(false);
        consolaArea.setLineWrap(true);
        consolaArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(consolaArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //Area de salida de texto

        //Area de entrada de texto
        inputField = new JTextField();
        enterButton = new JButton("Ingresar");
        JPanel inputPanel = new JPanel(new BorderLayout(5,5));
        inputPanel.add(inputField,BorderLayout.CENTER);
        inputPanel.add(enterButton,BorderLayout.EAST);

        //Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane,BorderLayout.CENTER);
        getContentPane().add(inputPanel,BorderLayout.SOUTH);

        setVisible(true);
    }

    public void appendLine(String line){
        consolaArea.append(line + "\n");
        consolaArea.setCaretPosition(consolaArea.getDocument().getLength());
    }

    public void setEnterAction(ActionListener listener){
        for (ActionListener old : inputField.getActionListeners()){
            inputField.removeActionListener(old);
        }

        for (ActionListener old : enterButton.getActionListeners()){
            enterButton.removeActionListener(old);
        }
        inputField.addActionListener(listener);
        enterButton.addActionListener(listener);
    }

    public String getInputText(){
        return inputField.getText();
    }

    public void clearInput(){
        inputField.setText("");
    }

    public void setEditabledInput(boolean editable){
        inputField.setEditable(editable);
    }

    public void showFrame(){
        setVisible(true);
    }

}
