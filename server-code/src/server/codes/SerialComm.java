package server.codes;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Enumeration;

public class SerialComm{
	private SerialMonitor sm;
	private DataManager dm;
	private UserInterface ui;
	
	
	SerialPort serialPort;
	private BufferedReader input;
	private PrintStream output;
	private static final int TIME_OUT = 5000;
	private static final int DATA_RATE = 9600;
	private boolean isOk;
	private boolean isAssociated = false;
	
	String positions[] = {"PRESIDENT", "V-PRESIDENT INT", "V-PRESIDENT EXT", "SECRETARY", "ASSOC. SECRETARY", "TREASURER", "AUDITOR", "PIO INT", "PIO EXT"};
	public SerialComm(String commPort){
		isOk = false;
		System.out.println("Connecting to "+commPort);
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		
		
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			if (currPortId.getName().equals(commPort)) {
				portId = currPortId;
				break;
			}
		}
		
		
		
		if (portId == null) {
			System.out.println("Port not found.");
			return;
		}
		
		System.out.println("Port found.");

		try {
			
			
			serialPort = (SerialPort) portId.open(getClass().getName(),TIME_OUT);
			serialPort.setSerialPortParams(DATA_RATE,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			System.out.println("Port opened.");


			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = new PrintStream(serialPort.getOutputStream(), true);

			
			serialPort.addEventListener(new SerialPortEventListener() {
				public synchronized void serialEvent(SerialPortEvent sEvent) {
					
					//if there are data available from the serial
					if (sEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
						try {
							
							String incomming =input.readLine();
							
							if(isAssociated){
								respondTo(incomming);
							}
							else{
								System.out.println(incomming);
							}
							
							
						} catch (Exception e) {
							System.out.println("Error in responding to serial event. "+e.getMessage());
							
						}
						
					}
					
				}
			});
			
			serialPort.notifyOnDataAvailable(true);
			isOk = true;
			System.out.println("ALL IS SETUPED ..");
			

		} catch (Exception e) {
			System.out.println("Port is unavailable.."+e.getMessage());
		}
		
	}
	public void associate(UserInterface ui,DataManager dm){
		this.ui = ui;
		this.dm = dm;
		isAssociated = true;
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	public boolean getStatus(){
		return isOk;
	}
	public void respondTo(String dataIn){
		char firstChar = dataIn.charAt(0);
		switch(firstChar){
		case 'S':
			ui.setOnlyActive(Integer.parseInt(dataIn.charAt(1)+""));
			ui.writeToSerialMonitor("Active voter at station "+dataIn.charAt(1));
			break;
		case 'L':
			ui.writeToSerialMonitor("Requesting login ( ID:"+dataIn.substring(1,9)+" PIN:"+dataIn.substring(9,13)+" )");
			boolean voterOk = dm.validateVoter(dataIn.substring(1,9), dataIn.substring(9,13));
			
			if(voterOk){
				output.print("o");
				ui.writeToSerialMonitor("Server reply: AFFIRMATIVE");
				dm.setAsVoted(dataIn.substring(1,9), dataIn.substring(9,13));
			}else{
				output.print("x");
				ui.writeToSerialMonitor("Server reply: NEGATIVE");
			}
			break;
		case 'U':
			ui.writeToSerialMonitor("Vote data received (CODE:"+dataIn+")");
			for(int i=0;i<9;i++){
				String pos = positions[i];
				int choice = Integer.parseInt(dataIn.charAt(i+2)+"");
				
				dm.updateCandidate(pos, choice);
			}
			ui.updateVotersLeaderBoard();
			ui.incrementStationCount(Integer.parseInt(dataIn.charAt(1)+""));
			ui.setAllInactive();
			break;
			
		default:
			ui.writeToSerialMonitor(dataIn);
			break;
		}
	}
	
	public synchronized void sendCandidatesToMachine(){
		String data[][] = dm.getCandidatesList();
		String formatedData = "";
		
		for(int i=0;i<9;i++){
			for(int j=0;j<2;j++){
				formatedData += data[i][j];
				if(j==0){
					formatedData += "_";
				}
			}
			if(i<8){
				formatedData += "-";
			}else{
				formatedData += ".";
			}
		}
		try {
			output.print(formatedData);
			System.out.println(formatedData);
		} catch (Exception e) {
			System.out.println("Error encountered in sending candidates. "+e );
		}
		
	}
	
	

}
