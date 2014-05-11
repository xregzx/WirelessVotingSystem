package server.codes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.JTableHeader;
import javax.swing.text.Position;

import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class UserInterface extends JFrame{

	private static final long serialVersionUID = 1L;

	DataManager data;
	SerialMonitor sm;
	
	private JPanel topPanel,botPanel;
	private JPanel homePanel, votersPanel, candidatePanel, statPanel;
	private ImageIcon active_up,active_down,inactive_up,inactive_down, defaultProf;
	private ImageIcon ptit1,ptit2,ptit3,ptit4,ptit5,ptit6,ptit7,ptit8,ptit9;

	//for first the home tab
	private JLabel u1,u2,u3,u4,u5,u6;
	private JLabel d1,d2,d3,d4,d5,d6;
	private JLabel s1Count, s2Count, s3Count, s4Count, s5Count, s6Count;
	private JLabel vtd1, vtd2, vtd3, vtd4, vtd5, vtd6;
	int stationCounts[] = new int[6];
	
	//for the voters tab
	private JPanel voterInfoPanel;
	private JTable voterTable;
	private JButton addVoter;
	private JButton deleteVoter;
	private JScrollPane candidatesPane;
	private JTable candidatesTable;
	private JButton addCandidates;
	private JButton deleteCandidates;
	private JPanel candidateInfoPanel;
	private JLabel lblInformation;
	private JPanel panel_5;
	private JLabel label_5;
	private final JLabel profilePictureVoter,profilePictureCandidate;
	private JLabel nameLabelCandidate, positionLabel;
	private JTable t1;
	private JTable t2;
	private JTable t3;
	private JTable t4;
	private JTable t5;
	private JTable t6;
	private JTable t7;
	private JTable t8;
	private JTable t9;
	private JLabel tit1;
	private JLabel tit2;
	private JLabel tit3;
	private JLabel tit4;
	private JLabel tit5;
	private JLabel tit6;
	private JLabel tit7;
	private JLabel tit8;
	private JLabel tit9;
	private JPanel panel_9;
	private JPanel panel_10;
	
	public UserInterface(final DataManager data, SerialComm s){
		this.data = data;
		
		getContentPane().setBackground(Color.WHITE);
		active_up = new ImageIcon(getClass().getResource("img/a_up.png"));
		active_down = new ImageIcon(getClass().getResource("img/a_down.png"));
		inactive_up = new ImageIcon(getClass().getResource("img/i_up.png"));
		inactive_down = new ImageIcon(getClass().getResource("img/i_down.png"));
		defaultProf = new ImageIcon(getClass().getResource("img/default.jpg"));
		ptit1 = new ImageIcon(getClass().getResource("img/1.png"));
		ptit2 = new ImageIcon(getClass().getResource("img/2.png"));
		ptit3 = new ImageIcon(getClass().getResource("img/3.png"));
		ptit4 = new ImageIcon(getClass().getResource("img/4.png"));
		ptit5 = new ImageIcon(getClass().getResource("img/5.png"));
		ptit6 = new ImageIcon(getClass().getResource("img/6.png"));
		ptit7 = new ImageIcon(getClass().getResource("img/7.png"));
		ptit8 = new ImageIcon(getClass().getResource("img/8.png"));
		ptit9 = new ImageIcon(getClass().getResource("img/9.png"));
		
		setSize(860, 704);
		setResizable(false);
		setTitle("ELECTION SERVER");
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(10, 266, 834, 375);
		getContentPane().add(tabbedPane);
		//-------------------------------------------------------------------------------------
		// HOME PANEL
		//-------------------------------------------------------------------------------------
		for(int i=0;i<6;i++){
			stationCounts[i] = 0;
		}
		homePanel = new JPanel();
		homePanel.setBackground(SystemColor.control);
		tabbedPane.addTab("HOME", null, homePanel, null);
		homePanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBorder(null);
		panel.setBounds(38, 108, 107, 136);
		homePanel.add(panel);
		panel.setLayout(null);
		
		s1Count = new JLabel(Integer.toString(stationCounts[0]));
		s1Count.setForeground(Color.DARK_GRAY);
		s1Count.setHorizontalAlignment(SwingConstants.CENTER);
		s1Count.setFont(new Font("Tahoma", Font.BOLD, 65));
		s1Count.setBounds(0, 0, 107, 90);
		panel.add(s1Count);
		
		vtd1 = new JLabel("VOTED");
		vtd1.setForeground(Color.DARK_GRAY);
		vtd1.setFont(new Font("Tahoma", Font.BOLD, 16));
		vtd1.setHorizontalAlignment(SwingConstants.CENTER);
		vtd1.setBounds(10, 101, 87, 14);
		panel.add(vtd1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(166, 108, 107, 136);
		homePanel.add(panel_1);
		panel_1.setLayout(null);
		
		s2Count = new JLabel(Integer.toString(stationCounts[1]));
		s2Count.setHorizontalAlignment(SwingConstants.CENTER);
		s2Count.setForeground(Color.DARK_GRAY);
		s2Count.setFont(new Font("Tahoma", Font.BOLD, 65));
		s2Count.setBounds(0, 0, 107, 90);
		panel_1.add(s2Count);
		
		vtd2 = new JLabel("VOTED");
		vtd2.setHorizontalAlignment(SwingConstants.CENTER);
		vtd2.setForeground(Color.DARK_GRAY);
		vtd2.setFont(new Font("Tahoma", Font.BOLD, 16));
		vtd2.setBounds(10, 101, 87, 14);
		panel_1.add(vtd2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(null);
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(294, 108, 107, 136);
		homePanel.add(panel_2);
		panel_2.setLayout(null);
		
		s3Count = new JLabel(Integer.toString(stationCounts[2]));
		s3Count.setHorizontalAlignment(SwingConstants.CENTER);
		s3Count.setForeground(Color.DARK_GRAY);
		s3Count.setFont(new Font("Tahoma", Font.BOLD, 65));
		s3Count.setBounds(0, 0, 107, 90);
		panel_2.add(s3Count);
		
		vtd3 = new JLabel("VOTED");
		vtd3.setHorizontalAlignment(SwingConstants.CENTER);
		vtd3.setForeground(Color.DARK_GRAY);
		vtd3.setFont(new Font("Tahoma", Font.BOLD, 16));
		vtd3.setBounds(10, 101, 87, 14);
		panel_2.add(vtd3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setBounds(421, 108, 107, 136);
		homePanel.add(panel_3);
		panel_3.setLayout(null);
		
		s4Count = new JLabel(Integer.toString(stationCounts[3]));
		s4Count.setHorizontalAlignment(SwingConstants.CENTER);
		s4Count.setForeground(Color.DARK_GRAY);
		s4Count.setFont(new Font("Tahoma", Font.BOLD, 65));
		s4Count.setBounds(0, 0, 107, 90);
		panel_3.add(s4Count);
		
		vtd4 = new JLabel("VOTED");
		vtd4.setHorizontalAlignment(SwingConstants.CENTER);
		vtd4.setForeground(Color.DARK_GRAY);
		vtd4.setFont(new Font("Tahoma", Font.BOLD, 16));
		vtd4.setBounds(10, 101, 87, 14);
		panel_3.add(vtd4);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(null);
		panel_6.setBackground(Color.LIGHT_GRAY);
		panel_6.setBounds(552, 108, 107, 136);
		homePanel.add(panel_6);
		panel_6.setLayout(null);
		
		s5Count = new JLabel(Integer.toString(stationCounts[4]));
		s5Count.setHorizontalAlignment(SwingConstants.CENTER);
		s5Count.setForeground(Color.DARK_GRAY);
		s5Count.setFont(new Font("Tahoma", Font.BOLD, 65));
		s5Count.setBounds(0, 0, 107, 90);
		panel_6.add(s5Count);
		
		vtd5 = new JLabel("VOTED");
		vtd5.setHorizontalAlignment(SwingConstants.CENTER);
		vtd5.setForeground(Color.DARK_GRAY);
		vtd5.setFont(new Font("Tahoma", Font.BOLD, 16));
		vtd5.setBounds(10, 101, 87, 14);
		panel_6.add(vtd5);
		
		JPanel bPanel_6 = new JPanel();
		bPanel_6.setBorder(null);
		bPanel_6.setBackground(Color.LIGHT_GRAY);
		bPanel_6.setBounds(683, 108, 107, 136);
		homePanel.add(bPanel_6);
		bPanel_6.setLayout(null);
		
		s6Count = new JLabel(Integer.toString(stationCounts[5]));
		s6Count.setHorizontalAlignment(SwingConstants.CENTER);
		s6Count.setForeground(Color.DARK_GRAY);
		s6Count.setFont(new Font("Tahoma", Font.BOLD, 65));
		s6Count.setBounds(0, 0, 107, 90);
		bPanel_6.add(s6Count);
		
		vtd6 = new JLabel("VOTED");
		vtd6.setHorizontalAlignment(SwingConstants.CENTER);
		vtd6.setForeground(Color.DARK_GRAY);
		vtd6.setFont(new Font("Tahoma", Font.BOLD, 16));
		vtd6.setBounds(10, 101, 87, 14);
		bPanel_6.add(vtd6);
		
		u1 = new JLabel("");
		u1.setIcon(inactive_up);
		u1.setBounds(38, 52, 107, 55);
		homePanel.add(u1);
		
		u2 = new JLabel("");
		u2.setIcon(inactive_up);
		u2.setBounds(166, 52, 107, 55);
		homePanel.add(u2);
		
		u3 = new JLabel("");
		u3.setIcon(inactive_up);
		u3.setBounds(294, 52, 107, 55);
		homePanel.add(u3);
		
		u4 = new JLabel("");
		u4.setIcon(inactive_up);
		u4.setBounds(421, 52, 107, 55);
		homePanel.add(u4);
		
		u5 = new JLabel("");
		u5.setIcon(inactive_up);
		u5.setBounds(552, 52, 107, 55);
		homePanel.add(u5);
		
		u6 = new JLabel("");
		u6.setIcon(inactive_up);
		u6.setBounds(683, 52, 107, 55);
		homePanel.add(u6);
		
		d1 = new JLabel("");
		d1.setIcon(inactive_down);
		d1.setBounds(38, 243, 107, 55);
		homePanel.add(d1);
		
		d2 = new JLabel("");
		d2.setIcon(inactive_down);
		d2.setBounds(166, 243, 107, 55);
		homePanel.add(d2);
		
		d3 = new JLabel("");
		d3.setIcon(inactive_down);
		d3.setBounds(294, 243, 107, 55);
		homePanel.add(d3);
		
		d4 = new JLabel("");
		d4.setIcon(inactive_down);
		d4.setBounds(421, 243, 107, 55);
		homePanel.add(d4);
		
		d5 = new JLabel("");
		d5.setIcon(inactive_down);
		d5.setBounds(552, 243, 107, 55);
		homePanel.add(d5);
		
		d6 = new JLabel("");
		d6.setIcon(inactive_down);
		d6.setBounds(683, 243, 107, 55);
		homePanel.add(d6);
		
		JLabel lblStation = new JLabel("STATION 1");
		lblStation.setHorizontalAlignment(SwingConstants.CENTER);
		lblStation.setBounds(38, 36, 107, 14);
		homePanel.add(lblStation);
		
		JLabel lblStation_1 = new JLabel("STATION 2");
		lblStation_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblStation_1.setBounds(166, 36, 107, 14);
		homePanel.add(lblStation_1);
		
		JLabel lblStation_2 = new JLabel("STATION 3");
		lblStation_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblStation_2.setBounds(294, 36, 107, 14);
		homePanel.add(lblStation_2);
		
		JLabel lblStation_3 = new JLabel("STATION 4");
		lblStation_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblStation_3.setBounds(421, 36, 107, 14);
		homePanel.add(lblStation_3);
		
		JLabel lblStation_4 = new JLabel("STATION 5");
		lblStation_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblStation_4.setBounds(552, 36, 107, 14);
		homePanel.add(lblStation_4);
		
		JLabel lblStation_5 = new JLabel("STATION 6");
		lblStation_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblStation_5.setBounds(683, 36, 107, 14);
		homePanel.add(lblStation_5);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(new Color(0, 51, 255));
		panel_8.setBounds(0, 0, 829, 25);
		homePanel.add(panel_8);
		
		JLabel lblStationMonitor = new JLabel("STATION MONITOR");
		panel_8.add(lblStationMonitor);
		lblStationMonitor.setForeground(Color.WHITE);
		lblStationMonitor.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStationMonitor.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnNewButton_1 = new JButton("SERIAL DATA LOG");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sm.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(294, 309, 234, 27);
		homePanel.add(btnNewButton_1);
		
		//-------------------------------------------------------------------------------------
		// VOTERS PANEL
		//-------------------------------------------------------------------------------------		
		votersPanel = new JPanel();
		votersPanel.setBackground(SystemColor.control);
		tabbedPane.addTab("VOTERS", null, votersPanel, null);
		votersPanel.setLayout(null);
		
		voterInfoPanel = new JPanel();
		voterInfoPanel.setBackground(SystemColor.control);
		voterInfoPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		voterInfoPanel.setBounds(590, 11, 229, 325);
		votersPanel.add(voterInfoPanel);
		voterInfoPanel.setLayout(null);
		
		profilePictureVoter = new JLabel("");
		profilePictureVoter.setIcon(rescaleImageLarge(defaultProf));
		profilePictureVoter.setBounds(10, 37, 209, 209);
		voterInfoPanel.add(profilePictureVoter);
		
		final JLabel nameLabelVoter = new JLabel("LASTNAME, FIRSTNAME");
		nameLabelVoter.setForeground(Color.WHITE);
		nameLabelVoter.setFont(new Font("Tahoma", Font.BOLD, 13));
		nameLabelVoter.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabelVoter.setBounds(0, 257, 229, 26);
		voterInfoPanel.add(nameLabelVoter);
		
		panel_5 = new JPanel();
		panel_5.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_5.setBackground(new Color(0, 51, 255));
		panel_5.setBounds(0, 0, 229, 26);
		voterInfoPanel.add(panel_5);
		
		label_5 = new JLabel("INFORMATION");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_5.add(label_5);
		
		panel_10 = new JPanel();
		panel_10.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_10.setLayout(null);
		panel_10.setBackground(new Color(0, 51, 255));
		panel_10.setBounds(0, 257, 229, 68);
		voterInfoPanel.add(panel_10);
		
		final JLabel votedLabel = new JLabel("NOT VOTED");
		votedLabel.setBounds(10, 42, 209, 26);
		panel_10.add(votedLabel);
		votedLabel.setForeground(Color.LIGHT_GRAY);
		votedLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		votedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		final JLabel stationLabel = new JLabel("STATION 0");
		stationLabel.setForeground(Color.WHITE);
		stationLabel.setBounds(0, 25, 229, 20);
		panel_10.add(stationLabel);
		stationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		stationLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setBounds(10, 11, 570, 279);
		votersPanel.add(tableScrollPane);
		
		voterTable = new JTable();
		voterTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = voterTable.getSelectedRow();
				String id = voterTable.getModel().getValueAt(row, 0).toString();
				profilePictureVoter.setIcon(rescaleImageLarge(data.getVoterPicture(id)));
				
				String info[] = data.getVoterInfo(id);
				nameLabelVoter.setText(info[0]);
				stationLabel.setText(info[1]);
				votedLabel.setText(info[2]);
			}
		});
		tableScrollPane.setViewportView(voterTable);
		voterTable.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		
		addVoter = new JButton("");
		addVoter.setIcon(new ImageIcon(UserInterface.class.getResource("/server/codes/img/a1.png")));
		addVoter.setRolloverIcon(new ImageIcon(UserInterface.class.getResource("/server/codes/img/a2.png")));
		addVoter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddVoterFrame avf = new AddVoterFrame(data,UserInterface.this);
				avf.setVisible(true);
			}
		});
		addVoter.setBounds(10, 301, 279, 35);
		votersPanel.add(addVoter);
		
		deleteVoter = new JButton("");
		deleteVoter.setRolloverIcon(new ImageIcon(getClass().getResource("img/d2.png")));
		deleteVoter.setIcon(new ImageIcon(getClass().getResource("img/d1.png")));
		deleteVoter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = -1;
				row = voterTable.getSelectedRow();
				if(row==-1){
					JOptionPane.showMessageDialog(UserInterface.this, "Please select a specific entry to delete.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String id = voterTable.getModel().getValueAt(row, 0).toString();
				data.removeVoter(id);
				updateVotersTable();
			}
		});
		deleteVoter.setBounds(299, 301, 281, 35);
		votersPanel.add(deleteVoter);
		JTableHeader votersHeader = voterTable.getTableHeader();
		votersHeader.setBackground(new Color(0,51,255));
		votersHeader.setForeground(Color.white);
		votersHeader.setFont(new Font("Tahoma", Font.BOLD, 12));
		

		
		//-------------------------------------------------------------------------------------
		// CANDIDATE PANEL
		//-------------------------------------------------------------------------------------			
		
		candidatePanel = new JPanel();
		tabbedPane.addTab("CANDIDATES", null, candidatePanel, null);
		candidatePanel.setLayout(null);
		
		candidatesPane = new JScrollPane();
		candidatesPane.setBounds(10, 11, 568, 279);
		candidatePanel.add(candidatesPane);
		
		candidatesTable = new JTable();
		candidatesTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int row = candidatesTable.getSelectedRow();
				String fname = candidatesTable.getModel().getValueAt(row, 0).toString();
				profilePictureCandidate.setIcon(rescaleImageLarge(data.getCandidatePicture(fname)));
				
				String info[] = data.getCandidateInfo(fname);
				nameLabelCandidate.setText(info[0]);
				positionLabel.setText(info[1]);
				
				
			}
		});
		candidatesPane.setViewportView(candidatesTable);
		candidatesPane.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		
		addCandidates = new JButton("");
		addCandidates.setRolloverIcon(new ImageIcon(getClass().getResource("img/a2.png")));
		addCandidates.setIcon(new ImageIcon(getClass().getResource("img/a1.png")));
		addCandidates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddCandidateFrame acf = new AddCandidateFrame(data,UserInterface.this);
				acf.setVisible(true);
				
			}
		});
		addCandidates.setBounds(10, 301, 279, 35);
		candidatePanel.add(addCandidates);
		
		deleteCandidates = new JButton("");
		deleteCandidates.setIcon(new ImageIcon(getClass().getResource("img/d1.png")));
		deleteCandidates.setRolloverIcon(new ImageIcon(getClass().getResource("img/d2.png")));
		deleteCandidates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = -1;
				row = candidatesTable.getSelectedRow();
				if(row==-1){
					JOptionPane.showMessageDialog(UserInterface.this, "Please select a specific entry to delete.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String fn = candidatesTable.getModel().getValueAt(row, 0).toString();
				data.removeCandidate(fn);
				updateCandidatesTable();
			}
		});

		deleteCandidates.setBounds(299, 301, 279, 35);
		candidatePanel.add(deleteCandidates);
		
		candidateInfoPanel = new JPanel();
		candidateInfoPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		candidateInfoPanel.setBackground(SystemColor.control);
		candidateInfoPanel.setBounds(590, 11, 229, 325);
		candidatePanel.add(candidateInfoPanel);
		candidateInfoPanel.setLayout(null);
		
		profilePictureCandidate = new JLabel("");
		profilePictureCandidate.setIcon(rescaleImageLarge(defaultProf));
		profilePictureCandidate.setBounds(10, 37, 209, 209);
		candidateInfoPanel.add(profilePictureCandidate);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_4.setBackground(new Color(0, 51, 255));
		panel_4.setBounds(0, 0, 229, 26);
		candidateInfoPanel.add(panel_4);
		
		lblInformation = new JLabel("INFORMATION");
		lblInformation.setForeground(Color.WHITE);
		lblInformation.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_4.add(lblInformation);
		
		panel_9 = new JPanel();
		panel_9.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_9.setBackground(new Color(0, 51, 255));
		panel_9.setBounds(0, 257, 229, 68);
		candidateInfoPanel.add(panel_9);
		panel_9.setLayout(null);
		
		nameLabelCandidate = new JLabel("LASTNAME, FIRSTNAME");
		nameLabelCandidate.setForeground(Color.WHITE);
		nameLabelCandidate.setBounds(0, 11, 229, 26);
		panel_9.add(nameLabelCandidate);
		nameLabelCandidate.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabelCandidate.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		positionLabel = new JLabel("POSITION");
		positionLabel.setForeground(Color.WHITE);
		positionLabel.setBounds(0, 37, 229, 20);
		panel_9.add(positionLabel);
		positionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		positionLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JTableHeader candidatesHeader = candidatesTable.getTableHeader();
		candidatesHeader.setBackground(new Color(0,51,255));
		candidatesHeader.setForeground(Color.white);
		candidatesHeader.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		
		//-------------------------------------------------------------------------------------
		// STATISTICS PANEL
		//-------------------------------------------------------------------------------------	
		statPanel = new JPanel();
		statPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("LEADERBOARD", null, statPanel, null);
		statPanel.setLayout(null);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(0, 51, 255));
		panel_7.setBounds(0, 0, 829, 34);
		statPanel.add(panel_7);
		
		JLabel lblLeadingCandidates = new JLabel("LEADING CANDIDATES");
		lblLeadingCandidates.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLeadingCandidates.setForeground(Color.WHITE);
		panel_7.add(lblLeadingCandidates);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(10, 89, 253, 36);
		statPanel.add(scrollPane_6);
		
		t1 = new JTable();
		JTableHeader h1 = t1.getTableHeader();
		h1.setBackground(new Color(0,51,255));
		h1.setForeground(Color.white);
		h1.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		scrollPane_6.setViewportView(t1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 192, 253, 36);
		statPanel.add(scrollPane);
		
		t4 = new JTable();
		JTableHeader h4 = t4.getTableHeader();
		h4.setBackground(new Color(0,51,255));
		h4.setForeground(Color.white);
		h4.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		scrollPane.setViewportView(t4);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 294, 253, 36);
		statPanel.add(scrollPane_3);
		
		t7 = new JTable();
		JTableHeader h7 = t7.getTableHeader();
		h7.setBackground(new Color(0,51,255));
		h7.setForeground(Color.white);
		h7.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		scrollPane_3.setViewportView(t7);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(566, 89, 253, 36);
		statPanel.add(scrollPane_1);
		
		t3 = new JTable();
		JTableHeader h3 = t3.getTableHeader();
		h3.setBackground(new Color(0,51,255));
		h3.setForeground(Color.white);
		h3.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		scrollPane_1.setViewportView(t3);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(566, 192, 253, 36);
		statPanel.add(scrollPane_2);
		
		t6 = new JTable();
		JTableHeader h6 = t6.getTableHeader();
		h6.setBackground(new Color(0,51,255));
		h6.setForeground(Color.white);
		h6.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		scrollPane_2.setViewportView(t6);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(566, 294, 253, 36);
		statPanel.add(scrollPane_4);
		
		t9 = new JTable();
		JTableHeader h9 = t9.getTableHeader();
		h9.setBackground(new Color(0,51,255));
		h9.setForeground(Color.white);
		h9.setFont(new Font("Tahoma", Font.BOLD, 10));
		scrollPane_4.setViewportView(t9);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(287, 89, 253, 36);
		statPanel.add(scrollPane_5);
		
		t2 = new JTable();
		JTableHeader h2 = t2.getTableHeader();
		h2.setBackground(new Color(0,51,255));
		h2.setForeground(Color.white);
		h2.setFont(new Font("Tahoma", Font.BOLD, 10));
		scrollPane_5.setViewportView(t2);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(287, 192, 253, 36);
		statPanel.add(scrollPane_7);
		
		t5 = new JTable();
		JTableHeader h5 = t5.getTableHeader();
		h5.setBackground(new Color(0,51,255));
		h5.setForeground(Color.white);
		h5.setFont(new Font("Tahoma", Font.BOLD, 10));
		scrollPane_7.setViewportView(t5);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(287, 294, 253, 36);
		statPanel.add(scrollPane_8);
		
		t8 = new JTable();
		JTableHeader h8 = t8.getTableHeader();
		h8.setBackground(new Color(0,51,255));
		h8.setForeground(Color.white);
		h8.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		scrollPane_8.setViewportView(t8);
		
		tit1 = new JLabel("");
		tit1.setBounds(10, 45, 253, 42);
		statPanel.add(tit1);
		
		tit2 = new JLabel("");
		tit2.setBounds(287, 45, 253, 42);
		statPanel.add(tit2);
		
		tit3 = new JLabel("");
		tit3.setBounds(566, 45, 253, 42);
		statPanel.add(tit3);
		
		tit4 = new JLabel("");
		tit4.setBounds(10, 150, 253, 42);
		statPanel.add(tit4);
		
		tit5 = new JLabel("");
		tit5.setBounds(287, 150, 253, 42);
		statPanel.add(tit5);
		
		tit6 = new JLabel("");
		tit6.setBounds(566, 150, 253, 42);
		statPanel.add(tit6);
		
		tit7 = new JLabel("");
		tit7.setBounds(10, 251, 253, 42);
		statPanel.add(tit7);
		
		tit8 = new JLabel("");
		tit8.setBounds(287, 251, 253, 42);
		statPanel.add(tit8);
		
		tit9 = new JLabel("");
		tit9.setBounds(566, 251, 253, 42);
		statPanel.add(tit9);
		
		tit1.setIcon(ptit1);
		tit2.setIcon(ptit2);
		tit3.setIcon(ptit3);
		tit4.setIcon(ptit4);
		tit5.setIcon(ptit5);
		tit6.setIcon(ptit6);
		tit7.setIcon(ptit7);
		tit8.setIcon(ptit8);
		tit9.setIcon(ptit9);
		
		topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		topPanel.setBounds(0, 0, 854, 260);
		getContentPane().add(topPanel);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("img/HEADER.png")));
		topPanel.add(lblNewLabel);
		
		botPanel = new JPanel();
		botPanel.setBackground(new Color(0, 51, 255));
		botPanel.setBounds(0, 645, 854, 31);
		getContentPane().add(botPanel);
		botPanel.setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;

		setLocation(x, y);
		
		updateVotersTable();
		updateCandidatesTable();
		updateVotersLeaderBoard();
		
		sm = new SerialMonitor();
		sm.setVisible(false);
		
		s.associate(this, this.data);
		s.sendCandidatesToMachine();
		
	}
	public void setAllInactive(){
		for(int i=1;i<=6;i++){
			setStationStatus(i, false);
		}
		homePanel.repaint();
	}
	
	public void setOnlyActive(int stationNum){
		for(int i=1;i<=6;i++){
			if(i==stationNum){
				setStationStatus(i, true);
			}
			else{
				setStationStatus(i, false);
			}
		}
		homePanel.repaint();
	}
	
	public void setStationStatus(int stationNumber, boolean active){
		switch(stationNumber){
		case 1:
			if(active){
				u1.setIcon(active_up);
				d1.setIcon(active_down);
				s1Count.setForeground(new Color(30, 144,255));
				vtd1.setForeground(new Color(30, 144,255));
			}
			else{
				u1.setIcon(inactive_up);
				d1.setIcon(inactive_down);
				s1Count.setForeground(Color.DARK_GRAY);
				vtd1.setForeground(Color.DARK_GRAY);
			}
			break;
		case 2:
			if(active){
				u2.setIcon(active_up);
				d2.setIcon(active_down);
				s2Count.setForeground(new Color(30, 144,255));
				vtd2.setForeground(new Color(30, 144,255));
			}
			else{
				u2.setIcon(inactive_up);
				d2.setIcon(inactive_down);
				s2Count.setForeground(Color.DARK_GRAY);
				vtd2.setForeground(Color.DARK_GRAY);
			}
			break;
		case 3:
			if(active){
				u3.setIcon(active_up);
				d3.setIcon(active_down);
				s3Count.setForeground(new Color(30, 144,255));
				vtd3.setForeground(new Color(30, 144,255));
			}
			else{
				u3.setIcon(inactive_up);
				d3.setIcon(inactive_down);
				s3Count.setForeground(Color.DARK_GRAY);
				vtd3.setForeground(Color.DARK_GRAY);
			}
			break;
		case 4:
			if(active){
				u4.setIcon(active_up);
				d4.setIcon(active_down);
				s4Count.setForeground(new Color(30, 144,255));
				vtd4.setForeground(new Color(30, 144,255));
			}
			else{
				u4.setIcon(inactive_up);
				d4.setIcon(inactive_down);
				s4Count.setForeground(Color.DARK_GRAY);
				vtd4.setForeground(Color.DARK_GRAY);
			}
			break;
		case 5:
			if(active){
				u5.setIcon(active_up);
				d5.setIcon(active_down);
				s5Count.setForeground(new Color(30, 144,255));
				vtd5.setForeground(new Color(30, 144,255));
			}
			else{
				u5.setIcon(inactive_up);
				d5.setIcon(inactive_down);
				s5Count.setForeground(Color.DARK_GRAY);
				vtd5.setForeground(Color.DARK_GRAY);
			}
			break;
		case 6:
			if(active){
				u6.setIcon(active_up);
				d6.setIcon(active_down);
				s6Count.setForeground(new Color(30, 144,255));
				vtd6.setForeground(new Color(30, 144,255));
			}
			else{
				u6.setIcon(inactive_up);
				d6.setIcon(inactive_down);
				s6Count.setForeground(Color.DARK_GRAY);
				vtd6.setForeground(Color.DARK_GRAY);
			}
			break;
		default:
			break;
			
			
		}
	}
	
	public void incrementStationCount(int stationNumber){
		switch (stationNumber) {
		case 1:
			stationCounts[0]++;
			s1Count.setText(Integer.toString(stationCounts[0]));
			break;
		case 2:
			stationCounts[1]++;
			s2Count.setText(Integer.toString(stationCounts[1]));
			break;
		case 3:
			stationCounts[2]++;
			s3Count.setText(Integer.toString(stationCounts[2]));
			break;
		case 4:
			stationCounts[3]++;
			s4Count.setText(Integer.toString(stationCounts[3]));
			break;
		case 5:
			stationCounts[4]++;
			s5Count.setText(Integer.toString(stationCounts[4]));
			break;
		case 6:
			stationCounts[5]++;
			s6Count.setText(Integer.toString(stationCounts[5]));
			break;

		default:
			break;
		}
	}
	public void updateVotersTable(){
		voterTable.setModel(DbUtils.resultSetToTableModel(data.getVotersTableData()));
	}
	
	public void updateCandidatesTable(){
		candidatesTable.setModel(DbUtils.resultSetToTableModel(data.getCandidatesTableData()));
	}
	
	public ImageIcon rescaleImageLarge(ImageIcon img){
		Image temp = img.getImage();  
		Image newImage = temp.getScaledInstance(209, 209,  java.awt.Image.SCALE_SMOOTH);	  
		return new ImageIcon(newImage); 
	}
	public void writeToSerialMonitor(String data){
		sm.printToLog(data);
	}
	public void updateVotersLeaderBoard(){
		t1.setModel(DbUtils.resultSetToTableModel(data.getLead1()));
		t2.setModel(DbUtils.resultSetToTableModel(data.getLead2()));
		t3.setModel(DbUtils.resultSetToTableModel(data.getLead3()));
		t4.setModel(DbUtils.resultSetToTableModel(data.getLead4()));
		t5.setModel(DbUtils.resultSetToTableModel(data.getLead5()));
		t6.setModel(DbUtils.resultSetToTableModel(data.getLead6()));
		t7.setModel(DbUtils.resultSetToTableModel(data.getLead7()));
		t8.setModel(DbUtils.resultSetToTableModel(data.getLead8()));
		t9.setModel(DbUtils.resultSetToTableModel(data.getLead9()));
	}
}
