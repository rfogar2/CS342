public class Connection {
    private City destination;
    private int distance;

    /* constructors */

    public Connection (City dest, int dist) {
        this.destination = dest;
        this.distance = dist;
    }

    /* getters */

    public City getDestination () {
        return this.destination;
    }

    public int getDistance () {
        return this.distance;
    }
}