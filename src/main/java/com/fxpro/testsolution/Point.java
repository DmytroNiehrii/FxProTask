package com.fxpro.testsolution;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
public class Point {
    public int i, j;

    public Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Point down() {
        return new Point(i, j - 1);
    }

    public Point left() {
        return new Point(i - 1, j);
    }

    public Point right() {
        return new Point(i + 1, j);
    }
}
