package filemanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class MyInternalFrame extends  JInternalFrame{
	DefaultMutableTreeNode root;
	InternalFrameListener internalFrameListener = new MyListener();
	JSplitPane splitPane = new JSplitPane();
	JScrollPane scrollPaneleft = new JScrollPane();
	JScrollPane scrollPaneright = new JScrollPane();
	JDesktopPane desktopPane;
	GUI mainGUIHandle;
	MyInternalFrame mainInternalFrameHandle = this;
	File fileRoot;
	FileNode filenode;
	File selectedFile;
	private static boolean IsSimpleMode = true;
	private JTree tree = new JTree();
	public MyInternalFrame(GUI mainGUI , JDesktopPane desktopPane)
	{
		super();
		mainGUIHandle=mainGUI;
	super.setBounds(0, 0, 500, 400);
	try {
		super.setSelected(true);
	} catch (PropertyVetoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	mainGUIHandle.setFocus(this, tree);
	super.setClosable(true);
	super.setResizable(true);
	super.setMaximizable(true);
	super.setIconifiable(true);
	desktopPane.add(this, BorderLayout.CENTER);

	
	splitPane.setEnabled(false);
	splitPane.setResizeWeight(0.2);
	super.getContentPane().add(splitPane);
	super.setVisible(true);
	fileRoot = new File("C:\\");
	filenode = new FileNode(fileRoot);
	root = new DefaultMutableTreeNode(filenode);
	tree = new JTree(root);
			JMenu MTree = new JMenu("Tree");
			JMenuItem MIexpand = new JMenuItem("Expand Branch");
			MTree.add(MIexpand);
			MIexpand.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					expandTree(tree, true);
				}
			});
			
			JMenuItem MIcollapse = new JMenuItem("Collapse Branch");
			MTree.add(MIcollapse);
			MIcollapse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					expandTree(tree, false);
				}
			});
	//
	// scrollPane
	scrollPaneleft = new JScrollPane(tree);
	scrollPaneleft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	splitPane.setLeftComponent(scrollPaneleft);
	scrollPaneright.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	splitPane.setRightComponent(scrollPaneright);
	
	super.addInternalFrameListener(internalFrameListener);
	
	// tree action
	tree.addTreeSelectionListener(new MyTreeSelectionListener());
	tree.setShowsRootHandles(true);
	CreateChildNodes ccn = new CreateChildNodes(true,fileRoot, root, 0);
	new Thread(ccn).start();
	wait(1000);
	tree.expandPath(new TreePath(root.getPath()));
	DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
	Icon closedIcon = new ImageIcon("folder.png");
	Icon openIcon = new ImageIcon("folder.png");
	Icon leafIcon = new ImageIcon("empty.png");
	renderer.setClosedIcon(closedIcon);
	renderer.setOpenIcon(openIcon);
	renderer.setLeafIcon(leafIcon);    
	}
	
	private void expandTree(JTree tree, boolean expand) {
		 
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(root), expand);
    }
	private void expandAll(JTree tree, TreePath path, boolean expand) {
        TreeNode node = (TreeNode) path.getLastPathComponent();

        if (node.getChildCount() >= 0) {
            Enumeration<? extends TreeNode> enumeration = node.children();
            while (enumeration.hasMoreElements()) {
                TreeNode treeNode = enumeration.nextElement();
                TreePath treePath = path.pathByAddingChild(treeNode);

                expandAll(tree, treePath, expand);
            }
        }

        if (expand) {
            tree.expandPath(path);
        } 
        else {
            tree.collapsePath(path);
        }
    }
	
	public void setvalueChanged(File file) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
		FilePanel filepanel = new FilePanel(mainGUIHandle, node);
		FileNode fileNode = new FileNode(file);
		mainGUIHandle.setLastNode(node);
		if (!node.isLeaf()) {
			filepanel.LastNode = node;
			filepanel.fillList(IsSimpleMode, new File(fileNode.getfile().getAbsolutePath())); //issimplemode
			setViewportView(filepanel);
			setTitle(fileNode.getfile().getAbsolutePath());
			CreateChildNodes ccn = new CreateChildNodes(false, fileNode.getfile(), node, 0);
			new Thread(ccn).start(); 
		} 
		else // Clear the nodes
		{
			filepanel.emptyList();
			setViewportView(filepanel);
			setTitle(fileNode.getfile().getAbsolutePath());
		}
	
		tree.expandPath(new TreePath(root.getPath()));
		/*
		 * DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		 * e.getPath().getLastPathComponent(); if(!node.isLeaf()) { FileNode fileNode =
		 * (FileNode)node.getUserObject();
		 * internalFrame.setTitle(fileNode.getfile().getAbsolutePath()); }
		 */
	}
	public class MyListener implements InternalFrameListener
	{

		@Override
		public void internalFrameOpened(InternalFrameEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void internalFrameClosing(InternalFrameEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void internalFrameClosed(InternalFrameEvent e) {
			mainGUIHandle.purgeFrame(mainInternalFrameHandle);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		}

		@Override
		public void internalFrameIconified(InternalFrameEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void internalFrameDeiconified(InternalFrameEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void internalFrameActivated(InternalFrameEvent e) { //Indicates when the focus window changes.
			mainGUIHandle.setFocus(mainInternalFrameHandle, tree);
			
		}

		@Override
		public void internalFrameDeactivated(InternalFrameEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	public void updateFileRoot(File file)
	{
		fileRoot = file;
		filenode = new FileNode(fileRoot);
		root = new DefaultMutableTreeNode(filenode);
		tree = new JTree(root);
		scrollPaneleft = new JScrollPane(tree);
		scrollPaneleft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		splitPane.setLeftComponent(scrollPaneleft);
		
		scrollPaneright.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		splitPane.setRightComponent(scrollPaneright);
		
		super.addInternalFrameListener(internalFrameListener);
		
		// tree action
		tree.addTreeSelectionListener(new MyTreeSelectionListener());
		tree.setShowsRootHandles(true);
		CreateChildNodes ccn = new CreateChildNodes(true, fileRoot, root, 0);
		new Thread(ccn).start();
		tree.expandPath(new TreePath(root.getPath()));
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		Icon closedIcon = new ImageIcon("folder.png");
		Icon openIcon = new ImageIcon("folder.png");
		Icon leafIcon = new ImageIcon("empty.png");
		renderer.setClosedIcon(closedIcon);
		renderer.setOpenIcon(openIcon);
		renderer.setLeafIcon(leafIcon);    
	}
	public void setViewportView(FilePanel filepanel) {
		scrollPaneright.setViewportView(filepanel);
	}
	
	public void setSimpleMode(boolean bool)
	{
		if(bool)
			IsSimpleMode = true;
		else
			IsSimpleMode = false;
	}
	public void setSelectedFile(File file)
	{
		selectedFile = file;
	}
	class MyTreeSelectionListener implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
			FilePanel filepanel = new FilePanel(mainGUIHandle, node);
			FileNode fileNode = (FileNode) node.getUserObject();
			mainGUIHandle.setLastNode(node);
			if (!node.isLeaf()) {
				filepanel.LastNode = node;
				filepanel.fillList(IsSimpleMode, new File(fileNode.getfile().getAbsolutePath())); //issimplemode
				setViewportView(filepanel);
				setTitle(fileNode.getfile().getAbsolutePath());
				CreateChildNodes ccn = new CreateChildNodes(false, fileNode.getfile(), node, 0);
				new Thread(ccn).start(); 
			} 
			else // Clear the nodes
			{
				filepanel.emptyList();
				setViewportView(filepanel);
				setTitle(fileNode.getfile().getAbsolutePath());
			}
		
			/*
			 * DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			 * e.getPath().getLastPathComponent(); if(!node.isLeaf()) { FileNode fileNode =
			 * (FileNode)node.getUserObject();
			 * internalFrame.setTitle(fileNode.getfile().getAbsolutePath()); }
			 */
		}
	}
	public class CreateChildNodes implements Runnable {

		private DefaultMutableTreeNode root;
		private File fileRoot;
		private boolean start;
		private int depth;

		public CreateChildNodes(boolean start, File fileRoot, DefaultMutableTreeNode root, int depth) {
			this.fileRoot = fileRoot;
			this.root = root;
			this.start = start;
			this.depth = depth;
		}

		@Override
		public void run() {
			createChildren(start, fileRoot, root, depth);
		}

		private void createChildren(boolean start, File fileRoot, DefaultMutableTreeNode node, int depth) {
			depth++;
			File[] files = fileRoot.listFiles();
			node.removeAllChildren();
			if (files == null)
				return;
			for (File file : files) {
				if(file.isHidden())
					continue;
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
				if(file.isDirectory() &&  (start || depth >= 1))
					node.add(childNode);
				if (depth ==2)
					continue;
				if (file.isDirectory()) {
					createChildren(false, file, childNode, depth);
				}
			}
		}
	}
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
}

