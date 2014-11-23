package dataUtility;

public class MissingFeatureIndex {
	
	public int instanceIndex;
	public int featureIndex;
	public String instanceLabel;
	
	public MissingFeatureIndex() {
		// TODO Auto-generated constructor stub
	}
	
	public MissingFeatureIndex(int instanceIndex, int featureIndex){
		this.instanceIndex = instanceIndex;
		this.featureIndex = featureIndex;
	}
	
	public MissingFeatureIndex(int instanceIndex, int featureIndex, String instanceLabel){
		this.instanceIndex = instanceIndex;
		this.featureIndex = featureIndex;
		this.instanceLabel = instanceLabel;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash = featureIndex + instanceIndex;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		MissingFeatureIndex ins = (MissingFeatureIndex) obj;
		return featureIndex == ins.featureIndex && instanceIndex == ins.instanceIndex;
	}

	
	@Override
	public String toString(){
		return "instanceIndex: "+this.instanceIndex + " featureIndex: "+ this.featureIndex + " instanceLabel: "+ this.instanceLabel;
	}
}
