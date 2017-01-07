package com.gnardini.lolmatchups.repository;

import com.gnardini.lolmatchups.model.RepoError;

public interface RepoCallback<Type> {

    void onSuccess(Type value);

    void onError(RepoError error, int code);

}
