import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ian Quint Leisner on 3/27/2017.
 */
public class A1 {

    int[] pallete64 = {
            0x000000, 0x00AA00, 0x0000AA, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xAAAA00, 0xAAAAAA,
            0x000055, 0x0000FF, 0x00AA55, 0x00AAFF, 0xAA0055, 0xAA00FF, 0xAAAA55, 0xAAAAFF,
            0x005500, 0x0055AA, 0x00FF00, 0x00FFAA, 0xAA5500, 0xAA55AA, 0xAAFF00, 0xAAFFAA,
            0x005555, 0x0055FF, 0x00FF55, 0x00FFFF, 0xAA5555, 0xAA55FF, 0xAAFF55, 0xAAFFFF,
            0x550000, 0x5500AA, 0x55AA00, 0x55AAAA, 0xFF0000, 0xFF00AA, 0xFFAA00, 0xFFAAAA,
            0x550055, 0x5500FF, 0x55AA55, 0x55AAFF, 0xFF0055, 0xFF00FF, 0xFFAA55, 0xFFAAFF,
            0x555500, 0x5555AA, 0x55FF00, 0x55FFAA, 0xFF5500, 0xFF55AA, 0xFFFF00, 0xFFFFAA,
            0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF
    };

    public static void main(String [] args) throws IOException {
        A1 a1 = new A1();
        a1.run();
    }

    public void run() throws IOException {
        File PATH = new File("C:\\Users\\Ian Quint Leisner\\Pictures\\img\\cor");
        BufferedImage pup = ImageIO.read(new File(PATH, "puppy.png"));
        BufferedImage pup64 = convert(pup);
        ImageIO.write(pup64, "png", new File(PATH, "pup64.png"));
    }


    BufferedImage convert(BufferedImage img){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){
                double tdist = 0;
                double menor = 1000000;
                Color corfinal = new Color(0, 0, 0);
                int cor = img.getRGB(x, y);
                Color cor1 = new Color(cor);
                for (int i = 0; i < pallete64.length; i++){
                    Color cor2 = new Color(pallete64[i]);
                    double rdist = (Math.pow(cor1.getRed() - cor2.getRed(), 2));
                    double gdist = (Math.pow(cor1.getGreen() - cor2.getGreen(), 2));
                    double bdist = (Math.pow(cor1.getBlue() - cor2.getBlue(), 2));
                    tdist = Math.sqrt(rdist + gdist + bdist);
                    if (tdist < menor) {
                        corfinal = cor2;
                        menor = tdist;
                    }
                }
                out.setRGB(x, y, corfinal.getRGB());
            }
        }
        return out;
    }
}
