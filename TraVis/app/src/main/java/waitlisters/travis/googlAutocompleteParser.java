package waitlisters.travis;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class googlAutocompleteParser {

	private static final String TAG = MainActivity.class.getSimpleName();
	public String translated;
	private static String API_KEY = "AIzaSyBc324mdQ70KcUJExl5UUaQH6pNamE2hIA";

	public static Translate createTranslateService() {

		return TranslateOptions.newBuilder().setApiKey(API_KEY).build().getService();
	}

	public void translateText(final String sourceText, final TextView text, final String language) {
		new AsyncTask<Object, Void, String>() {
			@Override
			protected String doInBackground(Object... params) {
				try {
					com.google.cloud.translate.Translate translate = TranslateOptions.newBuilder().setTargetLanguage(language).setApiKey("AIzaSyBc324mdQ70KcUJExl5UUaQH6pNamE2hIA").build().getService();
					Log.i(TAG, translate.toString());
					Translation translation = translate.translate(sourceText);
					translated = translation.getTranslatedText();
					Log.i(TAG, sourceText);
					Log.i(TAG, translated);
					return translation.getTranslatedText();
				}
				catch (Exception e){
					Log.e(TAG, e.getStackTrace().toString());
				}
				return "";
			}
			protected void onPostExecute(String result) {
				text.setText(result);
			}
		}.execute();
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
	public static String urlCreator(String term) {
		final String urlBeg = "http://suggestqueries.google.com/complete/search?client=firefox&q=\""; // Beginning of Google AutoComplete query URL
		final String urlEnd = "\""; // End of Google AutoComplete query URL
		term = webSpaceOptim(term); // Checks for spaces in search term string and accounts for them avoiding 404s since web doesn't like spaces

		return urlBeg + term + urlEnd;
	}

	
	
	// Method that returns Google Auto
	public static void urlReader(final String term, final ListView lv, final Context context) throws IOException {

		new AsyncTask<Object, Void, String>() {
			@Override
			protected String doInBackground(Object... params) {
				URL url = null;
				String newURL = urlCreator(term);
				BufferedReader in = null;
				String inputLine1 = "";
				try {
					url = new URL(newURL);
					in = new BufferedReader(new InputStreamReader(url.openStream()));

					String inputLine = "";
					int termLength = term.length(); // checks length of search term
					int charsToRemove = 10 + termLength; // calculates how many characters need to be removed from the beginning of the string
					while ((inputLine = in.readLine()) != null)
						inputLine1 = inputLine.substring(charsToRemove, inputLine.length() - 3); // declares new string with relevant data
					in.close();
				}
				catch(Exception e){}
				return inputLine1;
			}
			protected void onPostExecute(String result) {
				String[] queries;
				if(result!=null)
					queries = result.split("\",\""); // Takes individual AutoComplete results and makes them elements in an array
				else
					queries = new String[0];
				String[] arr = new String[Math.max(10, queries.length)];
				for(int i = 0; i < arr.length && i < queries.length; i++)
					arr[i] = queries[i];
				AssociationAdapter adapter = new AssociationAdapter(context, arr, "ru");
				lv.setAdapter(adapter);
			}
		}.execute();
	}

}