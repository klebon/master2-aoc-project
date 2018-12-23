package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import ihm.observer.ObsMonitor;
import ihm.observer.SubjectMonitor;

/**
 * ValueUI is a IHM showing DisplayMonitor value
 */
public class ValueUI extends JFrame implements ObsMonitor {
	
	private static final long serialVersionUID = -1802883184608148357L;
	private static int count = 1;
	private JLabel value;
	
	/**
	 * Constructor setting all the JFrame
	 * @param title is the title shown in the toolbar of the JFrame
	 */
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
	
	/**
	 * Function called in order to show views
	 */
	public void showView() {
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Update the showing value of the IHM according to the state of subject
	 * @param subject is the reference of the subject that we are observing
	 */
	@Override
	public void update(SubjectMonitor subject) {
		value.setText(subject.getState().toString());		
	}

}
