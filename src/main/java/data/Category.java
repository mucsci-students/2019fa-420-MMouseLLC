package data;

/**
 * Storage of a Relationship between two UMLItem objects
 * Original intent is used in UMLEnvironment as a list of relationships
 * @author Matt Fossett
 *
 */
public class Category extends ParentChildPair{
    
    private int categories;
    /** Simple String[] of names associated with the type of relationship:
     * 0 => None; 1 => OneToOne; 2 => OneToMany; 3 => ManyToMany; 4 => ManyToMany  **/
    final public String[] categoryTypes = {"None", "Aggregation", "Composition", "Generalization", "Realization"};

    /**
     * Construct new Relationship between parent and child, specifiying a quantifier
     * Quantifiers as strings are OneToOne, OneToMany, ManyToOne, ManyToMany
     * A quantifier out of range [0, quantifierNames.length) will default to 0
     * @param categories 
     */
    public Category(UMLItem p, UMLItem c, int categories){
        super(p, c);
        if (categories < 0 || categories >= categoryTypes.length){
        	categories = 0;
        }
        this.categories = categories;
    }

    /**
     * Construct new Relationship between parent and child, Defaulting to 0 
     *   Note field quantifierNames holds the names of each quantifier, where 0 is OneToOne
     * Quantifiers as strings are OneToOne, OneToMany, ManyToOne, ManyToMany
     */
    public Category(UMLItem p, UMLItem c){
        super(p, c);
        this.categories = 0;
    }
    
  
    
    /**
     * Return the int representation of the contained quantifier
     * @return int quantifier
     */
    public int getCategory() {
    	return categories;
    }
    
    /**
     * Change the quantifier
     * @param newQuantifier
     */
    public void setCategory(int newCategory) {
    	
    	this.categories = newCategory;
    }
    
    /**
     * Return quantifier as its verbose String name
     * @return String of Quantifier names
     */
    public String getCategoryType() {
    	return categoryTypes[categories];
    }
    
}
    
 