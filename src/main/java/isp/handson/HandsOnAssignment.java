package isp.handson;

import javax.crypto.Cipher;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class HandsOnAssignment {
    public static void main(String[] args) throws Exception {

        final BlockingQueue<byte[]> alice2bob = new LinkedBlockingQueue<>();
        final BlockingQueue<byte[]> bob2alice = new LinkedBlockingQueue<>();

        final Agent alice = new Agent("alice", alice2bob, bob2alice, null, null) {
            @Override
            public void execute() throws Exception {
                String msg = "Hi Bob, it's Alice!";
                outgoing.put(msg.getBytes());
                byte[] PT = msg.getBytes("UTF-8");
                //final Cipher cipherGen = Cipher.getInstance();
                //cipherGen.init(Cipher.ENCRYPT_MODE,key);
                //byte[] CT = cipherGen.doFinal();
                //byte[] IV = cipherGen.getIV();
            }
        };

        final Agent bob = new Agent("bob", bob2alice, alice2bob, null, null) {
            @Override
            public void execute() throws Exception {
                final byte[] pt = incoming.take();
                print("Got '%s'", new String(pt));
            }
        };

        alice.start();
        bob.start();
    }
}
