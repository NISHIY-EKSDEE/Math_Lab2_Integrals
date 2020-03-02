package com.company.logic;

public class IntegralSolver {

    public static double solveWithRectLeft(double leftBorder, double rightBorder, int n, Function func){
        double h = (rightBorder - leftBorder) / n;
        double result = 0;
        double currentX = leftBorder;
        for(int i = 0; i < n; i++){
            result += func.getResult(currentX);
            currentX += h;
        }
        result *= h;
        return result;
    }

    public static double solveWithRectMiddle(double leftBorder, double rightBorder, int n, Function func){
        double h = (rightBorder - leftBorder) / n;
        double result = 0;
        double currentX = leftBorder - h/2;
        for(int i = 0; i < n; i++){
            currentX += h;
            result += func.getResult(currentX);
        }
        result *= h;
        return result;
    }

    public static double solveWithRectRight(double leftBorder, double rightBorder, int n, Function func){
        double h = (rightBorder - leftBorder) / n;
        double result = 0;
        double currentX = leftBorder;
        for(int i = 0; i < n; i++){
            currentX += h;
            result += func.getResult(currentX);
        }
        result *= h;
        return result;
    }

    public static double solveWithTrapeze(double leftBorder, double rightBorder, int n, Function func){
        double h = (rightBorder - leftBorder) / n;
        double result = 0;
        double currentX = leftBorder;
        for(int i = 0; i < n - 1; i++){
            currentX += h;
            result += func.getResult(currentX);
        }
        result = h/2 * (2 * result + func.getResult(leftBorder) + func.getResult(rightBorder));
        return result;
    }

    public static double solveWithSimpson(double leftBorder, double rightBorder, int n, Function func){
        double h = (rightBorder - leftBorder) / n;
        double result = 0;

        for(int i = 1; i <= n - 1; i += 2){
            result += 4 * func.getResult(leftBorder + i*h);
        }

        for(int i = 2; i <= n - 2; i += 2){
            result += 2 * func.getResult(leftBorder + i*h);
        }

        result = (result + func.getResult(leftBorder) + func.getResult(rightBorder)) * h/3;
        return result;
    }
}
