package com.stepin.coronaapp.api;


import com.stepin.coronaapp.model.ErrorMessage;

public interface NetworkResponse<T> {
    void onResponse(T obj, ErrorMessage error);
    //void onError(ErrorMessage error);
}
