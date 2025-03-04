package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.ProductInfo;

public interface IProductService extends IService<ProductInfo> {
    Page<ProductInfo> getAllProduct(Integer page, Integer size);

    ProductInfo getProductById(Integer id);

    boolean addProduct(ProductInfo productInfo);

    boolean updateProductById(Integer id, ProductInfo product);

    boolean deleteProductById(Integer id);
}
