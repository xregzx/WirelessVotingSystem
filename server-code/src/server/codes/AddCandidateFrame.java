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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;

public class AddCandidateFrame extends JDialog{
	String positions[] = {"PRESIDENT", "V-PRESIDENT INT", "V-PRESIDENT EXT", "SECRETARY", "ASSOC. SECRETARY", "TREASURER", "AUDITOR", "PIO INT", "PIO EXT"};
		
	private File picFile;
	private String firstName, lastName, position;
	private int posIndex, cNum;
	private byte[] candidatePic = null;
	private JTextField pathField;
	private JTextField fnameField;
	private JTextField lnameField;
	JComboBox positionCombo, choiceNum;
	final DataManager dManager;
	public AddCandidateFrame(DataManager dm, final UserInterface ui){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		dManager = dm;
		
		setTitle("ADD CANDIDATE");

		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 51, 255));
		panel.setBounds(0, 0, 416, 47);
		getContentPane().add(panel);
		
		JLabel lblCandidatesDetails = new JLabel("CANDIDATE'S DETAILS");
		lblCandidatesDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblCandidatesDetails.setForeground(Color.WHITE);
		lblCandidatesDetails.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCandidatesDetails.setBounds(10, 0, 396, 47);
		panel.add(lblCandidatesDetails);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(0, 51, 255));
		panel_1.setBounds(0, 342, 416, 27);
		getContentPane().add(panel_1);
		
		JButton addButton = new JButton("ADD");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					firstName = fnameField.getText();
					lastName = lnameField.getText();
					position = positions[positionCombo.getSelectedIndex()];
					posIndex = positionCombo.getSelectedIndex()+1;
					cNum = choiceNum.getSelectedIndex()+1;
					
					dManager.addNewCandidate(firstName, lastName,position, candidatePic,posIndex,cNum );
					ui.updateCandidatesTable();
					AddCandidateFrame.this.dispose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(AddCandidateFrame.this,"Your input contains an error.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addButton.setBounds(61, 287, 135, 44);
		getContentPane().add(addButton);
		
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddCandidateFrame.this.dispose();
			}
		});
		cancelButton.setBounds(216, 287, 135, 44);
		getContentPane().add(cancelButton);
		
		JButton choose = new JButton("Choose");
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(AddCandidateFrame.this);
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
					
					candidatePic = bos.toByteArray();
					
				} catch (Exception e2) {
					
				}
			}
		});
		choose.setBounds(248, 239, 103, 27);
		getContentPane().add(choose);
		
		pathField = new JTextField();
		pathField.setEditable(false);
		pathField.setColumns(10);
		pathField.setBounds(61, 239, 184, 27);
		getContentPane().add(pathField);
		
		JLabel label = new JLabel("Picture :");
		label.setBounds(61, 216, 184, 27);
		getContentPane().add(label);
		
		JLabel lblPosition = new JLabel("Position :");
		lblPosition.setBounds(61, 165, 135, 27);
		getContentPane().add(lblPosition);
		
		fnameField = new JTextField();
		fnameField.setColumns(10);
		fnameField.setBounds(61, 85, 290, 27);
		getContentPane().add(fnameField);
		
		JLabel lblFirstName = new JLabel("First Name :");
		lblFirstName.setBounds(61, 58, 135, 27);
		getContentPane().add(lblFirstName);
		
		lnameField = new JTextField();
		lnameField.setColumns(10);
		lnameField.setBounds(61, 139, 290, 27);
		getContentPane().add(lnameField);
		
		JLabel lblLastName = new JLabel("Last Name :");
		lblLastName.setBounds(61, 112, 135, 27);
		getContentPane().add(lblLastName);
		
		positionCombo = new JComboBox(positions);
		positionCombo.setBounds(61, 190, 184, 27);
		getContentPane().add(positionCombo);
		
		choiceNum = new JComboBox(new Object[]{});
		choiceNum.setModel(new DefaultComboBoxModel(new String[] {"1", "2"}));
		choiceNum.setBounds(248, 190, 103, 27);
		getContentPane().add(choiceNum);
		
		JLabel lblChoiceNumber = new JLabel("Choice number:");
		lblChoiceNumber.setBounds(248, 171, 103, 14);
		getContentPane().add(lblChoiceNumber);
		setSize(422,396);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;

		setLocation(x, y);
		setResizable(false);
	}
}
