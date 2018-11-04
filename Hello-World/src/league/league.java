package league;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import database.database;

public class league {
	public static void main(String args[]) {
		File players = new File("players.txt");
		
		//we want rows for : playername, champion he played, what he did in the game
		database db = new database(3);
		Scanner console = new Scanner(System.in);
		db.load(players);
		int input = 0;
		String name;
		String[] line = {"something went wrong", "something went wrong", "something went wrong"};
		int found;
		
		/* should have different options on what to do.
		 * 1. add a new player to the list
		 * 2. look up a player
		 * 3. remove a player from the list because he did well?
		 *  
		 */
		System.out.println("welcome, never again you will forget who fed in your games!");
		
		while(input != 3) {
			System.out.println("you have the following options:");
			System.out.println("----1. Add a new player");
			System.out.println("----2. Look for a player");
			System.out.println("----3. Exit");
			
			try {
				input = console.nextInt();
				console.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Wrong input!");
			} 
			
			if(input == 2) {
				System.out.println("What was the players name?");
				name = console.nextLine();
				found = db.find(0, name);
				if(found == -1) {
					
					System.out.println();
					System.out.println();
					System.out.println();
					System.out.println();
					
					System.out.println("The player " + name + " was not found in your List");
					
					System.out.println();System.out.println();System.out.println();System.out.println();
				} else {
					try {
						line = db.getLine(found);
					} catch(Exception e) {
						//here this should never happen
						e.printStackTrace();
						return;
					}
					
					System.out.println();
					System.out.println();
					System.out.println();
					System.out.println();
					
					System.out.println(name + " played " + line[1] + " in your game.");
					System.out.println("your comment: " +line[2]);
					System.out.println("Have fun with your revenge :D");
					
					System.out.println();
					System.out.println();
					System.out.println();
					System.out.println();
					
				}
			} else if(input == 1){
				String[] s = new String[3];
				System.out.println("Which player would you like to add to your list?");
				s[0] = console.nextLine();
				
				if(db.find(0, s[0]) == -1) {
					System.out.println("What Champion did " + s[0] + "play?");
					s[1] = console.nextLine();
					System.out.println("write a comment on what he did or what you plan to do to him the next time you face him");
					s[2] = console.nextLine();
					
					db.add(s);
					System.out.println();
					System.out.println("The player was successfully added to your list");
				} else {
					System.out.println("this player is already in your list!");
				}
			} else if(input == 3){
				db.save(players);
				System.out.println("your entries were saved, looking forward to see you again!");
			} else {
				System.out.println("wrong input");
			}
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
