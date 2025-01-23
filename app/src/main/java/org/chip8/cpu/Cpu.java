package org.chip8.cpu;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.awt.image.BufferedImage;

import org.chip8.display.Gui;

public class Cpu {
    /**
     * 1-F (16) Variables
     */
    private int[] v;

    /**
     * Chip8 memory
     * 0x000 - 0x080 is for the fonts
     * 0x200 - 0xFFF is the main program
     * So PC starts at 0x200
     */
    private int PC = 0x200;
    private byte[] rom;
    private boolean debug = true;
    private BufferedImage gameScreen;

    public Cpu(BufferedImage gameScreen) {
        v = new int[16];
        this.gameScreen = gameScreen;
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

    /**
     * Adds NN to Variable[x]
     * 0x7XNN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void add(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Handle all of the 0x8XYN opcodes
     * 0x8XYN
     * 
     * @param x
     * @param y
     * @param n
     */
    private void handle0x8Opcode(byte x, byte y, byte n) {

        switch (n) {
            case 0x0:
                v[x] = v[y];
                break;

            case 0x1:
                v[x] |= v[y];
                break;

            case 0x2:
                v[x] &= v[y];
                break;

            case 0x3:
                v[x] ^= v[y];
                break;

            case 0x4:
                v[x] += v[y];
                break;

            case 0x5:
                v[x] -= v[y];
                break;

            case 0x6:
                v[x] >>= 1;
                break;

            case 0x7:
                v[x] = v[y] - v[x];
                break;

            case 0xE:
                v[x] <<= 1;
                break;

            default:
                System.out.println("Invalid opcode");
                break;
        }

    }

    /**
     * Skips instruction if V[x] != V[y]
     * 0x9XY0
     * 
     * @param argument1
     * @param argument2
     */
    private void skipNotEqual(byte argument1, byte argument2) {
        if (v[argument1] != v[argument2]) {
            // Skip
        }
    }

    /**
     * Set I to NNN
     * 0xANNN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void setAddress(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Jump to V0 + NNN
     * 0xBNNN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void jumpTo(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * v[x] = random & nn
     * 0xCXNN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void andRandom(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Draw to the screen. v[x], v[y], n
     * 0xDXYN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void draw(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Skip if key pressed
     * 0xEX9E
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void skipKeyPressed(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Skip if key not pressed
     * 0xEXA1
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void skipKeyNotPressed(byte argument1, byte argument2, byte argument3) {

    }

    /**
     * Set v[x] to delay timer
     * 
     * @param x
     */
    private void setvxTimerDelay(byte x) {

    }

    /**
     * Wait for key press
     * v[x] = keycode
     * 
     * @param x v[x]
     */
    private void waitForKeyPress(byte x) {
        int keyPressed = 0x0;
        v[x] = keyPressed;
    }

    /**
     * Set delay timer to v[x]
     * 
     * @param x v[x]
     */
    private void setDelayTimer(byte x) {

    }

    /**
     * Set sound timer to v[x]
     * 
     * @param x v[x]
     */
    private void setSoundTimer(byte x) {

    }

    /**
     * Add v[x] to I
     * 
     * @param x
     */
    private void iaddVx(byte x) {

    }

    /**
     * Sets I to sprite[v[x]]
     * 
     * @param x v[x]
     */
    private void setiSpriteLocation(byte x) {

    }

    /**
     * Store BCD of v[x] in memory locations I, I+1, I+2
     * 
     * @param x v[x]
     */
    private void setBcd(byte x) {

    }

    /**
     * Store v[0] ... v[x] in memory starting at I
     * 
     * @param x v[x]
     */
    private void storeV0toVxInMemory(byte x) {

    }

    /**
     * Read v[0] ... v[x] from memory starting at I
     * 
     * @param x v[x]
     */
    private void readV0toVxFromMemory(byte x) {

    }

    /**
     * Handle all opcodes 0xFXNN
     * 
     * @param argument1
     * @param argument2
     * @param argument3
     */
    private void handle0xFOpcode(byte argument1, byte argument2, byte argument3) {
        short endingNibbles;
        byte opcode_significant_bit = argument2;
        byte opcode_least_significant_bit = argument3;

        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(opcode_significant_bit);
        byteBuffer.put(opcode_least_significant_bit);
        endingNibbles = byteBuffer.getShort(0);

        switch (endingNibbles) {
            case 0x07:
                setvxTimerDelay(argument1);
                break;

            case 0x0A:
                waitForKeyPress(argument1);
                break;

            case 0x15:
                setDelayTimer(argument1);
                break;

            case 0x18:
                setSoundTimer(argument1);
                break;

            case 0x1E:
                iaddVx(argument1);
                break;

            case 0x29:
                setiSpriteLocation(argument1);
                break;

            case 0x33:
                setBcd(argument1);
                break;

            case 0x55:
                storeV0toVxInMemory(argument1);
                break;

            case 0x65:
                readV0toVxFromMemory(argument1);
                break;

            default:
                System.out.println("Invalid opcode");
                break;
        }

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
                add(argument1, argument2, argument3);
                break;

            case 0x8:
                handle0x8Opcode(argument1, argument2, argument3);
                break;

            case 0x9:
                skipNotEqual(argument1, argument2);
                break;

            case 0xA:
                setAddress(argument1, argument2, argument3);
                break;

            case 0xB:
                jumpTo(argument1, argument2, argument3);
                break;

            case 0xC:
                andRandom(argument1, argument2, argument3);
                break;

            case 0xD:
                draw(argument1, argument2, argument3);
                break;

            case 0xE:
                if (argument2 == 0x9 && argument3 == 0xE) {
                    skipKeyPressed(argument1, argument2, argument3);

                } else if (argument2 == 0xA && argument3 == 0x1) {
                    skipKeyNotPressed(argument1, argument2, argument3);
                } else {
                    System.out.println("Invalid opcode");
                }

                break;

            case 0xF:
                handle0xFOpcode(argument1, argument2, argument3);
                break;

            default:
                System.out.println("Invalid opcode");
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

        // Handle operation and execute associated instruction
        handleOperation(instructionCode, argument1, argument2, argument3);

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
