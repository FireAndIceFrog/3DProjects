package image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


@SuppressWarnings("serial")
public class Menu extends JMenuBar implements ActionListener{
	// Menu items in the File
    public File myFile;
	protected JMenuItem open;
	protected JMenuItem quit;
	protected JMenu menu;
	protected ImagePanel image;
	
	//menu structure by Mathew Lawrence 17354272
    public void actionPerformed(ActionEvent event) {

		// Respond to each of the action events generated by the menu
		JComponent source = (JComponent)event.getSource();
		//basic menu structure
		if(source == open) {
			myFile = new MenuChooser(this).getDir();
			if(myFile == null) {
				System.out.println("No File FOund");
			}  else if(image!=null) {
				image.getImage(myFile);
			}else {
				System.out.println("No Image Found!");
			}
		}else if(source == quit) {
			System.out.println("Quitting...");
			System.exit(1);
	}
	

    }
    public Menu(ImagePanel image){
    	// Add a file menu with some menu items
    	this.image = image;
    	menu = new JMenu("File");
    	open = new JMenuItem("Open");
    	quit = new JMenuItem("Quit");
    	menu.add(open);
    	menu.add(quit);
    	this.add(menu);
    	open.addActionListener(this);
    	quit.addActionListener(this);
    }


    

}
