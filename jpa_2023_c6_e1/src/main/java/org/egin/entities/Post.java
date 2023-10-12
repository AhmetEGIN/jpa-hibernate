package org.egin.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;



    // Tek taraflı ilişkiyi bu tarafta kurabilmek için JoinTable kullanmamız gerekmektedir.
    // one-to-many ilişkilerde mappedBy opposite tarafta kullanılır
//    @JoinColumn(name = "post_id") // JoinColumn ownerside da olmalıdır. Burada comment tarafına column eklenir.
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
