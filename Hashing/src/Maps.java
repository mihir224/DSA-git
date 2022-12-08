import java.util.HashMap;
import java.util.Map;

public class Maps {
    public static void main(String[] args) {
        Map<String, String> map=new HashMap<>();
        System.out.println(map.put("Name", "Mihir")); //logging the put method returns null as we are creating a
        // new reference in the map with the key "name"
        if(map.containsKey("Place")){
            System.out.println("Place already exists");
        }
        else{
            map.put("Place", "Haridwar");
            System.out.println("Place added");
        }
        map.put("Place", "Noida");
        if(map.containsKey("Place")){
            System.out.println("Place already exists");
        }
        else{
            map.put("Place", "Haridwar");
            System.out.println("Place added");
        }
        for(String key: map.keySet()){ //To print all the keys and values of the map
            System.out.println(key + " : " + map.get(key)); //get method returns the value of the specified key
        }
    }
}
