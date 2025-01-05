package com.sprint.app.errorresponse;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;

    public ErrorResponse(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public ErrorResponse() {
	}

	public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
