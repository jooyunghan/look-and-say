package com.jooyunghan.java8;

import java.util.List;
import java.util.stream.Stream;

import static com.jooyunghan.java8.Lists.*;
import static java.lang.System.out;
import static java.util.stream.Stream.iterate;


public class Java8Main {
    // using java8 stream
    // 이건 lazy stream이긴 하다
    // 그러나 Scala stream과 같은 실제 list가 아니라
    // iterator 기반의 lazy stream. 그래서 같은 Stream을 두번 순회할 수 없다.
    // 단 stream을 이용하면 loop 처리를 look 바깥에서 할 수 있게 된다
    public static void main(String[] args) {
        antSequence().skip(3).limit(10).forEach(out::println);
    }

    private static Stream<List<Integer>> antSequence() {
        return iterate(of(1), Java8Main::next3);
    }

    public static List<Integer> next(List<Integer> sequence) {
        return flatten(map(pair -> of(pair._1, pair._2), runLength(sequence)));
    }

    public static List<Integer> next2(List<Integer> sequence) {
        return flatMap(pair -> of(pair._1, pair._2), runLength(sequence));
    }

    public static List<Integer> next3(List<Integer> sequence) {
        return flatMap(g -> of(g.get(0), g.size()), group(sequence));
    }
}
