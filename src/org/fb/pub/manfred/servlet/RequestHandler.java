package org.fb.pub.manfred.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.fb.pub.manfred.bean.Article;
import org.fb.pub.manfred.bean.PubQAResponse;
import org.fb.pub.manfred.search_engine.SearchTest;
import org.fb.pub.manfred.util.DocToArticle;
import org.fb.pub.manfred.util.JSONParser;

import defaul.Sentence;
import defaul.Test;

public class RequestHandler extends HttpServlet {
	private static String indexDir = "/Users/manfreddrathen/Documents/workspace/02652/XMLIndex/index";
	private SearchTest searchEngine;
	private static int SIZE = 5;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            System.out.println("RequestHandler In--------------!");
            
            //Get request parameters
            String query = req.getParameter("query");

            Test queryTest = new Test(query);
            ArrayList<Sentence> allSentences = queryTest.run();
            
            
            ArrayList<Sentence> sentences;
            if(allSentences.size() < 5)
            	sentences = allSentences;
            else{
                sentences = new ArrayList<Sentence>();
                
                for(int i = 0; i < SIZE; i++){
                	sentences.add(allSentences.get(i));
                }
            }

            
            ArrayList<String> terms = queryTest.getAl();
            
            PubQAResponse res = new PubQAResponse();
            res.setSentences(sentences);
            res.setTerms(terms);
            /*
            //Loading index files
            searchEngine = new SearchTest(indexDir);
            
            //Query building and documents retrival
            String[] terms = query.trim().split(" ");
			ArrayList<Document> docList = searchEngine.retrieval("abstract", terms, num);
			
			ArrayList<Article> articleList = DocToArticle.parse(docList);
			*/
			
			//Use our json parser to parse the document into json string
			JSONParser jsonParser = new JSONParser();
			String response = jsonParser.parse(res);
			System.out.println(response);
			
			//Set response
			resp.setContentType("text/json;charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.print(response);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
