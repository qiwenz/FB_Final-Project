package defaul;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SolrSynonymParser;
import org.apache.lucene.util.Version;

public class QuestionParse {
  //private WordNetDatabase database;
  private final static String[] SYNSET_TYPES = {"", "noun", "verb"};
  
//  StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
//  SolrSynonymParser x = new SolrSynonymParser(true,true,analyzer);
  

  public String[] parsing(String str){
    
    str = str.toLowerCase().replace('?',' ' );
    int x = str.indexOf("what");
    int y = str.indexOf("what does");
    if(str.startsWith("what does"))       
      str = str.replace("what does","");
    else 
      str = str.replace("what","");
    String[] token = str.split(" "); 
    
    ArrayList<String>myToken = new ArrayList<String>();
    for(String xx:token){
      if(xx.equals(""))
        continue;
      else{
        xx=getSynonym(xx);
        myToken.add(xx);  
      }
           
    }
    String[] token1 = new String[myToken.size()];
    for (int i=0;i<myToken.size();i++) {
      token1[i]=myToken.get(i);
    }
    return token1;
  }
  
  public String getSynonym(String token){
    String sysToken = "";
    if(token.charAt(token.length()-1)=='s'){
      sysToken = token.substring(0, token.length()-1);
      //System.out.println("sysToken:"+sysToken);
    }
    else{
      sysToken = token;
    }
    return sysToken;
  }

  /*
  public QuestionParse() {
   // wordnet.database.dir
    database = WordNetDatabase.getFileInstance();
  }

  public List<Synset> getSynsets(String word) {
        return Arrays.asList(database.getSynsets(word));
  }
  
  
  
  public void synSetTest(String token){
    System.setProperty(PropertyNames.DATABASE_DIRECTORY, "./wn3.1.dict/dict");
    System.out.println(System.getProperty(PropertyNames.DATABASE_DIRECTORY, "."));
    QuestionParse tester = new QuestionParse();
    String word = token;
    List<Synset> synset_list = tester.getSynsets(word);
    System.out.println("\n\n** Process word: " + word);
    for (Synset synset : synset_list) {
      //System.out.println("\nsynset type:       " + SYNSET_TYPES[synset.getType().getCode()]);
      System.out.println("       definition: " + synset.getDefinition());
      // word forms are synonyms:
      for (String wordForm : synset.getWordForms()) {
        if (!wordForm.equals(word)) {
          System.out.println("       synonym:    " + wordForm);
          // antonyms mean the opposite:
          for (WordSense antonym : synset.getAntonyms(wordForm)) {
            //System.out.println(antonym);
            for (String opposite : antonym.getSynset().getWordForms()) {
              System.out.println("             antonym (of " + wordForm+"): " + opposite);
            }
          }
          
        }
      }
      System.out.println("\n");
    }
  }
  */
}
