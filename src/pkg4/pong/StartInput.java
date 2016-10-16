
package pkg4.pong;

/**
 *
 * @author Shubh
 */
 
/* From http://java.sun.com/docs/books/tutorial/index.html */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import pkg4.pong.Pong;
import pkg4.pong.network.Data;
import pkg4.pong.network.Networkop;
import pkg4.pong.network.PlayerData;
import java.util.ArrayList;
import pkg4.pong.network.PlayerTh;
import pkg4.pong.network.Streams;
import pkg4.pong.network.StartPacket;
/**
 * StartInput.java is a 1.4 application that uses
 these additional files:
   SpringUtilities.java
   ...
 */
public class StartInput extends JPanel
                                          implements ActionListener,
                                                     FocusListener {
    JTextField userField, Port_NumberField , IP;

    boolean addressSet = false;
    boolean preaddressSet = false;
    Font regularFont, italicFont;
    JLabel addressDisplay;
    final static int GAP = 10;
    int visi = 1;
    String getstr = "";
    Thread wait ;
    ArrayList<PlayerData> sampleplayers = new ArrayList<PlayerData>();
    public StartInput() {
        PlayerData computer = new PlayerData();
          computer.playerid ="COmp123";
          computer.name = "Smarty-Johny";
          computer.ip = "192.168.456.123:8000";
            PlayerData computer1 = new PlayerData();
          computer1.playerid ="Comp456";
          computer1.name = "Clever-Jane";
          computer1.ip = "192.168.455.122:8000";
            PlayerData computer2 = new PlayerData();
          computer2.playerid ="Comp789";
          computer2.name = "Fast-jolly";
          computer2.ip = "192.168.457.121:8000";
          
          sampleplayers.add(computer);
          sampleplayers.add(computer1);
           sampleplayers.add(computer2);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel leftHalf = new JPanel() {
            //Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                                     pref.height);
            }
        };
        leftHalf.setLayout(new BoxLayout(leftHalf,
                                         BoxLayout.PAGE_AXIS));
        leftHalf.add(createEntryFields());
        leftHalf.add(createButtons());

        add(leftHalf);
        add(createAddressDisplay());
    }

    protected JComponent createButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JButton button = new JButton("Start Game");
        button.addActionListener(this);
        panel.add(button);
        
        button = new JButton("Create Game");
        button.addActionListener(this);
        button.setActionCommand("start");
        panel.add(button);
        
        button = new JButton("Join Game");
        button.addActionListener(this);
        button.setActionCommand("clear");
        panel.add(button);

        //Match the SpringLayout's gap, subtracting 5 to make
        //up for the default gap FlowLayout provides.
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0,
                                                GAP-5, GAP-5));
        return panel;
    }

    /**
     * Called when the user clicks the button or presses
     * Enter in a text field.
     */
    public void actionPerformed(ActionEvent e) {
        if ("clear".equals(e.getActionCommand())) {
            addressSet = false;
           // userField.setText("");
           // Port_NumberField.setText("");

            //We can't just setText on the formatted text
            //field, since its value will remain set.
           // IP_addressField.setValue(null);
          joinButton();
        } 
        else if("start".equals(e.getActionCommand())){
            startButton();
            addressSet = false;
            preaddressSet = true;
        }
        else {
            forcegamestart();
            addressSet = true;
            visi = 0;
        }
        updateDisplays();
    }
    private void startButton(){
        String name = userField.getText();
         int port = Integer.parseInt(Port_NumberField.getText()) ;
         System.out.println(name);
         final Networkop network = new Networkop(name,port);
                  System.out.println(name);

                    network.Start();
                  System.out.println(name);
                   boolean check = true; 
            wait = new Thread(){
            public void run(){
                 network.waityo();
            }
                };
            //boolean check = network.waityo();
                  System.out.println(name);
           
    }
    private void forcegamestart(){
        if(wait!=null)
        wait.stop();
        Data data = Data.getInstance();
          int na = data.maxpos;
          int nb = 4 - na+1;
          System.out.println(""+na+" "+nb);
          for(int i=0;i<nb;i++){
              PlayerData playerc = sampleplayers.get(i);
              playerc.pos = na +i ;
              data.maxpos++;
              data.cd.add(playerc);
          }
          StartPacket start = new StartPacket();
          start.pl = data.cd;
          String startdata = "Start " +start.toString() ;
            ArrayList<Thread> streamsd =Streams.getInstance().streams; 
            for (int i=0;i<streamsd.size();i++){
			 PlayerTh player = (PlayerTh) streamsd.get(i);
			 player.writeBack(startdata);
		 }
          Pong pong = new Pong("");
         
    }
    
    private void joinButton(){
        String name = userField.getText();
         int port = Integer.parseInt(Port_NumberField.getText()) ;
         System.out.println("" + port);
         String ip = IP.getText();
              //  ip = "192.168.99.1:4000";
         Networkop network = new Networkop(name,ip,port);
         network.Start();
         
    }
    
    protected void updateDisplays() {
        //addressDisplay.setText(formatAddress());
        if (addressSet) {
            addressDisplay.setFont(regularFont);
            String ip = Networkop.getip();
            System.out.println(ip);
            String temp = "Connect to " + ip +":"+Port_NumberField.getText(); 
            addressDisplay.setText(temp);
            
        }else if(preaddressSet)
        {
            
         
//Pong temppong = new Pong(getstr);
                       

            String ip = Networkop.getip();
             System.out.println(ip);
            String temp = "Connect to " + ip +":"+Port_NumberField.getText(); 
            addressDisplay.setText(temp);
        }
        else {
//           String ip = Networkop.getip();
//            String temp = "Connect to " + ip +":"+Port_NumberField.getText(); 
//            addressDisplay.setText(temp);
        }
    }
      
      public String getStr(){
          return getstr;
      }

    protected JComponent createAddressDisplay() {
        JPanel panel = new JPanel(new BorderLayout());
        addressDisplay = new JLabel();
        addressDisplay.setHorizontalAlignment(JLabel.CENTER);
        regularFont = addressDisplay.getFont().deriveFont(Font.PLAIN,
                                                            16.0f);
        italicFont = regularFont.deriveFont(Font.ITALIC);
        updateDisplays();

        //Lay out the panel.
        panel.setBorder(BorderFactory.createEmptyBorder(
                                GAP/2, //top
                                0,     //left
                                GAP/2, //bottom
                                0));   //right
        panel.add(new JSeparator(JSeparator.VERTICAL),
                  BorderLayout.LINE_START);
        panel.add(addressDisplay,
                  BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(200, 150));

        return panel;
    }


    /**
     * Called when one of the fields gets the focus so that
     * we can select the focused field.
     */
    public void focusGained(FocusEvent e) {
        Component c = e.getComponent();
        if (c instanceof JFormattedTextField) {
            selectItLater(c);
        } else if (c instanceof JTextField) {
            ((JTextField)c).selectAll();
        }
    }

    //Workaround for formatted text field focus side effects.
    protected void selectItLater(Component c) {
        if (c instanceof JFormattedTextField) {
            final JFormattedTextField ftf = (JFormattedTextField)c;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ftf.selectAll();
                }
            });
        }
    }

    //Needed for FocusListener interface.
    public void focusLost(FocusEvent e) { } //ignore
  
    protected JComponent createEntryFields() {
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = {
            "Player Name : ",
            "Port Number : ",
            "IP : "
        };
        
        
        
        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0;

        //Create the text field and set it up.
        userField  = new JTextField();
        userField.setColumns(20);
        fields[fieldNum++] = userField;

        Port_NumberField = new JTextField();
        Port_NumberField.setColumns(20);
        fields[fieldNum++] = Port_NumberField;
        IP = new JTextField();
        IP.setColumns(20);
        fields[fieldNum++] = IP;


        //Associate label/field pairs, add everything,
        //and lay it out.
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i],
                                   JLabel.TRAILING);
            
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);

            //Add listeners to each field.
            JTextField tf = null;
            if (fields[i] instanceof JSpinner) {
                tf = getTextField((JSpinner)fields[i]);
            } else {
                tf = (JTextField)fields[i];
            }
            tf.addActionListener(this);
            tf.addFocusListener(this);
        }
        SpringUtilities.makeCompactGrid(panel,
                                        labelStrings.length, 2,
                                        GAP, GAP, //init x,y
                                        GAP, GAP/2);//xpad, ypad
        return panel;
    }

    public String[] getGame_typeStrings() {
        String[] Game_typeStrings = {
            
            "Start New Game",
            "Join Game"
        };
        return Game_typeStrings;
    }

    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor)editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                               + spinner.getEditor().getClass()
                               + " isn't a descendant of DefaultEditor");
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame(" Ping Pong ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        JComponent newContentPane = new StartInput();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);/*
        if (visi ==0 )
        {
            frame.setVisible(true);
        } else {  
            frame.setVisible(true);
        }*/
    }
    public void remove(){
        this.setVisible(false);
    }
    
}

