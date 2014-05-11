#include <SoftwareSerial.h>
#include <avr/pgmspace.h>
// #include <SD.h>

#define Serialprint(format, ...) StreamPrint_progmem(Serial,PSTR(format),##__VA_ARGS__)
#define Streamprint(stream,format, ...) StreamPrint_progmem(stream,PSTR(format),##__VA_ARGS__)

prog_char string_0[] PROGMEM = "\033E";
prog_char string_1[] PROGMEM = " PLEASE SELECT YOUR ";
prog_char string_2[] PROGMEM = "   STATION NUMBER   ";
prog_char string_3[] PROGMEM = "--------------------";
prog_char string_4[] PROGMEM = " Press # when done  ";
prog_char string_5[] PROGMEM = "      WIRELESS      ";
prog_char string_6[] PROGMEM = "   VOTING SYSTEM    ";
prog_char string_7[] PROGMEM = "      STATION ";
prog_char string_8[] PROGMEM = "  ENTER ID NUMBER:  ";
prog_char string_9[] PROGMEM = "      ";
prog_char string_10[] PROGMEM = "     ENTER PIN:     ";
prog_char string_11[] PROGMEM = "[A]";
prog_char string_12[] PROGMEM = "[B]";
prog_char string_13[] PROGMEM = " CHOICE :           ";
prog_char string_14[] PROGMEM = "                    ";
prog_char string_15[] PROGMEM = "       SORRY        ";
prog_char string_16[] PROGMEM = "    LOGIN FAILED!   ";
prog_char string_17[] PROGMEM = "      SUCCESS!      ";
prog_char string_18[] PROGMEM = " THANKS FOR VOTING. ";
prog_char string_19[] PROGMEM = " WAITING FOR SERVER ";
prog_char string_20[] PROGMEM = "   SERVER FOUND     ";
prog_char string_21[] PROGMEM = "    GETTING DATA    ";
prog_char string_22[] PROGMEM = "  SETUP COMPLETED.  ";
prog_char string_23[] PROGMEM = "       . . .        ";
prog_char string_24[] PROGMEM = "        ";
prog_char string_25[] PROGMEM = "   VALIDATING USER  ";

prog_char pos_1[] PROGMEM = "PRESIDENT";
prog_char pos_2[] PROGMEM = "V-PRESIDENT INT";
prog_char pos_3[] PROGMEM = "V-PRESIDENT EXT";
prog_char pos_4[] PROGMEM = "SECRETARY";
prog_char pos_5[] PROGMEM = "ASSOC. SECRETARY";
prog_char pos_6[] PROGMEM = "TREASURER";
prog_char pos_7[] PROGMEM = "AUDITOR";
prog_char pos_8[] PROGMEM = "PIO INT";
prog_char pos_9[] PROGMEM = "PIO EXT";

PROGMEM const char *string_table[] =
{   
  string_0,string_1,string_2,string_3,string_4,string_5,string_6,string_7,string_8, string_9,string_10,string_11,string_12,string_13,
  string_14,string_15,string_16,string_17,string_18,string_19,string_20,string_21,string_22,string_23,string_24,string_25
};

PROGMEM char *positions[]  = 
{
  pos_1,pos_2,pos_3,pos_4,pos_5,pos_6,pos_7,pos_8,pos_9
};

char buffer[25];
char posBuffer[17];

//data--------------------------------------------------------------------------------------
String candidates[9][2];
char dataFromServer[300];
byte nums[] = { B01100000,B11011010,B11110010,B01100110,B10110110,B10111110};
//variables----------------------------------------------------------------------------------
SoftwareSerial lcdSerial(2,3);
char keyPressed;
int station;
char voteData[12];
char idBuffer[9];
char pinBuffer[5];

//pins---------------------------------------------------------------------------------------
// File myfile;
int sd_cs = 4;
int latch = 5;
int clock = 6;
int data = 7;
int stationSensor = A0;

void StreamPrint_progmem(Print &out,PGM_P format,...);

