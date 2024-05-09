import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawingApp extends JFrame {
    private DrawingPanel drawingPanel;

    public DrawingApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingPanel = new DrawingPanel();
        getContentPane().add(drawingPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawingApp());
    }

    private class DrawingPanel extends JPanel {
        private static final int WIDTH = 400;
        private static final int HEIGHT = 400;

        private int lastX, lastY;
        private boolean dragging;

        private int[][] pixels = new int[WIDTH][HEIGHT];

        public DrawingPanel() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBackground(Color.WHITE);

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    lastX = e.getX();
                    lastY = e.getY();
                    dragging = true;
                }

                public void mouseReleased(MouseEvent e) {
                    dragging = false;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (dragging) {
                        int x = e.getX();
                        int y = e.getY();
                        Graphics g = getGraphics();
                        g.setColor(Color.BLACK);
                        g.drawLine(lastX, lastY, x, y);
                        lastX = x;
                        lastY = y;
                        g.dispose();
                    }
                }
            });
        }

        // Método para convertir el dibujo en una matriz de valores de píxeles
        public int[][] getPixelMatrix() {
            int[][] pixelMatrix = new int[WIDTH][HEIGHT];
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            this.paint(g2d);
            g2d.dispose();

            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    int rgb = image.getRGB(i, j);
                    // Convertir el color a escala de grises
                    int gray = (rgb >> 16) & 0xFF; // Solo tomamos el componente R
                    pixelMatrix[i][j] = gray;
                }
            }

            return pixelMatrix;
        }
    }
}
