package waitlisters.travis;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TranslationActivity extends AppCompatActivity {

    private TextView term;
    private Spinner language_selector;
    private TextView translated_text;
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
        language_selector = (Spinner) findViewById(R.id.language_selector);
        translated_text = (TextView) findViewById(R.id.translated_text);
        suggested = (Button) findViewById(R.id.suggestions);

        term.setText(getIntent().getStringExtra("TERM"));
        language_selector.setSelection((indexOfLanguage(getIntent().getStringExtra("LANGUAGE_SELECTED"))));
        //set translated text to something
        //make button take you to google search
    }

    private int indexOfLanguage(String language) {
        //TODO: Fix this
        return 0;
    }

}
