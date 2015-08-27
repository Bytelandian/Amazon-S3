import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class uploadFile implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		JFileChooser file = new JFileChooser();
		int status = file.showOpenDialog(GUI.mainscreen);
		if (status != JFileChooser.APPROVE_OPTION)
			return;
		ProgressListener progresslisten = new ProgressListener() {
			public void progressChanged(ProgressEvent progressEvent) {
				if (MyAWS.uploadpercent == null)
					return;
				GUI.progress.setValue((int) MyAWS.uploadpercent.getProgress()
						.getPercentTransferred());

			}
		};
		File uploadfile = file.getSelectedFile();
		
		try {
			uploadfile=RSA.encryptFile(MyAWS.rsaKey.getPublic(), uploadfile);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PutObjectRequest request = new PutObjectRequest(MyAWS.bucketName,
				uploadfile.getName(), uploadfile)
				.withGeneralProgressListener(progresslisten);

		MyAWS.uploadpercent = MyAWS.clientmanager.upload(request);
		
	}

}