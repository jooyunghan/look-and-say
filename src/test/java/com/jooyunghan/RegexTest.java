package com.jooyunghan;

import static com.jooyunghan.Regex.replaceAll;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jooyung.han on 7/15/16.
 */
public class RegexTest {

    @org.junit.Test
    public void replaceAllWorks() throws Exception {
        assertThat(replaceAll("aaabbccc", "b+", g -> "BB"), is("aaaBBccc"));
        assertThat(replaceAll("aacc", "", g -> "."), is(".a.a.c.c."));
        assertThat(replaceAll("aacc", "^", g -> "."), is(".aacc"));
    }
}
