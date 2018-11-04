package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class database {
	//this database only saves strings and it's rows are only addressable by their index!
	//always at least half of the array size is used
	static String[][] arr;
	static int rows;
	static int size;
	static int pos;
	static boolean gaps;
	static LinkedList<Integer> gaplist;
	static boolean valid[];
	
	public database(int rows) {	//have to know yourself which row means what
		size = 8;
		pos = 0;
		this.rows = rows;
		arr = new String[size][rows];
		valid = new boolean[size];
		gaplist  = new LinkedList<Integer>();
		gaps = false;
	}
	//Constructor
	public void add(String[] s) {
		if(s.length != rows) {
			System.out.println("unpassendes input");
			return;
		} else {
			if(pos >= size) {
				extend();
			} else if(gaps == true) {
				//Für den Fall dass es noch offene Lücken innerhalb der Liste hat wollen wir diese auffüllen
				
				int p = gaplist.removeFirst();
				for(int i = 0; i < rows; i++) {
					//copy the input array into arr
					arr[p][i] = s[i];
				}
				valid[p] = true;
				
				if(gaplist.isEmpty())
					gaps = false;
			}
			for(int i = 0; i < rows; i++) {
				//copy the input array into arr
				arr[pos][i] = s[i];
				valid[pos] = true;
			}
			pos++;
		}
	}
	
	public void extend() {
		//to be implemented
		//has to build a bigger array, copy arr into it and make arr point to it
		String[][] arr_new = new String[size * 2][rows];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < rows; j++) {
				arr_new[i][j] = arr[i][j];
			}
		}
		
		this.arr = arr_new;
		
	}
	
	public void load(File f) {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.size = Integer.parseInt(br.readLine());
			this.rows = Integer.parseInt(br.readLine());
			this.pos = Integer.parseInt(br.readLine());
			
			arr = new String[size][rows];
			
			for(int i = 0; i < pos; i++) {
				for(int j = 0; j < rows; j++) {
					arr[i][j] = br.readLine();
				}
			}
			
		} catch(IOException e) {
			e.getMessage();
		}
	}
	
	public void save(File f){
		//save this stuff into a textfile
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
		
			bw.write(Integer.toString(size));
			bw.newLine();
			bw.write(Integer.toString(rows));
			bw.newLine();
			bw.write(Integer.toString(pos));
			bw.newLine();
			
			for(int i = 0; i < pos; i++) {
				for(int j = 0; j < rows; j++) {
					bw.write(arr[i][j]);
					bw.newLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null) {
					bw.close();
				}
				if(fw != null) {
					fw.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int find(int row, String s) {
		for(int i = 0; i < pos; i++) {
			if(arr[i][row].equals(s))
				return i;
		}
		return -1;
	}
	
	public boolean delete(int index) {
		if(index >= pos) {
			//nothing was deleted
			return false;
		} else {
			for(int i = 0; i < rows; i++) {
				arr[index][i] = null;
			}
			valid[index] = false;
			gaplist.add(index);
			gaps = true;
			return true;
		}
	}
}
