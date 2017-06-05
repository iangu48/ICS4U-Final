import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.Object.*;

public class Prototype
{
   static JFrame window;
   static JFrame gameWindow;
   static JFrame exploreWindow;
   static Resource[] resources = {new Resource("Wood"), new Resource("Food"), new Resource("Iron")};
   static Item[] inventory = {new Item("A", 1), new Item("B", 2), new Item("C", 3)};
   static Messages gameMessages = new Messages();
   static Messages exploreMessages = new Messages();
   static Color backgroundColor = Color.BLACK;
   static Color textColor = Color.WHITE;
   final static int TEXTSPACING = 25;
   final static int LINESPACING = 15;
   static Player player = new Player ("Shawn", 50);

   private static class Display extends JPanel
   {
      int width;
      int height;
      
      public Display (int width, int height)
      {
         this.width = width;
         this.height = height;
         setSize(width,height);
      }
      
      public void paintComponent(Graphics g) 
      {
         super.paintComponent(g);
         paintBackground(g);
         paintContents(g);
      }
      
      public void paintBackground (Graphics g)
      {
         Graphics2D g2d = (Graphics2D) g;
         g2d.setColor(backgroundColor);
         g2d.fillRect(0, 0, width, height);
      }
      
      public void paintContents (Graphics g)
      {
      
      }
      
   }
   
   private static class StartScreenDisplay extends Display
   {
      public StartScreenDisplay (int width, int height)
      {
         super (width, height);
      }
      
      public void paintContents (Graphics g)
      {
         g.setColor(textColor);
         g.drawString("Welcome to the parody of the game \"A dark room\"", 15, 20);
         g.drawString("Made by: Shawn Wang, Michael Chang, Raghav Srinivasan, Tong Li Han, Ian Gu", 15, 40);
      }
      
   }
	
   private static class GameDisplay extends Display
   {
      private static final int MESSAGEXPOSITION = 20;
      private static final int MESSAGEINITIALYPOSITION = 20;
      private static final int RESOURCEXPOSITION = 470;
      private static final int RESOURCEINITIALYPOSITION = 20;
      
      public GameDisplay (int width, int height)
      {
         super (width, height);
      }
      
      public void paintContents (Graphics g)
      {
         g.setColor(textColor);
         paintMessages(g);
         paintResources(g);
      }
   
      public void paintMessages(Graphics g)
      {	
         String[] text = new String[1];
         int xPosition = MESSAGEXPOSITION;
         int yPosition = MESSAGEINITIALYPOSITION;
         for (int i = 0; (text = gameMessages.getText(i)) != null; i++)
         {
            for (int j = 0; j < text.length && text[j] != null; j++)
            {
               g.drawString(text[j], xPosition, yPosition);
               yPosition += LINESPACING;
            }
            yPosition += TEXTSPACING;
         
         }
      }
      
      public void paintResources(Graphics g)
      {	
         g.setColor(textColor);
         int xPosition = RESOURCEXPOSITION;
         int yPosition = RESOURCEINITIALYPOSITION;
         for (int i = 0; i < resources.length && resources[i] != null; i++)
         {
            g.drawString(resources[i].getName() + ": " + resources[i].getAmount(), xPosition, yPosition);
            yPosition += LINESPACING;
         }
      }
   } 

   private static class ExploreDisplay extends Display
   {
      private static final int TEXTXPOSITION = 20;
      private static final int TEXTINITIALYPOSITION = 50;
      private static final int MESSAGEXPOSITION = 220;
      private static final int MESSAGEINITIALYPOSITION = 50;
      private static final int INVENTORYXPOSITION = 500;
      private static final int INVENTORYINITIALYPOSITION = 50;
      
      public ExploreDisplay (int width, int length)
      {
         super(width, length);
      }
      
      public void paintContents(Graphics g)
      {
         g.setColor(textColor);
         paintPlayer(g);
         paintMessages(g);
         paintInventory(g);
      }
      
      public void paintPlayer(Graphics g)
      {
         int xPosition = TEXTXPOSITION;
         int yPosition = TEXTINITIALYPOSITION;
         g.drawString("Exploration", xPosition, yPosition);
         yPosition += LINESPACING;
         g.drawString(player.getName(),  xPosition, yPosition);
         yPosition += LINESPACING;
         g.drawString("Health: " + player.getHealth() + " / " + player.getMaxHealth(), xPosition, yPosition);
         yPosition += LINESPACING;
         g.drawString("Water: " + player.getWater() + " / " + player.getMaxWater(), xPosition, yPosition);
         yPosition += LINESPACING;
         g.drawString("Food: " + player.getFood(), xPosition, yPosition);
      }
   	
      public void paintMessages(Graphics g)
      {	
         String[] text;
         int xPosition = MESSAGEXPOSITION;
         int yPosition = MESSAGEINITIALYPOSITION;
         
         for (int i = 0; (text = exploreMessages.getText(i)) != null; i++)
         {
            for (int j = 0; j < text.length && text[j] != null; j++)
            {
               g.drawString(text[j], xPosition, yPosition);
               yPosition += LINESPACING;
            }
            yPosition += TEXTSPACING;
         }
         
      }
      
      public void paintInventory(Graphics g)
      {
         g.setColor(textColor);
         int xPosition = INVENTORYXPOSITION;
         int yPosition = INVENTORYINITIALYPOSITION;
         int i = 0;
         Item temp;
         while ((temp = player.displayInventory(i)) != null)
         {
            g.drawString(temp.getName() + ": " + temp.getAmount(), xPosition, yPosition);
            i++;
            yPosition += LINESPACING;
         }
      }
      
   }
   
