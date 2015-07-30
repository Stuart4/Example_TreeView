package org.stuartresearch.treeview;

import java.util.ArrayList;

/**
 * Created by jake on 7/29/15.
 */
public class TreeEntry {
    public int indent = 0;
    public String content = "";
    public TreeEntry parent = null;
    public ArrayList<TreeEntry> children = new ArrayList<>(5);
    public boolean isExpanded = true;

    public TreeEntry(String content) {
        this.content = content;
    }

    public TreeEntry() {
    }
}
