package BlockChain_Ethereum;

import java.util.concurrent.atomic.AtomicInteger;

public class NewSmartContract {


    private double sendProgress = 0;

    //constant : kwth per hour
    double speed = 120;
    // the whole contract in kwth(общее количество энергии в рамках контракта, которое мы передаем, 0,5 - это в секунду)
    double contractBody = 0.5;
    //energy cost per kwthhour in ether(стоимость энергии за кВт в эфирах)
//    double kwthCost = 0.3;

//    private List<Double> progresses = new ArrayList<>();

    public static void main(String[] args) throws Exception {
       new NewSmartContract();
    }

    long period = 1;

    public NewSmartContract() throws Exception {

        AtomicInteger progressInt = new AtomicInteger(0);
        Runnable prog = () -> {
            while (progressInt.get() / 100.0 <= 107) {
                progressInt.addAndGet((int) (period / (3600 * contractBody / speed) * 100 * 100));
//                System.out.println("prgoress " + progressInt.get() / 100.0);

                sendProgress =  progressInt.get() / 100.0;

                setSendProgress(sendProgress);


                try {
                    Thread.sleep(period * 3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread sendThread = new Thread(prog);
        sendThread.start();

    }

    public double getSendProgress() {
        return sendProgress;
    }

    public void setSendProgress(double sendProgress) {
        this.sendProgress = sendProgress;
    }



}
