import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MainClass {
    public static final int CARS_COUNT = 4;
    public static final CountDownLatch cdlStart = new CountDownLatch(CARS_COUNT);
    public static final CountDownLatch cdlFinish = new CountDownLatch(CARS_COUNT);
    private static Lock lock = new ReentrantLock();
    public static boolean flag = true;


    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40), new Tunnel());
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (Car car : cars) {
            new Thread(car).start();
        }

        try {
            cdlStart.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        try {
            cdlFinish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

    public static void win(Car c) {
        try {
            lock.lock();
            if (flag) {
                System.out.println(c.getName() + " WIN");
                flag = false;
            }
            cdlFinish.countDown();
        } finally {
            lock.unlock();
        }
    }
}

