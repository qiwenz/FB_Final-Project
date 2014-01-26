package defaul;

import java.io.BufferedReader;
import org.apache.lucene.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
//import rita.wordnet.*;
import java.util.Map;

public class Test {
	private String indexDir = "/Users/manfreddrathen/Documents/workspace/02652/XMLIndex/index";
	public String query;
	public static String fileDir = "data/question.txt";
	private ArrayList<Document> docList;
	private ArrayList<String> al;

	public ArrayList<String> getAl() {
		return al;
	}

	public void setAl(ArrayList<String> al) {
		this.al = al;
	}

	public Test(String que) {
		docList = new ArrayList<Document>();
		query = que;
	}

	public Test() {
		docList = new ArrayList<Document>();
		// query = que;
	}

	public ArrayList<Sentence> run() {
		// Test tst = new Test(query);
		QuestionParse qp = new QuestionParse();
		String res[] = qp.parsing(query);
		ArrayList<Sentence> result = this.searcher(res);
		HashMap queryFreq = analysisQuery(query);

		// call Yisong
		paragaMain abc=new paragaMain();
		result = abc.CandidateAnswerSelect(queryFreq, result);
		return result;
	}

	public HashMap analysisQuery(String sentence) {
		HashMap<String, Integer> myMap = new HashMap<String, Integer>();
		String token[] = sentence.toLowerCase().split(" ");
		for (String myToken : token) {
			// System.out.println("token:"+myToken);
			if (myMap.containsKey(myToken)) {
				// System.out.println("contains Key:"+myToken);
				Integer temp = myMap.get(myToken);
				// myMap.remove(myToken);
				myMap.put(myToken, temp + 1);
				// System.out.println("number:"+myMap.get(myToken));
			} else
				myMap.put(myToken, 1);
		}
		return myMap;
	}

	public static void main(String args[]) throws IOException {
		Test tst = new Test();
		File file = new File(fileDir);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str;
		while ((str = br.readLine()) != null) {
			QuestionParse qp = new QuestionParse();
			String res[] = qp.parsing(str);
			for (String x : res) {
				System.out.println("token: " + x);
				// tst.searcher("drug");
			}
			ArrayList<Sentence> result = tst.searcher(res);

		}
		System.out.println("\"functional unit\"~5000");
	}

	// public ArrayList<ArrayList<String>> getCandSent(){
	// File file = new File(fileDir);
	// BufferedReader br = new BufferedReader(new FileReader(file));
	// String str ;
	// while((str= br.readLine())!=null){
	// QuestionParse qp = new QuestionParse();
	// String res[]=qp.parsing(str);
	// for(String x:res){
	// System.out.println("token: "+x);
	// //tst.searcher("drug");
	// ArrayList<ArrayList<String>> al = this.searcher(x);
	// }
	// }
	// }

