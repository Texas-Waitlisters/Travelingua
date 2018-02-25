package waitlisters.travis;

import android.content.Context;
import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_tab, container, false);

//        history = (ArrayList<HistoryItem>) HistoryItem.listAll(HistoryItem.class);
        history = new ArrayList<HistoryItem>();
        history.add(new HistoryItem("dsfa", 0));
        history.add(new HistoryItem("lihjkm", 0));



        listview = (ListView) rootView.findViewById(R.id.historyList);
        HistoryItemAdapter adapter = new HistoryItemAdapter(this.getContext(), history);
        listview.setAdapter(adapter);

//        populateListView();



        return rootView;
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
            TextView term = (TextView) convertView.findViewById(R.id.term);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            term.setText(history.get(position).getValue());
            Date date = new Date(history.get(position).getTime());
            time.setText(date.getMonth() + "/" + date.getDate() + "/" + date.getYear() + " " + date.getHours() + ":" +
                    date.getMinutes() + ":" + date.getSeconds());

            return convertView;
        }

    }


}
