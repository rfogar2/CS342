import java.util.ArrayList;

public class City {
    private String name;
    private ArrayList<Connection> connections;
    private boolean visited;
    private int distance;

    /* constructors */

    /*
     * Constructr for City
     * Receives: cityName
     * Returns: City object
    */
    public City (String cityName) {
        this.name = cityName;
        this.connections = new ArrayList<Connection>();
        this.visited = false;
        this.distance = 0;
    }

    /* getters */

    /*
     * Gets name for City
     * Receives: Nothing
     * Returns: String of city name with state
    */
    public String getName () {
        return this.name;
    }

    /*
     * Gets distance from source for this city
     * Receives: Nothing
     * Returns: int distance from source of dijkstra
    */
    public int getDistance () {
        return this.distance;
    }

    /*
     * Gets connections between this and other City objects
     * Receives: Nothing
     * Returns: arraylist of Connection objects
    */
    public ArrayList<Connection> getConnections () {
        return this.connections;
    }

    /*
     * Gets if this City has been visited
     * Receives: Nothing
     * Returns: boolean visited
    */
    public boolean getVisited () {
        return this.visited;
    }

    /* setters */

    /*
     * Sets if this City has been visited
     * Receives: isVisited boolean value
     * Returns: Nothing
    */
    public void setVisited (boolean isVisited) {
        this.visited = isVisited;
    }

    /*
     * Sets distance from source for this city
     * Receives: int distance
     * Returns: Nothing
    */
    public void setDistance (int dist) {
        this.distance = dist;
    }

    /*
     * adds a connection from this City to another City
     * Receives: destination City and distance to that City
     * Returns: Nothing
    */
    public void addConnection (City dest, int distance) {
        Connection conn = new Connection(dest, distance);
        this.connections.add(conn);
    }
}
