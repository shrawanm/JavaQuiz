package javaCoursework;

import java.util.Scanner;
public class Manager {
	 Manager() {
	    // Constructor implementation
	}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CompetitorList competitorList = new CompetitorList();

        // Loading competitors from database
        System.out.println("loading competitors data");
        competitorList.loadFromDatabase();

        // Displaying all competitors
        System.out.println("\ncompetition Report:");
        competitorList.displayAllCompetitors();

        // Displaying the top performer
        Competitor topPerformer = competitorList.getTopPerformer();
        if (topPerformer != null) {
            System.out.println("\nTop Performer: " + topPerformer.getFullDetails());
        } else {
            System.out.println("\nNo competitors found.");
        }

        // Generate and display summary statistics
        String summaryStatistics = competitorList.generateSummaryStatistics();
        System.out.println("\n" + summaryStatistics);

        // Search for competitors by ID
        System.out.println("\nEnter a competitor ID to view details (-1 to exit):");
        while (true) {
            System.out.print("Competitor ID: ");
            int id = scanner.nextInt();

            if (id == -1) {
                System.out.println("Exiting");
                break;
            }

            // Search for the competitor by ID
            Competitor foundCompetitor = competitorList.findCompetitorByID(id);
            if (foundCompetitor != null) {
                System.out.println(foundCompetitor.getShortDetails());
            } else {
                System.out.println("Competitor not found.");
            }
        }
        scanner.close();
    }
}