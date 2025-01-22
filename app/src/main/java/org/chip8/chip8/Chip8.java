package org.chip8.chip8;

import org.chip8.cpu.Cpu;

public class Chip8 {

    private Cpu cpu;

    public Chip8() {
        cpu = new Cpu();
    }

    public static void main(String[] args) {
        System.out.println("Hello, Chip8!");
    }
}
