package com.github.gzhola.okjob.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 日志结果
 *
 * @author Hola
 * @since 2021-10-26
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LogResult implements Serializable {
    private static final long serialVersionUID = 42L;

    private int fromLineNum;
    private int toLineNum;
    private String logContent;
    private boolean isEnd;
}
