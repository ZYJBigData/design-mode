package com.zyj.play.interview.questions.flink.source.infra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LearnPojo {
    public long id;
    public String name;
    public long time;
    public long value;
    public long index;
    public long waterMark;
    
    public LearnPojo(Builder builder) {
        this.id = builder.id;
        this.time = builder.time;
        this.name = builder.name;
        this.value = builder.value;
        this.index = builder.index;
    }

    public static Builder builder(long id, String name) {
        return new Builder(id, name);
    }

    public static class Builder {
        private long id;
        private Long time;
        private String name;
        private Long value;
        private long index;

        public Builder(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder time(long time) {
            this.time = time;
            return this;
        }

        public Builder value(Long value) {
            this.value = value;
            return this;
        }

        public Builder index(long index) {
            this.index = index;
            return this;
        }

        public LearnPojo build() {
            return new LearnPojo(this);
        }
    }
}
