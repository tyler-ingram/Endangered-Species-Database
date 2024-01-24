package Species;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
 * Class        AddressBookGUI.java
 * Description  A class representing the GUI used in the Address Book 
 *              Application. This program that stores, retrieves, adds, and 
 *              updates addresses using SQL statements in Address Book DB.
 * Platform     jdk 1.8.0_241; NetBeans IDE 11.3; PC Windows 10
 * Course       CS 143
 * Hourse       4 hours and 17 minutes
 * Date         10/27/2022
  History Log   
 * @author	<i>Tyler Ingram</i>
 * @version 	%1% %2%
 * @see     	javax.swing.JFrame
 * @see         java.awt.Toolkit 
 *</pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class EndangeredSpeciesGUI extends JFrame implements MySQLConnection
{
    private Species mySpecies = new Species();
    private int sizeOfDB;
    private final Color white = Color.WHITE;    // Default background color for input textfield
    private final Color pink = Color.PINK;      // Background color for bad input textfield
    private DefaultListModel speciesModel = new DefaultListModel();
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Constructor  AddressBookGUI()-default constructor
     * Description  Create an instance of the GUI form and sets icon image.
     * Date         10/27/2022
     * History Log  
     * @author      <i>Tyler Ingram</i>
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public EndangeredSpeciesGUI() 
    {
        try
        {
            initComponents();        
            this.getRootPane().setDefaultButton(addJButton);
            this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/Images/Tiger.jpg"));
            //read from text file and fill ArrayList
            String url = DB_URL;
            String user = USER;
            String password = PASS;
            
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM EndangeredSpecies");
            rs.next();
            sizeOfDB = rs.getInt("count(*)");
            
            String query = "SELECT * FROM EndangeredSpecies";
            rs = stmt.executeQuery(query);
            rs.next(); //move to first record
            
            String name = rs.getString("name");
            Species tempSpecies = searchSpecies(name);
            
            display(tempSpecies);
            fillList();
        }
        catch(SQLException exp)
        {
            exp.printStackTrace();
            // Show error message
            JOptionPane.showMessageDialog(null, "Input error -- SQL error.",
                    "SQL Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       display()
     * Description  Show information about the species passed as parameter.
     * @param       mySpecies species
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log       
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void display(Species mySpecies)
    {
        nameJTextField.setText(mySpecies.getName());
        genusJTextField.setText(mySpecies.getGenus());
        populationJTextField.setText(String.valueOf(mySpecies.getPopulation()));
        dietJTextField.setText(mySpecies.getDiet());
        habitatJTextField.setText(mySpecies.getHabitat());
        predatorsJTextField.setText(mySpecies.getPredators());
        predatorsJTextField.setCaretPosition(0);
        dietJTextField.setCaretPosition(0);
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       fillList()
     * Description  Fill the list with the names of species sorted by what is 
     *              selected either name, or population
     * @param       mySpecies species
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log       
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/    
    private void fillList()
    {
        speciesModel.clear();
        boolean nameSort = nameJRadioButtonMenuItem.isSelected();
        try
        {
            String url = DB_URL;
            String user = USER;
            String password = PASS;
            
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            if(nameSort)
            {
                rs = stmt.executeQuery("SELECT name from EndangeredSpecies ORDER BY name ASC;");
//                rs.next();
                 while(rs.next())
                {
                    speciesModel.addElement(rs.getString("name"));
//                    rs.next();
                }
            }
            else
            {
                rs = stmt.executeQuery("SELECT name, population from EndangeredSpecies ORDER BY population ASC;");
                rs.next();
                while(rs.next())
                {
                    speciesModel.addElement(rs.getString("name") + ", " + 
                            rs.getInt("population"));
//                    rs.next();
                }
            }
            speciesJList.setModel(speciesModel);
            speciesJList.setSelectedIndex(0);
        }
        catch(SQLException exp)
        {
            exp.printStackTrace();
            // Show error message
            JOptionPane.showMessageDialog(null, "Input error -- SQL error.",
                    "SQL Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       searchSpecies()
     * Description  Search species in DB by name.
     * Date:        10/27/2022
     * @author      <i>Tyler Ingram</i>
     * @param       name String
     * @return      mySpecies species
     *</pre>
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    private Species searchSpecies(String name)
    {
        try
        { 
            
            if(name.contains(","))
                name = name.substring(0, name.indexOf(','));
            String url = DB_URL;
            String user = USER;
            String password = PASS;
            
            Connection con = DriverManager.getConnection(url,user,password);
            mySpecies = new Species();
            String query = "SELECT * FROM EndangeredSpecies WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1,name);
            ResultSet results = pstmt.executeQuery();
            results.next();
            mySpecies.setName(results.getString(1));
            mySpecies.setDiet(results.getString(2));
            mySpecies.setGenus(results.getString(3));
            mySpecies.setPopulation(results.getInt(4));
            mySpecies.setHabitat(results.getString(5));
            mySpecies.setPredators(results.getString(6));
            results.close();
            pstmt.close();
            con.close();
            return mySpecies;
        }
        catch(SQLException exp)
        {
            exp.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error searching database for species", 
                    "Search Error", JOptionPane.ERROR_MESSAGE);
            return new Species();
        }        
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sortButtonGroup = new javax.swing.ButtonGroup();
        titleJLabel = new javax.swing.JLabel();
        displayJPanel = new javax.swing.JPanel();
        nameJLabel = new javax.swing.JLabel();
        nameJTextField = new javax.swing.JTextField();
        genusJLabel = new javax.swing.JLabel();
        genusJTextField = new javax.swing.JTextField();
        populationJLabel = new javax.swing.JLabel();
        populationJTextField = new javax.swing.JTextField();
        dietJLabel = new javax.swing.JLabel();
        dietJScrollPane = new javax.swing.JScrollPane();
        dietJTextField = new javax.swing.JTextField();
        habitatJLabel = new javax.swing.JLabel();
        habitatJTextField = new javax.swing.JTextField();
        predatorsJLabel = new javax.swing.JLabel();
        predatorsJScrollPane = new javax.swing.JScrollPane();
        predatorsJTextField = new javax.swing.JTextField();
        controlJPanel = new javax.swing.JPanel();
        addJButton = new javax.swing.JButton();
        editJButton = new javax.swing.JButton();
        deleteJButton = new javax.swing.JButton();
        quitJButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        speciesJList = new javax.swing.JList<>();
        speciesJMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        newJMenuItem = new javax.swing.JMenuItem();
        printFormJMenuItem = new javax.swing.JMenuItem();
        printJMenuItem = new javax.swing.JMenuItem();
        quitJMenuItem = new javax.swing.JMenuItem();
        sortJMenu = new javax.swing.JMenu();
        nameJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        popJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        dbManagementJMenu = new javax.swing.JMenu();
        addJMenuItem = new javax.swing.JMenuItem();
        deleteJMenuItem = new javax.swing.JMenuItem();
        editJMenuItem = new javax.swing.JMenuItem();
        searchJMenuItem = new javax.swing.JMenuItem();
        speciesDetailsJMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Endangered Species");
        setResizable(false);

        titleJLabel.setFont(new java.awt.Font("Tempus Sans ITC", 2, 36)); // NOI18N
        titleJLabel.setForeground(new java.awt.Color(0, 102, 102));
        titleJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Tiger_small.jpg"))); // NOI18N
        titleJLabel.setText("Endangered Species");

        displayJPanel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        displayJPanel.setLayout(new java.awt.GridLayout(6, 2, 3, 3));

        nameJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameJLabel.setText("Name of Species:");
        displayJPanel.add(nameJLabel);

        nameJTextField.setEditable(false);
        nameJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nameJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        displayJPanel.add(nameJTextField);

        genusJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        genusJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        genusJLabel.setText("Genus:");
        displayJPanel.add(genusJLabel);

        genusJTextField.setEditable(false);
        genusJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        genusJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        displayJPanel.add(genusJTextField);

        populationJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        populationJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        populationJLabel.setText("Population:");
        displayJPanel.add(populationJLabel);

        populationJTextField.setEditable(false);
        populationJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        populationJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        displayJPanel.add(populationJTextField);

        dietJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dietJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dietJLabel.setText("Diet:");
        displayJPanel.add(dietJLabel);

        dietJTextField.setEditable(false);
        dietJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dietJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        dietJScrollPane.setViewportView(dietJTextField);

        displayJPanel.add(dietJScrollPane);

        habitatJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        habitatJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        habitatJLabel.setText("Habitat:");
        displayJPanel.add(habitatJLabel);

        habitatJTextField.setEditable(false);
        habitatJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        habitatJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        displayJPanel.add(habitatJTextField);

        predatorsJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        predatorsJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        predatorsJLabel.setText("Preadators:");
        displayJPanel.add(predatorsJLabel);

        predatorsJTextField.setEditable(false);
        predatorsJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        predatorsJTextField.setScrollOffset(1);
        predatorsJScrollPane.setViewportView(predatorsJTextField);

        displayJPanel.add(predatorsJScrollPane);

        controlJPanel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        controlJPanel.setLayout(new java.awt.GridLayout(1, 4, 3, 3));

        addJButton.setBackground(new java.awt.Color(255, 255, 204));
        addJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addJButton.setMnemonic('A');
        addJButton.setText("Add");
        addJButton.setToolTipText("Add new record");
        addJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(addJButton);

        editJButton.setBackground(new java.awt.Color(255, 255, 204));
        editJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        editJButton.setMnemonic('E');
        editJButton.setText("Edit");
        editJButton.setToolTipText("Edit current record");
        editJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(editJButton);

        deleteJButton.setBackground(new java.awt.Color(255, 255, 204));
        deleteJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        deleteJButton.setMnemonic('D');
        deleteJButton.setText("Delete");
        deleteJButton.setToolTipText("Delete current record");
        deleteJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(deleteJButton);

        quitJButton.setBackground(new java.awt.Color(255, 255, 204));
        quitJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        quitJButton.setMnemonic('Q');
        quitJButton.setText("Quit");
        quitJButton.setToolTipText("End application");
        quitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(quitJButton);

        speciesJList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        speciesJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                speciesJListMouseClicked(evt);
            }
        });
        speciesJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                speciesJListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(speciesJList);

        fileJMenu.setMnemonic('F');
        fileJMenu.setText("File");

        newJMenuItem.setMnemonic('n');
        newJMenuItem.setText("New");
        newJMenuItem.setToolTipText("Use a new set of species");
        fileJMenu.add(newJMenuItem);

        printFormJMenuItem.setMnemonic('f');
        printFormJMenuItem.setText("Print Form");
        printFormJMenuItem.setToolTipText("Print Form as GUI");
        printFormJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printFormJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printFormJMenuItem);

        printJMenuItem.setMnemonic('P');
        printJMenuItem.setText("Print");
        printJMenuItem.setToolTipText("Print the Species Info");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);

        quitJMenuItem.setMnemonic('Q');
        quitJMenuItem.setText("Quit");
        quitJMenuItem.setToolTipText("");
        quitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(quitJMenuItem);

        speciesJMenuBar.add(fileJMenu);

        sortJMenu.setMnemonic('F');
        sortJMenu.setText("Sort");
        sortButtonGroup.add(sortJMenu);

        sortButtonGroup.add(nameJRadioButtonMenuItem);
        nameJRadioButtonMenuItem.setMnemonic('s');
        nameJRadioButtonMenuItem.setSelected(true);
        nameJRadioButtonMenuItem.setText("Sort by Name");
        nameJRadioButtonMenuItem.setToolTipText("Sort the species by their names alphabetically");
        nameJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(nameJRadioButtonMenuItem);

        sortButtonGroup.add(popJRadioButtonMenuItem);
        popJRadioButtonMenuItem.setMnemonic('y');
        popJRadioButtonMenuItem.setText("Sort by population");
        popJRadioButtonMenuItem.setToolTipText("Sort the species by population in ascending order");
        popJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(popJRadioButtonMenuItem);

        speciesJMenuBar.add(sortJMenu);

        dbManagementJMenu.setMnemonic('F');
        dbManagementJMenu.setText("Database Management");

        addJMenuItem.setMnemonic('A');
        addJMenuItem.setText("Add");
        addJMenuItem.setToolTipText("Add a new species");
        addJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJMenuItemActionPerformed(evt);
            }
        });
        dbManagementJMenu.add(addJMenuItem);

        deleteJMenuItem.setMnemonic('D');
        deleteJMenuItem.setText("Delete");
        deleteJMenuItem.setToolTipText("Delete selected species");
        deleteJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJMenuItemActionPerformed(evt);
            }
        });
        dbManagementJMenu.add(deleteJMenuItem);

        editJMenuItem.setMnemonic('e');
        editJMenuItem.setText("Edit");
        editJMenuItem.setToolTipText("Edit the selected Species");
        editJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJMenuItemActionPerformed(evt);
            }
        });
        dbManagementJMenu.add(editJMenuItem);

        searchJMenuItem.setMnemonic('s');
        searchJMenuItem.setText("Search");
        searchJMenuItem.setToolTipText("Search for a species by name");
        searchJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJMenuItemActionPerformed(evt);
            }
        });
        dbManagementJMenu.add(searchJMenuItem);

        speciesDetailsJMenuItem.setMnemonic('o');
        speciesDetailsJMenuItem.setText("Species Details");
        speciesDetailsJMenuItem.setToolTipText("Show the details of the currently selected species");
        speciesDetailsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speciesDetailsJMenuItemActionPerformed(evt);
            }
        });
        dbManagementJMenu.add(speciesDetailsJMenuItem);

        speciesJMenuBar.add(dbManagementJMenu);

        helpMenu.setMnemonic('H');
        helpMenu.setText("Help");

        aboutJMenuItem.setMnemonic('b');
        aboutJMenuItem.setText("About");
        aboutJMenuItem.setToolTipText("Show About form");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutJMenuItem);

        speciesJMenuBar.add(helpMenu);

        setJMenuBar(speciesJMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(controlJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(displayJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(titleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(titleJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(displayJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(controlJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       quitJButtonActionPerformed()
     * Description  Event handler to end the application.
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log       
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void quitJButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_quitJButtonActionPerformed
    {//GEN-HEADEREND:event_quitJButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_quitJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       addJButtonActionPerformed()
     * Description  Add a new person. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addJButtonActionPerformed
    {//GEN-HEADEREND:event_addJButtonActionPerformed
        String message = "Species not added.";
        try
        {
            AddSpecies myAddForm = new AddSpecies(this,true);
            myAddForm.setVisible(true);
            int lastIndex = 0;
            Species newSpecies = myAddForm.getSpecies();
            
            if(newSpecies != null && !exists(newSpecies))
            {
            String url = DB_URL;
            String user = USER;
            String password = PASS;
            
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM EndangeredSpecies";
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            
            stmt.executeUpdate
                ("INSERT INTO EndangeredSpecies VALUES ( '" + newSpecies.getName() + "' ,'" +
                        newSpecies.getDiet() + "', '" + newSpecies.getGenus()
                + "', " + newSpecies.getPopulation() + ", '" + newSpecies.getHabitat()+
                        "', '" + newSpecies.getPredators()+ "')");
            
            display(newSpecies);
            speciesModel.clear();
            fillList();
            nameJRadioButtonMenuItem.setSelected(true);
            int index = 0;
            for(int i = 0; i < speciesModel.size(); i++)
            {
                if(speciesModel.get(i).equals(newSpecies.getName()))
                    index = i;
                
            }
            speciesJList.setSelectedIndex(index);
            sizeOfDB++;
            con.close();
            }
            else
            {
                message = "Species not added";
                display(searchSpecies((speciesModel.get(0).toString())));
            }
        }
        catch(NullPointerException exp)
        {
            exp.printStackTrace();
            JOptionPane.showMessageDialog(null, message, "Input Error",
                    JOptionPane.WARNING_MESSAGE);            
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating to database", 
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addJButtonActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       deleteJButtonActionPerformed()
     * Description  Delete the displayed species. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void deleteJButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteJButtonActionPerformed
    {//GEN-HEADEREND:event_deleteJButtonActionPerformed
        Species speciesToDelete = null;
        String name = "";
        if (speciesJList.getSelectedIndex()>0)
            speciesToDelete = searchSpecies(speciesJList.getSelectedValue());
        else
            speciesToDelete = searchSpecies(speciesModel.getElementAt(0).toString());
        
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want "
                + "to delte " + speciesToDelete.getName() + "?", "Delete Species",
                JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION)
        {
            if(speciesToDelete.getName().contains(","))
                 name = speciesToDelete.getName().substring(0,speciesToDelete.getName().indexOf(","));
                
            try
            {
            String url = DB_URL;
            String user = USER;
            String password = PASS;
            
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement();
            String query = "DELETE FROM EndangeredSpecies WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1,speciesToDelete.getName());
            pstmt.execute();
            
            query = "SELECT * FROM EndangeredSpecies";
          
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String speciesName = rs.getString("name");
            display(searchSpecies(speciesName));
            con.close();
            speciesModel.clear();
            fillList();
            }
            catch(SQLException exp)
            {
                JOptionPane.showMessageDialog(null, "Error deleting", 
                    " Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_deleteJButtonActionPerformed
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       editJButtonActionPerformed()
     * Description  Edit the displayed person. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void editJButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_editJButtonActionPerformed
    {//GEN-HEADEREND:event_editJButtonActionPerformed
        String message = "Species not edited.";
        try
        {
            int index = 0;
            String name = nameJTextField.getText();
            mySpecies = searchSpecies(name);
            AddSpecies myEditForm = new AddSpecies(mySpecies);
            myEditForm.setVisible(true);
            String oldName = mySpecies.getName();
            Species editSpecies = myEditForm.getSpecies();
            if(editSpecies !=null)
            {
                mySpecies.setName(editSpecies.getName());
                mySpecies.setDiet(editSpecies.getDiet());
                mySpecies.setPopulation(editSpecies.getPopulation());
                mySpecies.setGenus(editSpecies.getGenus());
                mySpecies.setHabitat(editSpecies.getHabitat());
                mySpecies.setPredators(editSpecies.getPredators());
                String url = DB_URL;
                String user = USER;
                String password = PASS;
            
                Connection con = DriverManager.getConnection(url,user,password);
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                String query = "SELECT * FROM EndangeredSpecies";
                ResultSet rs = stmt.executeQuery(query);
                rs.last();
                stmt.executeUpdate
                ("UPDATE EndangeredSpecies SET name =" + "'" +
                        mySpecies.getName() + "', diet = '" 
                        + mySpecies.getDiet()+ "', genus = '" + 
                        mySpecies.getGenus() + "', population = '" 
                        + mySpecies.getPopulation() +"', habitat = '" 
                        + mySpecies.getHabitat() + "', predators = '" 
                        + mySpecies.getPredators() +"' WHERE name = '" + 
                        oldName + "';");
            display(mySpecies);
            speciesModel.clear();
            fillList();
            nameJRadioButtonMenuItem.setSelected(true);
            for(int i = 0; i < speciesModel.size(); i++)
            {
                if(speciesModel.get(i).equals(mySpecies.getName()))
                    index = i; 
            }
            speciesJList.setSelectedIndex(index);
            stmt.close();
            con.close();
            
            }
            else
            {
                JOptionPane.showMessageDialog(null, message,
                        "Error!", JOptionPane.WARNING_MESSAGE);
            }
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
            JOptionPane.showMessageDialog(null, message, 
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_editJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       quitJMenuItemActionPerformed()
     * Description  Event handler to end the application. Calls the quitJButton
     *              doClick event handler,
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log       
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void quitJMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_quitJMenuItemActionPerformed
    {//GEN-HEADEREND:event_quitJMenuItemActionPerformed
        quitJButton.doClick();
    }//GEN-LAST:event_quitJMenuItemActionPerformed
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       printJMenuItemActionPerformed()
     * Description  Event handler to print the for as a GUI. Calls the
     *              PrintUtilities class printComponent method.
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  , 10/27/2022
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void printFormJMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_printFormJMenuItemActionPerformed
    {//GEN-HEADEREND:event_printFormJMenuItemActionPerformed
        PrintUtilities.printComponent(this);
    }//GEN-LAST:event_printFormJMenuItemActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       aboutJMenuItemActionPerformed()
     * Description  Create an About form and show it. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aboutJMenuItemActionPerformed
    {//GEN-HEADEREND:event_aboutJMenuItemActionPerformed
        About aboutWindow = new About(this, true);
        aboutWindow.setVisible(true);
    }//GEN-LAST:event_aboutJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       printJMenuItemActionPerformed()
     * Description  Print the info of the selected species in the list
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
         String name  = speciesJList.getSelectedValue();
            try
            {
                Species temp = new Species(searchSpecies(name));
                String output = "Species: " + temp.getName() + "\nGenus:: " + temp.getGenus()
                + "\nPopultaion remaining: " + temp.getPopulation() + "\nDiet: " + 
                temp.getDiet() + "\nHabitat: " + temp.getHabitat() + "\nPredators: " +
                        temp.getPredators();
                File printOutPutFile = new File("src/Species/Print.txt");
                FileOutputStream outSteam = new FileOutputStream(printOutPutFile);
                BufferedOutputStream buffOut = new BufferedOutputStream(outSteam);
                PrintWriter pw = new PrintWriter(buffOut,false);
                pw.print(output);
                pw.close();
            }
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(null, "Results not printed", "Print Error", JOptionPane.WARNING_MESSAGE);
            }
    }//GEN-LAST:event_printJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       addJMenuItemActionPerformed()
     * Description  Calls the addJButton event handler to add a new species.
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void addJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJMenuItemActionPerformed
        addJButton.doClick();
    }//GEN-LAST:event_addJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       deleteJMenuItemActionPerformed()
     * Description  Calls the deleteJButton event handler to delete a species.
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void deleteJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJMenuItemActionPerformed
        deleteJButton.doClick();
    }//GEN-LAST:event_deleteJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       editJMenuItemActionPerformed()
     * Description  Calls the editJButton event handler to edit a species.
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void editJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJMenuItemActionPerformed
        editJButton.doClick();
    }//GEN-LAST:event_editJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       searchJMenuItemActionPerformed()
     * Description  Searches for the species in the DB and selects it in the list
     *              if found
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void searchJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJMenuItemActionPerformed
        try
        {
        String name = JOptionPane.showInputDialog(null, "Enter name of species", "Species Search", 
                JOptionPane.INFORMATION_MESSAGE);
        if(name!= null)
        {
        nameJRadioButtonMenuItem.setSelected(true);
        String url = DB_URL;
        String user = USER;
        String password = PASS;
        Connection con = DriverManager.getConnection(url,user,password);
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
        String query = "SELECT * FROM EndangeredSpecies WHERE name like '%" + name + "%';";        
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        String searchName = rs.getString("name");
        Species seachedSpecies = searchSpecies(searchName);
        if(seachedSpecies != null)
        {
            display(seachedSpecies);
            for(int i =0; i < speciesModel.size(); i ++)
                if(speciesModel.get(i).equals(name))
                    speciesJList.setSelectedIndex(i);
        }
        else
            JOptionPane.showMessageDialog(null, "Species not found", "Search Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "SQL Exception", "SQL Error",
                    JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_searchJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       nameJRadioButtonMenuItemActionPerformed()
     * Description  Changes the list to be sorted by name by calling the fillList()
     *               method
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void nameJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameJRadioButtonMenuItemActionPerformed
        fillList();
        display(searchSpecies(speciesModel.get(0).toString()));
    }//GEN-LAST:event_nameJRadioButtonMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       popJRadioButtonMenuItemActionPerformed()
     * Description  Changes the list to be sorted by population by calling the 
     *              fillList() method
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void popJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popJRadioButtonMenuItemActionPerformed
        fillList();
        display(searchSpecies(speciesModel.get(0).toString()));

    }//GEN-LAST:event_popJRadioButtonMenuItemActionPerformed

   /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       speciesDetailsJMenuItemActionPerformed()
     * Description  Displays the detials of the selected species
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void speciesDetailsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speciesDetailsJMenuItemActionPerformed
        String speciesName = nameJTextField.getText();

        Species species = new Species(searchSpecies(speciesName));
        SpeciesDetails speciesDetails = new SpeciesDetails(this,true,species);
        speciesDetails.setVisible(true);
    }//GEN-LAST:event_speciesDetailsJMenuItemActionPerformed

    private void speciesJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_speciesJListValueChanged
//        String name = speciesJList.getSelectedValue();
//        if(name.contains(","))
//            name = name.substring(0, name.indexOf(','));
//        display(searchSpecies(name));
    }//GEN-LAST:event_speciesJListValueChanged
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       speciesJListMouseClicked()
     * Description  Displays the detials of the selected species
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void speciesJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_speciesJListMouseClicked
        String name = speciesJList.getSelectedValue();
        display(searchSpecies(name));
    }//GEN-LAST:event_speciesJListMouseClicked

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       exists()
     * Description  Check if parameter-given species exists in the DB. 
     * @param       mySpecies Species
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private boolean exists(Species mySpecies)
    {
        boolean found = false;
        try
        {
            String url = DB_URL;
            String user = USER;
            String password = PASS;
            
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM EndangeredSpecies WHERE name = '" + 
                    mySpecies.getName() + "' && genus = '" + mySpecies.getGenus() +"';";
            return !stmt.execute(query);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in exists.", 
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
        return found;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
     * Method       main()
     * Description  Displays splash screen and the main Address Book DB GUI form.
     * @param       args are the command line strings
     * @author      <i>Tyler Ingram</i>
     * Date         10/27/2022
     * History Log  
     *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void main(String args[])
    {
        // Show splash screen
        Splash mySplash = new Splash(4000);     // duration = 5 seconds
        mySplash.showSplash();                  // show splash screen   
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(EndangeredSpeciesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(EndangeredSpeciesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(EndangeredSpeciesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(EndangeredSpeciesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new EndangeredSpeciesGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JButton addJButton;
    private javax.swing.JMenuItem addJMenuItem;
    private javax.swing.JPanel controlJPanel;
    private javax.swing.JMenu dbManagementJMenu;
    private javax.swing.JButton deleteJButton;
    private javax.swing.JMenuItem deleteJMenuItem;
    private javax.swing.JLabel dietJLabel;
    private javax.swing.JScrollPane dietJScrollPane;
    private javax.swing.JTextField dietJTextField;
    private javax.swing.JPanel displayJPanel;
    private javax.swing.JButton editJButton;
    private javax.swing.JMenuItem editJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JLabel genusJLabel;
    private javax.swing.JTextField genusJTextField;
    private javax.swing.JLabel habitatJLabel;
    private javax.swing.JTextField habitatJTextField;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameJLabel;
    private javax.swing.JRadioButtonMenuItem nameJRadioButtonMenuItem;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JMenuItem newJMenuItem;
    private javax.swing.JRadioButtonMenuItem popJRadioButtonMenuItem;
    private javax.swing.JLabel populationJLabel;
    private javax.swing.JTextField populationJTextField;
    private javax.swing.JLabel predatorsJLabel;
    private javax.swing.JScrollPane predatorsJScrollPane;
    private javax.swing.JTextField predatorsJTextField;
    private javax.swing.JMenuItem printFormJMenuItem;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JButton quitJButton;
    private javax.swing.JMenuItem quitJMenuItem;
    private javax.swing.JMenuItem searchJMenuItem;
    private javax.swing.ButtonGroup sortButtonGroup;
    private javax.swing.JMenu sortJMenu;
    private javax.swing.JMenuItem speciesDetailsJMenuItem;
    private javax.swing.JList<String> speciesJList;
    private javax.swing.JMenuBar speciesJMenuBar;
    private javax.swing.JLabel titleJLabel;
    // End of variables declaration//GEN-END:variables
}
