package project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<K, V> {
    private K statusCode;
    private V message;

    public CommonResponse(K statusCode, V message) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
