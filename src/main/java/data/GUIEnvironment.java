package data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import utility.GUITile;

/**
 * 
 * @author Matt Fossett
 * Encapsulates UMLEnvironment and also stores additional fields for holding Arrow and tileMapping information
 * This is to keep view and model separate
 */
public class GUIEnvironment extends UMLEnvironment {

	
	 /** Tile is a map for the datastructure UMLItem and its visual representation, a Tile **/
	private Map<UMLItem, GUITile> tileMapping;
	
	/** Store the visual representation of an arrow between 2 UMLItems denoted as Parent/Child Pair **/
	private Map<ParentChildPair, Arrow> arrowMapping;

	/**
	 * Default init gui environment, sets up empty environment
	 */
	public GUIEnvironment() {
		super();
		this.tileMapping = new HashMap<UMLItem, GUITile>();
		this.arrowMapping = new HashMap<ParentChildPair, Arrow>();
	}

	/**
	 * initialize gui environment starting with one class, setup by a UMLItem item
	 * @param item
	 */
	public GUIEnvironment(UMLItem item) {
		this();
		items.add(item);
	}

	/**
	 * adds key value UMLItem item => GUITile tile to the tileMapping member
	 * @param item
	 * @param tile
	 */
	public void createMappingFor(UMLItem item, GUITile tile) {
		tileMapping.put(item, tile);
	}

	/**
	 * Returns value for given key item, null if key item not in map
	 * @param item
	 * @return UMLItem
	 */
	public GUITile getTileFor(UMLItem item) {
		return tileMapping.get(item);
	}

	/**
	 * removes mapping of key item from tileMapping member and env
	 */
	public void removeItemGUI(UMLItem item) {
		//boolean result = this.items.remove(item);
		this.tileMapping.remove(item);
		for(UMLItem child : item.getChildren()) {
			this.arrowMapping.remove(new ParentChildPair(item , child));
		}
		for(UMLItem parent : item.getParents()) {
			this.arrowMapping.remove(new ParentChildPair(parent , item));
		}
	}

	/**
	 * Adds an Arrow to the arrowMapping member based on a parent child pair
	 * @param parent
	 * @param child
	 * @param arr
	 */
	public void addArrow(UMLItem parent, UMLItem child, Arrow arr) {
		arrowMapping.put(new ParentChildPair(parent, child), arr);
	}
	
	/**
	 * Given a UMLItem parent and child, remove the corresponding arrow in the GUI environment
	 * @param parent
	 * @param child
	 */
	public void removeArrow(UMLItem parent, UMLItem child) {
		arrowMapping.remove(new ParentChildPair(parent, child));
	}
	
	/**
	 * Given ParentChild UMLItem pair, replace the existing arrow with newArrow
	 * @param pair
	 * @param newArrow
	 */
	public void replaceArrow(ParentChildPair pair, Arrow newArrow) {
		arrowMapping.replace(pair, newArrow);
	}

	/**
	 * Filters all managed arrow relationships given a UMLItem
	 * @param item
	 * @return Hashmap of arrow relationship
	 */
	public HashMap<ParentChildPair, Arrow> getRelationshipsFor(UMLItem item) {
		return (HashMap<ParentChildPair, Arrow>) arrowMapping.entrySet().stream()
				.filter(pair -> (pair.getKey().getChild() == item) || (pair.getKey().getParent() == item))
				.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
	}
}
