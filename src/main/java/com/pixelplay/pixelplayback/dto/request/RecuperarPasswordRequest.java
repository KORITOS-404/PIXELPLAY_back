package com.pixelplay.pixelplayback.dto.request;

public class RecuperarPasswordRequest {
    private String correo;

    public RecuperarPasswordRequest() {}

    public RecuperarPasswordRequest(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}