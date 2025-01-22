package org.chip8.display;

public class Gui {
    private Window window;
    private final int WIDTH = 640, HEIGHT = 320;

    public Gui() {
        window = new Window(WIDTH, HEIGHT);
    }
}
