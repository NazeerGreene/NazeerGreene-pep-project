package Controller;

public enum HttpStatusCode {
    SUCCESS(200),
    FAILURE_LOGIN(401),
    CLIENT_ERROR(400);
    

    private int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}
