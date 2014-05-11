package server.codes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Window.Type;

public class SerialMonitor extends JFrame{
	private JTextArea log;
	SerialComm sc;
	public SerialMonitor(){
		setTitle("LOG");
		setResizable(false);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 327, 575);
		getContentPane().add(scrollPane);
		
		log = new JTextArea();
		log.setEditable(false);
		scrollPane.setViewportView(log);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 51, 255));
		panel.setBounds(0, 0, 347, 39);
		getContentPane().add(panel);
		
		JLabel lblSerialDataLog = new JLabel("SERIAL DATA LOG");
		lblSerialDataLog.setForeground(Color.WHITE);
		lblSerialDataLog.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel.add(lblSerialDataLog);
		
		JButton btnNewButton = new JButton("CLEAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.setText("");
			}
		});
		btnNewButton.setBounds(10, 636, 327, 29);
		getContentPane().add(btnNewButton);
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(353,704);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;

		setLocation(x, y);
	}
	
	
	
	
	public void printToLog(String s){
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	String timestamp = sdf.format(cal.getTime());

		log.append("["+timestamp+"] :  "+s+"\n");
	}
}
