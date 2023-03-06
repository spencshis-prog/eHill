package serverside;

import java.util.List;

import org.bson.types.ObjectId;

public class User {
	private ObjectId id;
	String name;
	String pass;
	boolean online = false;
	
	public User() {}
	
	public User(String name, String pass){
		this.name = name;
		this.pass = pass;
	}

	
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	
	public static boolean Valid_user(String name, String pass) {
		List<User> userz = Server.users;
		for(int i = 0; i < Server.users.size(); i ++) {
			User temp = Server.users.get(i);
			if(temp.name.equals(name) && temp.pass.equals(pass) && temp.online == false) {
				temp.online = true;
				return true;
			}
		}
		return false;
	}
}
