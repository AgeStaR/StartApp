package shirobokov.evgeniy.agestar.startapp.converters;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
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
            lookup.put(tree.getId(), new TreeNode(new IconTreeItemHolder.TreeItem(tree.getId(), tree.getTitle(), tree.getParentId())));
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
        return res;
    }
}
