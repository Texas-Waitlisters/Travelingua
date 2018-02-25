package waitlisters.travis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

public class TranslationActivity extends AppCompatActivity {

    private TextView term;
    private Spinner language_selector;
    private TextView translated_term;
    private Button suggested;


    //TODO: This is how you pass info to this activity:
    /*
    Intent intent = new Intent(getBaseContext(), TranslationActivity.class);
    intent.putExtra("TERM", <The Word>);
    intent.putExtra("LANGUAGE_SELECTED", <The Selected Language Default>);
    startActivity(intent);

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        term = (TextView) findViewById(R.id.term);
        TextView translated_term = (TextView) findViewById(R.id.translated_term);
        language_selector = (Spinner) findViewById(R.id.language_selector);
        this.translated_term = (TextView) findViewById(R.id.translated_term);

        term.setText(getIntent().getStringExtra("TERM"));
        googlAutocompleteParser trans = new googlAutocompleteParser();
        trans.translateText(getIntent().getStringExtra("TERM"),translated_term, "ru");
        boolean newItem = getIntent().getBooleanExtra("NEW", true);
        if (newItem) {
            HistoryItem item = new HistoryItem(term.getText().toString(), new Date().getTime());
            item.save();
        }

        ListView listview = (ListView) this.findViewById(R.id.association_list);

        try{
            googlAutocompleteParser.urlReader(getIntent().getStringExtra("TERM"), listview, this);
        }
        catch(Exception e){}

        String country = getIntent().getStringExtra("COUNTRY");
        TextView tv = (TextView) findViewById(R.id.language);
        tv.setText(country);
        //language_selector.setSelection((indexOfLanguage(getIntent().getStringExtra("LANGUAGE_SELECTED"))));
        //set translated text to something
        //make button take you to google search
    }

    private int indexOfLanguage(String language) {
        //TODO: Fix this
        return 0;
    }

}
