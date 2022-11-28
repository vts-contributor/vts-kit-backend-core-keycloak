package vn.com.viettel.core.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String timestamp;

    private final String clientMessageId;

    private final String transactionId;

    private int code;

    private String message;

    private T data;

    public ResponseData(String clientMessageId, String transactionId) {
        this.code = 200;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.message = "Successful!";
        this.clientMessageId = clientMessageId;
        this.transactionId = transactionId;
    }

    public ResponseData<T> success(T data) {
        this.data = data;
        return this;
    }

    public ResponseData<T> error(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public ResponseData<T> error(int code, String message, T data) {
        this.data = data;
        this.code = code;
        this.message = message;
        return this;
    }

    public void setData(T data) {
        this.data = data;
    }

}
