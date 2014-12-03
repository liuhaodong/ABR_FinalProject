package dataUtility;

public class InstanceFeature {
	public int featureIndex;
	public int featureValueIndex;
	public String featureValue;
	public String instanceLabel;

	public InstanceFeature() {
		featureIndex = featureValueIndex = 0;
		featureValue = instanceLabel = "";
	}

	public InstanceFeature(int featureIndex, Integer featureValueIndex,
			String featureValue, String instanceLabel) {
		this.featureIndex = featureIndex;
		this.featureValueIndex = featureValueIndex;
		this.featureValue = featureValue;
		this.instanceLabel = instanceLabel;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash = featureIndex + featureValueIndex + instanceLabel.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		InstanceFeature ins = (InstanceFeature) obj;

		return featureIndex == ins.featureIndex
				&& ((featureValue.equals(ins.featureValue)) || featureValueIndex == ins.featureValueIndex)
				&& (instanceLabel.equals(ins.instanceLabel));
	}

	@Override
	public String toString() {
		return "featureIndex: " + this.featureIndex + "featureValueIndex: "
				+ this.featureValueIndex + "featureValue: " + this.featureValue
				+ "instanceLabel: " + this.instanceLabel;
	}

}
