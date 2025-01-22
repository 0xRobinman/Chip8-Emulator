package org.chip8.cpu;

import org.chip8.display.Gui;

public class Cpu {
    /**
     * 1-F (16) Variables
     */
    private int[] v;
    private Gui gui;
    /**
     * Program Counter
     */
    private int PC;

    public Cpu() {
        v = new int[16];
        gui = new Gui();
    }

    private short fetchOpcode() {

        return 0;
    }

    private void readInstruction() {
        // Fetch opcode
        short opcode = fetchOpcode();

        // Decode opcode

    }

}
