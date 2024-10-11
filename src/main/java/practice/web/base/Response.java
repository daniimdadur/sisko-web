package practice.web.base;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private int status;
    private Object message;
    private T data;
    private int total;
}
