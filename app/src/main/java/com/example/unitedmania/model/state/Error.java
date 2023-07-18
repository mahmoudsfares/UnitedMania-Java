package com.example.unitedmania.model.state;

public class Error<T> extends FetchState<T>{
    public Error(String error) {
        super.setData(null);
        super.setError(error);
    }
}
