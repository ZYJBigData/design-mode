package com.zyj.play.interview.questions.raft.exception;

/**
 *
 */
public class RaftNotSupportException extends RuntimeException {

    public RaftNotSupportException() {
    }

    public RaftNotSupportException(String message) {
        super(message);
    }
}