

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.OutputStream;
import java.io.PrintStream;

public final class EchoClient {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 22222)) {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            System.out.println(br.readLine());
            BufferedReader sbr = new BufferedReader(new InputStreamReader(System.in));
            OutputStream os = socket.getOutputStream();
			PrintStream out = new PrintStream(os, true, "UTF-8");
			System.out.print("Client> ");
			String text = sbr.readLine();		
			while (!text.equals("exit")) {
				out.println(text);			
				System.out.println("Server> " + br.readLine());
				System.out.print("Client> ");
				text = sbr.readLine();
			}
			out.print("exit");
        }
    }
}
