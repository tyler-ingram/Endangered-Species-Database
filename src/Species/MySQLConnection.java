package Species;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
 * Interface       MySQLConnection
 * Description     Interface that holds constants for the MySQL server
 * @author         <i>Tyler Ingram </i>
 * Environment     PC, Windows 10, NetBeans IDE 11.3, jdk 1.8.0_241
 * Date            10/18/2022
 * @version        1.0.0
 * History Log  
*</pre>
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public interface MySQLConnection 
{
    public static final String DB_URL = "jdbc:mysql://localhost:3306/javabook";
    public static final String USER = "scott";
    public static final String PASS = "tiger";
    public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
}
