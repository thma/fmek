package org.fmek.test;

import javax.inject.Named;

/**
 * Created by thma on 06.11.2015.
 */
@Named
@Autocorrection
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }
}
