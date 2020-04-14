package BlockChain_Ethereum;

import com.google.gson.GsonBuilder;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockChain {


    private boolean isActive = true; //флаг для состояния работы потока
    private NewSmartContract contract = new NewSmartContract();
    //    public String dataBlock = Double.toString(contract.getTransmitEE() + contract.getPriceEE());
//    Block block = new Block(dataBlock, "0");
    private static int difficulty = 4;
    private final static String PRIVATE_KEY = "90ea334ef00a63d294f31fed4a0a9f068a373fff0f4c1e0527fb6eade02ce65e";
    private final static String RECIPIENT = "0xaC2A315Ff043eb7dE62cE818485aE28e7AC42E5D"; // Получатель эфиров(мы)
    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static long GAS_PRICE = 200000000000L;


    public static void main(String[] args) throws Exception {
        new BlockChain();

    }


    private List<Block> blocks = new ArrayList<>();
    private List<Double> progresses = new ArrayList<>();
    private double number_Ethers = 5; // это количество эфиров уножаем на дельта прогресс, столько будем получать
    private List<Double> send_EE = new ArrayList<>();//массив переданной энергии
    private List<Double> rec_Ethers = new ArrayList<>();//массив полученных эфиров
    private List<Double> mas_sum_EE = new ArrayList<>();
    private List<Double> mas_sum_Eth = new ArrayList<>();
    private double kwthCost = 0.3;          //0,3 эфира за 1 кВт
    private double sum_EE = 0;
    private double sum_Eth = 0;



//    private double sum_Ethers = 36; // 36 эфиров за 120 кВт


    public BlockChain() throws Exception, UnsupportedOperationException {

        Runnable contr = () -> {

            while (blocks.size()-1 < 14) {
                progresses.add(contract.getSendProgress());

                int i;
                double del_prog = 0;
                for (i = 1; i < progresses.size(); i++) {
                    del_prog = progresses.get(i) - progresses.get(i - 1);
                    if((int) del_prog == 13 ||  (int) del_prog == 19.98) {
//                        !!! Из-за того что данные выдаются потоками ( и происходит обмен между потоками), один из потоков может
//                        вставать в очередь первее другого. Поэтому задано такое условие для значения дельта прогресс, чтобы не было ошибки в контракте
                        del_prog = 6.66;
                    }
                }
                try {
                    rec_Ethers.add(number_Ethers * del_prog);
                    send_EE.add((number_Ethers * del_prog)/kwthCost);
                } catch (UnsupportedOperationException e) {
                    e.printStackTrace();
                }

                setSend_EE(send_EE);
                setRec_Ethers(rec_Ethers);

                if(blocks.size() == 14){
                    sum_Data();
                    contract_Conditions();
                }


                /** Будем связываться с клиентом по этому веб адресу**/

                Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));

                TransactionManager transactionManager = new RawTransactionManager(
                        web3j,
                        getCredentialsFromPrivateKey()
                );

                Transfer transfer = new Transfer(web3j, transactionManager);

                try {
                    transfer.sendFunds(
                            RECIPIENT,
                            BigDecimal.valueOf(number_Ethers * del_prog),
                            Convert.Unit.ETHER,
                            BigInteger.valueOf(GAS_PRICE),
                            GAS_LIMIT
                    ).send();


                    if (blocks.size() >= 1) {
                        blocks.add(new Block("Received  " + number_Ethers * del_prog + "  Delta_Progress  " + del_prog,
                                blocks.get(blocks.size() - 1).hash));
                    } else  {

                            blocks.add(new Block("Received  " + number_Ethers * del_prog + "  Delta_Progress  " + del_prog,
                                    "0"));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                for (i = 0; i < blocks.size(); i++) {
                    blocks.get(i).mineBlock(difficulty);
                }
                System.out.println("Mining of the block " + i);

                System.out.println("\nBlockchain is Valid: " + checkBlock());


                String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blocks);
                System.out.println("\nThe blockchain : ");
                System.out.println(blockchainJson);


                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        };

        Thread thread = new Thread(contr);
        thread.start();
    }


    private Boolean checkBlock() {

        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //Цикл, проверяющий хэши

        for (int i = 1; i < blocks.size(); i++) {
            currentBlock = blocks.get(i);
            previousBlock = blocks.get(i - 1);


            // сравнить зарегистрированный хэш и вычисленный хэш:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return !isActive;
            }
//            // сравнить предыдущий хэш и зарегистрированный предыдущий хэш
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return !isActive;
            }
//
            //проверка, рассчитан ли хэш
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                ;
                System.out.println("This block hasn't been mined");
                return !isActive;
//
            }
        }
        return isActive;


    }

    private void contract_Conditions() {

        /** Законтрактовано **/
         final double total_EE = 1554;//необходимо передать ЭЭ (кВт) 1554
         final double total_Eth = 466;//необходимо получить эфиров   466
         double fine_EE;// Штраф
        System.out.println();
        System.out.println("In total : " + "  Transmitted energy  " + (int) getSum_EE()
                + " , Received Ethers  " + (int)getSum_Eth());

        System.out.println("Was contracted : " + "  Transmit energy  " + total_EE
                + " , Receive Ethers  " + total_Eth);

        double dif_EE = total_EE - (int)getSum_EE();    // непоставленная ЭЭ
        double much_EE = (int)getSum_EE() - total_EE;   // количество переданной ЭЭ сверх плана
        double dif_sum = total_Eth - (int)getSum_Eth(); // невыплаченная сумма
        double pay_EE = (int)getSum_Eth() + much_EE * kwthCost; // оплата за перевыполнение плана

        /** 1) Если потребитель не выплатил указанную в контракте
         *  сумму, то на него накладывается штраф в размере
         *  20 % сверх невыплаченной суммы
         *
         *  2) Если поставщик перевыполнил план поставки ЭЭ,
         *  то потребитель оплачивает ЭЭ по первоначальному тарифу
         *
         *  3) Если поставщик не выполнил план поставки ЭЭ
         *  или если потребитель перестал потреблять ЭЭ,
         *  то на одну из сторон накладывается штраф в размере
         *  20 % сверх стоимости непоставленной ЭЭ
         **/
        if ((int)getSum_Eth() < total_Eth){
            fine_EE = dif_sum + 0.2 * total_Eth;
            System.out.println("Consumer didn't pay " + total_Eth + "," + "\nConsumer pays fine : " + fine_EE);
            System.err.println("The terms of the contract are not fulfilled!!!");
            System.out.println();
        }

        if((int)getSum_EE() > total_EE) {
            System.out.println("Transmitted energy : " + (int)getSum_EE() + "  more than in total " + total_EE);
            System.out.println("Consumer pays for energy : " + pay_EE);
            System.err.println("The terms of the contract are not fulfilled!!!");
            System.out.println();
        }

        if((int)getSum_EE() < total_EE){
            fine_EE = dif_EE * 0.3 + 0.2 * total_Eth;
            System.out.println("Transmitted energy : " + (int)getSum_EE() + " less than in total " + total_EE);
            System.out.println("One of the parties pays fine : " + fine_EE);
            System.err.println("The terms of the contract are not fulfilled!!!");
            System.out.println();
        }

        if((int)getSum_Eth() == total_Eth && (int)getSum_EE() == total_EE) {
            System.out.println("Transmitted energy : " + (int)getSum_EE() + " equally to contracted " + total_EE);
            System.out.println("Received Ethers : " + (int)getSum_Eth() + " equally to contracted " + total_Eth);
            System.out.println("\u001B[32m" + "Contract terms are fulfilled successfully !!!" + "\u001B[37m");
            System.out.println();
        }
    }

    /** Метод для суммирования ЭЭ и эфиров **/
    private void sum_Data(){

        // Цикл для нахождения суммы массива ЭЭ
        for ( double el : getSend_EE()) {
            sum_EE += el;
        }
        setSum_EE(sum_EE);
//        System.out.println("sum_EE : " + sum_EE);


        // Цикл для нахождения суммы массива эфиров
        for ( double el : getRec_Ethers()) {
            sum_Eth += el;
        }
        setSum_Eth(sum_Eth);
//        System.out.println("sum_Eth : " + sum_Eth);
    }


    private Credentials getCredentialsFromPrivateKey () {
        return Credentials.create(PRIVATE_KEY);
    }

    public List<Double> getSend_EE() {
        return send_EE;
    }

    public void setSend_EE(List<Double> send_EE) {
        this.send_EE = send_EE;
    }

    public List<Double> getRec_Ethers() {
        return rec_Ethers;
    }

    public void setRec_Ethers(List<Double> rec_Ethers) {
        this.rec_Ethers = rec_Ethers;
    }

    public double getSum_EE() {
        return sum_EE;
    }

    public void setSum_EE(double sum_EE) {
        this.sum_EE = sum_EE;
    }

    public double getSum_Eth() {
        return sum_Eth;
    }

    public void setSum_Eth(double sum_Eth) {
        this.sum_Eth = sum_Eth;
    }
}
