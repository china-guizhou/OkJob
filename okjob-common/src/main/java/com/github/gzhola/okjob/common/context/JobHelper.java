package com.github.gzhola.okjob.common.context;

import com.github.gzhola.okjob.common.core.Constants;
import com.github.gzhola.okjob.common.log.JobFileAppender;
import com.github.gzhola.okjob.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * OkJob helper
 *
 * @author Hola
 * @since 2021-10-25
 */
@Slf4j(topic = "ok-job logger")
public class JobHelper {

    /**
     * current JobId
     *
     * @return
     */
    public static long getJobId() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return -1;
        }
        return jobContext.getJobId();
    }

    /**
     * current JobParam
     *
     * @return
     */
    public static String getJobParam() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return null;
        }
        return jobContext.getJobParam();
    }

    /**
     * current JobLogFileName
     *
     * @return
     */
    public static String getJobLogFileName() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return null;
        }
        return jobContext.getJobLogFileName();
    }

    /**
     * current ShardIndex
     *
     * @return
     */
    public static int getShardIndex() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return -1;
        }
        return jobContext.getShardIndex();
    }

    /**
     * current ShardTotal
     *
     * @return
     */
    public static int getShardTotal() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return -1;
        }
        return jobContext.getShardTotal();
    }

    /**
     * append log with pattern
     *
     * @param appendLogPattern  like "aaa {} bbb {} ccc"
     * @param appendLogArguments    like "111, true"
     */
    public static boolean log(String appendLogPattern, Object ... appendLogArguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(appendLogPattern, appendLogArguments);
        String appendLog = ft.getMessage();

        /*appendLog = appendLogPattern;
        if (appendLogArguments!=null && appendLogArguments.length>0) {
            appendLog = MessageFormat.format(appendLogPattern, appendLogArguments);
        }*/

        StackTraceElement callInfo = new Throwable().getStackTrace()[1];
        return logDetail(callInfo, appendLog);
    }

    /**
     * append exception stack
     *
     * @param e
     */
    public static boolean log(Throwable e) {

        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String appendLog = stringWriter.toString();

        StackTraceElement callInfo = new Throwable().getStackTrace()[1];
        return logDetail(callInfo, appendLog);
    }

    /**
     * append log
     *
     * @param callInfo
     * @param appendLog
     */
    private static boolean logDetail(StackTraceElement callInfo, String appendLog) {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return false;
        }

        /*// "yyyy-MM-dd HH:mm:ss [ClassName]-[MethodName]-[LineNumber]-[ThreadName] log";
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        StackTraceElement callInfo = stackTraceElements[1];*/

        StringBuffer sb = new StringBuffer();
        sb.append(DateUtils.formatDateTime(new Date())).append(" ").append("[").append(callInfo.getClassName()).append("#").append(callInfo.getMethodName()).append("]").append("-").append("[").append(callInfo.getLineNumber()).append("]").append("-").append("[").append(Thread.currentThread().getName()).append("]").append(" ")
                .append(appendLog!=null?appendLog:"");
        String formatAppendLog = sb.toString();

        // appendlog
        String logFileName = jobContext.getJobLogFileName();

        if (logFileName!=null && logFileName.trim().length()>0) {
            JobFileAppender.appendLog(logFileName, formatAppendLog);
            return true;
        } else {
            log.info(">>>>>>>>>>> {}", formatAppendLog);
            return false;
        }
    }

    // ---------------------- tool for handleResult ----------------------

    /**
     * handle success
     *
     * @return
     */
    public static boolean handleSuccess(){
        return handleResult(Constants.SUCCESS_CODE, null);
    }

    /**
     * handle success with log msg
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleSuccess(String handleMsg) {
        return handleResult(Constants.SUCCESS_CODE, handleMsg);
    }

    /**
     * handle fail
     *
     * @return
     */
    public static boolean handleFail(){
        return handleResult(Constants.ERROR_CODE, null);
    }

    /**
     * handle fail with log msg
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleFail(String handleMsg) {
        return handleResult(Constants.ERROR_CODE, handleMsg);
    }

    /**
     * handle timeout
     *
     * @return
     */
    public static boolean handleTimeout(){
        return handleResult(Constants.TIMEOUT_CODE, null);
    }

    /**
     * handle timeout with log msg
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleTimeout(String handleMsg){
        return handleResult(Constants.TIMEOUT_CODE, handleMsg);
    }

    /**
     * @param handleCode
     *
     *      200 : success
     *      500 : fail
     *      502 : timeout
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleResult(int handleCode, String handleMsg) {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return false;
        }

        jobContext.setHandleCode(handleCode);
        if (handleMsg != null) {
            jobContext.setHandleMsg(handleMsg);
        }
        return true;
    }

}
