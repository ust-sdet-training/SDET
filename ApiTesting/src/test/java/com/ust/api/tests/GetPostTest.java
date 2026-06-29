package com.ust.api.tests;

import com.ust.api.models.Post;
import com.ust.api.specs.SpecFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetPostTest {

    @Test
    void shouldRetrieveAndValidateDetails() {
        Post post = SpecFactory.getPostById(1);

        assertEquals(1, post.getId(), "id should be 1");
        assertEquals(1, post.getUserId(), "userId should be 1");
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", post.getTitle(), "title should match the expected response");
        assertFalse(post.getBody().isBlank(), "body should not be empty");
    }
}
