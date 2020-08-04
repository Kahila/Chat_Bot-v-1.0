/**
 * 
 * @author Kahila kalombo
 * @version 1.0
 * @since 2020-08-04
 * @filename Server
 * */

import java.io.IOException;

import sockets.Server;

public class Main {

	public static void main(String[] args) {
		int port = 8888;
		try {
			Thread t = new Server(port);
			t.start();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
