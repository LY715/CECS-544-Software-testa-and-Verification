package filemanager;

import java.awt.*;
import java.util.*;
import javax.swing.tree.*;
import javax.swing.*;

import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private static JPanel statusPanel;
	private static JComboBox comBox;
	private static ArrayList<MyInternalFrame> internalFrames = new ArrayList<MyInternalFrame>();
	private static JTree focusedTree = new JTree();
	static MyInternalFrame focusedInternalFrame;
	private static boolean IsSimpleMode = true;
	private static DefaultMutableTreeNode LastClickedNode;
	private static GUI frame;
	private File selectedFile;
	private static JLabel label;
	private File copyPasteFile;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUI();

					// set the current path
					comBox = new JComboBox();
					File[] drives = File.listRoots();
					for (int i = 0; i < drives.length; i++) {
						comBox.addItem(drives[i]);
					}

					comBox.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							if (e.getStateChange() == ItemEvent.SELECTED) {
								driveChanged(comBox);
							}
						}
					});

					JToolBar toolBar = new JToolBar();
					toolBar.setFloatable(false);
					JButton details = new JButton("Details");
					JButton simple = new JButton("Simple");
					toolBar.add(comBox);
					toolBar.add(details);
					toolBar.add(simple);
					simple.addActionListener(e -> {
						// JOptionPane.showMessageDialog(null, "Simple Pushed");;// your code here
						SimplePressed(focusedInternalFrame);
					});
					details.addActionListener(e -> {
						// JOptionPane.showMessageDialog(null, "Details Pushed");;// your code here
						DetailPressed(focusedInternalFrame);
					});
					frame.getContentPane().add(toolBar, BorderLayout.NORTH);

					// status bar at the bottom
					statusPanel = new JPanel();
					label = new JLabel("Current Drive: " + drives[0] + "    Free Space: " + Math.round(drives[0].getFreeSpace()*Math.pow(10, -9)) +"GB"
							+ " Used Space: " + Math.round(drives[0].getUsableSpace()*Math.pow(10, -9))+"GB" 
							+ " Total Space: " + Math.round(drives[0].getTotalSpace()*Math.pow(10, -9))+"GB");	
					statusPanel.add(label);
					frame.getContentPane().add(statusPanel, BorderLayout.SOUTH);

					frame.setTitle("CECS 544 Project Satoshi Yuki, Louis Yang");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void driveChanged(JComboBox selectedBox)
	{
		var item  = selectedBox.getSelectedItem();
		//set the status bar
		statusPanel.removeAll();
		label.setText("Current Drive: " + selectedBox.getSelectedItem() + "    Free Space: " + Math.round(((File) item).getFreeSpace()*Math.pow(10, -9)) + "GB" 
				+ " Used Space: " + Math.round(((File) item).getUsableSpace()*Math.pow(10, -9))+"GB" 
				+ " Total Space: " + Math.round(((File) item).getTotalSpace()*Math.pow(10, -9))+"GB");	
		statusPanel.add(label);
		
		if(item instanceof File)
			focusedInternalFrame.updateFileRoot((File)item);
		focusedInternalFrame.setvalueChanged((File)item);
	}
	
	public static void SimplePressed(MyInternalFrame internalframe) {
		IsSimpleMode = true;
		internalframe.setSimpleMode(true);
		RefreshRightPane(internalframe);
	}

	public static void DetailPressed(MyInternalFrame internalframe) {
		IsSimpleMode = false;
		internalframe.setSimpleMode(false);
		RefreshRightPane(internalframe);
	}

	static void RefreshRightPane(JInternalFrame internalframe) {
		JSplitPane splitPane;
		JScrollPane scrollPanelright;
		JViewport viewport;
		Component view;
		FilePanel filePanel;
		Component[] components = internalframe.getContentPane().getComponents();
		JList fileList;
		FileNode fileNode = (FileNode) LastClickedNode.getUserObject();
		for (Component component : components) {
			if (component instanceof JSplitPane) {
				splitPane = (JSplitPane) component;
				scrollPanelright = (JScrollPane) splitPane.getRightComponent();
				viewport = scrollPanelright.getViewport();
				view = viewport.getView();
				if (view instanceof FilePanel) {
					filePanel = (FilePanel) view;
					fileList = filePanel.list;
					if (!LastClickedNode.isLeaf()) {
						filePanel.fillList(IsSimpleMode, new File(fileNode.getfile().getAbsolutePath()));
						scrollPanelright.setViewportView(filePanel);
						internalFrames.get(0).setTitle(fileNode.getfile().getAbsolutePath());
					}
				}
			}
		}
	}
	
	/**
	 * Create the frame.
	 * 
	 * @throws PropertyVetoException
	 */
	public GUI() throws PropertyVetoException {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350, 50, 900, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JDesktopPane desktopPane = new JDesktopPane();
		contentPane.add(desktopPane, BorderLayout.CENTER);
		internalFrames.add( new MyInternalFrame(this, desktopPane));
		focusedInternalFrame = internalFrames.get(internalFrames.size()-1);
		
		// Set the menu on top
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// File
		JMenu Mfile = new JMenu("File");
		menuBar.add(Mfile);

		MyMenu mm = new MyMenu(this);
		Mfile.add(mm.MIrename);
		Mfile.add(mm.MIcopy);
		JMenuItem MIdelete = new JMenuItem("Delete");
		MIdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = frame.getSelectedFile();
				int result = JOptionPane.showConfirmDialog(MIdelete.getParent(),"Delete: "+ file.toPath(), "Deleting!!!",
			               JOptionPane.YES_NO_OPTION,
			               JOptionPane.QUESTION_MESSAGE);
				if(result== 0)
				{
					try {
						Files.delete(file.toPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		Mfile.add(MIdelete);
		JMenuItem MIrun = new JMenuItem("Run");
		Mfile.add(MIrun);
		MIrun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = frame.getSelectedFile();
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		JMenuItem MIexit = new JMenuItem("Exit");
		Mfile.add(MIexit);
		// Tree
		JMenu MTree = new JMenu("Tree");
		menuBar.add(MTree);
		JMenuItem MIexpand = new JMenuItem("Expand Branch");
		MTree.add(MIexpand);
		MIexpand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expandTree(focusedTree, true);
			}
		});

		JMenuItem MIcollapse = new JMenuItem("Collapse Branch");
		MTree.add(MIcollapse);
		MIcollapse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expandTree(focusedTree, false);
			}
		});
		// Window
		JMenu MWindow = new JMenu("Window");
		menuBar.add(MWindow);
		JMenuItem MInew = new JMenuItem("New");
		MInew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(internalFrames.size() < 10)
				{
				internalFrames.add(new MyInternalFrame(frame, desktopPane));
				internalFrames.get(internalFrames.size()-1).setLocation(0, 100);
				}
			}
		});
		MWindow.add(MInew);
		JMenuItem MIcascade = new JMenuItem("Cascade");
		MWindow.add(MIcascade);
		
		MIcascade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = 0;
				for(MyInternalFrame myframe : internalFrames )
				{
					myframe.setLocation(new Point(pos,pos));
					myframe.toFront();
					pos+=20;
				}
			}
		});
		
		
		// Help
		JMenu MHelp = new JMenu("Help");
		menuBar.add(MHelp);
		JMenuItem MIhelp = new JMenuItem("Help");
		MHelp.add(MIhelp);
		MIhelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setTitle("Help");
				dialog.setBounds(500, 400, 200, 100);

				JPanel topPanel = new JPanel(new GridLayout(3, 0));

				JLabel title = new JLabel(
						"<html><body> Help us! <br> Enter the amount of cash you want to donate <body></html>");
				title.setFont(new Font("Calibri", Font.BOLD, 20));

				JTextField textfield = new JTextField(20);
				textfield.setPreferredSize(new Dimension(10, 20));
				textfield.addKeyListener(new KeyAdapter() {
					public void keyTyped(KeyEvent e) {
						char c = e.getKeyChar();
						if (c < '0' || c > '9') {
							e.consume();
						}
					}
				});
				FlowLayout layout = new FlowLayout(FlowLayout.CENTER);

				JPanel titlePanel = new JPanel(layout);

				titlePanel.add(title);
				JPanel textPanel = new JPanel();
				textPanel.add(textfield);

				JPanel buttonPanel = new JPanel(layout);
				JButton donateButton = new JButton("Donate");
				donateButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String getValue = textfield.getText();
						JOptionPane.showMessageDialog(null, "Thanks for your " + getValue + " dollars");
					}
				});
				donateButton.setBounds(100, 100, 220, 30);
				buttonPanel.add(donateButton);
				topPanel.add(titlePanel);
				topPanel.add(textPanel);
				topPanel.add(buttonPanel);
				dialog.getContentPane().add(topPanel, BorderLayout.CENTER);
				dialog.setResizable(false);
				dialog.setSize(550, 250);
				dialog.setVisible(true);
			}
		});
		JMenuItem MIabout = new JMenuItem("About");
		MHelp.add(MIabout);
		MIabout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JDialog dialog = new JDialog();
				dialog.setTitle("About");
				dialog.setBounds(500, 400, 200, 100);
				JPanel northPanel = new JPanel(new GridLayout(3, 0));
				JLabel title = new JLabel("CECS 544 File Manager");
				title.setFont(new Font("Calibri", Font.BOLD, 25));
				FlowLayout layout1 = new FlowLayout(FlowLayout.CENTER);
				JPanel titlePanel = new JPanel(layout1);
				titlePanel.add(title);
				northPanel.add(titlePanel);

				JPanel textPanel = new JPanel(layout1);
				JLabel content = new JLabel("Copyright(c) 2022 Louis Yang, Satoshi Yuki");
				textPanel.add(content);
				northPanel.add(textPanel);

				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) {
							dialog.dispose();
						}
					}
				});

				JPanel buttonPanel = new JPanel();
				okButton.setBounds(100, 100, 220, 30);
				buttonPanel.add(okButton);
				northPanel.add(buttonPanel);

				dialog.getContentPane().add(northPanel, BorderLayout.CENTER);

				dialog.setResizable(false);
				dialog.setSize(550, 250);
				dialog.setVisible(true);
			}
		});
	}

	// Expand Tree or Collapse Tree
	// from https://www.logicbig.com/tutorials/java-swing/jtree-filtering.html
	private void expandTree(JTree tree, boolean expand) {
		if(expand)
			tree.expandPath(new TreePath(LastClickedNode.getPath()));
		else
			tree.collapsePath(new TreePath(LastClickedNode.getPath()));
	}
	
	public void setFocus(MyInternalFrame frame, JTree tree)
	{
		focusedInternalFrame = frame;
		focusedTree = tree;
	}
	
	public MyInternalFrame getfocusedframe() {
		return focusedInternalFrame;
	}
	public void purgeFrame(MyInternalFrame frame)
	{
		internalFrames.remove(frame);
		frame.removeAll();
		try {
			frame.setClosed(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = null;
	}
	
	public void setLastNode(DefaultMutableTreeNode lastClickedNode)
	{
		LastClickedNode = lastClickedNode;
	}
	
	public void setSelectedFile(File file)
	{
		selectedFile = file;
	}
	public File getSelectedFile()
	{
		return selectedFile;
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
	public void setCopyPasteFile(File file)
	{
		copyPasteFile = file;
	}
	public File getCopyPasteFile()
	{
		return copyPasteFile;
	}
	
}
