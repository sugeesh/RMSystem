
package org.hashtag.rms.util.rest;

import java.util.List;

/**
 * This class is for wrapping the REST Responses for sending to frontend.
 *
 * @author Sugeesh Chandraweera
 */

public class DataTableResponse<T> {

    private long entries;
    private List<T> dataRows;

    public long getEntries() {
        return entries;
    }

    public void setEntries(long entries) {
        this.entries = entries;
    }

    public List<T> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<T> dataRows) {
        this.dataRows = dataRows;
    }
}
