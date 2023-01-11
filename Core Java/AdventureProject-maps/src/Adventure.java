import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Adventure {
    private static Map<Integer,Location> locations=new HashMap<Integer, Location>();

    public Adventure(Map<Integer, Location> locations) {
        this.locations = locations;
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        locations.put(0,new Location(0,"Living Room"));
        locations.put(1,new Location(1,"Bed Room"));
        locations.put(2,new Location(2,"Master Bed Room"));
        locations.put(3,new Location(3,"Bathroom"));
        locations.put(4,new Location(4,"Balcony"));
        locations.put(5,new Location(5,"Terrace"));
        locations.get(1).addExits("W",2);
        locations.get(1).addExits("E",3);
        locations.get(1).addExits("S",4);
        locations.get(1).addExits("N",5);
        locations.get(1).addExits("Q",0); //quits the game

        locations.get(2).addExits("N",5); //only one direction to go at loc 2
        locations.get(2).addExits("Q",0);

        locations.get(3).addExits("W",2); //only one direction to go at loc 2
        locations.get(3).addExits("Q",0);

        locations.get(4).addExits("N",5); //only one direction to go at loc 2
        locations.get(4).addExits("W",2);
        locations.get(4).addExits("Q",0);//only one direction to go at loc 2

        locations.get(5).addExits("S",4);
        locations.get(5).addExits("W",2); //only one direction to go at loc 2
        locations.get(5).addExits("Q",0);


        int loc=1;
        while(true){
            Map<String, String> vocabulary=new HashMap<String, String>();
            vocabulary.put("QUIT","Q");
            vocabulary.put("NORTH","N");
            vocabulary.put("WEST","W");
            vocabulary.put("EAST","E");
            vocabulary.put("SOUTH","S");

            System.out.println(locations.get(loc).getDescription());
            Map<String, Integer> exits=locations.get(loc).getExits();
            if(loc==0){
                System.out.println("Quitting...");
                break;
            }
            System.out.println("You can either quit or go in any one of the available directions:");
            for(String exit:exits.keySet())
            {
                System.out.print(exit + " ");
            }
            System.out.println();
            System.out.println("What do you want to do?");
            String direction=scanner.nextLine().toUpperCase();
            if(direction.length()>1){
                String[] str=direction.split(" ");
                for(String word:str){
                    if(vocabulary.containsKey(word)){
                        direction=vocabulary.get(word);
                        break;
                    }
                }
            }
            if(exits.containsKey(direction)){
                loc=exits.get(direction);
            }
            else{
                System.out.println("Can't find a way there");
            }
        }


    }
}
