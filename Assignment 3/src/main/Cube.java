package main;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;

public class Cube {
	Plane [] sides;

	Point4 pCentre; 
	Point4 pAngles;
	Point4 pScale;
    final  static double a090 = Math.toRadians(90);
    final  static double a180 = Math.toRadians(180);
    final  static double a270 = Math.toRadians(270);
	public Cube(Point4 pCentre, Point4 pAngles, Point4 pScale) {
		this.pCentre = pCentre;
		this.pAngles = pAngles;
		this.pScale =  pScale;
		
	}
	
	Point4[] getInitialAngle() {
		Point4 initialAngle [] = new Point4[6];
		initialAngle[0] = new Point4(0,0,0); //front
		initialAngle[1] = new Point4(0,Math.PI,0); // back
		initialAngle[2] = new Point4(Math.PI/2.0,0,0);  //left
		initialAngle[3] = new Point4(-Math.PI/2.0,0,0); //right
		initialAngle[4] = new Point4(0,Math.PI/2.0,0); // bottom
		initialAngle[5] = new Point4(0,-Math.PI/2.0,0); // top
		return initialAngle;
	}
	Point4 [] getInitialCenter() {
		Point4 InitialCenter [] = new Point4[6];
		InitialCenter[0] = new Point4(0,0,0.5*pScale.z);
		InitialCenter[1] = new Point4(0,0,-0.5*pScale.z);
		InitialCenter[2] = new Point4(0,0.5*pScale.y,0);
		InitialCenter[3] = new Point4(0,-0.5*pScale.y,0);
		InitialCenter[4] = new Point4(0.5*pScale.x,0,0);
		InitialCenter[5] = new Point4(-0.5*pScale.x,0,0);
		return InitialCenter;
	}
	
	public void setProperty(String s) {
		if(sides==null)
			return;
		for(int i = 0; i < 6; ++i ) {
			sides[i].setProperty(s);
		
		}
	}
	public void setProperty(Color c) {
		if(sides!=null)
			return;
		for(int i = 0; i < 6; ++i ) {
			sides[i].setProperty(c);
		
		}
	}
	Plane [] getPlanes() {
		return sides;
	}
	 
	void setPlanes(){
		Point4 [] initialAngle  = getInitialAngle();
		Point4 [] InitialCenter = getInitialCenter();
		Point4 tempAngle = pAngles;
		Point4 tempCentre = pCentre;
		Point4 tempScale;
		
		sides = new Plane[6];
		for(int i = 0; i < 6; ++i) {
			
			
			tempAngle = initialAngle[i];
			tempCentre = InitialCenter[i];
			Matrix4 rotX= Matrix4.createRotationX(pAngles.x);
			Matrix4 rotY = Matrix4.createRotationY(pAngles.y);
			Matrix4 rotZ = Matrix4.createRotationZ(pAngles.z);
			Matrix4 disp = Matrix4.createDisplacement(pCentre.x,pCentre.y,pCentre.z);	
			
			
			Matrix4 tLW = Matrix4.localToWorld(tempCentre, tempAngle, pScale);
			
			tLW = tLW.times(disp).times((rotZ.times(rotY)).times(rotX));
	
					
	        Matrix4 tWL = Matrix4.worldToLocal(tempCentre, tempAngle, pScale);
	        tWL = tWL.times(disp).times((rotZ.times(rotY)).times(rotX));
	        
	       
	        
	        sides[i] = new Plane(tLW, tWL);
	        
	        //front back left right top bottom
	        
	        
		}
	}
	
	
	
	
	
	
	

}
