package data;

import java.util.ArrayList;

/*
 * UMLEnvironment is an object meant to keep track of all the UMLItems that 
 * exist at any given point in the running of the program. The purpose of this 
 * class will be to help define what can and cannot be used during the duration
 * of use of the program. 
 * @author Eric Hinerdeer
 * Date: August 29, 2019
 */
public class UMLEnvironment {
    
    ArrayList<UMLItem> items = new ArrayList<UMLItem>();
    int size;
    
    public int getSize() {
    	return items.size();
    }
    
    public UMLEnvironment() {
        this.size = 0;
    }
    
    public UMLEnvironment(UMLItem item) {
        this.items.add(item);
        this.size++;
    }
    
    public ArrayList<UMLItem> getItems() {
        return this.items;
    }
    
    /**
     * if a UMLItem with name itemName exists in this environment then returns that item
     *   else return null
     * @param itemName
     * @return
     */
    public UMLItem findItem(String itemName){
    		for (UMLItem i : items){
    			if (i.getName().equals(itemName)){
    				return i;
    			}
    		}
    		return null;
    }
    
    public boolean addItem(UMLItem item) {
        if(!this.items.isEmpty()) {
            for(int i = 0; i < this.items.size(); i++) {
                if(item.getName().equals(this.items.get(i).getName())) {
                    return false;
                }
            }
        }
        this.items.add(item);
        this.size++;
        return true;
    }
    
    public boolean removeItem(UMLItem item) {
        if(!this.items.isEmpty()) {
            for(int i = 0; i < this.items.size(); i++) {
            if(item.getId() == this.items.get(i).getId()) {
                this.items.remove(i);
                this.size--;
                return true;
            }
        }
        } else {
            return false;
        }
        return false;
    }
}
