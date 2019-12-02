package data;

/**
 * Storage of a Category between two UMLItem objects
 * Original intent is used in UMLEnvironment as a list of relationships
 * modified to be used for categories
 * @author Matt Fossett
 * @modified by Kasey Kocher
 *
 */
public class Category extends ParentChildPair{
    
    private int categories;
    /** Simple String[] of names associated with the type of category:
     * 0 => None; 1 => A; 2 => C; 3 => G; 4 => R  **/
    final public String[] categoryTypes = {"None", "Aggregation", "Composition", "Generalization", "Realization"};

    /**
     * Construct new Category between parent and child, specifiying a certain category
     * Categories as strings are OneToOne, OneToMany, ManyToOne, ManyToMany
     * A category out of range [0, categoryTypes.length) will default to 0
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
     * Construct new Category between parent and child, Defaulting to 0 
     *   Note field categoryTypes holds the names of each category, where 0 is None
     * Quantifiers as strings are Aggregation, Composition, Generalization, Realization
     */
    public Category(UMLItem p, UMLItem c){
        super(p, c);
        this.categories = 0;
    }
    
  
    
    /**
     * Return the int representation of the contained category
     * @return int categories
     */
    public int getCategory() {
    	return categories;
    }
    
    /**
     * Change the category
     * @param newCategory
     */
    public void setCategory(int newCategory) {
    	
    	this.categories = newCategory;
    }
    
    /**
     * Return category as its verbose String name
     * @return String of Category names
     */
    public String getCategoryType() {
    	return categoryTypes[categories];
    }
    
}
    
 