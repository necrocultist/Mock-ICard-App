package com.siemens.MockICardApp.graphql.inputOutput;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public  class EntranceExitInfo {
    private final String entrance;
    private final String  exit;

    public EntranceExitInfo(Timestamp entrance, Timestamp exit) {
        this.entrance = entrance != null ? entrance.toString() : null;
        this.exit = exit != null ? exit.toString() : null;
    }
}