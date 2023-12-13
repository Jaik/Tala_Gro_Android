package co.talagro.app.retrofit.response;

import java.util.List;

public class RewardResponse {
    private List<Reward> rewards;

    @Override
    public String toString() {
        return "RewardResponse{" +
                "rewards=" + rewards +
                '}';
    }
}
