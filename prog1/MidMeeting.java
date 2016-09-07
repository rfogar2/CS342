/*
 * -------------------------------------------------
 * MidMeeting program to find shortest average distance
 * for given participants in a given graph
 *
 * Class: CS 342, Fall 2016
 * System: OS X, Atom, Terminal
 * Author Code Number: 1675F
 * TA: Nianzu Ma
 * -------------------------------------------------
 */

import java.io.*;
import java.util.*;

public class MidMeeting {
    /*
     * Main function for running program
     * Receives: Nothing
     * Returns: Nothing
    */
    public static void main (String[] args) throws FileNotFoundException {
        System.out.println("Author Code Number: 1675F\n" +
                           "Class: CS 342, Fall 2016\n" +
                           "Program: #1, MidMeeting\n" +
                           "TA: Nianzu Ma");

        File cn = new File("CityNames.txt");
        File cd = new File("CityDistances.txt");
        File p = new File("participants.txt");

        Scanner cityNames = new Scanner(cn);
        Scanner cityDistances = new Scanner(cd);
        Scanner participants = new Scanner(p);

        ArrayList<City> cities = readCityNames(cityNames);
        addConnections(cities, cityDistances);

        findClosestAverageCity(cities, participants);
    }

    /*
     * Reads city names from CityNames.txt
     * Receives: Java.util.Scanner of city names file
     * Returns: returns an ArrayList of City objects
    */
    public static ArrayList<City> readCityNames (Scanner cityNames) {
        cityNames.nextLine(); // clear rest of line
        ArrayList<City> cities = new ArrayList<City>();

        while (cityNames.hasNextLine()) {
            City city = new City(cityNames.nextLine());
            cities.add(city);
        }

        return cities;
    }

    /*
     * Adds connections by reading CityDistances.txt file
     * Receives: ArrayList of City objects created in readCityNames, cityDistances Scanner
     * Returns: Nothing, mutates existing cities
    */
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

    /*
     * Sets up modified Dijkstra algorithm for finding distance from source
     *  to all other nodes
     * Receives: Cities read in from text files, source city to start at
     * Returns: Nothing
    */
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

    /*
     * Does the work for modified Dijkstra algorithm
     * Receives: Unvisited cities and source
     * Returns: Nothing, mutates distance in City objects
    */
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

    /*
     * Finds closest average city for given participants
     * Receives: Cities and participants
     * Returns: Nothing, prints out results
    */
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
