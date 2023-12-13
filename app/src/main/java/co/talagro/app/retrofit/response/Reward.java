package co.talagro.app.retrofit.response;

public class Reward {

    private int id;
    private Type type;
    private String reward_type;
    private String message;
    private int coins;

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", type=" + type +
                ", reward_type='" + reward_type + '\'' +
                ", message='" + message + '\'' +
                ", coins=" + coins +
                '}';
    }
}
