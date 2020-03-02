package com.company.logic;

@FunctionalInterface
public interface IntegratingMethod {
    double solve(double leftBorder, double rightBorder, int n, Function func);
}
