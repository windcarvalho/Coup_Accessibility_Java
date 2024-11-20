package tardigrade.resources.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tardigrade.game.IState;
import tardigrade.utils.Flag;

public class Tuple implements IState.ITuple, Serializable {
    private Flag key;
    private List<Object> data;

    private Tuple(Flag operation){
        this.key = operation;
        data = new ArrayList<>();
    }

    public static IState.ITuple create(Flag key) {
        return new Tuple(key);
    }

    @Override
    public List<Object> retrieve() {
        return data;
    }

    @Override
    public void append(Object object) {
        data.add(object);
    }

    @Override
    public Flag getKey(){
        return key;
    }
}