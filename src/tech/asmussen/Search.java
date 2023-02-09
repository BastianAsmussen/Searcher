package tech.asmussen;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

public class Search {
	
	public static void main(String[] args) {
		
		// TODO: Make the program compile the file list once, and then store it in a file for future use.
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter a file name to search for: ");
		String query = scanner.nextLine().trim();
		
		scanner.close();
		
		System.out.println("Compiling file list...");
		
		final long startTime = System.currentTimeMillis();
		
		ArrayList<File> files = new ArrayList<>();
		ArrayList<String> results = new ArrayList<>();
		
		File root = new File("C:/");
		
		getFiles(root, files::add);
		
		System.out.printf("Searching for \"%s\" in %s files...\n", query, formatter.format(files.size()));
		
		for (File file : files) {
			
			if (file.getName().contains(query)) {
				
				results.add(file.getAbsolutePath());
				
				String estimatedTime = Time.formatTime(Time.getEstimatedTime((System.currentTimeMillis() - startTime), results.size(), files.size()));
				System.out.printf("\rSearching for \"%s\", %s items found... ETA: %s.", query, formatter.format(results.size()), estimatedTime);
			}
		}
		
		System.out.printf("\nFound %s results in %s\n", formatter.format(results.size()), Time.formatTime(System.currentTimeMillis() - startTime));
		
		if (results.size() < 1) {
			
			return;
		}
		
		System.out.println("Results:");
		for (int i = 0; i < results.size(); i++) {
			
			String result = results.get(i);
			
			System.out.printf("%d) %s\n", i + 1, result);
		}
	}
	
	public static void getFiles(File dir, Consumer<File> fileConsumer) {
		
		if (dir.isDirectory()) {
			
			File[] files = dir.listFiles();
			
			if (files == null) return;
			
			for (File file : files) {
				
				getFiles(file, fileConsumer);
			}
			
		} else {
			
			fileConsumer.accept(dir);
		}
	}
}
