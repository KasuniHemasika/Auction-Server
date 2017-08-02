import java.util.HashMap;
import java.io.*;

class fileWriter{
	private HashMap<String,String[]> hmap;
	public fileWriter(HashMap<String,String[]> mymap){
		this.hmap=mymap;
	}
	public void write(){
		String csv = "output.csv";
		CSVWriter writer = new CSVWriter(new FileWriter(csv));

		for(Hashmap.Entry<String,String[]> entry: map.entrySet()){
			writer.writeNext(entry.getKey());
			writer.writeNext(entry.getValue());
		}
		writer.close();
	}

}