   import java.awt.*;
   import java.awt.event.*;
   import javax.swing.*;
   import java.util.Scanner;

    public class Prototype
   {
      static JFrame startScreen;
      static ButtonDisplay buttonScreen = new ButtonDisplay();
      static StoryDisplay storyScreen = new StoryDisplay();
      static ResourceDisplay resourceScreen = new ResourceDisplay();
      static StartScreenDisplay startScreenDisplay = new StartScreenDisplay();
      static int[] resources = new int [3];
      static String event = "";
   
       private static class StartScreenDisplay extends JPanel
      {
          public void paintComponent(Graphics g) 
         {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.black);
            g2.fillRect(0,0, 480, 640);
            g2.setColor(Color.white);
            g.drawString("Welcome to the parody of the game \"A dark room\"", 15, 20);
            g.drawString("Made by: Shawn Wang, Michael Chang, Raghav Srinivasan, Tong Li Han, Ian Gu", 15, 40);
         }
      
      }
   
       private static class ButtonDisplay extends JPanel 
      {
          public void paintComponent(Graphics g) 
         {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.black);
            g2.fillRect(0,0,480, 640);
         }
      }
    
       private static class StoryDisplay extends JPanel 
      {
          public void paintComponent(Graphics g) 
         {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.black);
            g2.fillRect(0,0,480, 640);
            g2.setColor(Color.white);
            g.drawString(event, 20, 20);
         }
      }
   
       private static class ResourceDisplay extends JPanel 
      {
          public void paintComponent(Graphics g) 
         {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.black);
            g2.fillRect(0,0,480, 640);
            g2.setColor(Color.white);
         // if (cheat)
            // g.drawString("Cheat active", 20, 20);
            g.drawString("Wood: " + resources[0], 20, 40);
            g.drawString("Food: " + resources[1], 20, 60);
            g.drawString("Iron: " + resources[2], 20, 80);
         
         }
      }
   
       private static class ResourceButtonHandler implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
            String action = e.getActionCommand();
            if (action.equals("Wood"))
            {
               resources[0] += 10;
               event = "Received 10 wood";
               resourceScreen.repaint();
               storyScreen.repaint();
            }
            else if (action.equals("Food"))
            {
               resources[1] += 10;
               event = "Received 10 Food";
               resourceScreen.repaint();
               storyScreen.repaint();
            }
            else if (action.equals("Iron"))
            {
               resources[2] += 10;
               event = "Received 10 iron";
               resourceScreen.repaint();
               storyScreen.repaint();
            }
         }
      }
    
       private static class ActionButtonHandler implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
            String action = e.getActionCommand();
            if (action.equals("Expedition"))
            {
               event = "Started expedition";
               storyScreen.repaint();
            }
         }
      }       
   
       private static class StartButtonHandler implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
            startScreen.setVisible(false);
            startGame();
         }
      }       
   
       public static void startGame()
      {
         JFrame gameWindow = new JFrame ("Game");
         gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         gameWindow.setSize(1000, 500);
         gameWindow.setLocation(100, 200);
      	
         JPanel masterPanel = new JPanel();
         masterPanel.setLayout (new BoxLayout(masterPanel, BoxLayout.LINE_AXIS));
      	
         ResourceButtonHandler resourceButtonListener = new ResourceButtonHandler(); 
         JButton woodButton = new JButton("Wood");
         woodButton.addActionListener(resourceButtonListener);
         	
         JButton foodButton = new JButton("Food");
         foodButton.addActionListener(resourceButtonListener);
         	
         JButton ironButton = new JButton("Iron");
         ironButton.addActionListener(resourceButtonListener);
         
         ActionButtonHandler actionButtonHandler = new ActionButtonHandler();
         JButton expeditionButton = new JButton("Expedition");
         expeditionButton.addActionListener(actionButtonHandler);
            
         buttonScreen.setLayout(new GridLayout(5,5));
         buttonScreen.add(woodButton);
         buttonScreen.add(foodButton);
         buttonScreen.add(ironButton);
         buttonScreen.add(expeditionButton);
         
         Dimension buttonScreenDimensions = new Dimension (200, 500);
         buttonScreen.setSize(buttonScreenDimensions);
         buttonScreen.setMinimumSize(buttonScreenDimensions);
         buttonScreen.setMaximumSize(buttonScreenDimensions);
         buttonScreen.setPreferredSize(buttonScreenDimensions);
         
         Dimension storyScreenDimensions = new Dimension (500, 500);
         storyScreen.setSize(storyScreenDimensions);
         storyScreen.setMinimumSize(storyScreenDimensions);
         storyScreen.setMaximumSize(storyScreenDimensions);
         storyScreen.setPreferredSize(storyScreenDimensions);
         
         Dimension resourceScreenDimensions = new Dimension (300, 500);
         resourceScreen.setSize(resourceScreenDimensions);
         resourceScreen.setMinimumSize(resourceScreenDimensions);
         resourceScreen.setMaximumSize(resourceScreenDimensions);
         resourceScreen.setPreferredSize(resourceScreenDimensions);
      	
         masterPanel.add(buttonScreen);
         masterPanel.add(storyScreen);
         masterPanel.add(resourceScreen);
      
         gameWindow.setContentPane(masterPanel);
         gameWindow.setVisible(true);
      
      }
   
       public static void main(String[] args) 
      {
         startScreen = new JFrame ("Game");
         startScreen.setContentPane(startScreenDisplay);
         startScreen.setSize(480, 640);
         startScreen.setLocation(400,200);
      
         startScreenDisplay.setLayout(new BorderLayout());
         StartButtonHandler startButtonHandler = new StartButtonHandler();
      
         JButton startButton = new JButton("Start game");
         startButton.addActionListener(startButtonHandler);
      
         startScreen.add(startButton, BorderLayout.PAGE_END);
         startScreen.setVisible(true);	
      }
   }