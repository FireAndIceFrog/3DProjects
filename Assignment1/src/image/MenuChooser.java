package image;

import java.io.File;
import javax.swing.JFileChooser;

//menu chooser by Mathew Lawrence 17354272
public class MenuChooser {
		public File myFile;
	// Here's how we use Java to browse our filesystem and
    // select a file
		public MenuChooser(Menu menu){
			
			
			
			
	        JFileChooser chooser = new JFileChooser("./");
	        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	
	    // Respond to button click
	        int retVal = chooser.showOpenDialog(menu);
	        if(retVal == JFileChooser.CANCEL_OPTION) {
	        	chooser.setVisible(false);
	        	chooser = null;
	        	myFile = null;
	        	System.out.println("Cancelled!");
	        }
	        if (retVal == JFileChooser.APPROVE_OPTION) {
			
			// Respond to use selecting file
			myFile = chooser.getSelectedFile();
			System.out.println("Choosing file " +  myFile.getAbsoluteFile());
	        }
		}
		public File getDir() {
			return myFile;
		}

}
