import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchManager implements Manager {
    protected static FileHandler fileHandler = new FileHandler("myDirectory");
    private String filename = "Matches.txt";

    public void add(Object item) {
        if (item instanceof Match) {
            System.out.println("Match " + ((Match) item).getMatchID() + " added successfully.");
            fileHandler.appendToFile(filename, String.valueOf(((Match) item)) + "\n");
            Match.saveLastID("lastMatchID.txt");
        } else {
            System.out.println("Only Matches can be added.");
        }
    }

    public boolean isExists(int MatchID) {
        List<String> lines = fileHandler.readLines(filename);
        for (String line : lines) {
            if (line.contains("matchID=" + MatchID)) {
                return true;  // Return true if customerID exists in the file
            }
        }
        return false;
    }


    public boolean search_by_id(int id) {
        if (fileHandler.searchContent(filename, "matchID=" + id))
            return true;
        else return false;
    }

    public void update(int id, String old, String New) {
        fileHandler.update(filename, "matchID=" + id, old, New);
    }

    public void delete(int id) {
        List<String> S = new FileHandler("myDirectory").readLines(filename);
        for (String x : S) {
            if (x.contains("" + id))
                fileHandler.deleteLineById(filename, "matchID=" + id);
        }
    }

    public String show_by_id(int id) {
        if (search_by_id(id)) {
            return fileHandler.readLineById(filename, "matchID=" + id);
        } else {
            return "can not found this id";
        }
    }

    void changestateofmatch(int mid) {

        String matchData = show_by_id(mid);

        String updatedData = matchData.replaceFirst("ismatchplayed=(true|false)", "ismatchplayed=" + true);

        update(mid, matchData, updatedData);
    }

    void set_home_away_goals(int mid, int home_goals,int away_goals){
        Pattern pattern = Pattern.compile("homeTeamGoals=(\\d+)");
        Matcher matcher = pattern.matcher(show_by_id(mid));
        Pattern pattern1 = Pattern.compile("awayTeamGoals=(\\d+)");
        Matcher matcher1 = pattern1.matcher(show_by_id(mid));

        if (matcher.find()) {
            int currentGoals = Integer.parseInt(matcher.group(1));
            update(mid, "homeTeamGoals=" + currentGoals, "homeTeamGoals=" + home_goals);
        }
        if (matcher1.find()) {
            int currentGoals = Integer.parseInt(matcher1.group(1));
            update(mid, "awayTeamGoals=" + currentGoals, "awayTeamGoals=" + away_goals);
        }

        Match match=new MatchManager().parseMatch(mid);
        Team ht,at;
        if(home_goals>away_goals){
            ht=match.getHomeTeam();
            new TeamManager().increase_wins(ht.getTeamID());
            at=match.getAwayTeam();
            new TeamManager().increase_losses(at.getTeamID());
        }
        else if(home_goals<away_goals){
            ht=match.getHomeTeam();
            new TeamManager().increase_losses(ht.getTeamID());
            at=match.getAwayTeam();
            new TeamManager().increase_wins(at.getTeamID());
        }
        else{
            ht=match.getHomeTeam();
            new TeamManager().increase_draws(ht.getTeamID());
            at=match.getAwayTeam();
            new TeamManager().increase_draws(at.getTeamID());
        }
        new TeamManager().increase_total_goals(ht.getTeamID(),home_goals);
        new TeamManager().increase_total_goals(at.getTeamID(),away_goals);
        new TeamManager().increase_total_goals_Conceded(ht.getTeamID(),away_goals);
        new TeamManager().increase_total_goals_Conceded(at.getTeamID(),home_goals);
        new MatchManager().changestateofmatch(mid);
    }



    public Match parseMatch(int mid) {
        Pattern pattern = Pattern.compile(
                "matchID=(\\d+), homeTeam=(.*?), awayTeam=(.*?), stadiumName=(.*?), date=(.*?), time=(.*?), homeTeamGoals=(\\d+), awayTeamGoals=(\\d+), ismatchplayed=(true|false)"
        );

        Matcher matcher = pattern.matcher(show_by_id(mid));

        if (matcher.find()) {
            int matchID = Integer.parseInt(matcher.group(1));
            String homeTeam = matcher.group(2).trim();
            String awayTeam = matcher.group(3).trim();
            String stadiumName = matcher.group(4).trim();
            String date = matcher.group(5).trim();
            String time = matcher.group(6).trim();
            int homeTeamGoals = Integer.parseInt(matcher.group(7));
            int awayTeamGoals = Integer.parseInt(matcher.group(8));
            boolean isMatchPlayed = Boolean.parseBoolean(matcher.group(9));

            return new Match(matchID, homeTeam, awayTeam, stadiumName, date, time, homeTeamGoals, awayTeamGoals, isMatchPlayed);
        } else {
            System.out.println("Invalid match data format.");
            return null;
        }
    }
}
