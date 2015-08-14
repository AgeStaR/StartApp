package shirobokov.evgeniy.agestar.startapp.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;

import shirobokov.evgeniy.agestar.startapp.R;

/**
 * Created by Евгений on 15.08.2015.
 */
public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.TreeItem> {
    private TextView tvValue;
    private PrintView arrowView;

    public IconTreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, TreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.title);

        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);

        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        //if My computer
        if (node.getLevel() == 1) {
            view.findViewById(R.id.btn_delete).setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class TreeItem {
        public Long id;
        public String title;
        public Long parentId;

        public TreeItem(Long id, String title, Long parentId) {
            this.id = id;
            this.title = title;
            this.parentId = parentId;
        }
    }
}
