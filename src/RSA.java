import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RSA {
	/*
	 * public static void main(String args[]) throws NoSuchAlgorithmException,
	 * NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
	 * InvalidKeyException, IOException, InvalidKeySpecException {
	 * 
	 * java.security.KeyPair rsa; // rsa = getNewKey();
	 * 
	 * // encryptFile(aeskey, rsa.getPublic(), "/home/mohit/Upload/Text"); rsa =
	 * loadKey();
	 * 
	 * decreptFile(rsa.getPrivate(), "/home/mohit/Upload/TextEncFile"); }
	 */
	public static java.security.KeyPair loadKey(File Pub_Key_File,
			File Pri_Key_File) throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		// TODO Auto-generated method stub
		FileInputStream pub_key = new FileInputStream(Pub_Key_File);
		byte[] pub = new byte[(int) Pub_Key_File.length()];
		pub_key.read(pub);

		pub_key.close();
		FileInputStream pri_key = new FileInputStream(new File(
				"/home/mohit/Upload/pri_key"));
		byte[] pri = new byte[(int) new File("/home/mohit/Upload/pri_key")
				.length()];
		pri_key.read(pri);
		pri_key.close();

		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pr = kf.generatePrivate(new PKCS8EncodedKeySpec(pri));
		PublicKey pb = kf.generatePublic(new X509EncodedKeySpec(pub));

		return new java.security.KeyPair(pb, pr);

	}

	public static java.security.KeyPair getNewKey(File pub_file, File priv_file)
			throws NoSuchAlgorithmException, IOException,
			InvalidKeySpecException {
		// TODO Auto-generated method stub
		KeyPairGenerator rsaKey = KeyPairGenerator.getInstance("RSA");
		rsaKey.initialize(2048);

		java.security.KeyPair rsa = rsaKey.genKeyPair();

		byte pub[] = rsa.getPublic().getEncoded();
		byte pri[] = rsa.getPrivate().getEncoded();

		FileOutputStream pub_key = new FileOutputStream(pub_file);
		pub_key.write(pub);
		pub_key.close();

		FileOutputStream pri_key = new FileOutputStream(priv_file);
		pri_key.write(pri);
		pri_key.close();

		return rsa;
	}

	public static void decreptFile(PrivateKey private1, File file1,File file2)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		// TODO Auto-generated method stub

		FileInputStream inpFile = new FileInputStream(file1);

		FileOutputStream decFile = new FileOutputStream(file2);

		Cipher decpt = Cipher.getInstance("RSA");
		decpt.init(Cipher.DECRYPT_MODE, private1);
		byte[] read = new byte[256];
		int readlen;
		readlen = inpFile.read(read);
		System.out.println(readlen);
		System.out.println(read);
		// System.out.println(encpt.doFinal(read));
		byte[] dec = decpt.doFinal(read);

		SecretKey aes = new SecretKeySpec(dec, 0, dec.length, "AES");

		decpt = Cipher.getInstance("AES");
		decpt.init(Cipher.DECRYPT_MODE, aes);
		read = new byte[64];
		byte[] out;
		while ((readlen = inpFile.read(read)) != -1) {
//			out=decFile.u(decpt.doFinal());
			out=decpt.update(read, 0, readlen);
			if (out!=null)
				decFile.write(out);
		}
		out=decpt.doFinal();
		if (out!=null)
			decFile.write(out);
		inpFile.close();
		decFile.close();
	}

	public static File encryptFile(PublicKey public1, File filename)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, IOException {
		// TODO Auto-generated method stub

		KeyGenerator aesKey = KeyGenerator.getInstance("AES");
		aesKey.init(128);
		SecretKey aeskey = aesKey.generateKey();

		FileInputStream inpFile = new FileInputStream(filename);
		File out = new File(filename.getName() + ".enc");
		FileOutputStream encFile = new FileOutputStream(out);
		Cipher encpt = Cipher.getInstance("RSA");
		encpt.init(Cipher.ENCRYPT_MODE, public1);

		encFile.write(encpt.doFinal(aeskey.getEncoded()));

		encpt = Cipher.getInstance("AES");
		encpt.init(Cipher.ENCRYPT_MODE, aeskey);
		byte[] read = new byte[64];
		byte[] outq=null;
		int readlen;
		while ((readlen = inpFile.read(read)) != -1) {
			
			outq=encpt.update(read, 0, readlen);
			if (outq!=null)
				encFile.write(outq);
		}
		outq = encpt.doFinal();
		if (outq!=null)
			encFile.write(outq);
		inpFile.close();
		encFile.close();
		return out;
	}
}
