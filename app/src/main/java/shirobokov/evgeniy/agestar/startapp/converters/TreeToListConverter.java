package shirobokov.evgeniy.agestar.startapp.converters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import shirobokov.evgeniy.agestar.startapp.models.Tree;

/**
 * Created by Евгений on 13.08.2015.
 */
public class TreeToListConverter {
    private static Long MIN_UNUSED_ID = 1L;
    private static HashSet<Long> usedIDs;
    private static List<Tree> result;

    private static void setUsedIDs(List<Tree> treeList) {
        for (Tree tree : treeList) {
            if (tree.getId() != null) {
                usedIDs.add(tree.getId());
            }
            if (tree.getSubs() != null) {
                if (!tree.getSubs().isEmpty()) {
                    setUsedIDs(tree.getSubs());
                }
            }
        }
    }

    private static void convertToList(List<Tree> treeList, Tree parent) {
        for (Tree tree : treeList) {
            if (tree.getId() == null) {
                while (usedIDs.contains(MIN_UNUSED_ID)) {
                    MIN_UNUSED_ID++;
                }
                tree.setId(MIN_UNUSED_ID);
                MIN_UNUSED_ID++;
            }
            if (parent != null) {
                tree.setParentId(parent.getId());
            } else {
                tree.setParentId(null);
            }
            result.add(tree);
            if (tree.getSubs() != null) {
                if (!tree.getSubs().isEmpty()) {
                    convertToList(tree.getSubs(), tree);
                }
            }
        }
    }


    public static List<Tree> convert(List<Tree> treeList) {
        usedIDs = new HashSet<>();
        result = new ArrayList<>();
        setUsedIDs(treeList);
        convertToList(treeList, null);
        return result;
    }
}

