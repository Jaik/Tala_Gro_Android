package co.talagro.app.retrofit.service;

import co.talagro.app.retrofit.request.UserUpdateRequest;
import co.talagro.app.retrofit.response.UserResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiClient {

    @POST("talagro/updateCoins/{id}")
    Call<UserResponse> updateCoins(@Path("id") final long id,
                                                @Body final UserUpdateRequest userCoinUpdateRequest);

    @GET("talagro/getCoins/{id}")
    Call<UserResponse> getCoins(@Path("id") final long userid);


}
