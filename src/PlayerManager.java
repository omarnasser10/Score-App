import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerManager implements Manager {
    protected static FileHandler fileHandler = new FileHandler("myDirectory");
    private String filename = "Players.txt";

    public void add(Object item) {
        if (item instanceof Player) {
            System.out.println("Player " + ((Player) item).getName() + " added successfully.");
            fileHandler.appendToFile(filename, String.valueOf(((Player) item)) + "\n");
            System.out.println(new TeamManager().add_player_to_team(((Player) item).getTeam().getTeamID(), ((Player) item).getPlayerID()));
            Player.saveLastID("lastPlayerID.txt");
        } else {
            System.out.println("Only Players can be added.");
        }
    }

    public boolean isExists(int playerID) {
        List<String> lines = fileHandler.readLines(filename);
        for (String line : lines) {
            if (line.contains("playerID=" + playerID)) {
                return true;
            }
        }
        return false;
    }


    public boolean search_by_id(int id) {
        if (fileHandler.searchContent(filename, "playerID=" + id))
            return true;
        else return false;
    }

    public void update(int id, String old, String New) {
        fileHandler.update(filename, "playerID=" + id, old, New);
    }

    public void delete(int id) {
        List<String> S = new FileHandler("myDirectory").readLines("Players.txt");
        for (String x : S) {
            if (x.contains("" + id))
                fileHandler.deleteLineById(filename, "playerID=" + id);
        }
    }

    public String show_by_id(int id) {
        if (search_by_id(id)) {
            return fileHandler.readLineById(filename, "playerID=" + id);
        } else {
            return "can not found this id";
        }
    }

    void increase_goals(int pid, int n_of_goals) {
        Pattern pattern = Pattern.compile("goals=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(pid));

        if (matcher.find()) {
            int currentGoals = Integer.parseInt(matcher.group(1));
            int result = currentGoals + n_of_goals;
            update(pid, "goals=" + currentGoals, "goals=" + result);
        }
    }

    void increase_assists(int pid, int n_of_assists) {
        Pattern pattern = Pattern.compile("assists=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(pid));

        if (matcher.find()) {
            int currentassists = Integer.parseInt(matcher.group(1));
            int result = currentassists + n_of_assists;
            update(pid, "assists=" + currentassists, "assists=" + result);
        }
    }

    void increase_yellow_card(int pid, int n_of_goals) {
        Pattern pattern = Pattern.compile("yellowCards=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(pid));

        if (matcher.find()) {
            int currentyc = Integer.parseInt(matcher.group(1));
            int result = currentyc + n_of_goals;
            update(pid, "yellowCards=" + currentyc, "yellowCards=" + result);
        }
    }

    void increase_red_card(int pid, int n_of_assists) {
        Pattern pattern = Pattern.compile("redCards=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(pid));

        if (matcher.find()) {
            int currentrc = Integer.parseInt(matcher.group(1));
            int result = currentrc + n_of_assists;
            update(pid, "redCards=" + currentrc, "redCards=" + result);
        }
    }

    void Modify_market_value(int pid, Double new_market_value) {
        Pattern pattern = Pattern.compile("marketValue=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(pid));

        if (matcher.find()) {
            int currentvalue = Integer.parseInt(matcher.group(1));
            update(pid, "marketValue=" + currentvalue, "marketValue=" + new_market_value);
        }
    }

    void modifyInjury(int pid, boolean newInjuryState) {

        String playerData = show_by_id(pid);

        String updatedData = playerData.replaceFirst("injury=(true|false)", "injury=" + newInjuryState);

        update(pid, playerData, updatedData);
    }

    public Player parsePlayer(int pid) {
        Pattern pattern = Pattern.compile(
                "playerID=(\\d+), name=(.*?), age=(\\d+), nationality=(.*?), position=(.*?), team=(.*?), shirtNumber=(\\d+), goals=(\\d+), assists=(\\d+), yellowCards=(\\d+), redCards=(\\d+), injury=(true|false), marketValue=(\\d+\\.\\d+)"
        );

        Matcher matcher = pattern.matcher(show_by_id(pid));

        if (matcher.find()) {

            int playerID = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            int age = Integer.parseInt(matcher.group(3));
            String nationality = matcher.group(4);
            String position = matcher.group(5);
            String teamname = matcher.group(6);
            int shirtNumber = Integer.parseInt(matcher.group(7));
            int goals = Integer.parseInt(matcher.group(8));
            int assists = Integer.parseInt(matcher.group(9));
            int yellowCards = Integer.parseInt(matcher.group(10));
            int redCards = Integer.parseInt(matcher.group(11));
            boolean injury = Boolean.parseBoolean(matcher.group(12));
            double marketValue = Double.parseDouble(matcher.group(13));

            Player player = new Player(playerID, name, age, nationality, position, new TeamManager().parseTeam(new TeamManager().getTeamIDByName(teamname)), shirtNumber, goals, assists, yellowCards, redCards, marketValue, injury);
            return player;
        } else {
            System.out.println("Invalid player data format.");
            return null;
        }
    }
}