import java.util.HashMap;
import java.util.Map;

public class Location {
    private final int locationID;
    private final String description;
    private final Map<String, Integer> exits;

    public Location(int locationID, String description) {
        this.locationID = locationID;
        this.description = description;
        this.exits=new HashMap<String, Integer>();
    }

    public int getLocationID() {
        return locationID;
    }

    public void addExits(String direction, int key){
        exits.put(direction, key);
    }
    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getExits() {
        return new HashMap<String, Integer>(exits); //returns a new hashmap with the copy of the entries of the exit map
        //this is done so that the object calling this function doesn't have access to the original hashmap
    }
}
