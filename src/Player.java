import java.io.*;

public class Player {
    private static int lastPlayerID = loadLastID("lastPlayerID.txt");
    protected static FileHandler fileHandler = new FileHandler("myDirectory");
    private int playerID;
    private String name;
    private int age;
    private String nationality;
    private String position;
    private Team team;
    private int shirtNumber;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private boolean injury;
    private double marketValue;

    public Player(String name, int age, String nationality, String position, Team team, int shirtNumber, boolean injury, double marketValue) {
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.position = position;
        this.team = team;
        this.shirtNumber = shirtNumber;
        this.injury = injury;
        this.marketValue = marketValue;
        this.playerID = ++lastPlayerID;
    }

    public Player(int playerID, String name, int age, String nationality, String position, Team team, int shirtNumber, int goals, int assists, int yellowCards, int redCards, double marketValue, boolean injury) {
        this.playerID = playerID;
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.position = position;
        this.team = team;
        this.shirtNumber = shirtNumber;
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.marketValue = marketValue;
        this.injury = injury;
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
            bw.write(String.valueOf(lastPlayerID));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(int shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public boolean isInjury() {
        return injury;
    }

    public void setInjury(boolean injury) {
        this.injury = injury;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }



    @Override
    public String toString() {
        return
                "playerID=" + playerID +
                        ", name=" + name +
                        ", age=" + age +
                        ", nationality=" + nationality+
                        ", position=" + position +
                        ", team=" + team.getTeamName() +
                        ", shirtNumber=" + shirtNumber +
                        ", goals=" + goals +
                        ", assists=" + assists +
                        ", yellowCards=" + yellowCards +
                        ", redCards=" + redCards +
                        ", injury=" + injury +
                        ", marketValue=" + marketValue;
    }
}
