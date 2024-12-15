import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import utils.AESUtils;

public class Main {
    private static final String KEY = "__Simple__Hwid__"; // 加密密钥

    public static void main(String[] args) {
        try {
            setClipboardString(getSHA(AESUtils.encrypt(getHWID(), KEY)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(getSHA(AESUtils.encrypt(getHWID(), KEY)));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        JOptionPane.showMessageDialog(null, "Your HWID has been copied to your clipboard!");
    }

    /**
     * 将字符串赋值到系统粘贴板
     * 
     * @param data 要复制的字符串
     */
    public static void setClipboardString(String data) {
        java.awt.Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new java.awt.datatransfer.StringSelection(data),
                        null);
    }

    /*
     * 获取硬件ID
     */
    private static String getHWID() throws NoSuchAlgorithmException {
        final StringBuilder s = new StringBuilder();
        final String main = System.getenv("PROCESS_IDENTIFIER") + System.getenv("COMPUTERNAME")
                            + System.getProperty("os.name") + System.getProperty("os.version");
        final byte[] bytes = main.getBytes(StandardCharsets.UTF_8);
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        final byte[] hash = messageDigest.digest(bytes);
        for (final byte b : hash) {
            s.append(String.format("%02x", b));
        }
        return s.toString();
    }

    /**
     * 获取SHA-256加密后的字符串
     * 
     * @param str 要加密的字符串
     */
    private static String getSHA(String str) throws NoSuchAlgorithmException {
        final StringBuilder s = new StringBuilder();
        final byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        final byte[] hash = messageDigest.digest(bytes);
        for (final byte b : hash) {
            s.append(String.format("%02x", b));
        }
        return s.toString();
    }
}
