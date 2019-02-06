import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Lab3 extends GameEngine {
	// Main Function
	public static void main(String args[]) {
		// Warning: Only call createGame in this function
		// Create a new Lab3
		createGame(new Lab3());
	}

	//-------------------------------------------------------
	// Game
	//-------------------------------------------------------
	
	// Booleans to keep track of whether a key is pressed or not
	boolean left, right, up;
	boolean gameOver;
	

	//-------------------------------------------------------
	// Spaceship
	//-------------------------------------------------------

	// Spaceship position & Velocity
	double spaceshipPositionX, spaceshipPositionY;
	double spaceshipVelocityX, spaceshipVelocityY;
	double shipVelocityX,shipVelocityY;

	// Spaceship angle
	double spaceshipAngle;
	double angle;
	
	double groundlevel=400;
	double gravity = 4;
	double XlandingSpeed=80, YlandingSpeed=80;
	double lander;

	// Code to update 'move' the spaceship
	public void updateSpaceship(double dt) {
		if(up == true) {
			// Increase the velocity of the spaceship
			// as determined by the angle
			spaceshipVelocityX +=  sin(spaceshipAngle) * 250 * dt;
			spaceshipVelocityY += -cos(spaceshipAngle) * 250 * dt;
		}

		// If the user is holding down the left arrow key
		// Make the spaceship rotate anti-clockwise
		if(left == true) {
			spaceshipAngle -= 90 * dt;
		}

		// If the user is holding down the right arrow key
		// Make the spaceship rotate clockwise
		if(right == true) {
			spaceshipAngle += 90 * dt;
		}

		// Make the spaceship move forward
		spaceshipPositionX += spaceshipVelocityX * dt;
		spaceshipPositionY += spaceshipVelocityY * dt;
		
		if(spaceshipPositionX>500){
			spaceshipPositionX = 0;
		}
		if(spaceshipPositionX<0){
			spaceshipPositionX = 500;
		}
		
		
	}
	
	public void updateGame(double dt) {
		
		
		if(spaceshipPositionY+15>groundlevel){
			if(shipVelocityX > XlandingSpeed){
				gameOver = true;
			}
			if(shipVelocityY > YlandingSpeed){
				gameOver = true;
			}
			spaceshipPositionY = groundlevel-15;
			spaceshipVelocityY = 0;
			spaceshipVelocityX = spaceshipVelocityX * .5;
			if(spaceshipVelocityX<.5){
				spaceshipVelocityX=0;
			}
			if(angle>30){
				gameOver = true;
			}
		}
		
		//stops the ship from exiting the screen from the top
		if(spaceshipPositionY<0){
			spaceshipPositionY = 0;
			spaceshipVelocityY = 0;
		}
		spaceshipVelocityY+=gravity;
		
		
		
	}


	// Function to draw the spaceship
	public void drawSpaceship() {
		// Set the color to white
		changeColor(white);

		// Save the current transform
		saveCurrentTransform();

		// ranslate to the position of the asteroid
		translate(spaceshipPositionX, spaceshipPositionY);

		// Rotate the drawing context around the angle of the asteroid
		rotate(spaceshipAngle);
		
		drawRectangle(-10, -10, 20, 30);
		//cone
		drawLine(-10 ,-10 ,0 ,-20 );
		drawLine(10 ,-10 ,0 ,-20 );
		//fins
		drawLine(-10 ,0 ,-15 ,20 );
		drawLine(10 ,0 ,15 ,20 );
		drawLine(-15 ,20 ,15 ,20 );
		drawCircle(0, 0, 5);
		
		if(up){
			changeColor(yellow);
			drawLine(-10,15,0,40);
			drawLine(10,15,0,40);
			changeColor(red);
			drawLine(-5,15,0,30);
			drawLine(5,15,0,30);
		}
		if(left){
			changeColor(red);
			drawLine(10,15,12,20);
			drawLine(15,15,12,20);
			changeColor(yellow);
			drawLine(10,15,12,30);
			drawLine(15,15,12,30);
			
		}
		if(right){
			changeColor(red);
			drawLine(-10,15,-12,20);
			drawLine(-15,15,-12,20);
			changeColor(yellow);
			drawLine(-10,15,-12,30);
			drawLine(-15,15,-12,30);
		}
	

		// Restore last transform to undo the rotate and translate transforms
		restoreLastTransform();
	}
	
	public void drawGround() {
	
		// Draw the actual spaceship
		drawLine(0,  groundlevel,  500,  groundlevel);
		
	}


	
	
	//-------------------------------------------------------
	// Game
	//-------------------------------------------------------

	public void init() {
		// Initialise game boolean
		gameOver = false;

		// Initialise key booleans
		left  = false;
		right = false;
		up    = false;

		// Initialise Spaceship
		spaceshipPositionX = 250; spaceshipPositionY = 250;
		spaceshipVelocityX = 0;   spaceshipVelocityY = 0;
		spaceshipAngle = 0;
		
		lander = rand(400);
		
	}

	// Updates the display
	public void update(double dt) {
		// If the game is over
		if(gameOver == true) {
			// Don't try to update anything.
			return;
		}
		

		// Update the spaceship
		updateSpaceship(dt);
		shipVelocityX=spaceshipVelocityX;
		shipVelocityY=spaceshipVelocityY;
		updateGame(dt);
		

		

		//-------------------------------------------------------
		// Add code to check for collisions
		//-------------------------------------------------------
	}

	// This gets called any time the Operating System
	// tells the program to paint itself
	public void paintComponent() {
		// Clear the background to black
		changeBackgroundColor(black);
		clearBackground(500, 500);
		drawGround();
		String s1,s2;
	
		angle=spaceshipAngle % 360;
		if(angle<0){
			angle = angle*-1; 
		}
		
		s1= String.format("%.2f", angle);
		drawText(30, 30, s1);
		s2= String.format("%.2f",spaceshipVelocityX);
		drawText(30, 60, s2);

		// If the game is not over yet
		if(gameOver == false) {
			// Paint the Spaceship
			drawSpaceship();

		} else {
			// If the game is over
			// Display GameOver text
			changeColor(white);
			drawText(85, 250, "GAME OVER!", "Arial", 50);
		}
	}

	// Called whenever a key is pressed
	public void keyPressed(KeyEvent e) {
		// The user pressed left arrow
		if(e.getKeyCode() == KeyEvent.VK_LEFT)  {
			left  = true;
		}
		// The user pressed right arrow
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		// The user pressed up arrow
		if(e.getKeyCode() == KeyEvent.VK_UP)    {
			up    = true;
		}
	}

	// Called whenever a key is released
	public void keyReleased(KeyEvent e) {
		// The user released left arrow
		if(e.getKeyCode() == KeyEvent.VK_LEFT)  {
			left  = false;
		}
		// The user released right arrow
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		// The user released up arrow
		if(e.getKeyCode() == KeyEvent.VK_UP)    {
			up    = false;
		}
	}
}