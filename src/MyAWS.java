import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class MyAWS {
	static AmazonS3 client;
	static TransferManager clientmanager;
	static String bucketName;
	static Upload uploadpercent;
	static java.security.KeyPair rsaKey;

	public static void main(String args[]) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		client = new Connect().client;
		clientmanager = new TransferManager(client);
		bucketName = "Bucket Name";
		rsaKey = new RSA().loadKey(new File("/home/mohit/Upload/pub_key"),
				new File("/home/mohit/Upload/pri_key"));
			
		System.out.println(rsaKey.getPrivate());
		new GUI();
	}
}
