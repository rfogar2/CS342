import java.util.ArrayList;

public class City {
    private String name;
    private ArrayList<Connection> connections;
    private boolean visited;
    private int distance;

    /* constructors */

    public City (String cityName) {
        this.name = cityName;
        this.connections = new ArrayList<Connection>();
        this.visited = false;
        this.distance = 0;
    }

    /* getters */

    public String getName () {
        return this.name;
    }

    public int getDistance () {
        return this.distance;
    }

    public ArrayList<Connection> getConnections () {
        return this.connections;
    }

    public boolean getVisited () {
        return this.visited;
    }

    /* setters */

    public void setVisited (boolean isVisited) {
        this.visited = isVisited;
    }

    public void setDistance (int dist) {
        this.distance = dist;
    }

    public void addConnection (City dest, int distance) {
        Connection conn = new Connection(dest, distance);
        this.connections.add(conn);
    }
}