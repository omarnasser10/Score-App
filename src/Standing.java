import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Standing {
    List<Team> teams = new ArrayList<>();

    public Standing() {
        loadTeams();
    }
    private void loadTeams() {
        teams.clear();
        for (int i = 1; i <= Team.lastTeamID; i++) {
            Team team = new TeamManager().parseTeam(i);
            if (team != null) {
                teams.add(team);
            }
        }
    }

    public void sortTeamsByPoints() {
        loadTeams();
        teams.sort(new PointsComparator());
    }

    public void displayLeagueTable() {
        System.out.println("==============================================================================================");
        System.out.printf("| %-20s | %-6s | %-6s | %-6s | %-6s | %-6s | %-6s | %-6s |%n",
                "Team", "Points", "Wins", "Draws", "Losses", "Total Goals", "Conceded", "GD");
        System.out.println("==============================================================================================");

        for (Team team : teams) {
            int goalDifference = team.getTotalGoals() - team.getTotalGoalsConceded();
            System.out.printf("| %-20s | %-6d | %-6d | %-6d | %-6d | %-12d | %-8d | %-6d |%n",
                    team.getTeamName(), team.getPoints(), team.getWins(), team.getDraws(), team.getLosses(),
                    team.getTotalGoals(), team.getTotalGoalsConceded(), goalDifference);
        }

        System.out.println("==============================================================================================");
    }


}

class PointsComparator implements Comparator<Team> {
    @Override
    public int compare(Team o1, Team o2) {
        int pointsComparison = Integer.compare(o2.getPoints(), o1.getPoints());

        if (pointsComparison != 0) {
            return pointsComparison;
        }

        int goalDifference1 = o1.getTotalGoals() - o1.getTotalGoalsConceded();
        int goalDifference2 = o2.getTotalGoals() - o2.getTotalGoalsConceded();

        return Integer.compare(goalDifference2, goalDifference1);
    }
}

