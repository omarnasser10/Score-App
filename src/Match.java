import java.io.*;

public class Match {
    protected static int lastMatchID = loadLastID("lastMatchID.txt");
    protected static FileHandler fileHandler = new FileHandler("myDirectory");
    private int matchID;
    private Team homeTeam;
    private Team awayTeam;
    private String stadiumName;
    private String date;
    private String time;
    private int homeTeamGoals;
    private int awayTeamGoals;
    private boolean ismatchplayed;

    public Match(String stadiumName, String homeTeam, String awayTeam, String date, String time) {
        this.stadiumName = stadiumName;
        this.matchID = ++lastMatchID;
        this.homeTeam = new TeamManager().parseTeam(new TeamManager().getTeamIDByName(homeTeam));
        this.awayTeam = new TeamManager().parseTeam(new TeamManager().getTeamIDByName(awayTeam));
        this.date = date;
        this.time = time;
        this.ismatchplayed=false;
    }

    public Match(int matchID, String homeTeam, String awayTeam, String stadiumName, String date, String time, int homeTeamGoals, int awayTeamGoals,boolean ismatchplayed) {
        this.matchID = matchID;
        this.homeTeam = new TeamManager().parseTeam(new TeamManager().getTeamIDByName(homeTeam));
        this.awayTeam = new TeamManager().parseTeam(new TeamManager().getTeamIDByName(awayTeam));
        this.stadiumName = stadiumName;
        this.date = date;
        this.time = time;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.ismatchplayed=ismatchplayed;
    }

    private static int loadLastID(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            return line != null ? Integer.parseInt(line) : 0;
        } catch (IOException e) {
            return 0;
        }
    }

    public static void saveLastID(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(String.valueOf(lastMatchID));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public boolean isIsmatchplayed() {
        return ismatchplayed;
    }

    public void setIsmatchplayed(boolean ismatchplayed) {
        this.ismatchplayed = ismatchplayed;
    }

    @Override
    public String toString() {
        return "matchID=" + matchID +
                ", homeTeam="+homeTeam.getTeamName()+
                ", awayTeam="+awayTeam.getTeamName()+
                ", stadiumName=" + stadiumName+
                ", date=" + date +
                ", time=" + time +
                ", homeTeamGoals=" + homeTeamGoals +
                ", awayTeamGoals=" + awayTeamGoals +
                ", ismatchplayed=" + ismatchplayed
                ;
    }


}
