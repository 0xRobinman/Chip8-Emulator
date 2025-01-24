package org.chip8.display;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class Gui extends JFrame implements KeyListener {
    private final int SCALE = 15;
    private final String DEBUG_FILE = "debug.txt";
    private BufferedImage gameScreen;
    private Canvas gameCanvas, debugCanvas;
    private BufferStrategy bs, debugbs;
    private Graphics g, debugGraphics;
    private boolean romInserted = false;
    private final String NO_ROM = "Please insert ROM";
    private File loadedRom;
    private ScrollPane debugPane;
    private static final int MAX_LINES = 128;
    private JLabel debugLines[];
    private JPanel debugPanel;
    private int currentKeyCode;
    private boolean keyPressed = false;
    private LinkedList<String> debugText;
    private File debugFile;
    private Writer debugWriter;

    public int getKeyCode() {
        return currentKeyCode;
    }

    public boolean isKeyPressed() {
        return keyPressed;
    }

    private JMenuBar getMenu() {

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem openRom = new JMenuItem("Open ROM");
        file.add(openRom);

        openRom.addActionListener((ActionEvent e) -> {
            loadedRom = getInputFile();
        });

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
        g.drawImage(gameScreen, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight(), null);
        bs.show();
    }

    /**
     * Render the debug screen.
     * Format $addr $opcode
     */
    public void renderDebug() {
        debugGraphics = debugbs.getDrawGraphics();
        debugGraphics.setColor(Color.BLUE);
        debugGraphics.fillRect(0, 0, debugCanvas.getWidth(), debugCanvas.getHeight());
        debugGraphics.setColor(Color.WHITE);
        debugGraphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        int i = 0;
        for (String line : debugText) {
            debugGraphics.drawString(line, 0, i * 12);
            i++;
            if (i * 12 > debugCanvas.getHeight()) {
                String removed_debug = debugText.removeFirst();
                try {
                    debugWriter.write(removed_debug + " \n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i--;
                break;
            }
        }
        debugbs.show();
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

    public File getLoadedRom() {
        return loadedRom;
    }

    public BufferedImage getGameScreen() {
        return gameScreen;
    }

    public LinkedList<String> getDebugText() {
        return debugText;
    }

    /**
     * Clear the debug file automatically generated by the app
     */
    private void clearDebugFile() {
        debugFile = new File(DEBUG_FILE);
        try {
            if (!debugFile.createNewFile()) {
                try {
                    Files.newBufferedWriter(Paths.get(DEBUG_FILE), StandardOpenOption.TRUNCATE_EXISTING);
                } catch (IOException e) {
                    System.out.println("File does not exist. Writing file");

                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not create new file");
            e.printStackTrace();
        }
        try {
            debugWriter = new BufferedWriter(new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(DEBUG_FILE), "utf-8")));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Gui(int width, int height) {
        loadedRom = null;
        debugText = new LinkedList<>();
        clearDebugFile();
        this.setTitle("Chip8 Emulator - 0xRobinman");
        gameCanvas = createCanvas(width, height);
        gameScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        gameCanvas.addKeyListener(this);
        debugCanvas = createCanvas(200, height);

        this.setPreferredSize(new Dimension(width * SCALE, height * SCALE));
        this.setSize(new Dimension(width * SCALE, height * SCALE));

        // Create border layout IF debug.
        // Main screen | debug screen
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(gameCanvas, BorderLayout.CENTER);
        panel.add(debugCanvas, BorderLayout.EAST);
        this.add(panel);
        // this.add(gameCanvas);
        this.pack();
        this.setJMenuBar(getMenu());
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setVisible(true);
        this.update(gameCanvas.getGraphics());
        this.update(debugCanvas.getGraphics());
        gameCanvas.createBufferStrategy(2);
        bs = gameCanvas.getBufferStrategy();
        debugCanvas.createBufferStrategy(2);
        debugbs = debugCanvas.getBufferStrategy();
    }

    /**
     * The CHIP 8 only has 16 keys, 0x0 - 0xF
     * However, for the sake of ease, we will also map the 'WASD' and arrow keys.
     * 
     * @param keyCode
     * @return
     */
    private int getMapedKeyCode(int keyCode) {
        return keyCode;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentKeyCode = getMapedKeyCode(e.getKeyCode());
        keyPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // If a key is released, and it's not the current key, ignore it since another
        // key is being pressed.
        if (e.getKeyCode() != currentKeyCode) {
            return;
        } else {
            keyPressed = false;
            currentKeyCode = -1;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ignore this one.
    }
}
