package org.chip8.chip8;

import org.chip8.cpu.Cpu;
import org.chip8.display.Gui;

public class Chip8 implements Runnable {

    private Cpu cpu;
    private Gui gui;
    private final int WIDTH = 64, HEIGHT = 32;
    private final int FPS = 60;
    private final double FRAME_INTERVAL = 1.0 / FPS;
    private int counter = 0;

    /**
     * Everything that must be done in a single frame.
     */
    private void handleFrame() {
        if (gui.isKeyPressed()) {
            cpu.setKeyPressed(gui.getKeyCode());
        } else {
            cpu.setKeyPressed(-1);
        }

        if (gui.getPause() != cpu.getPause()) {
            cpu.setPause(gui.getPause());
        }
        cpu.tick();

        // For every 11 CPU ticks, the clocks go down by 1.
        if (counter % 11 == 0)
            cpu.handleTimers();
        counter++;
        gui.renderGame();
    }

    @SuppressWarnings("empty-statement")
    public void gameLoop() {
        gui = new Gui(WIDTH, HEIGHT);
        cpu = new Cpu(gui.getGameScreen());
        cpu.attachDebugText(gui.getDebugText());
        boolean dropFrame = false;
        double currentTime = 0,
                previousTime = System.nanoTime() / 1e9,
                deltaTime = 0,
                accumulatedTime = 0;
        boolean romLoaded = false;

        while (true) {

            currentTime = System.nanoTime() / 1e9;
            deltaTime = currentTime - previousTime;
            previousTime = currentTime;
            accumulatedTime += deltaTime;

            for (dropFrame = !(accumulatedTime >= FRAME_INTERVAL); !dropFrame
                    && accumulatedTime >= FRAME_INTERVAL; accumulatedTime -= FRAME_INTERVAL)
                ;

            if (romLoaded) {
                handleFrame();
            } else if (!gui.romInserted()) {
                gui.noRomInsertedScreen();
            } else {
                if (gui.getLoadedRom() != null) {
                    cpu.loadRom(gui.getLoadedRom());
                    romLoaded = true;
                }
            }
            gui.renderDebug();

            if (dropFrame)
                easeUsage();

        }
    }

    private void easeUsage() {
        try {

            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {
        gameLoop();
    }

    public static void main(String[] args) {
        Chip8 chip8 = new Chip8();
        Thread t = new Thread(chip8);
        t.start();
    }

}
