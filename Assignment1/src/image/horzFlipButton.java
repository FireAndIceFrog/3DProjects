package image;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//horizontal flip button by mathew Lawrence 17354272
//along the lines of it's parent, all this child does is implement the mouse listener classes
@SuppressWarnings("serial")
public class horzFlipButton extends myButton implements MouseListener{

	public horzFlipButton(int x, int y, int width, int height, ImagePanel P) {
		super(x, y, width, height, P);
		// This works quite well - it has the same functionality as the original JBUtton
		text.setText("Horizontal Flip");
		text.addMouseListener(this);
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//p is for panel
			if(p!=null)
				//call the panel's hflip function if its not null
				p.HFlipImage();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.changeColor(Color.white, Color.DARK_GRAY);
		this.setColor();
			
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.changeColor(Color.black, Color.LIGHT_GRAY);
		this.setColor();
	}

}