void setup(){
  
  Serial.begin(9600);
  while (!Serial) {
  }
  lcdSerial.begin(9600);
  pinMode(latch, OUTPUT);
  pinMode(clock, OUTPUT);
  pinMode(data, OUTPUT);
  pinMode(sd_cs, OUTPUT);
  pinMode(stationSensor, INPUT);
  digitalWrite(latch, LOW);
  shiftOut(data, clock, LSBFIRST, nums[0]);
  digitalWrite(latch, HIGH);
  pinMode(10, OUTPUT);
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(14);      //                    //    
  setBufferAndPrint(19);      // WAITING FOR SERVER //
  setBufferAndPrint(23);      //       . . .        //
  setBufferAndPrint(14);      //                    //
  getCandidates();
  assignCandidates();
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(14);      //                    //
  setBufferAndPrint(20);      //   SERVER FOUND     //
  setBufferAndPrint(14);      //                    //
  setBufferAndPrint(14);      //                    //
  delay(500);
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(14);      //                    //
  setBufferAndPrint(21);      //    GETTING DATA    //
  setBufferAndPrint(23);      //       . . .        //
  setBufferAndPrint(14);      //                    //
  delay(1000);
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(14);      //                    //
  setBufferAndPrint(22);      //  SETUP COMPLETED.  //
  setBufferAndPrint(14);      //                    //
  setBufferAndPrint(14);      //                    //
  dispCandidates();
  // if (!SD.begin(sd_cs)) {
  //   Serial.println(F("Card failed, or not present"));
  //   // don't do anything more:
  //   return;
  // }
  Serial.println(F("Machine setup completed."));
  Serialprint("Free RAM: %d Bytes\n",freeRam());
}
// main program loop
void loop(){
  Serialprint("Free RAM: %d Bytes\n",freeRam());
  voteData[0] = 'U';
  station = waitForStation();
  voteData[1] = station+48;
  dispStartMessage();
  delay(1000);
  askVoterID();    
  askPin();
  if(validateUser())
  {
    Serial.print(F("S"));
    Serial.println(station);
    startVoting();      
    dispThankYou();      
    delay(1000);    
    voteData[11] = '\0';
    Serial.println(voteData);
    // logToSD();        
  }
  else
  {
    loginFailed();      
    delay(1000);
  }
  Serialprint("Free RAM: %d Bytes\n",freeRam());  
}

// copy the string to display in a common buffer and then displays it in the lcd
void setBufferAndPrint(int index){
  strcpy_P(buffer, (char*)pgm_read_word(&(string_table[index])));
  Streamprint(lcdSerial,"%s",buffer);
}

//wait for the user to chooce and confirm his station
int waitForStation(){
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(1);      // PLEASE SELECT YOUR //
  setBufferAndPrint(2);      //   STATION NUMBER   //
  setBufferAndPrint(3);      //--------------------//
  setBufferAndPrint(4);      // Press # when done  //
  
  //display the corresponding station number reflected in the potentiometer to the 7-segment
  while(lcdSerial.available()<=0)
  {
    int senseVal = analogRead(stationSensor);
    int temp = map(senseVal,0,1023,0,60);
    
    if(temp>=0 && temp<10){
      digitalWrite(latch, LOW);
      shiftOut(data, clock, LSBFIRST, nums[0]);
      digitalWrite(latch, HIGH);
    }
    else if(temp>=10 && temp<20){
      digitalWrite(latch, LOW);
      shiftOut(data, clock, LSBFIRST, nums[1]);
      digitalWrite(latch, HIGH);
    }
    else if(temp>=20 && temp<30){
      digitalWrite(latch, LOW);
      shiftOut(data, clock, LSBFIRST, nums[2]);
      digitalWrite(latch, HIGH);
    }
    else if(temp>=30 && temp<40){
      digitalWrite(latch, LOW);
      shiftOut(data, clock, LSBFIRST, nums[3]);
      digitalWrite(latch, HIGH);
    }
    else if(temp>=40 && temp<50){
      digitalWrite(latch, LOW);
      shiftOut(data, clock, LSBFIRST, nums[4]);
      digitalWrite(latch, HIGH);
    }
    else{
      digitalWrite(latch, LOW);
      shiftOut(data, clock, LSBFIRST, nums[5]);
      digitalWrite(latch, HIGH);
    }
    
  }

  keyPressed = lcdSerial.read();
  if(keyPressed=='#'){
    int senseVal = analogRead(stationSensor);
    int temp = map(senseVal,0,1023,0,60);
    
    if(temp>=0 && temp<10){
      return 1;
    }
    else if(temp>=10 && temp<20){
      return 2;
    }
    else if(temp>=20 && temp<30){
      return 3;
    }
    else if(temp>=30 && temp<40){
      return 4;
    }
    else if(temp>=40 && temp<50){
      return 5;
    }
    else{
      return 6;
    }
  }
  else{
    waitForStation(); 
  }
}


void dispStartMessage(){
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(5);      //      WIRELESS      //
  setBufferAndPrint(6);      //   VOTING SYSTEM    //
  setBufferAndPrint(3);      //--------------------//
  setBufferAndPrint(7);      //      STATION N     //
  Streamprint(lcdSerial,"%d     ",station);
}

void askVoterID(){
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(8);      //  ENTER ID NUMBER:  //
  setBufferAndPrint(3);      //--------------------//
  setBufferAndPrint(9);      //      nnnnnnnn      //
  for(int i=0;i<8;i++){
    while(lcdSerial.available()<=0);
    keyPressed = lcdSerial.read();
    lcdSerial.print(keyPressed);
    idBuffer[i] = keyPressed;
  }
  delay(300);
  idBuffer[8] = '\0';
}

void askPin(){
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(10);      //     ENTER PIN:     //
  setBufferAndPrint(3);       //--------------------//
  setBufferAndPrint(24);      //       nnnn         //
  for(int i=0;i<4;i++){
    while(lcdSerial.available()<=0);
    keyPressed = lcdSerial.read();
    Streamprint(lcdSerial,"*");
    pinBuffer[i] = keyPressed;
  }
  delay(300);
  pinBuffer[4] = '\0'; 
}

