package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;

@Component
public class GetProductsByTimestamp {
    @NonNull
    private java.sql.Timestamp before;
    @NonNull
    private java.sql.Timestamp after;

    public GetProductsByTimestamp() {
    }

    public GetProductsByTimestamp(@NonNull Timestamp before, @NonNull Timestamp after) {
        this.before = before;
        this.after = after;
    }

    @NonNull
    public Timestamp getBefore() {
        return before;
    }

    @NonNull
    public Timestamp getAfter() {
        return after;
    }
}
