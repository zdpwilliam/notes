package com.william.pattern.state;

/**
 * Created by william on 17-10-27.
 */
public interface State {

    void changeTo(State state);

}
