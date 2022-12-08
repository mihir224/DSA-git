
import java.util.HashSet;
import java.util.Set;

public class SpaceObject {

    private final String name;
    private final double orbitalPeriod;
    private final Set<SpaceObject> satellites;

    public SpaceObject(String name, double orbitalPeriod) {
        this.name = name;
        this.orbitalPeriod = orbitalPeriod;
        this.satellites=new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }
    public boolean addMoon(SpaceObject moon){
          return this.satellites.add(moon);
    }
    public Set<SpaceObject> getSatellites(){
        return new HashSet<>(this.satellites);
    }
}
