package com.ra.controller;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Value("${path-upload}")
    private String pathUpload;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("")
    public String product(Model model){
        List<Product> list = productService.getAll();
        model.addAttribute("list",list);
        return "product/index";
    }
    @GetMapping("/add-product")
    public String save(Model model){
        Product product = new Product();
        List<Category> categories  = categoryService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product",product);
        return "product/add";
    }
    @PostMapping("/add-product")
    public String create(@ModelAttribute("product") Product product, @RequestParam("img")MultipartFile file){
        //upload file
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(),new File(pathUpload+fileName));
            //Luu file vao data
            product.setImage(fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.save(product);
        return "redirect:/product";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        productService.delete(id);
        redirectAttrs.addFlashAttribute("success", "Xóa sản phẩm thành công");
        return "redirect:/product";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model) {
        Product product = productService.findById(id);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "product/edit";
    }

    @PostMapping("/update-product")
    public String update(@ModelAttribute("product") Product product,
                         RedirectAttributes redirectAttrs,
                         @RequestParam("img")MultipartFile file) {
        System.out.println(file);
        String fileName = file.getOriginalFilename();
        if(!fileName.isEmpty()){
            try {
                FileCopyUtils.copy(file.getBytes(),new File(pathUpload+fileName));
                //Luu file vao data
                product.setImage(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

       Boolean product1= productService.save(product);
        if(product1) {
            redirectAttrs.addFlashAttribute("success", "Cập nhật sản phẩm thành công");
            return "redirect:/product";
        }
        return "redirect:/product";
    }
}
