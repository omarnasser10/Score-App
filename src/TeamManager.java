import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamManager implements Manager {
    protected static FileHandler fileHandler = new FileHandler("myDirectory");
    private String filename = "Teams.txt";

    public void add(Object item) {
        if (item instanceof Team) {
            System.out.println("Team " + ((Team) item).getTeamName() + " added successfully.");
            fileHandler.appendToFile(filename, String.valueOf(((Team) item)) + "\n");
            Team.saveLastID("lastTeamID.txt");
        } else {
            System.out.println("Only Teams can be added.");
        }
    }

    public boolean isExists(int teamID) {
        List<String> lines = fileHandler.readLines(filename);
        for (String line : lines) {
            if (line.contains("teamID=" + teamID)) {
                return true;  // Return true if customerID exists in the file
            }
        }
        return false;
    }


    public boolean search_by_id(int id) {
        if (fileHandler.searchContent(filename, "teamID=" + id))
            return true;
        else return false;
    }

    public void update(int id, String old, String New) {
        fileHandler.update(filename, "teamID=" + id, old, New);
    }

    public void delete(int id) {
        List<String> S = new FileHandler("myDirectory").readLines("Teams.txt");
        for (String x : S) {
            if (x.contains("" + id))
                fileHandler.deleteLineById(filename, "teamID=" + id);
        }
    }

    public String show_by_id(int id) {
        if (search_by_id(id)) {
            return fileHandler.readLineById(filename, "teamID=" + id);
        } else {
            return "can not found this id";
        }
    }

    public String show_players_of_team(int id) {
        if (!search_by_id(id))
            return "this team id not found";
        List<String> lines = fileHandler.readLines(filename);
        for (String line : lines) {
            if (line.contains("teamID=" + id)) {
                int startIndex = line.indexOf("[") + 1;
                int endIndex = line.indexOf("]");
                if (startIndex > 0 && endIndex > startIndex) {
                    return line.substring(startIndex, endIndex).trim();
                }
            }
        }
        return "";
    }

    public String add_player_to_team(int teamID, int playerID) {
        List<String> lines = fileHandler.readLines(filename);
        String msg = "No updates were made.";

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            // تحقق إذا كانت السطر يحتوي على teamID
            if (line.contains("teamID=" + teamID)) {
                Pattern pattern = Pattern.compile("players\\{\\s*(null|\\[(.*?)\\])\\s*\\}");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String playersList = matcher.group(2) != null ? matcher.group(2).trim() : "";

                    // تحقق إذا كانت القائمة فارغة أو تحتوي على null
                    if (playersList.isEmpty()) {
                        playersList = String.valueOf(playerID);
                    } else {
                        List<String> players = new ArrayList<>(Arrays.asList(playersList.split(", ")));

                        // تحقق إذا كان اللاعب موجودًا بالفعل
                        if (!players.contains(String.valueOf(playerID))) {
                            players.add(String.valueOf(playerID));
                            playersList = String.join(", ", players);
                        } else {
                            return "Player already exists in the list.";
                        }
                    }

                    // تحديث السطر باستخدام `update`
                    String oldLine = line;
                    String newLine = line.replaceAll("players\\{\\s*(null|\\[.*?\\])\\s*\\}", "players{ [" + playersList + "] }");
                    update(teamID, oldLine, newLine);

                    msg = "Player added successfully.";
                    break;
                }
            }
        }

        return msg;
    }


    void modify_Coach_Name(int tid, String coachName) {

        String teamData = show_by_id(tid);

        String updatedData = teamData.replaceFirst("coachName='[^']*'", "coachName='" + coachName + "'");

        update(tid, teamData, updatedData);
    }

    void modify_captain(int tid, int pid) {
        if (show_if_player_exists(tid, pid)) {
            Pattern pattern = Pattern.compile("captain=(\\d+)");
            Matcher matcher = pattern.matcher(show_by_id(tid));

            if (matcher.find()) {
                int currentpid = Integer.parseInt(matcher.group(1));
                update(tid, "captain=" + currentpid, "captain=" + pid);
            }
        } else System.out.println("this player not exist in this team");
    }

    void increase_total_goals(int tid, int n_of_goals) {
        Pattern pattern = Pattern.compile("totalGoals=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(tid));

        if (matcher.find()) {
            int currenttotalGoals = Integer.parseInt(matcher.group(1));
            int result = currenttotalGoals + n_of_goals;
            update(tid, "totalGoals=" + currenttotalGoals, "totalGoals=" + result);
        }
    }

    void increase_total_goals_Conceded(int tid, int n_of_goals) {
        Pattern pattern = Pattern.compile("totalGoalsConceded=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(tid));

        if (matcher.find()) {
            int currenttotalGoalsConceded = Integer.parseInt(matcher.group(1));
            int result = currenttotalGoalsConceded + n_of_goals;
            update(tid, "totalGoalsConceded=" + currenttotalGoalsConceded, "totalGoalsConceded=" + result);
        }
    }


    void increase_wins(int tid) {
        Pattern pattern = Pattern.compile("wins=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(tid));

        if (matcher.find()) {
            int currentwins = Integer.parseInt(matcher.group(1));
            int result = currentwins + 1;
            update(tid, "wins=" + currentwins, "wins=" + result);
            increase_points(tid, 3);
        }
    }


    void increase_losses(int tid) {
        Pattern pattern = Pattern.compile("losses=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(tid));

        if (matcher.find()) {
            int currentlosses = Integer.parseInt(matcher.group(1));
            int result = currentlosses + 1;
            update(tid, "losses=" + currentlosses, "losses=" + result);
        }
    }

    void increase_draws(int tid) {
        Pattern pattern = Pattern.compile("draws=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(tid));

        if (matcher.find()) {
            int currentdraws = Integer.parseInt(matcher.group(1));
            int result = currentdraws + 1;
            update(tid, "draws=" + currentdraws, "draws=" + result);
            increase_points(tid, 1);
        }
    }

    void increase_points(int pid, int points) {
        Pattern pattern = Pattern.compile("points=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(pid));

        if (matcher.find()) {
            int currentpoints = Integer.parseInt(matcher.group(1));
            int result = currentpoints + points;
            update(pid, "points=" + currentpoints, "points=" + result);
        }
    }

    boolean show_if_player_exists(int tid, int pid) {
        Pattern pattern = Pattern.compile("players\\{ \\[(.*?)\\] \\}");
        Matcher matcher = pattern.matcher(show_by_id(tid));

        String currentPlayers = "";
        if (matcher.find())
            currentPlayers = matcher.group(1);

        if (currentPlayers.contains("" + pid))
            return true;
        else return false;

    }

    void removePlayerFromTeam(int teamID, int playerID) {

        Pattern pattern = Pattern.compile("players\\{ \\[(.*?)\\] \\}");
        Matcher matcher = pattern.matcher(show_by_id(teamID));

        if (matcher.find()) {
            String currentPlayers = matcher.group(1);
            List<String> playerList = new ArrayList<>(Arrays.asList(currentPlayers.split(", ")));


            playerList.remove(String.valueOf(playerID));

            String updatedPlayers = String.join(", ", playerList);
            update(teamID, matcher.group(0), "players{ [" + updatedPlayers + "] }");
            new PlayerManager().delete(playerID);
            System.out.println("Player " + playerID + " removed from team " + teamID);
        } else {
            System.out.println("No players found for team " + teamID);
        }
    }

    public Team parseTeam(int tid) {
        Pattern pattern = Pattern.compile(
                "teamID=(\\d+), teamName=(.*?), coachName=(.*?), foundationYear=(\\d+), stadiumName=(.*?), stadiumCapacity=(\\d+), league=(.*?), totalGoals=(\\d+), totalGoalsConceded=(\\d+), wins=(\\d+), draws=(\\d+), losses=(\\d+), points=(\\d+), currentRank=(\\d+), players\\{ \\[(.*?)\\] \\}, captain=(\\d+)"
        );

        Matcher matcher = pattern.matcher(show_by_id(tid));

        if (matcher.find()) {
            int teamID = Integer.parseInt(matcher.group(1));
            String teamName = matcher.group(2);
            String coachName = matcher.group(3);
            int foundationYear = Integer.parseInt(matcher.group(4));
            String stadiumName = matcher.group(5);
            int stadiumCapacity = Integer.parseInt(matcher.group(6));
            String league = matcher.group(7);
            int totalGoals = Integer.parseInt(matcher.group(8));
            int totalGoalsConceded = Integer.parseInt(matcher.group(9));
            int wins = Integer.parseInt(matcher.group(10));
            int draws = Integer.parseInt(matcher.group(11));
            int losses = Integer.parseInt(matcher.group(12));
            int points = Integer.parseInt(matcher.group(13));
            int currentRank = Integer.parseInt(matcher.group(14));
            int captain = Integer.parseInt(matcher.group(16));

            // تحقق من وجود بيانات اللاعبين
                List<Integer> players = new ArrayList<>();
                String[] playerArray = matcher.group(15).split(", ");
                for (String player : playerArray) {
                    if (!player.isEmpty()) {
                        players.add(Integer.parseInt(player.trim()));
                    }
                }
                Team team = new Team(foundationYear, teamID, teamName, coachName, stadiumName, stadiumCapacity, league, totalGoals, totalGoalsConceded, wins, draws, losses, points, currentRank, players, captain);
                return team;
        } else {
            System.out.println("Invalid team data format.");
            return null;
        }
    }


    public int getTeamIDByName(String teamName) {
        Pattern pattern = Pattern.compile(
                "teamID=(\\d+), teamName=" + teamName
        );

        List<String> lines = fileHandler.readLines(filename);
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        }

        System.out.println("Team not found.");
        return -1;
    }











































































}