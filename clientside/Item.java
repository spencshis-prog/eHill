package clientside;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Item {
	volatile ArrayList<Double> bids = new ArrayList<Double>();
	volatile ArrayList<String> bidders = new ArrayList<String>();
	String description;
	int duration;
	String name;
	Double curr_bid = 5.50;
	boolean open = true;
	double final_price;
	
	
	
	Item(String name, String desc, int dur, double min, double fin){
		this.name = name;
		description = desc;
		duration = dur;
		curr_bid = min;
		final_price = fin;
		startTime();
	}
	
	private void startTime() {
		Timer timer;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				timehandle(timer);
				
			}
		}, 1000, 1000);
	}
	
	private final void timehandle(Timer timer) {
		if(duration <= 1) {
			timer.cancel();
			open = false;
		}
		duration--;
		if(open == false) {
			timer.cancel();
			
			
		}
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



	@Override
	public String toString() {
		
		return "Item [name=" + name + ", description=" + description + ", duration=" + duration + ", current bid="
				+ curr_bid + ", bids=" + bids.toString() + ", bidders=" + bidders.toString() + ", open=" + open + ", final=" + final_price +"]";
	}
	
	
	public static HBox toHBOXS(Item item, Stage stage) {
		HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: black;");

        Label label = new Label(item.name);
        String descrip = new String(item.description);
        if(descrip.length() > 30) {
        	descrip = descrip.substring(0, 30);
        	descrip = descrip + "...";
        }
        Text descr = new Text(descrip);
        
        descr.setFont(Font.font(8));
        descr.setWrappingWidth(50);
        VBox help = new VBox();
        help.getChildren().add(label);
        help.getChildren().add(descr);
        Button butt = new Button("View");
        VBox right = new VBox();
        Label bid = new Label("Price: $" + String.format("%.2f",item.curr_bid));
        
       butt.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent e) {
                stage.close();
                graphics.view(item);
            }
        });
       Text status = new Text("Status: ");
       status.setFont(Font.font(8));
       
       if(item.open == true) status.setText("Status: open");
       else status.setText("Status: closed");
       
       
       	right.getChildren().add(bid);
       	right.getChildren().add(status);
       	
        hbox.getChildren().add(butt);
        hbox.getChildren().add(help);
        
        hbox.getChildren().add(right);
        
        return hbox;
		
		
		
		
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
	
	public String Bid(double bid) {
		System.out.println("button pressed");
		if(curr_bid >= bid || !open) return null; // if we can't bid
		curr_bid = bid;
		if(curr_bid >= final_price) {
			curr_bid = final_price;
			open = false;
		}
		bids.add(curr_bid);
		Client.flag = true;
		bidders.add(Client.user_name);
		return Client.user_name;
		
		
	}
	
	
	
	
	

	
}
