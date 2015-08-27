import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class listObjects implements ActionListener {
	int clickindex;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		GUI.listObjects=new DefaultListModel<String>();
		// TODO Auto-generated method stub
		ObjectListing list = MyAWS.client.listObjects(new ListObjectsRequest()
				.withBucketName(MyAWS.bucketName));
		for (S3ObjectSummary objects : list.getObjectSummaries()) {
			System.out.println(objects.getKey());
			
			GUI.listObjects.addElement(objects.getKey());
			
		}
		GUI.list.setModel(GUI.listObjects);
		
		
		
	}

}
