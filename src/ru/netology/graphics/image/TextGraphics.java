package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;

public class TextGraphics implements TextGraphicsConverter{
    protected TextColorSchema schema;
    protected int maxWidth;
    protected int maxHeight;
    protected double maxRatio = 4.0;

    public TextGraphics(TextColorSchema schema){
        setTextColorSchema(schema);
    }
    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));

        if ((double) img.getWidth() /img.getHeight() > maxRatio){
            throw new BadImageSizeException((double) img.getWidth() /img.getHeight(), maxRatio);
        }

        if (img.getWidth() > maxHeight){
            img = resizeImage(img, img.getWidth(), maxHeight);
        }

        if (img.getWidth() > maxWidth) {
            img = resizeImage(img, maxWidth, img.getHeight());
        }

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < img.getHeight(); i++){
            for (int j = 0; j < img.getWidth(); j++){
                // разбиение на RGB
                int num = img.getRGB(j, i);
                int blue =  num & 255;
                int green = (num >> 8) & 255;
                int red =   (num >> 16) & 255;

                // нахождение нужного символа
                answer.append(schema.convert((int) (0.2126 * red + 0.7152 * green + 0.0722 * blue)));
            }
            answer.append('\n');
        }

        return answer.toString();
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
        this.schema = schema;
    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
