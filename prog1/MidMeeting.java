import java.io.*;
import java.util.*;

public class MidMeeting {
    public static void main (String[] args) throws FileNotFoundException {
        File cn = new File("CityNames.txt");
        File cd = new File("CityDistances.txt");
        File p = new File("participants.txt");

        Scanner cityNames = new Scanner(cn);
        Scanner cityDistances = new Scanner(cd);
        Scanner participants = new Scanner(p);

        ArrayList<City> cities = readCityNames(cityNames);
        addConnections(cities, cityDistances);

        // passes in a copy since sorting by name
        distancesFromChicago(new ArrayList<City>(cities));

        findClosestAverageCity(cities, participants);
    }

    public static ArrayList<City> readCityNames (Scanner cityNames) {
        cityNames.nextLine(); // clear rest of line
        ArrayList<City> cities = new ArrayList<City>();

        while (cityNames.hasNextLine()) {
            City city = new City(cityNames.nextLine());
            cities.add(city);
        }

        return cities;
    }

    public static void addConnections (ArrayList<City> cities, Scanner cityDistances) {
        int numberOfDistances = cityDistances.nextInt();

        while (cityDistances.hasNextInt()) {
            // subtract 1 because city indexing starts at 1
            int cityAId = cityDistances.nextInt() - 1;
            int cityBId = cityDistances.nextInt() - 1;
            int distance = cityDistances.nextInt();

            City cityA = cities.get(cityAId);
            City cityB = cities.get(cityBId);

            cityA.addConnection(cityB, distance);
            cityB.addConnection(cityA, distance);
        }
    }

    public static void distancesFromChicago (ArrayList<City> cities) {
        City chicago = cities.get(57);
        dijkstra(cities, chicago);

        Collections.sort(cities, new Comparator<City>() {
           public int compare(City c1, City c2) {
               return c1.getName().compareTo(c2.getName());
           }
        });

        for (City dest : cities) {
            System.out.println("Shortest path from " + chicago.getName() +
                    " to " + dest.getName() + " is " + dest.getDistance() + " units.");
        }
    }

    public static void dijkstra (ArrayList<City> cities, City source) {
        PriorityQueue<City> unvisited = new PriorityQueue<City>(cities.size(), new Comparator<City>() {
            public int compare(City c1, City c2) {
                return c1.getDistance() - c2.getDistance();
            }
        });

        for (City city : cities) {
            if (city == source) {
                city.setDistance(0);
            } else {
                city.setDistance(Integer.MAX_VALUE);
            }
            city.setVisited(false);
            unvisited.add(city);
        }

        dijkstraTravel(unvisited, source);
    }

    public static void dijkstraTravel (PriorityQueue<City> unvisited, City source) {
        while (unvisited.size() != 0) {
            source.setVisited(true);

            for (Connection conn : source.getConnections()) {
                City c = conn.getDestination();
                int dist = source.getDistance() + conn.getDistance();
                if (!c.getVisited() && dist < c.getDistance()) {
                    // remove and re-add from priority queue after updating distance
                    unvisited.remove(c);
                    c.setDistance(dist);
                    unvisited.add(c);
                }
            }

            source = unvisited.poll();
        }
    }

    public static void findClosestAverageCity (ArrayList<City> cities, Scanner participants) {
        ArrayList<City> participantCities = new ArrayList<City>();

        participants.nextLine(); // skip first line

        while (participants.hasNextLine()) {
            String split_line[] = participants.nextLine().split(" ");
            int cityId = Integer.parseInt(split_line[1]);

            City participantCity = cities.get(cityId - 1);
            participantCities.add(participantCity);
        }

        City bestCity = null;
        float lowestAverageDistance = (float)Integer.MAX_VALUE;

        for (City source : cities) {
            int distanceForAll = 0;
            dijkstra(cities, source);

            for (City dest : participantCities) {
                distanceForAll += dest.getDistance();
            }

            float averageDistance = (float)distanceForAll / participantCities.size();

            if (averageDistance < lowestAverageDistance) {
                lowestAverageDistance = averageDistance;
                bestCity = source;
            }
        }

        System.out.println("City with smallest average distance is " + bestCity.getName() +
                " with an average distance of " + lowestAverageDistance);
    }
}
