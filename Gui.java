import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JTable;


public class Gui{
	private String [] prices=new String[8]; //get prices from the Server
	public Gui(String[] array){
		this.prices=array;
	}
	public void createTable(){ //create JFrame and Table
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Object data[][] ={{"FB","Facebook",prices[0]},{"VRTU","Virtusa Corporation - common stock",prices[1]},
						{"MSFT","Microsoft Corporation - Common Stock",prices[2]},
						{"GOOGL","Google Inc. - Class A Common Stock",prices[3]},{"YHOO","Yahoo! Inc. - Common Stock",prices[4]},
						{"XLNX","Xilinx",prices[5]},{"TSLA","Tesla Motors",prices[6]},{"TXN","Texas Instruments Incorporated - Common Stock",prices[7]}};
		Object columns[]={"Symbol","Sequrity Name","Price"};
		JTable datatable = new JTable(data,columns);
		JScrollPane x = new JScrollPane(datatable);
		frame.add(x,BorderLayout.CENTER);
		frame.setSize(1000,200);
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
	
	


}