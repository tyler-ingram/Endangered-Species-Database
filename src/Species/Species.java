package Species;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
 * Class            Species
 * File             Species.java
 * Description      Species class representing a Species extends the Animal class 
 *                  and implements the Comparable interface
 * @author          <i>Tyler Ingram </i>
 * Environment      PC, Windows 10, NetBeans IDE 11.3, jdk 1.8.0_241
 * Date             10/25/2022
 * @version         1.2.0
 * History Log  
*</pre>
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Species extends Animal implements Comparable
{
    private String genus;
    private int population;
    private String habitat;
    private String predators;
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Constructor      Species
        * Description      Default constructor of the Species class calls  
        *                  the overloaded constructor.
        * @author          <i> Tyler Ingram </i>
        * Date             10/25/2022
        * History log
        * </pre>
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Species()
    {
        this("","","",0,"","");
        
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Constructor      Species
        * Description      Overloaded constructor of the Species class with     
        *                  passed parameters.
        * @param           name String
        * @param           diet String
        * @param           genus String
        * @param           population int
        * @param           habitat String
        * @param           predators String
        * @author          <i> Tyler Ingram </i>
        * Date             10/25/2022
        * History log
        * </pre>
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Species(String name, String diet, String genus, int population, 
            String habitat, String predators)
    {
        this.name = name;
        this.diet = diet;
        this.genus = genus;
        this.population = population;
        this.habitat = habitat;
        this.predators = predators;
    }
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            * <pre>
            * Constructor      Species
            * Description      Overloaded constructor of the species class. 
            *                  Accepts another species as arguement. 
            * @param           anotherSpecies Species
            * @author          <i> Tyler Ingram </i>
            * Date             10/25/2022
            * History log
            * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public Species(Species anotherSpecies)
    {
                name = anotherSpecies.name;
                diet = anotherSpecies.diet;
                genus = anotherSpecies.genus;
                population = anotherSpecies.population;
                habitat = anotherSpecies.habitat;
                predators = anotherSpecies.predators;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        getGenus
        * Description   Returns the genus
        * @return       genus String
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/    
    public String getGenus() {
        return genus;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        setGenus
        * Description   Assigns the genus
        * @param        genus String
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setGenus(String genus) {
        this.genus = genus;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        getPopulation
        * Description   Returns the population
        * @return       population String
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int getPopulation() {
        return population;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        setPopulation
        * Description   Assigns the population
        * @param        population int
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setPopulation(int population) {
        this.population = population;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        getHabitat
        * Description   Returns the habitat
        * @return       habitat String
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public String getHabitat() {
        return habitat;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        setHabitat
        * Description   Assigns the habitat
        * @param        habitat String
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        getPredators
        * Description   Returns the predators
        * @return       predators String
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public String getPredators() {
        return predators;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        setPredators
        * Description   Assigns the predators
        * @param        predators String
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setPredators(String predators) {
        this.predators = predators;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        setName
        * Description   Assigns the name
        * @param        name String
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setName(String name) {
        this.name = name;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        * <pre>
        * Method        setDiet
        * Description   Assigns the diet
        * @param        diet String
        * @author       <i> Tyler Ingram </i>
        * Date          10/25/2022
        * History log
        * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setDiet(String diet) {
        this.diet = diet;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        *<pre> 
            * Method           compareTo
            * Description      Overriden method of the Comparable interface to 
            *                  compare two species based on their names and if 
            *                  their names are equal compare based on their genus. 
            * @return          int 
            * @param           obj object
            * @author          <i>Tyler Ingram</i>
            * Date             10/25/2022
            * History log   
        </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/        
        @Override
        public int compareTo(Object obj)
        {
            Species s = new Species((Species)obj);
            if((this.getName().equals(s.getName()))) 
                return this.getGenus().compareTo(s.genus);
            else 
                return this.getName().compareTo(s.getName());
        }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        *<pre> 
            * Method           equals
            * Description      Equals method to test if two species are equal
            *                  they are considered equal if their names and     
            *                  genus are equal.
            * @return          boolean 
            * @param           s Species
            * @author          <i>Tyler Ingram</i>
            * Date             10/25/2022
            * History log   
        </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
        public boolean equals(Species s)
        {
            return((this.getName().equals(s.getName())) && 
                    (this.getGenus().equals(s.getGenus())));
        }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
    *<pre> 
     * Method        toString() 
     * Description   Overriden toString method.Returns all fields of the Species.
     * @author       <i>Tyler Ingram</i> 
     * @return       String 
     * Date          10/25/2022
     * History Log  
    *</pre> 
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/    
    @Override 
    public String toString()
    {
        return "Name: " + this.getName()+" Diet: " + this.getDiet() + " Genus: " 
                + this.getGenus() + " Population: "+ this.getPopulation()  +
                " Habitat: " + this.getHabitat() + "Predators: " + 
                this.getPredators();
    }
}
