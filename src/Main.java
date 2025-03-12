import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int mainChoice;
        while (true) {
            // Main Menu
            System.out.println("🏆 Welcome to ScoreApp to manage Egyptian League 🏆");
            System.out.println("1. Manage Teams");
            System.out.println("2. Manage Players");
            System.out.println("3. Manage Matches");
            System.out.println("4. View League Table");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            mainChoice = scanner.nextInt();

            switch (mainChoice) {
                case 1:
                    manageTeams(scanner);
                    break;
                case 2:
                    managePlayers(scanner);
                    break;
                case 3:
                    manageMatches(scanner);
                    break;
                case 4:
                    viewLeagueTable();
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye! ⚽");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // ⚽ Manage Teams Menu
    public static void manageTeams(Scanner scanner) {
        int teamChoice;

        while (true) {
            System.out.println("⚽ Manage Teams");
            System.out.println("1. Add Team");
            System.out.println("2. View Teams");
            System.out.println("3. View players of the Team");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            teamChoice = scanner.nextInt();

            switch (teamChoice) {
                case 1:
                    addTeam(scanner);
                    break;
                case 2:
                    viewTeams();
                    break;
                case 3:
                    viewPlayerOfTeam(scanner);
                    break;
                case 4:
                    return; // Back to Main Menu
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void addTeam(Scanner scanner) {
        System.out.println("🆕 Add a New Team");

        System.out.print("Enter Foundation Year: ");
        int foundationYear = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Team Name: ");
        String teamName = scanner.nextLine();

        System.out.print("Enter Stadium Name: ");
        String stadiumName = scanner.nextLine();

        System.out.print("Enter Stadium Capacity: ");
        int stadiumCapacity = scanner.nextInt();

        Team newTeam = new Team(foundationYear, teamName, stadiumName, stadiumCapacity);
        new TeamManager().add(newTeam);

        System.out.println("✅ Team added successfully!");
    }

    // 📋 View Teams Method
    public static void viewTeams() {
        Standing list = new Standing();
        if (list.teams.isEmpty()) {
            System.out.println("❌ No teams available.");
        } else {
            System.out.println("📋 List of Teams:");
            for (Team team : list.teams) {
                System.out.println("-------------------------------------");
                System.out.println("Team Name: " + team.getTeamName());
                System.out.println("-------------------------------------");
            }
        }
    }


    public static void viewPlayerOfTeam(Scanner scanner) {

        System.out.println("Choose a team:");
        Standing list = new Standing();
        for (Team team : list.teams) {
            System.out.println("-------------------------------------");
            System.out.println("Team Name: " + team.getTeamName());
            System.out.println("-------------------------------------");
        }

        System.out.print("Enter the name of the team: ");
        scanner.nextLine();

        String teamChoice = scanner.nextLine();

        Team team = new TeamManager().parseTeam(new TeamManager().getTeamIDByName(teamChoice));

        if (team != null) {
            List<Integer> players = team.getPlayers();
            if (players.isEmpty()) {
                System.out.println("No players found for this team.");
            } else {
                for (int id : players) {
                    Player p = new PlayerManager().parsePlayer(id);
                    if (p != null) {
                        System.out.println("-------------------------------------");
                        System.out.println("Player Name: " + p.getName());
                        System.out.println("-------------------------------------");
                    } else {
                        System.out.println("Player with ID " + id + " not found.");
                    }
                }
            }
        } else {
            System.out.println("Team not found.");
        }
    }


    // ⚽ Manage Players Menu
    public static void managePlayers(Scanner scanner) {
        int playerChoice;

        while (true) {
            System.out.println("⚽ Manage Players");
            System.out.println("1. Add Player to team");
            System.out.println("2. View Player");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            playerChoice = scanner.nextInt();

            switch (playerChoice) {
                case 1:
                    addPlayerToTeam(scanner);
                    break;
                case 2:
                    viewPlayer(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void addPlayerToTeam(Scanner scanner) {
        System.out.println("🆕 Add a New Player");

        System.out.print("Enter player's age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter player's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter player's nationality: ");
        String nationality = scanner.nextLine();

        System.out.print("Enter player's position: ");
        String position = scanner.nextLine();

        System.out.print("Enter player's shirt number: ");
        int shirtNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Is the player injured? (true/false): ");
        boolean injury = scanner.nextBoolean();
        scanner.nextLine();

        System.out.print("Enter player's market value: ");
        double marketValue = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Choose a team to assign the player:");
        Standing list = new Standing();
        for (Team team : list.teams) {
            System.out.println("-------------------------------------");
            System.out.println("Team Name: " + team.getTeamName());
            System.out.println("-------------------------------------");
        }

        System.out.print("Enter the name of the team: ");
        String teamChoice = scanner.nextLine();

        Team team = new TeamManager().parseTeam(new TeamManager().getTeamIDByName(teamChoice));

        Player newPlayer = new Player(name, age, nationality, position, team, shirtNumber, injury, marketValue);
        new PlayerManager().add(newPlayer);

        System.out.println("✅ Player added successfully!");
    }

    public static void viewPlayer(Scanner scanner) {

        System.out.print("Enter player ID: ");
        int playerId = scanner.nextInt();
        scanner.nextLine();

        Player player = new PlayerManager().parsePlayer(playerId);

        if (player != null) {
            System.out.println("-------------------------------------");
            System.out.println("Player Name: " + player.getName());
            System.out.println("Age: " + player.getAge());
            System.out.println("Position: " + player.getPosition());
            System.out.println("Shirt Number: " + player.getShirtNumber());
            System.out.println("Market Value: " + player.getMarketValue());
            System.out.println("Injury: " + (player.isInjury() ? "Yes" : "No"));
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Player not found with ID: " + playerId);
        }
    }


    // ⚽ Manage Matches Menu
    public static void manageMatches(Scanner scanner) {
        int matchChoice;

        while (true) {
            System.out.println("⚽ Manage Matches");
            System.out.println("1. Schedule a Match");
            System.out.println("2. View Matches");
            System.out.println("3. Record Match Result");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            matchChoice = scanner.nextInt();

            switch (matchChoice) {
                case 1:
                    Scheduleamatch(scanner);
                    break;
                case 2:
                    viewMatches(scanner);
                    break;
                case 3:
                    recordmatchresult(scanner);
                    break;
                case 4:
                    return; // Back to Main Menu
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void Scheduleamatch(Scanner scanner) {
        System.out.println("🆕 Schedule a New Match");

        System.out.print("Enter stadium name: ");
        scanner.nextLine();
        String stadiumName = scanner.nextLine();

        System.out.print("Enter home team name: ");
        String homeTeam = scanner.nextLine();

        System.out.print("Enter away team name: ");
        String awayTeam = scanner.nextLine();

        System.out.print("Enter match date (yyyy-mm-dd): ");
        String date = scanner.nextLine();

        System.out.print("Enter match time (hh:mm): ");
        String time = scanner.nextLine();

        Match newMatch = new Match(stadiumName, homeTeam, awayTeam, date, time);

        new MatchManager().add(newMatch);

        System.out.println("✅ Match scheduled successfully!");
    }

    public static void viewMatches(Scanner scanner) {
        for (int i = 1; i <= Match.lastMatchID; i++) {
            Match m = new MatchManager().parseMatch(i);
            System.out.println(m);
        }
    }

    public static void recordmatchresult(Scanner scanner) {
        System.out.println("📝 Record Match Result");

        System.out.print("Enter match ID: ");
        int matchID = scanner.nextInt();

        System.out.print("Enter home team goals: ");
        int homeGoals = scanner.nextInt();

        System.out.print("Enter away team goals: ");
        int awayGoals = scanner.nextInt();

        new MatchManager().set_home_away_goals(matchID, homeGoals, awayGoals);
    }

    // ⚽ View League Table
    public static void viewLeagueTable() {
        System.out.println("Viewing the league table...");
        Standing standing = new Standing();
        standing.sortTeamsByPoints();
        standing.displayLeagueTable();
    }
}