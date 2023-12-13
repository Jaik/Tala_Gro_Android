package co.talagro.app.retrofit.service;

import co.talagro.app.retrofit.response.Reward;
import co.talagro.app.retrofit.response.RewardResponse;
import co.talagro.app.retrofit.response.Type;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RewardApiClient {
    @GET("talagro/getRewards/{type}")
    Call<RewardResponse> getRewards(@Path("type") final Type type);
}
