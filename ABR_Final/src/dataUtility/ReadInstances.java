package dataUtility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class ReadInstances {
	public static Instances readInstances(String path) throws IOException {
		BufferedReader bReader = new BufferedReader(new FileReader(path));
		Instances train = new Instances(bReader);
		train.setClassIndex(0);
		train.deleteAttributeAt(15);
		train.deleteAttributeAt(5);
		bReader.close();
		return train;
	}

}
