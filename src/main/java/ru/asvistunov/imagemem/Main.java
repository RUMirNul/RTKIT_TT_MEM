package ru.asvistunov.imagemem;

public class Main {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Нет параметров запуска.");
        }
        if (args.length == 1 && "help".equalsIgnoreCase(args[0])) {
            System.out.println(help());
        }
        if (args.length >= 3 && "mem".equalsIgnoreCase(args[0])) {
            try {
                ImageEditor imageEditor = new ImageEditor(new Configurator(args));
                imageEditor.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String help() {
        StringBuilder sb = new StringBuilder();
        sb.append("Консольная программа для добавления текста на изображение.\n\n");
        sb.append("Инструкция и доступные команды:\n\n");
        sb.append("Команды:\n");
        sb.append("help - выводит инструкцию по использованию программы.\n");
        sb.append("mem {путь к изображению} {'текст'} [...] - добавляет текст на изображение.\n");
        sb.append("\tРазрешенные расширения изображений: .png, .jpeg, .bmp.\n");
        sb.append("\tВ фигурных скобках {} указаны обязательные параметры и их последовательность. Скобки писать не надо.\n");
        sb.append("\tВместо квадратных скобок можно указать дополнительные параметры. Скобки писать не надо.\n");
        sb.append("\tДополнительные (необязательные) параметры:\n");
        sb.append("\t\t-f {имя_шрифта} - желаем шрифт для текста. По умолчанию - Dialog. Пример: -f Arial\n");
        sb.append("\t\t-c {hex_цвет} - цвет текста в формате HEX. По умолчанию - Красный. Пример: -c #a2add0\n");
        sb.append("\t\t-s {положительное_целое_число} - размер текста. По умолчанию: 20. Пример: -s 50\n");
        sb.append("\t\t-ts {plain, bold, italic} - типографический вид текста. Можно выбрать только один вариант.\n");
        sb.append("\t\tplain - обычный текст, bold - жирный текст, italic - курсивный текст. По умолчанию: plain(обычный текст). Пример: -ts bold\n");
        sb.append("\t\t-u - если указать будет подчеркнутый текст. По умолчанию: текст без подчеркивания. Пример: -ul\n");
        sb.append("\t\t-hp {top, middle, bottom} - расположение текста на изображении.\n");
        sb.append("\t\t top - сверху изображения, middle по центру изображения, bottom - снизу изображения. По умолчанию: bottom (снизу). Пример: -hp top\n");
        sb.append("\t\t-r - разрешить изменять размер текста, если он не помещается на изображение. По умолчанию: выключено. Пример: -r\n");
        sb.append("\n");
        sb.append("\t\tПример полного набора параметров: mem ./picture.png 'Hello World!' -f Arial -c #ffffff -s 200 -ts bold -hp top -u -r\n");
        sb.append("\t\tСверху на картинке picture.png напишется текст 'Hello World!' шрифтом Arial, белого цвета, жирными,\n");
        sb.append("\t\tподчёркнутыми буквами, размером 200 (или меньше, если размер изображения не позволяет вместить весь текст).\n");
        sb.append("\n\n\n\n Developed by Alexey \"RuMirNul\" Svistunov");
        return sb.toString();
    }
}