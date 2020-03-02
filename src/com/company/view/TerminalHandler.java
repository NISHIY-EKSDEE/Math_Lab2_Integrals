package com.company.view;

import com.company.logic.Function;
import com.company.logic.IntegralSolver;
import com.company.logic.IntegratingMethod;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TerminalHandler {
    Scanner scn;
    Function mainFunc;
    IntegratingMethod method;
    Function[] funcs = {
            x ->  -Math.pow(x, 3) -Math.pow(x, 2) - 2*x + 1,
            x -> x*x,
            x -> x/Math.sqrt(Math.pow(x, 4) + 16),
            x -> Math.sin(x)/(Math.pow(Math.cos(x), 2) + 1),
            x -> Math.exp(2*x)
    };
    String[] strFuncs = {
            "- x^3 - x^2 - 2x + 1",
            "x^2",
            "x/sqrt(x^4 + 16)",
            "sin(x) / (cos^2(x) + 1)",
            "e^(2x)"
    };

    public TerminalHandler(){
        scn = new Scanner(System.in);
    }

    public void start(){
        String cmd;
        System.out.printf("%s\n%s\n", "Программа запущена", "Введите help для просмотра доступных команд");
        while(scn.hasNextLine()){
            cmd = scn.nextLine().trim();
            switch(cmd.trim()){
                case "help":
                    System.out.println("solve - для начала нахождения интеграла");
                    System.out.println("exit - для выхода из программы");
                    break;
                case "solve":
                    solve();
                    break;
                case "":
                    break;
                case "exit":
                    System.out.println("Всего доброго!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неизвестная команда");
                    break;
            }
        }
    }

    private void solve(){
        double leftBorder, rightBorder, precision, curIntegral, prevIntegral;
        int n = 2;

        System.out.println("Выберете функцию для нахождения интеграла:");
        for(int i = 0; i < strFuncs.length; i++)
            System.out.printf("%d) %s\n", i+1, strFuncs[i]);

        String cmd = scn.nextLine();
        int number = Integer.parseInt(cmd.trim());
        if(number > 0 && number <= strFuncs.length) {
            mainFunc = funcs[number - 1];
        }else{
            System.out.println("Некорректный ввод");
            return;
        }

        System.out.println("Выберете метод нахождения интеграла:");
        System.out.println("1) Метод прямоугольников. Левых");
        System.out.println("2) Метод прямоугольников. Правых");
        System.out.println("3) Метод прямоугольников. Средних");
        System.out.println("4) Метод трапеций");
        System.out.println("5) Метод Симпсона");
        cmd = scn.nextLine();
        switch(cmd.trim()){
            case "1":
                method = IntegralSolver::solveWithRectLeft;
                break;
            case "2":
                method = IntegralSolver::solveWithRectRight;
                break;
            case "3":
                method = IntegralSolver::solveWithRectMiddle;
                break;
            case "4":
                method = IntegralSolver::solveWithTrapeze;
                break;
            case "5":
                method = IntegralSolver::solveWithSimpson;
                break;
            default:
                System.out.println("Некорректный ввод");
                return;
        }

        System.out.println("Введите левую, правую границы и точность");
        try {
            leftBorder = scn.nextDouble();
            rightBorder = scn.nextDouble();
            precision = scn.nextDouble();
            if(precision > 1){
                System.out.println("Некорректный ввод точности");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод");
            return;
        }

        curIntegral = method.solve(leftBorder, rightBorder, n, mainFunc);
        do{
            prevIntegral = curIntegral;
            n *= 2;
            curIntegral = method.solve(leftBorder, rightBorder, n, mainFunc);
        }while (Math.abs(curIntegral - prevIntegral) > precision);
        System.out.printf("Значение интеграла: %.6f\n", curIntegral);
        System.out.printf("При числе разбиений %d достигается требуемая точность\n", n);
    }
}
