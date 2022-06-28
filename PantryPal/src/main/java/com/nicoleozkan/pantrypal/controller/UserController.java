package com.nicoleozkan.pantrypal.controller;

import com.nicoleozkan.pantrypal.model.Product;
import com.nicoleozkan.pantrypal.model.User;
import com.nicoleozkan.pantrypal.repository.ProductRepository;
import com.nicoleozkan.pantrypal.repository.UserProductRepository;
import com.nicoleozkan.pantrypal.repository.UserRepository;
import com.nicoleozkan.pantrypal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProductRepository userProductRepository;
    @Autowired
    private UserService userService;

    Set<ConstraintViolation<User>> violations = new HashSet<>();

    Set<ConstraintViolation<Product>> productViolations = new HashSet<>();

    // Opens home page
    @GetMapping("/index")
    public String viewHomePage()
    {
        return "index";
    }

    // Routes to login page
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    // Creates a user object when sent to signup form for form to reference
    @GetMapping("/signupForm")
    public String signupUser(Model model)
    {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Creates a user object and adds it to the database
    @PostMapping("/processSignup")
    public String processSignup(User user)
    {
        // Add user to database
        userService.registerUser(user);
        return "login";
    }

    // Routes to updateProfile page
    @GetMapping("/updateProfileForm")
    public String updateProfileForm(Model model)
    {
        // Sends logged-in user with page
        model.addAttribute("user", userService.getPrincipal());

        // Sends state list with page to populate dropdown menu
        model.addAttribute("states", stateList());

        // Create set to hold user errors and send with page
        violations = new HashSet<>();
        model.addAttribute("errors", violations);

        // Create set to hold product errors and send with page
        productViolations = new HashSet<>();
        model.addAttribute("productErrors", productViolations);

        return "update-profile";
    }

    // Updates the user object and saves it to the database
    @PostMapping("/processUpdateUser")
    public String processUpdateUser(HttpServletRequest request, Model model)
    {
        // Set user fields with new input from form
        User user = userRepository.findByEmail(request.getParameter("email"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setPhone(request.getParameter("phone"));
        user.setEmail(request.getParameter("email"));
        user.setAddress1(request.getParameter("address1"));
        user.setAddress2(request.getParameter("address2"));
        user.setCity(request.getParameter("city"));
        user.setState(request.getParameter("state"));
        user.setZipcode(request.getParameter("zipcode"));
        user.setZipExtension(request.getParameter("zipExtension"));

        // Check for errors
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(user);
        model.addAttribute("errors", violations);

        // If invalid input entered, reload page with old user data
        if (!violations.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("states", stateList());
            return "update-profile";
        }

        // Updates valid user in database
        userRepository.save(user);
        return "redirect:/profile";
    }

    // State list object to populate states dropdown menu
    public class State {

        private String stateName;
        private String stateAbbrev;

        public State(String stateName, String stateAbbrev) {
            this.stateName = stateName;
            this.stateAbbrev = stateAbbrev;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getStateAbbrev() {
            return stateAbbrev;
        }

        public void setStateAbbrev(String stateAbbrev) {
            this.stateAbbrev = stateAbbrev;
        }
    }

    public List<State> stateList() {
        List<State> states = new ArrayList<>();

        State al = new State("Alabama", "AL");
        states.add(al);

        State ak = new State("Alaska", "AK");
        states.add(ak);

        State az = new State("Arizona", "AZ");
        states.add(az);

        State ar = new State("Arkansas", "AR");
        states.add(ar);

        State ca = new State("California", "CA");
        states.add(ca);

        State co = new State("Colorado", "CO");
        states.add(co);

        State ct = new State("Connecticut", "CT");
        states.add(ct);

        State de = new State("Delaware", "DE");
        states.add(de);

        State fl = new State("Florida", "FL");
        states.add(fl);

        State ga = new State("Georgia", "GA");
        states.add(ga);

        State hi = new State("Hawaii", "HI");
        states.add(hi);

        State id = new State("Idaho", "ID");
        states.add(id);

        State il = new State("Illinois", "IL");
        states.add(il);

        State in = new State("Indiana", "IN");
        states.add(in);

        State ia = new State("Iowa", "IA");
        states.add(ia);

        State ks = new State("Kansas", "KS");
        states.add(ks);

        State ky = new State("Kentucky", "KY");
        states.add(ky);

        State la = new State("Louisiana", "LA");
        states.add(la);

        State me = new State("Maine", "ME");
        states.add(me);

        State md = new State("Maryland", "MD");
        states.add(md);

        State ma = new State("Massachusetts", "MA");
        states.add(ma);

        State mi = new State("Michigan", "MI");
        states.add(mi);

        State mn = new State("Minnesota", "MN");
        states.add(mn);

        State ms = new State("Mississippi", "MS");
        states.add(ms);

        State mo = new State("Missouri", "MO");
        states.add(mo);

        State mt = new State("Montana", "MT");
        states.add(mt);

        State ne = new State("Nebraska", "NE");
        states.add(ne);

        State nv = new State("Nevada", "NV");
        states.add(nv);

        State nh = new State("New Hampshire", "NH");
        states.add(nh);

        State nj = new State("New Jersey", "NJ");
        states.add(nj);

        State nm = new State("New Mexico", "NM");
        states.add(nm);

        State ny = new State("New York", "NY");
        states.add(ny);

        State nc = new State("North Carolina", "NC");
        states.add(nc);

        State nd = new State("North Dakota", "ND");
        states.add(nd);

        State oh = new State("Ohio", "OH");
        states.add(oh);

        State ok = new State("Oklahoma", "OK");
        states.add(ok);

        State or = new State("Oregon", "OR");
        states.add(or);

        State pa = new State("Pennsylvania", "PA");
        states.add(pa);

        State ri = new State("Rhode Island", "RI");
        states.add(ri);

        State sc = new State("South Carolina", "SC");
        states.add(sc);

        State sd = new State("South Dakota", "SD");
        states.add(sd);

        State tn = new State("Tennessee", "TN");
        states.add(tn);

        State tx = new State("Texas", "TX");
        states.add(tx);

        State ut = new State("Utah", "UT");
        states.add(ut);

        State vt = new State("Vermont", "VT");
        states.add(vt);

        State va  = new State("Virginia", "VA");
        states.add(va);

        State wa = new State("Washington", "WA");
        states.add(wa);

        State wv = new State("West Virgina", "WV");
        states.add(wv);

        State wi = new State("Wisconsin", "WI");
        states.add(wi);

        State wy = new State("Wyoming", "WY");
        states.add(wy);

        return states;
    }
}