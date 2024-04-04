package ra.project_md03.model.service.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.entity.Product;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class WishService {
    @Autowired
    HttpSession httpSession;

    List<Product> wishList = new ArrayList<>();

    public List<Product> getWishList() {
        //kiem tra xem trong session co khong, co thi lay va gan vao cartItem/ khong co thi cho bang rong
        wishList = ((httpSession.getAttribute("wishList") != null) ? (List<Product>) httpSession.getAttribute("wishList") : new ArrayList<>());
        return wishList;
    }

    public void addToWishList(Product product) {
        Product product1 = findWishById(product.getProductId());
        if (product1 == null) {
            wishList.add(product);
        }
        httpSession.setAttribute("wishlist",wishList);
    }
    public void deleteWish(Integer id){
        Product product=findWishById(id);
        wishList.remove(product);
        httpSession.setAttribute("wishlist",wishList);
    }


    public Product findWishById(Integer id) {
        for (Product wishList : wishList) {
            if (wishList.getProductId() == id) {
                return wishList;
            }
        }
        return null;
    }


}
