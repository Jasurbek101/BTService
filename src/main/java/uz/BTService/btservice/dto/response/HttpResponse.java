package uz.BTService.btservice.dto.response;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class HttpResponse<T> {
    private boolean success;
    private String message;
    private T body;
    private int code;
    private ResourceBundle bundle;
    private String messageFormat = "error.%s";

    public HttpResponse() {
    }

    public HttpResponse(boolean success) {
        this.success = success;
    }

    public HttpResponse(boolean success, ResourceBundle bundle) {
        this.success = success;
        this.bundle = bundle;
    }

    public HttpResponse(boolean success, ResourceBundle bundle, String messageFormat) {
        this.success = success;
        this.bundle = bundle;
        this.messageFormat = messageFormat;
    }

    public HttpResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public HttpResponse(boolean success, String message, T body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }

    public HttpResponse(boolean success, String message, T body, int code) {
        this.success = success;
        this.message = message;
        this.body = body;
        this.code = code;
    }

    public static <T> HttpResponse<T> build() {
        return new HttpResponse();
    }

    public static <T> HttpResponse<T> build(boolean success) {
        return new HttpResponse(success);
    }

    public static <T> HttpResponse<T> build(boolean success, ResourceBundle bundle) {
        return new HttpResponse(success, bundle);
    }

    public static <T> HttpResponse<T> build(boolean success, ResourceBundle bundle, String messageFormat) {
        return new HttpResponse(success, bundle, messageFormat);
    }

    public static <T> HttpResponse<T> build(boolean success, String message) {
        return new HttpResponse(success, message);
    }

    public static <T> HttpResponse<T> build(boolean success, String message, T body) {
        return new HttpResponse(success, message, body);
    }

    public static <T> HttpResponse<T> build(boolean success, String message, T body, int code) {
        return new HttpResponse(success, message, body, code);
    }

    public HttpResponse<T> success(boolean success) {
        this.success = success;
        return this;
    }

    public HttpResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public HttpResponse<T> body(T body) {
        this.body = body;
        return this;
    }

    public HttpResponse<T> code(int code) {
        this.code = code;
        return this;
    }

    public HttpResponse<T> code(Status status) {
        String msg = null;
        if (this.bundle != null) {
            msg = this.bundle.getString(String.format(this.messageFormat, status.getCode()));
        }

        if (msg == null) {
            msg = status.getText();
        }

        return this.code(status.getCode()).message(msg);
    }

    public static Map<Object, Object> objToMap(Object... objects) {
        Map<Object, Object> map = new HashMap();

        for(int i = 1; i <= objects.length / 2; ++i) {
            map.put(objects[2 * i - 2], objects[2 * i - 1]);
        }

        return map;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public T getBody() {
        return this.body;
    }

    public int getCode() {
        return this.code;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private ResourceBundle getBundle() {
        return this.bundle;
    }

    private void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    private String getMessageFormat() {
        return this.messageFormat;
    }
    private void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public static enum Status {
        OK(200, "OK"),
        BAD_REQUEST(400, "Bad Request"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
        NOT_ACTIVE(700, "Not Active"),
        NOT_CONFIRMED(701, "Not Confirmed"),
        ALREADY_EXIST(702, "Already Exist"),
        SMS_NOT_SENT(703, "SMS Not Sent"),
        WRONG_USERNAME_OR_PASSWORD(800, "The username or password is wrong");

        private final int code;
        private final String text;

        private Status(int code, String text) {
            this.code = code;
            this.text = text;
        }

        public int getCode() {
            return this.code;
        }

        public String getText() {
            return this.text;
        }
    }

}
