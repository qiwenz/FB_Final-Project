package defaul;

import java.util.Map;

public class Sentence {
	private String pmid;
	private String abs;
	private Map<String, Integer> mp;
	private Map<String, Integer> NNwords;
	private double sentScore;

	public Map<String, Integer> getNNwords() {
		return NNwords;
	}

	public void setNNwords(Map<String, Integer> nNwords) {
		NNwords = nNwords;
	}

	public Sentence(String id, String cont, Map<String, Integer> map,
			double score) {
		this.pmid = id;
		this.abs = cont;
		this.mp = map;
		this.sentScore = score;
	}

	public Map<String, Integer> getMp() {
		return mp;
	}

	public void setMp(Map<String, Integer> mp) {
		this.mp = mp;
	}

	public String getPmid() {
		return pmid;
	}

	public void setPmid(String pmid) {
		this.pmid = pmid;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public double getSentScore() {
		return sentScore;
	}

	public void setSentScore(double sentScore) {
		this.sentScore = sentScore;
	}

}
