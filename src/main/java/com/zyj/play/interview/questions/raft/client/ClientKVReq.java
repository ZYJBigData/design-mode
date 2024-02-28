package com.zyj.play.interview.questions.raft.client;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class ClientKVReq implements Serializable {

    public static int PUT = 0;
    public static int GET = 1;

    int type;

    String key;

    String value;

    public enum Type {
        /**
         * 1111
         */
        PUT(0), GET(1);
        int code;

        Type(int code) {
            this.code = code;
        }

        public static Type value(int code) {
            for (Type type : values()) {
                if (type.code == code) {
                    return type;
                }
            }
            return null;
        }
    }
}
   