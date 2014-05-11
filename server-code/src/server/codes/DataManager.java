package server.codes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
public class DataManager {
	private Connection con;
	private String dbURL = "jdbc:sqlite:C:\\Users\\dell\\Documents\\Eclipse Projects\\election_data.sqlite";
	private boolean connected = false;
	public DataManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(dbURL);
			connected = true;
			System.out.println("Connected.");
			
		} catch (Exception e) {
			System.out.println("Error connecting.");
			connected = false;
		}

	}
	public boolean isConnected(){
		return connected;
	}
	public Connection getConnection(){
		return con;
	}
	public ResultSet getVotersTableData(){
		ResultSet output = null;
		String query = "Select ID,PIN,\"FIRST NAME\",\"LAST NAME\" from voters";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered in getting voters data.");
		}
		
		return output;
		
	}
	public ResultSet getCandidatesTableData(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\", POSITION from candidates";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered in getting candidates data.");
		}
		
		return output;
		
	}
	
	public ImageIcon getVoterPicture(String id){
		ResultSet output = null;
		ImageIcon pic;
		String query = "Select PICTURE from voters where ID="+id;
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
			byte[] imageArray = output.getBytes("PICTURE");
			pic = new ImageIcon(imageArray);
			
		} catch (Exception e) {
			System.out.println("Error encountered in getting voter pic.");
			pic = new ImageIcon(getClass().getResource("img/default.jpg"));
		}
		
		return pic;
	}
	
	public ImageIcon getCandidatePicture(String fname){
		ResultSet output = null;
		ImageIcon pic;
		String query = "Select PICTURE from candidates where \"FIRST NAME\"=\""+fname+"\"";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
			byte[] imageArray = output.getBytes("PICTURE");
			pic = new ImageIcon(imageArray);
			
		} catch (Exception e) {
			System.out.println("Error encountered in getting cand pic");
			pic = new ImageIcon(getClass().getResource("img/default.jpg"));
		}
		
		return pic;
	}
	
	public String[] getVoterInfo(String id){
		String out[] = new String[3];
		String query = "Select \"FIRST NAME\",\"LAST NAME\",\"STATION\",VOTED from voters where ID="+id;
		
		try {
			Statement s = con.createStatement();
			ResultSet output = s.executeQuery(query);
			String fname = output.getString("FIRST NAME");
			String lname = output.getString("LAST NAME");
			String station = output.getString("STATION");
			String voted = output.getString("VOTED");
			
			out[0] = lname+", "+fname;
			out[1] = "STATION "+station;
			if(voted.equals("FALSE")){
				out[2] = "NOT VOTED";
			}
			else{
				out[2] = "VOTED";
			}
			
			
		} catch (Exception e) {
			System.out.println("Error encountered in getting voter info.");
			out[0] = "Lastname, Firstname";
			out[1] = "Station 0";
			out[2] = "Unknown";
		}
		
		return out;
	}
	
	public String[] getCandidateInfo(String n){
		String out[] = new String[2];
		String query = "Select \"FIRST NAME\",\"LAST NAME\",POSITION from candidates where  \"FIRST NAME\"=\""+n+"\"";
		
		try {
			Statement s = con.createStatement();
			ResultSet output = s.executeQuery(query);
			String fname = output.getString("FIRST NAME");
			String lname = output.getString("LAST NAME");
			String position = output.getString("POSITION");
			
			out[0] = lname+", "+fname;
			out[1] = position;
			
			
		} catch (Exception e) {
			System.out.println("Error encountered in getting candidate info.");
			out[0] = "Lastname, Firstname";
			out[1] = "POSITION";
		}
		
		return out;
	}
	public void addNewVoter(String id, String pin, String fname, String lname, String station,byte[] img){
		String query = "INSERT INTO VOTERS  (ID, PIN, \"FIRST NAME\", \"LAST NAME\",  \"STATION\", \"PICTURE\") VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, id);
			st.setString(2, pin);
			st.setString(3, fname);
			st.setString(4, lname);
			st.setString(5, station);
			st.setBytes(6, img);
			st.execute();
			
		} catch (Exception e) {
			System.out.println("Error encountered while adding.");
		}
		
		
	}
	
	public void addNewCandidate(String fname, String lname, String position,byte[] img, int posInd, int cNum){
		String query = "INSERT INTO CANDIDATES  (\"FIRST NAME\", \"LAST NAME\",  POSITION, \"PICTURE\", \"POSITION INDEX\", CHOICE) VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, fname);
			st.setString(2, lname);
			st.setString(3, position);
			st.setBytes(4, img);
			st.setInt(5, posInd);
			st.setInt(6, cNum);
			st.execute();
			
		} catch (Exception e) {
			System.out.println("Error encountered while adding.");
		}
		
		
	}
	
	public void removeVoter(String id){
		String query = "DELETE FROM VOTERS WHERE ID="+id;
		try {
			Statement s = con.createStatement();
			s.executeQuery(query);
			
		} catch (Exception e) {
			System.out.println("Error encountered while deleting.");
		}
	}
	public void removeCandidate(String fn) {
		String query = "DELETE FROM CANDIDATES WHERE \"FIRST NAME\"=\""+fn+"\"";
		try {
			Statement s = con.createStatement();
			s.executeQuery(query);
			
		} catch (Exception e) {
			System.out.println("Error encountered while deleting.");
		}
		
	}
	public boolean validateVoter(String id, String pin) {
		String query = "SELECT PIN, VOTED FROM VOTERS WHERE ID="+id;
		try {
			Statement s = con.createStatement();
			ResultSet output = s.executeQuery(query);
			String vPin = output.getString("PIN");
			String voted = output.getString("VOTED");
			
			if(vPin.equals(pin) && voted.equals("FALSE")){
				return true;
			}
			else{
				return false;
			}
			
			
		} catch (Exception e) {
			System.out.println("error validating ..");
			return false;
		}
	}
	public synchronized void updateCandidate(String pos, int choice) {
		String query = "UPDATE CANDIDATES SET VOTES=VOTES+1 WHERE POSITION=\""+pos+"\" AND CHOICE="+choice;
		try {
			Statement s = con.createStatement();
			s.executeQuery(query);
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	public String[][] getCandidatesList() {
		
		String q1 = "SELECT \"FIRST NAME\", \"LAST NAME\" FROM CANDIDATES WHERE CHOICE=1 ORDER BY \"POSITION INDEX\" ASC";
		String choice_1[] = new String[9];
		
		try {
			Statement s = con.createStatement();
			ResultSet output1 = s.executeQuery(q1);
			
			int i1 = 0;
			while(output1.next()){
				String fname_temp = output1.getString(1);
				String lname = output1.getString(2);
				
				String fname = "";
				StringTokenizer st = new StringTokenizer(fname_temp);
				
				while(st.hasMoreTokens()){
					fname += st.nextToken().charAt(0);
				}
				choice_1[i1++] = lname+", "+fname;
			}
			
		} catch (Exception e) {
			System.out.println("Error encountered at getting choice 1 candidates.");
		}
		
		
		String q2 = "SELECT \"FIRST NAME\", \"LAST NAME\" FROM CANDIDATES WHERE CHOICE=2 ORDER BY \"POSITION INDEX\" ASC";
		String choice_2[] = new String[9];
		
		try {
			Statement s = con.createStatement();
			ResultSet output2 = s.executeQuery(q2);
			
			int i2 = 0;
			while(output2.next()){
				String fname_temp = output2.getString(1);
				String lname = output2.getString(2);
				String fname = "";
				StringTokenizer st = new StringTokenizer(fname_temp);
				while(st.hasMoreTokens()){
					fname += st.nextToken().charAt(0);
				}
				
				choice_2[i2++] = lname+", "+fname;
			}
			
		} catch (Exception e) {
			System.out.println("Error encountered at getting choice 2 candidates.");
		}
		
		String output[][] = new String[9][2];
		for(int a=0;a<9;a++){
			output[a][0] = choice_1[a];
			output[a][1] = choice_2[a];
		}
		
		return output;
		
	}
	
	public ResultSet getLead1(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\",VOTES FROM CANDIDATES WHERE \"POSITION\"=\"PRESIDENT\" ORDER BY VOTES DESC LIMIT 1";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered. getLead1()");
		}
		
		return output;
		
	}
	public ResultSet getLead2(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\",VOTES FROM CANDIDATES WHERE \"POSITION\"=\"V-PRESIDENT INT\" ORDER BY VOTES DESC LIMIT 1";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered. getLead2()");
		}
		
		return output;
		
	}
	public ResultSet getLead3(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\",VOTES FROM CANDIDATES WHERE \"POSITION\"=\"V-PRESIDENT EXT\" ORDER BY VOTES DESC LIMIT 1";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered. getLead3()");
		}
		
		return output;
		
	}
	public ResultSet getLead4(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\",VOTES FROM CANDIDATES WHERE \"POSITION\"=\"SECRETARY\" ORDER BY VOTES DESC LIMIT 1";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered. getLead4()");
		}
		
		return output;
		
	}
	public ResultSet getLead5(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\",VOTES FROM CANDIDATES WHERE \"POSITION\"=\"ASSOC. SECRETARY\" ORDER BY VOTES DESC LIMIT 1";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered. getLead5()");
		}
		
		return output;
		
	}
	public ResultSet getLead6(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\",VOTES FROM CANDIDATES WHERE \"POSITION\"=\"TREASURER\" ORDER BY VOTES DESC LIMIT 1";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered. getLead6()");
		}
		
		return output;
		
	}
	public ResultSet getLead7(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\",VOTES FROM CANDIDATES WHERE \"POSITION\"=\"AUDITOR\" ORDER BY VOTES DESC LIMIT 1";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered. getLead7()");
		}
		
		return output;
		
	}
	public ResultSet getLead8(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\",VOTES FROM CANDIDATES WHERE \"POSITION\"=\"PIO INT\" ORDER BY VOTES DESC LIMIT 1";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered. getLead8()");
		}
		
		return output;
		
	}
	public ResultSet getLead9(){
		ResultSet output = null;
		String query = "Select \"FIRST NAME\",\"LAST NAME\",VOTES FROM CANDIDATES WHERE \"POSITION\"=\"PIO EXT\" ORDER BY VOTES DESC LIMIT 1";
		
		try {
			Statement s = con.createStatement();
			output = s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered. getLead9()");
		}
		
		return output;
		
	}
	public void setAsVoted(String id, String pin){
		String query = "UPDATE VOTERS SET VOTED=\"TRUE\" WHERE ID="+id+" AND PIN="+pin;
		
		try {
			Statement s = con.createStatement();
			s.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error encountered in setting "+id+" to voted.");
		}
	}

}
