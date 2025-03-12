import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Team {
    protected static int lastTeamID =loadLastID("lastTeamID.txt");
    protected static FileHandler fileHandler = new FileHandler("myDirectory");
    private int teamID;
    private String teamName;
    private String coachName;
    private int foundationYear;
    private String stadiumName;
    private int stadiumCapacity;
    private String league;
    private int totalGoals;
    private int totalGoalsConceded;
    private int wins;
    private int draws;
    private int losses;
    private int points;
    private int currentRank;
    private List<Integer> players;
    private int captainid;

    public Team(int foundationYear, String teamName, String stadiumName, int stadiumCapacity) {
        this.foundationYear = foundationYear;
        this.teamID = ++lastTeamID;
        this.teamName = teamName;
        this.stadiumName = stadiumName;
        this.stadiumCapacity = stadiumCapacity;
        this.league = "Egyptian League";
        this.wins=this.draws=this.losses=this.totalGoals=this.totalGoalsConceded=this.points=0;
        this.players=new ArrayList<>();
    }

    public Team(int foundationYear, int teamID, String teamName, String coachName, String stadiumName, int stadiumCapacity, String league, int totalGoals, int totalGoalsConceded, int wins, int draws, int losses, int points, int currentRank, List<Integer> players, int captainid) {
        this.foundationYear = foundationYear;
        this.teamID = teamID;
        this.teamName = teamName;
        this.coachName = coachName;
        this.stadiumName = stadiumName;
        this.stadiumCapacity = stadiumCapacity;
        this.league = league;
        this.totalGoals = totalGoals;
        this.totalGoalsConceded = totalGoalsConceded;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.points = points;
        this.currentRank = currentRank;
        this.players = players;
        this.captainid = captainid;
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
            bw.write(String.valueOf(lastTeamID));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public int getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(int foundationYear) {
        this.foundationYear = foundationYear;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public int getStadiumCapacity() {
        return stadiumCapacity;
    }

    public void setStadiumCapacity(int stadiumCapacity) {
        this.stadiumCapacity = stadiumCapacity;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public int getTotalGoals() {
        return totalGoals;
    }

    public void setTotalGoals(int totalGoals) {
        this.totalGoals = totalGoals;
    }

    public int getTotalGoalsConceded() {
        return totalGoalsConceded;
    }

    public void setTotalGoalsConceded(int totalGoalsConceded) {
        this.totalGoalsConceded = totalGoalsConceded;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(int currentRank) {
        this.currentRank = currentRank;
    }

    public List<Integer> getPlayers() {
        return players;
    }

    public void setPlayers(List<Integer> players) {
        this.players = players;
    }

    public int getCaptain() {
        return captainid;
    }

    public void setCaptain(int captain) {
        this.captainid = captain;
    }

    public void addplayer(int pid){
        this.players.add(pid);
    }

    @Override
    public String toString() {
        return "teamID=" + teamID +
                ", teamName=" + teamName +
                ", coachName=" + coachName +
                ", foundationYear=" + foundationYear +
                ", stadiumName=" + stadiumName +
                ", stadiumCapacity=" + stadiumCapacity +
                ", league=" + league +
                ", totalGoals=" + totalGoals +
                ", totalGoalsConceded=" + totalGoalsConceded +
                ", wins=" + wins +
                ", draws=" + draws +
                ", losses=" + losses +
                ", points=" + points +
                ", currentRank=" + currentRank +
                ", players{ " + players +" }"+
                ", captain=" + captainid;
    }
}
