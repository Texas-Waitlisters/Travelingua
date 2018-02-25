package waitlisters.travis;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.LanguageListOption;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.common.collect.ImmutableList;
import java.io.PrintStream;

import java.net.*;
import java.util.Arrays;
import java.io.*;

public class googlAutocompleteParser {

	public static Translate createTranslateService() {
		return TranslateOptions.newBuilder().setApiKey("bde9fad2828e985c46899720766bc43abdc893ac").build().getService();
	}

	public static String translateText(String sourceText) {
		com.google.cloud.translate.Translate translate = createTranslateService();
		Translation translation = translate.translate(sourceText);
		return translation.getTranslatedText();
	}

	public static void main(String[] args) throws Exception {
		final String urlBeg = "http://suggestqueries.google.com/complete/search?client=firefox&q=\""; // Beginning of Google AutoComplete query URL
		final String urlEnd = "\""; // End of Google AutoComplete query URL
		String term = "bus"; // IN FUTURE SHOULD BE A PARAMETER GIVEN BY OBJECT RECOGNITION 
		term = webSpaceOptim(term); // Checks for spaces in search term string and accounts for them avoiding 404s since web doesn't like spaces

		// Builds newURL with term as query for Google AutoComplete
		String newURL = urlCreator(urlBeg, urlEnd, term);
		// TESTING THAT URL IS ACCURATE
		//System.out.println("Your URL is: " + newURL);

		//		urlReader(newURL, term);
		String inputLine = urlReader(newURL, term); // String with Google AutoComplete results
		String[] queries = inputLine.split("\",\""); // Takes individual AutoComplete results and makes them elements in an array
		// DEBUGGING
//		System.out.println(Arrays.toString(queries)); // Prints the queries array to confirm accurateness
		int arraySize = queries.length;
		// DEBUGGING
//				for (int i = 0; i < arraySize; i++) {
//					System.out.println(queries[i]);
//				}
	}

	public static String[] getTheStuff(String term) throws Exception
	{
		final String urlBeg = "http://suggestqueries.google.com/complete/search?client=firefox&q=\""; // Beginning of Google AutoComplete query URL
		final String urlEnd = "\""; // End of Google AutoComplete query URL
		term = webSpaceOptim(term); // Checks for spaces in search term string and accounts for them avoiding 404s since web doesn't like spaces

		// Builds newURL with term as query for Google AutoComplete
		String newURL = urlCreator(urlBeg, urlEnd, term);
		// TESTING THAT URL IS ACCURATE
		//System.out.println("Your URL is: " + newURL);

		//		urlReader(newURL, term);
		String inputLine = urlReader(newURL, term); // String with Google AutoComplete results
		String[] queries = inputLine.split("\",\""); // Takes individual AutoComplete results and makes them elements in an array
		// DEBUGGING
//		System.out.println(Arrays.toString(queries)); // Prints the queries array to confirm accurateness
		int arraySize = queries.length;
		return queries;
	}
	
	// Method that replaces spaces in the search term with the web friendly characters for spaces
	public static String webSpaceOptim(String term) {
		for (int i = 0; i < term.length(); i++) {
			if (term.charAt(i) == ' ') {
				term = term.replaceAll(" ", "%20");
			} 
		}
		return term;
	}


	
	// Method that creates URL based on search term
	public static String urlCreator(String urlBeg, String urlEnd, String term) {
		String newURL = urlBeg + term + urlEnd; // URL creation (concatenation)
		return newURL;
	}

	
	
	// Method that returns Google Auto
	public static String urlReader(String newURL, String term) throws IOException {
		//	public static void urlReader(String newURL, String term) throws IOException {
		URL url = null;
		// Try catch exception for a newURL
		try {
			url = new URL(newURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		// Initializes string variables
		String inputLine = "";
		String inputLine1 = "";
		int termLength = term.length(); // checks length of search term
		int charsToRemove = 10 + termLength; // calculates how many characters need to be removed from the beginning of the string
		while ((inputLine = in.readLine()) != null)
			inputLine1 = inputLine.substring(charsToRemove, inputLine.length() - 3); // declares new string with relevant data
		in.close();
		return inputLine1;
	}

}