import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ian Quint Leisner on 4/3/2017.
 */
public class A3 {

        public static void main(String [] args) throws IOException {
            A3 ex1 = new A3();
            ex1.run();
        }

        public void run() throws IOException {
            File PATH = new File("C:\\Users\\Ian Quint Leisner\\Pictures\\img\\gray");

            BufferedImage img1 = ImageIO.read(new File (PATH, "car.png"));
            BufferedImage equal = equalize(img1);
            ImageIO.write(equal, "png", new File(PATH, "carequalized.png"));
        }

        public int saturate(int valor){
            if (valor > 255)
                return 255;
            if (valor < 0)
                return 0;
            else
                return valor;
        }

        BufferedImage equalize (BufferedImage img){
            BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

            //criando histograma vazio
            int[] hist = new int[255];
            for (int i = 0; i < 255; i++){
                hist[i] = 0;
            }

            //preenchendo histograma
            for(int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int cor = img.getRGB(x, y);
                    Color c = new Color(cor);
                    int n = c.getRed();
                    hist[n]++;
                }
            }

            //criando histograma acumulado
            int[]hist_a = new int [255];
            hist_a[0] = hist[0];

            //preenchendo histograma acumulado
            for(int i = 1; i < 255; i++){
                hist_a[i] = (hist_a[i-1] + hist[i]);
            }

            //determinando h mínimo
            int hmin = 0;
            for (int i = 0; i < 255; i++){
                if(hist[i]!=0){
                    hmin = hist[i];
                    break;
                }
            }

            //aplicando formula equalizacao
            int[] novo = new int[255];
            for (int i = 0; i < 255; i++){
                novo[i] = (Math.round(((hist_a[i] - hmin) / ((float)img.getHeight() * img.getWidth()))* (256 - 1)) );
                saturate(novo[i]);
            }

            //equalizando imagem
            for(int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int cor = img.getRGB(x, y);
                    Color c = new Color(cor);
                    int g = novo[c.getRed()];
                    c = new Color(g, g, g);

                    out.setRGB(x, y, c.getRGB());
                }
            }

            return out;

        }
}
