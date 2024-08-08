package org.it.conrtoller;

import org.it.pojo.Category;
import org.it.pojo.Result;
import org.it.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  文章分类相关
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 新增文章分类
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    // 文章分类查询
    @GetMapping
    public Result<List<Category>> list() {
        List<Category> cs = categoryService.list();
        return Result.success(cs);
    }

    // 获取文章分类详情
    @GetMapping("/detail")
    public Result detail(Integer id) {
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    // 更新文章分类
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    // 删除文章分类
    @DeleteMapping
    public Result delete(Integer id) {
        if (id == null) {
            return Result.error("id不能为空");
        }
        if (categoryService.findById(id) == null){
            return Result.error("未找到此用户");
        }
        categoryService.delete(id);
        return Result.success();
    }
}

















