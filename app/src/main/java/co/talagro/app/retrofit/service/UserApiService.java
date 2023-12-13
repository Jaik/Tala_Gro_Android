package co.talagro.app.retrofit.service;

import co.talagro.app.retrofit.request.UserCoinUpdateRequest;
import co.talagro.app.retrofit.response.UserResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiService {

    @POST("talagro/updateCoins/{id}")
    Call<ResponseBody> adjustCreditToLpsAccount(@Path("id") final long id,
                                                @Body final UserCoinUpdateRequest userCoinUpdateRequest);

    @GET("talagro/getCoins/{id}")
    Call<UserResponse> getData(@Path("id") final long userid);


}
