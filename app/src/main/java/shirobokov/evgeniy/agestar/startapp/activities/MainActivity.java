package shirobokov.evgeniy.agestar.startapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import shirobokov.evgeniy.agestar.startapp.R;
import shirobokov.evgeniy.agestar.startapp.converters.ListToTreeConverter;
import shirobokov.evgeniy.agestar.startapp.converters.ListToTreeNodeConverter;
import shirobokov.evgeniy.agestar.startapp.models.Tree;
import shirobokov.evgeniy.agestar.startapp.repository.SQLiteDatabaseHelper;
import shirobokov.evgeniy.agestar.startapp.repository.TreeRepository;
import shirobokov.evgeniy.agestar.startapp.rest.RestRequestTreeTask;

public class MainActivity extends Activity {

    private SQLiteDatabaseHelper db;
    private TreeRepository treeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.db = new SQLiteDatabaseHelper(this);
        this.treeRepository = new TreeRepository(db);

    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView text = (TextView) findViewById(R.id.content_label);
        text.setText("50");

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.menu_layout);

        try {
            List<Tree> treeList = new RestRequestTreeTask(this).execute().get();
            //List<Tree> res = TreeToListConverter.convert(treeList);

//            treeRepository.createTreeList(res);

            List<Tree> newTre = treeRepository.getTreeList();
            List<Tree> res = ListToTreeConverter.convert(newTre);

            TreeNode root = TreeNode.root();
            //TreeNode computerRoot = new TreeNode(new IconTreeItemHolder.TreeItem(1L, "Text", 10L));

            List<TreeNode> convertedTreeNodes = ListToTreeNodeConverter.convert(newTre);


            root.addChildren(convertedTreeNodes);

            AndroidTreeView tView = new AndroidTreeView(this, root);
            tView.setDefaultAnimation(true);
            tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
            tView.setDefaultViewHolder(IconTreeItemHolder.class);

            layout.addView(tView.getView());

            int i = 0;
            i++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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

