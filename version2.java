import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class version2 extends GameEngine {
	// Main Function
	public static void main(String args[]) {
		// Warning: Only call createGame in this function
		// Create a new Lab3
		createGame(new version2());
	}

	//-------------------------------------------------------
	// Game
	//-------------------------------------------------------
	
	// Booleans to keep track of whether a key is pressed or not
	boolean left, right, up;
	boolean gameOver;
	
	enum GameState {Menu, Playing, Options};
	GameState state = GameState.Menu;

	int menuOption = 0;
	

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
	
	double fuel;
	double wind;
	double windReading;

	

	// Code to update 'move' the spaceship
	public void updateSpaceship(double dt) {
		if(fuel<=0){
				fuel=0;
				up=false;
				left=false;
				right=false;
			}
		if(up){
			fuel=fuel-.5;
		}
		if(left){
			fuel=fuel-.2;
		}
		if(right){
			fuel=fuel-.2;
		}
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
	
	public void drawControlPanel(){
		String s1,s2,s3,f,w,l;
		//int l;
		drawImage(control, 0, 400, 500, 100);
		changeColor(white);
		//drawRectangle(25,405,120,60);
		//drawRectangle(98,407,40,15);
		//drawRectangle(98,427,40,15);
		//drawRectangle(98,447,40,15);

		angle=spaceshipAngle % 360;
		if(angle<0){
			angle = angle*-1; 
		}
		s1= String.format("%.2f", angle);
		changeColor(black);
		drawText(50, 425, ("Angle:"), "TimesRoman", 15);
		if(angle>landingAngle){
			changeColor(red);
			drawText(120, 425, (s1), "TimesRoman", 15);
			changeColor(white);
		}else{
			changeColor(white);
			drawText(120, 425, (s1), "TimesRoman", 15);
		}
		
		s2= String.format("%.2f",spaceshipVelocityX);
		
		changeColor(black);
		drawText(40, 450, ("X Velocity"), "TimesRoman", 15);
		
		
		if(spaceshipVelocityX > XlandingSpeed || spaceshipVelocityX < XlandingSpeed*-1 ){
			changeColor(red);
			drawText(120, 450, (s2), "TimesRoman", 15);
			changeColor(white);
		}else{
			changeColor(white);
			drawText(120, 450, (s2), "TimesRoman", 15);
		}
			
		s3= String.format("%.2f",spaceshipVelocityY);
		changeColor(black);
		drawText(40, 477, ("Y Velocity"), "TimesRoman", 15);
		
		if(spaceshipVelocityY > YlandingSpeed){
			changeColor(red);
			drawText(120, 477, (s3), "TimesRoman", 15);
			changeColor(white);
		}else{
			changeColor(white);
			drawText(120, 477, (s3), "TimesRoman", 15);
		}
		
		
		f= String.format("%.2f",fuel);
		changeColor(black);
		drawText(280, 480, ("Fuel:"), "TimesRoman", 15);
		if(fuel < 30){
			changeColor(red);
			drawText(340, 480, (f+" %"), "TimesRoman", 14);
			drawSolidRectangle(240, 385+(100-(fuel*.7)), 20, fuel*.7);
		
			changeColor(white);
		}else{
			changeColor(white);
			drawText(340, 480, (f+" %"), "TimesRoman", 14);
			drawSolidRectangle(240, 385+(100-(fuel*.7)), 20, fuel*.7);
		}
		
		
		windReading = wind;
		if(windReading < 0){
			windReading = windReading *-1;
		}
		w= String.format("%.2f",windReading);
		changeColor(black);
		drawText(280, 425, ("Wind"), "TimesRoman", 15);
		changeColor(white);
		drawText(340, 425, (w), "TimesRoman", 14);
		if(wind > 0){
			changeColor(white);
			drawText(320, 450, ("-->-->-->-->"), "TimesRoman", 14);
		}else if(wind < 0){
			changeColor(white);
			drawText(320, 450, ("<--<--<--<--"), "TimesRoman", 14);
		}else{
			changeColor(white);
			drawText(305, 455, ("--------"), "TimesRoman", 14);
		}
		l = String.format("%.0f", level+1);
		
		changeColor(white);
		drawText(420, 450, ("Level: "+l), "TimesRoman", 15);
		
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
	
	public void paintMenu() {
		//Play
		if(menuOption == 0) {
			changeColor(red);
		} else {
			changeColor(black);
		}
        drawText(50, 50, "Play");

		//Options
		if(menuOption == 1) {
			changeColor(red);
		} else {
			changeColor(black);
		}
        drawText(50, 100, "Options");

		//Exit
		if(menuOption == 2) {
			changeColor(red);
		} else {
			changeColor(black);
		}
        drawText(50, 150, "Exit");
	}
		public void paintOptions() {
		//250x250
		if(menuOption == 0) {
			changeColor(red);
		} else {
			changeColor(black);
		}
        drawText(50, 50, "500x500");

		//500x500
		if(menuOption == 1) {
			changeColor(red);
		} else {
			changeColor(black);
		}
        drawText(50, 100, "750x750");

		//Exit
		if(menuOption == 2) {
			changeColor(red);
		} else {
			changeColor(black);
		}
        drawText(50, 150, "Back");
	}


	// This gets called any time the Operating System
	// tells the program to paint itself
	public void paintComponent() {
		// Clear the background to black
		changeBackgroundColor(white);
		clearBackground(width(), height());

		if(state == GameState.Menu) {
			paintMenu();
		} else if(state == GameState.Options) {
			paintOptions();
		} else if(state == GameState.Playing) {
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
	}
	}

	// Called whenever a key is pressed
	public void keyPressed(KeyEvent e) {
		if(state == GameState.Menu) {
    		keyPressedMenu(e);
    	} else if(state == GameState.Options) {
    		keyPressedOptions(e);
    	} else if(state == GameState.Playing) {
    		keyPressedGame(e);
    	}
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
	 public void keyPressedMenu(KeyEvent e) {
		//Up Arrow
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(menuOption > 0) {
				menuOption--;
			}
		}
		//Down Arrow
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(menuOption < 2) {
				menuOption++;
			}
		}
		//Enter Key
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(menuOption == 0) {
				//Start Game
				state = GameState.Playing;
			} else if(menuOption == 1) {
				//Option Menu
				state = GameState.Options;
				menuOption = 0;
			} else {
				//Exit
				System.exit(0);
			}
		}
	}

	//Called whenever a key is pressed in the options menu
    public void keyPressedOptions(KeyEvent e) {
    	//Up Arrow
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(menuOption > 0) {
				menuOption--;
			}
		}
		//Down Arrow
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(menuOption < 2) {
				menuOption++;
			}
		}
		//Enter Key
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(menuOption == 0) {
				//500x500
				setWindowSize(500, 500);
			} else if(menuOption == 1) {
				//750x750
				setWindowSize(750, 750);
			} else {
				//Back
				state = GameState.Menu;
				menuOption = 0;
			}
		}
    }
	 
	public void keyPressedGame(KeyEvent e) {
		//In the game
        if(e.getKeyCode() == KeyEvent.VK_Q ||
                e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			state = GameState.Menu;
		}
	}
}