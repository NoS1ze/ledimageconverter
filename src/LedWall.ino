#include <FastLED.h>
#include <Regexp.h>
#include <ESP8266WiFi.h>
#include <SPI.h>
#include <string.h>

#include <WiFiUdp.h>

//web Server Set Up
const char* ssid     = "xxxx";
const char* password = "xxxx";
WiFiServer server(80);
String header ="";
String udpMessage ="";

WiFiUDP Udp;
unsigned int localPort = 2390;
char packetBuffer[255]; //buffer to hold incoming packet53407
//char packetBuffer[53407]; //buffer to hold incoming packet53407
char  ReplyBuffer[] = "OK";       // a string to send back



// Current time
unsigned long currentTime = millis();
// Previous time
unsigned long previousTime = 0; 
unsigned long previousRotationTime = 0; 
// Define timeout time in milliseconds (example: 2000ms = 2s)
const long timeoutTime = 100;
//const long allowedStreamLatency = 1000;
unsigned long ledRotationTime = 480000; 
unsigned long nextRenderTime = 0; 

boolean enableRotation = true;
boolean passiveMode = false;


//Led Set Up
#define LED_PIN     5
#define NUM_LEDS    900
uint8_t ledBrightness = 64 ;
#define LED_TYPE    WS2811
#define COLOR_ORDER GRB
CRGB leds[NUM_LEDS];


int updtPerSecond = 100;
int updtPerSecondBase = 100;

#define brightnessPattern  "brightness=(\\d+)"


int currentModeIndex = 3;
boolean modeIndexChanged = true;
#define MODES_SIZE 6
String modes[MODES_SIZE]={"KNVL", "RAINBOW_STRIP", "RANDOM", "WHITE_STRIP", "OFF", "CAT"};
boolean modesState[MODES_SIZE]={false, true, true, true, false, true};

#define KNVL_SIZE 291
const int knvlLogo[] PROGMEM= {87,88,89,90,91,92,93,94,95,96,97,98,99,116,117,118,119,120,121,122,123,124,125,126,127,128,159,160,161,162,163,164,165,166,167,168,169,170,171,188,189,190,191,192,193,194,195,196,197,198,199,200,231,232,233,234,235,236,237,238,239,240,241,242,243,260,261,262,263,264,265,266,267,268,269,270,271,272,302,303,304,305,306,307,308,309,310,311,312,313,314,333,334,335,336,337,338,339,340,341,342,343,344,345,373,374,375,376,377,378,379,380,381,382,383,384,385,407,408,409,410,411,412,413,414,415,416,417,418,419,444,445,446,447,448,449,450,451,452,453,454,455,456,480,481,482,483,484,485,486,487,488,489,490,491,492,514,515,516,517,518,519,520,521,522,523,524,525,526,553,554,555,556,557,558,559,560,561,562,563,564,565,585,586,587,588,589,590,591,592,593,594,595,596,597,620,621,622,623,624,625,626,627,628,629,630,631,632,633,634,635,636,637,638,663,664,665,666,667,668,669,670,671,672,673,674,675,692,693,694,695,696,697,698,699,700,701,702,703,704,735,736,737,738,739,740,741,742,743,744,745,746,747,764,765,766,767,768,769,770,771,772,773,774,775,776,807,808,809,810,811,812,813,814,815,816,817,818,819,837,838,839,840,841,842,843,844,845,846,847,848};


#define CAT_SIZE 289
const int catLogo[] PROGMEM= {58,59,82,83,84,85,133,134,151,152,153,207,208,209,210,221,222,225,226,227,228,229,230,271,272,273,274,275,276,277,278,279,282,283,292,293,295,296,297,298,299,300,301,302,303,304,305,311,312,313,314,329,330,331,332,333,334,335,336,337,340,341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,364,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380,382,383,384,385,386,387,388,389,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,438,439,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,476,477,478,479,480,481,482,483,484,485,486,487,488,489,490,491,492,493,494,495,496,497,510,511,512,513,514,515,516,517,518,519,520,521,522,523,524,525,526,527,528,529,530,531,547,548,549,550,551,552,553,554,555,556,557,558,559,560,561,562,563,564,565,566,567,568,569,582,583,584,585,586,587,588,589,590,591,592,593,594,595,596,598,599,600,601,602,603,604,605,617,618,619,620,621,622,623,624,625,628,629,630,631,632,633,634,635,636,637,638,639,640,641,655,656,657,658,659,660,661,662,663,664,665,671,672,673,674,703,704,705,706,707,708,709,710,711,729,730,731,732,733,734};


