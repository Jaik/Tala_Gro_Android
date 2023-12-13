package co.talagro.app.retrofit;

import co.talagro.app.retrofit.response.UserResponse;

public interface UserServiceCallBack {
    void onDataReceived(UserResponse data);
    void onError(String errorMessage);
}
