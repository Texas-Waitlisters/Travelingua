package waitlisters.travis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.Assert.assertTrue;


public class HistoryTab extends Fragment {
    private ArrayList<HistoryItem> history;
    private ArrayAdapter adapter;
    private ListView listview;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history_tab, container, false);

        history = (ArrayList<HistoryItem>) HistoryItem.listAll(HistoryItem.class);

        listview = (ListView) rootView.findViewById(R.id.historyList);
        adapter = new HistoryItemAdapter(this.getContext(), history);
        listview.setAdapter(adapter);

        FloatingActionButton delete = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                HistoryItem.deleteAll(HistoryItem.class);
                onResume();
            }
        });

//        populateListView();



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        history = (ArrayList<HistoryItem>) HistoryItem.listAll(HistoryItem.class);

        listview = (ListView) rootView.findViewById(R.id.historyList);
        adapter = new HistoryItemAdapter(this.getContext(), history);
        listview.setAdapter(adapter);
//        if (adapter != null) {
//        adapter.notifyDataSetChanged();
//        listview.setAdapter(adapter);}
    }

    //    private void populateListView() {
//        ArrayAdapter<HistoryItem> adapter = new ArrayAdapter<HistoryItem>(getActivity(), R.layout.list_row, history);
//        listview.setAdapter(adapter);
//
//    }

    public class HistoryItemAdapter extends ArrayAdapter<HistoryItem> {
        Context context;
        ArrayList<HistoryItem> historyItems;

        public HistoryItemAdapter(Context c, ArrayList<HistoryItem> historyItems) {
            super(c, 0, historyItems);
            this.historyItems = historyItems;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
            }
            final TextView term = (TextView) convertView.findViewById(R.id.term);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            term.setText(history.get(position).getValue());
            Date date = new Date(history.get(position).getTime());
            time.setText(date.getMonth() + "/" + date.getDate() + "/" + date.getYear() + " " + date.getHours() + ":" +
                    date.getMinutes() + ":" + date.getSeconds());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), TranslationActivity.class);
                    intent.putExtra("TERM", term.getText().toString());
                    intent.putExtra("LANGUAGE_SELECTED", "English"); //TODO: fix this lang
                    intent.putExtra("NEW", false);
                    getContext().startActivity(intent);
                }
            });

            return convertView;
        }

    }


}