	// al0: candidate sentence al1: candidate ID
	public ArrayList<Sentence> searcher(String queryString[]) {
		al = new ArrayList<String>();
		for (String x : queryString) {
			if (x != "")
				al.add(x);
		}
		ArrayList<Sentence> result = new ArrayList<Sentence>();
		ArrayList<Integer> xx = new ArrayList<Integer>();
		// ArrayList<Map<String,Integer>> result = new
		// ArrayList<Map<String,Integer>>();
		ArrayList<String> candSent = new ArrayList<String>();
		ArrayList<String> candId = new ArrayList<String>();
		try {
			FSDirectory dir = SimpleFSDirectory.open(new File(indexDir));

			StandardAnalyzer analyzer = new StandardAnalyzer(
					Version.LUCENE_CURRENT);
			String querystr = "";

			if (al.size() >= 2) {
				for (int i = 0; i < al.size(); i++) {
					for (int j = 0; j < i; j++) {
						String que = al.get(i) + " " + al.get(j);
						if (querystr == "") {
							querystr = "\"" + que + "\"" + "~100";
						} else {
							querystr = querystr + " AND " + "\"" + que + "\""
									+ "~100";
						}
					}
				}
			} else {
				querystr = al.get(0);
			}
			System.out.println("querysty:" + querystr);

			Query q = new QueryParser(Version.LUCENE_CURRENT, "abstract",
					analyzer).parse(querystr);
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs tds = searcher.search(q, 100);
			ScoreDoc[] sd = tds.scoreDocs;
			
			System.out.println("askdhalkshdklashdklhklhqwiruqou        " + sd.length);
			
			for (int i = 0; i < sd.length; i++) {
				int docId = sd[i].doc;
				if (xx.contains(docId)) {
					continue;
				}
				Document d = searcher.doc(docId);
				docList.add(d);
				xx.add(docId);
				ArrayList<String> tokenList = new ArrayList<String>();
				for (String queryToken : queryString) {
					/*******************************************************/
					tokenList.add(queryToken);
					/********************************************************/
					// Term tm = new Term("abstract",queryToken);
				}
				String sentence = getCandSent(d.get("abstract"), tokenList);
				System.out.println(sentence);

				candSent.add(d.get("abstract"));
				// System.out.println((i + 1) + ". " + d.get("pmid"));
				// System.out.println((i + 1) + ". " + d.get("abstract"));
				// System.out.println("----------------------------------");
				for (int x : xx) {
					candId.add("" + x);
				}
				/****************************/
				Map<String, Integer> myMap = new HashMap<String, Integer>();
				String token[] = sentence.toLowerCase().split(" ");
				for (String myToken : token) {
					// System.out.println("token:"+myToken);
					if (myMap.containsKey(myToken)) {
						// System.out.println("contains Key:"+myToken);
						Integer temp = myMap.get(myToken);
						// myMap.remove(myToken);
						myMap.put(myToken, temp + 1);
						// System.out.println("number:"+myMap.get(myToken));
					} else
						myMap.put(myToken, 1);
				}
				/****************************/
				Sentence st = new Sentence(d.get("pmid") + "", sentence, myMap,
						0);
				result.add(st);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return result;
	}

	public String getCandSent(String sentSet, ArrayList<String> tokenList) {
		// System.out.println(sentSet);
		// System.out.println(tokenList);
		String sentence = "";
		if (tokenList.size() == 1) {
			String token = tokenList.get(0);
			sentence = "";
			sentSet = sentSet.toLowerCase();
			int pos = sentSet.indexOf(token);
			int startPos = pos;
			int endPos = pos;
			while (startPos != 0) {
				startPos -= 1;
				if (sentSet.charAt(startPos) == '.'
						|| sentSet.charAt(startPos) == '?') {
					startPos += 1;
					System.out.println("startPos:" + startPos);
					break;
				}

			}
			while (endPos != sentSet.length() - 1) {
				endPos += 1;
				if (sentSet.charAt(endPos) == '.'
						|| sentSet.charAt(endPos) == '?') {
					endPos += 1;
					System.out.println("endPos:" + endPos);
					break;
				}
			}
			sentence = sentSet.substring(startPos, endPos - 1);
		} else {
			final BreakIterator sentenceBreak = BreakIterator
					.getSentenceInstance(Locale.US);
			ArrayList<String> sentences = new ArrayList<String>();
			sentenceBreak.setText(sentSet);
			int start = sentenceBreak.first();
			for (int end = sentenceBreak.next(); end != BreakIterator.DONE; start = end, end = sentenceBreak
					.next()) {
				String mySentence = sentSet.substring(start, end);
				// System.out.println("sssssss:"+mySentence);
				sentences.add(mySentence);
			}
			int[] score = new int[sentences.size()];
			for (int i = 0; i < sentences.size(); i++) {
				String eachSentence = sentences.get(i);
				for (String token : tokenList) {
					if (eachSentence.contains(token)) {
						score[i]++;
					}
				}
			}
			int flagPos = 0;
			for (int i = 0; i < score.length; i++) {
				if (score[flagPos] < score[i])
					flagPos = i;
			}
			sentence = sentences.get(flagPos);
		}

		return sentence;
	}
}
