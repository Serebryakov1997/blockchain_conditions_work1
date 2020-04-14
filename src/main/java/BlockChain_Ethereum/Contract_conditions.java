package BlockChain_Ethereum;


public class Contract_conditions {

    /** Законтрактовано **/
    private final static Double total_EE = 1554.2;//необходимо передать ЭЭ (кВт)
    private final static Double total_Eth = 466.26;//необходимо получить эфиров

    private double fine_EE;// Штраф
    private double kwthCost = 0.3;//Стоимость 1 кВт




    public static void main(String[] args) throws Exception {

//        new Contract_conditions();


    }

//    private BlockChain blockChain = new BlockChain();

//    public Contract_conditions() throws Exception {
//        System.out.println("In total : " + "\nTransmitted energy : " + blockChain.getSum_EE()
//        + "\nReceived Ethers : " + blockChain.getSum_Eth());
//
//        System.out.println("Was contracted : " + "\nTransmit energy : " + total_EE
//                + "\nReceive Ethers : " + total_Eth);
//
//        double dif_EE = total_EE - blockChain.getSum_EE();    // непоставленная ЭЭ
//        double much_EE = blockChain.getSum_EE() - total_EE;   // количество переданной ЭЭ сверх плана
//        double dif_sum = total_Eth - blockChain.getSum_Eth(); // невыплаченная сумма
//        double pay_EE = blockChain.getSum_Eth() + much_EE * kwthCost; // оплата за перевыполнение плана
//
//        /** 1) Если потребитель не выплатил указанную в контракте
//         *  сумму, то на него накладывается штраф в размере
//         *  20 % сверх невыплаченной суммы
//         *
//         *  2) Если поставщик перевыполнил план поставки ЭЭ,
//         *  то потребитель оплачивает ЭЭ по первоначальному тарифу
//         *
//         *  3) Если поставщик не выполнил план поставки ЭЭ
//         *  или если потребитель перестал потреблять ЭЭ,
//         *  то на одну из сторон накладывается штраф в размере
//         *  20 % сверх стоимости непоставленной ЭЭ
//         **/
//        if (blockChain.getSum_Eth() < total_Eth){
//            fine_EE = dif_sum + 0.2 * total_Eth;
//            System.out.println("Consumer didn't pay " + total_Eth + "," + "\nConsumer pays fine : " + fine_EE);
//        }
//
//        if(blockChain.getSum_EE() > total_EE) {
//            System.out.println("Transmitted energy : " + blockChain.getSum_EE() + " > than in total" + total_EE);
//            System.out.println("Consumer pays for energy : " + pay_EE);
//        }
//
//        if(blockChain.getSum_EE() < total_EE){
//            fine_EE = dif_EE * 0.3 + 0.2 * total_Eth;
//            System.out.println("Transmitted energy : " + blockChain.getSum_EE() + " < than in total" + total_EE);
//            System.out.println("One of the parties pays fine : " + fine_EE);
//        }
//
//        if(blockChain.getSum_Eth() == total_Eth && blockChain.getSum_EE() == total_EE) {
//            System.out.println("Transmitted energy : " + blockChain.getSum_EE() + " = in total" + total_EE);
//            System.out.println("Consumer paid the required amount of ethers : " + total_Eth);
//            System.out.println("\u001B[32m" + "Contract terms are fulfilled successfully !!!" + "\u001B[37m");
//        }
//    }
}




