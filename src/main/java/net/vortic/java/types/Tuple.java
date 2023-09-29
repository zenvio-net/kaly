package net.vortic.java.types;

import lombok.Getter;

@Getter
public class Tuple<K, V> {

    private final K first;
    private final V second;

    public Tuple(K first, V second){
        this.first = first;
        this.second = second;
    }
}
