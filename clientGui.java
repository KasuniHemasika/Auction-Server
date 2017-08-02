import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JTable;


public class clientGui{
	private String [] prices=new String[8];
	private String Name;
	public clientGui(String[] array,String Name){
		this.prices=array;
		this.Name=Name;
	}
	public void createTable(){
		JFrame frame = new JFrame(Name+"-Stock Details");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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