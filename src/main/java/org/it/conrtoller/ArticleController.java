package org.it.conrtoller;

import org.it.pojo.Article;
import org.it.pojo.Result;
import org.it.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    // 新增文章
    @PostMapping
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return Result.success();
    }
}
