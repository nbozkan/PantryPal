package com.nicoleozkan.pantrypal.controller;

import com.nicoleozkan.pantrypal.model.Product;
import com.nicoleozkan.pantrypal.model.User;
import com.nicoleozkan.pantrypal.model.UserProduct;
import com.nicoleozkan.pantrypal.repository.ProductRepository;
import com.nicoleozkan.pantrypal.repository.UserProductRepository;
import com.nicoleozkan.pantrypal.repository.UserRepository;
import com.nicoleozkan.pantrypal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ProductController
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    @Autowired
    private UserService userService;

    Set<ConstraintViolation<Product>> productViolations = new HashSet<>();

    Set<ConstraintViolation<UserProduct>> userProductViolations = new HashSet<>();

    // Routes to profile home page
    @GetMapping("/profile")
    public String getProfilePage(Model model)
    {
        // Sends logged-in user with page
        User user = userService.getPrincipal();
        model.addAttribute("user", user);

        // Add list of products to addProduct form
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        // Add user's userProduct list to populate pantry table
        List<UserProduct> userProductList = user.getUserProducts();
        model.addAttribute("userProductList", userProductList);

        // Add error lists to page
        productViolations = new HashSet<>();
        model.addAttribute("productErrors", productViolations);
        userProductViolations = new HashSet<>();
        model.addAttribute("userProductErrors", userProductViolations);

        return "profile";
    }

    // Creates a product object and adds to database
    @PostMapping("/createProductForm")
    public String createProductForm(HttpServletRequest request, Model model)
    {
        // Create product
        Product product = new Product();
        product.setProductName(request.getParameter("productName"));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        productViolations = validate.validate(product);
        model.addAttribute("productErrors", productViolations);

        // If invalid input, reload page
        if (!productViolations.isEmpty())
        {
            model.addAttribute("user", userService.getPrincipal());
            return "profile";
        }

        // If valid, save objects
        productRepository.save(product);

        return "redirect:/profile";
    }

    // Creates a product object and adds to database
    @PostMapping("/addProductForm")
    public String addProductForm(HttpServletRequest request, Model model)
    {
        // Create userProduct and set fields
        UserProduct userProduct = new UserProduct();
        Product product = productRepository.getProductByProductName(request.getParameter("prodName"));
        userProduct.setProduct(product);
        userProduct.setProductId(product.getProductId());
        userProduct.setTotalAmount(request.getParameter("totalAmount"));
        userProduct.setUnit(request.getParameter("unit"));
        userProduct.setProductNotes(request.getParameter("productNotes"));

        // Get user, set userProduct to user's userProductList, set user to userProduct
        User user = userService.getPrincipal();
        userProduct.setUser(user);
        userProduct.setUserId(user.getUserId());

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        userProductViolations = validate.validate(userProduct);
        model.addAttribute("userProductErrors", userProductViolations);

        // If invalid input, reload page
        if (!userProductViolations.isEmpty())
        {
            model.addAttribute("user", userService.getPrincipal());
            return "profile";
        }

        // If valid, save objects
        userProductRepository.save(userProduct);

        return "redirect:/profile";
    }

    // Returns product page populated with loaded userProduct's data
    @GetMapping("/product/{userProductId}")
    public String getProductPage(@PathVariable Long userProductId, Model model)
    {
        model.addAttribute("userProduct", userProductRepository.findById(userProductId).get());
        productViolations = new HashSet<>();
        model.addAttribute("productErrors", productViolations);
        return "product";
    }

    // Returns update-product populated with loaded userProduct's data
    @GetMapping("/updateProduct/{userProductId}")
    public String editProductForm(@PathVariable Long userProductId, Model model)
    {
        UserProduct userProduct = userProductRepository.findById(userProductId).get();
        model.addAttribute("userProduct", userProduct);
        productViolations = new HashSet<>();
        model.addAttribute("productErrors", productViolations);
        userProductViolations = new HashSet<>();
        model.addAttribute("userProductErrors", userProductViolations);
        return "update-product";
    }

    // Updates userProduct with given input
    @PostMapping("/updateProduct")
    public String updateProduct(HttpServletRequest request, Model model)
    {
        // Update userProduct
        Long userProductId = Long.valueOf(request.getParameter("userProductId"));
        UserProduct userProduct = userProductRepository.findById(userProductId).get();

        // Calculate new totalAmount
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
        double amountUsed  = Double.parseDouble(request.getParameter("amountUsed"));

        // If updated totalAmount <= 0, it's deleted from user's userProduct list
        int compare = Double.compare(totalAmount, amountUsed);
        if (compare <= 0)
        {
            userProductRepository.deleteById(userProductId);
            return "redirect:/profile";
        }
        else
        {
            totalAmount = totalAmount - amountUsed;
            userProduct.setTotalAmount(String.valueOf(totalAmount));
        }

        userProduct.setUnit(request.getParameter("unit"));
        userProduct.setProductNotes(request.getParameter("productNotes"));

        // Validates userProduct fields
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        userProductViolations = validate.validate(userProduct);
        model.addAttribute("userProductErrors", userProductViolations);

        // If invalid input, reload page
        if (!userProductViolations.isEmpty())
        {
            model.addAttribute("userProduct", userProduct);
            productViolations = new HashSet<>();
            model.addAttribute("productErrors", productViolations);
            return "update-product";
        }

        // If valid, update userProduct
        userProductRepository.save(userProduct);

        return "redirect:/profile";
    }

    // Returns delete-product page populated with userProduct data
    @GetMapping("/deleteProduct/{userProductId}")
    public String deleteProduct(@PathVariable Long userProductId, Model model)
    {
        UserProduct userProduct = userProductRepository.findById(userProductId).get();
        model.addAttribute("userProduct", userProduct);
        productViolations = new HashSet<>();
        model.addAttribute("productErrors", productViolations);
        return "delete-product";
    }

    // Deletes userProduct from user's list
    @PostMapping("/deleteProduct")
    public String deleteProductForm(UserProduct userProduct)
    {
        userProductRepository.deleteById(userProduct.getUserProductId());
        return "redirect:/profile";
    }
}