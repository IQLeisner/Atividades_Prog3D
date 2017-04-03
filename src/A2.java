import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ian Quint Leisner on 3/27/2017.
 */
public class A2 {

    public static void main(String [] args) throws IOException {
        A2 ex1 = new A2();
        ex1.run();
    }

    public void run() throws IOException {
        File PATH = new File("C:\\Users\\Ian Quint Leisner\\Pictures\\img\\cor");

        float[][] smooth = new float[][]{
                {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f},
                {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f},
                {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f}};

        float[][] contrast = {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}};

        float[][] pixelate = {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}};

        BufferedImage img1 = ImageIO.read(new File (PATH, "turtle.jpg"));
        BufferedImage contrasted = convolve(img1, contrast);
        BufferedImage pixelated = pixelate(contrasted, 10);
        ImageIO.write(pixelated, "png", new File(PATH, "pixelated.png"));
    }

    public int saturate(int valor){
        if (valor > 255)
            return 255;
        if (valor < 0)
            return 0;
        else
            return valor;
    }

    BufferedImage convolve (BufferedImage img, float[][] kernel){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){

                int xneg = x-1,
                        yneg = y-1,
                        xplus = x+1,
                        yplus = y+1;

                if (xneg < 0 || yneg < 0 || xplus >= img.getWidth() || yplus >= img.getHeight())
                    continue;

                Color a = new Color(img.getRGB(xneg, yneg));
                Color b = new Color(img.getRGB(x, yneg));
                Color c = new Color(img.getRGB(xplus, yneg));
                Color d = new Color(img.getRGB(xplus, yneg));
                Color e = new Color(img.getRGB(x, y));
                Color f = new Color(img.getRGB(xneg, y));
                Color g = new Color(img.getRGB(xneg, yplus));
                Color h = new Color(img.getRGB(x, yplus));
                Color i = new Color(img.getRGB(xplus, yplus));

                Color[][] pixel = new Color[][]{
                        {a, b, c},
                        {d, e, f},
                        {g, h, i}};


                int red = 0;
                int gre = 0;
                int blu = 0;

                for(int k = 0; k < 3; k ++){
                    for (int j = 0; j < 3; j++){
                        red += kernel[k][j] * pixel[k][j].getRed();
                        gre += kernel[k][j] * pixel[k][j].getGreen();
                        blu += kernel[k][j] * pixel[k][j].getBlue();
                    }
                }

                Color fin = new Color(saturate(red), saturate(gre), saturate(blu));
                out.setRGB(x, y, fin.getRGB());
            }
        }
        return out;
    }

    BufferedImage pixelate (BufferedImage img, int size){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < img.getHeight(); y+=size){
            for(int x = 0; x < img.getWidth(); x+=size){
                if (x + size > img.getWidth())
                    x = img.getWidth() - size;
                if (y + size > img.getHeight())
                    y = img.getHeight() - size;
                int cor = img.getRGB(x, y);
                Color pixel = new Color(cor);
                for (int i = 0; i < size; i++){
                    for(int j = 0; j < size; j++) {
                        out.setRGB(x + j, y + i, pixel.getRGB());
                    }
                }
            }
        }
        return out;
    }

}
