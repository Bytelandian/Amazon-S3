import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class GUI {
	public static JFrame mainscreen;
	private JButton uploadfile;
	private JButton uploadfolder;
	private JButton rsakeys;
	private JButton genkeys;
	private JButton listfiles;
	public static JLabel label;
	public static JProgressBar progress;
	private JPanel panel;
	public static JList<String> list;
	public static DefaultListModel<String> listObjects;

	GUI() {
		mainscreen = new JFrame("AWS application");
		mainscreen.setSize(520, 520);
		progress = new JProgressBar(0, 100);
		label = new JLabel();
		uploadfile = new JButton("Choose File");
		uploadfolder = new JButton("Choose Folder");
		listfiles = new JButton("List Objects on S3");
		rsakeys=new JButton("Provide RSA Keys");
		genkeys=new JButton("Generate new RSA keys");
		rsakeys.addActionListener(new updatersakeys());
		genkeys.addActionListener(new generatekeys());
		
		uploadfile.addActionListener(new uploadFile());
		listfiles.addActionListener(new listObjects());
		uploadfolder.addActionListener(new uploadfolder());
		listObjects = new DefaultListModel<String>();
		list = new JList<String>(listObjects);
		progress.setStringPainted(true);
		GUI.list.addMouseListener(DownloadFile.listlisten);
		panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(rsakeys);
		panel.add(genkeys);
		panel.add(uploadfile);
		panel.add(uploadfolder);
		panel.add(listfiles);
		panel.add(label);
		panel.add(list);
		panel.add(progress);
		mainscreen.setContentPane(panel);
		mainscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainscreen.setVisible(true);
	}
}
