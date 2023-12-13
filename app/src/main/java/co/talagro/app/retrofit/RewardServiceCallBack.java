package co.talagro.app.retrofit;

import co.talagro.app.retrofit.response.RewardResponse;

public interface RewardServiceCallBack {
    void onDataReceived(RewardResponse data);
    void onError(String errorMessage);
}
