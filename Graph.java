import java.util.*;

class Graph {
    List<Island> islandList;
    List<List<Route>> adjacencyList;

    Graph() {
        islandList = new ArrayList<>();
        adjacencyList = new ArrayList<>();
    }

    void addIsland(String islandName) {
        if (getIsland(islandName) == null) {
            islandList.add(new Island(islandName));
            adjacencyList.add(new ArrayList<>()); // Initialize adjacency list for the new island
        }
    }

    void addRoute(String fromIsland, String toIsland, int travelTime) {
        int fromIndex = getIslandIndex(fromIsland);
        int toIndex = getIslandIndex(toIsland);

        if (fromIndex != -1 && toIndex != -1) {
            adjacencyList.get(fromIndex).add(new Route(fromIsland, toIsland, travelTime));
        }
    }

    void addExperienceToIsland(String islandName, String experienceName, int duration) {
        Island requestedIsland = getIsland(islandName);
        if (requestedIsland != null) {
            requestedIsland.addExperience(experienceName, duration);
        }
    }

    Island getIsland(String islandName) {
        for (Island island : islandList) {
            if (island.name.equals(islandName)) {
                return island;
            }
        }
        return null;
    }

    int getIslandIndex(String islandName) {
        for (int i = 0; i < islandList.size(); i++) {
            if (islandList.get(i).name.equals(islandName)) {
                return i;
            }
        }
        return -1;
    }

    List<Route> getRoutesFromIsland(String islandName) {
        int index = getIslandIndex(islandName);
        if (index != -1) {
            return adjacencyList.get(index);
        }
        return new ArrayList<>(); // Returns an empty list if island not found
    }
}

class Route {
    String fromIsland;
    String toIsland;
    int travelTime;

    Route(String fromIsland, String toIsland, int travelTime) {
        this.fromIsland = fromIsland;
        this.toIsland = toIsland;
        this.travelTime = travelTime;
    }
}

class Island {
    String name;
    List<Experience> experiences;

    Island(String name) {
        this.name = name;
        this.experiences = new ArrayList<>();
    }

    void addExperience(String experienceName, int duration) {
        experiences.add(new Experience(experienceName, duration));
    }

    List<Experience> getExperiences() {
        return experiences;
    }
}

class Experience {
    String name;
    int duration;

    Experience(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}
