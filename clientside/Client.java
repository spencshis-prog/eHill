package clientside;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;





class Client {
	public static volatile boolean flag = false;
	public static volatile List<Item> list =Collections.synchronizedList(new ArrayList<Item>());
	public static volatile boolean validate = false;
	public static volatile String user_name;
	public static volatile String pass;
	public static volatile boolean valid_user = false;
	volatile boolean upflag = false;
	
	
	private static String host = "127.0.0.1";
	private BufferedReader fromServer;
	private PrintWriter toServer;
	private Scanner consoleInput = new Scanner(System.in);

	public static void main(String[] args) {
		//graphics.main(args);
		try {
			new Client().setUpNetworking();
		} catch (Exception e) {
			e.printStackTrace();
		}
		graphics.main(args);
		
	}

	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		Socket socket = new Socket(host, 4243);
		System.out.println("Connecting to... " + socket);
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		toServer = new PrintWriter(socket.getOutputStream());

		Thread readerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				String input;
				try {
					while ((input = fromServer.readLine()) != null) {
						
						//TODO notify Listener
						System.out.println("From server: " + input);
						if(input.equals("Valid")) {
							Client.valid_user = true;
							
						}
							
						else if(input.equals("Invalid"));
						else if(input.contains("update")) {
								upflag = true;
							}
						else if(upflag) updater(input);
						else if(input.equals("done")) upflag = false;
						else processRequest(input);
						
						
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Thread writerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					//					String input = consoleInput.nextLine();
					//					String[] variables = input.split(",");
					//					Message request = new Message(variables[0], variables[1], Integer.valueOf(variables[2]));
					//					GsonBuilder builder = new GsonBuilder();
					//					Gson gson = builder.create();
					//					sendToServer(gson.toJson(request));
					
					
					if(Client.validate) {
						String usepass = Client.user_name + "|" +Client.pass;
						userval(usepass);
						Client.validate = false;
					}
					
					
					boolean help = Client.flag;
					
					if(help) {
						
						//System.out.println("flag is true");
						for(int i = 0; i < list.size(); i ++) {
							sendToServer(list.get(i).toString());
							//System.out.println(list.get(i).toString());
						}
						flag = false;
					}
				}
			}
		});

		readerThread.start();
		writerThread.start();
	}
	
	protected void processRequest(String input) {
		
		Item request = Item.strToItem(input);
		list.add(request);
		
	}
	protected void updater(String input) {
		Item item = Item.strToItem(input);
		for(int i = 0; i <list.size(); i ++) {
			if(list.get(i).name.equals(item.name)) {
				list.get(i).open = item.open;
				list.get(i).bidders = item.bidders;
				list.get(i).bids = item.bids;
				list.get(i).curr_bid = item.curr_bid;
				list.get(i).final_price = item.final_price;
				
			}
			
		}
		upflag = false;
	}
	
	protected void sendToServer(String string) {
		System.out.println("Sending to server: " + string);
		Item.strToItem(string);
		toServer.println(string);
		toServer.flush();
	}
	
	protected void userval(String string) {
		toServer.println(string);
		toServer.flush();
	}

	

}
