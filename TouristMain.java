import java.util.List;

public class TouristMain {
    public static void main(String[] args) {
        Graph tourGraph = new Graph();

        // Adding islands
        tourGraph.addIsland("Oahu");
        tourGraph.addIsland("Tahiti");
        tourGraph.addIsland("Samoa");

        // Adding routes
        tourGraph.addRoute("Oahu", "Tahiti", 20);
        tourGraph.addRoute("Samoa", "Tahiti", 5);
        tourGraph.addRoute("Oahu", "Samoa", 10);

        // Adding experiences to islands
        tourGraph.addExperienceToIsland("Oahu", "Snorkeling", 2);
        tourGraph.addExperienceToIsland("Oahu", "Hiking", 3);
        tourGraph.addExperienceToIsland("Tahiti", "Surfing", 4);
        tourGraph.addExperienceToIsland("Samoa", "Sightseeing", 6);

        // Travelling tourist scenario
        Tourist tourist = new Tourist(tourGraph);
        String startingIsland = "Oahu";

        List<String> tourismRoute = tourist.planRoute(startingIsland);
        System.out.println("Tourism Route: Starting Island [" + startingIsland + "]\n" + tourismRoute);
    }
}
