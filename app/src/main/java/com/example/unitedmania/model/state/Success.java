package com.example.unitedmania.model.state;

public class Success<T> extends FetchState<T>{
    public Success(T data) {
        super.setData(data);
        super.setError(null);
    }
}
