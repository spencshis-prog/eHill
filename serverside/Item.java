package serverside;
import org.bson.types.ObjectId;



import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class Item {
	private ObjectId id;
	ArrayList<Double> bids = new ArrayList<Double>();
	
	ArrayList<String> bidders = new ArrayList<String>();
	String description;
	int duration = 60;
	String name;
	Double curr_bid = 5.50;
	boolean open = true;
	double final_price;
	
	
	public Item() {}
	
	public Item(String name, String desc, int dur, double min, double fin){
		this.name = name;
		description = desc;
		duration = dur;
		curr_bid = min;
		final_price = fin;
		startTime();
	}
	
	
	private void startTime() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				timehandle(timer);
				
			}
		}, 1000, 1000);
	}
	
	private final void timehandle(Timer timer) {
		if(duration == 1) timer.cancel();
		duration--;
	}
	
	public void Start() {
		startTime();
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public double getFinal_price() {
		return final_price;
	}

	public void setFinal_price(double final_price) {
		this.final_price = final_price;
	}

	

	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public ArrayList<Double> getBids() {
		return bids;
	}


	public void setBids(ArrayList<Double> bids) {
		this.bids = bids;
	}


	public ArrayList<String> getBidders() {
		return bidders;
	}


	public void setBidders(ArrayList<String> bidders) {
		this.bidders = bidders;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Double getCurr_bid() {
		return curr_bid;
	}


	public void setCurr_bid(Double curr_bid) {
		this.curr_bid = curr_bid;
	}


	@Override
	public String toString() {

		return "Item [name=" + name + ", description=" + description + ", duration=" + duration + ", current bid="
				+ curr_bid + ", bids=" + bids.toString() + ", bidders=" + bidders.toString() + ", open=" + open + ", final=" + final_price +"]";
	}
	
	public static Item strToItem(String str) {
String[] variables = str.split(",");
		
		String name = variables[0].substring(11);
		
		String desc = variables[1].substring(13);
		
		String dur = variables[2].substring(10);
		int duration = Integer.parseInt(dur);
		
		String cur = variables[3].substring(13);
		double current = Double.parseDouble(cur);
		
		String bids = variables[4].substring(7);
		ArrayList<Double> bidds = new ArrayList<Double>();
		int i = 5;
		if(bids.substring(0,bids.length()-1).length() > 0) {
			if(bids.contains("]")) bids = bids.substring(0, bids.length()-1);
			bidds.add(Double.parseDouble(bids));
			for(; !variables[i-1].contains("]"); i ++) {
				bids = ("" + variables[i].substring(1));
				if(bids.contains("]")) bids = bids.substring(0, bids.length()-1);
				bidds.add(Double.parseDouble(bids));	
			}
		}
		
		ArrayList<String> bidders = new ArrayList<String>();
		String bidderz = variables[i].substring(10);
		if(bidderz.substring(0,bidderz.length()-1).length() > 0) {
			if(bidderz.contains("]")) bidderz = bidderz.substring(0, bidderz.length()-1);
			bidders.add(bidderz);
			for(; !variables[i-1].contains("]"); i ++) {
				bidderz = ("" + variables[i].substring(1));
				if(bidderz.contains("]")) bidderz = bidderz.substring(0, bidderz.length()-1);
				bidders.add(bidderz);
			}
		}
		
		i++;
		
		String openS = variables[i].substring(6);
		boolean open = false;
		if(openS.equals("true")) open = true;
		String fp = variables[i+1].substring(7, variables[i+1].length()-1);
		double finalval = Double.parseDouble(fp);
		
		Item temp = new Item(name, desc, duration, current, finalval); 	//TODO Final Price into to String and strToItem
		temp.setBidders(bidders);
		temp.setBids(bidds);
		temp.open = open;
		
		
		return temp;
	}
	
	public void bid(double bid, String name) {
		bids.add(bid);
		bidders.add(name);
	}
}
