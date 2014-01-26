package org.fb.pub.manfred.search_engine;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchTest {
	private IndexSearcher searcher;
	public SearchTest(String path) throws IOException{
		
		Directory destDir = FSDirectory.open(new File(path));		
		IndexReader reader = DirectoryReader.open(destDir);
	    searcher = new IndexSearcher(reader);
	}
	
	public IndexSearcher getSearcher() {
		return searcher;
	}

	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}

	public ArrayList<Document> retrieval(String field, String[] terms, int num) throws Exception {
		if(terms == null || terms.length == 0)
			return null;
		
		ArrayList<Document> docList = new ArrayList<Document>();

		QueryParser queryParser = new QueryParser(Version.LUCENE_40, field ,new StandardAnalyzer(Version.LUCENE_40));
		
		String queryString = terms[0].trim();
		for(int i = 1; i < terms.length; i++){
			queryString = queryString + " AND " + terms[i].trim();
		}
		
		Query query = queryParser.parse(queryString);
		
		TopScoreDocCollector collector = TopScoreDocCollector.create(num, true);
	    searcher.search(query, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    
	    System.out.println("Found " + hits.length + " hits.");
	    
	    for(int i=0;i<hits.length;++i) {
	      int docId = hits[i].doc;
	      Document d = searcher.doc(docId);
	      docList.add(d);
	      System.out.println((i + 1) + ". " + d.get("abstract"));
	    }
	    return docList;
	}
}
