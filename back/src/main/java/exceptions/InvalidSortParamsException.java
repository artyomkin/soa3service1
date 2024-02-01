package exceptions;

import java.util.List;

public class InvalidSortParamsException extends Exception {
    public List<String> invalidFields;
    public InvalidSortParamsException(String msg, List<String> invalidFields){
        super(msg);
        this.invalidFields = invalidFields;
    }
}
