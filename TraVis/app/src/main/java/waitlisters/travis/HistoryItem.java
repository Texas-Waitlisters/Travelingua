package waitlisters.travis;

import java.io.Serializable;
import java.util.Date;
import com.orm.SugarRecord;

/**
 * Created by ChiaHuaBladeWX on 2/24/2018.
 */

public class HistoryItem extends SugarRecord {
    private long time;
    private String value;

    public HistoryItem() {

    }

    public HistoryItem(String value, long date) {
        this.time = date;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public long getTime() {
        return time;
    }
}
