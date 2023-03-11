/*
 * This code is protected under the Gnu General Public License (Copyleft), 2005 by
 * IBM and the Computer Science Teachers of America organization. It may be freely
 * modified and redistributed under educational fair use.
 */

/*
 * @author Luke Zhang and Ishan Sahu
 */
import csta.ibm.pong.Game;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class SnakeGame extends Game {
	// Add any state variables or object references here
	ArrayList<Snake> snake = new ArrayList<Snake>();
	public static int snakeCounter = 4;
	Rat rat;
	int[] lastDirectionMoved = {1, 0}; // stores current movement direction 
	
	public static final int HEIGHT = 600; // getFieldHeight() for snake game area
	public static final int WIDTH = 600; // getFieldWidth() for snake game area
	
	Random r = new Random(); // using random class to produce random numbers to spawn rats
	
	@Override
	public void setup() {
		//set delay
		setDelay(75);
		
		// spawn a rat
		rat = new Rat();
		rat.setSize(20, 20);
		rat.setX((int)(Math.random()*25 + 1) * 20);		// randomly sets x and y coordinates for rat
		rat.setY((int)(Math.random()*25 + 1) * 20);
		rat.setColor(Color.RED);
		add(rat);
		
		// initialize a snake with 4 squares
		int initX = 1, initY = 6;
		for (int i = 0; i < 4; i++) {
			Snake snakePiece = new Snake();
			snakePiece.setSize(20, 20);					//sets size to 20x20
			snakePiece.setColor(Color.GREEN);
			snakePiece.setX(initX*24 + 2);				//sets location across 25x25 grid where each grid is 24x24 pixels 
			snakePiece.setY(initY*24 + 2);				// +2 gives padding so that each square in snake is seen individually
			initX += 1; 
			add(snakePiece);	
			snake.add(snakePiece);
			
		}
		//printSnake();
		repaint();
	}	
	
	
	@Override
	public void act() {
		// check if out of bounds or collision with rat obj
		Snake sp = snake.get(0);
		if (sp.collides(rat)) {
			//printSnake();
			//System.out.println();
			rat.setX((int)(Math.random()*25 + 1) * 20);			// randomly sets x and y coordinates for rat
			rat.setY((int)(Math.random()*25 + 1) * 20);
			rat.setColor(Color.RED);
			Snake np = new Snake();
			Snake pre = snake.get(snakeCounter -1);
			np.setSize(20, 20);
			np.setColor(Color.GREEN);
			np.setX(pre.getX());
			np.setY(pre.getY());
			snake.add(np);
			add(np);
			snakeCounter ++;
		}
		if (sp.getX() >= 575 || sp.getY() >= 525 || sp.getX() <= 0 || sp.getY() <= 0) {
			String msg = readFile("endFile.txt");
			msg += "\n your score: " + snakeCounter;
			JOptionPane.showMessageDialog(null, msg);
			System.exit(0);
		}
		
		for (int j = 2; j < snakeCounter; j++) {
			if (sp.collides(snake.get(j))) {
				//System.out.println(sp.getX() + " " + sp.getY() + " " + snake.get(j).getX() + " " + snake.get(j).getY());
			}
		}
		
		
		moveSnake();
		
		//printSnake();
		
		// get key pressed and change direction moved accordingly
		// however, if movement is directly opposite the current direction (ie. moving up, and user hits S to move down)
		// then do nothing
		if (WKeyPressed() && lastDirectionMoved[1] != 1) { 
			lastDirectionMoved[0] = 0;
			lastDirectionMoved[1] = -1;
		}
		if (SKeyPressed() && lastDirectionMoved[1] != -1) {
			lastDirectionMoved[0] = 0;
			lastDirectionMoved[1] = 1;
		}
		if (AKeyPressed() && lastDirectionMoved[0] != 1) {
			lastDirectionMoved[0] = -1;
			lastDirectionMoved[1] = 0;
		}
		if (DKeyPressed() && lastDirectionMoved[0] != -1) {
			lastDirectionMoved[0] = 1;
			lastDirectionMoved[1] = 0;
		}

		repaint();
	}
	/**
	 * no paramaters, no return
	 * Prints current location of the snake (x and y coordinates) for each snake piece
	 * used for testing purposes
	 */
	public void printSnake() {
		for (Snake sp: snake) {
			System.out.print(sp.getX() + " " + sp.getY() + " ");
		}
		System.out.println();
	}

	
	/**
	 * no parameters no return
	 * take snake tail, move to head
	 * this entire method must be kept in one function because they must be executed in order
	 */
	public void moveSnake() {
		Snake tail = snake.get(snakeCounter-1);
		Snake head = snake.get(0);
		tail.setX(head.getX() + lastDirectionMoved[0]*24);
		tail.setY(head.getY() + lastDirectionMoved[1]*24);
		snake.add(0, tail);
		snake.remove(snakeCounter);
	}
	
	/**
	 * 
	 * @param fileName
	 * @return String
	 * reads text from file and saves it into a string
	 */
	
	public static String readFile(String fileName) {
		String txt = "";
		try {
			File file = new File(fileName);
			Scanner input = new Scanner(file);
			// read instructions out of file "Instructions.txt"
			while (input.hasNextLine()) {
				txt += input.nextLine();
				txt += "\n";
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		return txt;
		
	}
	
	public static void main(String[] args) {
		String msg = readFile("Instructions.txt");
		JOptionPane.showMessageDialog(null, msg);
		SnakeGame s = new SnakeGame();
		s.setVisible(true);
		s.initComponents();		
	}
}