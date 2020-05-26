package util;

public class DatabaseException extends Exception {

    private static final long serialVersionUID = 1L;
    private final Exception ex;
    private final String msg;

    public DatabaseException(Exception e) {
        ex = e;
        msg = e.getMessage();
    }

    public DatabaseException(Exception exception, String message) {
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