package com.github.gzhola.okjob.common.model;

import com.github.gzhola.okjob.common.core.Constants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 *
 * @author Hola
 * @since 2021-10-24
 */
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode
@NoArgsConstructor
public class R<T> implements Serializable {

    public static final long serialVersionUID = 42112231L;

    private int code;
    private String msg;
    private T data;

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public R(T data) {
        this.code = Constants.SUCCESS_CODE;
        this.data = data;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getContent() {
        return data;
    }
    public void setContent(T data) {
        this.data = data;
    }
}
