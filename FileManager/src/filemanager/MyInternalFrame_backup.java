package filemanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Enumeration;

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
	CreateChildNodes ccn = new CreateChildNodes(fileRoot, root);
	new Thread(ccn).start();
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
		CreateChildNodes ccn = new CreateChildNodes(fileRoot, root);
		new Thread(ccn).start();
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
			} else // Clear the nodes
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

		public CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
			this.fileRoot = fileRoot;
			this.root = root;
		}

		@Override
		public void run() {
			createChildren(fileRoot, root);
		}

		private void createChildren(File fileRoot, DefaultMutableTreeNode node) {

			File[] files = fileRoot.listFiles();
			if (files == null)
				return;

			for (File file : files) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
				node.add(childNode);
				if (file.isDirectory()) {
					createChildren(file, childNode);
				}
			}
		}

	}
}

