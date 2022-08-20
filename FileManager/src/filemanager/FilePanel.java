package filemanager;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;


public class FilePanel extends JPanel{
	IconListRenderer iconListRendererSimple;
	IconListRenderer iconListRendererDetailed;
	JList list = new JList();
	DefaultListModel model = new DefaultListModel();
	File selectedFile;
	File[] files;
	DefaultMutableTreeNode LastNode;
	GUI mainHandle;
	public FilePanel(GUI frame ,DefaultMutableTreeNode node) {
		mainHandle = frame;
		list.setPreferredSize(new Dimension(500, 500));
		this.setDropTarget(new MyDropTarget());
		list.setDragEnabled(true);
		list.setModel(model);
		add(list);
		LastNode = node;
		iconListRendererSimple = new IconListRenderer(true);
		iconListRendererDetailed = new IconListRenderer(false);
		MyMenu mm = new MyMenu(mainHandle);
		JPopupMenu menu = new JPopupMenu("Menu");
		menu.add(mm.MIrename);
		menu.add(mm.MIcopy);
		menu.add(mm.MIdelete);
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
					JList list = (JList)e.getSource();
					File[] list2 = new File[files.length];
					int emptyIndex=0;
		            int row = list.locationToIndex(e.getPoint());
		            list.setSelectedIndex(row);
		            
		    		for(int i = 0; i < files.length; i++) {
		    			if(!files[i].isHidden()) {
		    				list2[emptyIndex++] = files[i];
		    			}
		    		}
		    		//list.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		    		//list.setModel(model);
		    		mainHandle.setSelectedFile(list2[row]);
		            mm.MIdelete.setLastFilename(list2[row]);
		            menu.show(e.getComponent(), e.getX(), e.getY());		
				}
				else if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					JList list = (JList)e.getSource();
					File[] list2 = new File[files.length];
					int emptyIndex=0;
		            int row = list.locationToIndex(e.getPoint());
		            list.setSelectedIndex(row);
		            
		    		for(int i = 0; i < files.length; i++) {
		    			if(!files[i].isHidden()) {
		    				list2[emptyIndex++] = files[i];
		    			}
		    		}
		    		try {
						Desktop.getDesktop().open(list2[row]);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				  }
				else if(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
					JList list = (JList)e.getSource();
					if(files!=null)
					{
					File[] list2 = new File[files.length];
					int emptyIndex=0;
		            int row = list.locationToIndex(e.getPoint());
		            list.setSelectedIndex(row);
		            
		    		for(int i = 0; i < files.length; i++) {
		    			if(!files[i].isHidden()) {
		    				list2[emptyIndex++] = files[i];
		    			}
		    		}
		    		mainHandle.setSelectedFile(list2[row]);
					}
				}
			}
		});		
	}

	public void emptyList()
	{
		model.clear();
		list.removeAll();
	}
	
	public void fillList(boolean isSimpleMode, File dir) 
	{
		if(isSimpleMode)
			fillListSimple(dir);
		else
			fillListDetail(dir);
	}
	
	private void fillListSimple(File dir) {
		files = dir.listFiles();
		model.clear();
		list.removeAll();
		for(int i = 0; i < files.length; i++) {
			if(!files[i].isHidden()) {
				model.addElement(files[i]);
			}
		}
		list.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		list.setModel(model);
		list.setCellRenderer(iconListRendererSimple);
		
	}
	
	private void fillListDetail(File dir) {
		files = dir.listFiles();
		model.clear();
		list.removeAll();
		for(int i = 0; i < files.length; i++) {
			if(!files[i].isHidden()) {
				model.addElement(files[i]);
			}
		}
		list.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		list.setModel(model);
		list.setCellRenderer(iconListRendererDetailed);
	}
	
	public class IconListRenderer extends DefaultListCellRenderer {
	    public boolean isSimple;
		public IconListRenderer(boolean isSimple) {
	    	if(isSimple)
	    		this.isSimple = true;
	    	else
	    		this.isSimple = false;
	    }
		public Component getListCellRendererComponent(
	            JList list, Object value, int index,
	            boolean isSelected, boolean cellHasFocus) {
	        JLabel label = (JLabel) super.getListCellRendererComponent(
	                list, value, index, isSelected, cellHasFocus);
	        Icon icon = this.getIcon(list, value, index, isSelected, cellHasFocus);
	        File filename = (File)value;
	        label.setIcon(icon);
	        if(isSimple)
	        	label.setText(filename.getName());
	        else
	        {
	    		String fullentry, name, date, size="";
	    		Date d;
	    		SimpleDateFormat format;
				d = new Date(filename.lastModified());
				name = filename.getName();
				if (name.length()> 35)
				{
					name = name.substring(0,31) + "...";
				}
				format = new SimpleDateFormat("MM-dd-YYYY"); 
				date = format.format(d);
				if(filename.length()>0)
				{
					size = String.valueOf(filename.length());
					size = String.format("%-20s", size);
				}
				name = String.format("%-35s", name);
		        date = String.format("%-20s", date);
		        fullentry = name + date + size;
		        label.setText(fullentry);
	        }
	        return label;
	    }
	    protected Icon getIcon(
	            JList list, Object value, int index,
	            boolean isSelected, boolean cellHasFocus) {
	    	return FileSystemView.getFileSystemView().getSystemIcon((File)value);
	    }
	}
	
	class MyDropTarget extends DropTarget {
		
		public void drop (DropTargetDropEvent evt) {
			try {
				evt.acceptDrop(DnDConstants.ACTION_COPY);
				List result = new ArrayList();
			
				if(evt.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)) {
					String temp = (String) evt.getTransferable().getTransferData(DataFlavor.stringFlavor);
					String[] next = temp.split("\\n");
					for(int i = 0; i < next.length; i++) {
						model.addElement(next[i]);
					}
				}
				else 
				{
					result = (List) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					for(Object o : result) {
						System.out.println(o.toString());
						model.addElement(o.toString());
					}
				}
			}
		
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
