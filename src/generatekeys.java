import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAKey;
import java.security.spec.InvalidKeySpecException;

import javax.swing.JFileChooser;


public class generatekeys implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser file = new JFileChooser();
		file.setDialogTitle("Save Public key");
		int status = file.showOpenDialog(GUI.mainscreen);
		if (status != JFileChooser.APPROVE_OPTION)
			return;
		File pub_key=file.getSelectedFile();
		JFileChooser file2 = new JFileChooser();
		file2.setDialogTitle("Save Private key");
		int status1 = file2.showOpenDialog(GUI.mainscreen);
		if (status != JFileChooser.APPROVE_OPTION)
			return;
		
		File priv_key=file2.getSelectedFile();
		try {
			MyAWS.rsaKey=RSA.getNewKey(pub_key, priv_key);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException
				| IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(MyAWS.rsaKey.getPublic());
	}

}