CRGBPalette16 currentPalette;
TBlendType    currentBlending;
boolean    randomBlend = true;


extern const TProgmemPalette16 myBlackPalette_p PROGMEM;
extern const TProgmemPalette16 myBlackAndWhitePalette_p PROGMEM;


void setup() {
    delay( 3000 ); // power-up safety delay
    FastLED.addLeds<LED_TYPE, LED_PIN, COLOR_ORDER>(leds, NUM_LEDS).setCorrection( TypicalLEDStrip );
    FastLED.setBrightness(  ledBrightness );
    
    currentPalette = RainbowColors_p;
    currentBlending = LINEARBLEND;


    Serial.begin(9600);


     // Connect to Wi-Fi network with SSID and password
    Serial.print("Connecting to ");
    Serial.println(ssid);
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print(".");
    }
    // Print local IP address and start web server
    Serial.println("");
    Serial.println("WiFi connected.");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());
    server.begin();



/////
///setParameters?brightness=64&updates=4&rotationTime=600000
 

  Udp.begin(localPort);
}


void loop()
{
 // Serial.println (".");
    ChangePalettePeriodically();
    
    //Serial.print("updtPerSecond: ");
    //Serial.println(updtPerSecond);
   // FastLED.delay(1000 / updtPerSecond);


  //Web Server
  WiFiClient client = server.available();   // Listen for incoming clients

  if (client) {                             // If a new client connects,
  //  Serial.println("New Client.");          // print a message out in the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    currentTime = millis();
    previousTime = currentTime;
    boolean isBrowser = false;
    //while (client.connected() && currentTime - previousTime <= timeoutTime) { // loop while the client's connected
    while (client.connected() && currentTime - previousTime <= timeoutTime) {
     // Serial.println (",");
      currentTime = millis();         
      if (client.available()) { 
         // previousTime = millis();    
        // Serial.println ("*");        // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        //      Serial.write(c);                    // print it out the serial monitor
        header += c;
        
        if (c == '\n') {                    // if the byte is a newline character
          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
          //unsigned long startTime = millis();  
          //  Serial.println("Parsing Header");
          Serial.println(header);
          if(header.indexOf("rst=1") >= 0){
             FastLED.clear();
          }

          if(header.indexOf("passiveMode=1") >= 0){
            passiveMode = true;
            //  Serial.println("Switching to Passive Mode");
            int startIndex = header.indexOf('!');
            int endIndex;
            while (startIndex != -1) {
                endIndex = header.indexOf('!', startIndex + 1);
                if (endIndex == -1) // Check if there are no more indices
                    break;
                String index = header.substring(startIndex + 1, endIndex);
                /*Serial.print("(");
                Serial.print(startIndex);
                Serial.print("-");
                Serial.print(endIndex);
                Serial.print(")Index: ");
                Serial.println(index);  */
                startIndex = endIndex + 1; // move to the next character after the '!'
                endIndex = header.indexOf('!', startIndex); // find the next '!' after the index
                if (endIndex == -1) // Check if there are no more values
                    break;
                String value = header.substring(startIndex, endIndex);
              /*  Serial.print("(");
                Serial.print(startIndex);
                Serial.print("-");
                Serial.print(endIndex);
                Serial.print(")Value: ");
                Serial.println(value);  */
                startIndex = header.indexOf('!', endIndex ); // move to the next '!'
                  if (startIndex == -1) // Check if there are no more values
                    break;
                leds[index.toInt()] = (uint64_t)strtoull(value.c_str(), 0, 16);
                /* Serial.print("Pixel: ");
                Serial.print(index.toInt());
                Serial.print(" set to: ");
                Serial.print((uint64_t)strtoull(value.c_str(), 0, 16));
                Serial.println("");    */

            }

            FastLED.show();

            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println("Connection: close");
            client.println();
            //header = "";
            break;
          // Serial.println(millis() - startTime);    
          }

            //response, from the page, generated by arduino
          String deviceType;
          if ((header.indexOf("/brwsr=1") >= 0) ) {
            deviceType = "browser";
          }
          if ((header.indexOf("/cmd=1") >= 0)) {
            deviceType = "cmd";
          }
          if ((deviceType == "browser") || (deviceType == "cmd")) {
            
            if (header.indexOf("/switch") >= 0) {
              Serial.println("/switch Processing");
              passiveMode = false;
              int index = header.indexOf("/switch") ;
         //     Serial.println(header[index-1]);
              int number = int( header[index-1]) -48;
              Serial.print("Number: ");
              Serial.println(number);
              Serial.println(modes[number]);
              modesState[number]=!modesState[number];
            }

            if (header.indexOf("/set") >= 0) {
              Serial.println("/set Processing");
              passiveMode = false;
              modeIndexChanged = true;
              int index = header.indexOf("/set") ;
              Serial.println(header[index-1]);
              int number = int( header[index-1]) -48;
              Serial.print("Number: ");
              Serial.println(number);
              currentModeIndex = number;
            }

            if (header.indexOf("/parameters") >= 0) {
              Serial.println("/parameters Processing");
              int brightnessIndex = header.indexOf("brightness=");
              int ampersandIndex = 0;
              if (brightnessIndex != -1) {
                  ampersandIndex = header.indexOf("&", brightnessIndex);
                  String brightnessValue = header.substring(brightnessIndex + 11, ampersandIndex);
                  ledBrightness = brightnessValue.toInt();
                 Serial.print("Brightness Set: " );
                  Serial.println(ledBrightness);
                  FastLED.setBrightness(  ledBrightness );
                  FastLED.show();
              }

              String remainingSTR = header.substring(ampersandIndex, header.length());

              brightnessIndex = remainingSTR.indexOf("updates=");
              if (brightnessIndex != -1) {
                ampersandIndex = remainingSTR.indexOf("&", brightnessIndex);
                String brightnessValue = remainingSTR.substring(brightnessIndex + 8, ampersandIndex);
                updtPerSecondBase = brightnessValue.toInt();
                updtPerSecond = updtPerSecondBase;
                Serial.print("Updates per Second Set: " );
                Serial.println(updtPerSecondBase);
              }

              brightnessIndex = remainingSTR.indexOf("rotationTime=");
              if (brightnessIndex != -1) {
                ampersandIndex = remainingSTR.indexOf(" ");
                String brightnessValue = remainingSTR.substring(brightnessIndex + 13, ampersandIndex);
                ledRotationTime = brightnessValue.toInt();

             //   Serial.println("remainingSTR : "  + remainingSTR);
              //  Serial.print("brightnessValue IS: " );
               // Serial.println(brightnessValue);
               // Serial.print("remainingSTR.length() Set: " );
               // Serial.println(remainingSTR.length());

                Serial.print("ledRotationTime Set: " );
               Serial.println(ledRotationTime);
              }
              
            }

            if (header.indexOf("/blend") >= 0) {
        //      Serial.println("/blend Processing");
              int index = header.indexOf("/blend") ;
     //         Serial.println(header[index-1]);
              long blend = int( header[index-1]) -48;
        //      Serial.print("Number: ");
          //    Serial.println(blend);    
              setBlend(blend);        
            }

            if (header.indexOf("/rotationSwitch") >= 0) {
              Serial.println("/rotationSwitch Processing");
              enableRotation = !enableRotation;
              Serial.println("ROTATION SWITCHED");
            }
            
            if (deviceType == "browser"){
              // HTTP headers always start with a response code (e.g. HTTP/1.1 200OK)
              // and a content-type so the client knows what's coming, then a blank line:
              client.println("HTTP/1.1 200 OK");
              client.println("Content-type:text/html");
              client.println("Connection: close");
              client.println();

              client.println("<!DOCTYPE html><html>");
              client.println("<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
              client.println("<link rel=\"icon\" href=\"data:,\">");
              // CSS to style the on/off buttons 
              // Feel free to change the background-color and font-size attributes to fit your preferences
              client.println("<style>html { font-family: Helvetica; display: inline-block; margin: 0px auto; text-align: center;}");
              client.println("body {text-align: center;}");
              client.println("form {display: inline-block;}");
              

              client.println(".button { border: none; color: white; padding: 16px 40px;");
              client.println("text-decoration: none; font-size: 30px; margin: 2px; cursor: pointer;}");
              
              //slider Style
              client.println(".slidertext { position: relative; bottom: 25px; left: 5px; }");
              client.println(".slidecontainer {width: 25vw;}");
              client.println(".slider {-webkit-appearance: none;width: 100%;height: 25px;background: #d3d3d3;outline: none;opacity: 0.7;-webkit-transition: .2s;transition: opacity .2s;}");
              client.println(".slider:hover {opacity: 1;}");
              client.println(".slider::-webkit-slider-thumb {-webkit-appearance: none;appearance: none;width: 25px;height: 25px;background: #04AA6D;cursor: pointer;}");
              client.println(".slider::-moz-range-thumb {width: 25px;height: 25px;background: #04AA6D;cursor: pointer;}");
              //
              client.println(".on { background-color: #25cf52;}");
              client.println(".off { background-color: #b01010;}");
              client.println(".set { background-color: #b809b2;}");
              client.println(".blend { padding: 10px 30px; font-size: 18px;}");
              client.println(".center { margin-left: auto; margin-right: auto; }");
              client.println(".button2 {background-color: #77878A;}</style></head>");
              
              // Web Page Heading
              client.println("<body><h1>LED WALL MODE: " + modes[currentModeIndex] + " </h1>");
              

              //form 
              client.println("<form action=\"/brwsr=1/parameters\"><div class=\"slidecontainer\"><label for=\"brightness\">Brightness:</label><br>");

              client.print("<input type=\"range\" min=\"0\" max=\"255\" value=\"");
              client.print(ledBrightness);
              client.println("\" class=\"slider\" id=\"brightness\" name=\"brightness\">");
              
              client.println("<span id=\"brightnessText\" class=\"slidertext\" ></span></div><div class=\"slidecontainer\"><label for=\"updates\">Updates Per Second:</label><br>");
              client.print("<input type=\"range\" min=\"1\" max=\"200\" value=\"");
              client.print(updtPerSecond);
              client.println("\" class=\"slider\" id=\"updates\" name=\"updates\">");

              client.print("<span id=\"updatesText\" class=\"slidertext\" ></span></div><label for=\"rotationTime\">Rotation Time (ms):</label><br><input type=\"text\" id=\"rotationTimerotationTime\" name=\"rotationTime\" value=\"");
              client.print(ledRotationTime);
              client.println("\"><br><input type=\"submit\" value=\"Submit\"></form> ");
              //

              client.println("<div>");
              if (enableRotation) {
                client.print("<p>ROTATION: <a href=\"/brwsr=1/rotationSwitch\"><button class=\"button on\">ON</button></a></p>");
              } else {
                client.print("<p>ROTATION: <a href=\"/brwsr=1/rotationSwitch\"><button class=\"button off\">OFF</button></a></p>");
              }
              client.println("</div>");

              client.println("<div><p>BLEND TYPE: ");
              if(currentBlending == NOBLEND){
                client.print("<a href=\"/brwsr=1/0/blend\"><button class=\"button blend on\">NOBLEND</button></a>");
              }else{
                client.print("<a href=\"/brwsr=1/0/blend\"><button class=\"button blend off\">NOBLEND</button></a>");
              }
              if(currentBlending == LINEARBLEND){
                client.print("<a href=\"/brwsr=1/1/blend\"><button class=\"button blend on\">LINEARBLEND</button></a>");
              }else{
                client.print("<a href=\"/brwsr=1/1/blend\"><button class=\"button blend off\">LINEARBLEND</button></a>");
              }
              if(currentBlending == LINEARBLEND_NOWRAP){
                client.print("<a href=\"/brwsr=1/2/blend\"><button class=\"button blend on\">LINEARBLEND_NOWRAP</button></a>");
              }else{
                client.print("<a href=\"/brwsr=1/2/blend\"><button class=\"button blend off\">LINEARBLEND_NOWRAP</button></a>");
              }
              if(randomBlend){
                client.print("<a href=\"/brwsr=1/3/blend\"><button class=\"button blend on\">RANDOM</button></a>");
              }else{
                client.print("<a href=\"/brwsr=1/3/blend\"><button class=\"button blend off\">RANDOM</button></a>");
              }
              client.println("</p></div>");



              //choice option between modes
              client.print("<table class=\"center\">");
              client.print("<tr>");
              client.print("<th>Mode</th>");
              client.print("<th>Status</th>");
              client.print("<th>Switch</th>");
              client.print("</tr>");
              for(int i = 0; i < MODES_SIZE ; i++) {
              client.print("<tr>");
              client.print("<td><p>" + modes[i] + "</p></td>");
                if (modesState[i]) {
                  client.print("<td><p><a href=\"/brwsr=1/");
                  client.print(i);
                  client.print("/switch\"><button class=\"button on\">ON</button></a></p></td>");
                } else {
                  client.println("<td><p><a href=\"/brwsr=1/");
                  client.println(i);
                  client.print("/switch\"><button class=\"button off\">OFF</button></a></p></td>");
                }
                client.print("<td><p><a href=\"/brwsr=1/");
                client.print(i);
                client.print("/set\"><button class=\"button set\">SET</button></a></p></td>");
                client.print("</tr>");
              }
              client.println("</table>");

              client.println("<script>var bslider = document.getElementById(\"brightness\");var boutput = document.getElementById(\"brightnessText\");boutput.innerHTML = bslider.value;bslider.oninput = function() {  boutput.innerHTML = this.value;}");

              client.println("var uslider = document.getElementById(\"updates\");var uoutput = document.getElementById(\"updatesText\");uoutput.innerHTML = uslider.value;uslider.oninput = function() {  uoutput.innerHTML = this.value;}</script>");
              client.println("</body></html>");
              
              // The HTTP response ends with another blank line
              client.println();
              // Break out of the while loop
              //Serial.println("Breaking.");
              //header = "";
            } else{
              client.println("HTTP/1.1 200 OK");
              client.println("Content-type:text/html");
              client.println("Connection: close");
              client.println();

              client.print("brightness=");
              client.print(ledBrightness);
              client.println("&");
              client.print("refreshRate=");
              client.print(updtPerSecond);
              client.println("&");
              client.print("autoRotation=");
              client.print(enableRotation);
              client.println("&");
              client.print("rotationTime=");
              client.print(ledRotationTime);
              client.println("&");
              client.println();
            }
            break;
            
            
          }
          header = "";
            

        } 

      }
      //----- if not bytes ready to read


    }

    //Serial.println(header);

    client.stop();
  //  Serial.println("Client disconnected.");
    currentTime = millis();  
    Serial.print("Connection Time:");
    Serial.println(currentTime - previousTime);
    // Clear the header variable
    header = "";

  }



  int packetSize = Udp.parsePacket();

  if (packetSize) {

    udpMessage = "";
   // Serial.print("Received packet of size ");

    //Serial.println(packetSize);

   // Serial.print("From ");

    unsigned long startTIme = millis();
    IPAddress remoteIp = Udp.remoteIP();

    //Serial.print(remoteIp);

    //Serial.print(", port ");

    //Serial.println(Udp.remotePort());

    // read the packet into packetBufffer


    int len = Udp.read(packetBuffer, 255);
    
    
    while (len > 0) {
     // Serial.print("+");
      //Serial.println(packetBuffer);
     // Serial.print(len);
      udpMessage= udpMessage + packetBuffer;
      //Serial.println(udpMessage);53407
     // len = Udp.read(packetBuffer, 255);
     len = Udp.read(packetBuffer, 255);
      packetBuffer[len] = 0;
    }

        Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());

    Udp.write(ReplyBuffer);

    Udp.endPacket();

    parseCommands(udpMessage);
    //Serial.print("Time:");
    //Serial.println(millis() - startTIme);
    //Serial.println("Contents:");

    //Serial.println(udpMessage);

    // send a reply, to the IP address and port that sent us the packet we received



    udpMessage="";
    //memset(packetBuffer, 0, sizeof packetBuffer);

  }

}

