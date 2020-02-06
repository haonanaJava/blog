package com.heyutang.blog;

import com.heyutang.blog.dao.CommentRepository;
import com.heyutang.blog.po.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Value("${comment.avatar}")
    String avatar ;
    @Autowired
    CommentRepository commentRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testListComment(){
        Long blogId = 4L;
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        List<Comment> commentList = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        for (Comment comment:commentList){
            System.out.println(comment);
        }
    }

    @Test
    public void testGetValue(){
        System.out.println(avatar);
    }

}
