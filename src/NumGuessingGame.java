/*
 * @authors: Cristian Herrera and Angie Sanchez
 * 
 * @program: NumGuessingGame.java
 * 
 * @dateCreated: 11/05/2022
 * 
 * @version: 2.00
 * 
 * @description:
 * 
 * 	A basic JAVA program that calculates a random 
 *  number starting from 1 through 100. The program then asks the user 
 * 	to guess the number. If the user guesses too high or too low then the 
 *	program should output "too high" or "too low" accordingly. The 
 *	program must let the user continue to guess until the user correctly 
 *	guesses the number.
 
 		★ Modify the program to output how many guesses it took the user to correctly 
		guess the right number. (CHECK)
 
		★★ Modify the program so that instead of the user guessing a number the 
		computer came up with, the computer guesses the number that the user has 
		secretly decided. The user must tell the computer whether it guessed too high 
		or too low. (CHECK)
 
		★★★★ Modify the program so that no matter what number the user thinks of 
		(1-100) the computer can guess it in 7 or less guesses. (CHECK)
		
		★★★ Implement exception handling for all user input if a menu is used. (CHECK)
		
		★★★ Add comments to your class definition, methods, and output statements. (CHECK)
		
		★★★ Add Team members names, program name, Date, and version of 
		completed program. (CHECK)
 */

// Random generator packages
import java.util.Random;

// GUI packages
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

// My custom Exceptions
import Exceptions.NumGreaterThanException;
import Exceptions.NumLessThanException;
import Exceptions.NumNotInRangeException;

public class NumGuessingGame {
	
