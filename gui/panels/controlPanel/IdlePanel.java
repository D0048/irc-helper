package gui.panels.controlPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class IdlePanel extends JPanel{
	private static final long serialVersionUID = -6143438348516086903L;
	
	JLabel label;
	
	public IdlePanel() {
		super();
		this.label= new JLabel("Nothing to show here");
		this.add(label);
	}
	
	public IdlePanel(String message) {
		super();
		this.label = new JLabel(message);
		this.add(label);
	}
}
