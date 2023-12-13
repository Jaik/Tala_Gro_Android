package co.talagro.app.retrofit.response;


public class UserResponse {

    private int id;
    private int coins;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", coins=" + coins +
                '}';
    }
}
