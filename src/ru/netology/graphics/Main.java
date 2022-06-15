package ru.netology.graphics;
import ru.netology.graphics.image.Converted;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {


        TextGraphicsConverter converter = new Converted(); // Создайте тут объект вашего класса конвертера
        //if((converter.getWidth() / converter.getHeight()) >  )

 //       converter.setMaxRatio(2);// передали максимальное соотношение ширины к длине


        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
        //String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
        //String imgTxt = converter.convert(url);
        //System.out.println(imgTxt);

    }
}
