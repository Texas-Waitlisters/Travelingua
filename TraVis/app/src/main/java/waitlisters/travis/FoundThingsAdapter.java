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

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ChiaHuaBladeWX on 2/25/2018.
 */

public class FoundThingsAdapter extends ArrayAdapter<String[]> {
    Context context;
    String[][] foundThings;

    public FoundThingsAdapter(Context c, String[][] foundThings) {
        super(c, 0, foundThings);
        this.foundThings = foundThings;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.found_things_row, parent, false);
        }
        TextView rating = (TextView) convertView.findViewById(R.id.ratingTextview);
        TextView term = (TextView) convertView.findViewById(R.id.termTextview);
        rating.setText(foundThings[position][0]);
        term.setText(foundThings[position][1]);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TranslationActivity.class);
                intent.putExtra("TERM", foundThings[position][1]);
                intent.putExtra("LANGUAGE_SELECTED", "English");
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }

}