   private static class ButtonHandler implements ActionListener 
   {
    
      public void actionPerformed(ActionEvent e) 
      {
         String action = e.getActionCommand();
         if (action.equals("Start game"))
            startGame();
            //System.exit(0);
         else if (action.equals("Wood"))
         {
            String[] text = new String[1];
            text[0] = "Received 10 wood.";
            gameMessages.addText(text);
            resources[0].addAmount(10);
         }
         else if (action.equals("Food"))
         {
            String[] text = new String[1];
            text[0] = "Received 10 food.";
            gameMessages.addText(text);
            resources[1].addAmount(10);
         }
         else if (action.equals("Iron"))
         {
            String[] text = new String[1];
            text[0] = "Received 10 iron.";
            gameMessages.addText(text);
            resources[2].addAmount(10);
         } 
         else if (action.equals("Expedition"))
         {
            if (resources[1].reduceAmount(10))
            {
               String[] text = new String[1];
               text[0] = "Started expedition";
               exploreWindow = new JFrame("Exploration");
               exploreWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               exploreWindow.setContentPane(new ExploreDisplay(600, 600));
               exploreWindow.setSize(600,600);
               exploreWindow.setLocation (300, 200);
               exploreMessages.addText(text);
               JButton exploreButton = new JButton("Explore");
               JButton returnButton = new JButton("Return");
               ButtonHandler handler = new ButtonHandler();
               exploreButton.addActionListener(handler);
               returnButton.addActionListener(handler);
               exploreWindow.getContentPane().setLayout(new BorderLayout());
               exploreWindow.add(exploreButton, BorderLayout.PAGE_START);
               exploreWindow.add(returnButton, BorderLayout.PAGE_END);
               gameMessages.clearTexts(); 
               gameWindow.setVisible(false);
               exploreWindow.setVisible(true);
            }
            else
            {
               String[] text = new String[1];
               text[0] = "Not enough food to start expedition";
               gameMessages.addText(text); 
            }
         }
         else if (action.equals("Explore"))
         {
            exploreMessages.addText(player.explore());
            exploreWindow.repaint();
            if (!player.isAlive())
            {
               exploreWindow.dispose();
               String[] text = new String[1];
               text[0] = "You wake up at the village.";
               gameMessages.addText(text);
               gameWindow.setVisible(true);
            }
         }
         else if (action.equals("Return"))
         {
            exploreWindow.dispose();
            String[] text = new String[1];
            text[0] = "Returned from expedition.";
            gameMessages.addText(text);
            gameWindow.setVisible(true);
         }
         gameWindow.repaint();
      }
   }
   
   public static void startGame()
   {
      window.setVisible(false);
      window.dispose();
   	 
      gameWindow = new JFrame ("La Sala");
      gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameWindow.setSize(1000, 500);
      gameWindow.setLocation(100, 200);
   	
      JPanel mainDisplay = new JPanel();
      mainDisplay.setLayout (new BoxLayout(mainDisplay, BoxLayout.LINE_AXIS));
   	
      Display buttonScreen = new Display(250, 500);
      buttonScreen.setPreferredSize(new Dimension (250, 500));
      
      ButtonHandler handler = new ButtonHandler(); 
      JButton woodButton = new JButton("Wood");
      woodButton.addActionListener(handler);
      JButton foodButton = new JButton("Food");
      foodButton.addActionListener(handler);
      JButton ironButton = new JButton("Iron");
      ironButton.addActionListener(handler);
      JButton expeditionButton = new JButton("Expedition");
      expeditionButton.addActionListener(handler);
      
      buttonScreen.setLayout(new GridLayout(5,5));
      buttonScreen.add(woodButton);
      buttonScreen.add(foodButton);
      buttonScreen.add(ironButton);
      buttonScreen.add(expeditionButton);
      
      GameDisplay storyScreen = new GameDisplay(750, 500);
      storyScreen.setPreferredSize(new Dimension (750, 500));
      
      mainDisplay.add(buttonScreen);
      mainDisplay.add(storyScreen);
   	
      gameWindow.setContentPane(mainDisplay);
      gameWindow.repaint();
      gameWindow.setVisible(true);
   }

   public static void main(String[] args) 
   {
      window = new JFrame ("Game");
      window.setContentPane(new StartScreenDisplay(480, 640));		
      window.setSize(480, 640);
      window.setLocation(400,200);
      window.getContentPane().setLayout(new BorderLayout());
      JButton startButton = new JButton("Start game");
      ButtonHandler handler = new ButtonHandler();
      startButton.addActionListener(handler);
      window.add(startButton, BorderLayout.PAGE_END);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setVisible(true);
   }
   
   private static class Messages
   {
      private static int MAXTEXTS = 5;
      private String[][] texts = new String[MAXTEXTS][];
   	
      public Messages ()
      {
      
      }
      
      public void addText(String[] newText)
      {
         for (int i = MAXTEXTS - 1; i > 0; i--)
            texts[i] = texts[i - 1];
         texts[0] = newText;
      	
      }
      
      public String[] getText (int i)
      {
         if (i >= 0 && i < MAXTEXTS)
            return texts[i];
         return null;
      }
      
      public void clearTexts()
      {
         texts = new String[MAXTEXTS][];
      }
   }
   
}