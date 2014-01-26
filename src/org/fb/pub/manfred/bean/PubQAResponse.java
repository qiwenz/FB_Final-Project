package org.fb.pub.manfred.bean;

import java.util.ArrayList;
import defaul.Sentence;

public class PubQAResponse {
	private ArrayList<Sentence> sentences;
	private ArrayList<String> terms;
	
	public ArrayList<Sentence> getSentences() {
		return sentences;
	}
	public void setSentences(ArrayList<Sentence> sentences) {
		this.sentences = sentences;
	}
	public ArrayList<String> getTerms() {
		return terms;
	}
	public void setTerms(ArrayList<String> terms) {
		this.terms = terms;
	}
}
