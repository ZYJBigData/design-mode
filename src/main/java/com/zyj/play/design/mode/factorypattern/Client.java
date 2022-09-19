package com.zyj.play.design.mode.factorypattern;

public class Client {
    public static void main(String[] args) {
        AbstractFactory abstractFactory= new IntelFactory();
        ComputerEngineer computerEngineer = new ComputerEngineer();
        computerEngineer.makeComputer(abstractFactory);
    }
}
