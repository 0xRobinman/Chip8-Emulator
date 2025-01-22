package org.chip8.cpu;

public class Cpu {
    /**
     * 1-F (16) Variables
     */
    private int[] v;

    /**
     * Program Counter
     */
    private int PC;

    public Cpu() {
        v = new int[16];
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
