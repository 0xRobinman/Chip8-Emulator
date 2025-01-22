package org.chip8.display;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Canvas;

public class Window extends JFrame {

    private BufferedImage gameScreen;
    private Canvas gameCanvas;

    private JMenuBar getMenu() {

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");

        file.add(new JMenuItem("Open ROM"));

        menubar.add(file);
        return menubar;
    }

    public void noRomInsertedScreen() {
        // Display black screen with 'no rom inserted' message.
    }

    private void setGameCanvas(int width, int height) {
        gameCanvas = new Canvas();
        gameCanvas.setPreferredSize(new Dimension(640, 320));
        gameCanvas.setMaximumSize(new Dimension(640, 320));
        gameCanvas.setMinimumSize(new Dimension(640, 320));
    }

    private JPanel getMainScreen(int width, int height) {
        JPanel mainScreen = new JPanel();

        mainScreen.add(gameCanvas);

        return mainScreen;
    }

    public Window(int width, int height) {

        this.setTitle("Chip8 Emulator - 0xRobinman");

        this.setGameCanvas(width, height);
        this.add(getMainScreen(width, height));

        this.setJMenuBar(getMenu());
        this.setSize(getPreferredSize());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