void parseCommands( String line)
{
    if(line.indexOf("rst=1") >= 0){
      //  Serial.println("FastLED.clear()");
          FastLED.clear();
      }

      if(line.indexOf("passiveMode=1") >= 0){
        passiveMode = true;
      //  Serial.println(line);
      //  Serial.println("Switching to Passive Mode");
        int startIndex = line.indexOf('!');
        int endIndex;
        while (startIndex != -1) {
            endIndex = line.indexOf('!', startIndex + 1);
            if (endIndex == -1) // Check if there are no more indices
                break;
            String index = line.substring(startIndex + 1, endIndex);
            /*Serial.print("(");
            Serial.print(startIndex);
            Serial.print("-");
            Serial.print(endIndex);
            Serial.print(")Index: ");
            Serial.println(index);  */
            startIndex = endIndex + 1; // move to the next character after the '!'
            endIndex = line.indexOf('!', startIndex); // find the next '!' after the index
            if (endIndex == -1) // Check if there are no more values
                break;
            String value = line.substring(startIndex, endIndex);
          /*  Serial.print("(");
            Serial.print(startIndex);
            Serial.print("-");
            Serial.print(endIndex);
            Serial.print(")Value: ");
            Serial.println(value);  */
            startIndex = line.indexOf('!', endIndex ); // move to the next '!'
              if (startIndex == -1) // Check if there are no more values
                break;
            leds[index.toInt()] = (uint64_t)strtoull(value.c_str(), 0, 16);
            /* Serial.print("Pixel: ");
            Serial.print(index.toInt());
            Serial.print(" set to: ");
            Serial.print((uint64_t)strtoull(value.c_str(), 0, 16));
            Serial.println("");    */

        }
        // FastLED.show();
      }

    if(line.indexOf("rndr=1") >= 0){
         // delay(200);
          FastLED.delay(5);
         // FastLED.show();
        //  delay(200);
      //    Serial.println("FastLED.show();");
    }

}



