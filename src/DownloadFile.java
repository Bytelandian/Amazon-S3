import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFileChooser;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class DownloadFile {
	static MouseListener listlisten = new MouseAdapter() {
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 2) {
				int clickindex = GUI.list.locationToIndex(event.getPoint());
				System.out.println();
				try {
					download(GUI.list.getModel().getElementAt(clickindex));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void download(String elementAt) throws IOException {
			// TODO Auto-generated method stub
			S3Object object = MyAWS.client.getObject(new GetObjectRequest(
					MyAWS.bucketName, elementAt));
			System.out.println(object.getKey());
			InputStream out = object.getObjectContent();
			JFileChooser file = new JFileChooser();
	//		file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int status = file.showOpenDialog(GUI.mainscreen);
			if (status != JFileChooser.APPROVE_OPTION)
				return;

			File saveFile = file.getSelectedFile();
			File tempFile=new File(saveFile+".dec");
	//		String temp = object.getKey();
	//		temp = temp.split("/")[temp.split("/").length - 1];
			FileOutputStream save = new FileOutputStream(tempFile);// + "/" + temp);
			int lenread = 0;
			byte[] bytes = new byte[1024];
			GUI.progress.setValue(0);
			int current = 0;
			int total = (int) object.getObjectMetadata().getContentLength();
			while ((lenread = out.read(bytes)) != -1) {
				int set = ((int) current * 100 / total);
				System.out.println(current + " " + total + " " + set);
				GUI.progress.setValue(set);
				
				Rectangle rectangle = GUI.progress.getBounds();
				rectangle.x = 0;
				rectangle.y = 0;         
				GUI.progress.paintImmediately(rectangle);
				save.write(bytes, 0, lenread);
				current += lenread;
			}
			System.out.println("Done");
			System.out.println(current + " " + total + " " + current / total);
			GUI.progress.setValue(100);
			out.close();
			save.close();
			try {
				RSA.decreptFile(MyAWS.rsaKey.getPrivate(), tempFile,saveFile);
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}