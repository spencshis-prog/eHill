This Code was Built By Spencer Shisler in Fall 2022 for the ECE 422 Final
The Server code connects to a mongoDB database and initializes variables for items based on the data in Mongo
Then the Server gets a list of Usernames and Passwords from Mongo as well
Now the Server waits for a client to connect to it
Once a client connects the client recieves the current information on the items
Then the Client will try and log on to the server, if the client's info is valid it will be allowed to view and bid on a variety of items
This bidding is updated with the server, so if someone else bids on the item, the client is immediatley notified of the bid and by who
