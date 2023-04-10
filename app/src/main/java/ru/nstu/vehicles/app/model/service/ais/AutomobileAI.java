package ru.nstu.vehicles.app.model.service.ais;


public class AutomobileAI extends BaseAI {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);

                synchronized (this) {
                    while (isSuspended())
                        wait();
                }

                System.out.println("AutomobileAI work");
            } catch (InterruptedException e) {
            }
        }
    }
}
