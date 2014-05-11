package server.codes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

public class LoginFrame extends JFrame{
	SerialComm serial;
	private JTextField usernameField;
	private JPasswordField passwordField;
	JButton connectButton;
	private final String un, pwd;
	private ImageIcon ldngImage;
	private String[] ports = { "com1", "com2", "com3"};
	private JTextField port;
	
	public LoginFrame(String username, String password, final DataManager dm){
		getContentPane().setBackground(new Color(0, 51, 255));
		
		un = username;
		pwd = password;
		
		setResizable(false);
		setType(Type.UTILITY);
		setTitle("SYSTEM LOGIN");
		getContentPane().setLayout(null);
		
		ldngImage = new ImageIcon(getClass().getResource("img/loading.gif"));
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(40, 121, 316, 28);
		getContentPane().add(lblLogin);
		
		usernameField = new JTextField();
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setBounds(40, 160, 154, 29);
		getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(202, 160, 154, 29);
		getContentPane().add(passwordField);
		
		connectButton = new JButton("START");
		connectButton.setBounds(40, 248, 316, 29);
		getContentPane().add(connectButton);
		connectButton.setEnabled(false);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(202, 146, 154, 14);
		getContentPane().add(lblPassword);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(40, 146, 154, 14);
		getContentPane().add(lblUsername);
		
		JLabel topLabel = new JLabel("");
		topLabel.setBounds(0, 0, 400, 135);
		topLabel.setIcon(new ImageIcon(getClass().getResource("img/l_top.png")));
		getContentPane().add(topLabel);
		
		JLabel botLabel = new JLabel("");
		botLabel.setBounds(0, 266, 400, 135);
		botLabel.setIcon(new ImageIcon(getClass().getResource("img/l_bot.png")));
		getContentPane().add(botLabel);
		
		final JButton btnTest = new JButton("TEST");
		btnTest.setToolTipText("Testing may take a few seconds. Please be patient.");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String portString = port.getText().toUpperCase();
				if(portString.indexOf("COM")!=0){
					JOptionPane.showMessageDialog(LoginFrame.this, "Please enter proper port name.\nExample: COM1", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				serial = new SerialComm(portString);
				if(serial.getStatus()){
					JOptionPane.showMessageDialog(LoginFrame.this, "Test successfull.", "TEST", JOptionPane.INFORMATION_MESSAGE);
					connectButton.setEnabled(true);
					port.setEnabled(false);
					btnTest.setEnabled(false);
				}
				else{
					JOptionPane.showMessageDialog(LoginFrame.this, "Test failed. Please check connection.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnTest.setBounds(202, 210, 154, 27);
		getContentPane().add(btnTest);
		
		port = new JTextField();
		port.setHorizontalAlignment(SwingConstants.CENTER);
		port.setBounds(40, 209, 154, 28);
		getContentPane().add(port);
		port.setColumns(10);
		
		JLabel lblCommport = new JLabel("Communication Port :");
		lblCommport.setHorizontalAlignment(SwingConstants.CENTER);
		lblCommport.setForeground(Color.WHITE);
		lblCommport.setBounds(40, 187, 316, 21);
		getContentPane().add(lblCommport);
		
		setSize(405,422);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		connectButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if(usernameField.getText().equals(un) && passwordField.getText().equals(pwd)){
					UserInterface ui = new UserInterface(dm, serial);			
					ui.setVisible(true);
					LoginFrame.this.dispose();
				}
				else{
					JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username and password combination.", "ERROR", JOptionPane.ERROR_MESSAGE);
					usernameField.setText("");
					passwordField.setText("");
				}
				
			}
		});
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;

		setLocation(x, y);
	}
}
