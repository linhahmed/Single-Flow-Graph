package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import Controller.Graph;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI {

	private JFrame frame;
	private JFrame frame2;
	private JTextField nodeText;
	private ListenableGraph<String, MyEdge> g;
	private JGraphXAdapter<String, MyEdge> graphAdapter;
	private mxGraphComponent component;
	private JTextField FromText;
	private JTextField toText;
	private JTextField GainText;
	public Graph graph;
	private JTextField StartFrom;
	private JTextField EndsTo;
	

	public static class MyEdge extends DefaultWeightedEdge {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String toString() {
			return String.valueOf(getWeight());
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 978, 760);
		frame.setTitle("Single Flow Graph Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(425, 33, 521, 637);
		frame.getContentPane().add(panel);

		JLabel lblNewLabel = new JLabel("Number of Nodes");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		lblNewLabel.setBounds(26, 35, 212, 33);
		frame.getContentPane().add(lblNewLabel);

		nodeText = new JTextField();
		nodeText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if(!(Character.isDigit(c)) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
					arg0.consume();
				}
			}
		});
		
		nodeText.setFont(new Font("Arial", Font.PLAIN, 20));
		nodeText.setBounds(249, 33, 57, 41);
		frame.getContentPane().add(nodeText);
		nodeText.setColumns(10);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nodeText.getText().isEmpty() || StartFrom.getText().isEmpty() || EndsTo.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Enter the number of nodes & the starting and ending node!!");
				} else {
					if (Integer.valueOf(StartFrom.getText()) > Integer.parseInt(nodeText.getText())
							|| Integer.valueOf(EndsTo.getText()) > Integer.parseInt(nodeText.getText())) {
						JOptionPane.showMessageDialog(frame, "The starting and ending node does not exist");
					} else {
						int size = Integer.parseInt(nodeText.getText());
						g = new DefaultListenableGraph<>(new DirectedWeightedPseudograph<String, MyEdge>(MyEdge.class));
					
						graph = new Graph(size);
						graph.addNodes();
						for (int i = 1; i <= size; i++) {
							g.addVertex(Integer.toString(i));
						}
						graphAdapter = new JGraphXAdapter<String, MyEdge>(g);
						 component = new mxGraphComponent(graphAdapter);
						component.zoom(1.7);
						component.zoomIn();
						component.getViewport().setOpaque(true);
						component.getViewport().setBackground(Color.WHITE);
						// component.getViewport().setPreferredSize();
						component.getGraph().setAllowLoops(true);
						panel.remove(component);
						panel.removeAll();
						panel.repaint();
						panel.revalidate();
						new mxCircleLayout(graphAdapter).execute(graphAdapter.getDefaultParent());
						new mxParallelEdgeLayout(graphAdapter).execute(graphAdapter.getDefaultParent());
						panel.removeAll();
						panel.revalidate();
						panel.add(component);
					}

				}

			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 22));
		btnNewButton.setBounds(305, 224, 76, 41);
		frame.getContentPane().add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("From");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 25));
		lblNewLabel_1.setBounds(26, 294, 67, 33);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblTo = new JLabel("To");
		lblTo.setFont(new Font("Arial", Font.PLAIN, 25));
		lblTo.setBounds(26, 368, 67, 33);
		frame.getContentPane().add(lblTo);

		FromText = new JTextField();
		FromText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if(!(Character.isDigit(c)) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
					arg0.consume();
				}
			}
		});
		FromText.setFont(new Font("Arial", Font.PLAIN, 20));
		FromText.setBounds(141, 294, 57, 41);
		frame.getContentPane().add(FromText);
		FromText.setColumns(10);

		toText = new JTextField();
		toText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if(!(Character.isDigit(c)) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
					arg0.consume();
				}
			}
		});
		toText.setFont(new Font("Arial", Font.PLAIN, 20));
		toText.setColumns(10);
		toText.setBounds(141, 368, 57, 41);
		frame.getContentPane().add(toText);

		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nodeText.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Enter the number of nodes & the starting and ending node!!");
				} else {
					if (FromText.getText().isEmpty() || toText.getText().isEmpty() || GainText.getText().isEmpty()) {
						JOptionPane.showMessageDialog(frame, "Enter the full details about the edge!!");
					} else {
						String from = FromText.getText();
						String to = toText.getText();
						String gain = GainText.getText();
						if (Integer.valueOf(from) > Integer.parseInt(nodeText.getText())
								|| Integer.valueOf(to) > Integer.parseInt(nodeText.getText())) {
							JOptionPane.showMessageDialog(frame, "The starting and ending node does not exist");
						} else {
							graph.addEdge(Integer.valueOf(from), Integer.valueOf(to), Double.valueOf(gain));
							g.addEdge(from, to);
							g.setEdgeWeight(from, to, Double.parseDouble(gain));
							JGraphXAdapter<String, MyEdge> graphAdapter = new JGraphXAdapter<String, MyEdge>(g);
							mxGraphComponent component = new mxGraphComponent(graphAdapter);
							component.zoom(1.7);
							component.zoomIn();
							component.getViewport().setOpaque(true);
							component.getViewport().setBackground(Color.WHITE);
							component.getGraph().setAllowLoops(true);
							mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
							layout.execute(graphAdapter.getDefaultParent());

							panel.removeAll();
							panel.revalidate();
							panel.add(component);
						}
					}
				}

			}
		});
		button.setFont(new Font("Arial", Font.PLAIN, 22));
		button.setBounds(203, 501, 76, 41);
		frame.getContentPane().add(button);

		JLabel lblGain = new JLabel("Gain");
		lblGain.setFont(new Font("Arial", Font.PLAIN, 25));
		lblGain.setBounds(26, 443, 67, 33);
		frame.getContentPane().add(lblGain);

		GainText = new JTextField();
		GainText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				String s=Character.toString(c);
				if((!(Character.isDigit(c)) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && !s.contains(".") && !s.contains("-") ) {
					arg0.consume();
				}
			}
		});
		GainText.setFont(new Font("Arial", Font.PLAIN, 20));
		GainText.setColumns(10);
		GainText.setBounds(141, 443, 57, 41);
		frame.getContentPane().add(GainText);

		JButton btnNewButton_1 = new JButton("Results");
		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				initializeF2();
			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 27));
		btnNewButton_1.setBounds(26, 600, 171, 50);
		frame.getContentPane().add(btnNewButton_1);

		StartFrom = new JTextField();
		StartFrom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if(!(Character.isDigit(c)) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
					arg0.consume();
				}
			}
		});
		StartFrom.setFont(new Font("Arial", Font.PLAIN, 20));
		StartFrom.setColumns(10);
		StartFrom.setBounds(249, 96, 57, 41);
		frame.getContentPane().add(StartFrom);

		JLabel lblNewLabel_2 = new JLabel("Add Edges");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(497, -214, 159, 33);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblStartsFrom = new JLabel("Starts From");
		lblStartsFrom.setFont(new Font("Arial", Font.PLAIN, 25));
		lblStartsFrom.setBounds(26, 96, 159, 33);
		frame.getContentPane().add(lblStartsFrom);

		JLabel label = new JLabel("To");
		label.setFont(new Font("Arial", Font.PLAIN, 25));
		label.setBounds(26, 157, 67, 33);
		frame.getContentPane().add(label);

		EndsTo = new JTextField();
		EndsTo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if(!(Character.isDigit(c)) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
					arg0.consume();
				}
			}
		});
		EndsTo.setFont(new Font("Arial", Font.PLAIN, 20));
		EndsTo.setColumns(10);
		EndsTo.setBounds(249, 151, 57, 41);
		frame.getContentPane().add(EndsTo);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				g = new DefaultListenableGraph<>(new DirectedWeightedPseudograph<String, MyEdge>(MyEdge.class));
				panel.remove(component);
				panel.removeAll();
				panel.repaint();
				panel.revalidate();
			}
		});
		btnReset.setFont(new Font("Arial", Font.BOLD, 27));
		btnReset.setBounds(238, 600, 171, 50);
		frame.getContentPane().add(btnReset);
	}

	private void initializeF2() {
		frame2 = new JFrame();
		frame2.setVisible(true);
		frame2.setBounds(100, 100, 825, 771);
		frame2.setTitle("Results");
		frame2.setResizable(false);
		frame2.getContentPane().setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 396, 343);
		frame2.getContentPane().add(scrollPane);

		JLabel label = new JLabel("Forward Paths:");
		label.setBackground(Color.WHITE);
		label.setFont(new Font("Arial", Font.PLAIN, 25));
		scrollPane.setColumnHeaderView(label);

		ArrayList<Integer[]> fp = graph.getForawrdPaths(Integer.parseInt(StartFrom.getText()), Integer.parseInt(EndsTo.getText()));
		ArrayList<String> f = new ArrayList<String>();
		for (int j = 0; j < fp.size(); j++) {
			int i = 0;
			StringBuilder sb = new StringBuilder();
			while (i < fp.get(j).length - 1) {
				sb.append(fp.get(j)[i]);
				sb.append("->");
				i++;
			}
			sb.append(fp.get(j)[i]);
			f.add(sb.toString());
		}
		for (String a : f) {
			textArea.append(a + "\n");
		}

		JTextArea textArea1 = new JTextArea();
		textArea1.setFont(new Font("Arial", Font.PLAIN, 20));
		textArea1.setBackground(new Color(216, 191, 216));
		textArea1.setEditable(false);

		JScrollPane scrollPane_1 = new JScrollPane(textArea1, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(397, 0, 396, 343);
		frame2.getContentPane().add(scrollPane_1);

		JLabel label_1 = new JLabel("Loops:");
		scrollPane_1.setColumnHeaderView(label_1);
		label_1.setFont(new Font("Arial", Font.PLAIN, 25));

		List<List<Integer>> loop = graph.getLoops();
		ArrayList<String> loo = new ArrayList<String>();
		for (int j = 0; j < loop.size(); j++) {
			int i = 0;
			StringBuilder sb = new StringBuilder();
			while (i < loop.get(j).size() - 1) {
				sb.append(loop.get(j).get(i));
				sb.append("->");
				i++;
			}
			sb.append(loop.get(j).get(i));
			loo.add(sb.toString());
		}
		for (String a : loo) {
			textArea1.append(a + "\n");
		}

		JTextArea textArea2 = new JTextArea();
		textArea2.setFont(new Font("Arial", Font.PLAIN, 20));
		textArea2.setBackground(new Color(216, 191, 216));
		textArea2.setEditable(false);

		JScrollPane scrollPane_2 = new JScrollPane(textArea2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setBounds(0, 344, 396, 343);
		frame2.getContentPane().add(scrollPane_2);

		JLabel label_2 = new JLabel("NonTouching Loops:");
		scrollPane_2.setColumnHeaderView(label_2);
		label_2.setFont(new Font("Arial", Font.PLAIN, 25));

		List<List<List<List<Integer>>>> NT = graph.getNonTouchingLoops();
		ArrayList<String> N = new ArrayList<String>();
		for (int j = 0; j < NT.size(); j++) {
			for (int l = 0; l < NT.get(j).size(); l++) {
				int i = 0;
				StringBuilder sb = new StringBuilder();
				while (i < NT.get(j).get(l).size() - 1) {
					sb.append(NT.get(j).get(l).get(i));
					sb.append(" and ");
					i++;
				}
				sb.append(NT.get(j).get(l).get(i));
				N.add(sb.toString());
			}

		}
		for (String a : N) {
			textArea2.append(a + "\n");
		}

		JTextArea textArea3 = new JTextArea();
		textArea3.setFont(new Font("Arial", Font.PLAIN, 20));
		textArea3.setEditable(false);

		JScrollPane scrollPane_3 = new JScrollPane(textArea3, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setBounds(397, 344, 396, 339);
		frame2.getContentPane().add(scrollPane_3);

		JLabel label_3 = new JLabel("Over All TF:");
		scrollPane_3.setColumnHeaderView(label_3);
		label_3.setFont(new Font("Arial", Font.PLAIN, 25));
		ArrayList<String> calc = new ArrayList<String>();
		calc.add("Delta = " + graph.getDelta());
		for(int i=0;i<fp.size();i++) {
			calc.add("Delta"+(i+1)+" = "+ graph.getDeltaOfPath(Arrays.asList(fp.get(i))));
		}
		calc.add("TF = "+ graph.CalculateTF());
		
		for (String a : calc) {
			textArea3.append(a + "\n");
		}

	}
}
