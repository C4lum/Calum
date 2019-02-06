import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class version3 extends GameEngine {
	// Main Function
	public static void main(String args[]) {
		// Warning: Only call createGame in this function
		// Create a new rocket_Landing
		createGame(new version3());
	}
	boolean paused = false;
	enum GameState {Menu, Playing, Options, Crashed, Landed};
	GameState state = GameState.Menu;

	int menuOption = 0;
	
	boolean left, right, up;
	boolean gameOver;
	boolean landed;
	boolean start = false;
	
	
	
	double spaceshipPositionX, spaceshipPositionY;
	double spaceshipVelocityX, spaceshipVelocityY;

	double spaceshipAngle;
	double angle;
	double landingAngle = 30;
	
	double groundlevel=400;
	double groundDiff;
	int groundarraysize=20;
	double[] groundarray = new double [20];
	double landDistance = (500/(groundarraysize-1));
	int landingPointA;
	int landingPointB;
	int landingPointC;
	
	double gravity = 4;
	double XlandingSpeed=80, YlandingSpeed=80;
	double fuel;
	double level = 0;
	
	double wind = 0;
	double windReading;
	double TLX,TRX,BLX,BRX;
	double TLY,TRY,BLY,BRY;
	double groundX,groundY,groundZ,groundA;
	
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
		
		if(spaceshipPositionX > 500){
			spaceshipPositionX = 0;
		}
		if(spaceshipPositionX < 0){
			spaceshipPositionX = 500;
		}
		
		
	}
	public void updateGame(double dt) {
		
		
		for(int i = 0; i < groundarraysize; i++){
			
			if(BLX > (landDistance*i) && BLX < (landDistance*(i+1))){
				groundX = BLX - landDistance*i;
				groundY = ((groundarray[i]-groundarray[(i+1)%20])/landDistance)*groundX;
					groundA = groundarray[i];
					groundY = groundY*-1;
					groundZ = groundlevel - groundY - groundA;
				if(BLY >= groundZ && i >= landingPointA && i <= landingPointC ){
					if(spaceshipVelocityX > XlandingSpeed || spaceshipVelocityX < XlandingSpeed*-1 ){
						//gameOver = true;
						state = GameState.Crashed;
					}
					if(spaceshipVelocityY > YlandingSpeed){
						//gameOver = true;
						state = GameState.Crashed;
					}
					spaceshipPositionY = groundlevel-groundarray[i]-20;
					spaceshipVelocityY = 0;
					spaceshipVelocityX = spaceshipVelocityX * .5;
					if(spaceshipVelocityX<.5){
						spaceshipVelocityX=0;
					}
					if(angle>landingAngle){
						//gameOver = true;
						state = GameState.Crashed;
					}
					//landed = true;
					if(state != GameState.Crashed) {
						state = GameState.Landed;
					}

				}else if(BLY >= groundZ){
					//gameOver = true;
					state = GameState.Crashed;
				}
			}
			if(BRX > (landDistance*i) && BRX < (landDistance*(i+1))){
				groundX = BRX - landDistance*i;
				groundY = ((groundarray[i]-groundarray[(i+1)%20])/landDistance)*groundX;
					groundA = groundarray[i];
					groundY = groundY*-1;
					groundZ = groundlevel - groundY - groundA;
				if(BRY >= groundZ && i >= landingPointA && i <= landingPointC ){
					if(spaceshipVelocityX > XlandingSpeed || spaceshipVelocityX < XlandingSpeed*-1 ){
						//gameOver = true;
						state = GameState.Crashed;
					}
					if(spaceshipVelocityY > YlandingSpeed){
						//gameOver = true;
						state = GameState.Crashed;
					}
					spaceshipPositionY = groundlevel-groundarray[i]-20;
					spaceshipVelocityY = 0;
					spaceshipVelocityX = spaceshipVelocityX * .5;
					if(spaceshipVelocityX<.5){
						spaceshipVelocityX=0;
					}
					if(angle>landingAngle){
						//gameOver = true;
						state = GameState.Crashed;
					}
					//landed = true;
					//state = GameState.Landed;
					if(state != GameState.Crashed) {
						state = GameState.Landed;
					}
				}else if(BRY >= groundZ){
					//gameOver = true;
					state = GameState.Crashed;
				}
			}
			if(TLX > (landDistance*i) && TLX < (landDistance*(i+1))){
				groundX = TLX - landDistance*i;
				groundY = ((groundarray[i]-groundarray[(i+1)%20])/landDistance)*groundX;
					groundA = groundarray[i];
					groundY = groundY*-1;
					groundZ = groundlevel - groundY - groundA;
				if(TLY >= groundZ){
					//gameOver = true;
					state = GameState.Crashed;
				}
			}
			if(TRX > (landDistance*i) && TRX < (landDistance*(i+1))){
				groundX = TRX - landDistance*i;
				groundY = ((groundarray[i]-groundarray[(i+1)%20])/landDistance)*groundX;
					groundA = groundarray[i];
					groundY = groundY*-1;
					groundZ = groundlevel - groundY - groundA;
				if(TRY >= groundZ){
					//gameOver = true;
					state = GameState.Crashed;
				}
			}
			
		}
				
		if(spaceshipPositionY + 20 >= groundlevel){
			
			if(spaceshipVelocityX > XlandingSpeed || spaceshipVelocityX < XlandingSpeed*-1 ){
				//gameOver = true;
			}
			if(spaceshipVelocityY > YlandingSpeed){
				//gameOver = true;
			}
			spaceshipPositionY = groundlevel-20;
			spaceshipVelocityY = 0;
			spaceshipVelocityX = spaceshipVelocityX * .5;
			if(spaceshipVelocityX<.5){
				spaceshipVelocityX=0;
			}
			if(angle>landingAngle){
				//gameOver = true;
				state = GameState.Crashed;
			}
		}
		//stops the ship from exiting the screen from the top
		if(spaceshipPositionY<0){
			spaceshipPositionY = 0;
			spaceshipVelocityY = 0;
		}
		spaceshipVelocityY+=gravity;
		
		spaceshipVelocityX+=wind;
		
		
		
		
	}
	public void drawSpaceship() {
		// Set the color to white
		changeColor(white);

		// Save the current transform
		saveCurrentTransform();

		// ranslate to the position of the asteroid
		translate(spaceshipPositionX, spaceshipPositionY);

		// Rotate the drawing context around the angle of the asteroid
		rotate(spaceshipAngle);
		
		TLX = spaceshipPositionX - 15 * cos(spaceshipAngle) + 20 * sin(spaceshipAngle);
		TRX = spaceshipPositionX + 15 * cos(spaceshipAngle) + 20 * sin(spaceshipAngle);
		BLX = spaceshipPositionX - 15 * cos(spaceshipAngle) - 20 * sin(spaceshipAngle);
		BRX = spaceshipPositionX + 15 * cos(spaceshipAngle) - 20 * sin(spaceshipAngle);
		
		TLY = spaceshipPositionY - 15 * sin(spaceshipAngle) - 20 * cos(spaceshipAngle);
		TRY = spaceshipPositionY + 15 * sin(spaceshipAngle) - 20 * cos(spaceshipAngle);
		BLY = spaceshipPositionY - 15 * sin(spaceshipAngle) + 20 * cos(spaceshipAngle);
		BRY = spaceshipPositionY + 15 * sin(spaceshipAngle) + 20 * cos(spaceshipAngle);

		
		
		// Draw the actual spaceship
		
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
		
	    changeColor(white);
		
		drawLine(0,  groundlevel,  500,  groundlevel);		
		
		
		for(int i = 0 ; i < groundarraysize-1; i++){
			if(i == landingPointA || i == landingPointB  ){
				changeColor(green);
				drawLine(landDistance*i,groundlevel - groundarray[i], landDistance*(i+1), groundlevel-groundarray[i+1],4);
				changeColor(gray);
				if(level == 1){
					changeColor(red);
				}else if(level == 2){
					changeColor(brown);
				}
				int polyX1,polyX2,polyX3,polyX4;
				int polyY1,polyY2,polyY3,polyY4;
				polyX1=(int)landDistance*i;
				polyX2=(int)landDistance*(i+1);
				polyX3=(int)landDistance*(i+1);
				polyX4=(int)landDistance*i;
				
				
				polyY1=(int)(groundlevel - groundarray[i]);
				polyY2=(int)(groundlevel-groundarray[i+1]);
				polyY3=(int)groundlevel;
				polyY4=(int)groundlevel;
				
				int x[] = {polyX1,polyX2,polyX3,polyX4};
		      int y[] = {polyY1,polyY2,polyY3,polyY4};
				int npoint = x.length;
				drawSolidPolygon(x,y,npoint);
			}else{
				changeColor(gray);
				if(level == 1){
					changeColor(red);
				}else if(level == 2){
					changeColor(brown);
				}
				drawLine(landDistance*i,groundlevel - groundarray[i], landDistance*(i+1), groundlevel-groundarray[i+1]);
				int polyX1,polyX2,polyX3,polyX4;
				int polyY1,polyY2,polyY3,polyY4;
				polyX1=(int)landDistance*i;
				polyX2=(int)landDistance*(i+1);
				polyX3=(int)landDistance*(i+1);
				polyX4=(int)landDistance*i;
				
				
				polyY1=(int)(groundlevel - groundarray[i]);
				polyY2=(int)(groundlevel-groundarray[i+1]);
				polyY3=(int)groundlevel;
				polyY4=(int)groundlevel;
				
				int x[] = {polyX1,polyX2,polyX3,polyX4};
		      int y[] = {polyY1,polyY2,polyY3,polyY4};
				int npoint = x.length;
				drawSolidPolygon(x,y,npoint);
			}
		}
	}
	
	public void paintMenu() {
		drawBoldText(25, 80, ("Rocket Lander"), "DialogInput", 60);
		//Play
		if(menuOption == 0) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 150, "Play");

		//Options
		if(menuOption == 1) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 200, "Options");

		//Exit
		if(menuOption == 2) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 250, "Exit");
	}

	//Function to paint the Options menu
	public void paintOptions() {
		//250x250
		if(menuOption == 0) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 50, "Moon - level 1");

		//500x500
		if(menuOption == 1) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 100, "Mars - level 2");
		
		if(menuOption == 2) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 150, "Earth - level 3");

		//Exit
		if(menuOption == 3) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 200, "Back");
	}
	
	public void paintCrashed() {
		drawText(50, 50, "GameOver");
		if(menuOption == 0) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 150, "Restart");

		//500x500
		if(menuOption == 1) {
			changeColor(red);
		} else {
			changeColor(white);
		}
		drawText(50, 200, "Back");
	}
	
	public void paintLanded() {
		
		
		
		if(menuOption == 0) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 150, "Next level");
		//250x250
		if(menuOption == 1) {
			changeColor(red);
		} else {
			changeColor(white);
		}
        drawText(50, 200, "Restart");

		//500x500
		if(menuOption == 2) {
			changeColor(red);
		} else {
			changeColor(white);
		}
		drawText(50, 250, "Back");
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
		spaceshipPositionX = 250; spaceshipPositionY = 50;
		spaceshipVelocityX = 0;   spaceshipVelocityY = 0;
		spaceshipAngle = 0;
		
		fuel=100;
		gravity = 4;
		if (level == 1){
			gravity = 6;
		}else if(level == 2){
			gravity = 8;
		}
		
			
		
		landingPointA = rand(20);
		if(landingPointA > 10){
			landingPointA = landingPointA - 2;
			landingPointB = landingPointA + 1;
			landingPointC = landingPointA + 2;
		}else{
			landingPointB = landingPointA + 1;
			landingPointC = landingPointA + 2;
		}	
		groundDiff = 200/((level-5)*-1);
		for(int i = 0 ; i < groundarraysize; i++){
			groundarray[i] = rand(groundDiff);
		}
		
		if(groundarray[landingPointB] != groundarray[landingPointA]){
			groundarray[landingPointB] = groundarray[landingPointA];
			groundarray[landingPointC] = groundarray[landingPointA];
		}
	}
	public void init1() {
		// Initialise game boolean
		gameOver = false;

		// Initialise key booleans
		left  = false;
		right = false;
		up    = false;

		// Initialise Spaceship
		spaceshipPositionX = 250; spaceshipPositionY = 50;
		spaceshipVelocityX = 0;   spaceshipVelocityY = 0;
		spaceshipAngle = 0;
		
		
		fuel=100;
		
		gravity = 4;
		if (level == 1){
			gravity = 6;
		}else if(level == 2){
			gravity = 8;
		}
		
		wind = 0;
		if (level == 1){
			wind = 2;
		}else if(level == 2){
			wind = 4;
		}
		int c = rand(10);
		if(c>5){
			wind = wind*-1;
		}
		
	}

	// Updates the display
	public void update(double dt) {
		//if(start){
		
		//if(state == GameState.Playing) {
		//	System.out.println("Playing");
		//}	
		
		//System.out.println("state: " + state);
		
			
		if(state == GameState.Playing && !paused) {
				// If the game is over
			//~ if(gameOver == true) {
				//~ // Don't try to update anything.
				//~ state = GameState.Crashed;
				//~ return;
			//~ }
			
			//~ if(landed == true) {
				//~ // Don't try to update anything.
				//~ state = GameState.Landed;
				//~ return;
			//~ }
		

			// Update the spaceship
			updateGame(dt);
			updateSpaceship(dt);
			
		}
	//	}

		

		//-------------------------------------------------------
		// Add code to check for collisions
		//-------------------------------------------------------
	}

	// This gets called any time the Operating System
	// tells the program to paint itself
	public void paintComponent() {
		// Clear the background to black
		
			changeBackgroundColor(white);
			clearBackground(width(), height());

			if(state == GameState.Menu) {
				changeBackgroundColor(black);
				clearBackground(500, 500);
				drawGround();
				drawControlPanel();
				drawSpaceship();
				paintMenu();
			} else if(state == GameState.Options) {
				paintOptions();
			} else if(state == GameState.Playing) {
				changeBackgroundColor(black);
				clearBackground(500, 500);
				drawGround();
		
				drawControlPanel();
				
				// If the game is not over yet
				if(gameOver == false) {
					// Paint the Spaceship
					drawSpaceship();
					//drawLine(0,groundZ,500,groundZ);
					//drawLine(BLX,0,BLX,500);
					
				} else {
					// If the game is over
					// Display GameOver text
					changeColor(white);
					drawText(85, 250, "GAME OVER!", "Arial", 50);
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
	

    //Called whenever a key is pressed in the menu
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
			if(menuOption < 3) {
				menuOption++;
			}
		}
		//Enter Key
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(menuOption == 0) {
				//moon
				level = 0;
				state = GameState.Playing;
			} else if(menuOption == 1) {
				//mars
				level = 1;
				state = GameState.Playing;
			}else if(menuOption == 2) {
				//earth
				level = 2;
				state = GameState.Playing;
			} else {
				//Back
				state = GameState.Menu;
				menuOption = 0;
				
			}
		}
	}
	
	

	//Called whenever a key is pressed in the game
    public void keyPressedGame(KeyEvent e) {
		//In the game
		if(e.getKeyCode() == KeyEvent.VK_Q ||
				e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			state = GameState.Menu;
		}
		if(e.getKeyCode() == KeyEvent.VK_P) {
			paused = !paused;
		}
    }
}