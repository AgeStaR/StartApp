package shirobokov.evgeniy.agestar.startapp.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shirobokov.evgeniy.agestar.startapp.models.Tree;

/**
 * Created by Евгений on 14.08.2015.
 */
public class ListToTreeConverter {

    public static List<Tree> convert(List<Tree> treeList) {

        Map<Long, Tree> lookup = new HashMap<>();
        for (Tree tree : treeList) {
            lookup.put(tree.getId(), tree);
        }
        for (Tree tree : lookup.values()) {
            Tree parent;
            if (lookup.containsKey(tree.getParentId())) {
                parent = lookup.get(tree.getParentId());
                parent.addSub(tree);
            }
        }
        List<Tree> res = new ArrayList<>();
        for (Tree tree : lookup.values()) {
            if (tree.getParentId() == null) {
                res.add(tree);
            }
        }
        return res;


//        treeList.ForEach(x => lookup.Add(x.ID, new Node { AssociatedObject = x }));
////        foreach (var item in lookup.Values) {
////            Node proposedParent;
////            if (lookup.TryGetValue(item.AssociatedObject.ParentID, out proposedParent)) {
////                item.Parent = proposedParent;
////                proposedParent.Children.Add(item);
////            }
////        }
//        return lookup.Values.Where(x => x.Parent == null);
    }
}
