package org.it.conrtoller;

import jakarta.validation.constraints.NotNull;
import org.it.pojo.Article;
import org.it.pojo.Category;
import org.it.pojo.PageBean;
import org.it.pojo.Result;
import org.it.service.ArticleService;
import org.it.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    // 新增文章
    @PostMapping
    public Result add(@RequestBody @Validated(Article.Add.class) Article article) {
        articleService.add(article);
        return Result.success();
    }

    // 文章分页详情查询
    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId, // 注解意思是前端可以不传
            @RequestParam(required = false) String state
    ) {
        PageBean pb = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    // 获取文章详情
    @GetMapping("/detail")
    public Result<Article> detail(Integer id) {
        Article article = articleService.detail(id);
        return Result.success(article);
    }

    // 更新文章
    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article) {
        // 1、查询要更新的文章是否存在
        Article detail = articleService.detail(article.getId());
        if (detail == null) {
            return Result.error("未找到此文章");
        }
        // 2、查询要更新的文章分类是否存在
        Category category = categoryService.findById(article.getCategoryId());
        if (category == null){
            return Result.error("文章分类不存在");
        }
        //2、更新
        articleService.update(article);
        return Result.success();
    }

    // 删除文章
    @DeleteMapping
    public Result delete(Integer id) {
        if (id == null) {
            return Result.error("文章id不能为空");
        }
        if (articleService.detail(id) == null){
            return Result.error("此文章不存在");
        }
        articleService.delete(id);
        return Result.success();
    }
}
