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

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class uploadfolder implements ActionListener {

	int numFiles = 0;
	int uploadFiles = 0;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		JFileChooser file = new JFileChooser();
		file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int status = file.showOpenDialog(GUI.mainscreen);
		if (status != JFileChooser.APPROVE_OPTION)
			return;
		countFiles(file.getSelectedFile());
		try {
			downloadFolder(file.getSelectedFile(), file.getSelectedFile()
					.getName());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}

	}

	private void countFiles(File selectedFile) {
		// TODO Auto-generated method stub
		File[] listFile = selectedFile.listFiles();
		for (File f : listFile) {
			if (f.isDirectory()) {
				countFiles(f);
			} else {
				numFiles += 1;
			}
		}
	}

	private void downloadFolder(File Dir, String Path)
			throws InterruptedException {
		// TODO Auto-generated method stub
		File[] listFile = Dir.listFiles();

		for (File f : listFile) {
			if (f.isDirectory()) {
				downloadFolder(f, Path + "/" + f.getName());
			} else {
				downloadFile(f, Path + "/" + f.getName());
				uploadFiles += 1;
			}
		}
	}

	private void downloadFile(File f, String Path) {
		// TODO Auto-generated method stub
		System.out.println(Path);
		ProgressListener progresslisten = new ProgressListener() {
			public void progressChanged(ProgressEvent progressEvent) {
				if (MyAWS.uploadpercent == null)
					return;
				
					
					GUI.label.setText("Uploading File " + uploadFiles + " of "
							+ numFiles);
				
					GUI.progress.setValue((int) MyAWS.uploadpercent.getProgress()
							.getPercentTransferred());

			}
		};
		File uploadfile = f;
		try {
			uploadfile=RSA.encryptFile(MyAWS.rsaKey.getPublic(), uploadfile);
			System.out.println("ghj");
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PutObjectRequest request = new PutObjectRequest(MyAWS.bucketName, Path+".enc",
				uploadfile).withGeneralProgressListener(progresslisten);
		GUI.label.setText("Uploaded File " + uploadFiles + " of " + numFiles);
		MyAWS.uploadpercent = MyAWS.clientmanager.upload(request);
	}
}