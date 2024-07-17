import java.util.*;

class Tourist {
    Graph graph;

    Tourist(Graph graph) {
        this.graph = graph;
    }

    public List<String> planRoute(String startIsland) {
        Map<String, Integer> shortestPaths = dijkstra(startIsland); // dijkstra returns the shortest travel path
        List<String> sortedIslands = new ArrayList<>(shortestPaths.keySet()); // Using a List to store the travel time values

        // Sort islands based on shortest path distances
        sortedIslands.sort(Comparator.comparingInt(shortestPaths::get));

        List<String> tourismRoute = new ArrayList<>();
        // These variables keep track of the total time spent throughout the travelled path
        int totalExperienceTime = 0;
        int totalTravelTime = 0;

        // Adding the experiences that are on the starting island
        Island startIslandPos = graph.getIsland(startIsland);
        if (startIslandPos != null) {
            for (Experience experience : startIslandPos.getExperiences()) {
                tourismRoute.add("[" + startIsland + "]: " + experience.getName() + " (Duration: " + experience.getDuration() + ")\n");
                totalExperienceTime += experience.getDuration();
            }
        }

        // Start from the starting island
        String currentIsland = startIsland;

        // Traverse through all islands in the order of shortest paths
        for (String nextIsland : sortedIslands) {
            if (currentIsland.equals(nextIsland)) {
                continue;
            }
            Island currentIslandPos = graph.getIsland(currentIsland);
            Island nextIslandPos = graph.getIsland(nextIsland);

            // If current island and next island are valid
            if (currentIslandPos != null && nextIslandPos != null) {
                // Get the shortest path distance from Dijkstra's result
                int travelTime = shortestPaths.get(nextIsland) - shortestPaths.get(currentIsland);
                totalTravelTime += travelTime;
                tourismRoute.add("From " + currentIsland + " to " + nextIsland + " (Travel Time: " + travelTime + ")\n");

                // Add experiences of the next island
                for (Experience experience : nextIslandPos.getExperiences()) {
                    tourismRoute.add("[" + nextIsland + "]: " + experience.getName() + " (Duration: " + experience.getDuration() + ")\n");
                    totalExperienceTime += experience.getDuration();
                }

                // Move to the next island
                currentIsland = nextIsland;
            }
        }

        // Sum of the total time
        int totalTime = totalExperienceTime + totalTravelTime;
        tourismRoute.add("Total Time: " + totalTime + " (Experiences: " + totalExperienceTime + ", Travel: " + totalTravelTime + ")");

        return tourismRoute;
    }

    private Map<String, Integer> dijkstra(String startIsland) {
        Map<String, Integer> travelPath = new HashMap<>();
        PriorityQueue<Route> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(route -> route.travelTime));
        Set<String> visited = new HashSet<>();

        // Initializing travel times to infinity
        for (Island island : graph.islandList) {
            travelPath.put(island.name, Integer.MAX_VALUE);
        }
        // Setting the starting island travel time to zero
        travelPath.put(startIsland, 0);

        // Add the start island to the priority queue
        priorityQueue.add(new Route(startIsland, startIsland, 0));

        while (!priorityQueue.isEmpty()) {
            Route currentRoute = priorityQueue.poll(); // Route with the lowest travel time is extracted
            String currentIsland = currentRoute.toIsland;

            // Uses the HashSet to see if the island has already been visited
            if (!visited.add(currentIsland)) {
                continue;
            }

            int currentPos = travelPath.get(currentIsland);

            // Looping through all the neighboring islands of the current island position
            for (Route neighbor : graph.getRoutesFromIsland(currentIsland)) {
                String neighborIsland = neighbor.toIsland;
                // New travel path time to the neighboring island
                int newTravelPath = currentPos + neighbor.travelTime;

                // If the new path is shorter, travelPath gets updated
                if (newTravelPath < travelPath.get(neighborIsland)) {
                    travelPath.put(neighborIsland, newTravelPath);
                    priorityQueue.add(new Route(currentIsland, neighborIsland, newTravelPath));
                }
            }
        }

        return travelPath;
    }
}

