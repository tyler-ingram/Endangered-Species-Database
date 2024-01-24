package Species;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
 * Class        CreateEndangeredSpeciesDB.java
 * Description  A class used to create the Endangered Species DB 
 * Platform     jdk 1.8.0_241; NetBeans IDE 11.3; PC Windows 10
 * Course       CS 143
 * Hours        4 hours and 17 minutes
 * Date         10/25/2022
  History Log   
 * @author	<i>Tyler Ingram</i>
 * @version 	%1% %2%
 * @see     	javax.swing.JFrame
 * @see         java.awt.Toolkit 
 *</pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class CreateEndangeredSpeciesDB implements MySQLConnection
{
    private static final String SPECIES_TEXT_FILE = "src/Species/Species.txt";
    private static final ArrayList<Species> species = new ArrayList<>();

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
     * Method       main()
     * Description  Creates the DB
     * @param       args are the command line strings
     * @author      <i>Tyler Ingram</i>
     * Date         10/25/2022
     * History Log  
     *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void main(String[] args) 
    {
        try
        {
            readFromTextFile(SPECIES_TEXT_FILE);
            String url = DB_URL;
            String user = USER;
            String password = PASS;
            
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY);
            DatabaseMetaData dbm  = con.getMetaData();
            ResultSet table;
            table = dbm.getTables(null, null, "EndangeredSpecies", null);
            if(table.next())
            {
                stmt.executeUpdate("DROP TABLE EndangeredSpecies");
            // drop it if it exists so we can make new one
            }
            stmt.executeUpdate("CREATE TABLE EndangeredSpecies (name VARCHAR(30),"
                    + " diet VARCHAR(200), genus VARCHAR(200), population INTEGER,"
                    + " habitat VARCHAR(200), predators VARCHAR (200), "
                    + "PRIMARY KEY (name))");
            
        for(int i = 0; i < species.size(); i++)
        {
            stmt.executeUpdate("INSERT INTO EndangeredSpecies VALUES( " +  "'" + species.get(i).getName() 
            + "'," + " '" + species.get(i).getDiet() + "'," + " '" + species.get(i).getGenus()
            + "'," + " '" + species.get(i).getPopulation() + "'," + " '"
            + species.get(i).getHabitat() + "'," + " '"
            + species.get(i).getPredators() + "')");
        }
            stmt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error Creating DB", "Create DB Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       readFromTextFile()
     * Description  Reads text file and creates an arraylist with species.
     * Date:        10/25/2022
     * @author      <i>Tyler Ingram</i>
     * @param       textFile String
     *</pre>
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private static void readFromTextFile(String textFile)
    {        
        try
        {            
            FileReader freader = new FileReader(textFile);
            BufferedReader input = new BufferedReader(freader);
            String line = input.readLine();
            while (line != null)
            {
                Species tempSpecies = new Species();
                StringTokenizer token = new StringTokenizer(line,"|");
                while(token.hasMoreElements())
                {
                    tempSpecies.setName(token.nextToken());
                    tempSpecies.setGenus(token.nextToken());
                    tempSpecies.setPopulation(Integer.parseInt(token.nextToken()));
                    tempSpecies.setDiet(token.nextToken());
                    tempSpecies.setHabitat(token.nextToken());
                    tempSpecies.setPredators(token.nextToken());
                }
                species.add(tempSpecies);
                line = input.readLine();
            }
            input.close();
        }
        catch(FileNotFoundException fnfexp)
        {
            JOptionPane.showMessageDialog(null, "Input error -- File not found.",
                    "File Not Found Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException | NumberFormatException exp)
        {
            exp.printStackTrace();
            JOptionPane.showMessageDialog(null, "Input error -- File could not be read.",
                    "File Read Error!", JOptionPane.ERROR_MESSAGE);
        }
    }    
}
