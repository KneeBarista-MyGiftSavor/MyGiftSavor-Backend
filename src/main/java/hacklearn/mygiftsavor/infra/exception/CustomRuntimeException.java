package hacklearn.mygiftsavor.infra.exception;

import lombok.Getter;

public class CustomRuntimeException extends RuntimeException{

    @Getter
    String name;

    public CustomRuntimeException(String msg){
        super(msg);
    }
}
