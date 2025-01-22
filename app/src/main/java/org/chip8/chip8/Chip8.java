package org.chip8.chip8;

import org.chip8.cpu.Cpu;

public class Chip8 {

    private Cpu cpu;
    private final int FPS = 60;
    private final double FRAME_INTERVAL = 1.0 / FPS;

    /**
     * Everything that must be done in a single frame.
     */
    private void handleFrame() {

    }

    public void startEmulator() {
        cpu = new Cpu();

        boolean dropFrame = false;
        double currentTime = 0, previousTime = System.nanoTime() / 1e9, deltaTime = 0, accumulatedTime = 0;

        while (true) {

            currentTime = System.nanoTime() / 1e9;
            deltaTime = currentTime - previousTime;
            previousTime = currentTime;
            accumulatedTime += deltaTime;

            for (dropFrame = !(accumulatedTime >= FRAME_INTERVAL); !dropFrame
                    && accumulatedTime >= FRAME_INTERVAL; accumulatedTime -= FRAME_INTERVAL)
                ;

            handleFrame();
        }

    }

    public static void main(String[] args) {
        Chip8 chip8 = new Chip8();
        chip8.startEmulator();
        System.out.println("Hello, Chip8!");
    }
}
