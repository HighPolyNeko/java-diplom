package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;

public class TextGraphics implements TextGraphicsConverter{
    protected TextColorSchema schema;
    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));

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

    }

    @Override
    public void setMaxHeight(int height) {

    }

    @Override
    public void setMaxRatio(double maxRatio) {

    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
