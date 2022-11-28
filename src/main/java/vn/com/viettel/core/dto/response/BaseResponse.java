package vn.com.viettel.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    private Integer code;
    private String message;
    private Object data;
	private String clientMessageId;
    private String transactionId;
    private String path;
    private String timestamp;
    private Integer status;
}