boolean validateUser(){
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(14);      //                    //
  setBufferAndPrint(25);      //  VALIDATING USER   //
  setBufferAndPrint(23);      //       . . .        //
  setBufferAndPrint(14);      //                    //
  delay(500);
  Serial.print(F("L"));
  Serial.print(idBuffer);
  Serial.println(pinBuffer);
  while(Serial.available()<=0);
  char resp = Serial.read();
  if(resp=='o'){
    return true;
  }
  else{
    return false;
  }
}


void startVoting(){
  for(int i=0;i<9;i++){
    displayAndAsk(i);
  }
}

void displayAndAsk(int pos){

  setBufferAndPrint(0);
  delay(100);
  strcpy_P(posBuffer, (char*)pgm_read_word(&(positions[pos])));
  lcdSerial.println(posBuffer);         //            POSITION//
  setBufferAndPrint(3);                                  //--------------------//
  setBufferAndPrint(11);                                 //[A]       candidate1//
  lcdSerial.println(candidates[pos][0]);                 //[B]       candidate2//
  setBufferAndPrint(12);
  lcdSerial.println(candidates[pos][1]);
  while(lcdSerial.available()<=0);
  keyPressed = lcdSerial.read();
  char choice = keyPressed;
  switch(choice){
    case 'A':
      voteData[pos+2] = 1+48;
      dispConfirmation(pos,0);     
      delay(1000);      
      break;
    case 'B':     
      voteData[pos+2] = 2+48;
      dispConfirmation(pos,1);      
      delay(1000);      
      break;    
    default:      
      displayAndAsk(pos);      
      break;
  }
  
}

void dispConfirmation(int pos,int choice){
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(13);      // CHOICE :           //
  setBufferAndPrint(3);       //--------------------//
  Streamprint(lcdSerial," ");
  lcdSerial.println(candidates[pos][choice]);
}

void loginFailed(){
  
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(14);      //                    //
  setBufferAndPrint(15);      //       SORRY        //
  setBufferAndPrint(16);      //    LOGIN FAILED!   //
  setBufferAndPrint(14);      //                    //

}

void dispThankYou(){
  setBufferAndPrint(0);
  delay(100);
  setBufferAndPrint(14);      //                    //
  setBufferAndPrint(17);      //      SUCCESS!      //
  setBufferAndPrint(18);      // THANKS FOR VOTING. //
  setBufferAndPrint(14);      //                    //
  
}


void StreamPrint_progmem(Print &out,PGM_P format,...)
{
  char formatString[128], *ptr;
  strncpy_P( formatString, format, sizeof(formatString) );
  formatString[ sizeof(formatString)-2 ]='\0'; 
  ptr=&formatString[ strlen(formatString)+1 ];
  va_list args;
  va_start (args,format);
  vsnprintf(ptr, sizeof(formatString)-1-strlen(formatString), formatString, args );
  va_end (args);
  formatString[ sizeof(formatString)-1 ]='\0'; 
  out.print(ptr);
}

void waitForServer(){
  Serial.println(F("Waiting for server .."));
  while(1){
    while(Serial.available()<=0);
    char incomming = Serial.read();
    if(incomming == 'S')
    {
      Serial.flush();
      break;
    }
  }
}

//-------------------------------------------------------------------------------------------
// GETTING DATA FROM SERVER
//-------------------------------------------------------------------------------------------
void getCandidates(){
  char inChar;
  int index = 0;
  int exitFlag = 0;

  Serial.println(F("Waiting for candidates .."));
  while(!Serial.available());
  while(1){
    if(Serial.available() > 0) 
    {
      if(index < 299)
      {
        inChar = Serial.read();
        if(inChar == '.'){
          exitFlag = 1;
        }
        dataFromServer[index] = inChar;
        index++;
        dataFromServer[index] = '\0';


      }
      //Serial.print(inChar);
      if(exitFlag==1){
        //Serial.println();
        break; 
      }
    }

  }
  //Serial.println(dataFromServer);
}

void assignCandidates(){
  Serial.println(F("Assigning candidates .."));
  int index = 0;
  int row = 0;
  int col =0;
  char c;
  
  while((c=dataFromServer[index++]) != '.'){       
    if(c=='_'){
      col = 1;
    }else if(c=='-'){
      row++;
      col=0;
    }else{
      candidates[row][col] += c;
    }
  }

}

void dispCandidates(){
  for(int i=0;i<9;i++){
    for(int j=0;j<2;j++){
      Serial.println(candidates[i][j]);
      delay(50);
    }
  }
}

// void logToSD(){
//   myfile = SD.open("datalog.txt", FILE_WRITE);

//   // if the file is available, write to it:
//   if (myfile) {
//     myfile.println(voteData);
//     myfile.close();
//   }  
//   // if the file isn't open, pop up an error:
//   else {
//     Serial.println(F("error opening datalog.txt"));
//   } 

// }

int freeRam () {
  extern int __heap_start, *__brkval;
  int v;
  return (int) &v - (__brkval == 0 ? (int) &__heap_start : (int) __brkval);
}
/*------------------------------------------------------------------
Arduino code for Wireless voting machine 
March 2013
xregzx
---------------------------------------------------------------------*/