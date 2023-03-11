/*
 * This code is protected under the Gnu General Public License (Copyleft), 2005 by
 * IBM and the Computer Science Teachers of America organization. It may be freely
 * modified and redistributed under educational fair use.
 */

import csta.ibm.pong.GameObject;
import java.util.Random;

public class Snake extends GameObject {
	
	/**
	 * Fill in this method with code that describes the behavior
	 * of a ball from one moment to the next
	 */
	public void act() {
		setX(getX());
		setY(getY());
	}
	
}