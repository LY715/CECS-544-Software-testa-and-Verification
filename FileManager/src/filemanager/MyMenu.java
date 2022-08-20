package filemanager;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.SwingUtilities;
import javax.swing.JTextPane;


public class MyMenu {
	
	GUI mainGuiHandle;
	JMenuItem MIrename = new JMenuItem("Rename");
	JMenuItem MIcopy = new JMenuItem("Copy");
	MIDelete MIdelete = new MIDelete("Delete");
	JMenuItem MIpaste = new JMenuItem("Paste");
	JButton ok;
	JButton cancel;
	JTextField fromText;
	JTextField toText;
	Path path;
	String barTitle = "Rename";
	JTextPane textPane;
	JDialog nameDialog;
	GUI internal;
	File  copyPasteFile;
	public MyMenu(GUI frame) {
		
		mainGuiHandle = frame;
		nameDialog = new JDialog();
		JPanel totalPanel = new JPanel(new GridLayout(2, 6));
		JPanel DirPanel = new JPanel();

		JPanel labelpane = new JPanel();
		
		JPanel fromPane = new JPanel();
		JLabel to = new JLabel("To: ");
		fromPane.add(to);
		to.setFont(new Font("Calibri", Font.BOLD, 15));
		
		DirPanel.setSize(10, 10);
		
		nameDialog.getContentPane().add(totalPanel);
		nameDialog.getContentPane().add(DirPanel, BorderLayout.NORTH);
		
		textPane = new JTextPane();
		if(MIdelete!=null && MIdelete.myFile!=null)
			textPane.setText("Current Directory: "+ MIdelete.myFile.getParent());
		textPane.setEditable(false);
		DirPanel.add(textPane);
		totalPanel.add(labelpane);
		
		JLabel from = new JLabel("From: ");
		labelpane.add(from);
		from.setFont(new Font("Calibri", Font.BOLD, 15));
		
		fromText = new JTextField(30);
		labelpane.add(fromText);
		
		ok = new JButton("Ok");
		
		labelpane.add(ok);
		totalPanel.add(fromPane);
		toText = new JTextField(30);
		fromPane.add(toText);
		cancel = new JButton("Cancel");
		fromPane.add(cancel);
		
		nameDialog.setBounds(500, 400, 550, 250);
		nameDialog.setSize(550, 144);
		nameDialog.setTitle(barTitle);
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File source, dest;
				source = new File(MIdelete.myFile.getParent()+ "\\" + fromText.getText());
				if(toText.getText().length()==0)  //This is an empty / null file. Copy the file for pasting.
					{
						mainGuiHandle.setCopyPasteFile(source);//return
					}
				else 
				{
					dest = new File(toText.getText());
				
					try {
						if(barTitle == "Rename") 
						Files.move(source.toPath(), dest.toPath());

					else
						Files.copy(source.toPath(), dest.toPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(totalPanel.getParent(),"Operation Failed");
					}
				}
				nameDialog.dispose();
				mainGuiHandle.RefreshRightPane(mainGuiHandle.getfocusedframe());
			}
		});
		
		
		MIrename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				barTitle="Rename";
				nameDialog.setTitle(barTitle);
				setupwindow();
				
				
			}
		});
		
		MIcopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				barTitle="Copy";
				nameDialog.setTitle(barTitle);
				setupwindow();
				
			}
			
		});
		MIpaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyPasteFile = mainGuiHandle.getCopyPasteFile();
				if(copyPasteFile!=null)
				{
					File file =  mainGuiHandle.getSelectedFile();
					Path parent = file.toPath().getParent();
					Path destPath = Paths.get(parent.toString() + "\\",copyPasteFile.getName());
					try {
						Files.copy(copyPasteFile.toPath(), destPath);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mainGuiHandle.RefreshRightPane(mainGuiHandle.getfocusedframe());
				}
			}
		});
		MIdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				int result = JOptionPane.showConfirmDialog(MIdelete.getParent(),"Delete: "+ MIdelete.myFile.getAbsolutePath(), "Deleting!!!",
			               JOptionPane.YES_NO_OPTION,
			               JOptionPane.QUESTION_MESSAGE);
				if(result== 0)
				{
					try {
						Files.delete(MIdelete.myFile.toPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mainGuiHandle.RefreshRightPane(mainGuiHandle.getfocusedframe());
				}
			}
		});
	}
	
	public void setupwindow()
	{
		File myFile;
		
		myFile = mainGuiHandle.getSelectedFile();
		if(myFile!=null)
		{
			textPane.setText("Current Directory: "+ myFile.getParent());
			fromText.setText(myFile.getName());
		}
		nameDialog.setVisible(true);
	}
	public class MIDelete extends JMenuItem
	{
		private File myFile;
		public void setLastFilename(File fileName)
		{
			myFile = fileName;
		}
		public MIDelete(String name)
		{
			super.setText(name);
		}
	}
}
