package com.example.unitedmania.model.state;

public class FetchState<T> {
    private T data = null;
    private String error = null;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
