package hackalearn.mygiftsavor.infra.exception;

public class DuplicateException extends CustomRuntimeException {

    public DuplicateException(String msg) {
        super(msg);
        name = "DuplicateException";
    }
}
