import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * When you do a web search, the results page shows you a <a href=
 * "http://searchengineland.com/anatomy-of-a-google-snippet-38357">snippet</a>
 * for each result, showing you search terms in context. For purposes of this
 * project, a snippet is a subsequence of a document that contains all the
 * search terms.
 * given a document (a sequence of
 * words) and set of search terms, find the minimal length subsequence in the
 * document that contains all of the search terms.
 * 
 * 
 */
public class MinimumSnippet {
	List<String> terms = new ArrayList<String>();
	List<String> document = new ArrayList<String>();

	List<Integer> termPos = new ArrayList<Integer>();//positions of terms
	List<String> tempSorce = new ArrayList<String>();//will be changed
	//will be used to check for the shortest occurrence 
	int tempStart;
	int tempEnd;
	int tempLength;

	
	int snipLength = Integer.MAX_VALUE;

	int startingPos;
	int endingPos;

	/**
	 * Compute minimum snippet.
	 * 
	 * Given a document (represented as an List<String>), and a set of terms
	 * (represented as a List<String>), find the shortest subsequence of the
	 * document that contains all of the terms.
	 * 
	 * 
	 */
	public MinimumSnippet(Iterable<String> source, final List<String> terms) {
		if (terms.isEmpty() == true) {
			throw new IllegalArgumentException("terms is empty");
		}
		
		
		for (String t : terms) {
			this.terms.add(t);
		}
		for (String s : source) {
			document.add(s);
			tempSorce.add(s);//will be modified later 
		}
		

		//if there is only one term starting and ending position will be the same and the 
		//positions where the term is found will be added to a list 
	    if (this.terms.size() == 1) {
	    	snipLength = 1;
			for (int d = 0; d < document.size(); d++) {
				if (this.terms.get(0).equals(document.get(d))) {
					startingPos = d;
					endingPos = d;
					termPos.add(d);
				}
			}
		}

		else {
			
			ArrayList<Integer> snippet = new ArrayList<Integer>();//holds the positions
			int round = 0;//number of times going through the loop 
			while (tempSorce.containsAll(terms)){
				snippet.clear();
				//this loop finds the first position of all the terms 
				for (String t : terms) {
					snippet.add(tempSorce.indexOf(t));
				}
				Collections.sort(snippet);

				//sets start, end, and length based on the current positions 
				tempStart = snippet.get(0);
				tempEnd = snippet.get(snippet.size() - 1);
				tempLength = (tempEnd - tempStart) + 1;	

				//if the length of the current snippet is less than the length
				//of the actual snippet (which starts off high so that it does this during 
				//the first round) then the position of the terms will be added to the 
				//termPos (position of terms) list and start, end, and length will be set. 
				if (tempLength < snipLength) {
					termPos.clear();
					startingPos = tempStart + round;
					endingPos = tempEnd + round;
					snipLength = tempLength;
					for (String t : terms) {
						termPos.add(tempSorce.indexOf(t) + round);
					}
				}
				
				//this will take the first found term out so that next time through the loop
				//it will find the next occurrence of all three terms. Then, if the next 
				//occurrence is smaller than then the current length, it will set the 
				// variables according to that occurrence 
				tempSorce.remove(tempStart);
				round++;
			}

		}

	}

	/**
	 * Returns whether or not all terms were found in the document. 
	 * 
	 * @return whether all terms were found in the document.
	 */
	public boolean foundAllTerms() {

		if (terms.size() < 1) {
			throw new IllegalArgumentException();
		}

		if (document.containsAll(terms)){
			return true;
		}
		return false;

	}

	/**
	 * Return the starting position of the snippet
	 * 
	 * @return the index in the document of the first element of the snippet
	 */
	public int getStartingPos() {

		return startingPos;

	}

	/**
	 * Return the ending position of the snippet
	 * 
	 * @return the index in the document of the last element of the snippet
	 */
	public int getEndingPos() {

		return endingPos;

	}

	/**
	 * Return total number of elements contained in the snippet.
	 * 
	 * @return
	 */
	public int getLength() {

		return snipLength;

	}

	/**
	 * Returns the position of one of the search terms as it appears in the
	 * original document
	 * 
	 * @param index
	 * 
	 * @return position of the term in the document
	 */
	public int getPos(int index) {
		return termPos.get(index);
	}

}
