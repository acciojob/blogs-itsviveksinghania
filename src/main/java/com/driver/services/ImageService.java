package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository2;

    @Autowired
    BlogRepository blogRepository2;

    public Image createAndReturn(Blog blog, String description, String dimensions){
        Image image = new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);
        image.setBlog(blog);
        imageRepository2.save(image);
        return image;
    }

    public void addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Blog blog = blogRepository2.findById(blogId).get();
        Image image = createAndReturn(blog, description, dimensions);
        List<Image> imageList = blog.getImageList();
        imageList.add(image);
        blog.setImageList(imageList);

        blogRepository2.save(blog); //Just calling the parent repository and child repository will automatically be called.
    }

    public void deleteImage(Integer id){
        imageRepository2.deleteById(id);
    }

    public Image findById(int id) {
        return imageRepository2.findById(id).get();
    }

    public int countImagesInScreen(Image image, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        String dimensions = image.getDimensions();
        int xi = 0;
        int yi = 0;
        int xs = 0;
        int ys = 0;
        int num = 0;
        for(int i = 0; i<dimensions.length(); i++){
            if(dimensions.charAt(i) == 'X'){
                xi = num;
                num = 0;
                continue;
            }
            num *= 10;
            num += (dimensions.charAt(i) - '0');
        }
        yi = num;
        num = 0;
        for(int i = 0; i<screenDimensions.length(); i++){
            if(screenDimensions.charAt(i) == 'X'){
                xs = num;
                num = 0;
                continue;
            }
            num *= 10;
            num += (screenDimensions.charAt(i) - '0');
        }
        ys = num;

        int ans = (int) (Math.floor((new Double(xs))/(new Double(xi))) * Math.floor((new Double(ys))/(new Double(yi))));
        return ans;
    }
}
