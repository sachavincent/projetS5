import java.net.*;
import java.io.*;
import java.util.*;

public class TcpServer {
	
	public static void main(String[] args) {
		try {
    		int port = Integer.parseInt(args[0]);
		
			ServerSocket sSocket = new ServerSocket(port);
			while(true) {
				Socket socket = sSocket.accept();
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				boolean stop=false;
				Scanner sc = new Scanner(System.in);
				String message = "";
				if("stop".equals(message)) 
					stop=true;
				while(!stop) {
					if("stop".equals(message)) 
						stop=true;
					if(!stop) {
						message = sc.next();
						sc.nextLine();	
						
						pw.println(message);
						while(!br.ready()) { }
						message = br.readLine();
						System.out.println(message);
					}
				}
				sSocket.close();
				socket.close();
				pw.close();
				br.close();
			}
		} catch (IOException e) {
			System.out.println("Erreur de connexion.");
		}
	}
}