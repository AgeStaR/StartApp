package shirobokov.evgeniy.agestar.startapp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Евгений on 12.08.2015.
 */
public class Tree {
    private Long id;
    private String title;
    private Long order;
    private Long parentId;
    private List<Tree> subs;

    public Tree() {
        this.subs = new ArrayList<>();
    }

    public Tree(Long id, String title, Long order, Long parentId) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.parentId = parentId;
        this.subs = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Tree> getSubs() {
        return subs;
    }

    public void setSubs(List<Tree> subs) {
        this.subs = subs;
    }

    public void addSub(Tree tree) {
        this.subs.add(tree);
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}
