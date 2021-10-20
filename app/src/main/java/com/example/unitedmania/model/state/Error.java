package com.example.unitedmania.model.state;

public class Error extends FetchState{
    public Error(String error) {
        super.setData(null);
        super.setError(error);
    }
}
