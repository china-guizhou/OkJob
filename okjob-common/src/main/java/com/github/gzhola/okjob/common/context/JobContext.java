package com.github.gzhola.okjob.common.context;

import com.github.gzhola.okjob.common.core.Constants;
import lombok.Getter;
import lombok.Setter;

/**
 * OkJob Context
 *
 * @author Hola
 * @since 2021-10-25
 */
public class JobContext {

    /**
     * 任务ID
     */
    @Getter
    private final long jobId;

    /**
     * 任务参数
     */
    @Getter
    private final String jobParam;

    /**
     * 任务日志文件名称
     */
    @Getter
    private final String jobLogFileName;

    /**
     * shard index
     */
    @Getter
    private final int shardIndex;

    /**
     * shard total
     */
    @Getter
    private final int shardTotal;

    /**
     * 任务执行返回状态码
     *      200 : success
     *      500 : fail
     *      502 : timeout
     */
    @Getter
    @Setter
    private int handleCode;

    /**
     * 任务执行返回信息
     */
    @Getter
    @Setter
    private String handleMsg;


    private static InheritableThreadLocal<JobContext> CONTEXT_HOLDER = new InheritableThreadLocal<>();

    public JobContext(long jobId, String jobParam, String jobLogFileName, int shardIndex, int shardTotal) {
        this.jobId = jobId;
        this.jobParam = jobParam;
        this.jobLogFileName = jobLogFileName;
        this.shardIndex = shardIndex;
        this.shardTotal = shardTotal;
        // 默认返回成功状态码
        this.handleCode = Constants.SUCCESS_CODE;
    }

    public static void setJobContext(JobContext jobContext){
        CONTEXT_HOLDER.set(jobContext);
    }

    public static JobContext getJobContext(){
        return CONTEXT_HOLDER.get();
    }

}