void FillLEDsFromPaletteColors( uint8_t colorIndex)
{
    uint8_t brightness = 255;
    
    for( int i = 0; i < NUM_LEDS; i++) {
        leds[i] = ColorFromPalette( currentPalette, colorIndex, brightness, currentBlending);
        colorIndex += 3;
    }
}


void ChangePalettePeriodically(){
  
  if(enableRotation){
    currentTime = millis();
    if((currentTime - previousRotationTime) > ledRotationTime){
      Serial.println("ROTATING");
      previousRotationTime = currentTime;
      modeIndexChanged = true;
      int i = currentModeIndex + 1;
      while(true){
        if(i >= MODES_SIZE){ //Start from beginning
          i = 0;
        }
        if(modesState[i]){
          currentModeIndex = i;
          Serial.println("MODE SET TO: " + modes[currentModeIndex]);
          break;
        }
        i++;
        if(i == currentModeIndex){ 
          break;  //break after full circle
        }
      }
    
      setRandomBlend();
    }

  }

    if(!passiveMode){
      switch(currentModeIndex){
        case 0 : //KNVL
          if(modeIndexChanged){
            updtPerSecond = updtPerSecondBase;
            
            FastLED.clear();
            for(int i = 0; i < KNVL_SIZE ; i++) {
              leds[knvlLogo[i]] = 0x00F58F78;
              delay(35);
              FastLED.show();
            }
          }else{
            if(nextRenderTime <=  millis()){
              long randNumber = random(KNVL_SIZE);
            // leds[knvlLogo[randNumber]] = CHSV(random8(),255,255);;  // Read array from Flash

              leds[pgm_read_dword(&(knvlLogo[randNumber]))] = CHSV(random8(),255,255);;  // Read array from Flash
              //leds[knvlLogo[randNumber]] = CHSV(random8(),random8(),random8());;  // Read array from Flash
              FastLED.show();
              nextRenderTime = millis() + (1000 / updtPerSecondBase);
            }
          }
          break;
        case 1 : //
        if(modeIndexChanged){
          updtPerSecond = updtPerSecondBase;
          currentPalette = RainbowStripeColors_p;
        }else{
          drawPalette();
        }
          break;
        case 2 : //RANDOM
          if(modeIndexChanged){
            updtPerSecond = updtPerSecondBase;
            SetupTotallyRandomPalette(); 
          }else{
            drawPalette();
          }
          break;
        case 3: //WHITE_STRIP
        if(modeIndexChanged){
            //Serial.println("Set WHITE_STRIP");
            updtPerSecond = updtPerSecondBase;
            currentPalette = myBlackAndWhitePalette_p;
        }else{
          // Serial.println("DRAW WHITE_STRIP");
            drawPalette();
        }
          break;
        case 4: //OFF
          updtPerSecond = updtPerSecondBase;
          currentPalette = myBlackPalette_p; 
          drawPalette();
          break;

        case 5: //CAT
        if(modeIndexChanged){
            updtPerSecond = updtPerSecondBase;     
            FastLED.clear();
            for(int i = 0; i < CAT_SIZE ; i++) {
              leds[catLogo[i]] = 0x00F58F78;
              delay(35);
              FastLED.show();
            }
          }else{
            if(nextRenderTime <=  millis()){
              long randNumber = random(CAT_SIZE);
              leds[pgm_read_dword(&(catLogo[randNumber]))] = CHSV(random8(),255,255);;  // Read array from Flash
              //leds[knvlLogo[randNumber]] = CHSV(random8(),random8(),random8());;  // Read array from Flash
              FastLED.show();
              nextRenderTime = millis() + (1000 / updtPerSecondBase);
            }
          }
        break;

        default : 
          updtPerSecond = updtPerSecondBase;
          SetupTotallyRandomPalette();
      }
    }
  modeIndexChanged = false;

 

}


