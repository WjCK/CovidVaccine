package util;

public class CustomException extends Exception {

    private static final long serialVersionUID = 164441311026305696L;
    private final Exception ex;
    private final String msg;

    public CustomException(Exception e) {
        ex = e;
        msg = e.getMessage();
    }

    public CustomException(Exception exception, String message) {
        this.ex = exception;
        this.msg = message;
    }

    public Exception getEx() {
        return ex;
    }

    public String getMsg() {
        return msg;
    }
}