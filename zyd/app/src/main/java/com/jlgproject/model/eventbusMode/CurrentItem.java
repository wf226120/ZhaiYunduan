package com.jlgproject.model.eventbusMode;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/7/31.
 */

public class CurrentItem implements Serializable{

    private int index;

    public CurrentItem(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
