package dataPreprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FeatureRemove {
	public void randomRemoveFeature(String inputFile, String outputFile,
			float percentage) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

		String tmpLine = "";
		while ((tmpLine = br.readLine()) != null) {
			if (tmpLine.startsWith("@data")) {
				bw.write(tmpLine);
				bw.write('\n');
				bw.write(br.read());
				int tmpChar = 0;
				while ((tmpChar = br.read()) != -1) {
					if (tmpChar == ',' || tmpChar == '\n') {
						bw.write(tmpChar);
						if (tmpChar == '\n') {
							if ((tmpChar = br.read()) != -1)
								bw.write(tmpChar);
						}
					} else {
						double randomNum = Math.random();
						if (randomNum < percentage) {
							bw.write('?');
						} else {
							bw.write(tmpChar);
						}
					}
				}
			} else {
				bw.write(tmpLine);
				bw.write('\n');
			}
		}
		br.close();
		bw.close();
	}

	public static void main(String[] args) throws IOException {
		FeatureRemove tmpFeatureRemove = new FeatureRemove();
		tmpFeatureRemove.randomRemoveFeature("data/mushroom_train.arff",
				"data/mushroom_train_miss.arff", (float) 0.005);
	}

}