/**
 * A 1.4 file that provides utility methods for
 * creating form- or grid-style layouts with SpringLayout.
 * These utilities are used by several programs, such as
 * SpringBox and SpringCompactGrid.
 */
 class SpringUtilities {
    /**
     * A debugging utility that prints to stdout the component's
     * minimum, preferred, and maximum sizes.
     */
    public static void printSizes(Component c) {
        System.out.println("minimumSize = " + c.getMinimumSize());
        System.out.println("preferredSize = " + c.getPreferredSize());
        System.out.println("maximumSize = " + c.getMaximumSize());
    }

    /**
     * Aligns the first <code>rows</code> * <code>cols</code>
     * components of <code>parent</code> in
     * a grid. Each component is as big as the maximum
     * preferred width and height of the components.
     * The parent is made just big enough to fit them all.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param initialX x location to start the grid at
     * @param initialY y location to start the grid at
     * @param xPad x padding between cells
     * @param yPad y padding between cells
     */
    public static void makeGrid(Container parent,
                                int rows, int cols,
                                int initialX, int initialY,
                                int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeGrid must use SpringLayout.");
            return;
        }

        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;

        //Calculate Springs that are the max of the width/height so that all
        //cells have the same size.
        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).
                                    getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).
                                    getWidth();
        for (int i = 1; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                                            parent.getComponent(i));

            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }

        //Apply the new width/height Spring. This forces all the
        //components to have the same size.
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                                            parent.getComponent(i));

            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }

        //Then adjust the x/y constraints of all the cells so that they
        //are aligned in a grid.
        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                                                 parent.getComponent(i));
            if (i % cols == 0) { //start of new row
                lastRowCons = lastCons;
                cons.setX(initialXSpring);
            } else { //x position depends on previous component
                cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST),
                                     xPadSpring));
            }

            if (i / cols == 0) { //first row
                cons.setY(initialYSpring);
            } else { //y position depends on previous row
                cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH),
                                     yPadSpring));
            }
            lastCons = cons;
        }

        //Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH,
                            Spring.sum(
                                Spring.constant(yPad),
                                lastCons.getConstraint(SpringLayout.SOUTH)));
        pCons.setConstraint(SpringLayout.EAST,
                            Spring.sum(
                                Spring.constant(xPad),
                                lastCons.getConstraint(SpringLayout.EAST)));
    }

    /* Used by makeCompactGrid. */
    private static SpringLayout.Constraints getConstraintsForCell(
                                                int row, int col,
                                                Container parent,
                                                int cols) {
        SpringLayout layout = (SpringLayout) parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }

    /**
     * Aligns the first <code>rows</code> * <code>cols</code>
     * components of <code>parent</code> in
     * a grid. Each component in a column is as wide as the maximum
     * preferred width of the components in that column;
     * height is similarly determined for each row.
     * The parent is made just big enough to fit them all.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param initialX x location to start the grid at
     * @param initialY y location to start the grid at
     * @param xPad x padding between cells
     * @param yPad y padding between cells
     */
    public static void makeCompactGrid(Container parent,
                                       int rows, int cols,
                                       int initialX, int initialY,
                                       int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
            return;
        }

        //Align all cells in each column and make them the same width.
        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; c++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; r++) {
                width = Spring.max(width,
                                   getConstraintsForCell(r, c, parent, cols).
                                       getWidth());
            }
            for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
        }

        //Align all cells in each row and make them the same height.
        Spring y = Spring.constant(initialY);
        for (int r = 0; r < rows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < cols; c++) {
                height = Spring.max(height,
                                    getConstraintsForCell(r, c, parent, cols).
                                        getHeight());
            }
            for (int c = 0; c < cols; c++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
        }

        //Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }
}


