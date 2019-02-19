package ru.itpark.concertcalendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itpark.concertcalendar.domain.Product;
import ru.itpark.concertcalendar.service.ProductService;

@Controller
@RequestMapping("/")
public class ProductController { // точка остановки будет срабатывать при вызове конструктора
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping //  -> '/'
    public String getAll(Model model) {
        model.addAttribute("items", service.getAll());
        return "all";
    }

    // -> /1, /2, /3000
    @GetMapping("/{id}") // path variable: id - будем доставать
    public String getById(@PathVariable int id, Model model) { // @PathVariable - достаём то, что было в Path и кладём в параметр
        model.addAttribute("item", service.getById(id));

        return "view";
    }

    // -> /1/edit, /2/edit, /3000/edit
    @GetMapping("/{id}/edit") // <- value = "/edit"
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("item", service.getByIdOrEmpty(id));
        // страничка добавления/редактирования
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable int id,
            @ModelAttribute Product product
    ) {
        service.save(product);

        return "redirect:/";
    }

    @GetMapping("/{id}/remove") // страничка удаления
    public String remove(
            @PathVariable int id,
            Model model
    ) {
        model.addAttribute("item", service.getById(id));
        return "remove";
    }

    @PostMapping("/{id}/remove") // страничка удаления
    public String remove(
            @PathVariable int id
    ) {
        service.removeById(id);
        return "redirect:/";
    }
}
