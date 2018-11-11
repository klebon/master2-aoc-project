package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import observer.Observer;
import observer.Subject;

// TODO: Commenter le code
public class ValueUI extends JFrame implements Observer {
	
	private static final long serialVersionUID = -1802883184608148357L;
	private static int count = 1;
	private JLabel value;
	private Subject subject;
	
	public ValueUI(String title) {
		this.value = new JLabel("...");
		this.value.setFont(new Font("Serif", Font.PLAIN, 30));
		this.value.setHorizontalAlignment(JLabel.CENTER);
		this.value.setVerticalAlignment(JLabel.CENTER);
		this.setTitle(title + " " + count);
		this.setLocationRelativeTo(null);
		this.getContentPane().setPreferredSize(new Dimension(200, 100));
		this.getContentPane().add(value, BorderLayout.CENTER);
		count++;
	}
	
	public void setSujet(Subject s) {
		this.subject = s;
		s.attach(this);
	}
	
	public void showView() {
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void update() {
		value.setText(this.subject.getState().toString());
	}

}
