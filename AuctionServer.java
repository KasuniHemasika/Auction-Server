import java.util.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AuctionServer implements Runnable{
    public static final int BASE_PORT = 2000;     
    
    private ServerSocket serverSocket; //variable declaration
    private static int socketNumber;
    private Socket connectionSocket;
	private String Name;
	private String choice;
	private static FileWriter detailsWriter;
	
	public static HashMap<String,String> prices= new HashMap<>(); //create HashMap to retrieve data from csv file
    
    public AuctionServer(int socket) throws IOException { //constructor1
		serverSocket = new ServerSocket(socket);  
		socketNumber = socket; 
    }
	
	public AuctionServer(Socket socket) { //constructor2 -creates welcoming socket
		this.connectionSocket = socket; 
    }

    public void server_loop() throws IOException { 
		
		while(true) { //create new sockets to communicate
			Socket socket = serverSocket.accept(); 	    
			Thread worker = new Thread(new AuctionServer(socket)); //put each client into a thread 
			worker.start(); //call start() and then run()
		}
    }

	public synchronized void run() {
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream())); //to get input stream
			PrintWriter out = new PrintWriter(new OutputStreamWriter(this.connectionSocket.getOutputStream())); //to set output stream
			String line; 
			String [] details = new String[2]; 
			int i=0;
			Name=in.readLine(); //Get name of the client
			out.println("Enter 1 to bid and 2 to add item"); //send message asking whether to bid or add
			out.flush();
			choice=in.readLine(); //get client's choice
			
			if(choice.equals("1")){ //if client wants to bid
			
				for(line = in.readLine();  line != null && !line.equals("quit");  line = in.readLine()) {
					System.out.println(line);
					
					if(i%2==0){ //to get the symbol
						details[0]=line;
						if(prices.containsKey(line)){ //if symbol contains in the HashMap
							out.println((prices.get(line)));
							out.flush();
						}else{ //if symbol doesn't contain in the HashMap
							out.println("-1"); 
							out.flush();
							i--; //send back to get another symbol
						}
					}
					else{ //if bid is placed
						details[1]=line;  
						prices.replace(details[0],details[1]); //replace value in HashMap
						detailsWriter.append(Name+" , "+details[0]+" , "+details[1]+"\n"); //write into csv file
						detailsWriter.flush();
					}
					i=i+1;
				}
				
			}else { //if client wants to add items
				i=0; 
				
				for(line = in.readLine();  line != null && !line.equals("quit");  line = in.readLine()){ //read lines until client enters "quit"
					
					if(i%2==0){ //get the symbol from the client
						details[i] = line;
						i++;
					}
					
					else { //get the price from the client
						details[i] = line;
						i=0;
					}
				}
				prices.put(details[0],details[1]); //add new item to the HashMap
				
			}
			
		}catch (IOException e) { 
			System.out.println(e); 
		}
		
		//creating GUI with selected details
		String [] price= {prices.get("FB"),prices.get("VRTU"),prices.get("MSFT"),prices.get("GOOGL"),prices.get("YHOO"),
							prices.get("XLNX"),prices.get("TSLA"),prices.get("TXN")};
		clientGui gui = new clientGui(price,Name);
		gui.createTable();
		
		try { //close socket after client quits	    
			this.connectionSocket.close(); 
		}catch(IOException e) {
		}
    }

	
	public static void main (String args[])  throws IOException{
		
		try { //read "stocks.csv"
            BufferedReader br = new BufferedReader(new FileReader("stocks.csv")); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
				prices.put(details[0],details[2]);
            }

        }catch(Exception e){
            System.out.println(e.toString());
		}
		
		try{ //create new FileWriter to write Bid Details
			detailsWriter= new FileWriter("BidDetails.csv");
		}catch(Exception e){
		}
		
		AuctionServer server = new AuctionServer(BASE_PORT); //create the Server
		server.server_loop(); //run server
		detailsWriter.close(); //close the writer
		
	}

}