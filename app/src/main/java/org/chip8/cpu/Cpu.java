package org.chip8.cpu;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.chip8.display.Gui;

public class Cpu {
    /**
     * 1-F (16) Variables
     */
    private int[] v;
    private int PC = 0x200;
    private byte[] rom;
    private boolean debug = true;

    public Cpu(Gui gui) {
        v = new int[16];
    }

    /**
     * Fetch 16 bit (2 byte) opcode
     * 
     * @return
     */
    private short fetchOpcode() {
        short opcode;
        byte opcode_significant_bit = rom[PC];
        byte opcode_least_significant_bit = rom[PC + 1];
        PC += 2;
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.put(opcode_significant_bit);
        byteBuffer.put(opcode_least_significant_bit);
        opcode = byteBuffer.getShort(0);
        return opcode;
    }

    /**
     * Clear screen
     * 0x00E0
     */
    private void clearDisplay() {

    }

    /**
     * Return from subroutine
     * 0x00EE
     */
    private void returnFromSubRoutine() {

    }

    /**
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void call(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Jumps to NNN
     * 0x1NNN
     */
    private void jump(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Goes to subroutine NN
     * 0x2NNN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void subRoutine(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Skips instruction if equal
     * 0x3NNN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void skipEqual(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Skips if not equal
     * 0x4NNN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void skipNotEqual(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Skips instruction if equal
     * 0x5NNN
     * 
     * @param argument1
     * @param argument2
     */
    private void skipEqual(byte argument1, byte argument2) {

    }

    /**
     * Sets Variable[x] to NN
     * 0x6XNN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void set(byte argument1, byte argument2, byte argument3) {

    }

    private void handleOperation(byte instruction, byte argument1, byte argument2, byte argument3) {
        switch (instruction) {
            case 0x0:
                if (argument1 == 0 && argument2 == 0xE) {
                    if (argument3 == 0) {
                        // Clear screen (0x00E0)
                        clearDisplay();
                    } else if (argument3 == 0xE) {
                        // Return from sub routine (0x00EE)
                        returnFromSubRoutine();
                    }
                } else {
                    // Call (0x0NNN)
                    call(argument1, argument2, argument3);
                }
                break;

            case 0x1:
                jump(argument1, argument2, argument3);
                break;

            case 0x2:
                subRoutine(argument1, argument2, argument3);
                break;

            case 0x3:
                skipEqual(argument1, argument2, argument3);
                break;

            case 0x4:
                skipNotEqual(argument1, argument2, argument3);
                break;

            case 0x5:
                skipEqual(argument1, argument2);
                break;

            case 0x6:
                set(argument1, argument2, argument3);
                break;

            case 0x7:
                break;

            case 0x8:
                break;

            case 0x9:
                break;

            case 0xA:
                break;

            case 0xB:
                break;

            case 0xC:
                break;

            case 0xD:
                break;

            case 0xE:
                break;

            case 0xF:
                break;

        }
    }

    public void tick() {
        short opcode = fetchOpcode();

        // Extract each nibble of the 4 byte buffer
        byte instructionCode = (byte) ((opcode & 0xF000) >> 12);
        byte argument1 = (byte) ((opcode & 0x0F00) >> 8);
        byte argument2 = (byte) ((opcode & 0x00F0) >> 4);
        byte argument3 = (byte) (opcode & 0x000F);

        switch (instructionCode) {
            case 0:

                break;
            default:
                throw new AssertionError();
        }

        String opcodeString = String.format("%04x", opcode & 0xffff);

        if (debug)
            System.out.println(opcodeString + "\t" + String.format("%01x", instructionCode) + "\t"
                    + String.format("%01x", argument1) + "\t" + String.format("%01x", argument2) + "\t"
                    + String.format("%01x", argument3));
    }

    /**
     * Read rom file.
     * 
     * @param file Rom file
     */
    public void loadRom(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            System.out.println("File size: " + inputStream.available());
            int romSize = inputStream.available();
            rom = new byte[romSize];
            inputStream.read(rom);

            if (inputStream.read() != -1) {
                System.out.println("Invalid Chip8 file.");
            } else {
                System.out.println("File loaded");
            }

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
