package com.jooyunghan.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Lists {
    static public <A> List<A> of(A... as) {
        return new ArrayList<>(Arrays.asList(as));
    }

    static public <A, B> List<B> flatMap(Function<A, List<B>> f, List<A> as) {
        List<B> result = new ArrayList<>();
        for (A a : as) {
            result.addAll(f.apply(a));
        }
        return result;
    }

    static public <A, B> List<B> map(Function<A, B> f, List<A> as) {
        List<B> result = new ArrayList<>();
        for (A a : as) {
            result.add(f.apply(a));
        }
        return result;
    }

    static public <A> List<A> flatten(List<List<A>> ass) {
        List<A> result = new ArrayList<>();
        for (List<A> as : ass) {
            result.addAll(as);
        }
        return result;
    }

    static public <A> List<List<A>> group(List<A> as) {
        final List<List<A>> groups = new ArrayList<>();
        List<A> last = null;
        for (A a : as) {
            if (last == null || !last.get(0).equals(a)) {
                groups.add(last = of(a));
            } else {
                last.add(a);
            }
        }
        return groups;
    }
}
