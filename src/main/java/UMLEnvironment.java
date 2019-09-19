/*
 * UMLEnvironment is an object meant to keep track of all the UMLItems that 
 * exist at any given point in the running of the program. The purpose of this 
 * class will be to help define what can and cannot be used during the duration
 * of use of the program. 
 * @author Eric Hinerdeer
 * Date: August 29, 2019
 */

import java.util.List;
import java.util.ArrayList;

public class UMLEnvironment {
    
    List<UMLItem> Items = new ArrayList<UMLItem>();
    int size;
    
    public int getSize() {
    	return Items.size();
    }
    
    public UMLEnvironment() {
        this.size = 0;
    }
    
    public UMLEnvironment(UMLItem item) {
        this.Items.add(item);
        this.size++;
    }
    
    public List<UMLItem> getItems() {
        return this.Items;
    }
    
    public boolean addItem(UMLItem item) {
        if(!this.Items.isEmpty()) {
            for(int i = 0; i < this.Items.size(); i++) {
                if(item.getName().equals(this.Items.get(i).getName())) {
                    return false;
                }
            }
        }
        this.Items.add(item);
        this.size++;
        return true;
    }
    
    public boolean removeItem(UMLItem item) {
        if(!this.Items.isEmpty()) {
            for(int i = 0; i < this.Items.size(); i++) {
            if(item.getId() == this.Items.get(i).getId()) {
                this.Items.remove(i);
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
