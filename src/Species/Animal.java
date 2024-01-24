package Species;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* <pre>
 * Class        Animal 
 * Project      Endangered Species DB
 * Description  A class that describes an Animal 
 * Environment  PC, Windows 10, jdk1.8.0_241, NetBeans 11.3
 * Date         10/25/2022
 * History Log  
 * @author      <i>Tyler Ingram</i>
 * @version     1.3.2
* </pre>
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public abstract class Animal 
{
    protected String name;
    protected String diet;
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Constructor      Animal
        * Description      Default constructor of the Animal class calls  
        *                  the overloaded constructor.
        * @author          <i> Tyler Ingram </i>
        * Date             10/25/2022
        * History log
        * </pre>
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    protected Animal()
    {
        this("","");
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Constructor      Animal
        * Description      Overloaded constructor of the Animal class 
        *                  defines variables. 
        * @param           name String
        * @param           diet String
        * @author          <i> Tyler Ingram </i>
        * Date             10/25/2022
        * History log
        * </pre>
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/    
    protected Animal(String name, String diet)
    {
        this.name = name;
        this.diet = diet;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre> 
     * Method        toString() 
     * Description   Abstract toString method
     * @author       <i>Tyler Ingram</i> 
     * Date          10/25/2022
     * History Log  
    *</pre> 
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    @Override
    public  abstract  String toString();
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
       *<pre>
       * Method         getName
       * Description    Returns the name
       * @return        name String
       * @author        <i>Tyler Ingram</i>
       * Date           10/25/2022
       * History Log   
       *</pre>
       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/    
    public String getName() 
    {
        return name;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
       *<pre>
       * Method         getDiet
       * Description    Returns the diet
       * @return        diet Stromg
       * @author        <i>Tyler Ingram</i>
       * Date           10/26/2022
       * History Log   
       *</pre>
       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public String getDiet() 
    {
        return diet;
    }  
}
