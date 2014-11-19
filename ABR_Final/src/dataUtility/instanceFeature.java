package dataUtility;

public class instanceFeature {
	public int featureIndex;
	public String featureValue;
	public String instanceLabel;
	
	public instanceFeature() {
		featureIndex = 0;
		featureValue = instanceLabel = "";
	}
	
	public instanceFeature(int featureIndex, String featureValue, String instanceLabel){
		this.featureIndex = featureIndex;
		this.featureValue = featureValue;
		this.instanceLabel = instanceLabel;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash = featureIndex + featureValue.hashCode()
				+ instanceLabel.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		instanceFeature ins = (instanceFeature) obj;

		return featureIndex == ins.featureIndex
				&& (featureValue.equals(ins.featureValue))
				&& (instanceLabel.equals(ins.instanceLabel));
	}

}
