/*
	File Name:	 Display.java
	Name:			 Shawn Wang
	Class:		 ICS4U1
	Date:			 June 15, 2017
	Description: Blank template for GUI panels. Draws background black. Allows for implementation or direct usage of this class.
*/

package Game.GUI;

// importing needed classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Display extends JPanel {

   // colors:
   private static boolean switchedLighting = false;   // if the colors are inversed (not implemented)
   protected static Color backgroundColor = Color.BLACK; // background color ()
   protected static Color textColor = Color.WHITE;
   
   // text distances: 
   protected final static int TEXTSPACING = 25;
   protected final static int LINESPACING = 15;
   
   // sizes:
   private int width;
   private int height;

   //constructor
   public Display(int width, int height) {
      this.width = width;
      this.height = height;
      setSize(width, height);
   }

   // returns state of inversed colors
   public static boolean getLighting() {
      return switchedLighting;
   }

   // inverses colors
   public static void lights() {
      if (backgroundColor.equals(Color.BLACK)) {
         backgroundColor = Color.DARK_GRAY;
      } 
      else {
         backgroundColor = Color.BLACK;
      }
      switchedLighting = true;
   }

   // paints the components of a display: background and contents
   // calls paintBackground and paintContents method
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      paintBackground(g);
      paintContents(g);
   }
   
   
   // paints the background black
   public void paintBackground(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.setColor(backgroundColor);
      g2d.fillRect(0, 0, width, height);
   }

   // paints the contents of a display
   // overwritten by implementaions if needed
   public void paintContents(Graphics g) {
   
   }

}
