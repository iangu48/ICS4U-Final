   import java.awt.*;
   import java.awt.event.*;
   import javax.swing.*;

    public class HelloWorldGUI2 {
      static HelloWorldDisplay displayPanel;
      static String ev = "";
      static int x = 200;
      static int y = 50;
      static int wood = 0;
      static int food = 0;
      static String time = "";
      static int timeH = 18;
      static int timeM = 0;
      static int coolDownW = 0;
      static int coolDownF = 0;
      
       private static class HelloWorldDisplay extends JPanel {
          public void paintComponent(Graphics g) 
         {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.black);
            g2.fillRect(0,0,480, 640);
            g2.setColor(Color.white);
            if (ev.equals ("d"))
            {
               if (coolDownW <= 0)
               { 
                  g.drawString ("Wood + 1", x, y);
                  wood ++;
                  ev = "";
                  timeM += 15;
                  coolDownW = 20;
                  coolDownF -= 15;
                  g.drawString("Cooldown: 15", 100, 20);
               }
               else
               {
                  g.drawString("Cooldown: 15", 100, 20);
                  g.drawString("Cooldown", x, y);
               }
            }
            else if (ev.equals ("f"))
            {
               if (coolDownF <= 0)
               {
                  g.drawString ("Food + 1", x, y);
                  food ++;
                  ev = "";
                  timeM += 25;
                  coolDownF = 10;
                  coolDownW -= 25;
                  g.drawString("Cooldown: 10", 100, 45);
               }
               else
               {
                  g.drawString("Cooldown: 10", 100, 45);
                  g.drawString("Cooldown", x, y);
               }
            }
            if (timeM >= 60)
            {
               timeM -= 60;
               timeH ++;
               if (timeH >= 24)
                  timeH -= 24;
            }
            time = "";
            if (timeH >= 10)
            {
               time += timeH;
            }
            else
            {
               time += "0" + timeH;
            }
            time += ":"; 
            if (timeM >= 10)
            {
               time += timeM;
            }
            else
            {
               time += "0" + timeM;
            }
            g.drawString("Time: " + time, 380, 20);
            g.drawString("Wood: " + wood, 380, 40);
            g.drawString("Food: " + food, 380, 60);
         }
      }
      
   	
   
       private static class ButtonHandler implements ActionListener {
          public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (action.equals("OK"))
               System.exit(0);
            else if (action.equals("Get wood"))
               ev = "d";
            else if (action.equals("Get food "))
               ev = "f";
            displayPanel.repaint();
         }
      }
   
       public static void main(String[] args) {
      
         displayPanel = new HelloWorldDisplay();
         JButton okButton = new JButton("OK");
         JButton wood = new JButton("Get wood");
         JButton food = new JButton("Get food ");
         ButtonHandler listener = new ButtonHandler();
         okButton.addActionListener(listener);
         wood.addActionListener(listener); 
         food.addActionListener(listener);
      	
         displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));
         displayPanel.add(wood);
         displayPanel.add(food);
         displayPanel.setBackground(Color.black);
      
         JFrame window = new JFrame("GUI Test");
         window.setContentPane(displayPanel);
         window.setSize(480,640);
         window.setLocation(500,200);
         window.setVisible(true);
         window.setBackground(Color.black);
      
      }
   
   }