	// MAIN METHOD - Used as a menu to select and launch games.
	public static void main(String[] args) {
		// The GUI object 
		final JPanel panel = new JPanel();
		
		// Welcome to Application message
		JOptionPane.showMessageDialog(null, "Welcome to the Guessing Games. Press Ok to then pick your game and start playing.", "Welcome", JOptionPane.INFORMATION_MESSAGE);		

		// Game picker menu logic
		while (true) {
			
			try {
				// Styles GUI
				UIManager.put("OptionPane.yesButtonText", "Computer Guessing Game");
				UIManager.put("OptionPane.noButtonText", "User Guessing Game");
				UIManager.put("OptionPane.cancelButtonText", "Quit");
				
				// Saving user response into variable
		    	int response = JOptionPane.showConfirmDialog(panel, "Now, pick your guessing game. \n\nWould you like to guess the computers number? Click \"User Guessing Game\". \nOr would you like the computer to guess your number? Click \"Computer Guessing Game\".", "Pick your game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		    	
		    	// Using save response value to launch selected game
		    	if (response == JOptionPane.YES_OPTION) computerGuessing(1, 100, 0);
		    	else if (response == JOptionPane.NO_OPTION) userGuessing();
		    	else {JOptionPane.showMessageDialog(panel, "Leaving so soon? Thank you for playing. Have a great day!", "Goodbye", JOptionPane.INFORMATION_MESSAGE); break;}; 
			} 
			
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, "No worries. You will win next time.", "Its okay", JOptionPane.INFORMATION_MESSAGE); 
			}
		}   
	}
	
	// COMPUTER GUESSING GAME - Implementing a recursive function and binary search algorithm 
	public static void computerGuessing (int low, int high, int count) {
		
		// The GUI object 
		final JPanel panel = new JPanel();
		UIManager.put("OptionPane.yesButtonText", "Higher");
		UIManager.put("OptionPane.noButtonText", "Lower");
		UIManager.put("OptionPane.cancelButtonText", "I give up");
		
		// Welcome message - making sure it only runs once.
		if (high == 100 & low == 1) {
			JOptionPane.showMessageDialog(panel,"Welcome to our Computer Guessing game. "
					+ "\n\nNow think of a whole number between 1-100 and our application will run a proprietary algorthm to guess your number in 7 trys\nGAURANTEED."
					+ " If it doesnt you win. All you have to do is tell the computer too high or too low when prompted. Good luck!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
		}
		
		// Handle errors when user lie. The algo always knows.
		try {
			// GAURD CLAUSE. If the lowest range goes higher than the high range. End game. User is cheating...
			if (low > high) throw new Exception();
			
			// Keeping tack of guesses
			count++;

			// Saving middle of range to implement binary search 
			int mid = (high-low)/2;
			System.out.println(mid);
	       
			// FIRST GUESS. User can select higher or lower.
	    	int response = JOptionPane.showConfirmDialog(panel, "Is your number " + mid + "?\nSelect 'I give up', if our algorithm beat you.", "Your not going to win...", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

	    	// HIGHER OR LOWER GUESS - Changes range base on input for binary search and calls function again
	        if (response == JOptionPane.YES_OPTION) computerGuessing(mid+1, high, count);
	        else if (response == JOptionPane.NO_OPTION) computerGuessing(low, mid-1, count);
	        
	        // QUIT GAME - user presses presses exit
	        else JOptionPane.showMessageDialog(panel, "Aha we were correct! The house always wins! And it only took " + count + " try(s).", "THE COMUPTER WINS. ALL HAIL THE ALGORITHIM", JOptionPane.INFORMATION_MESSAGE); 
	        

		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "Last possible choice. Your answer was guessed in " + count + " trys or you are cheating. Sorry but the house always wins.", "THE COMPUTER WINS. ALL HAIL THE ALGORITHIM", JOptionPane.ERROR_MESSAGE); 
		}
	}
	
	// USER GUESSING GAME - implements random numbers and error handling and infinite loop
	public static void userGuessing () {
		
		// A GUI object
		final JPanel panel = new JPanel();
		UIManager.put("OptionPane.cancelButtonText", "I give up");

		// Computer deciding random number
		Random rand = new Random();
		int randomNum = rand.nextInt(100) + 1;
		
		// Guesses it takes the user to win game
		int guesses = 0;
		
		// Welcome message.
		JOptionPane.showMessageDialog(panel,"Welcome to our User Guessing game. "
				+ "\n\nThe computer has decided a random number from 1 -100 using a complex algorithm. It is your job to guess the answer.\n"
				+ "Input your guess into the text box and the computer will tell you whether its too high or too low. Good Luck!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
		
		// Game Logic. Ends loop when user wins game or quits.
		while (true) {
			
			// Handle errors when user inputs wrong information.
			try {
				
				// Loop iteration for a guess count.
				guesses++;
				
				// Take get input from user. 
				String strInput = JOptionPane.showInputDialog(panel, "Guess a number or press 'I give up' to quit.");		
				
				// QUIT GAME - user presses cancel
				if (strInput.equals("")) throw new NumberFormatException();
				
				
				// Parse string input
				int numInput = Integer.parseInt(strInput);
								
				// WRONG ANSWER! Throw error and throw error with a message why.
				if (numInput < 1 || numInput > 100) throw new NumNotInRangeException();
				if (numInput > randomNum) throw new NumGreaterThanException(); 
				if (numInput < randomNum) throw new NumLessThanException();
				
				
				// CORRECT ANSWER! Show message and end loop.
				if (numInput == randomNum) {
					JOptionPane.showMessageDialog(panel, "Correct. It only took you "  + guesses + " try(s)!", "WINNER", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			}
			
			// ERRORS and the messages relating to each
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(panel, "You did NOT enter a integer! Start Over!", "Wrong input", JOptionPane.ERROR_MESSAGE);		
			}
			
			catch (NumGreaterThanException e) {
				JOptionPane.showMessageDialog(panel, "Too high. Press Ok to try again.", "Wrong", JOptionPane.WARNING_MESSAGE);
			}
			
			catch (NumLessThanException e) {
				JOptionPane.showMessageDialog(panel, "Too Low. Press Ok to try again.", "Wrong", JOptionPane.WARNING_MESSAGE);
			}
			catch (NumNotInRangeException e) {
				JOptionPane.showMessageDialog(panel, "Your number is not in range silly. Guess between 1 - 100.", "WINNER", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

