package com.gnardini.lolmatchups.repository;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<Type> implements Callback<Type> {

    /**
     * WoloxCallback's implementation of Retrofit's onResponse() callback
     * Try using WoloxCallback's onResponseSuccessful() and onResponseFailed() in your project.
     */
    @Override
    public void onResponse(Call<Type> call, Response<Type> response) {
        if (isAuthError(response)) {
            handleAuthError(response);
        } else if (response.isSuccessful()) {
            onResponseSuccessful(response.body());
        } else {
            onResponseFailed(response.errorBody(), response.code());
        }
    }

    /**
     * WoloxCallback's implementation of Retrofit's onFailure() callback
     * Try using WoloxCallback's onCallFailure() in your project.
     */
    @Override
    public void onFailure(Call<Type> call, Throwable t) {
        onCallFailure(t);
    }

    /**
     * Checks whether the response is an auth error or not.
     *
     * You should override this method and check if the response is an auth error, then return <b>true</b> if it is.
     * By default, this method returns <b>false</b>.
     *
     * @param response Retrofit response
     * @return <b>true</b> if the response is an auth error, <b>false</b> otherwise
     */
    protected boolean isAuthError(Response<Type> response) {
        return false;
    }

    /**
     * Handles the auth error response.
     * This method is only called when there is an auth error. (<i>isAuthError() returns true</i>)
     * You should remove tokens and do the corresponding cleaning inside this method.
     * By default, this method does nothing.
     *
     * @param response Retrofit response
     */
    protected void handleAuthError(Response<Type> response) {}

    /**
     * Successful HTTP response
     * @param response the API JSON response converted to a Java object.
     *                 The API response code is included in the response object.
     */
    public abstract void onResponseSuccessful(Type response);

    /**
     * Successful HTTP response but has an error body
     * @param responseBody The error body
     * @param code The error code
     */
    public abstract void onResponseFailed(ResponseBody responseBody, int code);

    /**
     * Invoked when a network or unexpected exception occurred during the HTTP request, meaning
     * that the request couldn't be executed.
     * @param t A Throwable with the cause of the call failure
     */
    public void onCallFailure(Throwable t) {
        onResponseFailed(null, 0);
    }

}
