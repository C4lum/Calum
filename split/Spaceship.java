//public interface IUpdateable {
	//public void update(double dt, boolean up, boolean left, boolean right);
//}

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Spaceship {//extends GameEngine /*implements IUpdateable*/ {
	private double mPositionX, mPositionY;
	private double mVelocityX, mVelocityY;
	private double mAngle;
	private double mFuel;
	private boolean mBoost;
	private GameEngine mEngine;
	
	public Spaceship(GameEngine engine) {
		mPositionX = 250;
		mPositionY = 50;
		mVelocityX = 0;
		mVelocityY = 0;
		mAngle = 0;
		mFuel = 100;
		mBoost = true;
		mEngine = engine;
	}
	public boolean Boost() {
		return mBoost;
	}
	public void Boost(boolean value) {
		mBoost = value;
	}
	public double PositionX() {
		return mPositionX;
	}
	public void PositionX(double value) {
		mPositionX = value;
	}
	public double PositionY() {
		return mPositionY;
	}
	public void PositionY(double value) {
		mPositionY = value;
	}
	public double VelocityX() {
		return mPositionX;
	}
	public void VelocityX(double value) {
		mPositionX = value;
	}
	public double VelocityY() {
		return mPositionY;
	}
	public void VelocityY(double value) {
		mPositionY = value;
	}
	public double Angle() {
		return mAngle;
	}
	public void Angle(double value) {
		mAngle = value;
	}
	public double Fuel() {
		return mFuel;
	}
	public void Fuel(double value) {
		mFuel = value;
	}
	
	
	
	public double TLX() {
		return mPositionX - 15 * mEngine.cos(mAngle) + 20 * mEngine.sin(mAngle);
	}
	public double TRX() {
		return mPositionX + 15 * mEngine.cos(mAngle) + 20 * mEngine.sin(mAngle);
	}
	public double BLX() {
		return mPositionX - 15 * mEngine.cos(mAngle) - 20 * mEngine.sin(mAngle);
	}
	public double BRX() {
		return mPositionX + 15 * mEngine.cos(mAngle) - 20 * mEngine.sin(mAngle);
	}
	
	public double TLY() {
		return mPositionX - 15 * mEngine.sin(mAngle) - 20 * mEngine.cos(mAngle);
	}
	public double TRY() {
		return mPositionX + 15 * mEngine.sin(mAngle) - 20 * mEngine.cos(mAngle);
	}
	public double BLY() {
		return mPositionX - 15 * mEngine.sin(mAngle) + 20 * mEngine.cos(mAngle);
	}
	public double BRY() {
		return mPositionX + 15 * mEngine.sin(mAngle) + 20 * mEngine.cos(mAngle);
	}
	
	
	public void update(double dt, boolean up, boolean left, boolean right) {
		// updateSpaceship
		
		if(Fuel()<=0){
				Fuel(0);
				Boost(false);
				/*up=false;
				left=false;
				right=false;*/
			}
		if(up){
			Fuel(Fuel()-.5);
		}
		if(left){
			Fuel(Fuel()-.2);
		}
		if(right){
			Fuel(Fuel()-.2);
		}
		if(up == true) {
			// Increase the velocity of the spaceship
			// as determined by the angle
			VelocityX(VelocityX() +  mEngine.sin(Angle()) * 250 * dt);
			VelocityY(VelocityY() + -mEngine.cos(Angle()) * 250 * dt);
		}

		// If the user is holding down the left arrow key
		// Make the spaceship rotate anti-clockwise
		if(left == true) {
			Angle(Angle() - 90 * dt);
		}

		// If the user is holding down the right arrow key
		// Make the spaceship rotate clockwise
		if(right == true) {
			Angle(Angle() + 90 * dt);
		}

		// Make the spaceship move forward
		PositionX(PositionX() + VelocityX() * dt);
		PositionY(PositionY() + VelocityY() * dt);
		
		if(PositionX() > 500){
			PositionX(0);
		}
		if(PositionX() < 0){
			PositionX(500);
		}
		
	}
	
	
	/*public void paint(Graphics g, boolean up, boolean left, boolean right) {
		mEngine.changeColor(mEngine.white);
		mEngine.saveCurrentTransform();
		mEngine.translate(mPositionX, mPositionY);
		mEngine.rotate(mAngle);
		
		
		if(!up){
			mEngine.drawImage(Rocket, -15, -20, 30, 40);
		}else{
			mEngine.drawImage(RocketBlast, -15, -20, 30, 60);
		}
		if(left){
			mEngine.changeColor(mEngine.red);
			mEngine.drawLine(10,15,12,20);
			mEngine.drawLine(15,15,12,20);
			mEngine.changeColor(mEngine.yellow);
			mEngine.drawLine(10,15,12,30);
			mEngine.drawLine(15,15,12,30);
			
		}
		if(right){
			mEngine.changeColor(mEngine.red);
			mEngine.drawLine(-10,15,-12,20);
			mEngine.drawLine(-15,15,-12,20);
			mEngine.changeColor(mEngine.yellow);
			mEngine.drawLine(-10,15,-12,30);
			mEngine.drawLine(-15,15,-12,30);
		}
		
		
		// Restore last transform to undo the rotate and translate transforms
		mEngine.restoreLastTransform();
		// paintSpaceship
	}*/
};