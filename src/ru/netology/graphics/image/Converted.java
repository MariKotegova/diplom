package ru.netology.graphics.image;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import java.awt.image.BufferedImage;


public class Converted implements TextGraphicsConverter{

    private int newWidth;// ширина
    private int newHeight; // высота
    private int maxWidth = 0;// ширина
    private int maxHeight = 0; // высота
    private double maxRatio = 0; // соотношение длины и высоты


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int Width = img.getWidth();
        int Height =img.getHeight();

        //если соотношение сторон больше заданного, то выкинь исключение
        if((Width / Height) > maxRatio && maxRatio != 0){
            throw new BadImageSizeException(maxRatio, (Width / Height));
        }

        // если есть максимальная высота и ширина
        if (maxWidth != 0 || maxHeight != 0){
            int x = 1;// ширина, соотношение
            int y = 1;// высота, соотношение
            if (maxWidth != 0 && maxWidth < Width){
                x = Width / maxWidth;}
            if (maxHeight != 0 && maxHeight < Height){
                y = Height / maxHeight;
            }

            double q = (x >= y) ? x : y;
            newWidth = (int) Math.round(Width / q);
            newHeight = (int) Math.round(Height / q);
        } else {
            this.newWidth = Width;
            this.newHeight = Height;
        }


        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);

        WritableRaster bwRaster = bwImg.getRaster();
// заполняем массив символами
        String [][] shemaColor = new String[newWidth][newHeight];
        ColorsShem schema = new ColorsShem();

        for (int i = 0 ; i < newWidth; i++ ) {
            for (int j = 0 ; j < newHeight; j++) {
                int color = bwRaster.getPixel(i, j, new int[3])[0];
                char c = schema.convert(color);
                shemaColor[i][j] = Character.toString(c) + Character.toString(c);
            }
        }
// Выводим эти символы на экран

        StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i < newWidth; i++ ) {
            for (int j = 0 ; j < newHeight; j++) {
                sb.append(shemaColor[i][j]);
            }
            sb.append("\n");
        }
        String string = sb.toString();
        return string;
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

    }
}
