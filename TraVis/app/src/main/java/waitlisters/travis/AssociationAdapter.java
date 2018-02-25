package waitlisters.travis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by natha on 2/25/2018.
 */

public class AssociationAdapter extends ArrayAdapter<String> {
    Context context;
    String[] foundThings;
    String language;
    googlAutocompleteParser trans = new googlAutocompleteParser();
    public AssociationAdapter(Context c, String[] stuff, String l) {
        super(c, 0, stuff);
        language = l;
        this.foundThings = stuff;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.associations_row, parent, false);
        }
        TextView original = (TextView) convertView.findViewById(R.id.original);
        TextView translated = (TextView) convertView.findViewById(R.id.translated);
        original.setText(foundThings[position]);
        trans.translateText(foundThings[position], translated, language);

        return convertView;
    }

}
