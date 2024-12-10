package com.example.wallet.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomSuccessResponse<T> {
    private T data;
    private String message;

    public CustomSuccessResponse(T data) {
        this.data = data;
        this.message = "Success";
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}