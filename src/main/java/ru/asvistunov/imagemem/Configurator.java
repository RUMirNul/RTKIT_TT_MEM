package ru.asvistunov.imagemem;

import java.awt.*;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Преобразует входные параметры в конфигурацию для дальнейшего использования в ImageEditor.
 */
public class Configurator {
    private final String path;
    private final String filename;
    private final String text;
    private String fontName = "Dialog";
    private Color color = Color.RED;
    private int fontSize = 20;
    private String typographicSelection = "plain";
    private boolean underline = false;
    private TextVerticalPosition verticalPosition = TextVerticalPosition.BOTTOM;
    private boolean checkTextOutOfBounds = false;
    private int textEndIndex = 0;

    public Configurator(String[] config) {
        this.path = setPath(config[1]);
        this.filename = setFilename(config[1]);
        this.text = setText(config);
        proccessingAdditionalParameters(config);
    }

    private static String setPath(String fullPath) {
        return Paths.get(fullPath).toAbsolutePath().normalize().getParent().toString();
    }

    private static String setFilename(String fullPath) {
        return Paths.get(fullPath).getFileName().toString();
    }

    /**
     * Из входных параметров получаем строку текста, которую потом поместим на изображение.
     */
    private String setText(String[] config) {
        StringBuilder sb = new StringBuilder();
        boolean isTextStart = false;
        int i = 2;
        for (; i < config.length; i++) {
            String str = config[i];
            if (isTextStart && str.endsWith("'")) {
                sb.append(str, 0, str.length() - 1);
                break;
            }
            if (isTextStart) {
                sb.append(str);
                sb.append(" ");
                continue;
            }
            if (str.startsWith("'")) {
                if (str.endsWith("'")) {
                    sb.append(str, 1, str.length() - 1);
                    break;
                }
                sb.append(str, 1, str.length());
                sb.append(" ");
                isTextStart = true;
            }
        }
        textEndIndex = i;
        return sb.toString();
    }

    /**
     * Рассматривает дополнительные параметры и изменяет конфигурацию.
     */
    private void proccessingAdditionalParameters(String[] config) {
        for (int i = textEndIndex + 1; i < config.length; i++) {
            String str = config[i];
            switch (str) {
                case "-f":
                    setFontName(config[i + 1]);
                    break;
                case "-c":
                    Color c = hexToRgb(config[i + 1]);
                    if (c != null) color = c;
                    break;
                case "-s":
                    setSize(config[i + 1]);
                    break;
                case "-ts":
                    setTypographicSelection(config[i + 1]);
                    break;
                case "-u":
                    underline = true;
                    break;
                case "-hp":
                    setVerticalPosition(config[i + 1]);
                    break;
                case "-r":
                    checkTextOutOfBounds = true;
                    break;
            }
        }
    }

    private void setFontName(String str) {
        if (Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).contains(str)) {
            fontName = str;
        }
    }

    private static Color hexToRgb(String hexColor) {
        if (!hexColor.matches("^#([A-Fa-f0-9]{6})$")) return null;
        return new Color(
                Integer.valueOf(hexColor.substring(1, 3), 16),
                Integer.valueOf(hexColor.substring(3, 5), 16),
                Integer.valueOf(hexColor.substring(5, 7), 16)
        );
    }

    private void setSize(String strSize) {
        try {
            if (fontSize <=0) throw new Exception("Неверный размер шрифта");
            fontSize = Integer.parseInt(strSize);
        } catch (Exception e) {
            //ignore
        }
    }

    private void setTypographicSelection(String str) {
        if (str.equalsIgnoreCase("bold")) typographicSelection = "bold";
        if (str.equalsIgnoreCase("italic")) typographicSelection = "italic";
    }

    private void setVerticalPosition(String str) {
        for (TextVerticalPosition position : TextVerticalPosition.values()) {
            if (position.getPosition().equalsIgnoreCase(str)) {
                verticalPosition = position;
                break;
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String getFilename() {
        return filename;
    }

    public String getText() {
        return text;
    }

    public String getFontName() {
        return fontName;
    }

    public Color getColor() {
        return color;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getTypographicSelection() {
        if (typographicSelection.equalsIgnoreCase("plain")) return Font.PLAIN;
        if (typographicSelection.equalsIgnoreCase("bold")) return Font.BOLD;
        if (typographicSelection.equalsIgnoreCase("italic")) return Font.ITALIC;
        return Font.PLAIN;
    }

    public boolean isUnderline() {
        return underline;
    }

    public TextVerticalPosition getVerticalPosition() {
        return verticalPosition;
    }

    public boolean isCheckTextOutOfBounds() {
        return checkTextOutOfBounds;
    }
}
