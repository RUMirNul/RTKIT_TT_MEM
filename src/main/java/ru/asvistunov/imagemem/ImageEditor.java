package ru.asvistunov.imagemem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Arrays;
import java.util.List;

/**
 * Добавляет текст на изображение. Зависит от конфигурации Configuration.
 */
public class ImageEditor {
    private final Configurator config;
    private final List<String> whitelistFileExtensions = Arrays.asList("png", "jpeg", "bmp");

    public ImageEditor(Configurator config) {
        this.config = config;
    }

    /**
     * Метод, который запускает процесс добавления текста на изображение.
     * @throws Exception - ошибки, возникшие во время выполнения работы программы.
     */
    public void execute() throws Exception {
        String fileExtension = config.getFilename().substring(config.getFilename().lastIndexOf(".") + 1);
        if (!whitelistFileExtensions.contains(fileExtension)) {
            throw new Exception("Не поддерживаемое расширение файла или файл не найден.");
        }

        Font font = new Font(config.getFontName(), config.getTypographicSelection(), config.getFontSize());

        BufferedImage image;
        try {
            image = ImageIO.read(new File(config.getPath() + File.separator + config.getFilename()));
        } catch (IOException e) {
            throw new Exception("Ошибка при работе с файлом.");
        }

        Graphics g = image.getGraphics();
        if (config.isCheckTextOutOfBounds()) {
            font = resizeText(image.getHeight(), image.getWidth(), font, g.getFontMetrics(font));
        }
        FontMetrics metrics = g.getFontMetrics(font);
        Coordinates position = getPosition(image.getHeight(), image.getWidth(), metrics);

        AttributedString atrText = new AttributedString(config.getText());
        atrText.addAttribute(TextAttribute.FONT, font);
        atrText.addAttribute(TextAttribute.FOREGROUND, config.getColor());
        if (config.isUnderline()) atrText.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        g.drawString(atrText.getIterator(), position.getPositionX(), position.getPositionY());
        g.dispose();
        ImageIO.write(image, fileExtension, new File(config.getPath()
                + File.separator
                + (config.getFilename().replace(".", "_mem."))));
    }

    /**
     * Проверяет размер текста и изменяет его, если текст выходит за границы изображения.
     * @param imageHeight - высота изображения.
     * @param imageWidth - ширина изображения.
     * @param font - шрифт текста.
     * @param fontMetrics - метрика шрифта.
     * @return переданный шрифт, если текст не выходит за пределы изображения, иначе возвращает шрифт с изменённым размером текста.
     */
    private Font resizeText(int imageHeight, int imageWidth, Font font, FontMetrics fontMetrics) {
        double expectedWidth = fontMetrics.stringWidth(config.getText());
        double expectedHeight = fontMetrics.getHeight();

        if (imageWidth <= expectedWidth || imageHeight <= expectedHeight) {
            double widthBasedFontSize = imageWidth / expectedWidth * font.getSize();
            double heightBasedFontSize = imageHeight / expectedHeight * font.getSize();
            int newFontSize = (int) Math.min(widthBasedFontSize, heightBasedFontSize);
            return new Font(font.getName(), font.getStyle(), newFontSize);
        }
        return font;
    }

    /**
     * Возвращает координаты изображения, где надо отобразить текст в зависимости от параметров.
     * @param imageHeight - высота изображения.
     * @param imageWidth - ширина изображения.
     * @param fontMetrics - метрика шрифта.
     * @return координаты, где отобразить текст
     */
    private Coordinates getPosition(int imageHeight, int imageWidth, FontMetrics fontMetrics) {
        int positionX = (imageWidth - fontMetrics.stringWidth(config.getText())) / 2;
        int positionY = (imageHeight - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        if (config.getVerticalPosition() == TextVerticalPosition.TOP) {
            positionY = fontMetrics.getAscent();
        }
        if (config.getVerticalPosition() == TextVerticalPosition.BOTTOM) {
            positionY = imageHeight - fontMetrics.getHeight() + fontMetrics.getAscent();
        }
        return new Coordinates(positionX, positionY);
    }

    private static class Coordinates {
        private final int positionX;
        private final int positionY;

        public Coordinates(int positionX, int positionY) {
            this.positionX = positionX;
            this.positionY = positionY;
        }

        public int getPositionX() {
            return positionX;
        }

        public int getPositionY() {
            return positionY;
        }
    }
}
