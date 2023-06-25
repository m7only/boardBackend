package m7.graduatework.component;

import m7.graduatework.entity.Ad;
import m7.graduatework.entity.Comment;
import m7.graduatework.service.AdsService;
import m7.graduatework.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CheckPermit {
    private final AdsService adsService;
    private final CommentService commentService;

    public CheckPermit(AdsService adsService, CommentService commentService) {
        this.adsService = adsService;
        this.commentService = commentService;
    }

    public boolean isAdOwnerOrAdmin(Authentication authentication, Long adId) {
        Ad ad = adsService.getAdById(adId);
        if (ad == null) {
            return false;
        }
        return isAdmin(authentication)
                || isOwner(authentication, ad.getAuthor().getUsername());
    }

    public boolean isCommentOwnerOrAdmin(Authentication authentication, Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            return false;
        }
        return isAdmin(authentication)
                || isOwner(authentication, comment.getAuthor().getUsername());
    }

    public boolean isAdmin(Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority
                                .getAuthority()
                                .equals("ROLE_ADMIN")
                );
    }

    public boolean isOwner(Authentication authentication, String username) {
        return authentication.getName().equals(username);
    }
}
