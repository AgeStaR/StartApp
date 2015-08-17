package shirobokov.evgeniy.agestar.startapp.test.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.List;

import shirobokov.evgeniy.agestar.startapp.repository.SQLiteDatabaseHelper;
import shirobokov.evgeniy.agestar.startapp.repository.TreeRepository;
import shirobokov.evgeniy.agestar.startapp.models.Tree;

/**
 * Created by Евгений on 17.08.2015.
 */
public class TreeRepositoryTest extends AndroidTestCase {
    private SQLiteDatabaseHelper db;
    private TreeRepository treeRepository;
    private Tree tree;
    private boolean result;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        this.db = new SQLiteDatabaseHelper(context);
        this.treeRepository = new TreeRepository(this.db);
    }

    public void testCreateValidTree() {
        givenTreeWithInitData();
        whenTreeIsCreated();
        verifyNoException();
    }

    public void testGetTreeList() {
        givenTreeWithInitData();
        whenTreeListIsGot();
        verifyNoException();
    }

    public void testDeleteAllTrees() {
        givenTreeWithInitData();
        whenAllTreesAreDeleted();
        verifyNoException();
    }

    private void whenAllTreesAreDeleted() {
        try {
            this.treeRepository.createTree(this.tree);
            this.treeRepository.deleteAll();
            List<Tree> treeList = this.treeRepository.getTreeList();
            if (treeList.size() == 0) result = true;
            else
                result = false;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

    }

    private void whenTreeIsCreated() {
        try {
            this.treeRepository.createTree(this.tree);
            List<Tree> treeList = this.treeRepository.getTreeList();
            this.result = true;
        } catch (Exception e) {
            e.printStackTrace();
            this.result = false;
        }
    }

    private void whenTreeListIsGot() {
        try {
            this.treeRepository.createTree(this.tree);
            List<Tree> treeList = this.treeRepository.getTreeList();
            if (treeList.get(0).equals(this.tree)) {
                this.result = true;
            } else {
                this.result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void verifyNoException() {
        assertTrue(this.result);
    }

    private Tree givenTreeWithInitData() {
        tree = new Tree();
        tree.setId(100L);
        tree.setOrder(101L);
        tree.setParentId(null);
        tree.setTitle("TestTitle");
        return tree;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
