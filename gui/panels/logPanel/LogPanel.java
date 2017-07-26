package gui.panels.logPanel;

import gui.StatusFlag;

import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LogPanel extends JPanel implements Runnable{
	private static final long serialVersionUID = -6111206288933781387L;
	
	public JLabel jlabel_logTitle=new JLabel("LogOutput：                                                                                                  "); 
    public JFrame jFrame_mainWindow = new JFrame();  
    public JTextField jtextField_enterBox = new JTextField(20);
    //public JTextPane jTextArea_logArea=new JTextPane();
    public JTextArea jTextArea_logArea=new JTextArea("Server日志开始记录:\n",10,30);
    public SimpleDateFormat df_date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public JScrollPane jScrollpane_logScroll;
    public GridLayout mainLayout = new GridLayout(3,2);//layout类型
    public Box vlogBox1 = Box.createVerticalBox();
    public Box hlogBox2 = Box.createHorizontalBox();
    public Box hlogBox3 = Box.createHorizontalBox();
    //public JPanel jPanel_log = new JPanel();
    public JPanel jPanel_log = this;
    public JButton button_send = new JButton("发送");
    
    Thread tflagChecker = new Thread(this);
    public JLabel jlabel_status = new JLabel("●");
    
    public LogPanel(){
    	super();
    	//标题位
    	jlabel_logTitle.setFont((new Font("宋体",Font.BOLD, 16)));
        jlabel_logTitle.setSize(20, 20);
        jlabel_status.setFont((new Font("宋体",Font.BOLD, 16)));
        hlogBox2.add(jlabel_status);
        hlogBox2.add(jlabel_logTitle);
        hlogBox2.add(Box.createGlue());
        vlogBox1.add(hlogBox2);
        //输出位
        jTextArea_logArea.setEditable(false);
        jTextArea_logArea.setLineWrap(true);
        jTextArea_logArea.setWrapStyleWord(true);
        jScrollpane_logScroll=new JScrollPane(jTextArea_logArea);
        jScrollpane_logScroll.setHorizontalScrollBarPolicy( 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        jScrollpane_logScroll.setVerticalScrollBarPolicy( 
        		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
 		jScrollpane_logScroll.setWheelScrollingEnabled(true);
 		vlogBox1.add(jScrollpane_logScroll);
 		//输入位
 		hlogBox3.add(jtextField_enterBox);
 		hlogBox3.add(button_send);
 		vlogBox1.add(hlogBox3);
 		jPanel_log.add(vlogBox1);//最后把整个panel加到窗口
 		//监视器
    	tflagChecker.start();
    	log("Starting Checker Thread on: "+tflagChecker.getId());
    	//log( "<html><font color=\"#FF0000\">我是红色字体</font></html> ");
    }
 		
 	public void log(String msg){
 		synchronized (LogPanel.class){
 			//jtextarea.setText(jlabel.getText().replace("</html>", "")+msg+"<br></html>");
 			jTextArea_logArea.setText(jTextArea_logArea.getText()+"["+df_date.format(new Date())+"] "+msg+"\n");
 			jTextArea_logArea.setCaretPosition(jTextArea_logArea.getText().length());
 			System.out.print("["+df_date.format(new Date())+"] "+msg+"\n");
 		}
 	}
 	@Override
 	public void run(){
 		try {
 			while(true){
 				this.jlabel_status.setForeground(StatusFlag.getHighestStatus());
 				Thread.sleep(500);
 			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 	}

}
