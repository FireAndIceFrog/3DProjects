package main;
import java.awt.Color;
public class RandColor {
	static int i = 0;
	static Color c = Color.black;
	public static void setColor(Color col) {
		c = col;
	}
	
	public static Color getColor() {
		i = (i+1)%5;
		if(i == 0)
			return Color.red;
		if(i==1)
			return Color.blue;
		if(i==2)
			return Color.yellow;
		if(i==3)
			return Color.gray;
		if(i==4)
			return Color.black;
		if(i==5)
			return Color.green;
		return	Color.CYAN;
	}
	public static Color getStandardColor() {
		return c;
	}
}
