package com.example.unitedmania.model.state;

public class Success extends FetchState{
    public Success(Object data) {
        super.setData(data);
        super.setError(null);
    }
}
