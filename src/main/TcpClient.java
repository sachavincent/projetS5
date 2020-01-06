import java.net.*;
import java.io.*;
import java.util.*;

public class TcpClient {
	
	public static void main(String[] args) {
		try {
    	    InetAddress ip = InetAddress.getByName(args[0]);
    		int port = Integer.parseInt(args[1]);
			Socket cSocket = new Socket(ip, port);
			PrintWriter pw = new PrintWriter(cSocket.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
			boolean stop=false;
			Scanner sc = new Scanner(System.in);
			String message = "";
			if("stop".equals(message)) 
				stop=true;
			while(!stop) {
				if("stop".equals(message)) 
					stop=true;
				if(!stop) {
					while(!br.ready()) { }
					message = br.readLine();
					System.out.println(message);
					if(!"stop".equals(message)) {
						message = sc.next();
						sc.nextLine();	
						pw.println(message);	
					}
				}
			}
			cSocket.close();
			pw.close();
			br.close();
   		} catch (IOException e) {
			System.out.println("Erreur de connexion.");
    	}
	}	
}