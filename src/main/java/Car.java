import java.util.concurrent.CyclicBarrier;



public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private int numberOfStage;
    static CyclicBarrier cb = new CyclicBarrier(MainClass.CARS_COUNT);

    public void decNumberOfStage(){
        numberOfStage--;
    }

    public int getNumberOfStage(){
        return numberOfStage;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        numberOfStage = race.getStages().size();
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.cdlStart.countDown();
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
            if (this.getNumberOfStage() == 0){
                MainClass.win(this);
            }
        }
    }
}
