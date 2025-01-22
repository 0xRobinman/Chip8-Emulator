package org.chip8.cpu;

import org.chip8.display.Gui;

public class Cpu {
    /**
     * 1-F (16) Variables
     */
    private int[] v;
    private Gui gui;
    private int PC;
    private byte[] rom;

    public Cpu(Gui gui) {
        v = new int[16];
        this.gui = gui;
    }

    private short fetchOpcode() {

        return 0;
    }

    /**
     * Read next instruction from ROM
     */
    private void readInstruction() {
        // Fetch opcode
        short opcode = fetchOpcode();

        // Decode opcode

    }

}
