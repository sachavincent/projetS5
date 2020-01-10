package view;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

public class VueMessage extends JPanel{
	JPanel panelMessage = new JPanel();
	private String message;
	private String d;
	public VueMessage(String message, String d) {
		this.message = message;
		this.d = d;
	}
	public JPanel test(){
		int screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode()
				.getWidth();
		SpringLayout springLayout = new SpringLayout();
		JLabel nom = new JLabel("Sacha V.");
		JLabel dateMessage = new JLabel(d);

		JTextArea contenuMessage = new JTextArea(5, 2);
		contenuMessage.setText(message);
		contenuMessage.setBorder(new LineBorder(Color.BLACK, 2, true));
		contenuMessage.setEditable(false);
		panelMessage.add(nom);
		panelMessage.add(contenuMessage);
		panelMessage.add(dateMessage);

		springLayout.putConstraint(SpringLayout.NORTH, nom, 20, SpringLayout.NORTH, contenuMessage);

		// panelMessage si 1er message sinon précédent
		springLayout.putConstraint(SpringLayout.NORTH, contenuMessage, 100, SpringLayout.NORTH, panelMessage);
		springLayout.putConstraint(SpringLayout.WEST, nom, 8, SpringLayout.EAST, contenuMessage);
		springLayout.putConstraint(SpringLayout.WEST, contenuMessage, screenWidth / 2, SpringLayout.WEST,
				panelMessage);
		springLayout.putConstraint(SpringLayout.EAST, panelMessage, 20, SpringLayout.EAST, nom);
		springLayout.putConstraint(SpringLayout.NORTH, dateMessage, 5, SpringLayout.SOUTH, contenuMessage);
		springLayout.putConstraint(SpringLayout.WEST, dateMessage, 0, SpringLayout.WEST, contenuMessage);

		panelMessage.setLayout(springLayout);

		//add(panelMessage,BorderLayout.WEST);
		return panelMessage;
	}

}
