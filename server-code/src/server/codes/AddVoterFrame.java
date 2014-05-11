package server.codes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddVoterFrame extends JDialog{
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField stationField;
	private JTextField pathField;
	private File picFile;
	private String id, pin, firstName, lastName, station;
	private byte[] voterPic = null;
	private JTextField idField;
	private JTextField pinField;
	final DataManager dManager;
	public AddVoterFrame(DataManager dm, final UserInterface ui){
		setModal(true);
		dManager = dm;
		setResizable(false);
		setTitle("ADD VOTER");
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 51, 255));
		panel.setBounds(0, 0, 414, 47);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblVotersDetails = new JLabel("VOTER'S DETAILS");
		lblVotersDetails.setForeground(Color.WHITE);
		lblVotersDetails.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblVotersDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblVotersDetails.setBounds(10, 0, 394, 47);
		panel.add(lblVotersDetails);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(61, 184, 290, 27);
		getContentPane().add(firstNameField);
		firstNameField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First Name :");
		lblNewLabel.setBounds(61, 159, 82, 27);
		getContentPane().add(lblNewLabel);
		
		JLabel lblLastName = new JLabel("Last Name :");
		lblLastName.setBounds(61, 211, 135, 27);
		getContentPane().add(lblLastName);
		
		lastNameField = new JTextField();
		lastNameField.setColumns(10);
		lastNameField.setBounds(61, 236, 290, 27);
		getContentPane().add(lastNameField);
		
		JLabel lblStationNumber = new JLabel("Station Number :");
		lblStationNumber.setBounds(61, 261, 135, 27);
		getContentPane().add(lblStationNumber);
		
		stationField = new JTextField();
		stationField.setColumns(10);
		stationField.setBounds(61, 288, 290, 27);
		getContentPane().add(stationField);
		
		JLabel lblPicture = new JLabel("Picture :");
		lblPicture.setBounds(61, 313, 184, 27);
		getContentPane().add(lblPicture);
		
		pathField = new JTextField();
		pathField.setEditable(false);
		pathField.setColumns(10);
		pathField.setBounds(61, 340, 184, 27);
		getContentPane().add(pathField);
		
		JButton chooseBtn = new JButton("Choose");
		chooseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(AddVoterFrame.this);
				try {
					picFile = fc.getSelectedFile();
					String path = picFile.getAbsolutePath();
					pathField.setText(path);
					
					FileInputStream fis = new FileInputStream(picFile);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte buf[] = new byte[1024];
					
					for(int readNum; (readNum=fis.read(buf))!=-1; ){
						bos.write(buf,0,readNum);
					}
					fis.close();
					
					voterPic = bos.toByteArray();
					
				} catch (Exception e2) {
					
				}
				
			}
		});
		chooseBtn.setBounds(248, 340, 103, 27);
		getContentPane().add(chooseBtn);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					id = idField.getText();
					int intID = Integer.parseInt(id);
					pin = pinField.getText();
					int intPIN = Integer.parseInt(pin);
					station = stationField.getText();
					int intStn = Integer.parseInt(station);
					
					firstName = firstNameField.getText();
					lastName = lastNameField.getText();
					
					dManager.addNewVoter(id, pin, firstName, lastName, station, voterPic);
					ui.updateVotersTable();
					AddVoterFrame.this.dispose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(AddVoterFrame.this,"Your input contains an error.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
		});
		btnAdd.setBounds(61, 390, 135, 44);
		getContentPane().add(btnAdd);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddVoterFrame.this.dispose();
			}
		});
		btnCancel.setBounds(216, 390, 135, 44);
		getContentPane().add(btnCancel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(0, 51, 255));
		panel_1.setBounds(0, 445, 414, 27);
		getContentPane().add(panel_1);
		
		idField = new JTextField();
		idField.setColumns(10);
		idField.setBounds(61, 83, 290, 27);
		getContentPane().add(idField);
		
		JLabel lblIdNumber = new JLabel("ID Number :");
		lblIdNumber.setBounds(61, 58, 82, 27);
		getContentPane().add(lblIdNumber);
		
		pinField = new JTextField();
		pinField.setColumns(10);
		pinField.setBounds(61, 134, 290, 27);
		getContentPane().add(pinField);
		
		JLabel lblPinNumber = new JLabel("PIN Number :");
		lblPinNumber.setBounds(61, 109, 82, 27);
		getContentPane().add(lblPinNumber);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(420, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;

		setLocation(x, y);
		setResizable(false);
		
		
		
	}
}
