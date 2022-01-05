import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel{
    private BufferedImage image;
    private JFrame window;
    private Graphics2D g;

    public static final int IMAGE_WIDTH  = 2000;
    public static final int IMAGE_HEIGHT = 2000;

    public static final int WINDOW_WIDTH  = 1000;
    public static final int WINDOW_HEIGHT = 1000;



    private final Renderer renderer = new Renderer();

    public Window() {
        window = new JFrame("Newton Fractal");
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        window.add(this);
        window.setVisible(true);
    }




    @Override
    public void paint(Graphics gr) {
        if(g == null) {
            image = (BufferedImage) createImage(IMAGE_WIDTH, IMAGE_HEIGHT);
            g = (Graphics2D) image.getGraphics();

            g.setColor(new Color (46, 46, 50));
            g.fillRect(0, 0, image.getWidth(), image.getHeight());

            renderer.render(g);

            try {
                ImageIO.write(image, "png", new File("images/fractal.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gr.drawImage(image.getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_DEFAULT), 0, 0, this);
    }



    public static void main(String[] args) {
        new Window();
    }
}