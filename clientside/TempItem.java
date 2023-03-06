package clientside;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TempItem {
	
	private String name, description, winner;
	private double finalPrice;
	
	public TempItem() {}
	
	public TempItem(String name, String description, String winner, double finalPrice) {
		this.name = name;
		this.description = description;
		this.winner = winner;
		this.finalPrice = finalPrice;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", description=" + description + ", winner=" + winner + ", finalPrice="
				+ finalPrice + "]";
	}
	
	public static HBox toHBOX(TempItem item) {
		HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: black;");

        Label label = new Label(item.name);
        
        Text descr = new Text(item.description);
        descr.setFont(Font.font(8));
        descr.setWrappingWidth(50);
        VBox help = new VBox();
        help.getChildren().add(label);
        help.getChildren().add(descr);
        Button butt = new Button("View");
        Label bid = new Label("Price: $" + String.format("%.2f",item.finalPrice));
        
        butt.setOnAction(e -> item.Bid(500.0)); //Temporary Variable 500.0
        hbox.getChildren().add(butt);
        hbox.getChildren().add(help);
        
        hbox.getChildren().add(bid);
        
        return hbox;
		
		
		
		
	}
	
	public void Bid(double bid) {
		System.out.println("button pressed");
		finalPrice = bid;
		Client.flag = true;
	}
}
