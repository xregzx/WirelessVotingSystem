package server.codes;

public class Main {
	static UserInterface mainFrame;
	static DataManager dManager;
	static SerialComm sComm;
	private static final String UNAME = "admin", PASS = "letmein";
	public static void main(String[] args) {
		dManager = new DataManager();
		
		LoginFrame login = new LoginFrame(UNAME,PASS,dManager);
		login.setVisible(true);
	}
}
