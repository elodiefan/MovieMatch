package use_case.customize;

public class CustomizeOutputData {

    private final String username;
    private final String colourChosen;

    public CustomizeOutputData(String username, String colourChosen) {
        this.username = username;
        this.colourChosen = colourChosen;
    }

    public String getUsername() {
        return username;
    }

    public String getColourChosen() {
        return colourChosen;
    }
}
