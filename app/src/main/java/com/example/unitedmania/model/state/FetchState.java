package com.example.unitedmania.model.state;

public class FetchState {
    private Object data = null;
    private String error = null;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
