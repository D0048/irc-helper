package gui;

import gui.panels.controlPanel.ControlPanel;
import gui.panels.logPanel.LogPanel;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	static JFrame jFrame_mainWindow = new JFrame();
	static GridLayout mainLayout = new GridLayout(3, 2);// layout类型
	static LogPanel logPanel = new LogPanel();
	static ControlPanel controlPanel = new ControlPanel();

	public static void startGui(String[] args) {
		StatusFlag.setIsPending();
		// frame 初始化
		jFrame_mainWindow.setVisible(true);
		jFrame_mainWindow.setSize(1000, 800);
		jFrame_mainWindow.setTitle("Irc-helper Console");
		jFrame_mainWindow.setLayout(mainLayout);
		jFrame_mainWindow
				.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame_mainWindow.setLocationRelativeTo(null);
		// jFrame_mainWindow.add(jPanel_log);

		jFrame_mainWindow.add(logPanel);
		jFrame_mainWindow.add(controlPanel);

		log("Gui载入成功！");
		StatusFlag.setOk(true);
		// StatusFlag.setPending(false);
		/*
		 * try { Thread.sleep(200); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		// displayException(new Exception("test"));
	}

	public static void log(String msg) {
		logPanel.log(msg);
	}

	public static void displayException(Exception e) {
		StatusFlag.setIsError();
		String errInfo = "Error message: " + e.getMessage();
		for (StackTraceElement ste : e.getStackTrace()) {
			errInfo = errInfo + "\n" + ste.toString();
		}
		log("[error]" + errInfo);
		// StatusFlag.setError(false);
	}

}