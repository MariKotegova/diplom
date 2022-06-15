package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import java.awt.image.BufferedImage;


public class Converted implements TextGraphicsConverter {

    private int newWidth;// ширина
    private int newHeight; // высота
    private int maxWidth = 0;// ширина
    private int maxHeight = 0; // высота
    private double maxRatio = 0; // соотношение длины и высоты

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int width = img.getWidth();
        int height = img.getHeight();

        //если соотношение сторон больше заданного, то выкинь исключение
        if ((width / height) > maxRatio && maxRatio != 0) {
            throw new BadImageSizeException(maxRatio, (width / height));
        }

        // если есть максимальная высота и ширина
        if (maxWidth != 0 || maxHeight != 0) {
            ratio(width, height);
        } else {
            this.newWidth = width;
            this.newHeight = height;
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();

        // заполняем массив символами  и выводим картинку на экран
        String[][] shemaColor = new String[newWidth][newHeight];
        String string = print(shemaColor, bwRaster);
        return string;
    }

    // если есть максимальная высота и ширина
    public void ratio (int width, int height){
        int proportionsWidht = 1;// ширина, соотношение
        int proportionsHeight = 1;// высота, соотношение
        if (maxWidth != 0 && maxWidth < width) {
            proportionsWidht = width / maxWidth;
        }
        if (maxHeight != 0 && maxHeight < height) {
            proportionsHeight = height / maxHeight;
        }

        double proportions = (proportionsWidht >= proportionsHeight) ? proportionsWidht : proportionsHeight;
        newWidth = (int) Math.round(width / proportions);
        newHeight = (int) Math.round(height / proportions);
    }

    // заполнить массив символами и вывести на экран
    public String print (String[][] shemaColor, WritableRaster bwRaster){
        ColorsShem schema = new ColorsShem();

        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                int color = bwRaster.getPixel(i, j, new int[3])[0];
                char c = schema.convert(color);
                shemaColor[i][j] = Character.toString(c) + Character.toString(c);
            }
        }

        // Выводим эти символы на экран
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                sb.append(shemaColor[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
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
