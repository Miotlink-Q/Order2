package com.mlink.launch.starter.task;

public abstract class  MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }
}
