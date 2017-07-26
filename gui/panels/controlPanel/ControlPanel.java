package gui.panels.controlPanel;

import gui.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2823491917549154922L;

	// public JPanel jPanel_ctl = new JPanel();
	public JPanel jPanel_ctl = this;
	public JLabel jlabel_ctlTitle = new JLabel(
			"Control:                                                                                              ");
	public JButton jBsysGc = new JButton("GC");

	public ControlPanel(){
    	this.add(jlabel_ctlTitle);
    	this.add(jBsysGc);
    	this.jBsysGc.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.gc();
		Gui.log("Running GC\n");
	}
}
