package ru.netology.graphics.image;

public class BadImageSizeException extends Exception {
    //Класс исключения, которое вы будете выбрасывать
    public BadImageSizeException(double ratio, double maxRatio) {
        super("Максимальное соотношение сторон изображения " + maxRatio + ", а у этой " + ratio);
    }
}
