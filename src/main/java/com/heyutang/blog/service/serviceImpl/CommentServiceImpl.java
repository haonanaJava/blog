package com.heyutang.blog.service.serviceImpl;

import com.heyutang.blog.dao.CommentRepository;
import com.heyutang.blog.po.Comment;
import com.heyutang.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        return commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
    }
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    public List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentView = new ArrayList<>();
        for (Comment comment:comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentView.add(c);
        }
        combineChildren(commentView);
        return commentView;
    }

    private List<Comment> tempReplys = new ArrayList<>();

    public void combineChildren(List<Comment> comments){
        for (Comment comment:comments){
            List<Comment> reply = comment.getReplyComments();
            for (Comment reply1:reply){
                recursively(reply1);
            }
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }
    public void recursively(Comment comment){
        tempReplys.add(comment);
        if (comment.getReplyComments().size() > 0){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply:replys){
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0){
                    recursively(reply);
                }
            }
        }
    }
}
