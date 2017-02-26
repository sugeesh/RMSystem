package org.hashtag.rms.util.exceptions;

/**
 * ServiceException is for handling the services exceptions
 *
 * @author Sugeesh Chandraweera
 */
public class ServiceException extends RuntimeException {

    String subject;
    String body;
    int  errorCode;

    public ServiceException() {

    }

    public ServiceException(String subject, String body, int errorCode) {
        super();
        this.subject = subject;
        this.body = body;
        this.errorCode = errorCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
