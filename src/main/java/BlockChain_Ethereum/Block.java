package BlockChain_Ethereum;

public class Block {

    public String hash;
    public String previousHash;

    private int nonce;
    private String dataBlock;




    public Block(String dataBlock, String previousHash) throws Exception {

        this.dataBlock = dataBlock;
        this.previousHash = previousHash;
//        this.allTime = System.currentTimeMillis() - start;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedHash = Hash.sha256(
                previousHash + nonce + dataBlock
        );
        return calculatedHash;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0','0');
        while (!hash.substring(0,difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined !!! : " + hash);
//        System.out.println("\u001B[33m" + " data.getProgress() : " + contract.getProgress() + "\u001B[37m");
    }

}
