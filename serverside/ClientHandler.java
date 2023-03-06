package serverside;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Observer;
import java.util.Map;
import java.util.Observable;

class ClientHandler implements Runnable, Observer {
	Object lock = new Object();
	private Server server;
	private Socket clientSocket;
	private BufferedReader fromClient;
	private PrintWriter toClient;

	protected ClientHandler(Server server, Socket clientSocket) {
		this.server = server;
		this.clientSocket = clientSocket;
		try {
			fromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			toClient = new PrintWriter(this.clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < Server.list.size(); i ++) {
			sendToClient(Server.list.get(i).toString());
		}
	}

	protected void sendToClient(String string) {
		System.out.println("Sending to client: " + string);
		toClient.println(string);
		toClient.flush();
	}

	@Override
	public void run() {
		String input;
		try {
			while ((input = fromClient.readLine()) != null) {
				System.out.println("From client: " + input);
				if(input.contains("|")) {
					String[] vars = new String[2];
					String[] va= input.split("\\|");
					for(int i = 0; i < va.length && i <2; i ++) {
						vars[i] = va[i];
					}
					if(va.length == 1) vars[1] = "";

					if(is_valid(vars)) sendToClient("Valid");
					else sendToClient("Invalid");
					System.out.println("" + is_valid(vars));
				}
				else {
					
					synchronized(lock) {
						server.update("update");
						
						Item item = Item.strToItem(input);
						//System.out.println("Our Item: " + item.toString());
						
						//collection.findOneAndReplace(Filters.eq("name", item.getName()), item);
						for(int i = 0; i <Server.list.size(); i ++) {
							if(Server.list.get(i).name.equals(item.name)) {
								Server.list.get(i).open = item.open;
								Server.list.get(i).bidders = item.bidders;
								Server.list.get(i).bids = item.bids;
								Server.list.get(i).curr_bid = item.curr_bid;
								Server.list.get(i).final_price = item.final_price;
								//sendToClient(item.toString());
								server.update(item.toString());
							}
							
						}
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		this.sendToClient((String) arg);
	}
	

	public boolean is_valid(String[] vars) {
		return User.Valid_user(vars[0], vars[1]);
	}
}