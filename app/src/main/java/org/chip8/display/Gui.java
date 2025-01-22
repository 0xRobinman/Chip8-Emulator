package org.chip8.display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;

public class Gui extends JFrame {

    private BufferedImage gameScreen;
    private Canvas gameCanvas;
    private BufferStrategy bs;
    private Graphics g;
    private boolean romInserted = false;
    private final String NO_ROM = "Please insert ROM";

    private JMenuBar getMenu() {

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");

        file.add(new JMenuItem("Open ROM"));

        menubar.add(file);
        return menubar;
    }

    public boolean romInserted() {
        return romInserted;
    }

    public void noRomInsertedScreen() {
        g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(NO_ROM, (int) ((this.getWidth() / 2) - ((NO_ROM.length() / 2) * 10)) - 10,
                (int) (0.5 * (this.getHeight()) - 40));
        bs.show();
    }

    public void renderGame() {
        g = bs.getDrawGraphics();
        bs.show();
    }

    private Canvas createCanvas(int width, int height) {
        Canvas canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        return canvas;
    }

    private File getInputFile() {
        JFileChooser fileSelect = new JFileChooser("");

        fileSelect.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    String filename = file.getName().toLowerCase();
                    return filename.endsWith(".ch8");
                }
            }

            @Override
            public String getDescription() {
                return "Chip8 files (*.ch8)";
            }

        });
        fileSelect.setAcceptAllFileFilterUsed(false);

        if (fileSelect.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            romInserted = true;
            return fileSelect.getSelectedFile();
        }
        return null;
    }

    public Gui(int width, int height) {
        this.setTitle("Chip8 Emulator - 0xRobinman");
        gameCanvas = createCanvas(width, height);
        gameScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.add(gameCanvas);
        this.pack();
        this.setJMenuBar(getMenu());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameCanvas.createBufferStrategy(2);
        bs = gameCanvas.getBufferStrategy();

    }
}
