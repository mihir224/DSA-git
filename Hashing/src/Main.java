import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {
    private static HashMap<String, SpaceObject> solarSystem=new HashMap<>();
    private static HashSet<SpaceObject> planets=new HashSet<>();
    public static void main(String[] args) {
        SpaceObject obj=new SpaceObject("Mercury", 98);
        solarSystem.put(obj.getName(), obj);
        planets.add(obj);

        obj=new SpaceObject("Venus",11);
        solarSystem.put(obj.getName(), obj);
        planets.add(obj);

        obj=new SpaceObject("Earth", 45);
        solarSystem.put(obj.getName(), obj);
        planets.add(obj);

        SpaceObject moon=new SpaceObject("Moon", 12);
        solarSystem.put(obj.getName(), obj);
        obj.addMoon(moon);

        obj=new SpaceObject("Mars", 86);
        solarSystem.put(obj.getName(), obj);
        planets.add(obj);

        moon=new SpaceObject("Euphoria", 64);
        solarSystem.put(moon.getName(), moon);
        obj.addMoon(moon);

        moon=new SpaceObject("Nebula", 66);
        solarSystem.put(moon.getName(), moon);
        obj.addMoon(moon);

        System.out.println("-----Planets-----");
        for(SpaceObject planet: planets){
            System.out.println(planet.getName());
        }
        SpaceObject body=solarSystem.get("Mars");
        System.out.println("-----Moons of " + body.getName() + "----- ");
        for(SpaceObject sc:body.getSatellites()){
            System.out.println(sc.getName());
        }

        //union of two sets
        Set<SpaceObject> unionOfMoons=new HashSet<>();
        for(SpaceObject planet:planets){
            unionOfMoons.addAll(planet.getSatellites());
        }
        System.out.println("-----All Moons-----");
        for(SpaceObject union:unionOfMoons){
            System.out.println(union.getName());
        }

    }
}
