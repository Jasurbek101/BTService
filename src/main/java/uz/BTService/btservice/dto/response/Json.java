package uz.BTService.btservice.dto.response;

import java.io.Serializable;

public class Json implements Serializable {
    private boolean success = false;
    private String msg = "";
    private Object obj = null;
    private String url;

    public Json() {
    }

    public static Json build() {
        return new Json();
    }

    public static Json build(boolean success) {
        Json json = build();
        json.setSuccess(success);
        return json;
    }

    public Json success(boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Json msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public Json obj(Object obj) {
        this.setObj(obj);
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getObj() {
        return this.obj;
    }

    public String getUrl() {
        return this.url;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
