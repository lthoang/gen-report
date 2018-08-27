package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Application {

	public static enum ReportType {
		REVENUE_BY_YEAR
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("[Usage] <input_file> <output_dir> <report_type>");
			System.exit(1);
		}

		String inputFileName = args[0];
		String outputFileDir = args[1];
		ReportType reportType = ReportType.valueOf(args[2].toUpperCase());
		System.out.println("Input file: " + inputFileName);
		System.out.println("Output directory: " + outputFileDir);
		System.out.println("Report type: " + reportType);

		Map<Long, Double> revenueByYears = new HashMap<Long, Double>();

		try {
			File file = new File(inputFileName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			int count = 0;
			while ((line = reader.readLine()) != null) {
				if (count++ == 0) {
					continue;
				}
				String[] row = line.split(",");
				Long year = Long.parseLong(row[6]);
				if (!revenueByYears.containsKey(year)) {
					revenueByYears.put(year, 0.);
				}
				Double revenue = Double.parseDouble(row[8]);
				revenueByYears.put(year, revenueByYears.get(year) + revenue);
			}
			File outputFile = new File(outputFileDir + '/' + reportType.name().toLowerCase() + ".csv");
			Writer writer = new FileWriter(outputFile);
			writer.write("Year,Revenue\n");
			for (Map.Entry<Long, Double> revenueByYear : revenueByYears.entrySet()) {
				writer.write(revenueByYear.getKey() + "," + revenueByYear.getValue() + "\n");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
