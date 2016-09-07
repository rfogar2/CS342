public class Connection {
    private City destination;
    private int distance;

    /* constructors */

    /*
     * Constructor for class
     * Receives: City destination and distance to that destination
     * Returns: Connection object
    */
    public Connection (City dest, int dist) {
        this.destination = dest;
        this.distance = dist;
    }

    /* getters */

    /*
     * Gets private variable destination
     * Receives: Nothing
     * Returns: City object
    */
    public City getDestination () {
        return this.destination;
    }

    /*
     * Gets private variable distance
     * Receives: Nothing
     * Returns: int distance to destination
    */
    public int getDistance () {
        return this.distance;
    }
}
