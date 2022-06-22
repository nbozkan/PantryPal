package com.nicoleozkan.pantrypal.controller;

import com.nicoleozkan.pantrypal.model.Product;
import com.nicoleozkan.pantrypal.model.User;
import com.nicoleozkan.pantrypal.repository.ProductRepository;
import com.nicoleozkan.pantrypal.repository.UserRepository;
import com.nicoleozkan.pantrypal.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class ProductController
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/createProduct")
    public String getProductForm(Model model)
    {
        model.addAttribute("product", new Product());
        return "createProduct";
    }

    // Creates a product object and adds to database
    @PostMapping("/createProductForm")
    public String addProductForm(@ModelAttribute("product") Product product)
    {
        // Add product to database
        productRepository.save(product);

        // Get user, add product to user product list, then save user
        User user = userService.getPrincipal();
        List<Product> products = user.getProducts();
        products.add(product);
        user.setProducts(products);
        userRepository.save(user);

        return "profile";
    }

    @GetMapping("/productPage/{productId}")
    public String getProductPage(@PathVariable Long productId, Model model)
    {
        model.addAttribute("product", productRepository.findById(productId));
        return "product";
    }

    @GetMapping("/updateProduct/{productId}")
    public String editProductForm(@PathVariable Long productId, Model model)
    {
        model.addAttribute("product", productRepository.findById(productId));
        return "updateProduct";
    }

    @PostMapping("/updateProductForm/{productId}")
    public String updateProduct(@PathVariable Long productId, @ModelAttribute("product") Product product)
    {
        Product updateProduct = productRepository.findById(productId).get();
        updateProduct.setProductId(productId);
        updateProduct.setProductName(product.getProductName());
        updateProduct.setProductNotes(product.getProductNotes());
        updateProduct.setTotalAmount(product.getTotalAmount());
        updateProduct.setAmountUsed(product.getAmountUsed());
        updateProduct.setUnit(product.getUnit());
        productRepository.save(updateProduct);
        return "redirect:/profile";
    }

    @GetMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId)
    {
        productRepository.deleteById(productId);
        return "redirect:/profile";
    }
}