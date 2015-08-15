package shirobokov.evgeniy.agestar.startapp.converters;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import shirobokov.evgeniy.agestar.startapp.activities.IconTreeItemHolder;
import shirobokov.evgeniy.agestar.startapp.models.Tree;

/**
 * Created by Евгений on 15.08.2015.
 */
public class ListToTreeNodeConverter {
    public static List<TreeNode> convert(List<Tree> treeList) {

        Map<Long, TreeNode> lookup = new HashMap<>();
        for (Tree tree : treeList) {
            lookup.put(tree.getId(), new TreeNode(new IconTreeItemHolder.TreeItem(tree.getId(), tree.getTitle(), tree.getOrder(), tree.getParentId())));
        }
        for (TreeNode tree : lookup.values()) {
            TreeNode parent;

            IconTreeItemHolder.TreeItem node = (IconTreeItemHolder.TreeItem) tree.getValue();
            if (node.parentId != null) {
                if (lookup.containsKey(node.parentId)) {
                    parent = lookup.get(node.parentId);
                    parent.addChild(tree);
                }
            }
        }
        List<TreeNode> res = new ArrayList<>();
        for (TreeNode tree : lookup.values()) {
            IconTreeItemHolder.TreeItem node = (IconTreeItemHolder.TreeItem) tree.getValue();
            if (node.parentId == null) {
                res.add(tree);
            }
        }
        return doSort(res);
    }

    private static List<TreeNode> doSort(List<TreeNode> treeNodeList) {
        TreeNode root = new TreeNode(new IconTreeItemHolder.TreeItem(null, "root", null, null));
        sort(treeNodeList, root);
        return root.getChildren();
    }

    private static void sort(List<TreeNode> treeNodeList, TreeNode parentNode) {
        List<TreeNode> changeableList = new ArrayList<>(treeNodeList);
        Collections.sort(changeableList, OrderComparator);
        treeNodeList = changeableList;
        parentNode.addChildren(treeNodeList);
        for (TreeNode treeNode : treeNodeList) {
            if (!treeNode.getChildren().isEmpty() && treeNode.getChildren().size() != 1) {
                sort(treeNode.getChildren(), treeNode);
            }
        }

        for (TreeNode treeNode : changeableList) {
            int size = treeNode.getChildren().size() / 2;
            Iterator it = treeNode.getChildren().iterator();
            while (size != 0) {
                it = treeNode.getChildren().iterator();
                TreeNode tree = (TreeNode) it.next();
                treeNode.deleteChild(tree);
                size--;
            }
        }

    }

    private static Comparator<TreeNode> OrderComparator
            = new Comparator<TreeNode>() {

        public int compare(TreeNode node1, TreeNode node2) {
            IconTreeItemHolder.TreeItem item1 = (IconTreeItemHolder.TreeItem) node1.getValue();
            IconTreeItemHolder.TreeItem item2 = (IconTreeItemHolder.TreeItem) node2.getValue();
            return item1.order.intValue() - (int) item2.order.intValue();
        }

    };

}
