package shirobokov.evgeniy.agestar.startapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import shirobokov.evgeniy.agestar.startapp.R;
import shirobokov.evgeniy.agestar.startapp.rest.RestRequestTreeTask;

public class MainActivity extends Activity {

    @Override
    protected void onStart() {
        super.onStart();
        new RestRequestTreeTask(this).execute();
        TextView text = (TextView) findViewById(R.id.content_label);
        text.setText("50");

        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("MyParentNode");
        TreeNode child0 = new TreeNode("ChildNode0");
        TreeNode child1 = new TreeNode("ChildNode1");

        parent.addChildren(child0, child1);
        root.addChild(parent);

        AndroidTreeView tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.menu_layout);

        layout.addView(tView.getView());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
