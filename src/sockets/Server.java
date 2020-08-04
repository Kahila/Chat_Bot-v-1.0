
/**
 * 
 * @author Kahila kalombo
 * @version 1.0
 * @since 2020-08-04
 * @filename Server
 * */
package sockets;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Server extends Thread {
	private ServerSocket serverSocket;
	
	public Server(int port) throws IOException{
		serverSocket = new ServerSocket(port);//binding the server to the port by creating a serverSocket
		serverSocket.setSoTimeout(10000);//time that the server will wait before timing out
	}
	
	//method that will be used to get a random string to display to client
	public String random(String one, String two, String three) {
		int rand = (int)(Math.random()*((3-1)+1) + 1);
		
		if (rand == 1)
			return (one);
		else if (rand == 2)
			return (two);
		
		return (three);
	}
	
	public void run() {
		int ii = 0; //string to be used for counting the number of question before breaking the loop
		String[] quest = new String[4];
		Pattern[] rgx = new Pattern[3]; //regex for each type of question 
		rgx[0] = Pattern.compile("[Aa][rR][Ee]");
		rgx[1] = Pattern.compile("[Ww][hH][yY]*");
		rgx[2] = Pattern.compile("[Vv][iI][Rr][uU][sS][^'']*");
		
		Pattern pat = Pattern.compile("[H][E][L]{2}[O][' '][B][O][T]"); //regex for starting session on the server
		
		while(true) {
			try {
				System.out.println("Ready for clients to Connect...");
				Socket server = serverSocket.accept();//waiting for client to connect
				
				//input stream that will be used in the print writer
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				
				PrintWriter pw = new PrintWriter(out, true);
				pw.println("\n\nHELLO - You may ask me 4 questions\n");
				pw.println("\n\nHELLO BOT\n");
				BufferedReader br;
				
				br = new BufferedReader(new InputStreamReader(server.getInputStream()));
				Matcher match = pat.matcher("HELLO BOT");
				if (match.find()) {
					pw.println("Ask me a question or Done");
					for (int i = 0; i < 4; i++) {
						ii = i;
						br = new BufferedReader(new InputStreamReader(server.getInputStream()));
						quest[i] = br.readLine();
						
						//checking if the client would like to end the session
						pat = Pattern.compile("[D][O][N][E]");
						match = pat.matcher(quest[i]);
						if (match.find()) {
							break;//exiting the loop and closing session
						}
						
						
						String str[] = quest[i].split(" ");//splitting the string so that I can compare only the first word
						
						if (rgx[0].matcher(str[0]).find())
							pw.printf("0%d %s\n", i+1, random("Yes", "No", "Maybe"));
						
						else if (rgx[1].matcher(str[0]).find())
							pw.printf("0%d Because the boass says so - see Ulink\n", i+1);
						
						else if (rgx[2].matcher(quest[i]).find())
							pw.printf("0%d Please see sacoronavirus.co.za\n", i+1);// random value 
						else
							pw.printf("0%d %s\n", i+1, random("Escusez-moi?", "Oh ok!", "Meh"));//random value
					}
					pw.printf("0%d OK BYE- %d questions answered\n", ii+1, ii);
				}
				server.close();
				pw.close();
			}catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			}catch (IOException e) {
				e.printStackTrace();
				break;
			}
			
		}
	}
}
