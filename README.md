# RTKIT_TT_MEM
Консольное приложение для добавления текста к изображениям.

## Использование приложения
### 1. Загрузка приложения на компьютер
```bash
git clone https://github.com/RUMirNul/RTKIT_TT_MEM
```
### 2. Сборка приложения (Maven)
```bash
mvn clean package
```
### 3. Запуск приложения
Используйте команду в каталоге с собраным приложением из пункта 2:
```bash
java -jar ImageMem-1.0-SNAPSHOT.jar {аргументы запуска}
```

## Аргументы запуска приложения
### Команда help
Выведет в консоль все доступные команды и параметры запуска.
```bash
java -jar ImageMem-1.0-SNAPSHOT.jar help
```

### Команда mem
Добавит текст на изображение.
```bash
java -jar ImageMem-1.0-SNAPSHOT.jar mem {путь_к_изображению} {'текст'} [опциональные_параметры]
```
#### Обязательные параметры
{путь_к_изображению} - обязательный параметр. Путь к файлу с изображением. Поддерживаемые расширения файлов: png, jpeg, bmp. Скобки писать не надо!        
{'текст'} - Обязательный параметр. Текст, который будет добавлен на изображение. Скобки писать не надо!        
Обязательные параметры надо указывать в заданном парадке.         

#### Опциональные параметры
**-f {имя_шрифта}** - желаем шрифт для текста. **По умолчанию:** Dialog. **Пример: -f Arial**       
**-c {hex_цвет}** - цвет текста в формате HEX. **По умолчанию:** Красный. **Пример: -c #a2add0**        
**-s {положительное_целое_число}** - размер текста. **По умолчанию:** 20. **Пример: -s 50**      
**-ts {plain, bold, italic}** - типографический вид текста. Можно выбрать только один вариант. plain - обычный текст, bold - жирный текст, italic - курсивный текст. **По умолчанию:** plain(обычный текст). **Пример: -ts bold**            
**-u** - если указать будет подчеркнутый текст. **По умолчанию:** текст без подчеркивания. **Пример: -ul**          
**-hp {top, middle, bottom}** - расположение текста на изображении. top - сверху изображения, middle по центру изображения, bottom - снизу изображения. **По умолчанию:** bottom (снизу). **Пример: -hp top**          
**-r** - разрешить изменять размер текста, если он не помещается на изображение. **По умолчанию:** выключено. **Пример: -r**          

**Пример полного набора параметров:** mem ./picture.png 'Hello World!' -f Arial -c #ffffff -s 200 -ts bold -hp top -u -r            
Сверху на картинке picture.png напишется текст 'Hello World!' шрифтом Arial, белого цвета, жирными, подчёркнутыми буквами, размером 200 (или меньше, если размер изображения не позволяет вместить весь текст).      

## Зависимости
- Java 8+
- Maven

