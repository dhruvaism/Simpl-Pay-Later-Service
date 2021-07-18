package com.simpl.paylater.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private Object data; //
    private String message;
    private boolean ok; //true, object is found, false -> there is some issue

    public Response(Object data, boolean ok) {
        this.data = data;
        this.ok = ok;
    }

    public Response(String message, boolean ok) {
        this.message = message;
        this.ok = ok;
    }

}
