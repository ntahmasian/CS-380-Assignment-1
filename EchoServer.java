
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer {

	public static void main(String[] args) throws Exception {
		try (ServerSocket serverSocket = new ServerSocket(22222)) {
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					Runnable r = () -> {
						try {
							String address = socket.getInetAddress().getHostAddress();
							System.out.printf("Client connected: %s%n", address);
							OutputStream os = socket.getOutputStream();
							PrintStream out = new PrintStream(os, true, "UTF-8");
							out.printf("Hi %s, thanks for connecting!%n", address);
							BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							String text = br.readLine();
							while (!text.equalsIgnoreCase("exit")) {
								out.println(text);
								text = br.readLine();
							}
							System.out.printf("Client disconnected: %s%n", address);
						} catch (IOException e) {
							e.printStackTrace();
						}
					};
					new Thread(r).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
