package shirobokov.evgeniy.agestar.startapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import shirobokov.evgeniy.agestar.startapp.R;
import shirobokov.evgeniy.agestar.startapp.converters.ListToTreeNodeConverter;
import shirobokov.evgeniy.agestar.startapp.converters.TreeToListConverter;
import shirobokov.evgeniy.agestar.startapp.models.Tree;
import shirobokov.evgeniy.agestar.startapp.repository.SQLiteDatabaseHelper;
import shirobokov.evgeniy.agestar.startapp.repository.TreeRepository;
import shirobokov.evgeniy.agestar.startapp.rest.RestRequestTreeTask;

public class MainActivity extends Activity {

    private SQLiteDatabaseHelper db;
    private TreeRepository treeRepository;
    private TextView selectedMenu;
    private View treeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.selectedMenu = (TextView) findViewById(R.id.selected_label);
        this.db = new SQLiteDatabaseHelper(this);
        this.treeRepository = new TreeRepository(db);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            List<Tree> treeList = treeRepository.getTreeList();
            if (treeList.isEmpty()) {
                treeList = new RestRequestTreeTask(this).execute().get();
                treeRepository.createTreeList(TreeToListConverter.convert(treeList));
                treeList = treeRepository.getTreeList();
            }
            AddTreeView(treeList);

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
        int id = item.getItemId();
        try {
            if (id == R.id.action_refresh) {
                treeRepository.deleteAll();
                List<Tree> treeList = null;
                treeList = new RestRequestTreeTask(this).execute().get();
                treeRepository.createTreeList(TreeToListConverter.convert(treeList));
                treeList = treeRepository.getTreeList();
                AddTreeView(treeList);
                return true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return super.onOptionsItemSelected(item);
    }

    private void AddTreeView(List<Tree> treeList) {
        TreeNode root = TreeNode.root();
        List<TreeNode> convertedTreeNodes = ListToTreeNodeConverter.convert(treeList);
        root.addChildren(convertedTreeNodes);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.menu_layout);
        AndroidTreeView tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);

        if (this.treeView == null) {
            this.treeView = tView.getView();
        } else {
            ((ViewGroup) this.treeView.getParent()).removeView(treeView);
            this.treeView = tView.getView();
        }

        layout.addView(this.treeView);

    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.TreeItem item = (IconTreeItemHolder.TreeItem) value;
            selectedMenu.setText(item.title);
        }
    };

}

