/*
 * UMLItem is a class that represents a single item in the UML Environment.
 * Getters and Setters are created here to define Id, Name, Parent, Children
 * and Attributes for the UMLItem.
 * @author Eric Hinerdeer
 * Date: August 29, 2019
 */


import java.util.List;
import java.util.ArrayList;

public class UMLItem {
    private int id;
    private String name;
    private List<String> attributes = new ArrayList<>();
    private int parent = 0;
    private List<Integer> children = new ArrayList<>();
    
    public UMLItem(int Id, String Name, int Parent, List<String> Attr) {
        this.id = Id;
        this.name = Name;
        this.parent = Parent;
        this.attributes = Attr;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int Id) {
        this.id = Id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String Name) {
        this.name = Name;
    }
    
    public List<String> getAttributes() {
        return this.attributes;
    }
    
    public void addAttribute(String attr) {
        this.attributes.add(attr);
    }
    
    public int getParent() {
        return this.parent;
    }
    
    public void setParent(int newParent) {
        this.parent = newParent;
    }
    
    public List<Integer> getChildren() {
        return this.children;
    }
    
    public void addChild(int newChild) {
        this.children.add(newChild);
    }
    
    public boolean removeChild(int remove) {
        for(int i = 0; i < this.children.size(); i++) {
            if(remove == this.children.get(i)) {
                this.children.remove(i);
                return true;
            }
        }
        return false;
    }
}
