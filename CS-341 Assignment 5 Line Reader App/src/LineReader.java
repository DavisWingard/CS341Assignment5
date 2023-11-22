import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class LineReader {
	
	private JButton btnNewButton;
	private JTextArea textArea;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LineReader window = new LineReader();
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
	public LineReader() {
		initialize();
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFile();
			}
		});
	}
	
	public void getFile() {
		File inputFile;
		Scanner fileInputScan = null;
		
		try {
		//use filechooser to select a file
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
		
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				inputFile = fileChooser.getSelectedFile();
				
				//open the file with scanner
				fileInputScan = new Scanner(inputFile);
				
				//check for empty file
				if (inputFile.length() == 0) {
					textArea.setText("Empty file");
					return;
				}
				
				//file has one line
				if (inputFile.length() == 1) {
					textArea.setText("Mean: " + fileInputScan.nextInt() + "\n" + "Standard deviation cannot be computed");
					return;
				}
				
				//check for invalid characters
				while (fileInputScan.hasNextLine()) {
					String s = fileInputScan.nextLine();
					for (int i = 0; i < s.length(); i++) {
						if (!Character.isAlphabetic(s.charAt(i))) {
							textArea.setText("Invalid character detected in file");
							return;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error. File could not be found");
		} finally {
			if (fileInputScan != null) {
				fileInputScan.close();
			}
		}
	}
	
	public LinkedList parseFile(Scanner fileInputScan) {
		LinkedList linkedList = new LinkedList();
		//keep track of how many lines are in the file and the total of the linkedlist nodes
		int traversed = 0;
		int total = 0;
		
		//this will still work if the program 
		while (fileInputScan.hasNextLine()) {
			int grabbedNum = fileInputScan.nextInt();
			linkedList.add(grabbedNum);
			traversed++;
			total = total+grabbedNum;
		}
		print(traversed, total, linkedList);
		return linkedList;
	}
	
	public void print(int traversed, int total, LinkedList linkedList) {
		//print mean
		double mean = (double) total/traversed;
		textArea.setText("Mean: " + mean);
		
		//find sum of square of distance between value and mean
		double squareSum = 0;
		while (linkedList.head != null) {
			squareSum += (double) Math.pow(linkedList.head.num - mean, 2);
			linkedList.head = linkedList.head.next;
		}
		
		//divide by total number of values
		squareSum = squareSum / traversed;
		
		//take square root
		double stdev = Math.sqrt(squareSum);
		textArea.setText(textArea.getText() + "\n" + "Standard deviation: " + stdev);
	}
	
	
	//testing
	/*
	public int printList(int total) {
		System.out.println(total);
		return total;
	}
	*/

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnNewButton = new JButton("Choose file");
		btnNewButton.setBounds(170, 50, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		textArea = new JTextArea();
		textArea.setBounds(80, 91, 294, 124);
		frame.getContentPane().add(textArea);
	}
}
