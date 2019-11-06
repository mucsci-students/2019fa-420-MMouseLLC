package data;

public class Relationship extends ParentChildPair{
    
    private int quantifier;
    /** Simple String[] of names associated with the type of relationship 
     * 0 => OneToOne; 1 => OneToMany; 2 => ManyToMany; 3=> ManyToMany  **/
    final public String[] quantifierNames = {"OneToOne, OneToMany, ManyToOne, ManyToMany"};

    /**
     * Construct new Relationship between parent and child, specifiying a quantifier
     * Quantifiers as strings are OneToOne, OneToMany, ManyToOne, ManyToMany
     * A quantifier out of range [0, quantifierNames.length) will default to 0
     */
    public Relationship(UMLItem p, UMLItem c, int quantifier){
        super(p, c);
        if (quantifier < 0 || quantifier >= quantifierNames.length){
            quantifier = 0;
        }
        this.quantifier = quantifier;
    }

    /**
     * Construct new Relationship between parent and child, Defaulting to 0 
     *   Note field quantifierNames holds the names of each quantifier, where 0 is OneToOne
     * Quantifiers as strings are OneToOne, OneToMany, ManyToOne, ManyToMany
     */
    public Relationship(UMLItem p, UMLItem c){
        super(p, c);
        this.quantifier = 0;
    }
    
    /**
     * Return the int representation of the contained quantifier
     * @return
     */
    public int getQuantifier() {
    	return quantifier;
    }
    
    /**
     * Return quantifier as its verbose String name
     * @return
     */
    public String getQuantifierName() {
    	return quantifierNames[quantifier];
    }
}