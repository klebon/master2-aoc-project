package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import main.Main;

/**
 * ParameterUI is a IHM managing diffusion choice
 */
public class ParameterUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 456467879876511L;
	
	private JButton validation;
	private JComboBox<String> choice;

	/**
	 * Constructor setting all the JFrame
	 */
	public ParameterUI() {
		String[] diffusionString = { "Atomique", "Séquentielle", "Version"};
		this.choice = new JComboBox<String>(diffusionString);
		this.choice.setSelectedIndex(0);
		
		this.validation = new JButton("Start");
		this.validation.addActionListener(this);
		
		this.setTitle("Paramètre");
		this.setLocationRelativeTo(null);
		this.getContentPane().setPreferredSize(new Dimension(200, 100));
		this.getContentPane().add(choice, BorderLayout.CENTER);
		this.getContentPane().add(validation, BorderLayout.PAGE_END);
	}

	/**
	 * Function called when validation is pressed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String result = (String) this.choice.getSelectedItem();
		String[] arguments = new String[] {result};
		Main.main(arguments);
	}
	
	/**
	 * Function called in order to show views
	 */
	public void showView() {
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Main
	 */
	public static void main(String[] args) {
		ParameterUI parameter = new ParameterUI();
		parameter.showView();
	}
	
}
