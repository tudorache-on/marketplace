package com.ebs.marketplace.service;

import com.ebs.marketplace.mapper.LikesDislikesMapper;
import com.ebs.marketplace.mapper.ProductMapper;
import com.ebs.marketplace.mapper.UserMapper;
import com.ebs.marketplace.model.LikesDislikes;
import com.ebs.marketplace.model.Product;
import com.ebs.marketplace.model.User;
import com.ebs.marketplace.model.ProductDto;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final LikesDislikesMapper likesDislikesMapper;

    @Autowired
    public ProductService(UserMapper userMapper, ProductMapper productMapper, LikesDislikesMapper likesDislikesMapper) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.likesDislikesMapper = likesDislikesMapper;
    }

    public Product createProduct(ProductDto productData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Product product = new Product();
        product.setTitle(productData.getTitle());
        product.setDescription(productData.getDescription());
        product.setPrice(productData.getPrice());
        product.setUserUsername(currentUsername);
        productMapper.insert(product);

        return product;
    }

    public List<Product> getProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return productMapper.findByUserUsername(currentUsername);
    }

    public void deleteProduct(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Product product = productMapper.findById(id);
        if (product == null) throw new IllegalArgumentException("Object does not exist!");
        if (product.getUserUsername().equals(currentUsername)) productMapper.deleteById(id);
    }

    public Product updateProduct(Long prod_id, ProductDto productDetails) {
        Product product = productMapper.findById(prod_id);
        if (product == null) throw new IllegalArgumentException("Object does not exist!");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (product.getUserUsername().equals(currentUsername)) {
            product.setTitle(productDetails.getTitle());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
        }
        productMapper.update(product);
        return product;
    }

    public List<Product> getAllPosts(int pageNr, int pageSize) {

        RowBounds rowBounds = new RowBounds((pageNr - 1) * pageSize, pageSize);

        return productMapper.findAll(rowBounds);
    }

    public void likeDislikeManager(long id, String likeDislike) {
        Product product = productMapper.findById(id);

        if (product == null) throw new IllegalArgumentException("Object does not exist!");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userMapper.findByUsername(currentUsername);

        if (currentUsername.equals(product.getUserUsername())) return;

        LikesDislikes productLikeDislike = likesDislikesMapper.findByUserIdAndProductId(user.getId(), id);

        if (likeDislike.equals("none")) likesDislikesMapper.deleteByUserIdAndProductId(user.getId(), id);

        if (!likeDislike.equals("like") && !likeDislike.equals("dislike")) {
            product.setLikesCounter(likesDislikesMapper.countByProductIdAndLikesDislikes(id, "like"));
            product.setDislikesCounter(likesDislikesMapper.countByProductIdAndLikesDislikes(id, "dislike"));
            productMapper.update(product);
            return;
        }

        if (productLikeDislike == null)
            likesDislikesMapper.insert(new LikesDislikes(user.getId(), id, likeDislike));
        else {
            productLikeDislike.setLikeDislike(likeDislike);
            likesDislikesMapper.update(productLikeDislike);
        }


        product.setLikesCounter(likesDislikesMapper.countByProductIdAndLikesDislikes(id, "like"));
        product.setDislikesCounter(likesDislikesMapper.countByProductIdAndLikesDislikes(id, "dislike"));
        productMapper.update(product);

    }

}
