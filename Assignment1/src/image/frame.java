package image;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
//Main File by Mathew Lawrence 17354272
//The frame is designed to have all the features accessible on startup.
@SuppressWarnings("serial")
public class frame extends JFrame implements MouseMotionListener{
	
	public static void main(String[] args) {
		//entry point, everything will be set up from the frame constructor
		new frame();
		
		
	}
	private int[] colors = null;
	private int boxHeight = 200;
	//set up basic height and width - the assignment said nothing about resizing so I didnt implement it.
	public static int DEFAULT_HEIGHT = 500;
	public static int DEFAULT_WIDTH = 800;
	//Originally this was supposed to just show the colors of the image, however the assignment specified that the colors be displayed in RGB mode, so i did that as well
	public colorBox mouseColorBox = null;
	//actual image, everything affecting the image will be in this class
	public ImagePanel imagePanel = null;
	//all these buttons are modified JPanels - I thought it would be fun to use the JPanel as an excersize - they look good and work just the same as a button.
	public InvertButton invert = null;
	public horzFlipButton hFlip = null;
	public VertFlipButton vFlip = null;
	//menu panel with drop down, with item picker
	public Menu menu = null;	
	
	public frame() {
		super("Assignment 1 By Mathew Lawrence 17354272");
		//set up the frame
		setSize (DEFAULT_WIDTH,DEFAULT_HEIGHT);
		setResizable(true);
		//this will be used to add to the frame
		Container content = this.getContentPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//imagePanel(x,y) - this will be created at the co ordinates at 0, 0.
		imagePanel = new ImagePanel(0,0);
		//add the menu panel
		menu = new Menu(imagePanel);
		this.setJMenuBar(menu);
		
		//all buttons use the image panel's functions (invert, flip) and as such require them as a parameter
		invert = new InvertButton(300,20,100,30, imagePanel);
		hFlip = new horzFlipButton(300,50,100,30, imagePanel);
		vFlip = new VertFlipButton(300,80,100,30, imagePanel);

		
		content.add(vFlip);
		content.add(invert);
		content.add(hFlip);
		

		
        // Instantiate our image panel class and add this panel to the
        // content pane of the JFrame
        
        

        // Read the image into the panel. The image file must be at
        // the top level of your project directory
     //   imagePanel.getImage(null);

		

        mouseColorBox = new colorBox(600,300);
        mouseColorBox.setLocation(0,boxHeight);

        content.add(mouseColorBox);
        content.add(imagePanel);
        
        mouseColorBox.setVisible(true);
        imagePanel.setVisible(true);
		invert.setVisible(true);
		setVisible(true);
		imagePanel.addMouseMotionListener(this);
		
		

		
	}




	public void mouseMoved(MouseEvent e) {
		if(mouseColorBox!=null) {
			//System.out.println(e.getX()+" "+e.getY());
			mouseColorBox.setMouseXY(e.getX(),e.getY());
			
			if (imagePanel != null) {
				boolean check = imagePanel.checkBounds(e.getX(),e.getY());
				if(check) {
					colors = imagePanel.getColor(e.getX(),e.getY());
					if(colors!=null)
						mouseColorBox.setBackground(colors);
				}
			}
		}
		
	}



	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}
