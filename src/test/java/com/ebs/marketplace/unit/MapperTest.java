package com.ebs.marketplace.unit;

import com.ebs.marketplace.mapper.*;
import com.ebs.marketplace.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private LikesDislikesMapper likesDislikesMapper;

    @Autowired
    private CheckedFilesMapper checkedFilesMapper;

    @Test
    public void userMapperTest() {
        assertEquals(1, userMapper.existsByUsernameOrEmail("ion", "ion"));
        assertEquals(1, userMapper.existsByEmail("ion"));
        assertEquals(1, userMapper.existsByUsername("ion"));
        assertEquals(User.class, userMapper.findByUsernameOrEmail("ion", "ion").getClass());
        assertEquals(User.class, userMapper.findByUsername("ion").getClass());
    }

    @Test
    public void productMapperTest() {
        assertEquals(Product.class, productMapper.findByUserUsername("ion").get(0).getClass());
        assertEquals(Product.class, productMapper.findById(1).getClass());
    }

    @Test
    public void notificationMapperTest() {
        assertEquals(Notification.class, notificationMapper.findByUserId(10).get(0).getClass());
        assertEquals(Notification.class, notificationMapper.findById(1).getClass());
    }

    @Test
    public void likesDislikesMapperTest() {
        assertEquals(LikesDislikes.class, likesDislikesMapper.findByUserIdAndProductId(10, 1).getClass());
    }

    @Test
    public void checkedFilesMapperTest() {
        assertEquals(1, checkedFilesMapper.existsByName("start-products-1.csv"));
    }
}