void setRandomBlend(){
  Serial.println("setRandomBlend");
  if(randomBlend){
    long randNumber = random(3);
    setBlend(randNumber);
  }
}


void setBlend(long number){
    Serial.println("setLineBlend");
    switch(number){
      case 0 : //NOBLEND
        currentBlending = NOBLEND; 
        break;

      case 1 : //LINEARBLEND
        currentBlending = LINEARBLEND;
        break;

      case 2 : //LINEARBLEND_NOWRAP
        currentBlending = LINEARBLEND_NOWRAP; 
        break;
        
      default : 
        randomBlend = !randomBlend;
    }
}

// This function fills the palette with totally random colors.
void SetupTotallyRandomPalette()
{
    Serial.println("Set Up RANDOM Palette");
    for( int i = 0; i < 16; i++) {
        currentPalette[i] = CHSV( random8(), 255, random8());
    }
}



// This function sets up a palette of purple and green stripes.
void drawPalette()
{
  //Serial.print("drawPalette ");
  //Serial.println(currentModeIndex);
   FastLED.delay(1000 / updtPerSecond);
   static uint8_t startIndex = 0;
   startIndex = startIndex + 1; /* motion speed */  
   FillLEDsFromPaletteColors(startIndex);  
   FastLED.show();
}





const TProgmemPalette16 myBlackAndWhitePalette_p PROGMEM =
{
    CRGB::White,
    CRGB::Black, 
    CRGB::Black,
    CRGB::Black,
    
    CRGB::White,
    CRGB::Black,
    CRGB::Black,
    CRGB::Black,
    
    CRGB::White,
    CRGB::Black,
    CRGB::Black,
    CRGB::Black,

    CRGB::White,
    CRGB::Black,
    CRGB::Black,
    CRGB::Black
};

const TProgmemPalette16 myBlackPalette_p PROGMEM =
{
    CRGB::Black,
    CRGB::Black, 
    CRGB::Black,
    CRGB::Black,
    
    CRGB::Black,
    CRGB::Black,
    CRGB::Black,
    CRGB::Black,
    
    CRGB::Black,
    CRGB::Black,
    CRGB::Black,
    CRGB::Black,
    CRGB::Black,
    CRGB::Black,
    CRGB::Black,
    CRGB::Black
};


