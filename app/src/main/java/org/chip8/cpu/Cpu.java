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
    private Gui gui;
    private int PC = 0x200;
    private byte[] rom;

    public Cpu(Gui gui) {
        v = new int[16];
        this.gui = gui;
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

    public void tick() {
        short opcode = fetchOpcode();
        System.out.println("Opcode: " + opcode);
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
