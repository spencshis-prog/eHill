package serverside;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;







class Server extends Observable {
	public static volatile List<Item> list =Collections.synchronizedList(new ArrayList<Item>());
	public static List<User> users= Collections.synchronizedList(new ArrayList<User>());

	private static MongoClient mongo;
	private static MongoDatabase database;
	private static MongoCollection<Item> collection;
	private static MongoCollection<User> collection1;

	private static final String URI = "mongodb+srv://help:1234@finalproject.ul9cwwf.mongodb.net/?retryWrites=true&w=majority";
	private static final String DB = "auction";
	private static final String COLLECTION = "items";
	private static final String COLLECTION1 = "Users";

	public static void main(String[] args) {
		
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

		mongo = MongoClients.create(URI);
		database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
		collection = database.getCollection(COLLECTION, Item.class); 
		collection1 = database.getCollection(COLLECTION1, User.class); 
		
		
		    

		MongoCursor cursor = collection.find(Filters.empty()).cursor();
		while(cursor.hasNext()) {
			//System.out.println(((Item)cursor.next()).toString());
			Item help = (Item) cursor.next();
			help.Start();
			list.add(help);
			//System.out.println(help.getDescription());
		}
		MongoCursor usa = collection1.find(Filters.empty()).cursor();
		while(usa.hasNext()) {
			//System.out.println(((Item)cursor.next()).toString());
			User help = (User) usa.next();
			
			users.add(help);
			//System.out.println(help.getDescription());
		}
//		mongo.close();
		new Server().runServer();
		
		
		
	}

	private void runServer() {
		try {
			setUpNetworking();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		ServerSocket serverSock = new ServerSocket(4243);
		while (true) {
			Socket clientSocket = serverSock.accept();
			System.out.println("Connecting to... " + clientSocket);

			ClientHandler handler = new ClientHandler(this, clientSocket);
			this.addObserver(handler);

			Thread t = new Thread(handler);
			t.start();
			
//			for(int i = 0; i < list.size(); i ++) {
//				handler.sendToClient(list.get(i).toString());
//			}

		}
	}

	protected void processRequest(String input) {
		String output = "Error";
		Gson gson = new Gson();
		Message message = gson.fromJson(input, Message.class);
		try {
			String temp = "";
			switch (message.type) {
			case "upper":
				temp = message.input.toUpperCase();
				break;
			case "lower":
				temp = message.input.toLowerCase();
				break;
			case "strip":
				temp = message.input.replace(" ", "");
				break;
			}
			output = "";
			for (int i = 0; i < message.number; i++) {
				output += temp;
				output += " ";
			}
			this.setChanged();
			this.notifyObservers(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(String input){
		this.setChanged();
		this.notifyObservers(input);
		
				
//		this.notifyObservers(variables);
	}
	
	
	public void reset() {
		  Item item = new Item("Shoes", "never worn", 200, 2.00, 75);
	      collection.findOneAndReplace(Filters.eq("name", item.getName()), item);
	      Item pants = new Item("Pants", "lightly pooped", 69, 4.20, 100);
	      collection.findOneAndReplace(Filters.eq("name", pants.getName()), pants);
	      Item post = new Item("Poster", "A large 5x5 poster containing a graphic of George Washington with an Assault Rifle and American Flag", 200, 2.00, 75);
	      collection.findOneAndReplace(Filters.eq("name", post.getName()), post);
		
	      User Nanda = new User("Nanda", "1234");
	      collection1.findOneAndReplace(Filters.eq("name", Nanda.getName()), Nanda);
	      User Spencer = new User("Spencer", "help");
	      collection1.findOneAndReplace(Filters.eq("name", Spencer.getName()), Spencer);	
	      User Guest = new User("Guest", "");
	      collection1.findOneAndReplace(Filters.eq("name", Guest.getName()), Guest);
	      User John = new User("John", "password");
	      collection1.findOneAndReplace(Filters.eq("name", John.getName()), John);
	      User Elo = new User("Elon_Must", "i4mR1ch");
	      collection1.findOneAndReplace(Filters.eq("name", Elo.getName()), Elo);
	}
	

	
	
}
