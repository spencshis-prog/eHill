package clientside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.EventListener;
import java.util.concurrent.TimeUnit;

import javax.swing.event.EventListenerList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;



public class graphics extends Application {


	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("JavaFX Welcome");

		primaryStage.show();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				Client.user_name = userTextField.getText();
				Client.pass = pwBox.getText();
				Client.validate = true;
				
				//runner(primaryStage, actiontarget);
				
				
				//TODO Listener
								try {
									TimeUnit.SECONDS.sleep(1);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								boolean help = Client.valid_user;
								if(Client.valid_user) {
									primaryStage.close();
									home();
								}else {
								actiontarget.setFill(Color.FIREBRICK);
								actiontarget.setText("Invalid Username or Password");
								}
				
			}
		});

	}

	/*
	 * After logging in, a customer must be able to select any item to bid on and place a bid, 
	which is sent to the server.
	4. A customer should be notified of all bids placed by all customers during their login 
	session, and be able to view a history of those bids.
	5. The client must always inform the customer of the most recent highest bid from any 
	customer, so that customers are aware of how much to bid next.
	6. The client must also show the customer whether an item can be bid upon further, or 
	whether the auction for that item is closed, in which case the customer should be shown 
	the winning customer (if any) and their final highest bid.
	7. The client should tell a customer when a bid is invalid. Recall that a bid can be invalid if 
	the bid amount is too low, or if the auction for that item is closed. The reason should be 
	visible to the customer.
	8. You should provide “quit” and/or “logout” buttons that end the program or parts of the 
	program gracefully.
	 */
	public static void home() {

		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		//TempItem item = new Item("Shoes", "Lightly worn but cool Also I have a big chungus and want to write more text", 60, 5.00);
		ScrollPane scroll = new ScrollPane();

		VBox view = new VBox();

		scroll.setContent(view);
		for(int i = 0; i < Client.list.size(); i ++) view.getChildren().add(Item.toHBOXS(Client.list.get(i),stage));




		Scene scene = new Scene(scroll, 300,100);
		stage.setScene(scene);
		stage.show();

	}


	static boolean val_bid = true;
	private static IntegerProperty timeSeconds;
	public static void view(Item item) {


		//Countdown Timer



		//	Set up Stage 
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10, 10, 10, 10));;
		VBox Left = new VBox();
		Left.setPadding(new Insets(10, 30, 0, 0));;
		Left.setSpacing(20);
		VBox Right = new VBox();
		root.setLeft(Left);
		root.setRight(Right);




		//		Left side of Stage Variables
		Label name = new Label(item.name);
		Label desc = new Label(item.description);
		Button back = new Button("Back");


		//		RIGHT SIDE OPEN VAR
		HBox TimeBox = new HBox();
		HBox Curr_bid = new HBox();
		VBox bids = new VBox();
		Label timetext = new Label("Time left: ");
		Label time = new Label();
		Label BID = new Label("");
		Label NAME = new Label("");
		Label Cur_bid = new Label("Current Bid: ");
		HBox we_bid = new HBox();
		TextField bid = new TextField();
		Button set_bid = new Button("Bid: ");
		Button buy = new Button ("Buy Now for $" + item.final_price);

		//		RIGHT SIDE CLOSE VAR
		Button viewres = new Button("View results");
		HBox end = new HBox();
		end.setSpacing(10);
		end.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;" + "-fx-border-color: black;");
		Label bought = new Label("Bought by: ");
		VBox Buyers = new VBox();
		end.getChildren().add(bought);
		Label hist = new Label("Bidding History");
		hist.setUnderline(true);

		viewres.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Right.getChildren().clear();
				Right.setSpacing(10);
				Curr_bid.getChildren().clear();
				end.getChildren().add(bids);
				Right.getChildren().add(end);
				Right.getChildren().add(hist);

				// Now the List of Buyers

				for(int i = 0; i < item.bids.size(); i ++) {
					HBox simp = new HBox();
					Label b1d = new Label("$" + String.format("%.2f", item.bids.get(i)));
					Label n4me = new Label(item.bidders.get(i) + ": ");
					simp.getChildren().add(n4me);
					simp.getChildren().add(b1d);
					Buyers.getChildren().add(simp);
				}
				Right.getChildren().add(Buyers);
			}
		});


		//Timer





		//	Left side of Stage

		name.setFont(new Font("Arial", 30));

		desc.setFont(new Font("Arial", 15));
		desc.setWrapText(true);
		desc.setMaxWidth(220);

		Left.getChildren().add(name);
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				stage.close();
				home();

			}
		});

		Left.getChildren().add(desc);
		root.setBottom(back);

		//Right Side
		if(item.open) {
			//Timer
			Timeline timer = new Timeline();
			Text invalid = new Text();
			
			timeSeconds = new SimpleIntegerProperty(item.duration);
			timer.setCycleCount(Timeline.INDEFINITE);



			timer.getKeyFrames().add(new KeyFrame(Duration.seconds(item.duration+1),
					new KeyValue(timeSeconds, 0)));




			timer.playFromStart();

			Timeline timeline = new Timeline();


			KeyFrame upd = new KeyFrame(Duration.seconds(0.5), 
					event -> {
						if(!item.bids.isEmpty()) {
							BID.setText("$" + String.format("%.2f", item.bids.get(item.bids.size()-1)));
							NAME.setText(item.bidders.get(item.bids.size()-1));
						}
						if(val_bid == false) {
							invalid.setFill(Color.FIREBRICK);
							invalid.setText("INVALID BID: Bid too low");
						}
						if (item.open == false) {
							timer.stop();
							timeline.stop();
							timeSeconds.set(0);
							item.open = false;
							invalid.setFill(Color.FIREBRICK);
							invalid.setText("Auction Closed");
							Right.getChildren().add(viewres);
						}



					});
			timeline.getKeyFrames().add(upd);
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.playFromStart();






			TimeBox.setSpacing(10);
			TimeBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
					+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
					+ "-fx-border-radius: 5;" + "-fx-border-color: black;");

			time.textProperty().bind(timeSeconds.asString());
			TimeBox.getChildren().add(timetext);
			TimeBox.getChildren().add(time);



			Curr_bid.setSpacing(10);
			Curr_bid.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
					+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
					+ "-fx-border-radius: 5;" + "-fx-border-color: black;");

			int help = item.bids.size();
			if(item.bids.isEmpty()) {
				BID.setText("$" + String.format("%.2f", item.curr_bid));
				NAME.setText("No Bids Yet");
			}
			else {
				BID.setText("" + item.bids.get(item.bids.size()-1));
				NAME.setText(item.bidders.get(item.bids.size()-1));
			}
			bids.getChildren().add(BID);
			bids.getChildren().add(NAME);

			Curr_bid.getChildren().add(Cur_bid);
			Curr_bid.getChildren().add(bids);

			we_bid.setSpacing(10);
			we_bid.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
					+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
					+ "-fx-border-radius: 5;" + "-fx-border-color: black;");

			we_bid.getChildren().add(set_bid);
			we_bid.getChildren().add(bid);
			bid.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
						String newValue) {
					if (!newValue.matches("\\d*")) {
						bid.setText(newValue.replaceAll("[^\\., ^0-9]", ""));
					}
				}
			});





			Right.getChildren().add(TimeBox);
			Right.getChildren().add(Curr_bid);
			Right.getChildren().add(we_bid);
			Right.getChildren().add(buy);
			Right.getChildren().add(invalid);

			set_bid.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {
					String in = bid.getText();
					in = in.replaceFirst("\\.", "A");
					in = in.replaceAll("\\.", "");
					in = in.replaceFirst("[A]", ".");
					double bid_val = Double.parseDouble(in);
					String name = item.Bid(bid_val);
					if(name == null) {
						//TODO Output saying bid is invalid
						val_bid = false;
					}


				}
			});

			buy.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {

					String name = item.Bid(item.final_price);
					if(name == null) {
						//TODO Output saying bid is invalid
						val_bid = false;
					}


				}
			});
		}
		else {	//	If Bid is closed
			Right.getChildren().clear();
			Right.setSpacing(10);
			Curr_bid.getChildren().clear();
			if(item.bids.isEmpty()) {
				BID.setText("$" + String.format("%.2f", item.curr_bid));
				NAME.setText("No Bids Yet");
			}
			else {
				BID.setText("" + item.bids.get(item.bids.size()-1));
				NAME.setText(item.bidders.get(item.bids.size()-1));
			}
			bids.getChildren().add(BID);
			bids.getChildren().add(NAME);
			end.getChildren().add(bids);
			Right.getChildren().add(end);
			Right.getChildren().add(hist);

			// Now the List of Buyers

			for(int i = 0; i < item.bids.size(); i ++) {
				HBox simp = new HBox();
				Label b1d = new Label("$" + String.format("%.2f", item.bids.get(i)));
				Label n4me = new Label(item.bidders.get(i) + ": ");
				simp.getChildren().add(n4me);
				simp.getChildren().add(b1d);
				Buyers.getChildren().add(simp);
			}
			Right.getChildren().add(Buyers);

		}
		//	Display Stage
		Scene scene = new Scene(root, 530, 300);
		stage.setScene(scene);
		stage.show();



	}

	void runner(Stage primarystage, Text actiontarget) {
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
